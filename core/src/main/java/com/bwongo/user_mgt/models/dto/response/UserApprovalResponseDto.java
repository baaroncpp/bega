package com.bwongo.user_mgt.models.dto.response;

import com.bwongo.base.models.enums.ApprovalEnum;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 * @LocalTime 11:23 AM
 **/
public record UserApprovalResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto createdBy,
        UserResponseDto modifiedBy,
        UserResponseDto user,
        ApprovalEnum status
) {
}
