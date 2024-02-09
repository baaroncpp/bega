package com.bwongo.account_mgt.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/26/23
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DisbursedPayment {
    private Date updatedOccupiedUntilDate;
    private BigDecimal landlordActualPayout;
    private BigDecimal systemResidueAmount;
    private BigDecimal commissionAmount;
    private BigDecimal tenantResidueAmount;
    private int numberOfPaidRentPeriods;
}
