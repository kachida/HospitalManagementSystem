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


// TODO: Auto-generated Javadoc
/**
 * User
 *
 * @author : Kannappan
 * @version : 1.0
 * 
 */

@Data

/**
 * Instantiates a new user.
 *
 * @param Id the id
 * @param roles the roles
 * @param username the username
 * @param email the email
 * @param phonenumber the phonenumber
 * @param address the address
 * @param createdBy the created by
 * @param lastModifiedBy the last modified by
 * @param createdDate the created date
 * @param lastModifiedDate the last modified date
 */
@AllArgsConstructor
@Entity
@Table(name="user")
public class User {

	/** The Id. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	long Id;
	
	/** The roles. */
	/*
	@NotBlank
	 @ManyToMany(fetch = FetchType.LAZY,
     cascade = {
         CascadeType.PERSIST,
         CascadeType.MERGE
     })
    @JoinTable(name = "user_role",
     joinColumns = { @JoinColumn(name = "user_id") },
     inverseJoinColumns = { @JoinColumn(name = "role_id") })
	List<Role> roles;
	*/
	String roles;
	
	/** The username. */
	@NotBlank
	String username;
	
	/** The email. */
	String email;
	
	/** The phonenumber. */
	String phonenumber;
	
	/** The address. */
	String address;
	
	/** The created by. */
	@Column(name="created_by")
	String createdBy;
	
	/** The last modified by. */
	@Column(name="last_modified_by")
	String lastModifiedBy;
	
	/** The created date. */
	@CreatedDate
    @Column(name="created_date")
    private LocalDateTime createdDate;
	
	/** The last modified date. */
	@LastModifiedDate
    @Column(name="last_modified_date")
    private LocalDateTime lastModifiedDate;
	
	/**
	 * Instantiates a new user.
	 */
	public User() {
		super();
	}
	
	public User(long Id, String roles, String username, String email, String phonenumber, String address, String createdBy)
	{
		this.Id = Id;
		this.roles = roles;
		this.username = username;
		this.email = email;
		this.phonenumber = phonenumber;
		this.address = address;
		this.createdBy = createdBy;
		
	}
	

}
