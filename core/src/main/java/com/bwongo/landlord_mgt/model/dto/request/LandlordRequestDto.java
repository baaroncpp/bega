package com.bwongo.landlord_mgt.model.dto.request;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.base.utils.BaseEnumValidation.isValidIdentificationType;
import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
public record LandlordRequestDto(
         String firstName,
         String middleName,
         String lastName,
         String identificationType,
         String identificationNumber,
         String phoneNumber,
         String secondPhoneNumber,
         String physicalAddress,
         String email,
         String loginPassword
) {
    public void validate(){
        Validate.notEmpty(firstName, NULL_FIRST_NAME);
        Validate.isTrue(firstName.length() < 4, ExceptionType.BAD_CREDENTIALS,SHORT_FIRST_NAME);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitive(firstName, INVALID_FIRST_NAME);
        Validate.notEmpty(lastName, NULL_LAST_NAME);
        Validate.isTrue(lastName.length() < 4, ExceptionType.BAD_CREDENTIALS, SHORT_LAST_NAME);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitive(lastName, INVALID_LAST_NAME);
        Validate.isTrue(isValidIdentificationType(identificationType), ExceptionType.BAD_REQUEST, INVALID_IDENTIFICATION_TYPE);
        Validate.notEmpty(identificationType, NULL_IDENTIFICATION_TYPE);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber, INVALID_PHONE_NUMBER, phoneNumber);
        StringRegExUtil.stringOfInternationalPhoneNumber(secondPhoneNumber, INVALID_PHONE_NUMBER, secondPhoneNumber);
        Validate.notEmpty(physicalAddress, NULL_PHYSICAL_ADDRESS);
        Validate.notEmpty(email, NULL_EMAIL);
        StringRegExUtil.stringOfEmail(email, INVALID_EMAIL);
        Validate.notEmpty(loginPassword, NULL_LOGIN_PASSWORD);
        StringRegExUtil.stringOfStandardPassword(loginPassword, INVALID_PASSWORD);
    }
}
