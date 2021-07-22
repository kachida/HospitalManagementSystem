package com.usersvc.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModelProperty;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
    @ApiModelProperty(notes = "The database generated user ID")
	long Id;
	
	@NotBlank
	@ApiModelProperty(notes = "Role of the user")
	String role;
	
	@NotBlank
	@ApiModelProperty(notes = "username")
	String username;
	@Email
	@ApiModelProperty(notes = "Email address of the user")
	String email;
	@ApiModelProperty(notes = "Phone number of the user")
	String phonenumber;
	@ApiModelProperty(notes = "Address of the user")
	String address;
	
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
	
	public User() {
		super();
	}
	
	

}
