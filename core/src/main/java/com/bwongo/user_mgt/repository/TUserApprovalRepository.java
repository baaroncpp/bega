package com.bwongo.user_mgt.repository;

import com.bwongo.base.models.enums.ApprovalEnum;
import com.bwongo.user_mgt.models.jpa.TUserApproval;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/25/24
 **/
@Repository
public interface TUserApprovalRepository extends JpaRepository<TUserApproval, Long> {
    List<TUserApproval> findAllByStatus(ApprovalEnum approvalEnum, Pageable pageable);
}
