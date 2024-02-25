package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.models.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
@Entity
@Setter
@Table(name = "t_previous_password", schema = "core")
public class TPreviousPassword extends BaseEntity {
    private TUser user;
    private String previousPassword;
    private int passwordChangeCount;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getUser() {
        return user;
    }

    @Column(name = "previous_password")
    public String getPreviousPassword() {
        return previousPassword;
    }

    @Column(name = "password_change_count")
    public int getPasswordChangeCount() {
        return passwordChangeCount;
    }
}
