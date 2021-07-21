package com.usersvc.models;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "useraudit")
public class UserAudit {
	
	private String action;
	private String entityType = "User";
	private String entityId;
	private List<String> changedEntityFields;
	private Map<String,Object> entityValue;
	private String modifiedBy;
	private Instant modifiedDate;
	public UserAudit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserAudit(String action, String entityType, String entityId, List<String> changedEntityFields,
			Map<String, Object> entityValue, String modifiedBy, Instant modifiedDate) {
		super();
		this.action = action;
		this.entityType = entityType;
		this.entityId = entityId;
		this.changedEntityFields = changedEntityFields;
		this.entityValue = entityValue;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	@Override
	public String toString() {
		return "EntityChangeVO [action=" + action + ", entityType=" + entityType + ", entityId=" + entityId
				+ ", changedEntityFields=" + changedEntityFields + ", entityValue=" + entityValue + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}
	
	
	
	

}
