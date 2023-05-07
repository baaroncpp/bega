package com.bwongo.landlord_mgt.utils;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
public class LandlordMsgConstants {
    private LandlordMsgConstants() { }

    public static final String NULL_FIRST_NAME = "firstName is null or empty";
    public static final String SHORT_FIRST_NAME = "firstName should have more than 6 characters";
    public static final String INVALID_FIRST_NAME = "firstName should contain only characters";
    public static final String NULL_LAST_NAME = "lastName is null or empty";
    public static final String SHORT_LAST_NAME = "lastName should have more than 6 characters";
    public static final String INVALID_LAST_NAME = "lastName should contain only characters";
    public static final String NULL_PHYSICAL_ADDRESS = "physicalAddress is null or empty";
    public static final String NULL_LOGIN_PASSWORD = "loginPassword is null or empty";
    public static final String LANDLORD_WITH_ID_EXISTS = "Landlord with ID: %s already exists";
}

