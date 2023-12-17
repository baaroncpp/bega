package com.bwongo.landlord_mgt.utils;

import com.bwongo.base.models.enums.IdentificationType;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;

import java.util.Map;

import static com.bwongo.base.utils.BaseEnumValidation.isValidIdentificationType;
import static com.bwongo.base.utils.BaseMsgConstants.INVALID_IDENTIFICATION_TYPE;
import static com.bwongo.base.utils.BaseMsgConstants.PASSWORD_CANNOT_BE_UPDATED;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 12/16/23
 **/
public class LandLordUtils {

    private static final String LOGIN_PASSWORD = "loginPassword";
    private static final String IDENTIFICATION_TYPE = "identificationType";

    private LandLordUtils() { }

    public static void checkIfPassedFieldsAreValid(Map<String, Object> fields){

        Validate.isTrue(fields.containsKey(LOGIN_PASSWORD), ExceptionType.ACCESS_DENIED, PASSWORD_CANNOT_BE_UPDATED);

        if(fields.containsKey(IDENTIFICATION_TYPE)){
            Validate.isTrue(isValidIdentificationType(fields.get(IDENTIFICATION_TYPE).toString()), ExceptionType.BAD_REQUEST, INVALID_IDENTIFICATION_TYPE);
            var identificationType = IdentificationType.valueOf(fields.get(IDENTIFICATION_TYPE).toString());
            fields.put(IDENTIFICATION_TYPE, identificationType);
        }



    }
}
