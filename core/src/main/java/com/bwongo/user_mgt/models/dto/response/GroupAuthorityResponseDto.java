package com.bwongo.user_mgt.models.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 **/
public record GroupAuthorityResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserGroupResponseDto userGroup,
        PermissionResponseDto permission
) {
}
