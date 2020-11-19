package com.checksammy.loca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.dto.CountersDto;
import com.checksammy.loca.service.BinHistoryViewWebService;
import com.checksammy.loca.service.LocationService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.response.ServiceResponse;

/**
 * This class handles all kind of requests originating from dashboard page of mobile app.
 * 
 * @author Abhishek Srivastava 
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/loca/api/dashboard")
public class DashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	BinHistoryViewWebService binHistoryViewWebService;
	
	
	@GetMapping("/counters")
	public ResponseEntity<ServiceResponse> getDashboardCounters(){
		
		ServiceResponse response = new ServiceResponse();
		
		CountersDto countersDto = new CountersDto();
		try {
			Long userCount = userService.getTotalNoOfUsers();
			Long locationCount = locationService.getTotalNoOfLocations();
			Long binCount = binHistoryViewWebService.getTotalNoOfBins();
			
			countersDto.setUserCount(userCount);
			countersDto.setLocationCount(locationCount);
			countersDto.setBinCount(binCount);
			response.setReturnObject(countersDto);
			response.setStatus("SUCCESS");
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus("FAILURE");
		}
		return ResponseEntity.ok().body(response);
	}

}
