package com.bwongo.landlord_mgt.model.dto.request;

import com.bwongo.apartment_mgt.utils.ApartmentUtil;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.user_mgt.util.UserMgtUtils;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.INVALID_DATE;
import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.NULL_DISTRICT_ID;
import static com.bwongo.base.utils.BaseEnumValidation.isValidIdentificationType;
import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.landlord_mgt.utils.LandlordMsgConstants.*;
import static com.bwongo.user_mgt.util.UserMsgConstants.INVALID_GENDER;
import static com.bwongo.user_mgt.util.UserMsgConstants.NULL_COUNTRY_ID;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
public record LandlordRequestDto(
         String username,
         String loginPassword,
         String physicalAddress,
         String firstName,
         String middleName,
         String lastName,
         String identificationType,
         String identificationNumber,
         String phoneNumber,
         String phoneNumber2,
         String email,
         Long districtId,
         Long countryId,
         String birthDate,
         String gender
) {
    public void validate(){
        Validate.notEmpty(firstName, NULL_FIRST_NAME);
        Validate.isTrue(firstName.length() > 3, ExceptionType.BAD_REQUEST,SHORT_FIRST_NAME);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitive(firstName, INVALID_FIRST_NAME);
        Validate.notEmpty(lastName, NULL_LAST_NAME);
        Validate.isTrue(lastName.length() > 3, ExceptionType.BAD_REQUEST, SHORT_LAST_NAME);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitive(lastName, INVALID_LAST_NAME);
        Validate.isTrue(isValidIdentificationType(identificationType), ExceptionType.BAD_REQUEST, INVALID_IDENTIFICATION_TYPE);
        Validate.notEmpty(identificationType, NULL_IDENTIFICATION_TYPE);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber, INVALID_PHONE_NUMBER, phoneNumber);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber2, INVALID_PHONE_NUMBER, phoneNumber);
        Validate.notEmpty(physicalAddress, NULL_PHYSICAL_ADDRESS);
        Validate.notEmpty(email, NULL_EMAIL);
        StringRegExUtil.stringOfEmail(email, INVALID_EMAIL);
        Validate.notEmpty(loginPassword, NULL_LOGIN_PASSWORD);
        StringRegExUtil.stringOfStandardPassword(loginPassword, INVALID_PASSWORD);
        Validate.notNull(districtId, ExceptionType.BAD_REQUEST, NULL_DISTRICT_ID);
        Validate.notNull(countryId, ExceptionType.BAD_REQUEST, NULL_COUNTRY_ID);
        Validate.isTrue(UserMgtUtils.isGender(gender), ExceptionType.BAD_REQUEST, INVALID_GENDER, gender);
        Validate.isTrue(ApartmentUtil.validateDate(birthDate), ExceptionType.BAD_REQUEST, INVALID_DATE, birthDate);
    }
}
