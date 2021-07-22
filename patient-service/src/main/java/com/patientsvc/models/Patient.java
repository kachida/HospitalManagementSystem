package com.patientsvc.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

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
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	Timestamp dateofvisit;
	
	@Column(name="user_id")
	@ApiModelProperty(notes = "user Id")
	long user_id;
	
	@Column(name="created_by")
	String createdBy;
	
	@Column(name="last_modified_by")
	String lastModifiedBy;
	
	@CreatedDate
    @Column(name="created_date")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createdDate;
	
	@LastModifiedDate
    @Column(name="last_modified_date")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime lastModifiedDate;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
