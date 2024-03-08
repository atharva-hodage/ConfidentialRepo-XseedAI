package com.xseedai.jobcreation.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.xseedai.jobcreation.dto.RecruiterDetailsDTO;
import com.xseedai.jobcreation.entity.HiringTeam;
import com.xseedai.jobcreation.entity.HiringTeamAccess;
import com.xseedai.jobcreation.entity.HiringTeamMember;

public interface HiringTeamMemberService {
	HiringTeamMember addHiringTeamMember(RecruiterDetailsDTO recruiterDetailsDTO, Long hiringTeamId);
	List<RecruiterDetailsDTO> getRecruitersByEmail(String email);
	ResponseEntity<List<RecruiterDetailsDTO>> getUsersByRoleId();
    HiringTeamAccess addAccess(HiringTeamAccess access);
    HiringTeam createHiringTeam(Long jobId);
    
    HiringTeamMember updateTeamMemberAccess(Long teamMemberId, Long accessId);



}
