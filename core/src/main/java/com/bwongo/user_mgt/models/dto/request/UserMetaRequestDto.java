package com.bwongo.user_mgt.models.dto.request;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/7/23
 **/
public record UserMetaRequestDto(
        String firstName,
        String lastName,
        String middleName,
        String phoneNumber,
        String phoneNumber2,
        String displayName,
        String gender,
        Date birthDate,
        String email,
        Long countryId,
        String identificationType,
        String identificationNumber
) {
}
