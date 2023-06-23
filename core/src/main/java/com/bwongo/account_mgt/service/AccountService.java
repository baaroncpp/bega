package com.bwongo.account_mgt.service;

import com.bwongo.account_mgt.models.dto.response.AccountResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/13/23
 **/
public interface AccountService {
    public boolean activateAccount(Long id);
    public boolean deactivateAccount(Long id);
    public boolean suspendAccount(Long id);
    public boolean closeAccount(Long id);
    public AccountResponseDto getAccountById(Long id);
    public List<AccountResponseDto> getAccountByActiveStatus(Pageable pageable, boolean isActive);
    public List<AccountResponseDto> getAllAccounts(Pageable pageable);
}
