package com.bwongo.tenant_mgt.models.dto.responses;

import com.bwongo.base.model.enums.IdentificationType;
import com.bwongo.tenant_mgt.models.enums.OccupationStatus;
import com.bwongo.user_mgt.models.dto.UserResponseDto;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/5/23
 **/
public record TenantResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto createdBy,
        boolean isActive,
        String tenantFullName,
        String identificationNumber,
        IdentificationType identificationType,
        String phoneNumber,
        String email,
        OccupationStatus occupationStatus,
        String occupationLocation,
        String occupationContactPhone,
        String emergencyContactName,
        String emergencyContactPhone
        ) {
}
