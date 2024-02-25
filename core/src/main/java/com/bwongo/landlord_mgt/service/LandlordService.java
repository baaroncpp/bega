package com.bwongo.landlord_mgt.service;

import com.bwongo.base.models.enums.AccountStatus;
import com.bwongo.base.models.enums.AccountType;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.account_mgt.repository.AccountRepository;
import com.bwongo.account_mgt.service.AccountService;
import com.bwongo.base.models.enums.FileType;
import com.bwongo.base.service.FileStorageService;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.landlord_mgt.models.dto.request.BankDetailRequestDto;
import com.bwongo.landlord_mgt.models.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.models.dto.response.LandlordBankDetailsResponseDto;
import com.bwongo.landlord_mgt.models.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.repository.BankDetailRepository;
import com.bwongo.landlord_mgt.repository.LandLordNextOfKinRepository;
import com.bwongo.landlord_mgt.repository.LandlordBankDetailsRepository;
import com.bwongo.landlord_mgt.repository.LandlordRepository;
import com.bwongo.landlord_mgt.service.dto.LandlordDtoService;
import com.bwongo.landlord_mgt.models.jpa.*;
import com.bwongo.base.service.AuditService;
import com.bwongo.base.repository.TCountryRepository;
import com.bwongo.base.repository.TDistrictRepository;
import com.bwongo.user_mgt.models.dto.request.NextOfKinRequestDto;
import com.bwongo.user_mgt.models.dto.response.NextOfKinResponseDto;
import com.bwongo.user_mgt.repository.TNextOfKinRepository;
import com.bwongo.user_mgt.repository.TUserMetaRepository;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bwongo.account_mgt.utils.AccountMsgConstant.*;
import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.landlord_mgt.utils.LandLordUtils.checkIfPassedFieldsAreValid;
import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.*;
import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class LandlordService{

    private final LandlordRepository landlordRepository;
    private final LandlordDtoService landlordDtoService;
    private final AuditService auditService;
    private final TCountryRepository countryRepository;
    private final TDistrictRepository districtRepository;
    private final TUserMetaRepository userMetaRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final BankDetailRepository bankDetailRepository;
    private final LandlordBankDetailsRepository landlordBankDetailsRepository;
    private final TNextOfKinRepository nextOfKinRepository;
    private final LandLordNextOfKinRepository landLordNextOfKinRepository;
    private final UserMgtDtoService userMgtDtoService;
    private final FileStorageService fileStorageService;

    @Transactional
    public LandlordResponseDto addLandlord(LandlordRequestDto landlordRequestDto) {

        landlordRequestDto.validate();

        final var idNumber = landlordRequestDto.getIdentificationNumber();
        final var email = landlordRequestDto.getEmail();
        final var countryId = landlordRequestDto.getCountryId();
        final var districtId = landlordRequestDto.getDistrictId();
        final var username = landlordRequestDto.getUsername();

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

    @Transactional
    public LandlordResponseDto updateLandlord(Long id, LandlordRequestDto landlordRequestDto) {

        var existingLandlord = getById(id);
        var currentLoginPassword = existingLandlord.getLoginPassword();

        final var loginPassword = landlordRequestDto.getLoginPassword();
        Validate.isTrue(loginPassword.isEmpty(), ExceptionType.ACCESS_DENIED, PASSWORD_CANNOT_BE_UPDATED);

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
        updatedLandlord.setLoginPassword(currentLoginPassword);
        auditService.stampAuditedEntity(updatedLandlord);

        return landlordDtoService.mapLandlordToLandlordResponseDto(landlordRepository.save(updatedLandlord));
    }

    @Transactional
    public LandlordResponseDto updateLandlordByField(Long id, Map<String, Object> fields){

        var existingLandlord = getById(id);

        var landlordRequestDto = new LandlordRequestDto();
        Validate.doesObjectContainFields(landlordRequestDto, new ArrayList<>(fields.keySet()));

        //TODO check and validate the passed fields
        checkIfPassedFieldsAreValid(fields);

        fields.forEach(
                (key, value) -> {
                    var field = ReflectionUtils.findField(TLandlord.class, key);
                    assert field != null;
                    field.setAccessible(Boolean.TRUE);
                    ReflectionUtils.setField(field, existingLandlord, value);
                }
        );
        auditService.stampAuditedEntity(existingLandlord);
        landlordRepository.save(existingLandlord);

        return landlordDtoService.mapLandlordToLandlordResponseDto(existingLandlord);
    }

    public LandlordResponseDto getLandlordById(Long id) {
        return landlordDtoService.mapLandlordToLandlordResponseDto(getById(id));
    }

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
        landlordAccount.setAccountNumber(accountService.createNonExistingAccountNumber());

        auditService.stampAuditedEntity(landlordAccount);
        accountRepository.save(landlordAccount);

        return Boolean.TRUE;
    }

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

    public List<LandlordResponseDto> getLandlords(Pageable pageable) {
        return landlordRepository.findAllByActive(Boolean.TRUE, pageable).stream()
                .map(landlordDtoService::mapLandlordToLandlordResponseDto)
                .toList();
    }

    @Transactional
    public LandlordBankDetailsResponseDto addLandlordBankDetails(BankDetailRequestDto bankDetailRequestDto, Long landlordId){

        bankDetailRequestDto.validate();
        var landlord = getById(landlordId);
        var metaData = landlord.getMetaData();
        var accountName = bankDetailRequestDto.accountName();
        var accountNumber = bankDetailRequestDto.accountNumber();

        var existingBankDetail = bankDetailRepository.findByAccountNameAndAccountNumber(accountName, accountNumber);
        Validate.isTrue(existingBankDetail.isEmpty(), ExceptionType.BAD_REQUEST, BANK_DETAILS_WITH_NAME_AND_NUMBER_EXIST, accountName, accountNumber);

        var bankDetails = landlordDtoService.mapBankDetailRequestToTBankDetail(bankDetailRequestDto);
        bankDetails.setUserMeta(metaData);
        bankDetails.setActive(Boolean.TRUE);

        auditService.stampAuditedEntity(bankDetails);
        var savedBankDetails = bankDetailRepository.save(bankDetails);

        var landlordBankDetails = new TLandLordBankDetails();
        landlordBankDetails.setBankDetail(savedBankDetails);
        landlordBankDetails.setLandlord(landlord);
        landlordBankDetails.setActive(Boolean.FALSE);

        auditService.stampAuditedEntity(landlordBankDetails);
        var savedLandlordBankDetail = landlordBankDetailsRepository.save(landlordBankDetails);

        return landlordDtoService.landlordBankDetailToDto(savedLandlordBankDetail);
    }

    @Transactional
    public LandlordBankDetailsResponseDto updateLandlordBankDetails(BankDetailRequestDto bankDetailRequestDto, Long landlordBankDetailsId){

        bankDetailRequestDto.validate();

        var existingLandlordDetails = getLandlordBankDetail(landlordBankDetailsId);

        var bankDetail = existingLandlordDetails.getBankDetail();
        bankDetail.setBankName(bankDetailRequestDto.bankName());
        bankDetail.setAccountNumber(bankDetailRequestDto.accountName());
        bankDetail.setAccountNumber(bankDetailRequestDto.accountNumber());
        auditService.stampAuditedEntity(bankDetail);

        var updatedBankDetail = bankDetailRepository.save(bankDetail);

        existingLandlordDetails.setBankDetail(updatedBankDetail);
        auditService.stampAuditedEntity(existingLandlordDetails);

        var updatedExistingLandlordDetail = landlordBankDetailsRepository.save(existingLandlordDetails);

        return landlordDtoService.landlordBankDetailToDto(updatedExistingLandlordDetail);
    }

    public List<LandlordBankDetailsResponseDto> getLandlordBankDetails(Long landlordId){
        var landlord = getById(landlordId);
        return landlordBankDetailsRepository.findAllByLandlord(landlord).stream()
                .map(landlordDtoService::landlordBankDetailToDto)
                .toList();
    }

    public boolean activateLandlordBankDetail(Long landlordBankDetailId){

        var landlordBankDetail = getLandlordBankDetail(landlordBankDetailId);
        Validate.isTrue(!landlordBankDetail.isActive(), ExceptionType.BAD_REQUEST, LANDLORD_BANK_DETAIL_ALREADY_ACTIVE, landlordBankDetailId);

        landlordBankDetail.setActive(Boolean.TRUE);
        auditService.stampAuditedEntity(landlordBankDetail);
        landlordBankDetailsRepository.save(landlordBankDetail);

        return Boolean.TRUE;
    }

    public boolean deactivateLandlordBankDetail(Long landlordBankDetailId){

        var landlordBankDetail = getLandlordBankDetail(landlordBankDetailId);
        Validate.isTrue(landlordBankDetail.isActive(), ExceptionType.BAD_REQUEST, LANDLORD_BANK_DETAIL_ALREADY_DE_ACTIVE, landlordBankDetailId);

        landlordBankDetail.setActive(Boolean.FALSE);
        auditService.stampAuditedEntity(landlordBankDetail);
        landlordBankDetailsRepository.save(landlordBankDetail);

        return Boolean.TRUE;
    }

    @Transactional
    public NextOfKinResponseDto registerLandlordNextOfKin(NextOfKinRequestDto nextOfKinRequestDto, Long landlordId){

        nextOfKinRequestDto.validate();

        var landlord = getById(landlordId);
        var email = nextOfKinRequestDto.email();
        var idNumber = nextOfKinRequestDto.idNumber();

        Validate.isTrue(!nextOfKinRepository.existsByEmail(email), ExceptionType.BAD_REQUEST, EMAIL_IS_TAKEN, email);
        Validate.isTrue(!nextOfKinRepository.existsByIdNumber(idNumber), ExceptionType.BAD_REQUEST, NEXT_OF_KIN_WITH_ID_EXISTS, idNumber);

        var nextOfKin = userMgtDtoService.mapNextOfKinRequestDtoToTNextOfKin(nextOfKinRequestDto);

        auditService.stampAuditedEntity(nextOfKin);
        var savedNextOfKin = nextOfKinRepository.save(nextOfKin);

        var landlordNextOfKin = new TLandlordNextOfKin();
        landlordNextOfKin.setLandlord(landlord);
        landlordNextOfKin.setNextOfKin(savedNextOfKin);
        auditService.stampAuditedEntity(landlordNextOfKin);

        landLordNextOfKinRepository.save(landlordNextOfKin);

        return userMgtDtoService.mapTNextOfKinToDto(savedNextOfKin);
    }

    @Transactional
    public NextOfKinResponseDto updateLandlordNextOfKin(NextOfKinRequestDto nextOfKinRequestDto, Long landlordNextOfKinId){

        var landlordNextOfKin = getLandlordNextOfKin(landlordNextOfKinId);

        nextOfKinRequestDto.validate();
        var existingNextOfKin = landlordNextOfKin.getNextOfKin();

        var email = nextOfKinRequestDto.email();
        var idNumber = nextOfKinRequestDto.idNumber();

        if(!email.equals(existingNextOfKin.getEmail()))
            Validate.isTrue(!nextOfKinRepository.existsByEmail(email), ExceptionType.BAD_REQUEST, EMAIL_IS_TAKEN, email);

        if(!idNumber.equals(existingNextOfKin.getIdNumber()))
            Validate.isTrue(!nextOfKinRepository.existsByIdNumber(idNumber), ExceptionType.BAD_REQUEST, NEXT_OF_KIN_WITH_ID_EXISTS, idNumber);

        var nextOfKin = userMgtDtoService.mapNextOfKinRequestDtoToTNextOfKin(nextOfKinRequestDto);
        nextOfKin.setId(landlordNextOfKinId);
        nextOfKin.setCreatedOn(existingNextOfKin.getCreatedOn());
        nextOfKin.setCreatedBy(existingNextOfKin.getCreatedBy());

        auditService.stampAuditedEntity(nextOfKin);
        var savedNextOfKin = nextOfKinRepository.save(nextOfKin);

        landlordNextOfKin.setNextOfKin(savedNextOfKin);

        auditService.stampAuditedEntity(landlordNextOfKin);

        landLordNextOfKinRepository.save(landlordNextOfKin);

        return userMgtDtoService.mapTNextOfKinToDto(savedNextOfKin);
    }

    public List<NextOfKinResponseDto> getAllLandlordNextOfKin(Long landlordId){
        var landlord = getById(landlordId);

        return landLordNextOfKinRepository.findAllByLandlord(landlord).stream()
                .map(landlordNextOfKin -> userMgtDtoService.mapTNextOfKinToDto(landlordNextOfKin.getNextOfKin()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteLandlordNextOfKin(Long landlordNextOfKinId){

        var landlordNextOfKin = getLandlordNextOfKin(landlordNextOfKinId);
        var nextOfKin = landlordNextOfKin.getNextOfKin();

        landLordNextOfKinRepository.delete(landlordNextOfKin);
        nextOfKinRepository.delete(nextOfKin);

        return Boolean.TRUE;
    }

    public void uploadLcLetter(MultipartFile file, String fileName, Long landlordId){

        var landlord = getById(landlordId);
        landlord.setOwnerShipLCLetterUrlPath(fileStorageService.store(file, fileName, FileType.DOCUMENT.name()));

        auditService.stampAuditedEntity(landlord);
        landlordRepository.save(landlord);
    }

    public void uploadBusinessAgreement(MultipartFile file, String fileName, Long landlordId){

        var landlord = getById(landlordId);
        landlord.setBusinessAgreementPath(fileStorageService.store(file, fileName, FileType.DOCUMENT.name()));

        auditService.stampAuditedEntity(landlord);
        landlordRepository.save(landlord);
    }

    public void uploadIdPhoto(MultipartFile file, String fileName, Long landlordId){
        var landlord = getById(landlordId);
        var metaData = landlord.getMetaData();

        metaData.setIdentificationPath(fileStorageService.store(file, fileName, FileType.IMAGE.name()));

        auditService.stampAuditedEntity(metaData);
        userMetaRepository.save(metaData);
    }

    public void uploadProfilePhoto(MultipartFile file, String fileName, Long landlordId){
        var landlord = getById(landlordId);
        var metaData = landlord.getMetaData();

        metaData.setImagePath(fileStorageService.store(file, fileName, FileType.IMAGE.name()));

        auditService.stampAuditedEntity(metaData);
        userMetaRepository.save(metaData);
    }

    private TLandlordNextOfKin getLandlordNextOfKin(Long id){

        var existingLandlordNextOfKin = landLordNextOfKinRepository.findById(id);
        Validate.isPresent(existingLandlordNextOfKin, LANDLORD_NEXT_OF_KIN_NOT_FOUND, id);

        return existingLandlordNextOfKin.get();
    }

    private TLandLordBankDetails getLandlordBankDetail(Long id){

        var existingLandlordBankDetail = landlordBankDetailsRepository.findById(id);
        Validate.isPresent(existingLandlordBankDetail, LANDLORD_BANK_DETAIL_NOT_FOUND, id);

        return existingLandlordBankDetail.get();
    }

    private TLandlord getById(Long id){

        var existingLandlord = landlordRepository.findById(id);
        Validate.isPresent(existingLandlord, LANDLORD_NOT_FOUND, id);

        final var landlord = existingLandlord.get();
        Validate.isTrue(landlord.isActive(), ExceptionType.BAD_REQUEST, LANDLORD_IS_INACTIVE);

        return landlord;
    }
}
