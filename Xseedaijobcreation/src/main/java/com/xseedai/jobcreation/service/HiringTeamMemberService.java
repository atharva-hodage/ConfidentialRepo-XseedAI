package com.xseedai.jobcreation.service;

import com.xseedai.jobcreation.dto.RecruiterDetailsDTO;
import com.xseedai.jobcreation.entity.HiringTeamAccess;
import com.xseedai.jobcreation.entity.HiringTeamMember;

public interface HiringTeamMemberService {
	HiringTeamMember addHiringTeamMember(RecruiterDetailsDTO recruiterDetailsDTO, Long hiringTeamId);
	
//	HiringTeamAccess getDefaultAccessById(Long accessId);
}
