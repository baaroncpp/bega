package com.bwongo.landlord_mgt.api;

import com.bwongo.landlord_mgt.models.dto.request.BankDetailRequestDto;
import com.bwongo.landlord_mgt.models.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.models.dto.response.BankDetailsResponseDto;
import com.bwongo.landlord_mgt.models.dto.response.LandlordBankDetailsResponseDto;
import com.bwongo.landlord_mgt.models.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.service.LandlordService;
import com.bwongo.user_mgt.models.dto.request.NextOfKinRequestDto;
import com.bwongo.user_mgt.models.dto.response.NextOfKinResponseDto;
import jakarta.annotation.security.RolesAllowed;
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
 * @Date 5/9/23
 **/
@RestController
@RequestMapping(path = "v1/api/landlord")
@RequiredArgsConstructor
public class LandlordApi {

    private final LandlordService landlordService;

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public LandlordResponseDto addLandlord(@RequestBody LandlordRequestDto landlordRequestDto){
        return landlordService.addLandlord(landlordRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public LandlordResponseDto updateLandlord(@PathVariable("id") Long id,
                                           @RequestBody LandlordRequestDto landlordRequestDto){
        return landlordService.updateLandlord(id, landlordRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public LandlordResponseDto getLandlordById(@PathVariable("id") Long id){
        return landlordService.getLandlordById(id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PatchMapping(path = "activate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean activateLandlord(@PathVariable("id") Long id){
        return landlordService.activateLandlord(id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PatchMapping(path = "deactivate/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean deactivateLandlord(@PathVariable("id") Long id){
        return landlordService.deactivateLandlord(id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<LandlordResponseDto> getLandlords(@RequestParam("page") int page,
                                                  @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return landlordService.getLandlords(pageable);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "{id}/bank-detail", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public LandlordBankDetailsResponseDto addBankDetails(@RequestBody BankDetailRequestDto bankDetailRequestDto,
                                                         @PathVariable("id") Long id){
        return landlordService.addLandlordBankDetails(bankDetailRequestDto, id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "{id}/bank-details", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<LandlordBankDetailsResponseDto> getAllLandlordBankDetails(@PathVariable("id") Long id){
        return landlordService.getLandlordBankDetails(id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "{id}/next-of-kin", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public NextOfKinResponseDto addLandlordNextOfKin(@PathVariable("id") Long id,
                                                     @RequestBody NextOfKinRequestDto nextOfKinRequestDto){
        return landlordService.registerLandlordNextOfKin(nextOfKinRequestDto, id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PutMapping(path = "next-of-kin/{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public NextOfKinResponseDto updateLandlordNextOfKin(@PathVariable("id") Long id,
                                                     @RequestBody NextOfKinRequestDto nextOfKinRequestDto){
        return landlordService.updateLandlordNextOfKin(nextOfKinRequestDto, id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @GetMapping(path = "{id}/next-of-kins", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<NextOfKinResponseDto> getAllLandlordNextOfKins(@PathVariable("id") Long id){
        return landlordService.getAllLandlordNextOfKin(id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @DeleteMapping(path = "next-of-kin/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteLandlordNextOfKin(@PathVariable("id") Long id){
        return landlordService.deleteLandlordNextOfKin(id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PatchMapping(path = "activate/bank-detail/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean activateLandlordBankDetail(@PathVariable("id") Long id){
        return landlordService.activateLandlordBankDetail(id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PatchMapping(path = "de-activate/bank-detail/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public boolean deactivateLandlordBankDetail(@PathVariable("id") Long id){
        return landlordService.deactivateLandlordBankDetail(id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.READ','ADMIN_ROLE.READ')")
    @PutMapping(path = "bank-detail/{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public LandlordBankDetailsResponseDto updateLandlordBankDetail(@RequestBody BankDetailRequestDto bankDetailRequestDto,
                                                                   @PathVariable("id") Long id){
        return landlordService.updateLandlordBankDetails(bankDetailRequestDto, id);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "upload/profile-photo")
    @ResponseStatus(HttpStatus.OK)
    public void uploadPhoto(@RequestParam(value = "file", required = true) MultipartFile file,
                            @RequestParam(value = "fileName", required = true) String fileName,
                            @RequestParam(value = "landlordId", required = true) Long landlordId){
        landlordService.uploadProfilePhoto(file, fileName, landlordId);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "upload/lc-letter")
    @ResponseStatus(HttpStatus.OK)
    public void uploadLcLetter(@RequestParam(value = "file", required = true) MultipartFile file,
                               @RequestParam(value = "fileName", required = true) String fileName,
                               @RequestParam(value = "landlordId", required = true) Long landlordId){
        landlordService.uploadLcLetter(file, fileName, landlordId);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "upload/business-letter")
    @ResponseStatus(HttpStatus.OK)
    public void uploadBusinessAgreement(@RequestParam(value = "file", required = true) MultipartFile file,
                                        @RequestParam(value = "fileName", required = true) String fileName,
                                        @RequestParam(value = "landlordId", required = true) Long landlordId){
        landlordService.uploadBusinessAgreement(file, fileName, landlordId);
    }

    @PreAuthorize("hasAnyAuthority('LANDLORD_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "upload/id-photo")
    @ResponseStatus(HttpStatus.OK)
    public void uploadIdPhoto(@RequestParam(value = "file", required = true) MultipartFile file,
                              @RequestParam(value = "fileName", required = true) String fileName,
                              @RequestParam(value = "landlordId", required = true) Long landlordId){
        landlordService.uploadIdPhoto(file, fileName, landlordId);
    }
}
