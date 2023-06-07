package com.bwongo.account_mgt.utils;

import com.bwongo.account_mgt.models.DisbursePayment;
import com.bwongo.account_mgt.models.DisbursedPayment;
import com.bwongo.account_mgt.models.enums.AccountStatus;
import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.apartment_mgt.models.enums.RentPeriod;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringUtil;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.commons.models.utils.Validate;

import java.math.BigDecimal;
import java.util.Date;

import static com.bwongo.account_mgt.utils.accountMsgConstant.*;
import static com.bwongo.commons.models.utils.DateTimeUtil.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
public class AccountUtils {

    private static final String REF_PREFIX = "bega";

    private AccountUtils() { }

    public static void checkIfAccountCanTransact(Account account){
        var accountId = account.getId();
        Validate.isTrue(!account.getAccountStatus().equals(AccountStatus.SUSPENDED), ExceptionType.BAD_REQUEST, ACCOUNT_SUSPENDED, accountId);
        Validate.isTrue(!account.getAccountStatus().equals(AccountStatus.CLOSED), ExceptionType.BAD_REQUEST, ACCOUNT_CLOSED, accountId);
        Validate.isTrue(!account.getAccountStatus().equals(AccountStatus.NOT_ACTIVE), ExceptionType.BAD_REQUEST, ACCOUNT_NOT_ACTIVE, accountId);
    }

    public static String getTransactionReference(){
        var result = new StringBuffer();
        result.append(REF_PREFIX);
        result.append(StringUtil.subStr(StringUtil.randomString(), 10));
        return result.toString().toUpperCase();
    }

    public static DisbursedPayment disbursePayment(DisbursePayment disbursePayment){

        final var amountPaid = disbursePayment.getPaidAmount();
        final var predefinedRentFee = disbursePayment.getPredefinedRentFee();
        final var landlordDefinedRentFee = disbursePayment.getLandlordDefinedRentFee();
        final var rentPeriod = disbursePayment.getRentPeriod();
        final var occupiedUntilDate = disbursePayment.getOccupiedUtilDate();
        final var managementPercentage = disbursePayment.getManagementPercentage();

        var nextOccupiedUntilDate = new Date();

        /* RENT PERIODS**/
        int rentPeriods = amountPaid.divide(predefinedRentFee).intValue();//TODO ROUND/REMOVE ALL DECIMALS

        BigDecimal amountForCompletelyPredefinedRentPaidPeriods = predefinedRentFee.multiply(BigDecimal.valueOf(rentPeriods));
        BigDecimal landlordAmount = landlordDefinedRentFee.multiply(BigDecimal.valueOf(rentPeriods));
        BigDecimal systemResidueAmount = amountForCompletelyPredefinedRentPaidPeriods.subtract(landlordAmount);

        BigDecimal tenantResidueAmount = amountPaid.subtract(amountForCompletelyPredefinedRentPaidPeriods);
        BigDecimal commissionAmount = landlordAmount.multiply(BigDecimal.valueOf(managementPercentage / 100));
        BigDecimal landlordActualPayout = landlordAmount.subtract(commissionAmount);

        if(rentPeriod.equals(RentPeriod.MONTHLY)){
            String occupiedUntilStringDate = DateTimeUtil.dateToString(occupiedUntilDate, YYYYMMDDHHMMSS);
            String nextOccupiedUntilStringDate = DateTimeUtil.addMonths(occupiedUntilStringDate, rentPeriods);
            nextOccupiedUntilDate = DateTimeUtil.stringToDate(nextOccupiedUntilStringDate, YYYYMMDDHHMMSS);
        }

        if(rentPeriod.equals(RentPeriod.WEEKLY)){
            int days = rentPeriods * 7;
            String occupiedUntilStringDate = DateTimeUtil.dateToString(occupiedUntilDate, YYYYMMDDHHMMSS);
            String nextOccupiedUntilStringDate = DateTimeUtil.addDays(occupiedUntilStringDate, days);
            nextOccupiedUntilDate = DateTimeUtil.stringToDate(nextOccupiedUntilStringDate, YYYYMMDDHHMMSS);
        }

        return DisbursedPayment.builder()
                .commissionAmount(commissionAmount)
                .landlordActualPayout(landlordActualPayout)
                .systemResidueAmount(systemResidueAmount)
                .tenantResidueAmount(tenantResidueAmount)
                .numberOfPaidRentPeriods(rentPeriods)
                .updatedOccupiedUntilDate(nextOccupiedUntilDate)
                .build();
    }
}
