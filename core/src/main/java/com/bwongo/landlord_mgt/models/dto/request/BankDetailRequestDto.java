package com.bwongo.landlord_mgt.models.dto.request;

import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.*;

public record BankDetailRequestDto(
        String bankName,
        String accountName,
        String accountNumber
) {
    public void validate(){
        Validate.notEmpty(bankName, NULL_BANK_NAME);
        Validate.notEmpty(accountName, NULL_ACCOUNT_NAME);
        Validate.notEmpty(accountNumber, NULL_ACCOUNT_NUMBER);
    }
}
