package com.bwongo.landlord_mgt.service.dto;

import com.bwongo.base.model.enums.IdentificationType;
import com.bwongo.landlord_mgt.model.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.model.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.model.jpa.Landlord;
import com.bwongo.user_mgt.models.jpa.TCountry;
import com.bwongo.user_mgt.models.jpa.TDistrict;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        country.setId(landlordRequestDto.countryId());

        var district = new TDistrict();
        district.setId(landlordRequestDto.districtId());

        StringBuilder displayName = new StringBuilder();
        displayName.append(landlordRequestDto.firstName())
                .append(" ")
                .append(landlordRequestDto.lastName());

        var userMeta = new TUserMeta();
        userMeta.setCountry(country);
        userMeta.setFirstName(landlordRequestDto.firstName());
        userMeta.setLastName(landlordRequestDto.lastName());
        userMeta.setMiddleName(landlordRequestDto.middleName());
        userMeta.setIdentificationType(IdentificationType.valueOf(landlordRequestDto.identificationType()));
        userMeta.setIdentificationNumber(landlordRequestDto.identificationNumber());
        userMeta.setPhoneNumber(landlordRequestDto.phoneNumber());
        userMeta.setPhoneNumber2(landlordRequestDto.phoneNumber2());
        userMeta.setEmail(landlordRequestDto.email());
        userMeta.setDisplayName(displayName.toString());

        Landlord landlord = new Landlord();
        landlord.setDistrict(district);
        landlord.setUsername(landlordRequestDto.username());
        landlord.setPhysicalAddress(landlordRequestDto.physicalAddress());
        landlord.setLoginPassword(passwordEncoder.encode(landlordRequestDto.loginPassword()));
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
