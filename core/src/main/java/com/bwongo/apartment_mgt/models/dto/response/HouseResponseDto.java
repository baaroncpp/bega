package com.bwongo.apartment_mgt.models.dto.response;

import com.bwongo.apartment_mgt.models.enums.RentPeriod;
import com.bwongo.apartment_mgt.models.jpa.HouseType;
import com.bwongo.user_mgt.models.dto.UserResponseDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
public record HouseResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto  createdBy,
        String houseNumber,
        HouseType houseType,
        ApartmentResponseDto apartmentResponseDto,
        BigDecimal rentFee,
        RentPeriod rentPeriod,
        String note,
        Boolean isOccupied,
        Boolean isRenovationChargeBilled,
        int initialRentPaymentPeriod,
        Date occupiedUntil
) {
}
