package com.bwongo.landlord_mgt.models.dto.response;

import com.bwongo.user_mgt.models.dto.response.UserResponseDto;

import java.util.Date;

public record BankDetailsResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto modifiedBy,
        UserResponseDto  createdBy,
        boolean isActive,
        String bankName,
        String accountName,
        String accountNumber
) {
}
