package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.models.jpa.BaseEntity;
import com.bwongo.user_mgt.models.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/20/23
 **/
@Setter
@Entity
@Table(name = "t_user", schema = "core")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TUser extends BaseEntity {
    private String username;
    private String password;
    private boolean accountLocked;
    private boolean accountExpired;
    private boolean credentialExpired;
    private boolean approved;
    private boolean initialPasswordReset;
    private TUserGroup userGroup;
    private boolean isDeleted;
    private Long approvedBy;
    private UserTypeEnum userType;
    private Long userMetaId;

    @Column(name = "username")
    public String getUsername() {
        return this.username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "account_locked")
    public boolean isAccountLocked() {
        return accountLocked;
    }

    @Column(name = "account_expired")
    public boolean isAccountExpired() {
        return accountExpired;
    }

    @Column(name = "cred_expired")
    public boolean isCredentialExpired() {
        return credentialExpired;
    }
    @JoinColumn(name = "user_group_id", referencedColumnName = "id", insertable = true, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUserGroup getUserGroup() {
        return userGroup;
    }
    @Column(name = "approved")
    public boolean isApproved() {
        return approved;
    }
    @Column(name = "is_deleted")
    public boolean getDeleted() {
        return isDeleted;
    }

    @Column(name = "approved_by")
    public Long getApprovedBy() {
        return approvedBy;
    }

    @Column(name = "initial_password_reset")
    public boolean isInitialPasswordReset() {
        return initialPasswordReset;
    }

    @Column(name = "user_type")
    @Enumerated(value = EnumType.STRING)
    public UserTypeEnum getUserType() {
        return userType;
    }

    @Column(name = "user_meta_id")
    public Long getUserMetaId() {
        return userMetaId;
    }
}
