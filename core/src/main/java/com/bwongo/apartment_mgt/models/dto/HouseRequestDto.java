package com.bwongo.apartment_mgt.models.dto;

import com.bwongo.apartment_mgt.utils.ApartmentEnumUtil;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;

import java.math.BigDecimal;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
public record HouseRequestDto (
        String houseNumber,
        Long houseTypeId,
        Long apartmentId,
        BigDecimal rentFee,
        String rentPeriod,
        String note
){
    public void validate(){
        Validate.notEmpty(houseNumber, NULL_HOUSE_NUMBER);
        Validate.notNull(houseTypeId, ExceptionType.BAD_REQUEST, NULL_HOUSE_TYPE_ID);
        Validate.notNull(apartmentId, ExceptionType.BAD_REQUEST, NULL_APARTMENT_ID);
        Validate.notNull(rentFee, ExceptionType.BAD_REQUEST, NULL_RENT_FEE);
        Validate.isTrue(rentFee.compareTo(BigDecimal.ZERO) > 0, ExceptionType.BAD_REQUEST, RENT_FEE_NOT_ZERO);
        Validate.notEmpty(rentPeriod, NULL_RENT_PERIOD);
        Validate.isTrue(ApartmentEnumUtil.isRentPeriod(rentPeriod), ExceptionType.BAD_REQUEST, INVALID_RENT_PERIOD);
    }
}
