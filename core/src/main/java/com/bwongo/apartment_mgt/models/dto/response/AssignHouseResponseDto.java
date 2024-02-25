package com.bwongo.apartment_mgt.models.dto.response;

import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import com.bwongo.base.models.enums.BillingDuration;
import com.bwongo.user_mgt.models.dto.response.UserResponseDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/13/23
 **/
public record AssignHouseResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto  createdBy,
        HouseResponseDto house,
        BigDecimal predefinedRent,
        BillingDuration billingDuration,
        TenantResponseDto tenant,
        BigDecimal depositAmount,
        BigDecimal rentAmountPaid,
        Date placementDate
) {
}
