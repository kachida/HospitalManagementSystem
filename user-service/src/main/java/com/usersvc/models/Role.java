package com.usersvc.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Role implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private long id;
	
	
	@Column(name="name")
	private String role_name;
	
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

}
