package com.xseedai.jobcreation.service.serviceimpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.xseedai.jobcreation.dto.JobCreationDto;
import com.xseedai.jobcreation.dto.JobListingDto;
import com.xseedai.jobcreation.entity.JobCreation;
import com.xseedai.jobcreation.entity.JobStatus;
import com.xseedai.jobcreation.entity.State;
import com.xseedai.jobcreation.repository.JobCreationRepository;
import com.xseedai.jobcreation.repository.JobStatusRepository;
import com.xseedai.jobcreation.service.JobListingService;
import org.springframework.data.jpa.domain.Specification;

@Service
public class JobListingServiceImpl implements JobListingService {

	@Autowired
	private JobCreationRepository jobCreationRepository;
	
	@Autowired
    private  ModelMapper modelMapper;
	
	@Autowired
	private JobStatusRepository jobStatusRepository;
//	@Override
//	public List<JobListingDto> getAllJobs() {
//		List<JobCreation> jobCreations = jobCreationRepository.findAll();
//		return jobCreations.stream().filter(job -> !job.isDeleted()).map(this::mapToDto).collect(Collectors.toList());
//	}
//
//	private JobListingDto mapToDto(JobCreation jobCreation) {
//		JobListingDto dto = new JobListingDto();
//
//		// we get these data from jobs table directly
//		dto.setJobId(jobCreation.getJobId());
//		dto.setJobCode(jobCreation.getJobCode());
//		dto.setBillRate(jobCreation.getBillRate());
//		dto.setRequirementOpenDate(jobCreation.getRequirementOpenDate());
//		dto.setRequirementCloseDate(jobCreation.getRequirementCloseDate());
//		dto.setJobCode(jobCreation.getJobCode());
//		dto.setJobId(jobCreation.getJobId());
//
//		// Set All Id's
//		dto.setCompanyId(jobCreation.getCompanyDetailsMaster().getCompanyDetailsId());
//		dto.setJobTypeId(jobCreation.getJobtype().getJobtypeId());
//		dto.setJobStatusId(jobCreation.getJobStatus().getStatusId());
//		dto.setVmsId(jobCreation.getVmsMaster().getVmsId());
//		dto.setClientId(jobCreation.getClientMaster().getClientId());
////		dto.setExpiringId(jobCreation.get);
//
//		// we get these data from other tables mapped with jobs table
//		dto.setJobTitile(jobCreation.getJobTitle().getJobTitle());
//		dto.setCountryName(jobCreation.getCountry().getCountryName());
//		dto.setCompamyName(jobCreation.getCompanyDetailsMaster().getCompanyName());
//		dto.setJobStatus(jobCreation.getJobStatus().getStatus());
//		dto.setJobType(jobCreation.getJobtype().getJobTypeName());
//		dto.setVmsName(jobCreation.getVmsMaster().getVmsName());
//		dto.setClientName(jobCreation.getClientMaster().getClientName());
//
//		// Calculate and set expiration date as string
//		LocalDate startDate = LocalDate.now();
//		LocalDate endDate = jobCreation.getRequirementCloseDate().toInstant().atZone(ZoneId.systemDefault())
//				.toLocalDate();
//		long daysUntilExpiration = ChronoUnit.DAYS.between(startDate, endDate);
//		dto.setExpiring(String.valueOf(daysUntilExpiration) + " days");
//
//		// For getting state name, State is mapped to country using list
//		List<State> states = jobCreation.getCountry().getState();
//		StringBuilder stateNames = new StringBuilder();
//		for (State state : states) {
//			stateNames.append(state.getStateName()).append(", ");
//		}
//		// Remove the last comma and space
//		if (stateNames.length() > 0) {
//			stateNames.setLength(stateNames.length() - 2);
//		}
//		dto.setStateName(stateNames.toString());
//
//		return dto;
//	}

	// Long type of Input

	@Override
	public List<JobListingDto> getAllFilteredJob(List<Long> statuses, List<Long> companies, List<Long> vms,
			List<String> expiring, List<Long> jobTypes, List<Long> clients) {
		List<JobCreation> jobCreations = jobCreationRepository.findAll();
		return jobCreations.stream().filter(job -> !job.isDeleted()).map(this::mapToDto)
				.filter(dto -> filterJobA(dto, statuses, companies, vms, expiring, jobTypes, clients))
				.collect(Collectors.toList());
	}

