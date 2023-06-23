package com.bwongo.tenant_mgt.models.dto.requests;

import com.bwongo.apartment_mgt.utils.ApartmentUtil;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.user_mgt.util.UserMgtUtils;

import java.util.Date;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.INVALID_DATE;
import static com.bwongo.base.utils.BaseEnumValidation.isValidIdentificationType;
import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.*;
import static com.bwongo.tenant_mgt.utils.EnumValidationUtil.isValidOccupationStatus;
import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.*;
import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/22/23
 **/
public record TenantRequestDto(
        String username,
        String firstName,
        String lastName,
        String middleName,
        String identificationNumber,
        String identificationType,
        String phoneNumber,
        String phoneNumber2,
        String email,
        String occupationStatus,
        String occupationLocation,
        String occupationContactPhone,
        String emergencyContactName,
        String emergencyContactPhone,
        String gender,
        String birthDate,
        Long countryId,
        String password
) {
    public void validate(){
        Validate.notEmpty(username, NULL_USERNAME);
        Validate.notEmpty(firstName, NULL_FIRST_NAME);
        Validate.isTrue(firstName.length() > 3, ExceptionType.BAD_REQUEST, FIRST_NAME_TOO_SHORT);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitiveAndOneSpace(firstName, INVALID_FIRST_NAME);
        Validate.notEmpty(lastName, NULL_LAST_NAME);
        Validate.isTrue(lastName.length() > 3, ExceptionType.BAD_REQUEST, LAST_NAME_TOO_SHORT);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitiveAndOneSpace(lastName, INVALID_FIRST_NAME);
        Validate.notEmpty(identificationNumber, NULL_IDENTIFICATION_NUMBER);
        Validate.notEmpty(identificationType, NULL_IDENTIFICATION_TYPE);
        Validate.isTrue(isValidIdentificationType(identificationType), ExceptionType.BAD_REQUEST, INVALID_IDENTIFICATION_TYPE);
        Validate.notEmpty(phoneNumber, NULL_PHONE_NUMBER);
        Validate.notEmpty(phoneNumber, NULL_PHONE_NUMBER_2);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber, INVALID_PHONE_NUMBER, phoneNumber);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber2, INVALID_PHONE_NUMBER, phoneNumber2);
        Validate.notEmpty(email, NULL_EMAIL);
        StringRegExUtil.stringOfEmail(email, INVALID_EMAIL);
        Validate.notEmpty(occupationStatus, NULL_OCCUPATION_STATUS);
        Validate.isTrue(isValidOccupationStatus(occupationStatus), ExceptionType.BAD_REQUEST, INVALID_OCCUPATION_STATUS);
        Validate.notEmpty(emergencyContactName, NULL_EMERGENCY_CONTACT_NAME);
        Validate.notEmpty(emergencyContactPhone, NULL_EMERGENCY_CONTACT_PHONE);
        StringRegExUtil.stringOfInternationalPhoneNumber(emergencyContactPhone, INVALID_PHONE_NUMBER, emergencyContactPhone);
        Validate.isTrue(UserMgtUtils.isGender(gender), ExceptionType.BAD_REQUEST, INVALID_GENDER, gender);
        Validate.notNull(birthDate, ExceptionType.BAD_REQUEST, NULL_BIRTH_DATE);
        Validate.isTrue(ApartmentUtil.validateDate(birthDate), ExceptionType.BAD_REQUEST, INVALID_DATE, birthDate);
        Validate.notNull(countryId, ExceptionType.BAD_REQUEST, NULL_COUNTRY_ID);
        StringRegExUtil.stringOfStandardPassword(password, INVALID_PASSWORD);
    }
}
