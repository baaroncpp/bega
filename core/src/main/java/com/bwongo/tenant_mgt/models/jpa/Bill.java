package com.bwongo.tenant_mgt.models.jpa;

import com.bwongo.base.model.BaseEntity;
import com.bwongo.tenant_mgt.models.enums.BillStatus;
import com.bwongo.tenant_mgt.models.enums.BillingDuration;

import java.math.BigDecimal;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/17/23
 **/
public class Bill extends BaseEntity {
    private String name;
    private String billProvider;
    private BillingDuration billingDuration;
    private BigDecimal amount;
    private BillStatus billStatus;

}
