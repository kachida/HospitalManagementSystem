package com.patientsvc.service;

import java.util.List;

import com.patientsvc.models.Patient;
import com.patientsvc.models.Patient;

public interface IPatientService {
	
	public List<Patient> getAllPatients(int pageNo, int pageSize, String sortBy);
	public List<Patient> getPatientsWithNameFilter(String query,int pageNo, int pageSize, String sortBy);
	public List<Patient> getPatientsWithMultipleFilter(String patientname, String role, String email,int pageNo, int pageSize, String sortBy);
	public Patient getPatientById(long id);
	public Patient addPatient(Patient patient);
	public Patient updatePatient(Patient patient,long id);
	public void deleterPatient(long id);
	

}
