package com.patientsvc.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientDto {

	long Id;	
	String name;
	String complain;
	String phoneno;
	String email;
	LocalDateTime dateofvisit;
	long user_id;
	
}
