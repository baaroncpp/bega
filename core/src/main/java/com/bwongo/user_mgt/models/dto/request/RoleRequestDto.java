package com.bwongo.user_mgt.models.dto.request;

import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 **/
public record RoleRequestDto(String name,
                             String note
) {
    public void validate(){
        Validate.notEmpty(name, ROLE_NAME_REQUIRED);
        Validate.notEmpty(note, ROLE_DESCRIPTION_REQUIRED);
    }
}
