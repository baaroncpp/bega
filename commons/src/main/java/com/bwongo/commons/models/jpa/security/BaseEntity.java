package com.bwongo.commons.models.jpa.security;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/20/23
 **/
@MappedSuperclass
public class BaseEntity {
    private Long id;
    private Date createdOn;
    private TUser createdBy;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "created_on")
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "created_by")
    public TUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(TUser createdBy) {
        this.createdBy = createdBy;
    }
}
