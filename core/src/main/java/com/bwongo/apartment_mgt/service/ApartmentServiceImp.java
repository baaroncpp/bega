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
import com.bwongo.apartment_mgt.service.dto.ApartmentDtoService;
import com.bwongo.apartment_mgt.utils.ApartmentUtil;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.landlord_mgt.models.jpa.Landlord;
import com.bwongo.landlord_mgt.repository.LandlordRepository;
import com.bwongo.base.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;
import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.*;

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
    private final ApartmentDtoService apartmentDtoService;
    private final AuditService auditService;
    private final LandlordRepository landlordRepository;

    @Override
    public HouseType addHouseType(HouseTypeRequestDto houseTypeRequestDto) {

        houseTypeRequestDto.validate();
        final var name = houseTypeRequestDto.name();
        Validate.isTrue(!houseTypeRepository.existsByName(name), ExceptionType.BAD_REQUEST, HOUSE_TYPE_NAME_EXISTS, name);

        var houseType = apartmentDtoService.mapHouseTypeRequestDtoToHouseType(houseTypeRequestDto);
        auditService.stampLongEntity(houseType);

        return houseTypeRepository.save(houseType);
    }

    @Override
    public HouseType updateHouseType(Long id, HouseTypeRequestDto houseTypeRequestDto) {

        houseTypeRequestDto.validate();
        var existingHouseType = houseTypeRepository.findById(id);
        Validate.isPresent(existingHouseType, HOUSE_TYPE_NOT_FOUND, id);

        final var houseType = existingHouseType.get();

        var updatedHouseType = apartmentDtoService.mapHouseTypeRequestDtoToHouseType(houseTypeRequestDto);
        updatedHouseType.setId(id);
        updatedHouseType.setCreatedOn(houseType.getCreatedOn());

        auditService.stampLongEntity(houseType);

        return houseTypeRepository.save(updatedHouseType);
    }

    @Override
    public List<HouseType> getAllHouseTypes() {
        return houseTypeRepository.findAll();
    }

    @Override
    public ApartmentResponseDto addApartment(ApartmentRequestDto apartmentRequestDto) {

        apartmentRequestDto.validate();
        final var apartmentName = apartmentRequestDto.apartmentName();

        Validate.isTrue(!apartmentRepository.existsByApartmentName(apartmentName), ExceptionType.BAD_REQUEST, APARTMENT_NAME_EXISTS, apartmentName);

        final var landlordId = apartmentRequestDto.landlordId();
        var landlord = getLandlord(landlordId);

        var apartment = apartmentDtoService.mapApartmentRequestDtoToApartment(apartmentRequestDto);
        apartment.setApartmentOwner(landlord);
        auditService.stampAuditedEntity(apartment);

        return apartmentDtoService.mapApartmentToApartmentResponseDto(apartmentRepository.save(apartment));
    }

    @Override
    public ApartmentResponseDto updateApartment(Long id, ApartmentRequestDto apartmentRequestDto) {

        var existingApartment = apartmentRepository.findById(id);
        Validate.isPresent(existingApartment, APARTMENT_WITH_ID_NOT_FOUND, id);

        final var apartment = existingApartment.get();

        final var landlordId = apartmentRequestDto.landlordId();
        var landlord = getLandlord(landlordId);

        var updatedApartment = apartmentDtoService.mapApartmentRequestDtoToApartment(apartmentRequestDto);
        updatedApartment.setId(id);
        updatedApartment.setApartmentOwner(landlord);
        updatedApartment.setCreatedBy(apartment.getCreatedBy());
        updatedApartment.setCreatedOn(apartment.getCreatedOn());

        auditService.stampAuditedEntity(apartment);

        return apartmentDtoService.mapApartmentToApartmentResponseDto(apartmentRepository.save(updatedApartment));
    }

    @Override
    public ApartmentResponseDto getApartmentById(Long id) {

        Validate.isTrue(apartmentRepository.existsById(id), ExceptionType.RESOURCE_NOT_FOUND, APARTMENT_WITH_ID_NOT_FOUND, id);
        final var apartment = apartmentRepository.findById(id).get();
        return apartmentDtoService.mapApartmentToApartmentResponseDto(apartment);
    }

    @Override
    public List<ApartmentResponseDto> getApartmentsByLandlord(Long landlordId) {

        var existingLandlord = landlordRepository.findById(landlordId);
        Validate.isPresent(existingLandlord, LANDLORD_NOT_FOUND, landlordId);

        var landlord = existingLandlord.get();

        return apartmentRepository.findAllByApartmentOwner(landlord).stream()
                .map(apartmentDtoService::mapApartmentToApartmentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentResponseDto> getApartments(Pageable pageable) {
        return apartmentRepository.findAll(pageable).stream()
                .map(apartmentDtoService::mapApartmentToApartmentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public HouseResponseDto addHouse(HouseRequestDto houseRequestDto) {

        houseRequestDto.validate();

        final var apartmentId = houseRequestDto.apartmentId();
        var existingApartment = apartmentRepository.findById(apartmentId);
        Validate.isPresent(existingApartment, APARTMENT_WITH_ID_NOT_FOUND, apartmentId);

        final var apartment = existingApartment.get();
        var house = apartmentDtoService.mapHouseRequestDtoToHouse(houseRequestDto);
        house.setApartment(apartment);
        house.setReferenceNumber(ApartmentUtil.generateHouseReferenceNumber(apartment.getApartmentName()));

        auditService.stampAuditedEntity(house);

        return apartmentDtoService.mapHouseToHouseResponseDto(houseRepository.save(house));
    }

    @Override
    public HouseResponseDto updateHouse(Long id, HouseRequestDto houseRequestDto) {

        houseRequestDto.validate();
        var existingHouse = houseRepository.findById(id);
        Validate.isPresent(existingHouse, HOUSE_TYPE_NOT_FOUND, id);

        var house = existingHouse.get();

        final var apartmentId = houseRequestDto.apartmentId();
        var existingApartment = apartmentRepository.findById(apartmentId);
        Validate.isPresent(existingApartment, APARTMENT_WITH_ID_NOT_FOUND, apartmentId);

        final var apartment = existingApartment.get();
        var updatedHouse = apartmentDtoService.mapHouseRequestDtoToHouse(houseRequestDto);
        updatedHouse.setId(id);
        updatedHouse.setApartment(apartment);
        updatedHouse.setReferenceNumber(house.getReferenceNumber());
        updatedHouse.setCreatedBy(house.getCreatedBy());
        updatedHouse.setCreatedOn(house.getCreatedOn());

        auditService.stampAuditedEntity(updatedHouse);

        return apartmentDtoService.mapHouseToHouseResponseDto(houseRepository.save(updatedHouse));
    }

    @Override
    public List<HouseResponseDto> getHousesByApartment(Long apartmentId) {

        var existingApartment = apartmentRepository.findById(apartmentId);
        Validate.isPresent(existingApartment, APARTMENT_WITH_ID_NOT_FOUND, apartmentId);
        final var apartment = existingApartment.get();

        return houseRepository.findAllByApartment(apartment).stream()
                .map(apartmentDtoService::mapHouseToHouseResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HouseResponseDto> getHousesByLandlord(Long landlordId) {
        return null;
    }

    @Override
    public List<HouseResponseDto> getHousesByIsOccupied(boolean isOccupied, Pageable pageable) {
        return houseRepository.findAllByIsOccupied(isOccupied, pageable).stream()
                .map(apartmentDtoService::mapHouseToHouseResponseDto)
                .collect(Collectors.toList());
    }

    private Landlord getLandlord(Long id){
        var landlord = landlordRepository.findById(id);
        Validate.isPresent(landlord, LANDLORD_NOT_FOUND, id);
        return landlord.get();
    }
}
