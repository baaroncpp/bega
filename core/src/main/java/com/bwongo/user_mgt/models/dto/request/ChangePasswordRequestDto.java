package com.bwongo.user_mgt.models.dto.request;

import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
public record ChangePasswordRequestDto(
        String newPassword,
        String oldPassword
) {
    public void validate(){
        Validate.notEmpty(newPassword, NULL_NEW_PASSWORD);
        Validate.notEmpty(oldPassword, NULL_OLD_PASSWORD);
        StringRegExUtil.stringOfStandardPassword(newPassword, STANDARD_PASSWORD);
    }
}
