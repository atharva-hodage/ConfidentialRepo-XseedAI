package com.xseedai.jobcreation.repository;


import com.xseedai.jobcreation.entity.JobPayRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPayRateRepository extends JpaRepository<JobPayRate, Long> {
    JobPayRate findByJobPayRateName(String jobPayRateName);
}
