package com.checksammy.loca.controller;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.dto.LocationDto;
import com.checksammy.loca.dto.LocationPropertyManagmentDto;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.LocationNote;
import com.checksammy.loca.model.LocationPropertyDetailView;
import com.checksammy.loca.model.User;
import com.checksammy.loca.service.LocationNoteService;
import com.checksammy.loca.service.LocationService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

/**
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/location")
public class LocationController {

	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationNoteService locationNoteService;
	
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

	@GetMapping("/listall")
	public ResponseEntity<ServiceResponse> getAllLocation() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<Location> locations = locationService.getAllLocation();
			response.setReturnObject(locations);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{locationId}")
	public ResponseEntity<ServiceResponse> getLocationById(@PathVariable("locationId") Long locationId) {
		ServiceResponse response = new ServiceResponse();
		try {
			LocationDto location = locationService.getLocationByIdNew(locationId);
			response.setReturnObject(location);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/save")
	public ResponseEntity<ServiceResponse> save(@RequestBody LocationPropertyManagmentDto locPropMgmtDto) {
		ServiceResponse response = new ServiceResponse();
		if (locPropMgmtDto.getLocation().getId() != null && locPropMgmtDto.getLocation().getId() != 0) {
			locationService.deleteLocationPropertyMapping(locPropMgmtDto.getLocation().getId());
		}
		try {
			Location location = locationService.add(locPropMgmtDto);
			if (location.getId() != null) {
				if(location.getLocationNotes() != null && location.getLocationNotes().size() > 0) {
					for (LocationNote locationNotes : location.getLocationNotes()) {
						User userData = userService.getUserId(locationNotes.getCreatedBy());
						locationNotes.setCreatedUserDetails(userData.getFirstName() + " " + userData.getLastName());
						locationNotes.setLocationId(location.getId());
						locationNotes.setCreatedTs(Instant.now());
					}
					List<LocationNote> locatonNotes = locationNoteService.saveLocationNotes(location.getLocationNotes());
					location.setLocationNotes(new HashSet<LocationNote>(locatonNotes));
				}
				response.setReturnObject(location);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				response.setReturnObject("duplicate entry");
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getCause().getCause().getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/web/pagedList")
	public ResponseEntity<ServiceResponse> getPaginatedLocationsForPortal(Pageable pageable) {
		ServiceResponse response = new ServiceResponse();
		try {
			Page<LocationPropertyDetailView> locations = locationService
					.getPaginatedAllLocationPropertyDetails(pageable);
			response.setReturnObject(locations);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/web/list")
	public ResponseEntity<ServiceResponse> getLocationsForPortal() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<LocationPropertyDetailView> locations = locationService.getAllLocationPropertyDetails();
			response.setReturnObject(locations);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/web/delete/{id}")
	public ResponseEntity<ServiceResponse> deleteLocation(@PathVariable("id") Long id) {
		ServiceResponse response = new ServiceResponse();
		try {
			locationService.deleteById(id);
			response.setReturnObject("Location " + id + " is deleted successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/web/{id}")
	public ResponseEntity<ServiceResponse> getLocationPropDetailById(@PathVariable("id") Long id) {
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<LocationPropertyDetailView> locPropDetailView = locationService.getLocationPropertyDetailsById(id);
			response.setReturnObject(locPropDetailView);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* Delete Location property mapping */
	@DeleteMapping("/delete/mapp/{locationId}")
	public ResponseEntity<ServiceResponse> deleteMapping(@PathVariable("locationId") Long locationId) {
		ServiceResponse response = new ServiceResponse();
		try {
			locationService.deleteLocationPropertyMapping(locationId);
			response.setReturnObject(
					"Location_ property Details Mapping Id " + locationId + " is deleted successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/web/count")
	public ResponseEntity<ServiceResponse> getLocationCount() {
		ServiceResponse response = new ServiceResponse();
		try {
			Long totalCount = locationService.getTotalNoOfLocations();
			response.setReturnObject(totalCount);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
	/* SPRINT 7 - delete location notes */
	@DeleteMapping("/delete/notes/{noteId}")
	public ResponseEntity<ServiceResponse> deleteLocationNotes(@PathVariable("noteId") Long noteId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean status = locationNoteService.deleteLocationNotes(noteId);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
	/*Location List By User Id*/
	@GetMapping("/listby/{userId}")
	public ResponseEntity<ServiceResponse> getLocationListByUserId(@PathVariable("userId") Long userId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<Location> locationList = locationService.locationListByUserId(userId);
			response.setReturnObject(locationList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
}
