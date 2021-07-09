package com.patientsvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.patientsvc.aspect.Loggable;
import com.patientsvc.models.Patient;
import com.patientsvc.repository.IPatientRepository;
import com.patientsvc.feign.UserServiceClient;
import com.patientsvc.models.User;

@Service
public class PatientServiceImpl implements IPatientService {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	IPatientRepository patientRepository;
	
	@Autowired
	UserServiceClient userServiceClient;

	//fetch all patients 
	@Override
	@Transactional(readOnly=true)
	@Loggable
	public List<Patient> getAllPatients(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Patient> pagedResult = patientRepository.findAll(paging);
		
		if(pagedResult.hasContent())
		{
			return pagedResult.getContent();
		}else
		{
			return new ArrayList<Patient>();
		}
	}

	/*Spring persistance implmenation using entity manager
	 * Fetch patient by id
	 */
	@Override
	@Transactional(readOnly=true)
	@Loggable
	public Patient getPatientById(long id) {
		// TODO Auto-generated method stub
		Patient patient = (Patient) entityManager.find(Patient.class, id);
		return patient;
	}

	
	/*Spring Persistance Implmentation using Entity Manager
	 * create patient record
	 */
	@Override
	@Transactional
	@Loggable
	public Patient addPatient(Patient patient) {
		entityManager.persist(patient);
		return patient;
	}
	


	/*Spring persistance Implementation using Transaction Manager
	 * update patient record
	 */
	@Override
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW,
					rollbackFor = Exception.class,
					noRollbackFor = EntityNotFoundException.class)
	public Optional<Patient> updatePatient(Patient updatedPatient, long id) {
		// TODO Auto-generated method stub
		long user_id = updatedPatient.getUser_id();
		User user = getUserServiceData(user_id);
		Optional<Patient> patientDetails = patientRepository.findById(id);
		 if(patientDetails.isPresent())
		 {
			Patient patient= patientDetails.get();
			patient.setName(updatedPatient.getName());
			patient.setPhoneno(updatedPatient.getPhoneno());
			patient.setEmail(updatedPatient.getEmail());
			patient.setDateofvisit(updatedPatient.getDateofvisit());
			patient.setComplain(updatedPatient.getComplain());
			patientRepository.save(patient);
		}
		 return patientDetails;
	}
	
	//Feign Client to fetch user details from user module API
	public User getUserServiceData(long user_id) {
		return userServiceClient.getUserById(user_id);
	}

	/*Spring persistance Implementation using Transaction Manager
	 * delete patient record
	 */
	@Override
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW,
					rollbackFor = Exception.class,
					noRollbackFor = EntityNotFoundException.class)
	public void deletePatient(long id) {
		patientRepository.deleteById(id);
	}
	
	

}
