package com.patientsvc.service;

import java.util.List;
import java.util.Optional;

import com.patientsvc.models.Patient;

public interface IPatientService {
	
	public List<Patient> getAllPatients(int pageNo, int pageSize, String sortBy);
	public Patient getPatientById(long id);
	public Patient addPatient(Patient patient);
	public Optional<Patient> updatePatient(Patient patient,long id);
	public void deletePatient(long id);
	

}
