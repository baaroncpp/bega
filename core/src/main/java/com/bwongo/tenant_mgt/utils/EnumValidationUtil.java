package com.bwongo.tenant_mgt.utils;

import com.bwongo.tenant_mgt.models.enums.OccupationStatus;

import java.util.Arrays;
import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/22/23
 **/
public class EnumValidationUtil {

    private EnumValidationUtil() { }

    public static boolean isValidOccupationStatus(String value){
        List<String> occupationStatusList = Arrays.asList(
                OccupationStatus.EMPLOYED.name(),
                OccupationStatus.UNEMPLOYED.name()
        );
        return occupationStatusList.contains(value);
    }
}
