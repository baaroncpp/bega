package com.bwongo.apartment_mgt.models.jpa;

import com.bwongo.base.model.jpa.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
@Entity
@Table(name = "t_account", schema = "core")
public class Account extends AuditEntity {
    
}
