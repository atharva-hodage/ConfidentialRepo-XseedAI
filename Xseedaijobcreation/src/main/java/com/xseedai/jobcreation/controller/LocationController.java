package com.xseedai.jobcreation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xseedai.jobcreation.entity.City;
import com.xseedai.jobcreation.entity.Country;
import com.xseedai.jobcreation.entity.State;
import com.xseedai.jobcreation.service.LocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Location-Controller", description = "Endpoints for managing locations (countries, states, cities)")
@RestController
@RequestMapping("api/jobcreation")
@AllArgsConstructor

public class LocationController {

	@Autowired
	private LocationService locationService;

	@PostMapping("/addcountry")
	@Operation(summary = "Add a new country", description = "Creates a new country.")
	@ApiResponse(responseCode = "201", description = "Country created successfully")
	public ResponseEntity<Country> Country(@RequestBody Country country) {
		Country createdCountry = locationService.createCountry(country);
		return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);
	}

	@PostMapping("/addstate")
	@Operation(summary = "Add a new state", description = "Creates a new state and associates it with a country.")
	@ApiResponse(responseCode = "201", description = "State created successfully")
	@JsonIgnore
	public ResponseEntity<State> createState(@RequestBody State state, @RequestParam("countryId") Long countryId) {
		State createdState = locationService.createState(state, countryId);
		return new ResponseEntity<>(createdState, HttpStatus.CREATED);
	}

	@PostMapping("/addcity")
	@Operation(summary = "Add a new city", description = "Creates a new city and associates it with a state.")
	@ApiResponse(responseCode = "201", description = "City created successfully")
	public ResponseEntity<City> createCity(@RequestBody City city, @RequestParam("stateId") Long stateId) {
		City createdCity = locationService.createCity(city, stateId);
		return new ResponseEntity<>(createdCity, HttpStatus.CREATED);
	}

	@GetMapping("/getcountry")
	@Operation(summary = "Get all countries", description = "Retrieves a list of all countries.")
	@ApiResponse(responseCode = "200", description = "List of countries retrieved successfully")
	public ResponseEntity<List<Country>> getAllCountries() {
		List<Country> countries = locationService.getAllCountries();
		return ResponseEntity.ok().body(countries);
	}

	@GetMapping("/getstate")
	@Operation(summary = "Get states by country ID", description = "Retrieves a list of states by country ID.")
	@ApiResponse(responseCode = "200", description = "List of states retrieved successfully")
	public ResponseEntity<List<State>> getStatesByCountryId(@RequestParam("countryId") Long countryId) {
		List<State> states = locationService.getStatesByCountryId(countryId);
		return ResponseEntity.ok().body(states);
	}

	@GetMapping("/getcity")
	@Operation(summary = "Get cities by state ID", description = "Retrieves a list of cities by state ID.")
	@ApiResponse(responseCode = "200", description = "List of cities retrieved successfully")
	public ResponseEntity<List<City>> getCitiesByStateId(@RequestParam("stateId") Long stateId) {
		List<City> cities = locationService.getCitiesByStateId(stateId);
		return ResponseEntity.ok().body(cities);
	}
}
