package com.usersvc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	/**
	  * User Id
	  */
	long Id;
	
	/**
	  * role of user
	  */
	String role;
	
	/**
	  * user name
	  */
    String username;
    
    /**
	  * email
	  */
	String email;
	
    /**
	  * phonenumber
	  */
	String phonenumber;
	
    /**
	  * address
	  */
	String address;

}
