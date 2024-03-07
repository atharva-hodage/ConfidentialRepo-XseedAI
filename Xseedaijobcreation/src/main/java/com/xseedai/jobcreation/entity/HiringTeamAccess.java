package com.xseedai.jobcreation.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;





@Entity
@Data
@Table(name = "Hiring_Team_Access")

public class HiringTeamAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long accessId;

    @Column(name = "name", nullable = false)
    private String accessName;

}