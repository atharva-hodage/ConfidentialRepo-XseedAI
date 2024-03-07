package com.xseedai.jobcreation.service.serviceimpl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xseedai.jobcreation.dto.RecruiterDetailsDTO;
import com.xseedai.jobcreation.entity.HiringTeam;
import com.xseedai.jobcreation.entity.HiringTeamAccess;
import com.xseedai.jobcreation.entity.HiringTeamMember;
import com.xseedai.jobcreation.repository.HiringTeamAccessRepository;
import com.xseedai.jobcreation.repository.HiringTeamMemberRepository;
import com.xseedai.jobcreation.repository.HiringTeamRepository;
import com.xseedai.jobcreation.service.HiringTeamMemberService;
@Service
public class HiringTeamMemberServiceImpl implements HiringTeamMemberService{
    
    @Autowired
    private  HiringTeamRepository hiringTeamRepository;
    
    
    @Autowired
    private  HiringTeamMemberRepository hiringTeamMemberRepository;

    @Autowired
    private  HiringTeamAccessRepository hiringTeamAccessRepository;
    
     @Autowired
        private ModelMapper modelMapper;

     @Override
        public HiringTeamMember addHiringTeamMember(RecruiterDetailsDTO recruiterDetailsDTO, Long hiringTeamId) {
            // Map RecruiterDetailsDTO to HiringTeamMember
            HiringTeamMember hiringTeamMember = modelMapper.map(recruiterDetailsDTO, HiringTeamMember.class);

         // Set the profile image data separately
          hiringTeamMember.setImageData(recruiterDetailsDTO.getImageData());
            // Set default role and access
            hiringTeamMember.setRole("Recruiter");
       
            Long defaultAccessId = 1L; // Replace with the actual default accessId
            HiringTeamAccess defaultAccess = getDefaultAccessById(defaultAccessId);
            
            hiringTeamMember.setHiringTeamAccess(defaultAccess);
            // Validate or perform any necessary logic before saving

            // Set the hiringTeamId
            HiringTeam hiringTeam = hiringTeamRepository.findById(hiringTeamId)
                    .orElseThrow(() -> new IllegalArgumentException("Hiring Team not found with ID: " + hiringTeamId));
            hiringTeamMember.setHiringTeam(hiringTeam);

            return hiringTeamMemberRepository.save(hiringTeamMember);
        }

     private HiringTeamAccess getDefaultAccessById(Long accessId) {
            return hiringTeamAccessRepository.findByAccessId(accessId)
                    .orElseThrow(() -> new IllegalStateException("Default access with ID " + accessId + " not found in the database."));
        }
}