	private boolean filterJobA(JobListingDto dto, List<Long> statuses, List<Long> companies, List<Long> vms,
			List<String> expiring, List<Long> jobTypes, List<Long> clients) {

		if (statuses != null && !statuses.isEmpty() && !statuses.contains(dto.getJobStatusId())) {
			return false;
		}

		if (companies != null && !companies.isEmpty() && !companies.contains(dto.getCompanyId())) {
			return false;
		}

		if (vms != null && !vms.isEmpty() && !vms.contains(dto.getVmsId())) {
			return false;
		}

		if (expiring != null && !expiring.isEmpty() && !expiring.contains(dto.getExpiring())) {
			return false;
		}

		if (jobTypes != null && !jobTypes.isEmpty() && !jobTypes.contains(dto.getJobTypeId())) {
			return false;
		}

		if (clients != null && !clients.isEmpty() && !clients.contains(dto.getClientId())) {
			return false;
		}

		return true;
	}
	@Override
	public Page<JobListingDto> getAllFilteredJob(List<Long> statuses, List<Long> companies, List<Long> vms,
	                                              List<String> expiring, List<Long> jobTypes, List<Long> clients,
	                                              Pageable pageable) {
		   Page<JobCreation> jobCreationsPage = jobCreationRepository.findAll(pageable);
		    List<JobListingDto> filteredDtos = jobCreationsPage.getContent().stream()
		    		.filter(job -> !job.isDeleted())
		            .map(this::mapToDto)
		            .filter(dto -> filterJobA(dto, statuses, companies, vms, expiring, jobTypes, clients))
		            .collect(Collectors.toList());
		    return new PageImpl<>(filteredDtos, pageable, jobCreationsPage.getTotalElements());
	}

	// String Type of Input

//	@Override
//	public List<JobListingDto> getAllFilteredJobsStringType(List<String> statuses, List<String> companies,
//			List<String> vms, List<String> expiring, List<String> jobTypes, List<String> clients) {
//		List<JobCreation> jobCreations = jobCreationRepository.findAll();
//		return jobCreations.stream().filter(job -> !job.isDeleted()).map(this::mapToDto)
//				.filter(dto -> filterJob(dto, statuses, companies, vms, expiring, jobTypes, clients))
//				.collect(Collectors.toList());
//	}
//
//	private boolean filterJob(JobListingDto dto, List<String> statuses, List<String> companies, List<String> vms,
//			List<String> expiring, List<String> jobTypes, List<String> clients) {
//
//		if (statuses != null && !statuses.isEmpty() && !statuses.contains(dto.getJobStatus())) {
//			return false;
//		}
//
//		if (companies != null && !companies.isEmpty() && !companies.contains(dto.getCompamyName())) {
//			return false;
//		}
//
//		if (vms != null && !vms.isEmpty() && !vms.contains(dto.getVmsName())) {
//			return false;
//		}
//
//		if (expiring != null && !expiring.isEmpty() && !expiring.contains(dto.getExpiring())) {
//			return false;
//		}
//
//		if (jobTypes != null && !jobTypes.isEmpty() && !jobTypes.contains(dto.getJobType())) {
//			return false;
//		}
//
//		if (clients != null && !clients.isEmpty() && !clients.contains(dto.getClientName())) {
//			return false;
//		}
//
//		return true;
//	}

	@Override
	public Page<JobListingDto> getAllJobs(Pageable pageable) {
	    Page<JobCreation> jobCreations = jobCreationRepository.findAll(pageable);
	    return jobCreations.map(this::mapToDto);
	}

