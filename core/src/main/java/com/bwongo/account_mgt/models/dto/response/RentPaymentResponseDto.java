package com.bwongo.account_mgt.models.dto.response;

import com.bwongo.apartment_mgt.models.dto.response.HouseResponseDto;
import com.bwongo.apartment_mgt.models.enums.PaymentStatus;
import com.bwongo.apartment_mgt.models.enums.PaymentType;
import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserResponseDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/22/23
 **/
public record RentPaymentResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto  createdBy,
        BigDecimal amount,
        TenantResponseDto tenant,
        PaymentType paymentType,
        HouseResponseDto house,
        PaymentStatus paymentStatus,
        String note
) {
}
