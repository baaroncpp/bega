package com.bwongo.base.service;

import com.bwongo.base.models.dto.response.CountryResponseDto;
import com.bwongo.base.models.dto.response.DistrictResponseDto;
import com.bwongo.base.models.jpa.TCountry;
import com.bwongo.base.models.jpa.TDistrict;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
@Service
public class BaseDtoService {

    public CountryResponseDto countryToDto(TCountry country){

        if(country == null){
            return null;
        }

        return new CountryResponseDto(
                country.getId(),
                country.getCreatedOn(),
                country.getModifiedOn(),
                country.getName(),
                country.getIsoAlpha2(),
                country.getIsoAlpha3(),
                country.getCountryCode()
        );
    }

    public DistrictResponseDto districtToDto(TDistrict district){

        if(district == null){
            return null;
        }

        return new DistrictResponseDto(
                district.getId(),
                district.getCreatedOn(),
                district.getModifiedOn(),
                countryToDto(district.getCountry()),
                district.getName(),
                district.getRegion()
        );
    }
}
