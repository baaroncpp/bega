package com.bwongo.apartment_mgt.service;

import com.bwongo.apartment_mgt.models.dto.request.AssignHouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.response.AssignHouseResponseDto;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/13/23
 **/
public interface AssignAndVacateHouseService {
    AssignHouseResponseDto assignHouse(AssignHouseRequestDto assignHouseRequestDto);
}
