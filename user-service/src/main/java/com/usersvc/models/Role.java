package com.usersvc.models;

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


// TODO: Auto-generated Javadoc
/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new role.
 *
 * @param id the id
 * @param role_name the role name
 * @param createdBy the created by
 * @param lastModifiedBy the last modified by
 * @param createdDate the created date
 * @param lastModifiedDate the last modified date
 */
@AllArgsConstructor

/**
 * Instantiates a new role.
 */
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Role  {
	

	
	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private long id;
	
	
	/** The role name. */
	@Column(name="name")
	private String role_name;
	
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
	
	/*
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "roles")
    private List<User> users;
    */

	/**
	 * Instantiates a new role.
	 *
	 * @param id the id
	 * @param role_name the role name
	 */
	public Role(long id, String role_name)
	{
		this.id=id;
		this.role_name=role_name;
	}
}
