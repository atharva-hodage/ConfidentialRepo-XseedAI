package com.xseedai.jobcreation.controller;

import java.security.Key;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.xseedai.jobcreation.dto.JobCreationDto;
import com.xseedai.jobcreation.entity.CompanyDetailsMaster;
import com.xseedai.jobcreation.entity.Currency;
import com.xseedai.jobcreation.entity.JobCreation;
import com.xseedai.jobcreation.entity.JobPayRate;
import com.xseedai.jobcreation.entity.JobStatus;
import com.xseedai.jobcreation.entity.JobTitleMaster;
import com.xseedai.jobcreation.entity.JobType;
import com.xseedai.jobcreation.entity.SkillsMaster;
import com.xseedai.jobcreation.service.JobCreationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Tag(name = "Job-Creation-Controller", description = "Endpoints for managing job creation")
@RestController
@RequestMapping("api/jobcreation")
@AllArgsConstructor
public class JobCreationController {
	@Autowired
	private JobCreationService jobCreationService;

	@PostMapping("/addjobtitle")
	@Operation(summary = "Create a new job title", description = "Creates a new job title.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Job title created successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<JobTitleMaster> saveJobTitle(@RequestBody JobTitleMaster jobTitle) {
		JobTitleMaster saveJobTitle = jobCreationService.saveJobTitle(jobTitle);
		return new ResponseEntity<>(saveJobTitle, HttpStatus.CREATED);
	}

	@GetMapping("/getalljobtitle")
	@Operation(summary = "Get all job titles", description = "Retrieves a list of all job titles.")
	@ApiResponse(responseCode = "200", description = "List of job titles retrieved successfully")
	public ResponseEntity<List<JobTitleMaster>> getAllJobTitles() {
		List<JobTitleMaster> jobTitles = jobCreationService.getAllJobTitles();
		return ResponseEntity.ok().body(jobTitles);
	}

	@GetMapping("/search")
	@Operation(summary = "Search job titles", description = "Searches for job titles based on the provided query.")
	@ApiResponse(responseCode = "200", description = "List of job titles retrieved successfully")
	public ResponseEntity<List<JobTitleMaster>> searchJobTitles(@RequestParam("query") String query) {
		List<JobTitleMaster> jobTitles = jobCreationService.searchJobTitles(query);
		return ResponseEntity.ok().body(jobTitles);
	}

	@PostMapping("/addSkillsToDatabase")
	@Operation(summary = "Add skills to database", description = "Adds skills to the database.")
	public ResponseEntity<?> addSkillsToDatabase(@RequestBody SkillsMaster skillMaster) {
		return jobCreationService.addSkillsToDatabase(skillMaster);
	}

	@GetMapping("/getSkillsFromDatabase")
	@Operation(summary = "Get skills from database", description = "Retrieves skills from the database.")
	@ApiResponse(responseCode = "302", description = "Skills retrieved successfully")
	public ResponseEntity<?> getSkillsFromDatabase() {
		return new ResponseEntity<>(jobCreationService.getSkillsFromDatabase(), HttpStatus.FOUND);
	}

	@GetMapping("/searchSkill")
	@Operation(summary = "Search skills", description = "Searches for skills based on the provided keyword.")
	@ApiResponse(responseCode = "200", description = "List of matched skills retrieved successfully")
	public ResponseEntity<List<String>> searchSkills(@RequestParam String keyword) {
		List<String> matchedSkills = jobCreationService.findMatchingSkills(keyword);
		return ResponseEntity.ok(matchedSkills);
	}

