package com.patientsvc.models;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="patient")
public class Patient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	long Id;
	
	@NotEmpty
	String name;
	
	@NotEmpty
	String complain;
	
	String phoneno;
	
	@Email
	String email;
	
	@Column(name="date_of_visit")
	Timestamp dateofvisit;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
