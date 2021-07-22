package com.patientsvc.models;

import lombok.Value;

@Value
public class User {

	
	private long Id;
	private String role;
	private String username;
	private String email;
	private String phonenumber;
	private String address;
	

}
