package com.bwongo.commons.models.utils;

import com.bwongo.commons.models.exceptions.BadRequestException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static com.bwongo.commons.models.utils.ConstantMessages.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
public class Validate {
    private Validate() {}

    public static void isTrue(boolean value, String message, Object ... params){
        if (!value) {
            throw new BadRequestException(message, params);
        }
    }

    public static void notNull(Object value, String message, Object ... params){

        if (value == null) {
            throw new BadRequestException(message, params);
        }
    }

    public static void notEmpty(String value, String message, Object ... params){
        if (!StringUtils.hasLength(value)) {
            throw new BadRequestException(message, (Object) params);
        }
    }

    public static void isPresent(Optional<?> value, String message, Object ... params){
        if(value.isEmpty()){
            throw new BadRequestException(String.format(message,params));
        }
    }

    private static void checkForbiddenWord(String word){
        boolean anyMatch = FORBIDDEN_WORDS.stream()
                .anyMatch(fw -> fw.contains(word));

        if(anyMatch){
            throw new BadRequestException(String.format(IS_FORBIDDEN_WORD, word));
        }
    }

    public static void doesObjectContainFields(Object object, List<String> fields){
        for(String value : fields){
            Validate.isTrue(doesObjectContainField(object, value), String.format(IS_INVALID_FIELD, value));
        }
    }

    public static boolean doesObjectContainField(Object object, String fieldName) {
        Class<?> objectClass = object.getClass();
        for (Field field : objectClass.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
