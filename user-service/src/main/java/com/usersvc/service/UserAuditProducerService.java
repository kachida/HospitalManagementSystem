package com.usersvc.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.javers.core.commit.Commit;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.usersvc.models.UserAudit;
import com.usersvc.utils.CdoSnapshotToEntityChangeVOConverter;

// TODO: Auto-generated Javadoc
/**
 * The Class UserAuditProducerService.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Service
public class UserAuditProducerService {
	
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(UserAuditProducerService.class);
	
	/** The kafka template. */
	private final KafkaTemplate<String,UserAudit> kafkaTemplate;
	
	/**
	 * Instantiates a new user audit producer service.
	 *
	 * @param entityChangeKafkaTemplate the entity change kafka template
	 */
	public UserAuditProducerService(KafkaTemplate<String, UserAudit> entityChangeKafkaTemplate)
	{
		this.kafkaTemplate = entityChangeKafkaTemplate; 
	}
	
	/**
	 * Broadcast entity change.
	 *
	 * @param commit the commit
	 */
	public void broadcastEntityChange(Commit commit)
	{
		if(commit.getSnapshots().isEmpty())
		{
			return;
		}
		CdoSnapshot snapshot = commit.getSnapshots().get(0);
		UserAudit vo = CdoSnapshotToEntityChangeVOConverter.convert(snapshot, new UserAudit());
		String topic =  getManagedTypeSimpleName(snapshot).toUpperCase()+"_"+vo.getAction();
		//String key = vo.getAction();
		send(new ProducerRecord<>(topic,vo));
	}
	
	/**
	 * Send.
	 *
	 * @param record the record
	 */
	public void send(ProducerRecord<String, UserAudit> record)
	{
		kafkaTemplate.send(record).addCallback(
				result -> log.debug(	"Sent entity-change-topic {} with key {} and changes to params {} with resulting offset {} ",
				record.topic(), record.key(), record.value().getChangedEntityFields(), result.getRecordMetadata().offset()), 
				ex -> log.error("Failed to send entity-change-topic {} with key {} and changes to params {} due to {}", 
						record.topic(), record.key(), record.value().getChangedEntityFields(), ex.getMessage(), ex));
		
		
	}
	
	
	/**
	 * Gets the managed type simple name.
	 *
	 * @param snapshot the snapshot
	 * @return the managed type simple name
	 */
	protected static String getManagedTypeSimpleName(CdoSnapshot snapshot)
	{
		String className = snapshot.getManagedType().getName();
		return className.substring(className.lastIndexOf('.')+1);
	}
	

	
	
}
