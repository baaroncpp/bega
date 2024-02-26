package com.bwongo.user_mgt.models.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 **/
public record PermissionResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        RoleResponseDto role,
        String name,
        Boolean isAssignable
) {
}
