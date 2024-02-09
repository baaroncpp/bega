package com.bwongo.account_mgt.models.dto.request;

import com.bwongo.account_mgt.utils.AccountUtils;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import static com.bwongo.account_mgt.utils.AccountMsgConstant.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/13/23
 **/
public record AccountRequestDto(
        Long userMetaId,
        String accountStatus,
        String accountType
) {
    public void validate(){
        Validate.notNull(userMetaId, ExceptionType.BAD_REQUEST, NULL_USER_META);
        Validate.notEmpty(accountStatus, NULL_ACCOUNT_STATUS);
        Validate.isTrue(AccountUtils.isAccountStatus(accountStatus), ExceptionType.BAD_REQUEST, INVALID_ACCOUNT_STATUS);
        Validate.notEmpty(accountType, NULL_ACCOUNT_TYPE);
        Validate.isTrue(AccountUtils.isAccountType(accountType), ExceptionType.BAD_REQUEST, INVALID_ACCOUNT_TYPE);
    }
}
