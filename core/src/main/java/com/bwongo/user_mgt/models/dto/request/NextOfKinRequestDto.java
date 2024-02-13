package com.bwongo.user_mgt.models.dto.request;

import com.bwongo.apartment_mgt.utils.ApartmentUtil;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.INVALID_DATE;
import static com.bwongo.base.utils.BaseEnumValidation.isRelationShipType;
import static com.bwongo.base.utils.BaseEnumValidation.isValidIdentificationType;
import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.*;
import static com.bwongo.user_mgt.util.UserMsgConstants.*;

public record NextOfKinRequestDto(
        String firstName,
        String secondName,
        String birthDate,
        String phoneNumber,
        String phoneNumber2,
        String relationShipType,
        String relationShipNote,
        String email,
        String identificationType,
        String idNumber
) {
    public void validate(){
        Validate.notEmpty(firstName, NULL_FIRST_NAME);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitive(firstName, INVALID_FIRST_NAME);
        Validate.isTrue(firstName.length() > 3, ExceptionType.BAD_REQUEST, SHORT_FIRST_NAME);
        Validate.notEmpty(secondName, NULL_SECOND_NAME);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitive(secondName, INVALID_SECOND_NAME);
        Validate.isTrue(secondName.length() > 3, ExceptionType.BAD_REQUEST, SHORT_SECOND_NAME);
        Validate.notEmpty(birthDate, NULL_BIRTH_DATE);
        Validate.isTrue(ApartmentUtil.validateDate(birthDate), ExceptionType.BAD_REQUEST, INVALID_DATE, birthDate);
        Validate.notEmpty(identificationType, NULL_IDENTIFICATION_TYPE);
        Validate.notEmpty(phoneNumber, NULL_PHONE_NUMBER);
        Validate.notEmpty(phoneNumber2, NULL_PHONE_NUMBER_2);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber, INVALID_PHONE_NUMBER, phoneNumber);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber2, INVALID_PHONE_NUMBER, phoneNumber2);
        Validate.notEmpty(relationShipType, NULL_RELATION_SHIP_TYPE);
        Validate.isTrue(isRelationShipType(relationShipType), ExceptionType.BAD_REQUEST, INVALID_RELATION_SHIP_TYPE);
        Validate.notEmpty(relationShipNote, NULL_RELATION_SHIP_NOTE);
        Validate.notEmpty(email, NULL_EMAIL);
        StringRegExUtil.stringOfEmail(email, INVALID_EMAIL);
        Validate.notEmpty(identificationType, NULL_IDENTIFICATION_TYPE);
        Validate.isTrue(isValidIdentificationType(identificationType), ExceptionType.BAD_REQUEST, INVALID_IDENTIFICATION_TYPE);
        Validate.notEmpty(idNumber, NULL_ID_NUMBER);
    }
}
