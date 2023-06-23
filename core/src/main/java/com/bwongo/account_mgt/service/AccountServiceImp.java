package com.bwongo.account_mgt.service;

import com.bwongo.account_mgt.models.dto.response.AccountResponseDto;
import com.bwongo.account_mgt.models.enums.AccountStatus;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.account_mgt.repository.AccountRepository;
import com.bwongo.account_mgt.service.dto.AccountDtoService;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.utils.AuditService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.account_mgt.utils.AccountMsgConstant.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/13/23
 **/
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImp implements AccountService{

    private final AccountRepository accountRepository;
    private final AccountDtoService accountDtoService;
    private final AuditService auditService;

    @Override
    public boolean activateAccount(Long id) {

        var account = getAccount(id);
        Validate.isTrue(!account.isActive(), ExceptionType.BAD_REQUEST, ACCOUNT_ALREADY_ACTIVE);

        account.setActive(Boolean.TRUE);
        account.setAccountStatus(AccountStatus.ACTIVE);
        auditService.stampAuditedEntity(account);

        accountRepository.save(account);

        return Boolean.TRUE;
    }

    @Override
    public boolean deactivateAccount(Long id) {
        var account = getAccount(id);
        Validate.isTrue(account.isActive(), ExceptionType.BAD_REQUEST, ACCOUNT_ALREADY_INACTIVE);

        account.setActive(Boolean.FALSE);
        account.setAccountStatus(AccountStatus.NOT_ACTIVE);
        auditService.stampAuditedEntity(account);

        accountRepository.save(account);
        return Boolean.TRUE;
    }

    @Override
    public boolean suspendAccount(Long id) {
        var account = getAccount(id);
        Validate.isTrue(account.getAccountStatus().equals(AccountStatus.SUSPENDED), ExceptionType.BAD_REQUEST, ACCOUNT_ALREADY_SUSPENDED);

        account.setActive(Boolean.FALSE);
        account.setAccountStatus(AccountStatus.SUSPENDED);
        auditService.stampAuditedEntity(account);

        account.setSuspendedBy(account.getModifiedBy());
        account.setSuspendedOn(DateTimeUtil.getCurrentUTCTime());
        accountRepository.save(account);

        return Boolean.TRUE;
    }

    @Override
    public boolean closeAccount(Long id) {
        var account = getAccount(id);
        Validate.isTrue(account.getAccountStatus().equals(AccountStatus.CLOSED), ExceptionType.BAD_REQUEST, ACCOUNT_ALREADY_CLOSED);

        account.setActive(Boolean.FALSE);
        account.setAccountStatus(AccountStatus.CLOSED);
        auditService.stampAuditedEntity(account);

        account.setClosedBy(account.getModifiedBy());
        account.setClosedOn(DateTimeUtil.getCurrentUTCTime());

        return Boolean.TRUE;
    }

    @Override
    public AccountResponseDto getAccountById(Long id) {
        return accountDtoService.mapAccountToAccountResponseDto(getAccount(id));
    }

    @Override
    public List<AccountResponseDto> getAccountByActiveStatus(Pageable pageable, boolean isActive) {
        return accountRepository.findAllByActive(isActive, pageable).stream()
                .map(accountDtoService::mapAccountToAccountResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountResponseDto> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).stream()
                .map(accountDtoService::mapAccountToAccountResponseDto)
                .collect(Collectors.toList());
    }

    private Account getAccount(Long id){
        var existingAccount = accountRepository.findById(id);
        Validate.isPresent(existingAccount, ACCOUNT_NOT_FOUND, id);
        return existingAccount.get();
    }
}
