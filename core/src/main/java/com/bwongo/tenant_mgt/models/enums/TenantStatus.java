package com.bwongo.tenant_mgt.models.enums;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/13/23
 **/
public enum TenantStatus {
    ACTIVE("Tenant is active and can use available services"),
    EVICTED("Tenant has been evicted due to specified reasons"),
    BLOCKED("Tenant is invalid, cannot be reactivated"),
    SUSPENDED("Tenant is inactive but under evaluation and can be reactivated");

    final String note;

    TenantStatus(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
