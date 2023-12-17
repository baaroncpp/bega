package com.bwongo.landlord_mgt.service.dto;

import com.bwongo.base.models.enums.IdentificationType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.landlord_mgt.models.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.models.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.models.jpa.Landlord;
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

    public Landlord mapLandlordRequestDtoToLandlord(LandlordRequestDto landlordRequestDto){

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

        Landlord landlord = new Landlord();
        landlord.setDistrict(district);
        landlord.setUsername(landlordRequestDto.getUsername());
        landlord.setPhysicalAddress(landlordRequestDto.getPhysicalAddress());
        landlord.setLoginPassword(passwordEncoder.encode(landlordRequestDto.getLoginPassword()));
        landlord.setMetaData(userMeta);

        return landlord;
    }

    public LandlordResponseDto mapLandlordToLandlordResponseDto(Landlord landlord){

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
                userMgtDtoService.mapTUserMetaToUserMetaResponseDto(landlord.getMetaData())
        );
    }
}
