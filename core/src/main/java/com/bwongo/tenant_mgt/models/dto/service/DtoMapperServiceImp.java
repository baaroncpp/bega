package com.bwongo.tenant_mgt.models.dto.service;

import com.bwongo.tenant_mgt.models.dto.requests.TenantRequestDto;
import com.bwongo.tenant_mgt.models.enums.IdentificationType;
import com.bwongo.tenant_mgt.models.enums.OccupationStatus;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/24/23
 **/
@Service
public class DtoMapperServiceImp implements DtoMapperService{

    @Override
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
}
