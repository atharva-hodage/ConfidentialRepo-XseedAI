package com.xseedai.jobcreation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@Table(name = "hiring_team_member")
public class HiringTeamMember {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long teamMemberId;

	    @JsonBackReference 
	    @ManyToOne
	    @JoinColumn(name = "hiring_team_id", nullable = false)
	    private HiringTeam hiringTeam;

	    @Column(name = "name", nullable = false)
	    private String name;

	    @Column(name = "email")
	    private String email;

	    @Column(name = "role", nullable = false)
	    private String role;

	    @ManyToOne
	    @JoinColumn(name = "access_id", nullable = false)
	    private HiringTeamAccess hiringTeamAccess;
	    
	    
	    @Lob // Indicates that this attribute should be persisted as a large object (BLOB in database)
	    @Column(name = "image_data")
	    private byte[] imageData;
	}