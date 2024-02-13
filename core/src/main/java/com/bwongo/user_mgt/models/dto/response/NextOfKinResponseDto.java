package com.bwongo.user_mgt.models.dto.response;

import com.bwongo.base.models.enums.IdentificationType;
import com.bwongo.user_mgt.models.enums.RelationShipType;

import java.util.Date;

public record NextOfKinResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto createdBy,
        boolean isActive,
        String firstName,
        String secondName,
        Date dateOfBirth,
        String firstPhoneNumber,
        String secondPhoneNumber,
        RelationShipType relationShipType,
        String relationShipNote,
        String email,
        IdentificationType identificationType,
        String idNumber,
        String idPhotoUrlPath
) {
}
