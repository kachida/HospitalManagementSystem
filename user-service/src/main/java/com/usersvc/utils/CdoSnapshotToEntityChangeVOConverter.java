package com.usersvc.utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.javers.core.metamodel.object.CdoSnapshot;

import com.usersvc.models.UserAudit;
import lombok.extern.slf4j.Slf4j;


// TODO: Auto-generated Javadoc
/**
 *  The Constant log.
 *
 * @author : Kannappan
 * @version : 1.0
 */

/** The Constant log. */
@Slf4j
public class CdoSnapshotToEntityChangeVOConverter {

	/**
	 * Instantiates a new cdo snapshot to entity change VO converter.
	 */
	private CdoSnapshotToEntityChangeVOConverter()
	{
		
	}
	
	
	/**
	 * Convert.
	 *
	 * @param from the from
	 * @param to the to
	 * @return the user audit
	 */
	public static UserAudit convert(CdoSnapshot from, UserAudit to)
	{
		log.info("userAudit convert");
		log.info(from.toString());
		to.setAction(extractAction(from));
		to.setEntityType(from.getManagedType().getName());
		//to.setEntityId(from.getGlobalId().value());
		to.setEntityId(from.getGlobalId().value().split("/")[1]);
		to.setModifiedBy(from.getCommitMetadata().getAuthor());
		to.setChangedEntityFields(from.getChanged());
		to.setEntityValue(from.getState().getPropertyNames().stream().collect(Collectors.toMap(key -> key , key -> {
			Object o = from.getPropertyValue(key);
			if(o instanceof Instant)
			{
				return DateTimeFormatter.ISO_INSTANT.format((Instant) o);
			}
			return o;
			
		})));
		to.setModifiedDate(from.getCommitMetadata().getCommitDate().toInstant(ZoneOffset.UTC));
		return to;
		
	}
	
	/**
	 * Extract action.
	 *
	 * @param from the from
	 * @return the string
	 */
	private static String extractAction(CdoSnapshot from)
	{
		switch(from.getType())
		{
		case INITIAL: return "CREATE";
		case UPDATE: return "UPDATE";
		case TERMINAL: return "DELETE";
		}
		return null;
		
	}
	
}
