package com.bwongo.base.models.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
public record DistrictResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        CountryResponseDto country,
        String name,
        String region) {
}
