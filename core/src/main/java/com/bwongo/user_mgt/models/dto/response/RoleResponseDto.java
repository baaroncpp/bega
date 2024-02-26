package com.bwongo.user_mgt.models.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 **/
public record RoleResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String name,
        String note
) {
}
