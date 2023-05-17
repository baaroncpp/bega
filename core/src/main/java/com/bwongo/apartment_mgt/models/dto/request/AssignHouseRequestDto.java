package com.bwongo.apartment_mgt.models.dto.request;

import com.bwongo.apartment_mgt.utils.ApartmentUtil;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;

import java.math.BigDecimal;
import java.util.Date;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/13/23
 **/
public record AssignHouseRequestDto(
        Long houseId,
        BigDecimal predefinedRent,
        String billingDuration,
        Long tenantId,
        BigDecimal depositAmount,
        String placementDate
) {
    public void validate(){
        Validate.notNull(houseId, ExceptionType.BAD_REQUEST, NULL_HOUSE_ID);
        Validate.isTrue(predefinedRent.compareTo(BigDecimal.ZERO) > 0, ExceptionType.BAD_REQUEST, RENT_FEE_NOT_ZERO);
        Validate.isTrue(ApartmentUtil.isBillingDuration(billingDuration), ExceptionType.BAD_REQUEST, INVALID_BILLING_DURATION);
        Validate.notNull(tenantId, ExceptionType.BAD_REQUEST, NULL_TENANT_ID);
        Validate.isTrue(depositAmount.compareTo(BigDecimal.ZERO) > 0, ExceptionType.BAD_REQUEST, DEPOSIT_AMOUNT_NOT_ZERO);
        Validate.isTrue(ApartmentUtil.validateAssignHouseDate(placementDate), ExceptionType.BAD_REQUEST, INVALID_PLACEMENT_DATE, placementDate);
    }
}
