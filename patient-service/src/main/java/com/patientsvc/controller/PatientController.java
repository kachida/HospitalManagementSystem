package com.patientsvc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patientsvc.aspect.Loggable;
import com.patientsvc.models.AuthenticationRequest;
import com.patientsvc.models.AuthenticationResponse;
import com.patientsvc.models.Patient;
import com.patientsvc.security.JwtUtil;
import com.patientsvc.service.IPatientService;
import com.patientsvc.service.MyUserDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/patients")
@EnableDiscoveryClient
@Api(value="PatientController", description="Operations pertaining to patients in patient module API")
public class PatientController {

	private final IPatientService patientService;
	private final AuthenticationManager authenticationManager;
	private final MyUserDetailService userDetailsService;
	private final JwtUtil jwtUtilToken;
	
	public PatientController(IPatientService patientService, AuthenticationManager authenticationManager,
			MyUserDetailService userDetailsService, JwtUtil jwtUtilToken) {
		super();
		this.patientService = patientService;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtilToken = jwtUtilToken;
	}

	// fetch all patients
	@GetMapping("/")
	@Loggable
	@ApiOperation(value = "Retrieve all Patients with pagination and sorting supported", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public List<Patient> getAllPatients(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "Id") String sortBy) {
		List<Patient> patientList = patientService.getAllPatients(pageNo, pageSize, sortBy);
		return patientList;

	}

	// fetch patient by id
	@GetMapping("/{id}")
	@Loggable
	@ApiOperation(value = "Retrieve patient details with given id", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token"),
	})
	public Patient getUserById(@PathVariable long id) {
		return patientService.getPatientById(id);
	}

	// create new patient record
	@PostMapping("/")
	@Loggable
	@ApiOperation(value = "To create a new patient record", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Successfully the record is created"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public Patient savePatientRecord(@RequestBody Patient patient) {
		return patientService.addPatient(patient);
	}

	// update existing patient
	@PutMapping("/{id}")
	@Loggable
	@ApiOperation(value = "To update the existing  patient", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully the record is updated"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public Patient updatePatientRecord(@RequestBody Patient patient, @PathVariable long id) {
		Optional<Patient> patientDetails = patientService.updatePatient(patient, id);
		return patientDetails.get();
	}

	// delete patients
	@DeleteMapping("/{id}")
	@Loggable
	@ApiOperation(value = "To delete the existing  patient record", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully the record is deleted"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public void deletePatientRecord(@PathVariable long id) {
		patientService.deletePatient(id);
	}

	//Authenticate
	@PostMapping("/authenticate")
	@Loggable
	@ApiOperation(value = "To generate a new JWT Token", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully the jwt token is generated"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
	{
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtilToken.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
}
