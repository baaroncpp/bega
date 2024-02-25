package com.bwongo.tenant_mgt.models.dto.responses;

import com.bwongo.base.models.enums.OccupationStatus;
import com.bwongo.base.models.enums.TenantStatus;
import com.bwongo.user_mgt.models.dto.response.UserMetaResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserResponseDto;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/5/23
 **/
public record TenantResponseDto(
        Long id,
        String username,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto createdBy,
        boolean isActive,
        OccupationStatus occupationStatus,
        String occupationLocation,
        String occupationContactPhone,
        String emergencyContactName,
        String emergencyContactPhone,
        TenantStatus tenantStatus,
        UserMetaResponseDto userMeta,
        String tenantAgreementPath
        ) {
}