	@PostMapping("/saveJobType")
	@Operation(summary = "Save job type", description = "Creates a new job type.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Job type created successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<JobType> postJobType(@RequestBody JobType jobType) {
		JobType createdJobType = jobCreationService.postJobType(jobType);
		return new ResponseEntity<>(createdJobType, HttpStatus.CREATED);
	}

//
	@GetMapping("/getJobType")
	@Operation(summary = "Get job type by ID", description = "Retrieves job type by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Job type retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Job type not found") })
	public ResponseEntity<JobType> getJobTypeById(@RequestParam Long jobTypeId) {
		JobType jobType = jobCreationService.getJobTypeById(jobTypeId);
		if (jobType != null) {
			return new ResponseEntity<>(jobType, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getJobTypeByName")
	@Operation(summary = "Get job type by name", description = "Retrieves job type by its name.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Job type retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Job type not found") })
	public ResponseEntity<JobType> getJobTypeByName(@RequestParam String jobTypeName) {
		JobType jobType = jobCreationService.getJobTypeByName(jobTypeName);
		if (jobType != null) {
			return new ResponseEntity<>(jobType, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getJobTypeList")
	@Operation(summary = "Get all job types", description = "Retrieves a list of all job types.")
	@ApiResponse(responseCode = "200", description = "List of job types retrieved successfully")
	public ResponseEntity<List<JobType>> getAllJobTypes() {
		List<JobType> jobTypes = jobCreationService.getAllJobTypes();
		return new ResponseEntity<>(jobTypes, HttpStatus.OK);
	}

	@PostMapping("/saveCurrency")
	@Operation(summary = "Save currency", description = "Creates a new currency.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Currency created successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Currency> postCurrency(@RequestBody Currency currency) {
		Currency createdCurrency = jobCreationService.postCurrency(currency);
		return new ResponseEntity<>(createdCurrency, HttpStatus.CREATED);
	}

	@GetMapping("/getCurrencyById/{currencyId}")
	@Operation(summary = "Get currency by ID", description = "Retrieves currency by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Currency retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Currency not found") })
	public ResponseEntity<Currency> getCurrencyById(@PathVariable Long currencyId) {
		Currency currency = jobCreationService.getCurrencyById(currencyId);
		if (currency != null) {
			return new ResponseEntity<>(currency, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getCurrencyByName/{currencyName}")
	@Operation(summary = "Get currency by name", description = "Retrieves currency by its name.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Currency retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Currency not found") })
	public ResponseEntity<Currency> getCurrencyByName(@PathVariable String currencyName) {
		Currency currency = jobCreationService.getCurrencyByName(currencyName);
		if (currency != null) {
			return new ResponseEntity<>(currency, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAllCurrencies")
	@Operation(summary = "Get all currencies", description = "Retrieves a list of all currencies.")
	@ApiResponse(responseCode = "200", description = "List of currencies retrieved successfully")
	public ResponseEntity<List<Currency>> getAllCurrencies() {
		List<Currency> currencies = jobCreationService.getAllCurrencies();
		return new ResponseEntity<>(currencies, HttpStatus.OK);
	}

	
	 @PostMapping("/saveJobPayRate")
	 @Operation(summary = "Save JobPayRate", description = "Creates a new JobPayRate.")
		@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "JobPayRate created successfully"),
				@ApiResponse(responseCode = "500", description = "Internal server error") })
	    public ResponseEntity<JobPayRate> createJobPayRate(@RequestBody JobPayRate jobPayRate) {
	        JobPayRate createdJobPayRate = jobCreationService.saveJobPayRate(jobPayRate);
	        return new ResponseEntity<>(createdJobPayRate, HttpStatus.CREATED);
	    }

	    @GetMapping("/getAllJobPayRate")
	    public ResponseEntity<List<JobPayRate>> getAllJobPayRates() {
	        List<JobPayRate> jobPayRates = jobCreationService.getAllJobPayRates();
	        return new ResponseEntity<>(jobPayRates, HttpStatus.OK);
	    }

	    @GetMapping("/getJobPayRateById/{id}")
	    @Operation(summary = "Get JobPayRate by ID", description = "Retrieves JobPayRate by its ID.")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "JobPayRate retrieved successfully"),
				@ApiResponse(responseCode = "404", description = "JobPayRate not found") })
	    public ResponseEntity<JobPayRate> getJobPayRateById(@PathVariable Long id) {
	        JobPayRate jobPayRate = jobCreationService.getJobPayRateById(id);
	        if (jobPayRate == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(jobPayRate, HttpStatus.OK);
	    }

	    @GetMapping("/getJobPayRateByName/{name}")
		@Operation(summary = "Get JobPayRate by name", description = "Retrieves JobPayRate by its name.")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "JobPayRate retrieved successfully"),
				@ApiResponse(responseCode = "404", description = "JobPayRate not found") })
	    public ResponseEntity<JobPayRate> getJobPayRateByName(@PathVariable String name) {
	        JobPayRate jobPayRate = jobCreationService.getJobPayRateByName(name);
	        if (jobPayRate == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(jobPayRate, HttpStatus.OK);
	    }
	
	
	
	@GetMapping("/getAllCompanies")
	@Operation(summary = "Get all companies", description = "Retrieves a list of all companies.")
	@ApiResponse(responseCode = "200", description = "List of companies retrieved successfully")
	public ResponseEntity<List<CompanyDetailsMaster>> getAllCompanies() {
		List<CompanyDetailsMaster> companies = jobCreationService.getAllCompanies();
		return ResponseEntity.ok(companies);
	}

	@GetMapping("/getCompanyById/{id}")
	@Operation(summary = "Get company by ID", description = "Retrieves company by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Company retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Company not found") })
	public ResponseEntity<CompanyDetailsMaster> getCompanyById(@PathVariable Long id) {
		CompanyDetailsMaster company = jobCreationService.getCompanyById(id);
		if (company != null) {
			return ResponseEntity.ok(company);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/saveCompany")
	@Operation(summary = "Save company", description = "Creates a new company.")
	@ApiResponse(responseCode = "201", description = "Company created successfully")
	public ResponseEntity<CompanyDetailsMaster> createCompany(@RequestBody CompanyDetailsMaster company) {
		CompanyDetailsMaster createdCompany = jobCreationService.createCompany(company);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
	}

	@GetMapping("/getJobStatusById/{statusId}")
	@Operation(summary = "Get job status by ID", description = "Retrieves job status by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Job status retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Job status not found") })
	public ResponseEntity<JobStatus> getJobStatusById(@PathVariable Long statusId) {
		JobStatus jobStatus = jobCreationService.getJobStatusById(statusId);
		if (jobStatus != null) {
			return ResponseEntity.ok(jobStatus);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/getJobStatusAll")
	@Operation(summary = "Get all job statuses", description = "Retrieves a list of all job statuses.")
	@ApiResponse(responseCode = "200", description = "List of job statuses retrieved successfully")
	public ResponseEntity<List<JobStatus>> getAllJobStatuses() {
		List<JobStatus> jobStatuses = jobCreationService.getAllJobStatuses();
		return ResponseEntity.ok(jobStatuses);
	}

	@PostMapping("/addJobStatus")
	@Operation(summary = "Add job status", description = "Creates a new job status.")
	@ApiResponse(responseCode = "201", description = "Job status created successfully")
	public ResponseEntity<JobStatus> createJobStatus(@RequestBody JobStatus jobStatus) {
		JobStatus createdJobStatus = jobCreationService.createJobStatus(jobStatus);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdJobStatus);
	}

//	@PostMapping("/create")
//	@Operation(summary = "Create job", description = "Creates a new job.")
//	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Job created successfully"),
//			@ApiResponse(responseCode = "400", description = "Bad request"),
//			@ApiResponse(responseCode = "500", description = "Internal server error") })
//	public ResponseEntity<JobCreationDto> createJob(@RequestBody JobCreationDto jobCreationDto) {
//		try {
//			JobCreationDto createdJob = jobCreationService.createJob(jobCreationDto);
//			return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
//		} catch (IllegalArgumentException e) {
//			return ResponseEntity.badRequest().body(null);
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//		}
//	}
    // Decode the JWT token
//    Claims claims = Jwts.parser().setSigningKey("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437").parseClaimsJws(jwtToken).getBody();
//	 byte[] keyBytes = Decoders.BASE64.decode("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");
//	Key keys =  Keys.hmacShaKeyFor(keyBytes);
//	Claims claims = Jwts.parserBuilder().setSigningKey(keys).build().parseClaimsJws(jwtToken).getBody();
//    // Extract user ID from the decoded JWT claims
//    Long userId = Long.parseLong(claims.get("userId").toString());
//    System.out.println("This is User Id"+userId);
    // Call service method passing user ID
	@PostMapping("/create")
	@Operation(summary = "Create job", description = "Creates a new job.")
	@ApiResponses(value = { 
	    @ApiResponse(responseCode = "201", description = "Job created successfully"),
	    @ApiResponse(responseCode = "400", description = "Bad request"),
	    @ApiResponse(responseCode = "500", description = "Internal server error") 
	})
	public ResponseEntity<JobCreationDto> createJob(@RequestHeader("loggedInUser") String jwtToken, @RequestBody JobCreationDto jobCreationDto) {
//		System.out.println("This is jwtToken"+jwtToken);
		try {

			Long userId = Long.parseLong(jwtToken);
			System.out.println("\n\n\n\n This is User Id"+userId);
	        JobCreationDto createdJob = jobCreationService.createJob(jobCreationDto, userId);
	        System.out.println("This is Company Id"+createdJob.getCompanyId());
	        //
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(null);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
//	  private Key getSignKey() {
//	        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
//	        return Keys.hmacShaKeyFor(keyBytes);
//	    }
	    
	@PutMapping("/update")
	@Operation(summary = "Update job", description = "Updates an existing job.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Job updated successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<JobCreationDto> updateJob(@RequestParam Long jobId,
			@RequestBody JobCreationDto jobCreationDto) {
		try {
			JobCreationDto updatedJob = jobCreationService.updateJob(jobId, jobCreationDto);
			return ResponseEntity.ok(updatedJob);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/delete")
	@Operation(summary = "Delete job", description = "Deletes a job.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Job deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Job not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<String> deleteJob(@RequestParam Long jobId) {
		try {
			jobCreationService.softDeleteJob(jobId);
			return ResponseEntity.ok("Job with ID " + jobId + " has been successfully deleted");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to delete job: " + e.getMessage());
		}
	}
}
