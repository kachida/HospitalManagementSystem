package com.vitalsignsvc.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModelProperty;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="vitalsign")
@JsonIgnoreProperties({ "id" })
public class Vitalsign {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated user ID")
	@Column(name="ID")
	long id;
	
	@Column(name="user_id")
	@ApiModelProperty(notes = "user id")
	long user_id;
	@Column(name="patient_id")
	@ApiModelProperty(notes = "patient id")
	long patient_id;
	
	@ApiModelProperty(notes = "temperature in celsius")
	float temperature;
	@ApiModelProperty(notes = "blood sugar level in unit")
	float bloodsugar;
	@ApiModelProperty(notes = "Body weight in kg's")
	float weight;
	@ApiModelProperty(notes = "height in cm's")
	float height;
	@ApiModelProperty(notes = "spo2")
	float spo2;
	@ApiModelProperty(notes = "pulse")
	float pulse;
	@ApiModelProperty(notes = "patient name")
	String patient_name;
	@ApiModelProperty(notes = "user name")
	String user_name;
	
	@Column(name="created_by")
	String createdBy;
	
	@Column(name="last_modified_by")
	String lastModifiedBy;
	
	@CreatedDate
    @Timestamp
    @Column(name="created_date")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createdDate;
	
	@LastModifiedDate
    @Timestamp
    @Column(name="last_modified_date")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime lastModifiedDate;

	public Vitalsign() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
