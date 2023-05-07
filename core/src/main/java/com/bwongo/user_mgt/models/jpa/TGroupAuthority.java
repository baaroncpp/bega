package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.model.jpa.BaseEntity;
import jakarta.persistence.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/21/23
 **/
@Entity
@Table(name = "t_group_authority",
        schema = "core",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = { "user_group_id", "permission_id" })})

public class TGroupAuthority extends BaseEntity {
    private TUserGroup userGroup;
    private TPermission permission;

    @JoinColumn(name = "user_group_id", referencedColumnName = "id", insertable = true, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public TUserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(TUserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @JoinColumn(name = "permission_id", referencedColumnName = "id", insertable = true, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    public TPermission getPermission() {
        return permission;
    }

    public void setPermission(TPermission permission) {
        this.permission = permission;
    }

}
