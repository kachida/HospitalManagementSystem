package com.vitalsignsvc.service;

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
import com.vitalsignsvc.aspect.Loggable;
import com.vitalsignsvc.dto.VitalsignDto;
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
	private final ModelMapper modelMapper;
	private final String INDEX="vitalsigndata";
	private final String TYPE = "vitalsigns";
	private final RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("elasticsearch",9200,"http"), new HttpHost("elasticsearch",9300,"http")));
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public VitalsignServiceImpl( 
			IVitalsignRepository vitalsignRepository,
			PatientServiceClient patientServiceClient, 
			UserServiceClient userServiceClient, 
			ModelMapper modelMapper) {
		super();
		this.vitalsignRepository = vitalsignRepository;
		this.patientServiceClient = patientServiceClient;
		this.userServiceClient = userServiceClient;
		this.modelMapper = modelMapper;
	}

	// fetch all vitalsign records
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public List<VitalsignDto> getAllVitalSignRecords(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Vitalsign> pagedResult = vitalsignRepository.findAll(paging);
		List<Vitalsign> vitalSignList = pagedResult.getContent();
		Type listType = new TypeToken<List<VitalsignDto>>() {}.getType();
		List<VitalsignDto> vitalsignDtoList =  modelMapper.map(vitalSignList, listType);
		return vitalsignDtoList;
	}

	/*
	 * Spring persistance implmenation using entity manager Fetch vitalsign by id
	 */
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public VitalsignDto getVitalsignRecordById(long id) {
		// TODO Auto-generated method stub
		Vitalsign vitalsignDetails = vitalsignRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException());
		VitalsignDto vitalsignDto = modelMapper.map(vitalsignDetails, VitalsignDto.class);
		return vitalsignDto;
	}

	/*
	 * Spring persistance implmentation using entity manager create vitalsign record
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@Loggable
	public VitalsignDto addVitalsignRecord(VitalsignDto vitalsignDto) throws IOException {
		
		Vitalsign myVitalsign = modelMapper.map(vitalsignDto, Vitalsign.class);
		Vitalsign vitalsignEntity = vitalsignRepository.save(myVitalsign);
		Map<String, Object> dataMap = objectMapper.convertValue(vitalsignEntity, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE,String.valueOf(vitalsignEntity.getId())).source(dataMap);
		restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		VitalsignDto vitalsign = modelMapper.map(vitalsignEntity, VitalsignDto.class);
		return vitalsign;
	}

	/*
	 * Spring persistance implementation using transaction manager update vitalsign
	 * record
	 */
	@Override
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
	public VitalsignDto updateVitalsignRecord(VitalsignDto updatedVitalSignDto, long id) {

		Vitalsign vitalsign = modelMapper.map(updatedVitalSignDto, Vitalsign.class);
		// Retrieve patient name with given Id from Patient Module using fiegn client
		long patient_id = vitalsign.getPatient_id();
		long user_id = vitalsign.getUser_id();
		User user = getUserServiceData(user_id);
		Patient patient = getPatientServiceData(patient_id);
		
	    Vitalsign vitalsignEntity = vitalsignRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException());;

	    vitalsignEntity.setBloodsugar(vitalsign.getBloodsugar());
	    vitalsignEntity.setHeight(vitalsign.getHeight());
	    vitalsignEntity.setPatient_id(vitalsign.getPatient_id());
	    vitalsignEntity.setPulse(vitalsign.getPulse());
	    vitalsignEntity.setSpo2(vitalsign.getSpo2());
	    vitalsignEntity.setTemperature(vitalsign.getTemperature());
	    vitalsignEntity.setWeight(vitalsign.getWeight());
	    vitalsignEntity.setPatient_id(vitalsign.getPatient_id());
	    vitalsignEntity.setUser_id(vitalsign.getUser_id());
	    vitalsignEntity.setPatient_name(patient.getName());
	    vitalsignEntity.setUser_name(user.getUsername());
		vitalsignRepository.save(vitalsignEntity);
		VitalsignDto vitalsignDto = modelMapper.map(vitalsignEntity,VitalsignDto.class);
		return vitalsignDto;
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
