package com.bwongo.user_mgt.service;

import com.bwongo.base.models.enums.ApprovalEnum;
import com.bwongo.base.repository.TCountryRepository;
import com.bwongo.base.service.AuditService;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.user_mgt.models.dto.request.ChangePasswordRequestDto;
import com.bwongo.user_mgt.models.dto.request.UserMetaRequestDto;
import com.bwongo.user_mgt.models.dto.request.UserRequestDto;
import com.bwongo.user_mgt.models.dto.response.UserMetaResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserResponseDto;
import com.bwongo.base.models.enums.UserTypeEnum;
import com.bwongo.user_mgt.models.jpa.TPreviousPassword;
import com.bwongo.user_mgt.models.jpa.TUser;
import com.bwongo.user_mgt.models.jpa.TUserApproval;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import com.bwongo.user_mgt.repository.*;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.bwongo.user_mgt.util.UserMgtUtils.checkThatUserIsAssignable;
import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String PASSWORD = "password";
    private static final String USERTYPE = "userType";

    private final TUserRepository userRepository;
    private final TUserGroupRepository userGroupRepository;
    private final TUserMetaRepository userMetaRepository;
    private final TCountryRepository countryRepository;
    private final TUserApprovalRepository userApprovalRepository;
    private final AuditService auditService;
    private final PasswordEncoder passwordEncoder;
    private final UserMgtDtoService userMgtDtoService;
    private final TPreviousPasswordRepository previousPasswordRepository;
    private final TGroupAuthorityRepository groupAuthorityRepository;

    @Transactional
    public boolean changePassword(ChangePasswordRequestDto changePasswordRequestDto){

        changePasswordRequestDto.validate();
        var existingUser = userRepository.findById(auditService.getLoggedInUser().getId());
        var user = existingUser.get();

        var newPassword = changePasswordRequestDto.newPassword();
        var oldPassword = changePasswordRequestDto.oldPassword();
        var oldEncryptedPassword = user.getPassword();

        Validate.isTrue(passwordEncoder.matches(oldPassword, oldEncryptedPassword), ExceptionType.BAD_REQUEST, OLD_PASSWORDS_DONT_MATCH);
        Validate.isTrue(!newPassword.equals(oldPassword), ExceptionType.BAD_REQUEST, OLD_NEW_SAME_PASSWORD);

        var previousPasswords = previousPasswordRepository.findAllByUser(user);

        previousPasswords.forEach(previousPassword -> Validate.isTrue(!(passwordEncoder.matches(newPassword, previousPassword.getPreviousPassword())) ,
                ExceptionType.BAD_REQUEST,
                PASSWORD_USED_BEFORE));

        var optionalMaxPasswordCount = previousPasswords.stream().mapToInt(TPreviousPassword::getPasswordChangeCount).max();
        int maxPasswordCount = 0;

        if(optionalMaxPasswordCount.isPresent())
            maxPasswordCount = optionalMaxPasswordCount.getAsInt();

        var previousPassword = userMgtDtoService.dtoToTPreviousPassword(changePasswordRequestDto);
        previousPassword.setPreviousPassword(oldEncryptedPassword);
        previousPassword.setPasswordChangeCount(maxPasswordCount + 1);
        previousPassword.setUser(user);

        user.setPassword(passwordEncoder.encode(newPassword));
        auditService.stampLongEntity(user);
        userRepository.save(user);

        auditService.stampLongEntity(previousPassword);
        previousPasswordRepository.save(previousPassword);

        return Boolean.TRUE;
    }

    @Transactional
    public UserResponseDto addUser(UserRequestDto userRequestDto) {

        userRequestDto.validate();
        Validate.notEmpty(userRequestDto.password(), PASSWORD_REQUIRED);
        StringRegExUtil.stringOfStandardPassword(userRequestDto.password(), STANDARD_PASSWORD);

        var existingUserUsername = userRepository.findByUsername(userRequestDto.username());
        Validate.isTrue(existingUserUsername.isEmpty(), ExceptionType.BAD_REQUEST,USERNAME_TAKEN, userRequestDto.username());

        var existingUserGroup = userGroupRepository.findById(userRequestDto.userGroupId());
        Validate.isPresent(existingUserGroup, USER_GROUP_DOES_NOT_EXIST, userRequestDto.userGroupId());
        final var userGroup = existingUserGroup.get();

        var user = userMgtDtoService.dtoToTUser(userRequestDto);
        user.setAccountExpired(Boolean.FALSE);
        user.setAccountLocked(Boolean.FALSE);
        user.setApproved(Boolean.FALSE);
        user.setDeleted(Boolean.FALSE);
        user.setInitialPasswordReset(Boolean.FALSE);
        user.setPassword(passwordEncoder.encode(userRequestDto.password()));
        user.setUserGroup(userGroup);
        user.setUserType(UserTypeEnum.valueOf(userRequestDto.userType()));

        auditService.stampLongEntity(user);
        var savedUser = userRepository.save(user);

        //initiate user approval
        var userApproval = new TUserApproval();
        userApproval.setUser(user);
        userApproval.setStatus(ApprovalEnum.PENDING);
        auditService.stampAuditedEntity(userApproval);

        userApprovalRepository.save(userApproval);

        return userMgtDtoService.mapTUserToUserResponseDto(savedUser);
    }

    @Transactional
    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {

        userRequestDto.validate();
        Validate.isTrue(userRequestDto.password() == null, ExceptionType.BAD_REQUEST, CANNOT_UPDATE_PASSWORD);

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, String.format(USER_DOES_NOT_EXIST, userId));

        var existingUserGroup = userGroupRepository.findById(userRequestDto.userGroupId());
        Validate.isPresent(existingUserGroup, USER_GROUP_DOES_NOT_EXIST, userRequestDto.userGroupId());
        final var userGroup = existingUserGroup.get();

        var user = userMgtDtoService.dtoToTUser(userRequestDto);
        user.setUserGroup(userGroup);

        return userMgtDtoService.mapTUserToUserResponseDto(userRepository.save(user));
    }

    public UserResponseDto getUserById(Long id) {
        return userMgtDtoService.mapTUserToUserResponseDto(getUser(id));
    }

    public UserMetaResponseDto getUserByEmail(String email) {

        var existingUser = userMetaRepository.findByEmail(email);
        Validate.isPresent(existingUser, USER_WITH_EMAIL_DOES_NOT_EXIST, email);

        var userMeta = new TUserMeta();
        if(existingUser.isPresent())
            userMeta = existingUser.get();

        return userMgtDtoService.mapTUserMetaToUserMetaResponseDto(userMeta);
    }

    public UserMetaResponseDto getUserByPhoneNumber(String phoneNumber) {
        var existingUser = userMetaRepository.findByPhoneNumber(phoneNumber);
        Validate.isPresent(existingUser, USER_WITH_PHONE_NUMBER_DOES_NOT_EXIST, phoneNumber);

        var userMeta = new TUserMeta();
        if(existingUser.isPresent())
            userMeta = existingUser.get();

        return userMgtDtoService.mapTUserMetaToUserMetaResponseDto(userMeta);
    }

    public UserResponseDto reAssignUserToGroup(Long groupId, Long userId) {

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, userId);
        checkThatUserIsAssignable(existingUser.get());

        var existingUserGroup = userGroupRepository.findById(groupId);
        Validate.isPresent(existingUser, USER_GROUP_DOES_NOT_EXIST, groupId);
        final var userGroup = existingUserGroup.get();

        Validate.isTrue(groupId == userGroup.getId(), ExceptionType.BAD_REQUEST, String.format(USER_ALREADY_ASSIGNED_TO_USER_GROUP, groupId));

        existingUser.get().setUserGroup(existingUserGroup.get());
        auditService.stampLongEntity(existingUser.get());

        return userMgtDtoService.mapTUserToUserResponseDto(existingUser.get());

    }

    @Transactional
    public UserMetaResponseDto addUserMetaData(Long userId, UserMetaRequestDto userMetaRequestDto) {

        userMetaRequestDto.validate();

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, userId);
        final var user = existingUser.get();
        if(user.getUserType().equals(UserTypeEnum.ADMIN))
            checkThatUserIsAssignable(user);

        var existingCountry = countryRepository.findById(userMetaRequestDto.countryId());
        Validate.isPresent(existingCountry, COUNTRY_WITH_ID_NOT_FOUND, userMetaRequestDto.countryId());
        final var country = existingCountry.get();

        Validate.isTrue(!userMetaRepository.existsByEmail(userMetaRequestDto.email()), ExceptionType.BAD_REQUEST, EMAIL_ALREADY_TAKEN, userMetaRequestDto.email());
        Validate.isTrue(!userMetaRepository.existsByPhoneNumber(userMetaRequestDto.phoneNumber()), ExceptionType.BAD_REQUEST, PHONE_NUMBER_ALREADY_TAKEN, userMetaRequestDto.phoneNumber());
        Validate.isTrue(!userMetaRepository.existsByPhoneNumber2(userMetaRequestDto.phoneNumber2()), ExceptionType.BAD_REQUEST, SECOND_PHONE_NUMBER_ALREADY_TAKEN, userMetaRequestDto.phoneNumber2());

        var userMeta = userMgtDtoService.mapUserMetaRequestDtoToTUserMeta(userMetaRequestDto);
        userMeta.setCountry(country);
        userMeta.setDisplayName(user.getUsername());
        userMeta.setNonVerifiedEmail(Boolean.FALSE);
        userMeta.setNonVerifiedPhoneNumber(Boolean.TRUE);
        auditService.stampAuditedEntity(userMeta);

        var result = userMetaRepository.save(userMeta);
        user.setUserMetaId(result.getId());
        auditService.stampLongEntity(user);

        userRepository.save(user);

        return userMgtDtoService.mapTUserMetaToUserMetaResponseDto(result);
    }

    @Transactional
    public UserMetaResponseDto updateUserMetaData(Long id, UserMetaRequestDto userMetaRequestDto) {

        var existingMetaData = userMetaRepository.findById(id);
        Validate.isPresent(existingMetaData, USER_META_NOT_FOUND, id);
        var metaData = existingMetaData.get();

        var existingCountry = countryRepository.findById(userMetaRequestDto.countryId());
        Validate.isPresent(existingCountry, COUNTRY_WITH_ID_NOT_FOUND, userMetaRequestDto.countryId());
        final var country = existingCountry.get();

        if(!metaData.getEmail().equals(userMetaRequestDto.email()))
            Validate.isTrue(!userMetaRepository.existsByEmail(userMetaRequestDto.email()), ExceptionType.BAD_REQUEST, EMAIL_ALREADY_TAKEN, userMetaRequestDto.email());

        if(!metaData.getPhoneNumber().equals(userMetaRequestDto.phoneNumber()))
            Validate.isTrue(!userMetaRepository.existsByPhoneNumber(userMetaRequestDto.phoneNumber()), ExceptionType.BAD_REQUEST, PHONE_NUMBER_ALREADY_TAKEN, userMetaRequestDto.phoneNumber());

        if(!metaData.getPhoneNumber2().equals(userMetaRequestDto.phoneNumber2()))
            Validate.isTrue(!userMetaRepository.existsByPhoneNumber2(userMetaRequestDto.phoneNumber2()), ExceptionType.BAD_REQUEST, SECOND_PHONE_NUMBER_ALREADY_TAKEN, userMetaRequestDto.phoneNumber2());

        var userMeta = userMgtDtoService.mapUserMetaRequestDtoToTUserMeta(userMetaRequestDto);
        userMeta.setId(id);
        userMeta.setCountry(country);
        userMeta.setDisplayName(metaData.getDisplayName());
        userMeta.setNonVerifiedEmail(Boolean.FALSE);
        userMeta.setNonVerifiedPhoneNumber(Boolean.TRUE);
        auditService.stampAuditedEntity(userMeta);

        return userMgtDtoService.mapTUserMetaToUserMetaResponseDto(userMetaRepository.save(userMeta));
    }


    public UserMetaResponseDto getUserMetaData(Long id) {

        var existingUserMeta = userMetaRepository.findById(id);
        Validate.isPresent(existingUserMeta, USER_DOES_NOT_EXIST, id);

        var userMeta = new TUserMeta();
        if(existingUserMeta.isPresent())
            userMeta = existingUserMeta.get();

        return userMgtDtoService.mapTUserMetaToUserMetaResponseDto(userMeta);
    }

    private TUser getUser(Long id){
        var existingUser = userRepository.findById(id);
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, id);

        var user = new TUser();
        if(existingUser.isPresent())
            user = existingUser.get();

        return user;
    }
}
