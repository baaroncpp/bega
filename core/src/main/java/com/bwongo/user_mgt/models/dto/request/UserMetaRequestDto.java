package com.bwongo.user_mgt.models.dto.request;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;

import java.util.Date;

import static com.bwongo.base.utils.BaseEnumValidation.isValidIdentificationType;
import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.user_mgt.util.UserMsgConstants.*;
import static com.bwongo.user_mgt.util.UserMsgConstants.INVALID_PHONE_NUMBER;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/7/23
 **/
public record UserMetaRequestDto(
        String firstName,
        String lastName,
        String middleName,
        String phoneNumber,
        String phoneNumber2,
        String displayName,
        String gender,
        Date birthDate,
        String email,
        Long countryId,
        String identificationType,
        String identificationNumber
) {
    public void validate(){
        Validate.notEmpty(lastName, LAST_NAME_REQUIRED);
        Validate.notEmpty(firstName, FIRST_NAME_REQUIRED);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitive(lastName, LAST_NAME_ONLY_CHARACTERS);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitive(lastName, FIRST_NAME_ONLY_CHARACTERS);
        Validate.notEmpty(phoneNumber, PHONE_NUMBER_REQUIRED);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber, INVALID_PHONE_NUMBER);
        Validate.notEmpty(email, EMAIL_REQUIRED);
        StringRegExUtil.stringOfEmail(email, INVALID_EMAIL);
        Validate.notEmpty(identificationNumber, IDENTIFICATION_NUMBER_REQUIRED);
        Validate.notNull(countryId, ExceptionType.BAD_REQUEST, COUNTRY_ID_REQUIRED);
        Validate.notNull(birthDate, ExceptionType.BAD_REQUEST, DOB_REQUIRED);
        Validate.notNull(gender, ExceptionType.BAD_REQUEST, GENDER_REQUIRED);
        Validate.notNull(identificationType, ExceptionType.BAD_REQUEST, IDENTIFICATION_TYPE_REQUIRED);
        Validate.isTrue(isValidIdentificationType(identificationType), ExceptionType.BAD_REQUEST, INVALID_IDENTIFICATION_TYPE);

        if(!phoneNumber2.isEmpty()){
            StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber, INVALID_SECOND_PHONE_NUMBER);
        }
    }
}
