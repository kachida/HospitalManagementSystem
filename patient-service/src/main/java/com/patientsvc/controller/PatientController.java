package com.patientsvc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.patientsvc.models.AuthenticationRequest;
import com.patientsvc.models.AuthenticationResponse;
import com.patientsvc.models.Patient;
import com.patientsvc.security.JwtUtil;
import com.patientsvc.service.IPatientService;
import com.patientsvc.service.MyUserDetailService;


@RestController
@RequestMapping("/patientsvc")
public class PatientController {

	@Autowired
	IPatientService patientService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtilToken;

	// fetch all patients
	@GetMapping("/patients")
	public ResponseEntity<List<Patient>> getAllPatients(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "Id") String sortBy) {
		List<Patient> patientList = patientService.getAllPatients(pageNo, pageSize, sortBy);

		return new ResponseEntity<List<Patient>>(patientList, new HttpHeaders(), HttpStatus.OK);

	}

	// fetch patient by id
	@GetMapping("/patients/{id}")
	public ResponseEntity<Patient> getUserById(@PathVariable long id) {
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientById(id));
	}

	// create new patient record
	@PostMapping("/patients")
	public ResponseEntity<Patient> savePatientRecord(@RequestBody Patient patient) {
		return new ResponseEntity<Patient>(patientService.addPatient(patient), HttpStatus.CREATED);
	}

	// update existing patient
	@PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatientRecord(@RequestBody Patient patient, @PathVariable long id) {
		Optional<Patient> patientDetails = patientService.updatePatient(patient, id);
		if (patientDetails.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(patient);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(patientDetails.get());
		}
	}

	// delete patients
	@DeleteMapping("/patients/{id}")
	public void deletePatientRecord(@PathVariable long id) {
		patientService.deletePatient(id);
	}

	//Authenticate
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
	{
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtilToken.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
}
