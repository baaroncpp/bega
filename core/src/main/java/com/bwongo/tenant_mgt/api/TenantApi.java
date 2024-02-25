package com.bwongo.tenant_mgt.api;

import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import com.bwongo.tenant_mgt.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.WRITE', 'TENANT_ROLE.WRITE')")
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public TenantResponseDto addTenant(@RequestBody TenantRequestDto tenantRequestDto){
        return tenantService.addTenant(tenantRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.UPDATE', 'TENANT_ROLE.UPDATE')")
    @PutMapping(path = "{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public TenantResponseDto updateTenant(@PathVariable("id") Long id,
                                          @RequestBody TenantRequestDto tenantRequestDto){
        return tenantService.updateTenant(id, tenantRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ', 'TENANT_ROLE.READ')")
    @GetMapping(path = "{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public TenantResponseDto getTenantById(@PathVariable("id") Long id){
        return tenantService.getTenantById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ', 'TENANT_ROLE.READ')")
    @GetMapping(produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<TenantResponseDto> getTenants(@RequestParam("page") int page,
                                              @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return tenantService.getTenants(pageable);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.UPDATE', 'TENANT_ROLE.UPDATE')")
    @PatchMapping(path = "activate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean activateTenant(@PathVariable("id") Long id){
        return tenantService.activateTenant(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.UPDATE', 'TENANT_ROLE.UPDATE')")
    @PatchMapping(path = "deactivate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean deactivateTenant(@PathVariable("id") Long id){
        return tenantService.deactivateTenant(id);
    }

    @PreAuthorize("hasAnyAuthority('TENANT_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "upload/id-photo")
    @ResponseStatus(HttpStatus.OK)
    public void uploadIdPhoto(@RequestParam(value = "file", required = true) MultipartFile file,
                              @RequestParam(value = "fileName", required = true) String fileName,
                              @RequestParam(value = "tenantId", required = true) Long tenantId){
        tenantService.uploadIdPhoto(file, fileName, tenantId);
    }

    @PreAuthorize("hasAnyAuthority('TENANT_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "upload/profile-photo")
    @ResponseStatus(HttpStatus.OK)
    public void uploadPhoto(@RequestParam(value = "file", required = true) MultipartFile file,
                            @RequestParam(value = "fileName", required = true) String fileName,
                            @RequestParam(value = "tenantId", required = true) Long tenantId){
        tenantService.uploadProfilePhoto(file, fileName, tenantId);
    }

    public void evictTenant(){

    }

    public void makeRenovationRequest(){

    }

    public void raiseComplaint(){

    }

}
