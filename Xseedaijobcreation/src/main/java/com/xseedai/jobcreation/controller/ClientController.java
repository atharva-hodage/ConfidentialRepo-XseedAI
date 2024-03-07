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

import com.xseedai.jobcreation.entity.ClientMaster;
import com.xseedai.jobcreation.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;


@Tag(
	name="Client-Controller"	)
@RestController
@RequestMapping("api/jobcreation")
@AllArgsConstructor
public class ClientController {

	@Autowired
	private ClientService clientService;

	@PostMapping("/addclient/{vmsId}")
	 @Operation(summary = "Create a new client",
     description = "Creates a new client associated with the specified vmsId.")
	  @ApiResponses(value = {
	            @ApiResponse(responseCode = "201", description = "Client created successfully"),
	            @ApiResponse(responseCode = "404", description = "Virtual machine system not found")
	    })
	public ResponseEntity<ClientMaster> createClient(@RequestBody ClientMaster clientMaster,@Parameter(description = "The ID of the vms") @PathVariable Long vmsId) {
		try {
			ClientMaster createdClient = clientService.createClient(clientMaster, vmsId);
			return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getclient/{vmsId}")
	 @Operation(summary = "Get clients by vmsId",
     description = "Retrieves a list of clients associated with the specified vmsId.")
@ApiResponses(value = {
  @ApiResponse(responseCode = "200", description = "List of clients retrieved successfully"),
  @ApiResponse(responseCode = "404", description = "Virtual machine system not found")
})
	public ResponseEntity<List<ClientMaster>> getClientsByVmsId( @Parameter(description = "The ID of vms")@PathVariable Long vmsId) {
		List<ClientMaster> clients = clientService.getClientsByVmsId(vmsId);
		return new ResponseEntity<>(clients, HttpStatus.OK);
	}
}
