package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.base.models.enums.ApprovalEnum;
import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
@Entity
@Table(name = "t_user_approval", schema = "core")
@Setter
public class TUserApproval extends AuditEntity {
    private TUser user;
    private ApprovalEnum status;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getUser() {
        return user;
    }

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    public ApprovalEnum getStatus() {
        return status;
    }
}
