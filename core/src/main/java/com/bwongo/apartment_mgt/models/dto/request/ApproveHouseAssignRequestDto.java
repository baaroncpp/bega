package com.bwongo.apartment_mgt.models.dto.request;

import com.bwongo.apartment_mgt.utils.ApartmentUtil;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/19/23
 **/
public record ApproveHouseAssignRequestDto(
         Long assignHouseId,
         String approvalStatus,
         String note
) {
    public void validate(){
        Validate.notNull(assignHouseId, ExceptionType.BAD_REQUEST, NULL_ASSIGN_HOUSE_ID);
        Validate.notEmpty(approvalStatus, NULL_APPROVAL_STATUS, approvalStatus);
        Validate.isTrue(ApartmentUtil.isApprovalStatus(approvalStatus), ExceptionType.BAD_REQUEST, INVALID_APPROVAL_STATUS);
    }
}
