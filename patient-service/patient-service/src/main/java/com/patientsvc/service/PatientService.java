package com.patientsvc.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patientsvc.models.Patient;

@Service
public class PatientService implements IPatientService {
	
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Patient> getAllPatient(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

	//Spring Persistance Implmenation using Entity Manager
	@Override
	public Patient getPatientById(long id) {
		// TODO Auto-generated method stub
		Patient patient = (Patient) entityManager.find(Patient.class, id);
		return patient;
	}

	
	//Spring Persistance Implmenation using Entity Manager
	@Override
	@Transactional
	public Patient addPatient(Patient patient) {
		entityManager.persist(patient);
		return patient;
	}
	
	@Override
	public void deleterPatient(long id) {
		
		
	}

	//Spring persistance Implementation using Transaction Manager
	@Override
	@Transactional
	public Patient updatePatient(Patient patient, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
