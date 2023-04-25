package com.bwongo.tenant_mgt.service;

import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.dto.service.DtoMapperServiceImp;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.tenant_mgt.repository.TenantRepository;
import com.bwongo.tenant_mgt.utils.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.TENANT_WITH_ID;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/22/23
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class TenantServiceImp implements TenantService{

    private final TenantRepository tenantRepository;
    private final AuditService auditService;
    private final DtoMapperServiceImp dtoMapperService;

    @Override
    public Tenant addTenant(TenantRequestDto tenantRequestDto) {

        tenantRequestDto.validate();
        var existingTenant = tenantRepository.findByIdentificationNumber(tenantRequestDto.identificationNumber());
        Validate.isTrue(existingTenant.isEmpty(), TENANT_WITH_ID, tenantRequestDto.identificationNumber());

        var tenant = dtoMapperService.mapTenantRequestDtoToTenant(tenantRequestDto);
        auditService.stampAuditedEntity(tenant);

        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant updateTenant(TenantRequestDto tenantRequestDto) {
        return null;
    }

    @Override
    public Tenant getTenantById(Long id) {
        return null;
    }

    @Override
    public Page<Tenant> getTenants(Pageable pageable) {
        return null;
    }

    @Override
    public Boolean deleteTenant(Long id) {
        return null;
    }
}
