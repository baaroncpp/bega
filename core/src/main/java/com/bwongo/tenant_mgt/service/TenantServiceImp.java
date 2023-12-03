package com.bwongo.tenant_mgt.service;

import com.bwongo.account_mgt.models.enums.AccountStatus;
import com.bwongo.account_mgt.models.enums.AccountType;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.account_mgt.repository.AccountRepository;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import com.bwongo.tenant_mgt.models.enums.TenantStatus;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.tenant_mgt.repository.TenantRepository;
import com.bwongo.tenant_mgt.service.dto.TenantDtoService;
import com.bwongo.tenant_mgt.utils.AuditService;
import com.bwongo.user_mgt.repository.TCountryRepository;
import com.bwongo.user_mgt.repository.TUserMetaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.account_mgt.utils.AccountMsgConstant.*;
import static com.bwongo.account_mgt.utils.AccountUtils.generateAccountNumber;
import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.*;
import static com.bwongo.tenant_mgt.utils.TenantUtils.checkIfTenantIsActive;
import static com.bwongo.user_mgt.util.UserMsgConstants.*;

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
    @Transactional
    public TenantResponseDto addTenant(TenantRequestDto tenantRequestDto) {

        tenantRequestDto.validate();

        final var email = tenantRequestDto.email();
        final var idNumber = tenantRequestDto.identificationNumber();
        final var countryId = tenantRequestDto.countryId();
        final var phoneNumber = tenantRequestDto.phoneNumber();
        final var phoneNumber2 = tenantRequestDto.phoneNumber2();

        Validate.isTrue(!userMetaRepository.existsByEmail(email), ExceptionType.BAD_REQUEST, EMAIL_IS_TAKEN, email);
        Validate.isTrue(!userMetaRepository.existsByIdentificationNumber(idNumber),  ExceptionType.BAD_REQUEST, TENANT_WITH_ID_EXISTS, idNumber);
        Validate.isTrue(countryRepository.existsById(countryId), ExceptionType.BAD_REQUEST, COUNTRY_NOT_FOUND, countryId);
        Validate.isTrue(!userMetaRepository.existsByPhoneNumber(phoneNumber), ExceptionType.BAD_REQUEST, PHONE_NUMBER_TAKEN, phoneNumber);
        Validate.isTrue(!userMetaRepository.existsByPhoneNumber(phoneNumber2), ExceptionType.BAD_REQUEST, PHONE_NUMBER_TAKEN, phoneNumber2);
        Validate.isTrue(!userMetaRepository.existsByPhoneNumber2(phoneNumber), ExceptionType.BAD_REQUEST, PHONE_NUMBER_TAKEN, phoneNumber);
        Validate.isTrue(!userMetaRepository.existsByPhoneNumber2(phoneNumber2), ExceptionType.BAD_REQUEST, PHONE_NUMBER_TAKEN, phoneNumber2);

        var tenant = tenantDtoService.mapTenantRequestDtoToTenant(tenantRequestDto);
        tenant.setActive(Boolean.FALSE);
        tenant.setTenantStatus(TenantStatus.BLOCKED);

        var userMeta = tenant.getUserMeta();
        userMeta.setNonVerifiedPhoneNumber(Boolean.TRUE);
        userMeta.setNonVerifiedEmail(Boolean.TRUE);
        auditService.stampAuditedEntity(userMeta);
        var existingUserMeta = userMetaRepository.save(userMeta);

        tenant.setUserMeta(existingUserMeta);
        auditService.stampAuditedEntity(tenant);

        return tenantDtoService.mapTenantToTenantResponseDto(tenantRepository.save(tenant));
    }

    @Override
    @Transactional
    public TenantResponseDto updateTenant(Long id, TenantRequestDto tenantRequestDto) {

        tenantRequestDto.validate();
        var existingTenant = getById(id);
        checkIfTenantIsActive(existingTenant);

        var updateTenant = tenantDtoService.mapTenantRequestDtoToTenant(tenantRequestDto);
        updateTenant.setId(existingTenant.getId());

        var updateUserMeta = updateTenant.getUserMeta();
        updateUserMeta.setId(existingTenant.getUserMeta().getId());
        updateUserMeta.setNonVerifiedPhoneNumber(Boolean.TRUE);
        updateUserMeta.setNonVerifiedEmail(Boolean.TRUE);

        auditService.stampAuditedEntity(updateUserMeta);
        var existingUpdatedUserMeta = userMetaRepository.save(updateUserMeta);

        updateTenant.setUserMeta(existingUpdatedUserMeta);
        updateTenant.setActive(existingTenant.isActive());
        auditService.stampAuditedEntity(updateTenant);

        return tenantDtoService.mapTenantToTenantResponseDto(tenantRepository.save(updateTenant));
    }

    @Override
    public TenantResponseDto getTenantById(Long id) {
        var tenant = getById(id);
        checkIfTenantIsActive(tenant);
        return tenantDtoService.mapTenantToTenantResponseDto(tenant);
    }

    @Override
    public List<TenantResponseDto> getTenants(Pageable pageable) {
        return tenantRepository.findAllByActive(Boolean.TRUE, pageable).stream()
                .map(tenantDtoService::mapTenantToTenantResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Boolean deactivateTenant(Long id) {

        final var tenant = getById(id);
        Validate.isTrue(tenant.isActive(), ExceptionType.BAD_REQUEST, TENANT_ALREADY_INACTIVE);
        tenant.setActive(Boolean.FALSE);
        tenant.setTenantStatus(TenantStatus.SUSPENDED);
        auditService.stampAuditedEntity(tenant);

        var existingTenantAccount = accountRepository.findByUserMeta(tenant.getUserMeta());
        Validate.isPresent(existingTenantAccount, TENANT_ACCOUNT_NOT_FOUND, id);
        var tenantAccount = existingTenantAccount.get();

        tenantAccount.setActive(Boolean.FALSE);
        tenantAccount.setSuspendedOn(DateTimeUtil.getCurrentUTCTime());
        tenantAccount.setSuspendedBy(tenant.getModifiedBy());
        tenantAccount.setAccountStatus(AccountStatus.SUSPENDED);
        auditService.stampAuditedEntity(tenantAccount);
        accountRepository.save(tenantAccount);

        tenant.setActive(Boolean.FALSE);
        auditService.stampAuditedEntity(tenant);
        tenantRepository.save(tenant);

        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean activateTenant(Long id) {

        final var tenant = getById(id);
        Validate.isTrue(!tenant.isActive(), ExceptionType.BAD_REQUEST, TENANT_ALREADY_ACTIVE);

        tenant.setActive(Boolean.TRUE);
        tenant.setTenantStatus(TenantStatus.ACTIVE);
        auditService.stampAuditedEntity(tenant);

        var tenantAccount = new Account();
        tenantAccount.setAvailableBalance(BigDecimal.ZERO);
        tenantAccount.setActivatedOn(DateTimeUtil.getCurrentUTCTime());
        tenantAccount.setActivatedBy(tenant.getModifiedBy());
        tenantAccount.setActive(Boolean.TRUE);
        tenantAccount.setAccountType(AccountType.BUSINESS);
        tenantAccount.setUserMeta(tenant.getUserMeta());
        tenantAccount.setAccountStatus(AccountStatus.ACTIVE);
        tenantAccount.setAccountNumber(generateAccountNumber());

        auditService.stampAuditedEntity(tenantAccount);
        accountRepository.save(tenantAccount);

        tenantRepository.save(tenant);

        return Boolean.TRUE;
    }

    @Override
    public Boolean evictTenant(Long id) {
        final var tenant = getById(id);

        tenant.setActive(Boolean.FALSE);
        tenant.setTenantStatus(TenantStatus.EVICTED);
        auditService.stampAuditedEntity(tenant);

        tenantRepository.save(tenant);

        //TODO record eviction

        return Boolean.TRUE;
    }

    private Tenant getById(Long id) {
        var existingTenant = tenantRepository.findById(id);
        Validate.isPresent(existingTenant, TENANT_NOT_FOUND, id);

        return existingTenant.get();
    }
}
