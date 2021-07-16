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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
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
	
	public User() {
		super();
	}
	
	

}
