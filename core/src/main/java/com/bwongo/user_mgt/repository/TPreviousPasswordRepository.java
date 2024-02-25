package com.bwongo.user_mgt.repository;

import com.bwongo.user_mgt.models.jpa.TPreviousPassword;
import com.bwongo.user_mgt.models.jpa.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
@Repository
public interface TPreviousPasswordRepository extends JpaRepository<TPreviousPassword, Long> {
    List<TPreviousPassword> findAllByUser(TUser user);
}
