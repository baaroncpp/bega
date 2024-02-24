package com.bwongo.base.utils;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
public class BaseMsgConstants {
    private BaseMsgConstants() { }

    public static final String NULL_PHONE_NUMBER = "phoneNumber is null or empty";
    public static final String NULL_PHONE_NUMBER_2 = "phoneNumber2 is null or empty";
    public static final String INVALID_PHONE_NUMBER = "%s is invalid phoneNumber";
    public static final String NULL_IDENTIFICATION_NUMBER = "identificationNumber is null or empty";
    public static final String NULL_IDENTIFICATION_TYPE = "identificationType is null or empty";
    public static final String NULL_EMAIL = "email is null or empty";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String EMAIL_IS_TAKEN = "email: %s is used by another user";
    public static final String PHONE_NUMBER_TAKEN = "phoneNumber: %s is used by another user";
    public static final String INVALID_IDENTIFICATION_TYPE = "Invalid identificationType";
    public static final String INVALID_PASSWORD = "password must contain a number, a special character and an upper case character";
    public static final String PASSWORD_CANNOT_BE_UPDATED = "Password cannot be updated";
    public static final String FILE_STORAGE_PATH_NOT_FOUND = "File upload path cannot be Empty.";
    public static final String EMPTY_FILE = "failed to store empty file";
    public static final String UNACCEPTED_FILE_TYPE = "unaccepted file type";
}
