package com.usersvc.utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.javers.core.metamodel.object.CdoSnapshot;

import com.usersvc.models.UserAudit;

public class CdoSnapshotToEntityChangeVOConverter {

	private CdoSnapshotToEntityChangeVOConverter()
	{
		
	}
	
	
	public static UserAudit convert(CdoSnapshot from, UserAudit to)
	{
		to.setAction(extractAction(from));
		to.setEntityType(from.getManagedType().getName());
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
