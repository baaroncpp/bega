package com.bwongo.apartment_mgt.models.dto.response;

import com.bwongo.apartment_mgt.models.enums.ApartmentType;
import com.bwongo.apartment_mgt.models.enums.ManagementFeeType;
import com.bwongo.landlord_mgt.model.dto.response.LandlordResponseDto;
import com.bwongo.user_mgt.models.dto.UserResponseDto;

import java.math.BigDecimal;
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
        BigDecimal managementFee,
        ManagementFeeType managementFeeType,
        boolean isRenovationServiced
) {
}
