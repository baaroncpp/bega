package com.bwongo.tenant_mgt.service.dto;

import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.base.model.enums.IdentificationType;
import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import com.bwongo.tenant_mgt.models.enums.OccupationStatus;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.user_mgt.models.enums.GenderEnum;
import com.bwongo.user_mgt.models.jpa.TCountry;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.PLACEMENT_DATE_FORMAT;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/24/23
 **/
@Service
@RequiredArgsConstructor
public class TenantDtoService {

    private final UserMgtDtoService userMgtDtoService;
    private final PasswordEncoder passwordEncoder;

    public Tenant mapTenantRequestDtoToTenant(TenantRequestDto tenantRequestDto){

        if(tenantRequestDto == null){
            return null;
        }
        var country = new TCountry();
        country.setId(tenantRequestDto.countryId());

        var displayName = new StringBuilder();
        displayName
                .append(tenantRequestDto.firstName())
                .append(" ")
                .append(tenantRequestDto.lastName());

        var userMeta = new TUserMeta();
        userMeta.setGender(GenderEnum.valueOf(tenantRequestDto.gender()));
        userMeta.setPhoneNumber(tenantRequestDto.phoneNumber());
        userMeta.setPhoneNumber2(tenantRequestDto.phoneNumber2());
        userMeta.setEmail(tenantRequestDto.email());
        userMeta.setIdentificationType(IdentificationType.valueOf(tenantRequestDto.identificationType()));
        userMeta.setIdentificationNumber(tenantRequestDto.identificationNumber());
        userMeta.setBirthDate(DateTimeUtil.stringToDate(tenantRequestDto.birthDate(), PLACEMENT_DATE_FORMAT));
        userMeta.setCountry(country);
        userMeta.setFirstName(tenantRequestDto.firstName());
        userMeta.setLastName(tenantRequestDto.lastName());
        userMeta.setMiddleName(tenantRequestDto.middleName());
        userMeta.setDisplayName(displayName.toString());

        Tenant tenant = new Tenant();
        tenant.setOccupationStatus(OccupationStatus.valueOf(tenantRequestDto.occupationStatus()));
        tenant.setOccupationLocation(tenantRequestDto.occupationLocation());
        tenant.setOccupationContactPhone(tenantRequestDto.occupationContactPhone());
        tenant.setEmergencyContactName(tenantRequestDto.emergencyContactName());
        tenant.setEmergencyContactPhone(tenantRequestDto.emergencyContactPhone());
        tenant.setPassword(passwordEncoder.encode(tenantRequestDto.password()));
        tenant.setUsername(tenantRequestDto.username());
        tenant.setUserMeta(userMeta);

        return tenant;
    }

    public TenantResponseDto mapTenantToTenantResponseDto(Tenant tenant) {

        if(tenant == null) {
            return null;
        }

        return new TenantResponseDto(
                tenant.getId(),
                tenant.getUsername(),
                tenant.getCreatedOn(),
                tenant.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(tenant.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(tenant.getCreatedBy()),
                tenant.isActive(),
                tenant.getOccupationStatus(),
                tenant.getOccupationLocation(),
                tenant.getOccupationContactPhone(),
                tenant.getEmergencyContactName(),
                tenant.getEmergencyContactPhone(),
                tenant.getTenantStatus(),
                userMgtDtoService.mapTUserMetaToUserMetaResponseDto(tenant.getUserMeta())
        );
    }
}
