package com.bwongo.account_mgt.utils;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
public class AccountMsgConstant {

    private AccountMsgConstant() {}

    public static final String ACCOUNT_NOT_FOUND = "account with ID: %s not found";
    public static final String TENANT_ACCOUNT_NOT_FOUND = "account for tenant with ID: %s not found";
    public static final String LOW_DEPOSIT_AMOUNT = "depositAmount is less than expected";
    public static final String LANDLORD_ACCOUNT_NOT_FOUND = "account for landlord with ID: %s not found";
    public static final String SYSTEM_COMMISSION_ACCOUNT_NOT_FOUND = "system commission account not found";
    public static final String SYSTEM_MAIN_ACCOUNT_NOT_FOUND = "system main account not found";
    public static final String ACCOUNT_SUSPENDED = "account with ID: %s is suspended";
    public static final String ACCOUNT_CLOSED = "account with ID: %s is closed";
    public static final String ACCOUNT_NOT_ACTIVE = "account with ID: %s is not active";
    public static final String NULL_AMOUNT = "amount is null";
    public static final String AMOUNT_IS_ZERO = "amount must be greater than 0";
    public static final String NULL_USER_META = "userMetaId is null";
    public static final String NULL_ACCOUNT_STATUS = "accountStatus is null or empty";
    public static final String INVALID_ACCOUNT_STATUS = "Invalid accountStatus";
    public static final String NULL_ACCOUNT_TYPE = "accountType is null or empty";
    public static final String INVALID_ACCOUNT_TYPE = "Invalid accountType";
    public static final String ACCOUNT_ALREADY_ACTIVE = "account is already active";
    public static final String ACCOUNT_ALREADY_INACTIVE = "account is already inactive";
    public static final String ACCOUNT_ALREADY_SUSPENDED = "account is already suspended";
    public static final String ACCOUNT_ALREADY_CLOSED = "account is already closed";
}
