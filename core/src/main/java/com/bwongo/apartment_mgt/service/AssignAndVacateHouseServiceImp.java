package com.bwongo.apartment_mgt.service;

import com.bwongo.apartment_mgt.models.dto.request.AssignHouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.response.AssignHouseResponseDto;
import com.bwongo.apartment_mgt.repository.HouseRepository;
import com.bwongo.tenant_mgt.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/13/23
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class AssignAndVacateHouseServiceImp implements AssignAndVacateHouseService{

    private final TenantRepository tenantRepository;
    private final HouseRepository houseRepository;

    @Override
    public AssignHouseResponseDto assignHouse(AssignHouseRequestDto assignHouseRequestDto) {
        return null;
    }
}
