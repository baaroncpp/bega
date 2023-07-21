package com.bwongo.account_mgt.api;

import com.bwongo.account_mgt.models.dto.response.AccountResponseDto;
import com.bwongo.account_mgt.service.AccountService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/13/23
 **/
@RestController
@RequestMapping(path = "v1/api")
@RequiredArgsConstructor
public class AccountApi {

    private final AccountService accountService;

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "accounts", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseDto> getAllAccounts(@RequestParam("page") int page,
                                                   @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return accountService.getAllAccounts(pageable);
    }
}
