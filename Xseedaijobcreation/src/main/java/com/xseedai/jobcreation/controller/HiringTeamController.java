package com.xseedai.jobcreation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xseedai.jobcreation.dto.RecruiterDetailsDTO;
import com.xseedai.jobcreation.entity.HiringTeam;
import com.xseedai.jobcreation.entity.HiringTeamAccess;
import com.xseedai.jobcreation.entity.HiringTeamMember;
import com.xseedai.jobcreation.service.HiringTeamMemberService;
@RestController
@RequestMapping("/api/jobcreation")
public class HiringTeamController {
	
	@Autowired
	private HiringTeamMemberService hiringTeamMemberService;
	
	@GetMapping("/by-email")
	public List<RecruiterDetailsDTO> getRecruitersByEmail(@RequestParam String email) {
		return hiringTeamMemberService.getRecruitersByEmail(email);
	}

	@GetMapping("/get-all-recruiters")
	public ResponseEntity<List<RecruiterDetailsDTO>> getUsersByRoleId() {
		return hiringTeamMemberService.getUsersByRoleId();
	}

	@PostMapping("/addHiringTeamMember")
	public ResponseEntity<HiringTeamMember> addHiringTeamMember(@RequestBody RecruiterDetailsDTO recruiterDetailsDTO,
			@RequestParam Long hiringTeamId) {
		HiringTeamMember savedMember = hiringTeamMemberService.addHiringTeamMember(recruiterDetailsDTO, hiringTeamId);
		return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
	}

	@PostMapping("/addaccess")
	public ResponseEntity<HiringTeamAccess> addAccess(@RequestBody HiringTeamAccess access) {
		HiringTeamAccess addedAccess = hiringTeamMemberService.addAccess(access);
		return ResponseEntity.status(HttpStatus.CREATED).body(addedAccess);
	}
	
	//Dummy CreateNewHiringTeam Api Not to be used for actual purpose
	  @PostMapping("/createNewHiringTeam/{jobId}")
	    public ResponseEntity<HiringTeam> createHiringTeam(@PathVariable Long jobId) {
	        HiringTeam hiringTeam = hiringTeamMemberService.createHiringTeam(jobId);
	        if (hiringTeam == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(hiringTeam, HttpStatus.CREATED);
	    }
	  

	    @PutMapping("/{teamMemberId}/access/{accessId}")
	    public HiringTeamMember updateTeamMemberAccess(@PathVariable Long teamMemberId, @PathVariable Long accessId) {
	        return hiringTeamMemberService.updateTeamMemberAccess(teamMemberId, accessId);
	    }
}
