package com.bwongo.apartment_mgt.service.dto;

import com.bwongo.apartment_mgt.models.dto.request.ApartmentRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.AssignHouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.HouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.HouseTypeRequestDto;
import com.bwongo.apartment_mgt.models.dto.response.ApartmentResponseDto;
import com.bwongo.apartment_mgt.models.dto.response.AssignHouseResponseDto;
import com.bwongo.apartment_mgt.models.dto.response.HouseResponseDto;
import com.bwongo.apartment_mgt.models.enums.ApartmentType;
import com.bwongo.apartment_mgt.models.enums.ManagementFeeType;
import com.bwongo.apartment_mgt.models.enums.RentPeriod;
import com.bwongo.apartment_mgt.models.jpa.Apartment;
import com.bwongo.apartment_mgt.models.jpa.AssignHouse;
import com.bwongo.apartment_mgt.models.jpa.House;
import com.bwongo.apartment_mgt.models.jpa.HouseType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.landlord_mgt.model.jpa.Landlord;
import com.bwongo.landlord_mgt.service.dto.LandlordDtoService;
import com.bwongo.tenant_mgt.models.enums.BillingDuration;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.tenant_mgt.service.dto.TenantDtoService;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.PLACEMENT_DATE_FORMAT;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/10/23
 **/
@Service
@RequiredArgsConstructor
public class ApartmentDtoService {

    private final UserMgtDtoService userMgtDtoService;
    private final LandlordDtoService landlordDtoService;
    private final TenantDtoService tenantDtoService;

    public HouseType mapHouseTypeRequestDtoToHouseType(HouseTypeRequestDto houseTypeRequestDto){

        if(houseTypeRequestDto == null){
            return null;
        }

        HouseType houseType = new HouseType();
        houseType.setName(houseTypeRequestDto.name());
        houseType.setNote(houseTypeRequestDto.note());

        return houseType;
    }

    public House mapHouseRequestDtoToHouse(HouseRequestDto houseRequestDto){

        if(houseRequestDto == null){
            return null;
        }

        Apartment apartment = new Apartment();
        apartment.setId(houseRequestDto.apartmentId());

        HouseType houseType = new HouseType();
        houseType.setId(houseRequestDto.houseTypeId());

        House house = new House();
        house.setHouseType(houseType);
        house.setApartment(apartment);
        house.setHouseNumber(houseRequestDto.houseNumber());
        house.setRentFee(houseRequestDto.rentFee());
        house.setRentPeriod(RentPeriod.valueOf(houseRequestDto.rentPeriod()));
        house.setNote(houseRequestDto.note());
        house.setIsOccupied(houseRequestDto.isOccupied());
        house.setInitialRentPaymentPeriod(houseRequestDto.initialRentPaymentPeriod());

        return house;
    }

    public HouseResponseDto mapHouseToHouseResponseDto(House house){

        if(house == null){
            return null;
        }

        return new HouseResponseDto(
                house.getId(),
                house.getCreatedOn(),
                house.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(house.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(house.getCreatedBy()),
                house.getHouseNumber(),
                house.getHouseType(),
                mapApartmentToApartmentResponseDto(house.getApartment()),
                house.getRentFee(),
                house.getRentPeriod(),
                house.getNote(),
                house.getIsOccupied(),
                house.isRenovationChargeBilled(),
                house.getInitialRentPaymentPeriod()
        );
    }

    public ApartmentResponseDto mapApartmentToApartmentResponseDto(Apartment apartment){

        if(apartment == null){
            return null;
        }

        return new ApartmentResponseDto(
                apartment.getId(),
                apartment.getCreatedOn(),
                apartment.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(apartment.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(apartment.getCreatedBy()),
                apartment.getApartmentName(),
                apartment.getApartmentType(),
                apartment.getLocation(),
                landlordDtoService.mapLandlordToLandlordResponseDto(apartment.getApartmentOwner()),
                apartment.getApartmentDescription(),
                apartment.getManagementFee(),
                apartment.getManagementFeeType()
        );
    }

    public Apartment mapApartmentRequestDtoToApartment(ApartmentRequestDto apartmentRequestDto){

        if(apartmentRequestDto == null){
            return null;
        }
        Landlord landlord = new Landlord();
        landlord.setId(apartmentRequestDto.landlordId());

        Apartment apartment = new Apartment();
        apartment.setApartmentName(apartmentRequestDto.apartmentName());
        apartment.setApartmentType(ApartmentType.valueOf(apartmentRequestDto.apartmentType()));
        apartment.setLocation(apartmentRequestDto.location());
        apartment.setApartmentOwner(landlord);
        apartment.setApartmentDescription(apartmentRequestDto.apartmentDescription());
        apartment.setManagementFee(apartmentRequestDto.managementFee());
        apartment.setManagementFeeType(ManagementFeeType.valueOf(apartmentRequestDto.managementFeeType()));

        return apartment;
    }

    public AssignHouse mapAssignHouseRequestDtoToAssignHouse(AssignHouseRequestDto assignHouseRequestDto){

        if(assignHouseRequestDto == null){
            return null;
        }
        House house = new House();
        house.setId(assignHouseRequestDto.houseId());

        Tenant tenant = new Tenant();
        tenant.setId(assignHouseRequestDto.tenantId());

        AssignHouse assignHouse = new AssignHouse();
        assignHouse.setHouse(house);
        assignHouse.setPredefinedRent(assignHouseRequestDto.predefinedRent());
        assignHouse.setBillingDuration(BillingDuration.valueOf(assignHouseRequestDto.billingDuration()));
        assignHouse.setTenant(tenant);
        assignHouse.setDepositAmount(assignHouseRequestDto.depositAmount());
        assignHouse.setPlacementDate(DateTimeUtil.stringToDate(assignHouseRequestDto.placementDate(), PLACEMENT_DATE_FORMAT));

        return assignHouse;
    }

    public AssignHouseResponseDto mapAssignHouseToAssignHouseResponseDto(AssignHouse assignHouse){

        if(assignHouse == null){
            return null;
        }

        return new AssignHouseResponseDto(
                assignHouse.getId(),
                assignHouse.getCreatedOn(),
                assignHouse.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(assignHouse.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(assignHouse.getCreatedBy()),
                mapHouseToHouseResponseDto(assignHouse.getHouse()),
                assignHouse.getPredefinedRent(),
                assignHouse.getBillingDuration(),
                tenantDtoService.mapTenantToTenantResponseDto(assignHouse.getTenant()),
                assignHouse.getDepositAmount(),
                assignHouse.getRentAmountPaid(),
                assignHouse.getPlacementDate()
        );
    }
}
