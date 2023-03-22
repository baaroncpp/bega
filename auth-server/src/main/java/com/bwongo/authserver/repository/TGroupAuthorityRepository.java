package com.bwongo.authserver.repository;

import com.bwongo.commons.models.jpa.security.TGroupAuthority;
import com.bwongo.commons.models.jpa.security.TUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
@Repository
public interface TGroupAuthorityRepository extends JpaRepository<TGroupAuthority, Long> {
    List<TGroupAuthority> findByUserGroup(TUserGroup userGroup);
}
