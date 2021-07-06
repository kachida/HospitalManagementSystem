package com.usersvc.models;

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

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="user")
@NamedQueries({
	@NamedQuery(name="User.FindByEmail",query="FROM User WHERE email=?1"),
	@NamedQuery(name="User.FindByRoleAndUsername",query="SELECT a FROM User a WHERE a.role=?1 AND a.username=?2")
})
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	long Id;
	
	@NotBlank
	String role;
	
	@NotBlank
	String username;
	@Email
	String email;
	String phonenumber;
	String address;
	
	public User() {
		super();
	}
	
	

}
