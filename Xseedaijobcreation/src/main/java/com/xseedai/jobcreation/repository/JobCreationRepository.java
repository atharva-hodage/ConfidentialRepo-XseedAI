package com.xseedai.jobcreation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xseedai.jobcreation.entity.JobCreation;

public interface JobCreationRepository extends JpaRepository<JobCreation, Long>, JpaSpecificationExecutor<JobCreation>  {
	Page<JobCreation> findByDeletedFalse(Pageable pageable);

	Page<JobCreation> findByJobTitleContainingIgnoreCaseAndDeletedFalse(String title, Pageable pageable);
	
	

	
}
