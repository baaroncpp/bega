package com.bwongo.tenant_mgt.api;

import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.tenant_mgt.service.TenantService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Tenant addTenant(@RequestBody TenantRequestDto tenantRequestDto){
        return tenantService.addTenant(tenantRequestDto);
    }

    @PutMapping(path = "{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public Tenant updateTenant(@PathVariable("id") Long id,
                            @RequestBody TenantRequestDto tenantRequestDto){
        return tenantService.updateTenant(id, tenantRequestDto);
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public Tenant getTenantById(@PathVariable("id") Long id){
        return tenantService.getTenantById(id);
    }

    @GetMapping(produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public Page<Tenant> getTenants(@RequestParam("page") int page,
                                      @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("createdOn").descending());
        return tenantService.getTenants(pageable);
    }

    @PatchMapping(path = "activate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean activateTenant(@PathVariable("id") Long id){
        return tenantService.activateTenant(id);
    }

    @PatchMapping(path = "deactivate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean deactivateTenant(@PathVariable("id") Long id){
        return tenantService.deactivateTenant(id);
    }

}
