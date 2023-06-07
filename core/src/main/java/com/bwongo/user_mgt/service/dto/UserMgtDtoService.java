package com.bwongo.user_mgt.service.dto;

import com.bwongo.base.model.enums.IdentificationType;
import com.bwongo.user_mgt.models.dto.UserGroupResponseDto;
import com.bwongo.user_mgt.models.dto.UserMetaRequestDto;
import com.bwongo.user_mgt.models.dto.UserMetaResponseDto;
import com.bwongo.user_mgt.models.dto.UserResponseDto;
import com.bwongo.user_mgt.models.enums.GenderEnum;
import com.bwongo.user_mgt.models.jpa.TCountry;
import com.bwongo.user_mgt.models.jpa.TUser;
import com.bwongo.user_mgt.models.jpa.TUserGroup;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/5/23
 **/
@Service
public class UserMgtDtoService {

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
                userMeta.getCountry(),
                userMeta.getIdentificationType(),
                userMeta.getIdentificationNumber(),
                userMeta.getIdentificationPath(),
                userMeta.isNonVerifiedEmail(),
                userMeta.isNonVerifiedPhoneNumber()
        );
    }
}
