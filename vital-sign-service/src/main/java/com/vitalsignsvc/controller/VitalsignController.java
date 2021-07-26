package com.vitalsignsvc.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
import com.vitalsignsvc.dto.VitalsignDto;
import com.vitalsignsvc.models.AuthenticationRequest;
import com.vitalsignsvc.models.AuthenticationResponse;
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
		public List<VitalsignDto> getAllVitalsignRecords(@RequestParam(defaultValue = "0") int pageNo,
				@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "Id") String sortBy) {
			return  vitalsignService.getAllVitalSignRecords(pageNo, pageSize, sortBy);
			
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
		public VitalsignDto getUserById(@PathVariable long id) {
			return vitalsignService.getVitalsignRecordById(id);
		}

		// create new vitalsign record
		@PostMapping("/{id}")
		@Loggable
		@ApiOperation(value = "To create a new vitalsign record", produces = "application/json")
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Successfully the record is created"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
		})
		public VitalsignDto saveVitalsignRecord(@RequestBody VitalsignDto vitalsign) throws IOException {
			return vitalsignService.addVitalsignRecord(vitalsign);
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
		public VitalsignDto updateVitalsignRecord(@RequestBody VitalsignDto vitalsign, @PathVariable long id) {
			return vitalsignService.updateVitalsignRecord(vitalsign, id);
			
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
