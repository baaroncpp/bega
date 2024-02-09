package com.bwongo.security.models.jpa;

import com.bwongo.base.models.jpa.BaseEntity;
import com.bwongo.user_mgt.models.jpa.TUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 12/17/23
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name = "t_refresh_token", schema = "core")
public class TRefreshToken extends BaseEntity {
    private String token;
    private Instant expiryDate;
    private TUser user;

    @Column(name = "token")
    public String getToken() {
        return token;
    }

    @Column(name = "expiry_date")
    public Instant getExpiryDate() {
        return expiryDate;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getUser() {
        return user;
    }
}
