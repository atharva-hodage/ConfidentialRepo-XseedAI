package com.xseedai.jobcreation.service.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xseedai.jobcreation.dto.RecruiterDetailsDTO;
import com.xseedai.jobcreation.entity.HiringTeam;
import com.xseedai.jobcreation.entity.HiringTeamAccess;
import com.xseedai.jobcreation.entity.HiringTeamMember;
import com.xseedai.jobcreation.entity.JobCreation;
import com.xseedai.jobcreation.feignclient.IdentityServiceFeignClient;
import com.xseedai.jobcreation.repository.HiringTeamAccessRepository;
import com.xseedai.jobcreation.repository.HiringTeamMemberRepository;
import com.xseedai.jobcreation.repository.HiringTeamRepository;
import com.xseedai.jobcreation.repository.JobCreationRepository;
import com.xseedai.jobcreation.service.HiringTeamMemberService;
@Service
public class HiringTeamMemberServiceImpl implements HiringTeamMemberService{
    
    @Autowired
    private  HiringTeamRepository hiringTeamRepository;
    
    
    @Autowired
    private  HiringTeamMemberRepository hiringTeamMemberRepository;

    
    @Autowired
    private JobCreationRepository jobCreationRepository;

    @Autowired
    private  HiringTeamAccessRepository hiringTeamAccessRepository;
    
     @Autowired
        private ModelMapper modelMapper;
     @Autowired
     private IdentityServiceFeignClient identityServiceFeignClient;
     
     @Override
     public List<RecruiterDetailsDTO> getRecruitersByEmail(String email) {
          return identityServiceFeignClient.getRecruitersByEmail(email);
     }

     @Override
     public ResponseEntity<List<RecruiterDetailsDTO>> getUsersByRoleId() {
         return identityServiceFeignClient.getUsersByRoleId();
     }
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
     
     @Override
     public HiringTeamAccess addAccess(HiringTeamAccess access) {
         return hiringTeamAccessRepository.save(access);
     }

	

     @Override
     public HiringTeam createHiringTeam(Long jobId) {
         JobCreation job = jobCreationRepository.findById(jobId).orElse(null);
         if (job == null) {
             // Handle if job not found
             return null;
         }

         HiringTeam hiringTeam = new HiringTeam();
         hiringTeam.setJob(job);
         HiringTeam savedHiringTeam = hiringTeamRepository.save(hiringTeam);
         HiringTeamAccess defaultMemberAccess = new HiringTeamAccess();
         defaultMemberAccess.setAccessId(3L);
         
         
         HiringTeamMember defaultHiringTeamMember= new HiringTeamMember();
         defaultHiringTeamMember.setTeamMemberId(job.getUserId());
         defaultHiringTeamMember.setName(job.getCreatedBy());
         defaultHiringTeamMember.setEmail("ppcbum@gmail.com");
         defaultHiringTeamMember.setRole("Manager");
         defaultHiringTeamMember.setHiringTeam(hiringTeam);
         defaultHiringTeamMember.setHiringTeamAccess(defaultMemberAccess);
         hiringTeamMemberRepository.save(defaultHiringTeamMember);
         return savedHiringTeam;
     }

     @Override
     public HiringTeamMember updateTeamMemberAccess(Long teamMemberId, Long accessId) {
         HiringTeamMember teamMember = hiringTeamMemberRepository.findById(teamMemberId).orElseThrow(() -> new RuntimeException("Team member not found"));
         HiringTeamAccess access = new HiringTeamAccess();
         access.setAccessId(accessId);
         teamMember.setHiringTeamAccess(access);
         return hiringTeamMemberRepository.save(teamMember);
     }
}