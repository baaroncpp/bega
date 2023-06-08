package com.bwongo.tenant_mgt.service;

import com.bwongo.account_mgt.models.enums.AccountType;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.account_mgt.repository.AccountRepository;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.tenant_mgt.repository.TenantRepository;
import com.bwongo.tenant_mgt.service.dto.TenantDtoService;
import com.bwongo.tenant_mgt.utils.AuditService;
import com.bwongo.user_mgt.repository.TCountryRepository;
import com.bwongo.user_mgt.repository.TUserMetaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.account_mgt.utils.accountMsgConstant.TENANT_ACCOUNT_NOT_FOUND;
import static com.bwongo.base.utils.BaseMsgConstants.EMAIL_IS_TAKEN;
import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.*;
import static com.bwongo.user_mgt.util.UserMsgConstants.COUNTRY_NOT_FOUND;

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
    private final TUserMetaRepository userMetaRepository;
    private final TCountryRepository countryRepository;
    private final AccountRepository accountRepository;

    @Override
    public TenantResponseDto addTenant(TenantRequestDto tenantRequestDto) {

        tenantRequestDto.validate();

        final var email = tenantRequestDto.email();
        final var idNumber = tenantRequestDto.identificationNumber();
        final var countryId = tenantRequestDto.countryId();

        Validate.isTrue(userMetaRepository.existsByEmail(email), ExceptionType.BAD_REQUEST, EMAIL_IS_TAKEN, email);
        Validate.isTrue(userMetaRepository.existsByIdentificationNumber(idNumber),  ExceptionType.BAD_REQUEST, TENANT_WITH_ID_EXISTS, idNumber);
        Validate.isTrue(!countryRepository.existsById(countryId), ExceptionType.BAD_REQUEST, COUNTRY_NOT_FOUND, countryId);

        var tenant = tenantDtoService.mapTenantRequestDtoToTenant(tenantRequestDto);
        tenant.setActive(Boolean.TRUE);

        var userMeta = tenant.getUserMeta();
        auditService.stampAuditedEntity(userMeta);
        var existingUserMeta = userMetaRepository.save(userMeta);

        tenant.setUserMeta(existingUserMeta);
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

        var updateUserMeta = updateTenant.getUserMeta();
        updateUserMeta.setId(existingTenant.getUserMeta().getId());
        auditService.stampAuditedEntity(updateUserMeta);
        var existingUpdatedUserMeta = userMetaRepository.save(updateUserMeta);

        updateTenant.setUserMeta(existingUpdatedUserMeta);
        updateTenant.setActive(existingTenant.isActive());
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

        var existingTenantAccount = accountRepository.findByUserMeta(tenant.getUserMeta());
        Validate.isPresent(existingTenantAccount, TENANT_ACCOUNT_NOT_FOUND, id);
        var tenantAccount = existingTenantAccount.get();

        tenantAccount.setActive(Boolean.FALSE);
        tenantAccount.setSuspendedOn(DateTimeUtil.getCurrentUTCTime());
        tenantAccount.setSuspendedBy(tenant.getModifiedBy());
        auditService.stampAuditedEntity(tenantAccount);
        accountRepository.save(tenantAccount);

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

        var tenantAccount = new Account();
        tenantAccount.setAvailableBalance(BigDecimal.ZERO);
        tenantAccount.setActivatedOn(DateTimeUtil.getCurrentUTCTime());
        tenantAccount.setActivatedBy(tenant.getModifiedBy());
        tenantAccount.setActive(Boolean.TRUE);
        tenantAccount.setAccountType(AccountType.BUSINESS);
        tenantAccount.setUserMeta(tenant.getUserMeta());

        auditService.stampAuditedEntity(tenantAccount);
        accountRepository.save(tenantAccount);

        tenantRepository.save(tenant);

        return Boolean.TRUE;
    }

    private Tenant getById(Long id) {
        var existingTenant = tenantRepository.findById(id);
        Validate.isPresent(existingTenant, TENANT_NOT_FOUND, id);
        return existingTenant.get();
    }
}
