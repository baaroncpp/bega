package com.bwongo.apartment_mgt.api;

import com.bwongo.apartment_mgt.models.dto.request.ApartmentRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.HouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.HouseTypeRequestDto;
import com.bwongo.apartment_mgt.models.dto.response.ApartmentResponseDto;
import com.bwongo.apartment_mgt.models.dto.response.HouseResponseDto;
import com.bwongo.apartment_mgt.models.jpa.HouseType;
import com.bwongo.apartment_mgt.service.ApartmentService;
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
 * @Date 5/11/23
 **/
@RestController
@RequestMapping(path = "v1/api")
@RequiredArgsConstructor
public class ApartmentApi {

    private final ApartmentService apartmentService;

    /** HOUSE_TYPE **/

    @RolesAllowed({"ROLE_ADMIN.WRITE"})
    @PostMapping(path = "house-type", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public HouseType addHouseType(@RequestBody HouseTypeRequestDto houseTypeRequestDto){
        return apartmentService.addHouseType(houseTypeRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.UPDATE"})
    @PutMapping(path = "house-type/{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public HouseType updateHouseType(@PathVariable("id") Long id,
                                     @RequestBody HouseTypeRequestDto houseTypeRequestDto){
        return apartmentService.updateHouseType(id, houseTypeRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "house-types", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<HouseType> getHouseTypes(){
        return apartmentService.getAllHouseTypes();
    }

    /** APARTMENT **/

    @RolesAllowed({"ROLE_ADMIN.WRITE"})
    @PostMapping(path = "apartment", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public ApartmentResponseDto addApartment(@RequestBody ApartmentRequestDto apartmentRequestDto){
        return apartmentService.addApartment(apartmentRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.WRITE"})
    @PutMapping(path = "apartment/{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public ApartmentResponseDto updateApartment(@PathVariable("id") Long id,
                                                @RequestBody ApartmentRequestDto apartmentRequestDto){
        return apartmentService.updateApartment(id, apartmentRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "apartment/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public ApartmentResponseDto getApartmentById(@PathVariable("id") Long id){
        return apartmentService.getApartmentById(id);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "apartments/landlord/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentResponseDto> getApartmentsByLandlordId(@PathVariable("id") Long id){
        return apartmentService.getApartmentsByLandlord(id);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "apartments", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentResponseDto> getApartments(@RequestParam("page") int page,
                                                    @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return apartmentService.getApartments(pageable);
    }

    /** HOUSE **/

    @RolesAllowed({"ROLE_ADMIN.WRITE"})
    @PostMapping(path = "house", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public HouseResponseDto addHouse(@RequestBody HouseRequestDto houseRequestDto){
        return apartmentService.addHouse(houseRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.UPDATE"})
    @PutMapping(path = "house/{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public HouseResponseDto updateHouse(@PathVariable("id") Long id, @RequestBody HouseRequestDto houseRequestDto){
        return apartmentService.updateHouse(id, houseRequestDto);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "houses/apartment/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<HouseResponseDto> getHousesByApartment(@PathVariable("id") Long id){
        return apartmentService.getHousesByApartment(id);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "houses/landlord/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<HouseResponseDto> getHousesByLandlord(@PathVariable("id") Long id){
        return apartmentService.getHousesByLandlord(id);
    }

    @RolesAllowed({"ROLE_ADMIN.READ"})
    @GetMapping(path = "houses", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<HouseResponseDto> getHouses(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("isOccupied") boolean isOccupied){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return apartmentService.getHousesByIsOccupied(isOccupied, pageable);
    }
}
