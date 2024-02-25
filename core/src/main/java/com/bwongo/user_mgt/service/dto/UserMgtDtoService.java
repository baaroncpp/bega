package com.bwongo.user_mgt.service.dto;

import com.bwongo.base.models.enums.IdentificationType;
import com.bwongo.base.service.BaseDtoService;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.user_mgt.models.dto.request.ChangePasswordRequestDto;
import com.bwongo.user_mgt.models.dto.request.NextOfKinRequestDto;
import com.bwongo.user_mgt.models.dto.request.UserRequestDto;
import com.bwongo.user_mgt.models.dto.response.NextOfKinResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserGroupResponseDto;
import com.bwongo.user_mgt.models.dto.request.UserMetaRequestDto;
import com.bwongo.user_mgt.models.dto.response.UserMetaResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserResponseDto;
import com.bwongo.base.models.enums.GenderEnum;
import com.bwongo.base.models.jpa.TCountry;
import com.bwongo.base.models.enums.RelationShipType;
import com.bwongo.user_mgt.models.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.PLACEMENT_DATE_FORMAT;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/5/23
 **/
@Service
@RequiredArgsConstructor
public class UserMgtDtoService {

    private final BaseDtoService baseDtoService;

    public UserResponseDto mapTUserToUserResponseDto(TUser user){

        if(user == null){
            return null;
        }

        return new UserResponseDto(
                user.getId(),
                user.getCreatedOn(),
                user.getModifiedOn(),
                user.getUsername(),
                user.isApproved(),
                mapTUserGroupToUserGroupResponseDto(user.getUserGroup()),
                user.getUserType(),
                user.getUserMetaId()
        );
    }

    public UserGroupResponseDto mapTUserGroupToUserGroupResponseDto(TUserGroup userGroup){

        if(userGroup == null){
            return null;
        }

        return new UserGroupResponseDto(
                userGroup.getId(),
                userGroup.getCreatedOn(),
                userGroup.getModifiedOn(),
                userGroup.getName(),
                userGroup.getNote()
        );
    }

    public TUserMeta mapUserMetaRequestDtoToTUserMeta(UserMetaRequestDto userMetaRequestDto){

        if(userMetaRequestDto == null){
            return null;
        }
        var country = new TCountry();
        country.setId(userMetaRequestDto.countryId());

        var userMeta = new TUserMeta();
        userMeta.setFirstName(userMeta.getFirstName());
        userMeta.setLastName(userMetaRequestDto.lastName());
        userMeta.setPhoneNumber(userMeta.getPhoneNumber());
        userMeta.setPhoneNumber2(userMeta.getPhoneNumber2());
        userMeta.setDisplayName(userMetaRequestDto.displayName());
        userMeta.setGender(GenderEnum.valueOf(userMetaRequestDto.gender()));
        userMeta.setBirthDate(userMetaRequestDto.birthDate());
        userMeta.setEmail(userMetaRequestDto.email());
        userMeta.setCountry(country);
        userMeta.setIdentificationType(IdentificationType.valueOf(userMetaRequestDto.identificationType()));
        userMeta.setIdentificationNumber(userMeta.getIdentificationNumber());

        return userMeta;
    }

    public UserMetaResponseDto mapTUserMetaToUserMetaResponseDto(TUserMeta userMeta){

        if(userMeta == null){
            return null;
        }

        return new UserMetaResponseDto(
                userMeta.getId(),
                userMeta.getCreatedOn(),
                userMeta.getModifiedOn(),
                mapTUserToUserResponseDto(userMeta.getModifiedBy()),
                mapTUserToUserResponseDto(userMeta.getCreatedBy()),
                userMeta.isActive(),
                userMeta.getFirstName(),
                userMeta.getLastName(),
                userMeta.getMiddleName(),
                userMeta.getPhoneNumber(),
                userMeta.getPhoneNumber2(),
                userMeta.getImagePath(),
                userMeta.getDisplayName(),
                userMeta.getGender(),
                userMeta.getBirthDate(),
                userMeta.getEmail(),
                baseDtoService.countryToDto(userMeta.getCountry()),
                userMeta.getIdentificationType(),
                userMeta.getIdentificationNumber(),
                userMeta.getIdentificationPath(),
                userMeta.isNonVerifiedEmail(),
                userMeta.isNonVerifiedPhoneNumber()
        );
    }

    public TNextOfKin mapNextOfKinRequestDtoToTNextOfKin(NextOfKinRequestDto nextOfKinRequestDto){

        if(nextOfKinRequestDto == null){
            return null;
        }

        var nextOfKin = new TNextOfKin();
        nextOfKin.setFirstName(nextOfKinRequestDto.firstName());
        nextOfKin.setSecondName(nextOfKinRequestDto.secondName());
        nextOfKin.setDateOfBirth(DateTimeUtil.stringToDate(nextOfKinRequestDto.birthDate(), PLACEMENT_DATE_FORMAT));
        nextOfKin.setFirstPhoneNumber(nextOfKinRequestDto.phoneNumber());
        nextOfKin.setSecondPhoneNumber(nextOfKinRequestDto.phoneNumber2());
        nextOfKin.setRelationShipType(RelationShipType.valueOf(nextOfKinRequestDto.relationShipType()));
        nextOfKin.setRelationShipNote(nextOfKinRequestDto.relationShipNote());
        nextOfKin.setEmail(nextOfKinRequestDto.email());
        nextOfKin.setIdentificationType(IdentificationType.valueOf(nextOfKinRequestDto.identificationType()));
        nextOfKin.setIdNumber(nextOfKinRequestDto.idNumber());

        return nextOfKin;
    }

    public NextOfKinResponseDto mapTNextOfKinToDto(TNextOfKin nextOfKin){

        if(nextOfKin == null){
            return null;
        }

        return new NextOfKinResponseDto(
                nextOfKin.getId(),
                nextOfKin.getCreatedOn(),
                nextOfKin.getModifiedOn(),
                mapTUserToUserResponseDto(nextOfKin.getModifiedBy()),
                mapTUserToUserResponseDto(nextOfKin.getCreatedBy()),
                nextOfKin.isActive(),
                nextOfKin.getFirstName(),
                nextOfKin.getSecondName(),
                nextOfKin.getDateOfBirth(),
                nextOfKin.getFirstPhoneNumber(),
                nextOfKin.getSecondPhoneNumber(),
                nextOfKin.getRelationShipType(),
                nextOfKin.getRelationShipNote(),
                nextOfKin.getEmail(),
                nextOfKin.getIdentificationType(),
                nextOfKin.getIdNumber(),
                nextOfKin.getIdPhotoUrlPath()
        );
    }

    public TPreviousPassword dtoToTPreviousPassword(ChangePasswordRequestDto changePasswordRequestDto){

        if(changePasswordRequestDto == null){
            return null;
        }

        var previousPassword = new TPreviousPassword();
        previousPassword.setPreviousPassword(changePasswordRequestDto.oldPassword());

        return previousPassword;
    }

    public TUser dtoToTUser(UserRequestDto userRequestDto){

        if(userRequestDto == null){
            return null;
        }

        var userGroup = new TUserGroup();
        userGroup.setId(userRequestDto.userGroupId());

        var user = new TUser();
        user.setUsername(userRequestDto.username());
        user.setPassword(userRequestDto.password());
        user.setUserGroup(userGroup);

        return user;
    }
}
