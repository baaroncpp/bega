package com.bwongo.account_mgt.service;

import com.bwongo.account_mgt.models.DisbursePayment;
import com.bwongo.account_mgt.models.dto.request.RentPaymentRequestDto;
import com.bwongo.account_mgt.models.dto.response.RentPaymentResponseDto;
import com.bwongo.base.models.enums.TransactionType;
import com.bwongo.account_mgt.models.jpa.Transaction;
import com.bwongo.account_mgt.repository.AccountRepository;
import com.bwongo.account_mgt.repository.RentPaymentRepository;
import com.bwongo.account_mgt.repository.DebtRepository;
import com.bwongo.account_mgt.repository.TransactionRepository;
import com.bwongo.account_mgt.service.dto.AccountDtoService;
import com.bwongo.account_mgt.utils.AccountUtils;
import com.bwongo.base.models.enums.PaymentStatus;
import com.bwongo.base.models.enums.PaymentType;
import com.bwongo.apartment_mgt.repository.HouseRepository;
import com.bwongo.apartment_mgt.utils.ApartmentUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.repository.TenantRepository;
import com.bwongo.base.service.AuditService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.bwongo.account_mgt.utils.AccountMsgConstant.*;
import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;
import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/22/23
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class RentPaymentServiceImp implements RentPaymentService{
    private final TransactionRepository transactionRepository;

    private final Long SYSTEM_COMMISSION_ACCOUNT = 32L; // move to properties file
    private final Long SYSTEM_MAIN_ACCOUNT = 33L; // move to properties file

    private final RentPaymentRepository rentPaymentRepository;
    private final TenantRepository tenantRepository;
    private final HouseRepository houseRepository;
    private final AccountRepository accountRepository;
    private final DebtRepository debtRepository;
    private final AccountDtoService accountDtoService;
    private final AuditService auditService;

    public void momoRentPaymentDeposit(){

    }

    public void cashRentPaymentDeposit(){
        
    }


    @Transactional
    @Override
    public RentPaymentResponseDto makeRentPayment(RentPaymentRequestDto rentPaymentRequestDto) {

        rentPaymentRequestDto.validate();
        var tenantId = rentPaymentRequestDto.tenantId();
        var houseId = rentPaymentRequestDto.houseId();

        var existingTenant = tenantRepository.findById(tenantId);
        Validate.isPresent(existingTenant, TENANT_NOT_FOUND, tenantId);
        final var tenant = existingTenant.get();

        var existingHouse = houseRepository.findById(houseId);
        Validate.isPresent(existingHouse, HOUSE_NOT_FOUND, houseId);
        final var house = existingHouse.get();

        ApartmentUtil.checkIfHouseIsValidForRentPayment(house);

        var paidAmount = rentPaymentRequestDto.amount();

        var rentPayment = accountDtoService.mapRentPaymentRequestDtoToRentPayment(rentPaymentRequestDto);
        rentPayment.setPaymentType(PaymentType.CASH);
        rentPayment.setTenant(tenant);
        rentPayment.setAmount(paidAmount);
        rentPayment.setHouse(house);
        rentPayment.setNote(rentPaymentRequestDto.note());
        rentPayment.setPaymentStatus(PaymentStatus.SUCCESSFUL);

        auditService.stampAuditedEntity(rentPayment);
        rentPaymentRepository.save(rentPayment);

        // move money to tenant account

        var existingTenantAccount = accountRepository.findByUserMeta(tenant.getUserMeta());
        Validate.isPresent(existingTenantAccount, TENANT_ACCOUNT_NOT_FOUND, tenantId);
        final var tenantAccount = existingTenantAccount.get();

        AccountUtils.checkIfAccountCanTransact(tenantAccount);

        var tenantAvailableAccountBalance = tenantAccount.getAvailableBalance();
        BigDecimal paidPlusTenantAvailableAccountBalance = paidAmount.add(tenantAvailableAccountBalance);

        if(tenantAvailableAccountBalance.compareTo(BigDecimal.ZERO) >= 0){

            //TENANT DEPOSIT TRANSACTION
            var tenantDepositTransaction = new Transaction();
            tenantDepositTransaction.setExternalTransactionId(null);
            tenantDepositTransaction.setTransactionType(TransactionType.DEPOSIT);
            tenantDepositTransaction.setAmount(rentPaymentRequestDto.amount());
            tenantDepositTransaction.setAmountAfter(paidPlusTenantAvailableAccountBalance);
            tenantDepositTransaction.setAmountBefore(tenantAvailableAccountBalance);
            tenantDepositTransaction.setAccount(tenantAccount);
            tenantDepositTransaction.setReferenceNumber(AccountUtils.getTransactionReference());
            tenantDepositTransaction.setActive(Boolean.TRUE);
            auditService.stampAuditedEntity(tenantDepositTransaction);
            transactionRepository.save(tenantDepositTransaction);

            if(paidPlusTenantAvailableAccountBalance.compareTo(house.getCurrentPredefinedRentFee()) > 0){
                var disbursePayment = DisbursePayment.builder()
                        .rentPeriod(house.getRentPeriod())
                        .landlordDefinedRentFee(house.getRentFee())
                        .managementPercentage(house.getManagementPercentage())
                        .predefinedRentFee(house.getCurrentPredefinedRentFee())
                        .occupiedUtilDate(house.getOccupiedUntil())
                        .paidAmount(paidPlusTenantAvailableAccountBalance)
                        .build();

                var disbursedPayment = AccountUtils.disbursePayment(disbursePayment);

                /* UPDATE LANDLORD ACCOUNT BALANCE **/
                var existingLandlordAccount = accountRepository.findByUserMeta(house.getApartment().getApartmentOwner().getMetaData());
                Validate.isPresent(existingLandlordAccount, LANDLORD_ACCOUNT_NOT_FOUND, house.getApartment().getApartmentOwner().getId());
                final var landlordAccount = existingLandlordAccount.get();
                AccountUtils.checkIfAccountCanTransact(landlordAccount);

                var landlordAvailableBalance = landlordAccount.getAvailableBalance();
                var landlordUpdatedBalance = landlordAvailableBalance.add(disbursedPayment.getLandlordActualPayout());

                landlordAccount.setAvailableBalance(landlordUpdatedBalance);
                auditService.stampAuditedEntity(landlordAccount);
                accountRepository.save(landlordAccount);

                /* UPDATE TENANT ACCOUNT BALANCE **/
                tenantAccount.setAvailableBalance(disbursedPayment.getTenantResidueAmount());
                auditService.stampAuditedEntity(tenantAccount);
                accountRepository.save(tenantAccount);

                /* UPDATE SYSTEM COMMISSION ACCOUNT **/
                var existingSystemCommissionAccount = accountRepository.findById(SYSTEM_COMMISSION_ACCOUNT);
                Validate.isPresent(existingSystemCommissionAccount, SYSTEM_COMMISSION_ACCOUNT_NOT_FOUND);
                var systemCommissionAccount = existingSystemCommissionAccount.get();
                AccountUtils.checkIfAccountCanTransact(systemCommissionAccount);

                var systemCommissionAvailableBalance = systemCommissionAccount.getAvailableBalance();
                var systemCommissionUpdatedBalance = systemCommissionAvailableBalance.add(disbursedPayment.getCommissionAmount());

                systemCommissionAccount.setAvailableBalance(systemCommissionUpdatedBalance);
                auditService.stampAuditedEntity(systemCommissionAccount);
                accountRepository.save(systemCommissionAccount);

                /* UPDATE SYSTEM CREDIT ACCOUNT **/
                var existingSystemMainAccount = accountRepository.findById(SYSTEM_MAIN_ACCOUNT);
                Validate.isPresent(existingSystemMainAccount, SYSTEM_MAIN_ACCOUNT_NOT_FOUND);
                var systemMainAccount = existingSystemMainAccount.get();
                AccountUtils.checkIfAccountCanTransact(systemMainAccount);

                var systemMainAccountAvailableBalance = systemMainAccount.getAvailableBalance();
                var systemMainAccountUpdatedBalance = systemMainAccountAvailableBalance.add(disbursedPayment.getSystemResidueAmount());
                systemMainAccount.setAvailableBalance(systemMainAccountUpdatedBalance);
                auditService.stampAuditedEntity(systemMainAccount);
                accountRepository.save(systemMainAccount);

                /* UPDATE HOUSE UNTIL DATE **/
                house.setOccupiedUntil(disbursedPayment.getUpdatedOccupiedUntilDate());
                auditService.stampAuditedEntity(house);
                houseRepository.save(house);
            }else{
                tenantAccount.setAvailableBalance(paidPlusTenantAvailableAccountBalance);
                auditService.stampAuditedEntity(tenantAccount);
                accountRepository.save(tenantAccount);
            }

        }

        return null;
    }

    @Override
    public List<RentPaymentResponseDto> getPaymentsByTenantId(Long tenantId, Pageable pageable) {
        return null;
    }

    @Override
    public List<RentPaymentResponseDto> getPayments(Pageable pageable) {
        return null;
    }
}
