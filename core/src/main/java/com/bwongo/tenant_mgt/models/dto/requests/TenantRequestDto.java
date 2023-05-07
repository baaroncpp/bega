package com.bwongo.tenant_mgt.models.dto.requests;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.base.utils.BaseEnumValidation.isValidIdentificationType;
import static com.bwongo.base.utils.BaseMsgConstants.*;
import static com.bwongo.tenant_mgt.utils.EnumValidationUtil.isValidOccupationStatus;
import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/22/23
 **/
public record TenantRequestDto(
        String tenantFullName,
        String identificationNumber,
        String identificationType,
        String phoneNumber,
        String email,
        String occupationStatus,
        String occupationLocation,
        String occupationContactPhone,
        String emergencyContactName,
        String emergencyContactPhone) {
    public void validate(){
        Validate.notEmpty(tenantFullName, NULL_TENANT_NAME);
        Validate.isTrue(tenantFullName.length() > 6, ExceptionType.BAD_REQUEST, FULL_TENANT_NAME_TOO_SHORT);
        StringRegExUtil.stringOfOnlyCharsNoneCaseSensitiveAndOneSpace(tenantFullName, INVALID_FULL_TENANT_NAME);
        Validate.notEmpty(identificationNumber, NULL_IDENTIFICATION_NUMBER);
        Validate.notEmpty(identificationType, NULL_IDENTIFICATION_TYPE);
        Validate.isTrue(isValidIdentificationType(identificationType), ExceptionType.BAD_REQUEST, INVALID_IDENTIFICATION_TYPE);
        Validate.notEmpty(phoneNumber, NULL_PHONE_NUMBER);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber, INVALID_PHONE_NUMBER, phoneNumber);
        Validate.notEmpty(email, NULL_EMAIL);
        StringRegExUtil.stringOfEmail(email, INVALID_EMAIL);
        Validate.notEmpty(occupationStatus, NULL_OCCUPATION_STATUS);
        Validate.isTrue(isValidOccupationStatus(occupationStatus), ExceptionType.BAD_REQUEST, INVALID_OCCUPATION_STATUS);
        Validate.notEmpty(emergencyContactName, NULL_EMERGENCY_CONTACT_NAME);
        Validate.notEmpty(emergencyContactPhone, NULL_EMERGENCY_CONTACT_PHONE);
        StringRegExUtil.stringOfInternationalPhoneNumber(emergencyContactPhone, INVALID_PHONE_NUMBER, emergencyContactPhone);
    }
}
