package com.vitalsignsvc.service;

import java.util.List;

import com.vitalsignsvc.dto.VitalsignDto;

public interface IVitalsignService {
	
	public List<VitalsignDto> getAllVitalSignRecords(int pageNo, int pageSize, String sortBy);
	public VitalsignDto getVitalsignRecordById(long id);
	public VitalsignDto addVitalsignRecord(VitalsignDto vitalSign);
	public VitalsignDto updateVitalsignRecord(VitalsignDto vitalsign,long id);
	public void deleteVitalsignRecord(long id);

}
