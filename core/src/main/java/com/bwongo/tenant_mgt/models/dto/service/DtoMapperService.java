package com.bwongo.tenant_mgt.models.dto.service;

import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.jpa.Tenant;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/25/23
 **/
public interface DtoMapperService {
    Tenant mapTenantRequestDtoToTenant(TenantRequestDto tenantRequestDto);
}
