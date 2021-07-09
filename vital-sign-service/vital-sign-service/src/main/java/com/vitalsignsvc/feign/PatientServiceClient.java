package com.vitalsignsvc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vitalsignsvc.models.Patient;

@FeignClient(name="patient-svc-client" , url="http://localhost:8082", configuration = PatientServiceClientConfiguration.class)
public interface PatientServiceClient {

	@GetMapping("/patientsvc/patients/{id}")
	Patient getPatientById(@PathVariable long id);
	
}
