package com.bwongo.tenant_mgt.utils;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/22/23
 **/
public class TenantMsgConstants {
    private TenantMsgConstants() { }

    public static final String APPLICATION_JSON = "application/json";
    public static final String NULL_TENANT_NAME = "tenantFullName is null or empty";
    public static final String FULL_TENANT_NAME_TOO_SHORT = "tenantFullName should have more than 6 characters";
    public static final String INVALID_FULL_TENANT_NAME = "tenantFullName should contain only characters";
    public static final String NULL_OCCUPATION_STATUS = "occupationStatus is null or empty";
    public static final String INVALID_OCCUPATION_STATUS = "Invalid occupationStatus";
    public static final String NULL_EMERGENCY_CONTACT_NAME = "emergencyContactName is null or empty";
    public static final String NULL_EMERGENCY_CONTACT_PHONE = "emergencyContactPhone is null or empty";
    public static final String TENANT_WITH_ID_EXISTS = "Tenant with ID: %s already exists";
    public static final String TENANT_NOT_FOUND = "Tenant with ID: %s not found";
    public static final String TENANT_ALREADY_INACTIVE = "Tenant is already inactive";
    public static final String TENANT_ALREADY_ACTIVE = "Tenant is already active";
    public static final String TENANT_IS_INACTIVE = "Tenant is inactive";
}
