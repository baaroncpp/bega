package com.bwongo.apartment_mgt.models.dto.response;

import com.bwongo.apartment_mgt.models.enums.ApartmentType;
import com.bwongo.apartment_mgt.models.enums.ManagementFeeType;
import com.bwongo.landlord_mgt.models.dto.response.LandlordResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserResponseDto;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
public record ApartmentResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto  createdBy,
        String apartmentName,
        ApartmentType apartmentType,
        String location,
        LandlordResponseDto apartmentOwner,
        String apartmentDescription,
        ManagementFeeType managementFeeType,
        boolean isRenovationServiced
) {
}
