package com.vitalsignsvc.models;

import java.sql.Timestamp;
import lombok.Value;

@Value
public class Patient {
	
	private long Id;
	private String name;
	private String complain;
	private String phoneno;
	private String email;
	private Timestamp dateofvisit;
	private long user_id;
}
