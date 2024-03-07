package com.xseedai.jobcreation.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xseedai.jobcreation.dto.RecruiterDetailsDTO;

import java.util.List;

@FeignClient(name = "identity-service") // Name is the application name of the identity service
public interface IdentityServiceFeignClient {

    @GetMapping(value="user/by-email",consumes = "application/json")
    List<RecruiterDetailsDTO> getRecruitersByEmail(@RequestParam String email);

    @GetMapping(value ="user/get-all-recruiters",consumes = "application/json")
    ResponseEntity<List<RecruiterDetailsDTO>> getUsersByRoleId();
    
    @GetMapping(value ="user/username-by-id",consumes = "application/json")
    ResponseEntity<String> getUsernameByUserId(@RequestParam("id") Long id);

    
}