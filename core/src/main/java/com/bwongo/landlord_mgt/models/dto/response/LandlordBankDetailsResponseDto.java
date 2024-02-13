package com.bwongo.landlord_mgt.models.dto.response;

import com.bwongo.user_mgt.models.dto.response.UserResponseDto;

import java.util.Date;

public record LandlordBankDetailsResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto  createdBy,
        boolean isActive,
        BankDetailsResponseDto bankDetail,
        LandlordResponseDto landlord
) {
}