	private JobListingDto mapToDto(JobCreation jobCreation) {
	    JobListingDto dto = new JobListingDto();

	    // Set data directly from JobCreation entity
	    dto.setJobId(jobCreation.getJobId());
	    dto.setJobCode(jobCreation.getJobCode());
	    dto.setBillRate(jobCreation.getBillRate());
	    dto.setRequirementOpenDate(jobCreation.getRequirementOpenDate());
	    dto.setRequirementCloseDate(jobCreation.getRequirementCloseDate());

	    // Set IDs
	    dto.setCompanyId(jobCreation.getCompanyDetailsMaster().getCompanyDetailsId());
	    dto.setJobTypeId(jobCreation.getJobtype().getJobtypeId());
	    dto.setJobStatusId(jobCreation.getJobStatus().getStatusId());
	    dto.setVmsId(jobCreation.getVmsMaster().getVmsId());
	    dto.setClientId(jobCreation.getClientMaster().getClientId());

	    // Set data from related entities
	    dto.setJobTitile(jobCreation.getJobTitle().getJobTitle());
	    dto.setCountryName(jobCreation.getCountry().getCountryName());
	    dto.setCompamyName(jobCreation.getCompanyDetailsMaster().getCompanyName());
	    dto.setJobStatus(jobCreation.getJobStatus().getStatus());
	    dto.setJobType(jobCreation.getJobtype().getJobTypeName());
	    dto.setVmsName(jobCreation.getVmsMaster().getVmsName());
	    dto.setClientName(jobCreation.getClientMaster().getClientName());

	    // Calculate and set expiration date as string
	    LocalDate startDate = LocalDate.now();
	    LocalDate endDate = jobCreation.getRequirementCloseDate().toInstant().atZone(ZoneId.systemDefault())
	            .toLocalDate();
	    long daysUntilExpiration = ChronoUnit.DAYS.between(startDate, endDate);
	    dto.setExpiring(String.valueOf(daysUntilExpiration) + " days");

	    // Set state name
	    if (jobCreation.getState() != null) {
	        dto.setStateName(jobCreation.getState().getStateName());
	    }

	    // Set city name
	    if (jobCreation.getCity() != null) {
	        dto.setCityName(jobCreation.getCity().getCityName());
	    }

	    return dto;
	}

	@Override
	public Page<JobListingDto> searchJobs(String keyword, Pageable pageable) {
	    Specification<JobCreation> spec = (root, query, criteriaBuilder) -> {
	        return criteriaBuilder.or(
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("jobCode")), "%" + keyword.toLowerCase() + "%"),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("jobTitle").get("jobTitle")), "%" + keyword.toLowerCase() + "%"),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("companyDetailsMaster").get("companyName")), "%" + keyword.toLowerCase() + "%"),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("country").get("countryName")), "%" + keyword.toLowerCase() + "%"),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("state").get("stateName")), "%" + keyword.toLowerCase() + "%"),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("city").get("cityName")), "%" + keyword.toLowerCase() + "%"),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("jobStatus").get("status")), "%" + keyword.toLowerCase() + "%"),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("jobtype").get("jobTypeName")), "%" + keyword.toLowerCase() + "%"),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("clientMaster").get("clientName")), "%" + keyword.toLowerCase() + "%"),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("vmsMaster").get("vmsName")), "%" + keyword.toLowerCase() + "%")
	        );
	    };
	    return jobCreationRepository.findAll(spec, pageable).map(this::mapToDto);
	}

	@Override
	public JobCreationDto closeJob(Long jobId) {
		try {
            JobCreation existingJob = jobCreationRepository.findById(jobId)
                    .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

            JobStatus closedStatus = jobStatusRepository.findById(2L) //  ID 2 corresponds to "Closed"
                    .orElseThrow(() -> new IllegalArgumentException("Job Status not found with ID: 2")); // Adjust this exception message as needed

            existingJob.setJobStatus(closedStatus); // Set the job status to "Closed"

            // Set modified details
            existingJob.setModifiedBy("API"); // here the name of the recruiter will be appended
            existingJob.setModifiedOn(LocalDateTime.now());

            JobCreation updatedJob = jobCreationRepository.save(existingJob);

            return modelMapper.map(updatedJob, JobCreationDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to close job: " + e.getMessage());
        }
    }
	}

//	private JobListingDto mapToDto(JobCreation jobCreation) {
//	    JobListingDto dto = new JobListingDto();
//	    // Map fields from JobCreation to JobListingDto
//	    dto.setJobId(jobCreation.getJobId());
//	    dto.setJobCode(jobCreation.getJobCode());
//	    dto.setJobTitile(jobCreation.getJobTitle().getJobTitle());
//	    dto.setCompamyName(jobCreation.getCompanyDetailsMaster().getCompanyName());
//	    dto.setCountryName(jobCreation.getCountry().getCountryName());
//	    dto.setStateName(jobCreation.getState().getStateName());
//	    dto.setCityName(jobCreation.getCity().getCityName());
//	    dto.setBillRate(jobCreation.getBillRate());
//	    dto.setRequirementOpenDate(jobCreation.getRequirementOpenDate());
//	    dto.setRequirementCloseDate(jobCreation.getRequirementCloseDate());
//	    dto.setJobStatus(jobCreation.getJobStatus().getStatus());
//	    dto.setJobType(jobCreation.getJobtype().getJobTypeName());
//	    dto.setClientName(jobCreation.getClientMaster().getClientName());
//	    dto.setVmsName(jobCreation.getVmsMaster().getVmsName());
//	    // Set other fields similarly
//	    return dto;
//	}


