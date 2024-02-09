package com.bwongo.account_mgt.models;

import com.bwongo.apartment_mgt.models.enums.RentPeriod;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/24/23
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class DisbursePayment {
    private Date occupiedUtilDate;
    private BigDecimal paidAmount;
    private BigDecimal predefinedRentFee;
    private BigDecimal landlordDefinedRentFee;
    private RentPeriod rentPeriod;
    private Double managementPercentage;
}
