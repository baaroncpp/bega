package com.bwongo.user_mgt.util;

import com.bwongo.user_mgt.models.enums.GenderEnum;

import java.util.Arrays;
import java.util.List;

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
}
