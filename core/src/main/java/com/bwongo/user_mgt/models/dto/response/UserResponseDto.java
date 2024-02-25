package com.bwongo.user_mgt.models.dto.response;

import com.bwongo.base.models.enums.UserTypeEnum;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/5/23
 **/
public record UserResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String username,
        boolean approved,
        UserGroupResponseDto userGroup,
        UserTypeEnum userType,
        Long userMetaId
) {
}
