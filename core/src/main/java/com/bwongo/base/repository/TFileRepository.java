package com.bwongo.base.repository;

import com.bwongo.base.models.jpa.TFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/24/24
 **/
@Repository
public interface TFileRepository extends JpaRepository<TFile, Long> {
    boolean existsByFilePath(String filePath);
}
