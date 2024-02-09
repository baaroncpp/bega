package com.bwongo.landlord_mgt.models.jpa;

import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
@Table(name = "t_bank_detail", schema = "core")
public class TBankDetail extends AuditEntity {
    private String bankName;
    private String accountName;
    private String accountNumber;
    private TUserMeta userMeta;

    @Column(name = "bank_name")
    public String getBankName() {
        return bankName;
    }

    @Column(name = "account_name")
    public String getAccountName() {
        return accountName;
    }

    @Column(name = "account_number")
    public String getAccountNumber() {
        return accountNumber;
    }

    @JoinColumn(name = "user_meta_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUserMeta getUserMeta() {
        return userMeta;
    }
}
