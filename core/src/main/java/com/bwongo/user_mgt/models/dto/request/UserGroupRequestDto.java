package com.bwongo.user_mgt.models.dto.request;

import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 **/
public record UserGroupRequestDto(
        String name,
        String note
) {
    public void validate(){
        Validate.notEmpty(name, USER_GROUP_NAME_IS_NULL);
        Validate.notEmpty(note, USER_GROUP_NOTE_IS_NULL);
        StringRegExUtil.stringOfOnlyUpperCase(name, USER_GROUP_NAME_ONLY_UPPER_CASE);
    }
}
