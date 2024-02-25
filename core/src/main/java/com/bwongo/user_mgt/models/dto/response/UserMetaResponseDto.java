package com.bwongo.user_mgt.models.dto.response;

import com.bwongo.base.models.dto.response.CountryResponseDto;
import com.bwongo.base.models.enums.IdentificationType;
import com.bwongo.base.models.enums.GenderEnum;
import com.bwongo.base.models.jpa.TCountry;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/7/23
 **/
public record UserMetaResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto createdBy,
        boolean isActive,
        String firstName,
        String lastName,
        String middleName,
        String phoneNumber,
        String phoneNumber2,
        String imagePath,
        String displayName,
        GenderEnum gender,
        Date birthDate,
        String email,
        CountryResponseDto country,
        IdentificationType identificationType,
        String identificationNumber,
        String identificationPath,
        Boolean nonVerifiedEmail,
        Boolean nonVerifiedPhoneNumber
) {
}
