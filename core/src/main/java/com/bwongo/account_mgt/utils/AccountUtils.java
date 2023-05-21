package com.bwongo.account_mgt.utils;

import com.bwongo.account_mgt.models.enums.AccountStatus;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringUtil;
import com.bwongo.commons.models.utils.Validate;
import static com.bwongo.account_mgt.utils.accountMsgConstant.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
public class AccountUtils {

    private static final String REF_PREFIX = "bega";

    private AccountUtils() { }

    public static void checkIfAccountCanTransact(Account account){
        var accountId = account.getId();
        Validate.isTrue(!account.getAccountStatus().equals(AccountStatus.SUSPENDED), ExceptionType.BAD_REQUEST, ACCOUNT_SUSPENDED, accountId);
        Validate.isTrue(!account.getAccountStatus().equals(AccountStatus.CLOSED), ExceptionType.BAD_REQUEST, ACCOUNT_CLOSED, accountId);
        Validate.isTrue(!account.getAccountStatus().equals(AccountStatus.NOT_ACTIVE), ExceptionType.BAD_REQUEST, ACCOUNT_NOT_ACTIVE, accountId);
    }

    public static String getTransactionReference(){
        StringBuffer result = new StringBuffer();
        result.append(REF_PREFIX);
        result.append(StringUtil.subStr(StringUtil.randomString(), 10));
        return result.toString();
    }
}
