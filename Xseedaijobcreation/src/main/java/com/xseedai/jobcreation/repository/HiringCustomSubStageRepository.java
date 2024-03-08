package com.xseedai.jobcreation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xseedai.jobcreation.entity.HiringCustomSubStage;

import jakarta.transaction.Transactional;


public interface HiringCustomSubStageRepository extends JpaRepository<HiringCustomSubStage, Long>{
    List<HiringCustomSubStage> findByStage_HiringStageIdAndJobCreation_JobId(Long stageId, Long jobCreationId);
    
    List<HiringCustomSubStage> findByJobCreation_JobIdAndIsDefaultTrue(Long jobId);

    @Transactional
    void deleteByJobCreation_JobIdAndIsDefaultFalse(Long jobId);

}
