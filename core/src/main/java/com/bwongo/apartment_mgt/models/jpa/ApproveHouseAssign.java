package com.bwongo.apartment_mgt.models.jpa;

import com.bwongo.apartment_mgt.models.enums.ApprovalStatus;
import com.bwongo.base.models.jpa.AuditEntity;
import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/19/23
 **/
@Entity
@Table(name = "t_approve_house_assign", schema = "core")
@Setter
public class ApproveHouseAssign extends AuditEntity {
    private AssignHouse assignHouse;
    private ApprovalStatus approvalStatus;
    private String note;

    @JoinColumn(name = "assign_house_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public AssignHouse getAssignHouse() {
        return assignHouse;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public String getNote() {
        return note;
    }
}
