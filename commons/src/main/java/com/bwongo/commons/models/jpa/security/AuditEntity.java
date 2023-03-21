package com.bwongo.commons.models.jpa.security;

import jakarta.persistence.MappedSuperclass;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/20/23
 **/
@MappedSuperclass
public class AuditEntity extends BaseEntity{
    private Date modifiedOn;
    private TUser modifiedBy;
}
