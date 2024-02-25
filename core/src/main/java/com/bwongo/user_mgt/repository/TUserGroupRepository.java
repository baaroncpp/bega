package com.bwongo.user_mgt.repository;

import com.bwongo.user_mgt.models.jpa.TUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
@Repository
public interface TUserGroupRepository extends JpaRepository<TUserGroup, Long> {
}
