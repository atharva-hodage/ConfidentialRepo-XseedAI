package com.xseedai.jobcreation.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class JobCreation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long jobId;

	private String jobCode;
	
	
	@OneToOne(mappedBy="job")
	private HiringTeam hiringTeam;
	
//	@ManyToOne
//	@JoinColumn(name = "hiringteam_id")
//	@JsonIgnore
//	private HiringTeam hiringTeam;
	
	private Long userId;

	@ManyToOne
	@JoinColumn(name = "jobTitleId")
	@JsonIgnore
	private JobTitleMaster jobTitle;
	
	@ManyToOne()
	@JoinColumn(name = "companyId", nullable = false)
	private CompanyDetailsMaster companyDetailsMaster;

	@ManyToOne()
	@JoinColumn(name = "vmsId", nullable = false)
	@JsonIgnore
	private VmsMaster vmsMaster;

	@ManyToOne()
	@JoinColumn(name = "clientMasterId", nullable = false)
	@JsonIgnore
	private ClientMaster clientMaster;

	@ManyToOne
	@JoinColumn(name = "jobtypeId")
	@JsonIgnore
	private JobType jobtype;

	@ManyToOne
	@JoinColumn(name = "country_id")
	@JsonIgnore
	private Country country;

	@ManyToOne
	@JoinColumn(name = "state_id")
	@JsonIgnore
	private State state;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "city_id")
	private City city;


	@OneToMany(mappedBy = "jobCreation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<HiringCustomSubStage> hiringCustomSubStage = new ArrayList<>();
	
	private int numberOfOpenings;
//
	private Long maxSubmission;

	private Long billRate;

	private Long minimumPayRate;

	private Date requirementOpenDate;

	private Date requirementCloseDate;

	private Date contractStartDate;

	private Date contractEndDate;

	private Long duration;

	@ManyToOne
	@JoinColumn(name = "jobCurrencyId")
	@JsonIgnore
	private Currency currency;

	@ManyToOne
	@JoinColumn(name = "jobPayRateId")
	@JsonIgnore
	private JobPayRate jobPayRate;
	
	
	@ManyToOne
	@JoinColumn(name = "jobStatusId")
	@JsonIgnore
	private JobStatus jobStatus;

	@Column(name = "createdBy", length = 36, nullable = false)
	private String createdBy;

	@Column(name = "createdOn", nullable = false)
	private LocalDateTime createdOn;
//
	@Column(name = "modifiedBy", length = 36)
	private String modifiedBy;

	@Column(name = "modifiedOn")
	private LocalDateTime modifiedOn;

	private String jobDescription;

	@Column(name = "deleted")
	private Boolean deleted = false;

	public boolean isDeleted() {
		return deleted != null && deleted;
	}
//
	private List<Long> skillId = new ArrayList<>();
}