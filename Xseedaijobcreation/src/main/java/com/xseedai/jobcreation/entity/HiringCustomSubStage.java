package com.xseedai.jobcreation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HiringCustomSubStage")
public class HiringCustomSubStage  {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long hiringCustomSubStageId;

	    @Column(nullable = false)
	    private String customStageName;
	    
	    @ManyToOne
	    @JoinColumn(name = "stageId")
	    @JsonIgnore
	    private HiringStage stage;


	    
	    @ManyToOne
	    @JoinColumn(name = "jobCreationId", nullable = false)
	    @JsonIgnore
	    private JobCreation jobCreation;
	    
	    @Column(name = "isSelectedStep")
	    private boolean isSelected;
	    
	    @Column(name = "isDefaultStep")
	    private boolean isDefault;

}
