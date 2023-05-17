package com.bwongo.tenant_mgt.service.dto;

import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.base.model.enums.IdentificationType;
import com.bwongo.tenant_mgt.models.dto.responses.TenantResponseDto;
import com.bwongo.tenant_mgt.models.enums.OccupationStatus;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/24/23
 **/
@Service
@RequiredArgsConstructor
public class TenantDtoService {

    private final UserMgtDtoService userMgtDtoService;

    public Tenant mapTenantRequestDtoToTenant(TenantRequestDto tenantRequestDto){

        if(tenantRequestDto == null){
            return null;
        }

        Tenant tenant = new Tenant();
        tenant.setTenantFullName(tenantRequestDto.tenantFullName());
        tenant.setIdentificationType(IdentificationType.valueOf(tenantRequestDto.identificationType()));
        tenant.setIdentificationNumber(tenantRequestDto.identificationNumber());
        tenant.setPhoneNumber(tenantRequestDto.phoneNumber());
        tenant.setEmail(tenantRequestDto.email());
        tenant.setOccupationStatus(OccupationStatus.valueOf(tenantRequestDto.occupationStatus()));
        tenant.setOccupationLocation(tenantRequestDto.occupationLocation());
        tenant.setOccupationContactPhone(tenantRequestDto.occupationContactPhone());
        tenant.setEmergencyContactName(tenantRequestDto.emergencyContactName());
        tenant.setEmergencyContactPhone(tenantRequestDto.emergencyContactPhone());

        return tenant;
    }

    public TenantResponseDto mapTenantToTenantResponseDto(Tenant tenant) {

        if(tenant == null) {
            return null;
        }

        return new TenantResponseDto(
                tenant.getId(),
                tenant.getCreatedOn(),
                tenant.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(tenant.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(tenant.getCreatedBy()),
                tenant.isActive(),
                tenant.getTenantFullName(),
                tenant.getIdentificationNumber(),
                tenant.getIdentificationType(),
                tenant.getPhoneNumber(),
                tenant.getEmail(),
                tenant.getOccupationStatus(),
                tenant.getOccupationLocation(),
                tenant.getOccupationContactPhone(),
                tenant.getEmergencyContactName(),
                tenant.getEmergencyContactPhone(),
                tenant.getTenantStatus()
        );
    }
}
