package com.vitalsignsvc.service;

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
import org.springframework.web.bind.annotation.GetMapping;

import com.vitalsignsvc.aspect.Loggable;
import com.vitalsignsvc.feign.PatientServiceClient;
import com.vitalsignsvc.feign.UserServiceClient;
import com.vitalsignsvc.models.Patient;
import com.vitalsignsvc.models.User;
import com.vitalsignsvc.models.Vitalsign;
import com.vitalsignsvc.repository.IVitalsignRepository;

@Service
public class VitalsignServiceImpl implements IVitalsignService {

	@PersistenceContext
	private EntityManager entityManager;

	
	private final IVitalsignRepository vitalsignRepository;
	private final PatientServiceClient patientServiceClient;
	private final UserServiceClient userServiceClient;
	
	public VitalsignServiceImpl( IVitalsignRepository vitalsignRepository,
			PatientServiceClient patientServiceClient, UserServiceClient userServiceClient) {
		super();
		this.vitalsignRepository = vitalsignRepository;
		this.patientServiceClient = patientServiceClient;
		this.userServiceClient = userServiceClient;
	}

	// fetch all vitalsign records
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public List<Vitalsign> getAllVitalSignRecords(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Vitalsign> pagedResult = vitalsignRepository.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Vitalsign>();
		}
	}

	/*
	 * Spring persistance implmenation using entity manager Fetch vitalsign by id
	 */
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public Vitalsign getVitalsignRecordById(long id) {
		// TODO Auto-generated method stub
		Vitalsign vitalSign = vitalsignRepository.findById(id).orElse(null);
		return vitalSign;
	}

	/*
	 * Spring persistance implmentation using entity manager create vitalsign record
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@Loggable
	public Vitalsign addVitalsignRecord(Vitalsign vitalSign) {
		
		return vitalsignRepository.save(vitalSign);
	}

	/*
	 * Spring persistance implementation using transaction manager update vitalsign
	 * record
	 */
	@Override
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
	public Optional<Vitalsign> updateVitalsignRecord(Vitalsign updatedVitalSign, long id) {

		long patient_id = updatedVitalSign.getPatient_id();
		long user_id = updatedVitalSign.getUser_id();

		User user = getUserServiceData(user_id);
		Patient patient = getPatientServiceData(patient_id);
		// Retrieve patient name with given Id from Patient Module using fiegn client
		Optional<Vitalsign> vitalsignDetails = vitalsignRepository.findById(id);
			Vitalsign vitalsign = vitalsignDetails.get();
			vitalsign.setBloodsugar(updatedVitalSign.getBloodsugar());
			vitalsign.setHeight(updatedVitalSign.getHeight());
			vitalsign.setPatient_id(updatedVitalSign.getPatient_id());
			vitalsign.setPulse(updatedVitalSign.getPulse());
			vitalsign.setSpo2(updatedVitalSign.getSpo2());
			vitalsign.setTemperature(updatedVitalSign.getTemperature());
			vitalsign.setWeight(updatedVitalSign.getWeight());
			vitalsign.setPatient_id(updatedVitalSign.getPatient_id());
			vitalsign.setUser_id(updatedVitalSign.getUser_id());
			vitalsign.setPatient_name(patient.getName());
			vitalsign.setUser_name(user.getUsername());
			vitalsignRepository.save(vitalsign);
		return vitalsignDetails;
	}

	//Feign Client to fetch user details from user module API
	public User getUserServiceData(long user_id) {
		return userServiceClient.getUserById(user_id);
	}

	//Feign Client to fetch patient details from patient module API
	public Patient getPatientServiceData(long patient_id) {
		return patientServiceClient.getPatientById(patient_id);
	}

	/*
	 * Spring persistance Implementation using Transaction Manager delete patient
	 * record
	 */
	@Override
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
	public void deleteVitalsignRecord(long id) {
		// TODO Auto-generated method stub
		vitalsignRepository.deleteById(id);
	}

}
