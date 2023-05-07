package com.bwongo.landlord_mgt.service.dto;

import com.bwongo.base.model.enums.IdentificationType;
import com.bwongo.landlord_mgt.model.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.model.dto.response.LandlordResponseDto;
import com.bwongo.landlord_mgt.model.jpa.Landlord;
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

        Landlord landlord = new Landlord();
        landlord.setFirstName(landlordRequestDto.firstName());
        landlord.setLastName(landlordRequestDto.lastName());
        landlord.setMiddleName(landlordRequestDto.middleName());
        landlord.setIdentificationType(IdentificationType.valueOf(landlordRequestDto.identificationType()));
        landlord.setIdentificationNumber(landlordRequestDto.identificationNumber());
        landlord.setPhoneNumber(landlordRequestDto.phoneNumber());
        landlord.setSecondPhoneNumber(landlordRequestDto.secondPhoneNumber());
        landlord.setPhysicalAddress(landlordRequestDto.physicalAddress());
        landlord.setEmail(landlordRequestDto.email());
        landlord.setLoginPassword(passwordEncoder.encode(landlordRequestDto.loginPassword()));

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
                landlord.getFirstName(),
                landlord.getMiddleName(),
                landlord.getLastName(),
                landlord.getIdentificationType(),
                landlord.getIdentificationNumber(),
                landlord.getPhoneNumber(),
                landlord.getSecondPhoneNumber(),
                landlord.getPhysicalAddress(),
                landlord.getEmail()
        );
    }
}
