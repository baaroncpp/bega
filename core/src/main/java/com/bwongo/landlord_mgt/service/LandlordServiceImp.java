package com.bwongo.landlord_mgt.service;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.landlord_mgt.model.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.model.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.repository.LandlordRepository;
import com.bwongo.landlord_mgt.service.dto.LandlordDtoService;
import com.bwongo.tenant_mgt.utils.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.bwongo.base.utils.BaseMsgConstants.EMAIL_IS_TAKEN;
import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.LANDLORD_WITH_ID_EXISTS;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
@Slf4j
@RequiredArgsConstructor
public class LandlordServiceImp implements LandlordService{

    private final LandlordRepository landlordRepository;
    private final LandlordDtoService landlordDtoService;
    private final AuditService auditService;

    @Override
    public LandlordResponseDto addLandLord(LandlordRequestDto landlordRequestDto) {

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
    public LandlordResponseDto updateLandLord(Long id, LandlordRequestDto landlordRequestDto) {
        return null;
    }

    @Override
    public LandlordResponseDto getLandLordById(Long id) {
        return null;
    }

    @Override
    public List<LandlordResponseDto> getLandlords(Pageable pageable) {
        return null;
    }
}
