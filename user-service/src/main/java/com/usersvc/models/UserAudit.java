package com.usersvc.models;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


// TODO: Auto-generated Javadoc
/**
 * User Audit
 *
 * @author : Kannappan
 * @version : 1.0
 * 
 */

@Data
@Document(collection = "useraudit")
public class UserAudit {
	
	/** The action. */
	private String action;
	
	/** The entity type. */
	private String entityType = "User";
	
	/** The entity id. */
	private String entityId;
	
	/** The changed entity fields. */
	private List<String> changedEntityFields;
	
	/** The entity value. */
	private Map<String,Object> entityValue;
	
	/** The modified by. */
	private String modifiedBy;
	
	/** The modified date. */
	private Instant modifiedDate;
	
	/**
	 * Instantiates a new user audit.
	 */
	public UserAudit() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new user audit.
	 *
	 * @param action the action
	 * @param entityType the entity type
	 * @param entityId the entity id
	 * @param changedEntityFields the changed entity fields
	 * @param entityValue the entity value
	 * @param modifiedBy the modified by
	 * @param modifiedDate the modified date
	 */
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
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "EntityChangeVO [action=" + action + ", entityType=" + entityType + ", entityId=" + entityId
				+ ", changedEntityFields=" + changedEntityFields + ", entityValue=" + entityValue + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}
	
	
	
	

}
