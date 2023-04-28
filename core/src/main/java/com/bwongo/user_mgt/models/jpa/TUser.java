package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.model.jpa.AuditEntity;
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
@Table(name = "t_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TUser extends AuditEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private boolean accountLocked;
    private boolean accountExpired;
    private boolean credentialExpired;
    private boolean approved;
    private boolean initialPasswordReset;
    private TUserGroup userGroup;
    private boolean accountDeleted;
    private Long approvedBy;
    private UserTypeEnum userType;

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    @Column(name = "username")
    public String getUsername() {
        return username;
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
    @Column(name = "credential_expired")
    public boolean isCredentialExpired() {
        return credentialExpired;
    }
    @Column(name = "approved")
    public boolean isApproved() {
        return approved;
    }
    @Column(name = "initial_password_reset")
    public boolean isInitialPasswordReset() {
        return initialPasswordReset;
    }
    @JoinColumn(name = "user_group_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUserGroup getUserGroup() {
        return userGroup;
    }
    @Column(name = "account_deleted")
    public boolean isAccountDeleted() {
        return accountDeleted;
    }
    @Column(name = "")
    public Long getApprovedBy() {
        return approvedBy;
    }
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    public UserTypeEnum getUserType() {
        return userType;
    }
}
