package com.bwongo.account_mgt.service;

import com.bwongo.account_mgt.models.dto.request.RentPaymentRequestDto;
import com.bwongo.account_mgt.models.dto.response.RentPaymentResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/22/23
 **/
public interface RentPaymentService {
    RentPaymentResponseDto makeRentPayment(RentPaymentRequestDto rentPaymentRequestDto);
    List<RentPaymentResponseDto> getPaymentsByTenantId(Long tenantId, Pageable pageable);
    List<RentPaymentResponseDto> getPayments(Pageable pageable);
}
