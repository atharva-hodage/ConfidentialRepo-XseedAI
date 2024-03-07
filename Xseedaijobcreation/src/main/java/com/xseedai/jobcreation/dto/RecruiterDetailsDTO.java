package com.xseedai.jobcreation.dto;

import java.util.HashSet;
import java.util.Set;



import lombok.Data;

@Data
public class RecruiterDetailsDTO {
    
    private Long id;
     private String name;
     private String email;
    // private Set<Role> roles=new HashSet<>();
     private byte[] imageData;  // New field for image data
}