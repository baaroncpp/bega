package com.bwongo.tenant_mgt.api;

import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import com.bwongo.tenant_mgt.service.TenantService;
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
 * @Date 4/25/23
 **/
@RestController
@RequestMapping(path = "v1/api/tenant")
@RequiredArgsConstructor
public class TenantApi {

    private final TenantService tenantService;

    @RolesAllowed({"ROLE_ADMIN.WRITE"})
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public TenantResponseDto addTenant(@RequestBody TenantRequestDto tenantRequestDto){
        return tenantService.addTenant(tenantRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.UPDATE"})
    @PutMapping(path = "{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public TenantResponseDto updateTenant(@PathVariable("id") Long id,
                            @RequestBody TenantRequestDto tenantRequestDto){
        return tenantService.updateTenant(id, tenantRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public TenantResponseDto getTenantById(@PathVariable("id") Long id){
        return tenantService.getTenantById(id);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<TenantResponseDto> getTenants(@RequestParam("page") int page,
                                              @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return tenantService.getTenants(pageable);
    }

    @RolesAllowed({"ROLE_ADMIN.UPDATE"})
    @PatchMapping(path = "activate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean activateTenant(@PathVariable("id") Long id){
        return tenantService.activateTenant(id);
    }

    @RolesAllowed({"ROLE_ADMIN.UPDATE"})
    @PatchMapping(path = "deactivate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean deactivateTenant(@PathVariable("id") Long id){
        return tenantService.deactivateTenant(id);
    }

}
