package com.bwongo.landlord_mgt.service.dto;

import com.bwongo.base.models.enums.IdentificationType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.landlord_mgt.models.dto.request.BankDetailRequestDto;
import com.bwongo.landlord_mgt.models.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.models.dto.response.BankDetailsResponseDto;
import com.bwongo.landlord_mgt.models.dto.response.LandlordBankDetailsResponseDto;
import com.bwongo.landlord_mgt.models.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.models.jpa.TBankDetail;
import com.bwongo.landlord_mgt.models.jpa.TLandLordBankDetails;
import com.bwongo.landlord_mgt.models.jpa.TLandlord;
import com.bwongo.user_mgt.models.enums.GenderEnum;
import com.bwongo.base.models.jpa.TCountry;
import com.bwongo.base.models.jpa.TDistrict;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.PLACEMENT_DATE_FORMAT;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
@Service
@RequiredArgsConstructor
public class LandlordDtoService {

    private final UserMgtDtoService userMgtDtoService;
    private final PasswordEncoder passwordEncoder;

    public TLandlord mapLandlordRequestDtoToLandlord(LandlordRequestDto landlordRequestDto){

        if(landlordRequestDto == null){
            return null;
        }
        var country = new TCountry();
        country.setId(landlordRequestDto.getCountryId());

        var district = new TDistrict();
        district.setId(landlordRequestDto.getDistrictId());

        StringBuilder displayName = new StringBuilder();
        displayName.append(landlordRequestDto.getFirstName())
                .append(" ")
                .append(landlordRequestDto.getLastName());

        var userMeta = new TUserMeta();
        userMeta.setCountry(country);
        userMeta.setFirstName(landlordRequestDto.getFirstName());
        userMeta.setLastName(landlordRequestDto.getLastName());
        userMeta.setMiddleName(landlordRequestDto.getMiddleName());
        userMeta.setIdentificationType(IdentificationType.valueOf(landlordRequestDto.getIdentificationType()));
        userMeta.setIdentificationNumber(landlordRequestDto.getIdentificationNumber());
        userMeta.setPhoneNumber(landlordRequestDto.getPhoneNumber());
        userMeta.setPhoneNumber2(landlordRequestDto.getPhoneNumber2());
        userMeta.setEmail(landlordRequestDto.getEmail());
        userMeta.setDisplayName(displayName.toString());
        userMeta.setGender(GenderEnum.valueOf(landlordRequestDto.getGender()));
        userMeta.setBirthDate(DateTimeUtil.stringToDate(landlordRequestDto.getBirthDate(), PLACEMENT_DATE_FORMAT));

        TLandlord landlord = new TLandlord();
        landlord.setDistrict(district);
        landlord.setUsername(landlordRequestDto.getUsername());
        landlord.setPhysicalAddress(landlordRequestDto.getPhysicalAddress());
        landlord.setLoginPassword(passwordEncoder.encode(landlordRequestDto.getLoginPassword()));
        landlord.setMetaData(userMeta);
        landlord.setTin(landlordRequestDto.getTin());

        return landlord;
    }

    public LandlordResponseDto mapLandlordToLandlordResponseDto(TLandlord landlord){

        if(landlord == null){
            return null;
        }

        return new LandlordResponseDto(
                landlord.getId(),
                landlord.getCreatedOn(),
                landlord.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(landlord.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(landlord.getCreatedBy()),
                landlord.isActive(),
                landlord.getUsername(),
                landlord.getDistrict(),
                landlord.getPhysicalAddress(),
                landlord.getTin(),
                landlord.getOwnerShipLCLetterUrlPath(),
                userMgtDtoService.mapTUserMetaToUserMetaResponseDto(landlord.getMetaData())
        );
    }

    public TBankDetail mapBankDetailRequestToTBankDetail(BankDetailRequestDto bankDetailRequestDto){

        if(bankDetailRequestDto == null){
            return null;
        }

        var bankDetail = new TBankDetail();
        bankDetail.setBankName(bankDetailRequestDto.bankName());
        bankDetail.setAccountName(bankDetailRequestDto.accountName());
        bankDetail.setAccountNumber(bankDetailRequestDto.accountNumber());

        return bankDetail;
    }

    public BankDetailsResponseDto bankDetailToDto(TBankDetail bankDetail){

        if(bankDetail == null){
            return null;
        }

        return new BankDetailsResponseDto(
                bankDetail.getId(),
                bankDetail.getCreatedOn(),
                bankDetail.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(bankDetail.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(bankDetail.getCreatedBy()),
                bankDetail.isActive(),
                bankDetail.getBankName(),
                bankDetail.getAccountName(),
                bankDetail.getAccountNumber()
        );
    }

    public LandlordBankDetailsResponseDto landlordBankDetailToDto(TLandLordBankDetails landLordBankDetails){

        if(landLordBankDetails == null){
            return null;
        }

        return new LandlordBankDetailsResponseDto(
                landLordBankDetails.getId(),
                landLordBankDetails.getCreatedOn(),
                landLordBankDetails.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(landLordBankDetails.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(landLordBankDetails.getCreatedBy()),
                landLordBankDetails.isActive(),
                bankDetailToDto(landLordBankDetails.getBankDetail()),
                mapLandlordToLandlordResponseDto(landLordBankDetails.getLandlord())
        );
    }
}
