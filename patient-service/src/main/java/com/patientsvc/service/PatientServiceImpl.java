package com.patientsvc.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patientsvc.aspect.Loggable;
import com.patientsvc.dto.PatientDto;
import com.patientsvc.models.Patient;
import com.patientsvc.repository.IPatientRepository;
import com.patientsvc.feign.UserServiceClient;
import com.patientsvc.models.User;

@Service
public class PatientServiceImpl implements IPatientService {

	@PersistenceContext
	private EntityManager entityManager;

	private final IPatientRepository patientRepository;
	private final UserServiceClient userServiceClient;
	private final ModelMapper modelMapper;
	private final String INDEX="patientdata";
	private final String TYPE = "patients";
	private final RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("elasticsearch",9200,"http"), new HttpHost("elasticsearch",9300,"http")));
	private ObjectMapper objectMapper = new ObjectMapper();

	public PatientServiceImpl(IPatientRepository patientRepository, UserServiceClient userServiceClient,
			ModelMapper modelMapper) {
		super();
		this.patientRepository = patientRepository;
		this.userServiceClient = userServiceClient;
		this.modelMapper = modelMapper;
	}

	// fetch all patients
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public List<PatientDto> getAllPatients(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Patient> pagedResult = patientRepository.findAll(paging);
		List<Patient> patientList = pagedResult.getContent();
		Type listType = new TypeToken<List<PatientDto>>() {
		}.getType();
		List<PatientDto> patientDtoList = modelMapper.map(patientList, listType);
		return patientDtoList;
	}

	/*
	 * Spring persistance implmenation using entity manager Fetch patient by id
	 */
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public PatientDto getPatientById(long id) {
		// TODO Auto-generated method stub
		Patient patientDetails = (Patient) entityManager.find(Patient.class, id);
		PatientDto patientDto = modelMapper.map(patientDetails, PatientDto.class);
		return patientDto;
	}

	/*
	 * Spring Persistance Implmentation using Entity Manager create patient record
	 */
	@Override
	@Transactional
	@Loggable
	public PatientDto addPatient(PatientDto patient) throws IOException {
		Patient patientDetails = modelMapper.map(patient, Patient.class);
		entityManager.persist(patientDetails);
		Map<String, Object> dataMap = objectMapper.convertValue(patientDetails, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE,String.valueOf(patientDetails.getId())).source(dataMap);
		restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		PatientDto patientDto = modelMapper.map(patientDetails, PatientDto.class);
		return patientDto;
	}

	/*
	 * Spring persistance Implementation using Transaction Manager update patient
	 * record
	 */
	@Override
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
	public PatientDto updatePatient(PatientDto updatedPatient, long id) {
		// TODO Auto-generated method stub
		Patient patient = modelMapper.map(updatedPatient, Patient.class);
		Patient patientEntity = patientRepository.findById(id)
				.orElseThrow(() -> new java.util.NoSuchElementException());
		;
		patientEntity.setName(patient.getName());
		patientEntity.setPhoneno(patient.getPhoneno());
		patientEntity.setEmail(patient.getEmail());
		patientEntity.setDateofvisit(patient.getDateofvisit());
		patientEntity.setComplain(patient.getComplain());
		patientRepository.save(patientEntity);
		PatientDto patientDto = modelMapper.map(patientEntity, PatientDto.class);
		return patientDto;
	}

	// Feign Client to fetch user details from user module API
	public User getUserServiceData(long user_id) {
		return userServiceClient.getUserById(user_id);
	}

	/*
	 * Spring persistance Implementation using Transaction Manager delete patient
	 * record
	 */
	@Override
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
	public void deletePatient(long id) {
		patientRepository.deleteById(id);
	}

}
