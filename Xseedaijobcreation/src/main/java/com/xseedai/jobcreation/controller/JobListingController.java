package com.xseedai.jobcreation.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xseedai.jobcreation.dto.JobListingDto;
import com.xseedai.jobcreation.service.JobListingService;

import lombok.AllArgsConstructor;
@Tag(name = "Job-Listing-Controller", description = "Endpoints for managing job listing")

@RestController
@RequestMapping("api/joblisting")
@AllArgsConstructor
public class JobListingController {

	@Autowired
	private JobListingService jobListingService;

	@GetMapping("/getAllJobs")
	@Operation(summary = "Get all jobs", description = "Retrieves all jobs with pagination support.")
	@ApiResponse(responseCode = "200", description = "List of jobs retrieved successfully")
	public Page<JobListingDto> getAllJobs(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return jobListingService.getAllJobs(pageable);
	}
//
//	@GetMapping("/getAllFilteredJobsStringType")
//	public List<JobListingDto> getAllFilteredJobsStringType(@RequestParam(required = false) List<String> statuses,
//			@RequestParam(required = false) List<String> companies, @RequestParam(required = false) List<String> vms,
//			@RequestParam(required = false) List<String> expiring,
//			@RequestParam(required = false) List<String> jobTypes,
//			@RequestParam(required = false) List<String> clients) {
//
//		return jobListingService.getAllFilteredJobsStringType(statuses, companies, vms, expiring, jobTypes, clients);
//	}

//	@GetMapping("/getAllFilteredJobs")
//	public List<JobListingDto> getAllFilteredJobs(@RequestParam(required = false) List<Long> statuses,
//			@RequestParam(required = false) List<Long> companies, @RequestParam(required = false) List<Long> vms,
//			@RequestParam(required = false) List<String> expiring, @RequestParam(required = false) List<Long> jobTypes,
//			@RequestParam(required = false) List<Long> clients) {
//
//		return jobListingService.getAllFilteredJob(statuses, companies, vms, expiring, jobTypes, clients);
//	}

	@GetMapping("/getAllFilteredJobs")
	@Operation(summary = "Get filtered jobs", description = "Retrieves filtered jobs with pagination support.")
	@ApiResponse(responseCode = "200", description = "List of filtered jobs retrieved successfully")
	public ResponseEntity<Page<JobListingDto>> getAllFilteredJobs(@RequestParam(required = false) List<Long> statuses,
			@RequestParam(required = false) List<Long> companies, @RequestParam(required = false) List<Long> vms,
			@RequestParam(required = false) List<String> expiring, @RequestParam(required = false) List<Long> jobTypes,
			@RequestParam(required = false) List<Long> clients, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<JobListingDto> filteredJobs = jobListingService.getAllFilteredJob(statuses, companies, vms, expiring,
				jobTypes, clients, pageable);
		return ResponseEntity.ok(filteredJobs);
	}
	
	@GetMapping("/searchJobs")
    public Page<JobListingDto> searchJobs(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return jobListingService.searchJobs(keyword, pageable);
    }
}
