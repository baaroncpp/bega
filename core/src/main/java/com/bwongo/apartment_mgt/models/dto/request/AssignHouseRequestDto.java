package com.bwongo.apartment_mgt.models.dto.request;

import java.math.BigDecimal;
import java.util.Date;

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
        BigDecimal rentAmountPaid,
        Date placementDate
) {

}
