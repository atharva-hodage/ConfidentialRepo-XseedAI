package com.xseedai.jobcreation.entity;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "JobPayRate")
public class JobPayRate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long jobpayrateId;
	
	private String jobPayRateName;
	
	@OneToMany(mappedBy = "jobPayRate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<JobCreation> jobCreation = new ArrayList<>();
}