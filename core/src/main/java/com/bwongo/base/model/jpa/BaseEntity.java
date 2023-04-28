package com.bwongo.base.model.jpa;

import com.bwongo.user_mgt.models.jpa.TUser;
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
    private TUser createdBy;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "created_on")
    public Date getCreatedOn() {
        return createdOn;
    }

    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getCreatedBy() {
        return createdBy;
    }
}
