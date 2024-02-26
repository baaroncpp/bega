package com.bwongo.user_mgt.models.dto.request;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.INVALID_APPROVAL_STATUS;
import static com.bwongo.apartment_mgt.utils.ApartmentUtil.isApprovalStatus;
import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 * @LocalTime 11:24 AM
 **/
public record UserApprovalRequestDto(
        Long id,
        Long userId,
        String status
) {
    public void validate(){
        Validate.notNull(userId, ExceptionType.BAD_REQUEST, USER_ID_REQUIRED);
        Validate.notEmpty(status, APPROVAL_STATUS_REQUIRED);
        Validate.isTrue(isApprovalStatus(status), ExceptionType.BAD_REQUEST, INVALID_APPROVAL_STATUS);
    }
}
