package com.vitalsignsvc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitalsignsvc.aspect.Loggable;
import com.vitalsignsvc.models.AuthenticationRequest;
import com.vitalsignsvc.models.AuthenticationResponse;
import com.vitalsignsvc.models.Vitalsign;
import com.vitalsignsvc.security.JwtUtil;
import com.vitalsignsvc.service.IVitalsignService;
import com.vitalsignsvc.service.MyUserDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/vitalsigns")
@EnableDiscoveryClient
@Api(value="VitalsignController", description="Operations pertaining to vitalsigns in vitalsign module API")
public class VitalsignController {

	
	private final IVitalsignService vitalsignService;
	private final AuthenticationManager authenticationManager;
	private final MyUserDetailService userDetailsService;
	private final JwtUtil jwtUtilToken;
	
	
	
	
	public VitalsignController(IVitalsignService vitalsignService, AuthenticationManager authenticationManager,
			MyUserDetailService userDetailsService, JwtUtil jwtUtilToken) {
		super();
		this.vitalsignService = vitalsignService;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtilToken = jwtUtilToken;
	}

	// fetch all vitalsign records
		@GetMapping("/")
		@Loggable
		@ApiOperation(value = "Retrieve all vitalsign records with pagination and sorting supported", produces = "application/json")
		@ApiResponses(value = {
		        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
		})
		public ResponseEntity<List<Vitalsign>> getAllVitalsignRecords(@RequestParam(defaultValue = "0") int pageNo,
				@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "Id") String sortBy) {
			List<Vitalsign> vitalsignList = vitalsignService.getAllVitalSignRecords(pageNo, pageSize, sortBy);
			return new ResponseEntity<List<Vitalsign>>(vitalsignList, new HttpHeaders(), HttpStatus.OK);
		}

		// fetch vitalsign record by id
		@GetMapping("/{id}")
		@Loggable
		@ApiOperation(value = "Retrieve vitalsign details with given id", produces = "application/json")
		@ApiResponses(value = {
		        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
		        @ApiResponse(code = 204, message = "No resource found for this id"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token"),
		})
		public ResponseEntity<Vitalsign> getUserById(@PathVariable long id) {
			return ResponseEntity.status(HttpStatus.OK).body(vitalsignService.getVitalsignRecordById(id));
		}

		// create new vitalsign record
		@PostMapping("/{id}")
		@Loggable
		@ApiOperation(value = "To create a new vitalsign record", produces = "application/json")
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Successfully the record is created"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
		})
		public ResponseEntity<Vitalsign> saveVitalsignRecord(@RequestBody Vitalsign vitalsign) {
			return new ResponseEntity<Vitalsign>(vitalsignService.addVitalsignRecord(vitalsign), HttpStatus.CREATED);
		}

		// update existing vitalsign record
		@PutMapping("/{id}")
		@Loggable
		@ApiOperation(value = "To update the existing  vitalsign record", produces = "application/json")
		@ApiResponses(value = {
		        @ApiResponse(code = 200, message = "Successfully the record is updated"),
		        @ApiResponse(code = 204, message = "No resource found for this id"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
		})
		public ResponseEntity<Vitalsign> updateVitalsignRecord(@RequestBody Vitalsign vitalsign, @PathVariable long id) {
			Optional<Vitalsign> vitalsignDetails = vitalsignService.updateVitalsignRecord(vitalsign, id);
			if (vitalsignDetails.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(vitalsign);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(vitalsignDetails.get());
			}
		}

		// delete vitalsign record
		@DeleteMapping("/{id}")
		@Loggable
		@ApiOperation(value = "To delete the existing  vitalsign record", produces = "application/json")
		@ApiResponses(value = {
		        @ApiResponse(code = 200, message = "Successfully the record is deleted"),
		        @ApiResponse(code = 204, message = "No resource found for this id"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
		})
		public void deleteVitalsignRecord(@PathVariable long id) {
			vitalsignService.deleteVitalsignRecord(id);
		}
		
		//Authenticate
		@PostMapping("/authenticate")
		@Loggable
		@ApiOperation(value = "To generate a new JWT Token", produces = "application/json")
		@ApiResponses(value = {
		        @ApiResponse(code = 200, message = "Successfully the jwt token is generated"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
		})
		public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
		{
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			final String jwt = jwtUtilToken.generateToken(userDetails);
			return ResponseEntity.ok(new AuthenticationResponse(jwt));
			
		}
}
