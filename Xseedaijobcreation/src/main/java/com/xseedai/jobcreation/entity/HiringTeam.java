package com.xseedai.jobcreation.entity;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "hiring_team")
public class HiringTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long hiringTeamId;

    @OneToOne
    @JoinColumn(name = "job_id", unique = true, nullable = false)
    private JobCreation job;

    //@Transient
    @JsonManagedReference
    @OneToMany(mappedBy = "hiringTeam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HiringTeamMember> teamMembers=new ArrayList<>();
}
