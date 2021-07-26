package com.patientsvc.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

	long Id;	
	String name;
	String complain;
	String phoneno;
	String email;
	LocalDateTime dateofvisit;
	long user_id;
	
}
