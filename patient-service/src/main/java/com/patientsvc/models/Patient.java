package com.patientsvc.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(notes = "The database generated user ID")
	long Id;
	
	@NotEmpty
	@ApiModelProperty(notes = "patient name")
	String name;
	
	@NotEmpty
	@ApiModelProperty(notes = "complain details")
	String complain;
	
	@ApiModelProperty(notes = "patient's phone number")
	String phoneno;
	
	@Email
	@ApiModelProperty(notes = "patient's email address")
	String email;
	
	@Column(name="date_of_visit")
	@ApiModelProperty(notes = "date of visit")
	Timestamp dateofvisit;
	
	@Column(name="user_id")
	@ApiModelProperty(notes = "user Id")
	long user_id;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
