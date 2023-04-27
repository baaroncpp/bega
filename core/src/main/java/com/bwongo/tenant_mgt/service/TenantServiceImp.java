package com.bwongo.tenant_mgt.service;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
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

import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.*;

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
        Validate.isTrue(existingTenant.isEmpty(), ExceptionType.RESOURCE_NOT_FOUND, TENANT_WITH_ID_EXISTS, tenantRequestDto.identificationNumber());

        var tenant = dtoMapperService.mapTenantRequestDtoToTenant(tenantRequestDto);
        tenant.setActive(Boolean.TRUE);
        auditService.stampAuditedEntity(tenant);

        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant updateTenant(Long id, TenantRequestDto tenantRequestDto) {

        tenantRequestDto.validate();
        var existingTenant = getTenantById(id);
        Validate.isTrue(existingTenant.isActive(), ExceptionType.BAD_REQUEST, TENANT_IS_INACTIVE);

        var updateTenant = dtoMapperService.mapTenantRequestDtoToTenant(tenantRequestDto);
        updateTenant.setId(existingTenant.getId());
        updateTenant.setCreatedBy(existingTenant.getCreatedBy());
        updateTenant.setCreatedOn(existingTenant.getCreatedOn());

        auditService.stampAuditedEntity(updateTenant);

        return tenantRepository.save(updateTenant);
    }

    @Override
    public Tenant getTenantById(Long id) {

        var existingTenant = tenantRepository.findById(id);
        Validate.isPresent(existingTenant, TENANT_NOT_FOUND, id);
        return existingTenant.get();
    }

    @Override
    public Page<Tenant> getTenants(Pageable pageable) {
        return tenantRepository.findAllByActive(Boolean.TRUE, pageable);
    }

    @Override
    public Boolean deactivateTenant(Long id) {

        final var tenant = getTenantById(id);
        Validate.isTrue(tenant.isActive(), ExceptionType.BAD_REQUEST, TENANT_ALREADY_INACTIVE);

        tenant.setActive(Boolean.FALSE);
        auditService.stampAuditedEntity(tenant);

        tenantRepository.save(tenant);

        return Boolean.TRUE;
    }

    @Override
    public Boolean activateTenant(Long id) {

        final var tenant = getTenantById(id);
        Validate.isTrue(!tenant.isActive(), ExceptionType.BAD_REQUEST, TENANT_ALREADY_ACTIVE);

        tenant.setActive(Boolean.TRUE);
        auditService.stampAuditedEntity(tenant);

        tenantRepository.save(tenant);

        return Boolean.TRUE;
    }
}
