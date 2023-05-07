package com.bwongo.user_mgt.models.dto;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/5/23
 **/
public record UserGroupResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String name,
        String note
) {
}
