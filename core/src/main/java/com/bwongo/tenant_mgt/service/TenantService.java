package com.bwongo.tenant_mgt.service;

import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/22/23
 **/
public interface TenantService {
    TenantResponseDto addTenant(TenantRequestDto tenantRequestDto);
    TenantResponseDto updateTenant(Long id, TenantRequestDto tenantRequestDto);
    TenantResponseDto getTenantById(Long id);
    List<TenantResponseDto> getTenants(Pageable pageable);
    Boolean deactivateTenant(Long id);
    Boolean activateTenant(Long id);
}
