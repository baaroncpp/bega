package com.bwongo.base.model.jpa;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/20/23
 **/
@MappedSuperclass
@Setter
public class BaseEntity {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedOn() {
        return createdOn;
    }

    @Column(name = "modified_on")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getModifiedOn() {
        return modifiedOn;
    }
}
