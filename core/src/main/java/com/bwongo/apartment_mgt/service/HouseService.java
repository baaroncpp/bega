package com.bwongo.apartment_mgt.service;

import com.bwongo.apartment_mgt.models.dto.request.HouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.HouseTypeRequestDto;
import com.bwongo.apartment_mgt.models.dto.response.HouseResponseDto;
import com.bwongo.apartment_mgt.models.jpa.HouseType;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/10/23
 **/
public interface HouseService {
    HouseType addHouseType(HouseTypeRequestDto houseTypeRequestDto);
    HouseType updateHouseType(Long id, HouseTypeRequestDto houseTypeRequestDto);
    HouseResponseDto addHouse(HouseRequestDto houseRequestDto);
    HouseResponseDto updateHouse(Long id, HouseRequestDto houseRequestDto);
    HouseResponseDto getHouseById(Long id);
    List<HouseResponseDto> getHousesByLandlord(Long landlordId);
    List<HouseResponseDto> getHousesByApartment(Long apartmentId);
}
