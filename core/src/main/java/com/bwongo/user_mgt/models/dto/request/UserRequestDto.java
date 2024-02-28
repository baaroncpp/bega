package com.bwongo.user_mgt.models.dto.request;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.base.utils.BaseEnumValidation.isUserType;
import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
public record UserRequestDto(
        String username,
        String password,
        Long userGroupId,
        String userType
) {
    public void validate(){
        Validate.notEmpty(username, USERNAME_REQUIRED);
        Validate.notEmpty(password, PASSWORD_REQUIRED);
        Validate.notNull(userGroupId, ExceptionType.BAD_REQUEST, USER_GROUP_ID_REQUIRED);
        Validate.notNull(userType, ExceptionType.BAD_REQUEST, USER_TYPE_REQUIRED);
        Validate.isTrue(isUserType(userType), ExceptionType.BAD_REQUEST, VALID_USER_TYPE);
        StringRegExUtil.stringOfOnlyNumbersAndChars(username, USERNAME_SHOULD_CONTAIN_ONLY_CHARS_AND_NUMBERS);
        StringRegExUtil.stringOfStandardPassword(password, STANDARD_PASSWORD);
    }

    public void validateUpdate(){
        Validate.notEmpty(username, USERNAME_REQUIRED);
        Validate.notNull(userGroupId, ExceptionType.BAD_REQUEST, USER_GROUP_ID_REQUIRED);
        Validate.notNull(userType, ExceptionType.BAD_REQUEST, USER_TYPE_REQUIRED);
        Validate.isTrue(isUserType(userType), ExceptionType.BAD_REQUEST, VALID_USER_TYPE);
        StringRegExUtil.stringOfOnlyNumbersAndChars(username, USERNAME_SHOULD_CONTAIN_ONLY_CHARS_AND_NUMBERS);
    }
}
