package com.vitalsignsvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VitalsignDto {
	
	long id;
	float temperature;
	float bloodsugar;
	float weight;
	float height;
	float spo2;
	float pulse;
	String patient_name;
	String user_name;
	long user_id;
	long patient_id;
	

}
