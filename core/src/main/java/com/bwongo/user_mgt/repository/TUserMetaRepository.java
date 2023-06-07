package com.bwongo.user_mgt.repository;

import com.bwongo.user_mgt.models.jpa.TUserMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/7/23
 **/
@Repository
public interface TUserMetaRepository extends JpaRepository<TUserMeta, Long> {
}
