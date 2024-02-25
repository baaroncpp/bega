package com.bwongo.base.models.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
public record CountryResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String name,
        String isoAlpha2,
        String isoAlpha3,
        Integer countryCode
) {
}
