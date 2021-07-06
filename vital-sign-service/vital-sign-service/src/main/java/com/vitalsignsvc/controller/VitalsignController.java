package com.vitalsignsvc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.vitalsignsvc.models.Vitalsign;
import com.vitalsignsvc.service.IVitalsignService;

@RestController
@RequestMapping("/vitalsignsvc")
public class VitalsignController {

	@Autowired
	IVitalsignService vitalsignService;
	
	
	// fetch all vitalsign records
		@GetMapping("/vitalsign")
		public ResponseEntity<List<Vitalsign>> getAllVitalsignRecords(@RequestParam(defaultValue = "0") int pageNo,
				@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "Id") String sortBy) {
			List<Vitalsign> vitalsignList = vitalsignService.getAllVitalSignRecords(pageNo, pageSize, sortBy);
			return new ResponseEntity<List<Vitalsign>>(vitalsignList, new HttpHeaders(), HttpStatus.OK);
		}

		// fetch vitalsign record by id
		@GetMapping("/vitalsign/{id}")
		public ResponseEntity<Vitalsign> getUserById(@PathVariable long id) {
			return ResponseEntity.status(HttpStatus.OK).body(vitalsignService.getVitalsignRecordById(id));
		}

		// create new vitalsign record
		@PostMapping("/createvitalsign/{id}")
		public ResponseEntity<Vitalsign> saveVitalsignRecord(@RequestBody Vitalsign vitalsign,@PathVariable long id) {
			return new ResponseEntity<Vitalsign>(vitalsignService.addVitalsignRecord(vitalsign,id), HttpStatus.CREATED);
		}

		// update existing vitalsign record
		@PutMapping("/vitalsign/{patient_id}/{id}")
		public ResponseEntity<Vitalsign> updateVitalsignRecord(@RequestBody Vitalsign vitalsign,@PathVariable long patient_id, @PathVariable long id) {
			Optional<Vitalsign> vitalsignDetails = vitalsignService.updateVitalsignRecord(vitalsign,patient_id, id);
			if (vitalsignDetails.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(vitalsign);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(vitalsignDetails.get());
			}
		}

		// delete vitalsign record
		@DeleteMapping("/vitalsign/{id}")
		public void deleteVitalsignRecord(@PathVariable long id) {
			vitalsignService.deleteVitalsignRecord(id);
		}
}
