package com.bwongo.account_mgt.models.jpa;

import com.bwongo.account_mgt.models.enums.TransactionStatus;
import com.bwongo.account_mgt.models.enums.TransactionType;
import com.bwongo.base.model.jpa.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
@Entity
@Table(name = "t_transaction", schema = "core")
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends AuditEntity {
    private BigDecimal amount;
    private Account account;
    private String externalTransactionId;
    private String referenceNumber;
    private TransactionType transactionType;
    private BigDecimal amountBefore;
    private BigDecimal amountAfter;
    private TransactionStatus transactionStatus;

    @Column(name = "")
    public BigDecimal getAmount() {
        return amount;
    }

    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public Account getAccount() {
        return account;
    }

    @Column(name = "external_transaction_id")
    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    @Column(name = "reference_number")
    public String getReferenceNumber() {
        return referenceNumber;
    }

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Column(name = "amount_before")
    public BigDecimal getAmountBefore() {
        return amountBefore;
    }

    @Column(name = "amount_after")
    public BigDecimal getAmountAfter() {
        return amountAfter;
    }

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }
}
