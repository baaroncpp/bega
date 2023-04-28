package com.bwongo.apartment_mgt.service;

import com.bwongo.apartment_mgt.models.dto.*;
import com.bwongo.apartment_mgt.models.jpa.HouseType;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
public interface ApartmentService {

    /** HOUSE_TYPE **/
    HouseType addHouseType(HouseTypeRequestDto houseTypeRequestDto);
    HouseType updateHouseType(Long id, HouseTypeRequestDto houseTypeRequestDto);
    List<HouseType> getAllHouseTypes();

    /** APARTMENT **/
    ApartmentResponseDto addApartment(ApartmentRequestDto apartmentRequestDto);
    ApartmentResponseDto updateApartment(Long id, ApartmentRequestDto apartmentRequestDto);
    ApartmentResponseDto getApartmentById(Long id);
    List<ApartmentResponseDto> getApartmentsByLandlord(Long landlordId);

    /** HOUSE **/
    HouseResponseDto addHouse(HouseRequestDto houseRequestDto);
    HouseResponseDto updateHouse(Long id, HouseRequestDto houseRequestDto);
    List<HouseResponseDto> getHousesByApartment(Long apartmentId);
    List<HouseResponseDto> getHousesByLandlord(Long landlordId);
}
