package com.patientsvc.dao;

import java.util.List;

import com.patientsvc.models.Patient;

public interface IPatientDAO {
	
	public List<Patient> getAllPatient(int pageNo, int pageSize, String sortBy);
	public Patient getPatientById(long id);
	public Patient addPatient(Patient patient);
	public Patient updatePatient(Patient patient,long id);
	public void deleterPatient(long id);
	

}
