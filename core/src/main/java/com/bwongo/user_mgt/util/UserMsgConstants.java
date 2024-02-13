package com.bwongo.user_mgt.util;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/7/23
 **/
public class UserMsgConstants {

    private UserMsgConstants() { }

    public static final String INVALID_GENDER = "%s is invalid Gender";
    public static final String NULL_BIRTH_DATE = "birthDate is NULL or Empty";
    public static final String NULL_COUNTRY_ID = "countryId is NULL";
    public static final String COUNTRY_NOT_FOUND = "country with ID: %s not found";
    public static final String DISTRICT_NOT_FOUND = "district with ID: %s not found";
    public static final String NULL_SECOND_NAME = "secondName is NULL or Empty";
    public static final String INVALID_SECOND_NAME = "secondName should contain only characters";
    public static final String SHORT_SECOND_NAME = "secondName should have more that 3 characters";
    public static final String NULL_RELATION_SHIP_TYPE = "relationShipType is NULL or Empty";
    public static final String INVALID_RELATION_SHIP_TYPE = "invalid relationShipType, Options : HUSBAND, WIFE, SON, DAUGHTER, FATHER, MOTHER, GRANDFATHER, GRANDMOTHER, UNCLE, AUNT, SISTER, BROTHER, COUSIN, NEPHEW, NIECE, OTHERS";
    public static final String NULL_RELATION_SHIP_NOTE = "relationShipNote is NULL or Empty";
    public static final String NULL_ID_NUMBER = "idNumber  is NULL or Empty";
}
