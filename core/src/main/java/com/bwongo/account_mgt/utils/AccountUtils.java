package com.bwongo.account_mgt.models.utils;

import com.bwongo.account_mgt.models.enums.AccountStatus;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
public class AccountUtils {

    private AccountUtils() { }

    public static void checkIfAccountCanTransact(Account account){
        var accountId = account.getId();
        Validate.isTrue(account.getAccountStatus().equals(AccountStatus.SUSPENDED), ExceptionType.BAD_REQUEST, ACCOUNT_SUSPENDED, accountId);
        Validate.isTrue(account.getAccountStatus().equals(AccountStatus.CLOSED), ExceptionType.BAD_REQUEST, ACCOUNT_CLOSED, accountId);
    }
}
