package com.bwongo.tenant_mgt.api;

import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.tenant_mgt.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @PostMapping(/*consumes = APPLICATION_JSON, produces = APPLICATION_JSON*/)
    public Tenant addTenant(@RequestBody TenantRequestDto tenantRequestDto){
        return tenantService.addTenant(tenantRequestDto);
    }
}
