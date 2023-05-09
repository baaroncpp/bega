package com.bwongo.landlord_mgt.api;

import com.bwongo.landlord_mgt.model.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.model.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.service.LandlordService;
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
 * @Date 5/9/23
 **/
@RestController
@RequestMapping(path = "v1/api/landlord")
@RequiredArgsConstructor
public class LandlordApi {

    private final LandlordService landlordService;

    @RolesAllowed({"ROLE_ADMIN.WRITE"})
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public LandlordResponseDto addLandlord(@RequestBody LandlordRequestDto landlordRequestDto){
        return landlordService.addLandlord(landlordRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.UPDATE"})
    @PutMapping(path = "{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public LandlordResponseDto updateLandlord(@PathVariable("id") Long id,
                                           @RequestBody LandlordRequestDto landlordRequestDto){
        return landlordService.updateLandlord(id, landlordRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public LandlordResponseDto getLandlordById(@PathVariable("id") Long id){
        return landlordService.getLandlordById(id);
    }

    @RolesAllowed({"ROLE_ADMIN.UPDATE"})
    @PatchMapping(path = "activate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean activateLandlord(@PathVariable("id") Long id){
        return landlordService.activateLandlord(id);
    }

    @RolesAllowed({"ROLE_ADMIN.UPDATE"})
    @PatchMapping(path = "deactivate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean deactivateLandlord(@PathVariable("id") Long id){
        return landlordService.deactivateLandlord(id);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<LandlordResponseDto> getLandlords(@RequestParam("page") int page,
                                       @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return landlordService.getLandlords(pageable);
    }
}
