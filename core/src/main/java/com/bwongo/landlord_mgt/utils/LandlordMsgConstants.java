package com.bwongo.landlord_mgt.utils;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
public class LandlordMsgConstants {
    private LandlordMsgConstants() { }

    public static final String NULL_FIRST_NAME = "firstName is null or empty";
    public static final String NULL_USERNAME = "username is null or empty";
    public static final String USERNAME_IS_TAKEN = "username %s is used by another landlord";
    public static final String SHORT_FIRST_NAME = "firstName should have more than 3 characters";
    public static final String INVALID_FIRST_NAME = "firstName should contain only characters";
    public static final String NULL_LAST_NAME = "lastName is null or empty";
    public static final String SHORT_LAST_NAME = "lastName should have more than 3 characters";
    public static final String INVALID_LAST_NAME = "lastName should contain only characters";
    public static final String NULL_PHYSICAL_ADDRESS = "physicalAddress is null or empty";
    public static final String NULL_LOGIN_PASSWORD = "loginPassword is null or empty";
    public static final String LANDLORD_WITH_ID_EXISTS = "Landlord with ID: %s already exists";
    public static final String NEXT_OF_KIN_WITH_ID_EXISTS = "NextOfKin with ID: %s already exists";
    public static final String LANDLORD_NOT_FOUND = "Landlord with ID: %s not found";
    public static final String LANDLORD_IS_INACTIVE = "Landlord is inactive";
    public static final String LANDLORD_IS_ACTIVE = "Landlord is active";
    public static final String NULL_TIN = "tin is null or empty";
    public static final String INVALID_TIN_LENGTH = "tin must be 10 digits";
    public static final String INVALID_TIN = "tin must be 10 digits";
    public static final String NULL_BANK_NAME = "bankName is null or empty";
    public static final String NULL_ACCOUNT_NAME = "accountName is null or empty";
    public static final String NULL_ACCOUNT_NUMBER = "accountNumber is null or empty";
    public static final String BANK_DETAILS_WITH_NAME_AND_NUMBER_EXIST = "Bank details with accountName : %s and accountNumber : %s already exists";
    public static final String LANDLORD_BANK_DETAIL_NOT_FOUND = "Landlord bankDetail with ID: %s not found";
    public static final String LANDLORD_NEXT_OF_KIN_NOT_FOUND = "Landlord nextOfKin with ID: %s not found";
    public static final String LANDLORD_BANK_DETAIL_ALREADY_ACTIVE = "Landlord bankDetail with ID: %s already active";
    public static final String LANDLORD_BANK_DETAIL_ALREADY_DE_ACTIVE = "Landlord bankDetail with ID: %s already de-active";
}

