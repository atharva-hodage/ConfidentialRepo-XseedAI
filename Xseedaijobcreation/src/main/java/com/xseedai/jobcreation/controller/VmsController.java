package com.xseedai.jobcreation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xseedai.jobcreation.entity.VmsMaster;
import com.xseedai.jobcreation.service.VmsService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@Tag(name = "VMS-Controller", description = "Endpoints for managing Vendor Management Systems")

@RestController
@RequestMapping("api/jobcreation")
@AllArgsConstructor

public class VmsController {

	@Autowired
	private VmsService vmsService;

	 @PostMapping("/addvms/{companyDetailsId}")
	 @Operation(summary = "Add a new VMS", description = "Creates a new Vendor Management System.")
	    @ApiResponse(responseCode = "201", description = "VMS created successfully")
	    public ResponseEntity<VmsMaster> createVmsMaster(@RequestBody VmsMaster vmsMaster, @PathVariable Long companyDetailsId) {
	        VmsMaster createdVmsMaster = vmsService.createVmsMaster(vmsMaster, companyDetailsId);
	        return new ResponseEntity<>(createdVmsMaster, HttpStatus.CREATED);
	    }
	 
	 @GetMapping("/getvms/{companyDetailsId}")
	 @Operation(summary = "Get VMS by company details ID", description = "Retrieves a list of VMS by company details ID.")
	    @ApiResponse(responseCode = "200", description = "List of VMS retrieved successfully")
	    @ApiResponse(responseCode = "204", description = "No content - No VMS found for the given company details ID")
	    public ResponseEntity<List<VmsMaster>> getVmsByCompanyDetailsId(@PathVariable Long companyDetailsId) {
	        List<VmsMaster> vmsList = vmsService.getVmsByCompanyDetailsId(companyDetailsId);
	        if (vmsList.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(vmsList, HttpStatus.OK);
	    }
}
