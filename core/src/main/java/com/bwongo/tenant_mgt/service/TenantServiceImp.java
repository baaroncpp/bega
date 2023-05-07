package com.bwongo.tenant_mgt.service;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.tenant_mgt.repository.TenantRepository;
import com.bwongo.tenant_mgt.service.dto.TenantDtoService;
import com.bwongo.tenant_mgt.utils.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.base.utils.BaseMsgConstants.EMAIL_IS_TAKEN;
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
    private final TenantDtoService tenantDtoService;

    @Override
    public TenantResponseDto addTenant(TenantRequestDto tenantRequestDto) {

        tenantRequestDto.validate();

        final var email = tenantRequestDto.email();
        final var idNumber = tenantRequestDto.identificationNumber();

        Validate.isTrue(!tenantRepository.existsByEmail(email), ExceptionType.BAD_REQUEST, EMAIL_IS_TAKEN, email);
        Validate.isTrue(!tenantRepository.existsByIdentificationNumber(idNumber),  ExceptionType.BAD_REQUEST, TENANT_WITH_ID_EXISTS, idNumber);

        var tenant = tenantDtoService.mapTenantRequestDtoToTenant(tenantRequestDto);
        tenant.setActive(Boolean.TRUE);
        auditService.stampAuditedEntity(tenant);

        return tenantDtoService.mapTenantToTenantResponseDto(tenantRepository.save(tenant));
    }

    @Override
    public TenantResponseDto updateTenant(Long id, TenantRequestDto tenantRequestDto) {

        tenantRequestDto.validate();
        var existingTenant = getById(id);
        Validate.isTrue(existingTenant.isActive(), ExceptionType.BAD_REQUEST, TENANT_IS_INACTIVE);

        var updateTenant = tenantDtoService.mapTenantRequestDtoToTenant(tenantRequestDto);
        updateTenant.setId(existingTenant.getId());
        updateTenant.setCreatedBy(existingTenant.getCreatedBy());
        updateTenant.setCreatedOn(existingTenant.getCreatedOn());

        auditService.stampAuditedEntity(updateTenant);

        return tenantDtoService.mapTenantToTenantResponseDto(tenantRepository.save(updateTenant));
    }

    @Override
    public TenantResponseDto getTenantById(Long id) {
        return tenantDtoService.mapTenantToTenantResponseDto(getById(id));
    }

    @Override
    public List<TenantResponseDto> getTenants(Pageable pageable) {
        return tenantRepository.findAllByActive(Boolean.TRUE, pageable).stream()
                .map(tenantDtoService::mapTenantToTenantResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deactivateTenant(Long id) {

        final var tenant = getById(id);
        Validate.isTrue(tenant.isActive(), ExceptionType.BAD_REQUEST, TENANT_ALREADY_INACTIVE);

        tenant.setActive(Boolean.FALSE);
        auditService.stampAuditedEntity(tenant);
        tenantRepository.save(tenant);

        return Boolean.TRUE;
    }

    @Override
    public Boolean activateTenant(Long id) {

        final var tenant = getById(id);
        Validate.isTrue(!tenant.isActive(), ExceptionType.BAD_REQUEST, TENANT_ALREADY_ACTIVE);

        tenant.setActive(Boolean.TRUE);
        auditService.stampAuditedEntity(tenant);
        tenantRepository.save(tenant);

        return Boolean.TRUE;
    }

    private Tenant getById(Long id) {
        var existingTenant = tenantRepository.findById(id);
        Validate.isPresent(existingTenant, TENANT_NOT_FOUND, id);
        return existingTenant.get();
    }
}
