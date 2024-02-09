package com.bwongo.account_mgt.models.jpa;

import com.bwongo.account_mgt.models.enums.DebtStatus;
import com.bwongo.base.models.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/28/23
 **/
@Entity
@Table(name = "t_debt", schema = "core")
@Setter
public class Debt extends BaseEntity {
    private Account debtorAccount;
    private BigDecimal payableAmount;
    private Account creditorAccount;
    private DebtStatus debtStatus;

    @JoinColumn(name = "debtor_account_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    public Account getDebtorAccount() {
        return debtorAccount;
    }

    @Column(name = "payable_amount")
    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    @JoinColumn(name = "creditor_account_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    public Account getCreditorAccount() {
        return creditorAccount;
    }

    @Column(name = "debt_status")
    public DebtStatus getDebtStatus() {
        return debtStatus;
    }
}
