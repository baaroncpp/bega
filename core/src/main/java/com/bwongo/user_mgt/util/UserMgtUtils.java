package com.bwongo.user_mgt.util;

import com.bwongo.base.models.enums.GenderEnum;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.user_mgt.models.jpa.TUser;

import java.util.Arrays;
import java.util.List;

import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/7/23
 **/
public class UserMgtUtils {

    private UserMgtUtils() { }

    public static boolean isGender(String value){
        List<String> genderList = Arrays.asList(
                GenderEnum.MALE.name(),
                GenderEnum.FEMALE.name()
        );

        return genderList.contains(value);
    }

    public static void checkThatUserIsAssignable(TUser user){
        Validate.isTrue(user.isApproved(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_NOT_APPROVED, user.getId());
        Validate.isTrue(!user.getDeleted(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_DELETED);
        Validate.isTrue(!user.isAccountExpired(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_EXPIRED);
        Validate.isTrue(!user.isCredentialExpired(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_CREDENTIALS_EXPIRED);
        Validate.isTrue(!user.isAccountLocked(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_LOCKED);
    }
}
