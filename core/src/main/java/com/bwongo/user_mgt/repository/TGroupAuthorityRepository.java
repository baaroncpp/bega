package com.bwongo.user_mgt.repository;

import com.bwongo.user_mgt.models.jpa.TGroupAuthority;
import com.bwongo.user_mgt.models.jpa.TPermission;
import com.bwongo.user_mgt.models.jpa.TUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
@Repository
public interface TGroupAuthorityRepository extends JpaRepository<TGroupAuthority, Long> {
    List<TGroupAuthority> findByUserGroup(TUserGroup userGroup);
    Optional<TGroupAuthority> findByUserGroupAndPermission(TUserGroup userGroup, TPermission permission);

}
