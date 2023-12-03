package com.bwongo.tenant_mgt.utils;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.models.enums.TenantStatus;
import com.bwongo.tenant_mgt.models.jpa.Tenant;

import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 9/15/23
 **/
public class TenantUtils {

    private TenantUtils() { }

    public static void checkIfTenantIsActive(Tenant tenant){
        Validate.isTrue(!tenant.getTenantStatus().equals(TenantStatus.EVICTED), ExceptionType.BAD_REQUEST, TENANT_IS_EVICTED);
        Validate.isTrue(!tenant.getTenantStatus().equals(TenantStatus.SUSPENDED), ExceptionType.BAD_REQUEST, TENANT_IS_SUSPENDED);
        Validate.isTrue(!tenant.getTenantStatus().equals(TenantStatus.BLOCKED), ExceptionType.BAD_REQUEST, TENANT_IS_BLOCKED);
        Validate.isTrue(tenant.isActive(), ExceptionType.BAD_REQUEST, TENANT_IS_INACTIVE);
    }
}
