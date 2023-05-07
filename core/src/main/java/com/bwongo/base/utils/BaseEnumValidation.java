package com.bwongo.base.utils;

import com.bwongo.base.model.enums.IdentificationType;

import java.util.Arrays;
import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
public class BaseEnumValidation {

    private BaseEnumValidation() { }

    public static boolean isValidIdentificationType(String value){
        List<String> identificationTypes = Arrays.asList(
                IdentificationType.DRIVING_LICENSE.name(),
                IdentificationType.OTHER.name(),
                IdentificationType.PASSPORT.name(),
                IdentificationType.NATIONAL_ID.name()
        );
        return identificationTypes.contains(value);
    }
}
