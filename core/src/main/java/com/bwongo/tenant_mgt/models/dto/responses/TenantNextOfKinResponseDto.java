package com.bwongo.tenant_mgt.models.dto.responses;

import com.bwongo.user_mgt.models.dto.response.NextOfKinResponseDto;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/24/24
 **/
public record TenantNextOfKinResponseDto(
        TenantResponseDto tenant,
        NextOfKinResponseDto nextOfKin
) {
}
