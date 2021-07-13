package com.vitalsignsvc.service;

import java.util.List;
import java.util.Optional;

import com.vitalsignsvc.models.Vitalsign;



public interface IVitalsignService {
	
	public List<Vitalsign> getAllVitalSignRecords(int pageNo, int pageSize, String sortBy);
	public Vitalsign getVitalsignRecordById(long id);
	public Vitalsign addVitalsignRecord(Vitalsign vitalSign);
	public Optional<Vitalsign> updateVitalsignRecord(Vitalsign vitalsign,long id);
	public void deleteVitalsignRecord(long id);

}
