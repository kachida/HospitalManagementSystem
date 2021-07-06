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

import com.vitalsignsvc.models.Patient;
import com.vitalsignsvc.models.Vitalsign;
import com.vitalsignsvc.repository.IPatientRepository;
import com.vitalsignsvc.repository.IVitalsignRepository;

@Service
public class VitalsignServiceImpl implements IVitalsignService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	IVitalsignRepository vitalsignRepository;
	
	@Autowired
	IPatientRepository patientRepository;
	
	//fetch all vitalsign records
	@Override
	@Transactional(readOnly=true)
	public List<Vitalsign> getAllVitalSignRecords(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Vitalsign> pagedResult = vitalsignRepository.findAll(paging);
		
		if(pagedResult.hasContent())
		{
			return pagedResult.getContent();
		}else
		{
			return new ArrayList<Vitalsign>();
		}
	}

	/*Spring persistance implmenation using entity manager
	 * Fetch vitalsign by id
	 */
	@Override
	@Transactional(readOnly=true)
	public Vitalsign getVitalsignRecordById(long id) {
		// TODO Auto-generated method stub
		Vitalsign vitalSign = vitalsignRepository.findById(id).orElse(null);
		return vitalSign;
	}
	
	
	/*Spring persistance implmentation using entity manager
	 * create vitalsign record
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Vitalsign addVitalsignRecord(Vitalsign vitalSign,long patient_id) {
		// TODO Auto-generated method stub
		Optional<Patient> patient=patientRepository.findById(patient_id);
		if(!patient.isEmpty())
		{
			vitalSign.setPatient_id(patient.get());
			vitalsignRepository.save(vitalSign);
		}
		
		return vitalSign;
	}


	/*Spring persistance implementation using transaction manager
	 * update vitalsign record
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,
					rollbackFor = Exception.class,
					noRollbackFor = EntityNotFoundException.class)
	public Optional<Vitalsign> updateVitalsignRecord(Vitalsign updatedVitalSign,long patient_id, long id) {
				Optional<Patient> patientDetails=patientRepository.findById(patient_id);
				Optional<Vitalsign> vitalsignDetails = vitalsignRepository.findById(id);
				 if(vitalsignDetails.isPresent() && patientDetails.isEmpty())
				 {
					Vitalsign vitalsign= vitalsignDetails.get();
					vitalsign.setBloodsugar(updatedVitalSign.getBloodsugar());
					vitalsign.setHeight(updatedVitalSign.getHeight());
					vitalsign.setPatient_id(updatedVitalSign.getPatient_id());
					vitalsign.setPulse(updatedVitalSign.getPulse());
					vitalsign.setSpo2(updatedVitalSign.getSpo2());
					vitalsign.setTemperature(updatedVitalSign.getTemperature());
					vitalsign.setWeight(updatedVitalSign.getWeight());
					vitalsign.setPatient_id(patientDetails.get());
					vitalsignRepository.save(vitalsign);
				}
				 return vitalsignDetails;
	}

	/*Spring persistance Implementation using Transaction Manager
	 * delete patient record
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,
					rollbackFor = Exception.class,
					noRollbackFor = EntityNotFoundException.class)
	public void deleteVitalsignRecord(long id) {
		// TODO Auto-generated method stub
		vitalsignRepository.deleteById(id);
	}

}
