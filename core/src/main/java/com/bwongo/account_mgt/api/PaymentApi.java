package com.bwongo.account_mgt.api;

import com.bwongo.account_mgt.models.dto.request.RentPaymentRequestDto;
import com.bwongo.account_mgt.models.dto.response.RentPaymentResponseDto;
import com.bwongo.account_mgt.service.RentPaymentService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 7/14/23
 **/
@RestController
@RequestMapping(path = "v1/api")
@RequiredArgsConstructor
public class PaymentApi {

    private final RentPaymentService rentPaymentService;

    @PreAuthorize("hasAuthority('ADMIN_ROLE.WRITE')")
    @PostMapping(path = "pay-rent", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public RentPaymentResponseDto makeRentPayment(@RequestBody RentPaymentRequestDto rentPaymentRequestDto){
        return rentPaymentService.makeRentPayment(rentPaymentRequestDto);
    }
}
