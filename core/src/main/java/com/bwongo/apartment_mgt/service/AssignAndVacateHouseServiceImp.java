package com.bwongo.apartment_mgt.service;

import com.bwongo.apartment_mgt.models.dto.request.AssignHouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.response.AssignHouseResponseDto;
import com.bwongo.apartment_mgt.repository.HouseRepository;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;
import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.*;

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

        assignHouseRequestDto.validate();

        final var tenantId = assignHouseRequestDto.tenantId();
        final var houseId = assignHouseRequestDto.houseId();

        var existingTenant = tenantRepository.findById(tenantId);
        var existingHouse = houseRepository.findById(houseId);

        Validate.isPresent(existingTenant, TENANT_NOT_FOUND, tenantId);
        Validate.isPresent(existingHouse, HOUSE_NOT_FOUND, houseId);

        final var tenant = existingTenant.get();
        final var house = existingHouse.get();

        if(house.isRenovationChargeBilled()){

        }

        return null;
    }
}
