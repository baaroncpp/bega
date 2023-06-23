package com.bwongo.account_mgt.service.dto;

import com.bwongo.account_mgt.models.dto.request.RentPaymentRequestDto;
import com.bwongo.account_mgt.models.dto.response.AccountResponseDto;
import com.bwongo.account_mgt.models.dto.response.RentPaymentResponseDto;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.account_mgt.models.jpa.RentPayment;
import com.bwongo.apartment_mgt.models.jpa.House;
import com.bwongo.apartment_mgt.service.dto.ApartmentDtoService;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.tenant_mgt.service.dto.TenantDtoService;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/22/23
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDtoService {

    private final UserMgtDtoService userMgtDtoService;
    private final TenantDtoService tenantDtoService;
    private final ApartmentDtoService apartmentDtoService;

    public RentPayment mapRentPaymentRequestDtoToRentPayment(RentPaymentRequestDto rentPaymentRequestDto){

        if(rentPaymentRequestDto == null){
            return null;
        }
        Tenant tenant = new Tenant();
        tenant.setId(rentPaymentRequestDto.tenantId());

        House house = new House();
        house.setId(rentPaymentRequestDto.houseId());

        RentPayment rentPayment = new RentPayment();
        rentPayment.setAmount(rentPaymentRequestDto.amount());
        rentPayment.setTenant(tenant);
        rentPayment.setHouse(house);
        rentPayment.setNote(rentPaymentRequestDto.note());

        return rentPayment;
    }

    public RentPaymentResponseDto mapRentPaymentToRentPaymentResponseDto(RentPayment rentPayment){

        if(rentPayment == null){
            return null;
        }

        return new RentPaymentResponseDto(
                rentPayment.getId(),
                rentPayment.getCreatedOn(),
                rentPayment.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(rentPayment.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(rentPayment.getCreatedBy()),
                rentPayment.getAmount(),
                tenantDtoService.mapTenantToTenantResponseDto(rentPayment.getTenant()),
                rentPayment.getPaymentType(),
                apartmentDtoService.mapHouseToHouseResponseDto(rentPayment.getHouse()),
                rentPayment.getPaymentStatus(),
                rentPayment.getNote()
        );
    }

    public AccountResponseDto mapAccountToAccountResponseDto(Account account){

        if(account == null){
            return null;
        }

        return new AccountResponseDto(
                account.getId(),
                account.getCreatedOn(),
                account.getModifiedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(account.getModifiedBy()),
                userMgtDtoService.mapTUserToUserResponseDto(account.getCreatedBy()),
                userMgtDtoService.mapTUserMetaToUserMetaResponseDto(account.getUserMeta()),
                account.getAccountStatus(),
                account.getAccountType(),
                account.getAvailableBalance(),
                account.getActivatedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(account.getActivatedBy()),
                account.getSuspendedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(account.getSuspendedBy()),
                account.getClosedOn(),
                userMgtDtoService.mapTUserToUserResponseDto(account.getClosedBy())
        );
    }
}
