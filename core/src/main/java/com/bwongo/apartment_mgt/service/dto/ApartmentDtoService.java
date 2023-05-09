package com.bwongo.apartment_mgt.service.dto;

import com.bwongo.apartment_mgt.models.dto.request.HouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.HouseTypeRequestDto;
import com.bwongo.apartment_mgt.models.dto.response.ApartmentResponseDto;
import com.bwongo.apartment_mgt.models.dto.response.HouseResponseDto;
import com.bwongo.apartment_mgt.models.enums.RentPeriod;
import com.bwongo.apartment_mgt.models.jpa.Apartment;
import com.bwongo.apartment_mgt.models.jpa.House;
import com.bwongo.apartment_mgt.models.jpa.HouseType;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/10/23
 **/
@Service
@RequiredArgsConstructor
public class ApartmentDtoService {

    private final UserMgtDtoService userMgtDtoService;

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
        house.setRentFee(houseRequestDto.rentFee());
        house.setRentPeriod(RentPeriod.valueOf(houseRequestDto.rentPeriod()));
        house.setNote(houseRequestDto.note());

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
                mapApartmentResponseDtoToApartment(house.getApartment()),
                house.getRentFee(),
                house.getRentPeriod(),
                house.getNote(),
                house.getIsOccupied()
        );
    }

    public ApartmentResponseDto mapApartmentResponseDtoToApartment(Apartment apartment){

        if(apartment == null){
            return null;
        }

        return new ApartmentResponseDto(

        );
    }
}
