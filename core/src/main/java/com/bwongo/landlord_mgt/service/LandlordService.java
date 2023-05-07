package com.bwongo.landlord_mgt.service;

import com.bwongo.landlord_mgt.model.dto.request.LandlordRequestDto;
import com.bwongo.landlord_mgt.model.dto.response.LandlordResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
public interface LandlordService {
    LandlordResponseDto addLandLord(LandlordRequestDto landlordRequestDto);
    LandlordResponseDto updateLandLord(Long id, LandlordRequestDto landlordRequestDto);
    LandlordResponseDto getLandLordById(Long id);
    List<LandlordResponseDto> getLandlords(Pageable pageable);
}
