package com.bwongo.tenant_mgt.utils;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/22/23
 **/
public class TenantMsgConstants {
    private TenantMsgConstants() { }

    public static final String NULL_TENANT_NAME = "tenantFullName is null or empty";
    public static final String NULL_IDENTIFICATION_NUMBER = "identificationNumber is null or empty";
    public static final String NULL_IDENTIFICATION_TYPE = "identificationType is null or empty";
    public static final String INVALID_IDENTIFICATION_TYPE = "Invalid identificationType";
    public static final String NULL_EMAIL = "email is null or empty";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String NULL_OCCUPATION_STATUS = "occupationStatus is null or empty";
    public static final String NULL_EMERGENCY_CONTACT_NAME = "emergencyContactName is null or empty";
    public static final String NULL_PHONE_NUMBER = "phoneNumber is null or empty";
    public static final String INVALID_PHONE_NUMBER = "%s is invalid phoneNumber";
    public static final String NULL_EMERGENCY_CONTACT_PHONE = "emergencyContactPhone is null or empty";
    public static final String TENANT_WITH_ID = "Tenant with %s already exists";

}
