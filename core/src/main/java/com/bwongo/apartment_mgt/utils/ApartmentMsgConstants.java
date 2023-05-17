package com.bwongo.apartment_mgt.utils;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
public class ApartmentMsgConstants {
    private ApartmentMsgConstants() { }

    public static final String NULL_HOUSE_TYPE_NAME = "name is null or empty";
    public static final String HOUSE_TYPE_NAME_EXISTS = "name: %s exists";
    public static final String HOUSE_TYPE_NOT_FOUND = "HouseType with ID: %s not found";
    public static final String NULL_HOUSE_TYPE_NOTE = "note is null or empty";
    public static final String NULL_APARTMENT_NAME = "apartmentName is null or empty";
    public static final String NULL_APARTMENT_TYPE = "apartmentType is null or empty";
    public static final String INVALID_APARTMENT_TYPE = "%s invalid apartmentType";
    public static final String APARTMENT_NAME_EXISTS = "Apartment with name %s exists";
    public static final String APARTMENT_WITH_ID_NOT_FOUND = "Apartment with ID: %s not found";
    public static final String NULL_LOCATION = "location is null or empty";
    public static final String NULL_LANDLORD_ID = "landlordId is null or empty";
    public static final String NULL_MANAGEMENT_FEE = "managementFee is null or empty";
    public static final String MANAGEMENT_FEE_NOT_ZERO = "managementFee must be greater than 0";
    public static final String MANAGEMENT_FEE_TYPE = "managementFeeType is null or empty";
    public static final String INVALID_MANAGEMENT_FEE_TYPE = "%s invalid managementFeeType";
    public static final String NULL_HOUSE_NUMBER = "houseNumber is null or empty";
    public static final String NULL_HOUSE_TYPE_ID = "houseTypeId is null or empty";
    public static final String NULL_APARTMENT_ID = "apartmentId is null or empty";
    public static final String NULL_RENT_FEE = "rentFee is null or empty";
    public static final String RENT_FEE_NOT_ZERO = "rentFee must be greater than 0";
    public static final String NULL_RENT_PERIOD = "rentPeriod is null or empty";
    public static final String INVALID_RENT_PERIOD = "%s invalid rentPeriod";
    public static final String NULL_RENOVATION_CHARGE = "isRenovationChargeBilled is null or empty";
    public static final String NAME_TOO_SHORT = "name should have more than 3 characters";
    public static final String NAME_ONLY_CHARACTERS = "name should contain only characters";
}
