package com.bwongo.apartment_mgt.models.dto;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.apartment_mgt.utils.ApartmentEnumUtil;

import java.math.BigDecimal;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
public record ApartmentRequestDto(
        String apartmentName,
        String apartmentType,
        String location,
        Long landlordId,
        String apartmentDescription,
        BigDecimal managementFee,
        String managementFeeType
) {
    public void validate(){
        Validate.notEmpty(apartmentName, NULL_APARTMENT_NAME);
        Validate.notEmpty(apartmentType, NULL_APARTMENT_TYPE);
        Validate.isTrue(ApartmentEnumUtil.isApartmentType(apartmentType), ExceptionType.BAD_REQUEST, INVALID_APARTMENT_TYPE, apartmentType);
        Validate.notEmpty(location, NULL_LOCATION);
        Validate.notNull(landlordId, ExceptionType.BAD_REQUEST, NULL_LANDLORD_ID);
        Validate.notNull(managementFee, ExceptionType.BAD_REQUEST, NULL_MANAGEMENT_FEE);
        Validate.isTrue(managementFee.compareTo(BigDecimal.ZERO) > 0, ExceptionType.BAD_REQUEST, MANAGEMENT_FEE_NOT_ZERO);
        Validate.notEmpty(managementFeeType, MANAGEMENT_FEE_TYPE);
        Validate.isTrue(ApartmentEnumUtil.isManagementFeeType(managementFeeType), ExceptionType.BAD_REQUEST, INVALID_MANAGEMENT_FEE_TYPE);
    }
}
