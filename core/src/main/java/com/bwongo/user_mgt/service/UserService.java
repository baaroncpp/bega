package com.bwongo.user_mgt.service;

import com.bwongo.base.models.enums.ApprovalStatus;
import com.bwongo.base.repository.TCountryRepository;
import com.bwongo.base.service.AuditService;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.user_mgt.models.dto.request.ChangePasswordRequestDto;
import com.bwongo.user_mgt.models.dto.request.UserApprovalRequestDto;
import com.bwongo.user_mgt.models.dto.request.UserMetaRequestDto;
import com.bwongo.user_mgt.models.dto.request.UserRequestDto;
import com.bwongo.user_mgt.models.dto.response.PermissionResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserApprovalResponseDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.INVALID_APPROVAL_STATUS;
import static com.bwongo.apartment_mgt.utils.ApartmentUtil.isApprovalStatus;
import static com.bwongo.base.utils.BaseEnumValidation.isUserType;
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
        userApproval.setStatus(ApprovalStatus.PENDING);
        auditService.stampAuditedEntity(userApproval);

        userApprovalRepository.save(userApproval);

        return userMgtDtoService.mapTUserToUserResponseDto(savedUser);
    }

    @Transactional
    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {

        userRequestDto.validateUpdate();
        Validate.isTrue(userRequestDto.password() == null, ExceptionType.BAD_REQUEST, CANNOT_UPDATE_PASSWORD);

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, String.format(USER_DOES_NOT_EXIST, userId));
        var existingUsername = existingUser.get().getUsername();

        if(!userRequestDto.username().equals(existingUsername))
            Validate.isTrue(!userRepository.existsByUsername(userRequestDto.username()), ExceptionType.BAD_REQUEST, USERNAME_TAKEN, userRequestDto.username());

        var existingUserGroup = userGroupRepository.findById(userRequestDto.userGroupId());
        Validate.isPresent(existingUserGroup, USER_GROUP_DOES_NOT_EXIST, userRequestDto.userGroupId());
        final var userGroup = existingUserGroup.get();

        var user = userMgtDtoService.dtoToTUser(userRequestDto);
        user.setPassword(existingUser.get().getPassword());
        user.setId(existingUser.get().getId());
        user.setUserGroup(userGroup);
        user.setUserType(UserTypeEnum.valueOf(userRequestDto.userType()));
        user.setCreatedOn(existingUser.get().getCreatedOn());

        auditService.stampLongEntity(user);

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

        System.out.println(userMeta.toString());

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
        userMeta.setCreatedOn(metaData.getCreatedOn());
        userMeta.setCreatedBy(metaData.getCreatedBy());
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

    public boolean deleteUserAccount(Long userId) {

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, userId);
        final var user = existingUser.get();

        user.setAccountExpired(Boolean.TRUE);
        user.setAccountLocked(Boolean.TRUE);
        user.setDeleted(Boolean.TRUE);
        user.setApproved(Boolean.FALSE);

        auditService.stampLongEntity(user);
        userRepository.save(user);

        return Boolean.TRUE;
    }

    public List<UserApprovalResponseDto> getUserApprovals(String status, Pageable pageable) {

        Validate.isTrue(isApprovalStatus(status), ExceptionType.BAD_REQUEST, INVALID_APPROVAL_STATUS, status);
        var approvalEnum = ApprovalStatus.valueOf(status);

        return userApprovalRepository.findAllByStatus(approvalEnum, pageable).stream()
                .map(userMgtDtoService::userApprovalToDto)
                .collect(Collectors.toList());
    }

    public List<PermissionResponseDto> getPermissions(){
        return getUserPermissionsById(auditService.getLoggedInUser().getId());
    }

    public List<PermissionResponseDto> getUserPermissionsById(Long id){

        var user = getUser(id);
        var groupAuthorities = groupAuthorityRepository.findByUserGroup(user.getUserGroup());

        return groupAuthorities
                .stream()
                .map(groupAuthority -> userMgtDtoService.permissionToDto(groupAuthority.getPermission()))
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).stream()
                .map(userMgtDtoService::mapTUserToUserResponseDto)
                .collect(Collectors.toList());
    }

    public Long getNumberOfUsersByType(String userType) {
        Validate.isTrue(isUserType(userType), ExceptionType.BAD_REQUEST, VALID_USER_TYPE);
        UserTypeEnum userTypeEnum = UserTypeEnum.valueOf(userType);
        return userRepository.countByUserType(userTypeEnum);
    }

    public boolean suspendUserAccount(Long userId) {

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, userId);
        final var user = existingUser.get();

        Validate.isTrue(!user.isAccountLocked(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_IS_ALREADY_LOCKED);

        user.setAccountLocked(Boolean.TRUE);
        auditService.stampLongEntity(user);
        userRepository.save(user);

        return Boolean.TRUE;
    }

    @Transactional
    public UserResponseDto approveUser(UserApprovalRequestDto userApprovalRequestDto) {

        userApprovalRequestDto.validate();
        Validate.isTrue(!userApprovalRequestDto.status().equals(ApprovalStatus.PENDING.name()), ExceptionType.BAD_REQUEST, PENDING_STATUS_INVALID);

        var existingApproval = userApprovalRepository.findById(userApprovalRequestDto.id());
        Validate.isPresent(existingApproval, USER_APPROVAL_NOT_FOUND, userApprovalRequestDto.id());
        final var userApproval = existingApproval.get();

        Validate.isTrue(!userApproval.getUser().isApproved(), ExceptionType.BAD_REQUEST, USER_ALREADY_APPROVED, userApproval.getUser().getId());

        ApprovalStatus approvalStatus = ApprovalStatus.valueOf(userApprovalRequestDto.status());
        userApproval.setStatus(approvalStatus);
        auditService.stampAuditedEntity(userApproval);

        var savedUserApproval = userApprovalRepository.save(userApproval);

        var existingUser = userRepository.findById(userApproval.getUser().getId());
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, userApproval.getUser().getId());
        final var user = existingUser.get();

        if(savedUserApproval.getStatus().equals(ApprovalStatus.APPROVED)) {
            user.setApproved(Boolean.TRUE);
            user.setAccountExpired(Boolean.FALSE);
            user.setAccountLocked(Boolean.FALSE);
            user.setDeleted(Boolean.FALSE);
            user.setApprovedBy(auditService.getLoggedInUser().getId());
        }

        if(savedUserApproval.getStatus().equals(ApprovalStatus.REJECTED)) {
            user.setApproved(Boolean.FALSE);
            user.setAccountExpired(Boolean.TRUE);
            user.setAccountLocked(Boolean.TRUE);
            user.setDeleted(Boolean.TRUE);
            user.setApprovedBy(auditService.getLoggedInUser().getId());
        }

        auditService.stampLongEntity(user);

        var approvedUser = userRepository.save(user);

        return userMgtDtoService.mapTUserToUserResponseDto(approvedUser);
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
