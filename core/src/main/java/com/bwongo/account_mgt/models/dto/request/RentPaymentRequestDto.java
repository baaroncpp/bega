package com.bwongo.account_mgt.models.dto.request;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import java.math.BigDecimal;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;
import static com.bwongo.account_mgt.utils.accountMsgConstant.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/22/23
 **/
public record RentPaymentRequestDto(
        BigDecimal amount,
        Long tenantId,
        Long  houseId,
        String note
) {
    public void validate(){
        Validate.notNull(amount, ExceptionType.BAD_REQUEST, NULL_AMOUNT);
        Validate.isTrue(amount.compareTo(BigDecimal.ZERO) > 0, ExceptionType.BAD_REQUEST, AMOUNT_IS_ZERO);
        Validate.notNull(tenantId, ExceptionType.BAD_REQUEST, NULL_TENANT_ID);
        Validate.notNull(houseId, ExceptionType.BAD_REQUEST, NULL_HOUSE_ID);
    }
}
