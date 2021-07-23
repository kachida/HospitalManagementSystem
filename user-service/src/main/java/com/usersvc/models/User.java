package com.usersvc.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


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
	long Id;
	
	@NotBlank
	String role;
	
	@NotBlank
	String username;
	String email;
	String phonenumber;
	String address;
	
	@Column(name="created_by")
	String createdBy;
	
	@Column(name="last_modified_by")
	String lastModifiedBy;
	
	@CreatedDate
    @Column(name="created_date")
    private LocalDateTime createdDate;
	
	@LastModifiedDate
    @Column(name="last_modified_date")
    private LocalDateTime lastModifiedDate;
	
	public User() {
		super();
	}
	
	

}
