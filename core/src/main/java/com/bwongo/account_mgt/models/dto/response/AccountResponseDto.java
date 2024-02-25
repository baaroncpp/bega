package com.bwongo.account_mgt.models.dto.response;

import com.bwongo.base.models.enums.AccountStatus;
import com.bwongo.base.models.enums.AccountType;
import com.bwongo.user_mgt.models.dto.response.UserMetaResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserResponseDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/13/23
 **/
public record AccountResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto  createdBy,
        UserMetaResponseDto userMeta,
        String accountNumber,
        AccountStatus accountStatus,
        AccountType accountType,
        BigDecimal availableBalance,
        Date activatedOn,
        UserResponseDto activatedBy,
        Date suspendedOn,
        UserResponseDto suspendedBy,
        Date closedOn,
        UserResponseDto closedBy
) {
}
