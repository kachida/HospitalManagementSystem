package com.patientsvc.service;

import java.io.IOException;
import java.util.List;

import com.patientsvc.dto.PatientDto;


public interface IPatientService {
	
	public List<PatientDto> getAllPatients(int pageNo, int pageSize, String sortBy);
	public PatientDto getPatientById(long id);
	public PatientDto addPatient(PatientDto patient) throws IOException;
	public PatientDto updatePatient(PatientDto patient,long id);
	public void deletePatient(long id);
	

}
