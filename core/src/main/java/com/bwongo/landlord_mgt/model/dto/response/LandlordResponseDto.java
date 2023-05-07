package com.bwongo.landlord_mgt.model.dto.response;

import com.bwongo.base.model.enums.IdentificationType;
import com.bwongo.user_mgt.models.dto.UserResponseDto;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
public record LandlordResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto  createdBy,
        boolean isActive,
        String firstName,
        String middleName,
        String lastName,
        IdentificationType identificationType,
        String identificationNumber,
        String phoneNumber,
        String secondPhoneNumber,
        String physicalAddress,
        String email
) {
}
