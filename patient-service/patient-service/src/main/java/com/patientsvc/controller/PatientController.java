package com.patientsvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patientsvc.models.Patient;
import com.patientsvc.service.IPatientService;

@RestController
@RequestMapping("/patientsvc")
public class PatientController {
	
	@Autowired
	IPatientService patientService;
	
	@GetMapping("/patients")
	public ResponseEntity<List<Patient>> getAllPatients(
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		return null;
		
	}

}
