package com.bwongo.commons.models.jpa.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/20/23
 **/
@Entity
@Table(name = "t_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TUser extends AuditEntity{
    private String firstName;
    private String lastName;
    private String email;
}
