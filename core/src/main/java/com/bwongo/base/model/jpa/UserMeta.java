package com.bwongo.base.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
@Entity
@Table(name = "t_user_meta", schema = "core")
public class UserMeta extends AuditEntity{
}
