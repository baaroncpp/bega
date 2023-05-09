package com.bwongo.apartment_mgt.service;

import com.bwongo.apartment_mgt.models.dto.request.ApartmentRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.HouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.HouseTypeRequestDto;
import com.bwongo.apartment_mgt.models.dto.response.ApartmentResponseDto;
import com.bwongo.apartment_mgt.models.dto.response.HouseResponseDto;
import com.bwongo.apartment_mgt.models.jpa.HouseType;
import com.bwongo.apartment_mgt.repository.ApartmentRepository;
import com.bwongo.apartment_mgt.repository.HouseRepository;
import com.bwongo.apartment_mgt.repository.HouseTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ApartmentServiceImp implements ApartmentService{

    private final ApartmentRepository apartmentRepository;
    private final HouseRepository houseRepository;
    private final HouseTypeRepository houseTypeRepository;

    @Override
    public HouseType addHouseType(HouseTypeRequestDto houseTypeRequestDto) {
        return null;
    }

    @Override
    public HouseType updateHouseType(Long id, HouseTypeRequestDto houseTypeRequestDto) {
        return null;
    }

    @Override
    public List<HouseType> getAllHouseTypes() {
        return null;
    }

    @Override
    public ApartmentResponseDto addApartment(ApartmentRequestDto apartmentRequestDto) {
        return null;
    }

    @Override
    public ApartmentResponseDto updateApartment(Long id, ApartmentRequestDto apartmentRequestDto) {
        return null;
    }

    @Override
    public ApartmentResponseDto getApartmentById(Long id) {
        return null;
    }

    @Override
    public List<ApartmentResponseDto> getApartmentsByLandlord(Long landlordId) {
        return null;
    }

    @Override
    public HouseResponseDto addHouse(HouseRequestDto houseRequestDto) {
        return null;
    }

    @Override
    public HouseResponseDto updateHouse(Long id, HouseRequestDto houseRequestDto) {
        return null;
    }

    @Override
    public List<HouseResponseDto> getHousesByApartment(Long apartmentId) {
        return null;
    }

    @Override
    public List<HouseResponseDto> getHousesByLandlord(Long landlordId) {
        return null;
    }
}
