package com.bwongo.landlord_mgt.service;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.landlord_mgt.model.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.model.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.repository.LandlordRepository;
import com.bwongo.landlord_mgt.service.dto.LandlordDtoService;
import com.bwongo.landlord_mgt.model.jpa.*;
import com.bwongo.tenant_mgt.utils.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.*;
/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class LandlordServiceImp implements LandlordService{

    private final LandlordRepository landlordRepository;
    private final LandlordDtoService landlordDtoService;
    private final AuditService auditService;

    @Override
    public LandlordResponseDto addLandlord(LandlordRequestDto landlordRequestDto) {

        landlordRequestDto.validate();

        final var idNumber = landlordRequestDto.identificationNumber();
        final var email = landlordRequestDto.email();

        Validate.isTrue(!landlordRepository.existsByEmail(email), ExceptionType.BAD_REQUEST, EMAIL_IS_TAKEN, email);
        Validate.isTrue(!landlordRepository.existsByIdentificationNumber(idNumber), ExceptionType.BAD_REQUEST, LANDLORD_WITH_ID_EXISTS, idNumber);

        var landlord = landlordDtoService.mapLandlordRequestDtoToLandlord(landlordRequestDto);
        landlord.setActive(Boolean.TRUE);
        auditService.stampAuditedEntity(landlord);

        return landlordDtoService.mapLandlordToLandlordResponseDto(landlordRepository.save(landlord));
    }

    @Override
    public LandlordResponseDto updateLandlord(Long id, LandlordRequestDto landlordRequestDto) {

        var existingLandlord = getById(id);
        Validate.isTrue(existingLandlord.isActive(), ExceptionType.BAD_REQUEST, LANDLORD_IS_INACTIVE);

        var updatedLandlord = landlordDtoService.mapLandlordRequestDtoToLandlord(landlordRequestDto);
        updatedLandlord.setId(id);
        auditService.stampAuditedEntity(updatedLandlord);

        return landlordDtoService.mapLandlordToLandlordResponseDto(updatedLandlord);
    }

    @Override
    public LandlordResponseDto getLandlordById(Long id) {
        return landlordDtoService.mapLandlordToLandlordResponseDto(getById(id));
    }

    @Override
    public boolean activateLandlord(Long id) {

        var existingLandlord = landlordRepository.findById(id);
        Validate.isPresent(existingLandlord, LANDLORD_NOT_FOUND, id);

        final var landlord = existingLandlord.get();
        Validate.isTrue(!landlord.isActive(), ExceptionType.BAD_REQUEST, LANDLORD_IS_ACTIVE);

        landlord.setActive(Boolean.TRUE);
        auditService.stampAuditedEntity(landlord);
        landlordRepository.save(landlord);

        return Boolean.TRUE;
    }

    @Override
    public boolean deactivateLandlord(Long id) {

        var existingLandlord = getById(id);
        existingLandlord.setActive(Boolean.FALSE);

        auditService.stampAuditedEntity(existingLandlord);
        landlordRepository.save(existingLandlord);

        return Boolean.TRUE;
    }

    @Override
    public List<LandlordResponseDto> getLandlords(Pageable pageable) {
        return landlordRepository.findAllByActive(Boolean.TRUE, pageable).stream()
                .map(landlordDtoService::mapLandlordToLandlordResponseDto)
                .collect(Collectors.toList());
    }

    private Landlord getById(Long id){

        var existingLandlord = landlordRepository.findById(id);
        Validate.isPresent(existingLandlord, LANDLORD_NOT_FOUND, id);

        final var landlord = existingLandlord.get();
        Validate.isTrue(landlord.isActive(), ExceptionType.BAD_REQUEST, LANDLORD_IS_INACTIVE);

        return landlord;
    }
}
