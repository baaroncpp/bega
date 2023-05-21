package com.bwongo.account_mgt.utils;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
public class accountMsgConstant {

    private accountMsgConstant() {}

    public static final String ACCOUNT_NOT_FOUND = "account with ID: %s not found";
    public static final String TENANT_ACCOUNT_NOT_FOUND = "account for tenant with ID: %s not found";
    public static final String LOW_DEPOSIT_AMOUNT = "depositAmount is less than expected";
    public static final String LANDLORD_ACCOUNT_NOT_FOUND = "account for landlord with ID: %s not found";
    public static final String SYSTEM_COMMISSION_ACCOUNT_NOT_FOUND = "system commission account not found";
    public static final String SYSTEM_MAIN_ACCOUNT_NOT_FOUND = "system main account not found";
    public static final String ACCOUNT_SUSPENDED = "account with ID: %s is suspended";
    public static final String ACCOUNT_CLOSED = "account with ID: %s is closed";
    public static final String ACCOUNT_NOT_ACTIVE = "account with ID: %s is not active";
}
