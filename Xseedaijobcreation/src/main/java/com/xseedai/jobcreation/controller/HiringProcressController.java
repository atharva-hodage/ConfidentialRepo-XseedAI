package com.xseedai.jobcreation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.xseedai.jobcreation.dto.HiringCustomSubStageDto;
import com.xseedai.jobcreation.dto.RecruiterDetailsDTO;
import com.xseedai.jobcreation.entity.HiringCustomSubStage;
import com.xseedai.jobcreation.entity.HiringDefaultSubStage;
import com.xseedai.jobcreation.entity.HiringStage;
import com.xseedai.jobcreation.entity.HiringTeam;
import com.xseedai.jobcreation.entity.HiringTeamMember;
import com.xseedai.jobcreation.service.HiringProcressService;
import com.xseedai.jobcreation.service.HiringTeamMemberService;

@RestController
@RequestMapping("/api/hiringprocess")
public class HiringProcressController {
	@Autowired
	private HiringProcressService hiringProcressService;



	@PostMapping("/hiringstage")
	public ResponseEntity<HiringStage> addHiringStage(@RequestBody HiringStage hiringStage) {
		HiringStage savedHiringStage = hiringProcressService.addHiringStage(hiringStage);
		return new ResponseEntity<>(savedHiringStage, HttpStatus.CREATED);
	}

	@PostMapping("/hiringdefaultsubstage/{hiringStageId}")
	public ResponseEntity<HiringDefaultSubStage> addHiringDefaultSubStage(
			@RequestBody HiringDefaultSubStage hiringDefaultSubStage, @PathVariable Long hiringStageId) {
		HiringDefaultSubStage savedSubStage = hiringProcressService.addHiringDefaultSubStage(hiringDefaultSubStage,
				hiringStageId);
		return new ResponseEntity<>(savedSubStage, HttpStatus.CREATED);
	}

//	@PostMapping("/hiringCustomSubStage/{hiringStageId}")
//	public ResponseEntity<HiringCustomSubStage> addHiringCustomSubStage(
//			@RequestBody HiringCustomSubStageDto hiringCustomSubStageDto, @PathVariable Long hiringStageId) {
//		try {
//			HiringCustomSubStage hiringCustomSubStage = hiringProcressService
//					.addHiringCustomSubStage(hiringCustomSubStageDto, hiringStageId);
//			return ResponseEntity.ok(hiringCustomSubStage);
//		} catch (IllegalArgumentException e) {
//			return ResponseEntity.badRequest().body(null);
//		}
//	}

	
	@PostMapping("/hiringCustomSubStage")
	public ResponseEntity<HiringCustomSubStage> addHiringCustomSubStage(
	        @RequestParam String customStageName,
	        @RequestParam Long jobCreationId,
	        @RequestParam Long hiringStageId) {
	    try {
	        HiringCustomSubStage hiringCustomSubStage = hiringProcressService
	                .addHiringCustomSubStage(customStageName, jobCreationId, hiringStageId);
	        return ResponseEntity.ok(hiringCustomSubStage);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(null);
	    }
	}
	
	
	@GetMapping("/gethiringstage/{stageId}/job/{jobCreationId}")
	public List<HiringCustomSubStageDto> getCustomSubStagesByStageAndJob(@PathVariable Long stageId,
			@PathVariable Long jobCreationId) {
		return hiringProcressService.getCustomSubStagesByStageAndJob(stageId, jobCreationId);
	}

//	   @PutMapping("/deselect")
//	    public ResponseEntity<Void> deselectSubStages(@RequestBody List<Long> subStageIds) {
//		   hiringProcressService.deselectSubStages(subStageIds);
//	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//	    }

	@DeleteMapping("/switchToDefault/{jobId}")
	public List<HiringCustomSubStageDto> switchToDefault(@PathVariable Long jobId) {
		return hiringProcressService.switchToDefault(jobId);
	}

	@PutMapping("/updateSubStageSelection")
	public ResponseEntity<String> updateSubStageSelection(@RequestBody List<Long> subStageIds) {
		return hiringProcressService.updateSubStageSelection(subStageIds);
	}

	
  

}
