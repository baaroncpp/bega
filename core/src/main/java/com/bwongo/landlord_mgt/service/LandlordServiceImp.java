package com.bwongo.landlord_mgt.service;

import com.bwongo.account_mgt.models.enums.AccountStatus;
import com.bwongo.account_mgt.models.enums.AccountType;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.account_mgt.repository.AccountRepository;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.landlord_mgt.model.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.model.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.repository.LandlordRepository;
import com.bwongo.landlord_mgt.service.dto.LandlordDtoService;
import com.bwongo.landlord_mgt.model.jpa.*;
import com.bwongo.tenant_mgt.utils.AuditService;
import com.bwongo.user_mgt.repository.TCountryRepository;
import com.bwongo.user_mgt.repository.TDistrictRepository;
import com.bwongo.user_mgt.repository.TUserMetaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.account_mgt.utils.AccountMsgConstant.LANDLORD_ACCOUNT_NOT_FOUND;
import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.*;
import static com.bwongo.user_mgt.util.UserMsgConstants.COUNTRY_NOT_FOUND;
import static com.bwongo.user_mgt.util.UserMsgConstants.DISTRICT_NOT_FOUND;

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
    private final TCountryRepository countryRepository;
    private final TDistrictRepository districtRepository;
    private final TUserMetaRepository userMetaRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public LandlordResponseDto addLandlord(LandlordRequestDto landlordRequestDto) {

        landlordRequestDto.validate();

        final var idNumber = landlordRequestDto.identificationNumber();
        final var email = landlordRequestDto.email();
        final var countryId = landlordRequestDto.countryId();
        final var districtId = landlordRequestDto.districtId();
        final var username = landlordRequestDto.username();

        Validate.isTrue(!landlordRepository.existsByUsername(username), ExceptionType.BAD_REQUEST, USERNAME_IS_TAKEN, username);
        Validate.isTrue(!userMetaRepository.existsByEmail(email), ExceptionType.BAD_REQUEST, EMAIL_IS_TAKEN, email);
        Validate.isTrue(!userMetaRepository.existsByIdentificationNumber(idNumber), ExceptionType.BAD_REQUEST, LANDLORD_WITH_ID_EXISTS, idNumber);
        Validate.isTrue(countryRepository.existsById(countryId), ExceptionType.BAD_REQUEST, COUNTRY_NOT_FOUND, countryId);
        Validate.isTrue(districtRepository.existsById(districtId), ExceptionType.BAD_REQUEST, DISTRICT_NOT_FOUND, districtId);

        var landlord = landlordDtoService.mapLandlordRequestDtoToLandlord(landlordRequestDto);

        var userMeta = landlord.getMetaData();
        userMeta.setNonVerifiedEmail(Boolean.TRUE);
        userMeta.setNonVerifiedPhoneNumber(Boolean.TRUE);

        auditService.stampAuditedEntity(userMeta);
        var existingUserMeta = userMetaRepository.save(userMeta);

        landlord.setActive(Boolean.FALSE);
        landlord.setMetaData(existingUserMeta);
        auditService.stampAuditedEntity(landlord);

        return landlordDtoService.mapLandlordToLandlordResponseDto(landlordRepository.save(landlord));
    }

    @Override
    @Transactional
    public LandlordResponseDto updateLandlord(Long id, LandlordRequestDto landlordRequestDto) {

        var existingLandlord = getById(id);

        var updatedLandlord = landlordDtoService.mapLandlordRequestDtoToLandlord(landlordRequestDto);
        var updatedUserMeta = updatedLandlord.getMetaData();
        updatedUserMeta.setId(existingLandlord.getMetaData().getId());
        updatedUserMeta.setNonVerifiedEmail(Boolean.TRUE);
        updatedUserMeta.setNonVerifiedPhoneNumber(Boolean.TRUE);

        auditService.stampAuditedEntity(updatedUserMeta);
        userMetaRepository.save(updatedUserMeta);

        updatedLandlord.setId(id);
        updatedLandlord.setActive(existingLandlord.isActive());
        updatedLandlord.setMetaData(updatedUserMeta);
        auditService.stampAuditedEntity(updatedLandlord);

        return landlordDtoService.mapLandlordToLandlordResponseDto(landlordRepository.save(updatedLandlord));
    }

    @Override
    public LandlordResponseDto getLandlordById(Long id) {
        return landlordDtoService.mapLandlordToLandlordResponseDto(getById(id));
    }

    @Override
    @Transactional
    public boolean activateLandlord(Long id) {

        var existingLandlord = landlordRepository.findById(id);
        Validate.isPresent(existingLandlord, LANDLORD_NOT_FOUND, id);

        final var landlord = existingLandlord.get();
        Validate.isTrue(!landlord.isActive(), ExceptionType.BAD_REQUEST, LANDLORD_IS_ACTIVE);

        landlord.setActive(Boolean.TRUE);
        auditService.stampAuditedEntity(landlord);
        landlordRepository.save(landlord);

        var landlordAccount = new Account();
        landlordAccount.setAvailableBalance(BigDecimal.ZERO);
        landlordAccount.setActivatedOn(DateTimeUtil.getCurrentUTCTime());
        landlordAccount.setActivatedBy(landlord.getModifiedBy());
        landlordAccount.setActive(Boolean.TRUE);
        landlordAccount.setAccountType(AccountType.BUSINESS);
        landlordAccount.setUserMeta(landlord.getMetaData());
        landlordAccount.setAccountStatus(AccountStatus.ACTIVE);

        auditService.stampAuditedEntity(landlordAccount);
        accountRepository.save(landlordAccount);

        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public boolean deactivateLandlord(Long id) {

        var existingLandlord = getById(id);
        existingLandlord.setActive(Boolean.FALSE);
        auditService.stampAuditedEntity(existingLandlord);
        landlordRepository.save(existingLandlord);

        var existingLandlordAccount = accountRepository.findByUserMeta(existingLandlord.getMetaData());
        Validate.isPresent(existingLandlordAccount, LANDLORD_ACCOUNT_NOT_FOUND, id);
        var landlordAccount = existingLandlordAccount.get();

        landlordAccount.setActive(Boolean.FALSE);
        landlordAccount.setSuspendedOn(DateTimeUtil.getCurrentUTCTime());
        landlordAccount.setSuspendedBy(existingLandlord.getModifiedBy());
        auditService.stampAuditedEntity(landlordAccount);
        accountRepository.save(landlordAccount);

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
