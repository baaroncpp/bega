package com.bwongo.tenant_mgt.service;

import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/22/23
 **/
public interface TenantService {
    Tenant addTenant(TenantRequestDto tenantRequestDto);
    Tenant updateTenant(Long id, TenantRequestDto tenantRequestDto);
    Tenant getTenantById(Long id);
    Page<Tenant> getTenants(Pageable pageable);
    Boolean deactivateTenant(Long id);
    Boolean activateTenant(Long id);
}
