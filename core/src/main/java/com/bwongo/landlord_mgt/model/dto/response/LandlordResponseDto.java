package com.bwongo.landlord_mgt.model.dto.response;

import com.bwongo.user_mgt.models.dto.response.UserMetaResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserResponseDto;
import com.bwongo.user_mgt.models.jpa.TDistrict;

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
        String username,
        TDistrict district,
        String physicalAddress,
        UserMetaResponseDto userMeta
) {
}
