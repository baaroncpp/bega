package com.bwongo.apartment_mgt.service;

import com.bwongo.account_mgt.models.enums.TransactionStatus;
import com.bwongo.account_mgt.models.enums.TransactionType;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.account_mgt.models.jpa.Transaction;
import com.bwongo.account_mgt.repository.AccountRepository;
import com.bwongo.account_mgt.repository.TransactionRepository;
import com.bwongo.account_mgt.utils.AccountUtils;
import com.bwongo.apartment_mgt.models.dto.request.ApproveHouseAssignRequestDto;
import com.bwongo.apartment_mgt.models.dto.request.AssignHouseRequestDto;
import com.bwongo.apartment_mgt.models.dto.response.ApproveHouseAssignResponseDto;
import com.bwongo.apartment_mgt.models.dto.response.AssignHouseResponseDto;
import com.bwongo.apartment_mgt.models.enums.PaymentStatus;
import com.bwongo.apartment_mgt.models.enums.PaymentType;
import com.bwongo.account_mgt.models.jpa.RentPayment;
import com.bwongo.apartment_mgt.repository.AssignHouseRepository;
import com.bwongo.apartment_mgt.repository.HouseRepository;
import com.bwongo.account_mgt.repository.RentPaymentRepository;
import com.bwongo.apartment_mgt.service.dto.ApartmentDtoService;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import com.bwongo.tenant_mgt.repository.TenantRepository;
import com.bwongo.tenant_mgt.utils.AuditService;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.bwongo.account_mgt.utils.accountMsgConstant.*;
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
    private final RentPaymentRepository rentPaymentRepository;
    private final ApartmentDtoService apartmentDtoService;
    private final AccountRepository accountRepository;
    private final AssignHouseRepository assignHouseRepository;
    private final TransactionRepository transactionRepository;
    private final AuditService auditService;

    private static final Long SYSTEM_COMMISSION_ACCOUNT_ID = 20L;
    private static final Long SYSTEM_MAIN_ACCOUNT_ID = 21L;
    private static final String HOUSE_ASSIGNMENT_PAYMENT_NOTE = "house assignment payment";
    private static final String SYSTEM_COMMISSION = "System Commission";

    @Transactional
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

        var expectedInitialRentPayment = assignHouseRequestDto.predefinedRent().multiply(BigDecimal.valueOf(house.getInitialRentPaymentPeriod()));
        var tenantDepositAmount = assignHouseRequestDto.depositAmount();

        //ADD RENOVATION FEE TO INITIAL RENT PAYMENT
        if(house.isRenovationChargeBilled()){
            expectedInitialRentPayment.add(assignHouseRequestDto.predefinedRent());
        }

        Validate.isTrue(expectedInitialRentPayment.compareTo(tenantDepositAmount) < 0, ExceptionType.BAD_REQUEST, LOW_DEPOSIT_AMOUNT);
        var rentPayed = house.getRentFee().multiply(BigDecimal.valueOf(rentPeriodsPaid(tenantDepositAmount, house.getRentFee())));

        /* ASSIGN HOUSE**/
        var assignHouse = apartmentDtoService.mapAssignHouseRequestDtoToAssignHouse(assignHouseRequestDto);
        assignHouse.setHouse(house);
        assignHouse.setTenant(tenant);
        assignHouse.setExpectedInitialRentPayment(expectedInitialRentPayment);
        assignHouse.setApproved(Boolean.FALSE);
        assignHouse.setRentAmountPaid(rentPayed);

        auditService.stampAuditedEntity(assignHouse);

        /* UPDATE ACCOUNTS **/
        //TENANT ACCOUNT
        final var tenantAccount = getTenantAccountByTenant(tenant);

        AccountUtils.checkIfAccountCanTransact(tenantAccount);
        var tenantCurrentAccountBalance = tenantAccount.getAvailableBalance();
        var tenantUpdatedAccountBalance = tenantCurrentAccountBalance.add(tenantDepositAmount);

        tenantAccount.setAvailableBalance(tenantUpdatedAccountBalance);
        auditService.stampAuditedEntity(tenantAccount);

        /* TENANT DEPOSIT TRANSACTION **/
        Transaction tenantDepositTransaction = Transaction.builder()
                .amount(tenantDepositAmount)
                .amountBefore(tenantCurrentAccountBalance)
                .amountAfter(tenantUpdatedAccountBalance)
                .account(tenantAccount)
                .transactionType(TransactionType.DEPOSIT)
                .transactionStatus(TransactionStatus.SUCCESSFUL)
                .referenceNumber(AccountUtils.getTransactionReference())
                .externalTransactionId(assignHouseRequestDto.receiptNumber())
                .build();

        auditService.stampAuditedEntity(tenantDepositTransaction);

        /* PERSIST ENTITIES*/
        transactionRepository.save(tenantDepositTransaction);
        accountRepository.save(tenantAccount);
        assignHouseRepository.save(assignHouse);

        return apartmentDtoService.mapAssignHouseToAssignHouseResponseDto(assignHouseRepository.save(assignHouse));
    }

    public ApproveHouseAssignResponseDto approveHouseAssignment(ApproveHouseAssignRequestDto approveHouseAssignRequestDto){

        approveHouseAssignRequestDto.validate();
        final var assignHouseId = approveHouseAssignRequestDto.assignHouseId();
        var existingAssignHouse = assignHouseRepository.findById(assignHouseId);
        Validate.isPresent(existingAssignHouse, ASSIGN_HOUSE_NOT_FOUND, assignHouseId);

        var assignHouse = existingAssignHouse.get();

        var house = assignHouse.getHouse();
        var apartment = house.getApartment();
        var tenant = assignHouse.getTenant();

        // IF RENOVATION FEE IS CHARGED THEN IT ALREADY INCLUDED HERE
        var expectedInitialRentPayment = assignHouse.getExpectedInitialRentPayment();
        var tenantDepositAmount = assignHouse.getDepositAmount();
        var landlordExpectedInitialAmount = house.getRentFee().multiply(BigDecimal.valueOf(house.getInitialRentPaymentPeriod()));

        //ADD RENOVATION FEE TO LANDLORD EXPECTED AMOUNT
        if(house.isRenovationChargeBilled()){
            landlordExpectedInitialAmount.add(house.getRentFee());
        }

        if(apartment.isRenovationServiced() && house.isRenovationChargeBilled()){
            landlordExpectedInitialAmount.subtract(house.getRentFee());
        }

        //TENANT ACCOUNT
        final var tenantAccount = getTenantAccountByTenant(tenant);
        AccountUtils.checkIfAccountCanTransact(tenantAccount);

        var tenantUpdatedBalance = tenantAccount.getAvailableBalance().subtract(expectedInitialRentPayment);

        /* TENANT WITHDRAW TRANSACTION **/
        Transaction tenantDepositTransaction = Transaction.builder()
                .amount(tenantDepositAmount)
                .amountBefore(tenantAccount.getAvailableBalance())
                .amountAfter(tenantUpdatedBalance)
                .account(tenantAccount)
                .transactionType(TransactionType.WITHDRAW)
                .transactionStatus(TransactionStatus.SUCCESSFUL)
                .referenceNumber(AccountUtils.getTransactionReference())
                //.externalTransactionId(assignHouseRequestDto.receiptNumber())
                .build();

        //SYSTEM MAIN ACCOUNT
        var existingSystemMainAccount = accountRepository.findById(SYSTEM_MAIN_ACCOUNT_ID);
        Validate.isPresent(existingSystemMainAccount, SYSTEM_MAIN_ACCOUNT_NOT_FOUND);

        var systemMainAccount = existingSystemMainAccount.get();
        AccountUtils.checkIfAccountCanTransact(systemMainAccount);

        //SYSTEM COMMISSION ACCOUNT
        var existingSystemCommissionAccount = accountRepository.findById(SYSTEM_COMMISSION_ACCOUNT_ID);
        Validate.isPresent(existingSystemCommissionAccount, SYSTEM_COMMISSION_ACCOUNT_NOT_FOUND);

        var systemCommissionAccount = existingSystemCommissionAccount.get();
        AccountUtils.checkIfAccountCanTransact(systemCommissionAccount);

        //LANDLORD ACCOUNT
        var landlordUserMeta = house.getApartment().getApartmentOwner().getMetaData();
        var existingLandlordAccount = accountRepository.findByUserMeta(landlordUserMeta);
        Validate.isPresent(existingLandlordAccount, LANDLORD_ACCOUNT_NOT_FOUND, house.getApartment().getApartmentOwner().getId());

        final var landlordAccount = existingLandlordAccount.get();
        AccountUtils.checkIfAccountCanTransact(landlordAccount);

        /* RECORD PAYMENT **/
        RentPayment rentPayment = new RentPayment();
        rentPayment.setPaymentType(PaymentType.CASH);
        rentPayment.setTenant(tenant);
        rentPayment.setAmount(expectedInitialRentPayment);
        rentPayment.setHouse(house);
        rentPayment.setNote(HOUSE_ASSIGNMENT_PAYMENT_NOTE);
        rentPayment.setPaymentStatus(PaymentStatus.PENDING);

        auditService.stampAuditedEntity(rentPayment);
        rentPaymentRepository.save(rentPayment);



        return null;
    }

    public void vacateHouse(){

    }

    private int rentPeriodsPaid(BigDecimal depositAmount, BigDecimal rentAmount){
        BigDecimal result = depositAmount.divide(rentAmount, 0, RoundingMode.CEILING);
        return result.intValue();
    }

    private Account getTenantAccountByTenant(Tenant tenant){
        var existingTenantAccount = accountRepository.findByUserMeta(tenant.getUserMeta());
        Validate.isPresent(existingTenantAccount, TENANT_ACCOUNT_NOT_FOUND, tenant.getId());
        return existingTenantAccount.get();
    }

}
