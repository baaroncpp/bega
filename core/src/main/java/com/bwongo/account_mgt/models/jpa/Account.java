package com.bwongo.account_mgt.models.jpa;

import com.bwongo.account_mgt.models.enums.AccountStatus;
import com.bwongo.account_mgt.models.enums.AccountType;
import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.user_mgt.models.jpa.TUser;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
@Entity
@Table(name = "t_account", schema = "core")
@Setter
public class Account extends AuditEntity {
    private String accountNumber;
    private TUserMeta userMeta;
    private AccountStatus accountStatus;
    private AccountType accountType;
    private BigDecimal availableBalance;
    private Date activatedOn;
    private TUser activatedBy;
    private Date suspendedOn;
    private TUser suspendedBy;
    private Date closedOn;
    private TUser closedBy;

    @Column(name = "account_number")
    public String getAccountNumber() {
        return accountNumber;
    }

    @JoinColumn(name = "user_meta_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUserMeta getUserMeta() {
        return userMeta;
    }

    @Column(name = "account_status")
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    @Column(name = "account_type")
    public AccountType getAccountType() {
        return accountType;
    }

    @Column(name = "available_balance")
    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    @Column(name = "activated_on")
    public Date getActivatedOn() {
        return activatedOn;
    }

    @JoinColumn(name = "activated_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getActivatedBy() {
        return activatedBy;
    }

    @Column(name = "suspended_on")
    public Date getSuspendedOn() {
        return suspendedOn;
    }

    @JoinColumn(name = "suspended_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getSuspendedBy() {
        return suspendedBy;
    }

    @Column(name = "closed_on")
    public Date getClosedOn() {
        return closedOn;
    }

    @JoinColumn(name = "closed_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getClosedBy() {
        return closedBy;
    }
}
