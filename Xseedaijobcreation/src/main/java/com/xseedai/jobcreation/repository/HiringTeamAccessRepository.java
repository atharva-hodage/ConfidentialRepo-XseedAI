package com.xseedai.jobcreation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xseedai.jobcreation.entity.HiringTeamAccess;

public interface HiringTeamAccessRepository extends JpaRepository<HiringTeamAccess, Long>{

     Optional<HiringTeamAccess> findByAccessId(Long accessId);



}