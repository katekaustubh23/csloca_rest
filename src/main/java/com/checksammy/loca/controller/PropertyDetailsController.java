/**
 * 
 */
package com.checksammy.loca.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.PropertyDetailsDto;
import com.checksammy.loca.dto.PropertyDetailsSaveDto;
import com.checksammy.loca.model.LocationPropertyDetails;
import com.checksammy.loca.model.PropertyDetailNote;
import com.checksammy.loca.model.PropertyDetails;
import com.checksammy.loca.model.User;
import com.checksammy.loca.repository.LocationPropertyDetailRepository;
import com.checksammy.loca.service.PropertyDetailNoteService;
import com.checksammy.loca.service.PropertyDetailsService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

/**
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/propertydetail")
public class PropertyDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(PropertyDetailsController.class);

	@Autowired
	PropertyDetailsService service;

	@Autowired
	LocationPropertyDetailRepository locationPropertyDetailRepo;

	@Autowired
	PropertyDetailNoteService proDetailNoteService;

	@Autowired
	UserService userService;

	@GetMapping(value = "/listall")
	public ResponseEntity<ServiceResponse> getAll(Pageable pageable) {
		ServiceResponse response = new ServiceResponse();
		try {
			Page<PropertyDetails> propertyDetails = service.getAll(pageable);
			response.setReturnObject(propertyDetails);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

//	 required for add-location  13-01-2020
	@GetMapping(value = "/list/all")
	public ResponseEntity<ServiceResponse> getAllList() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<PropertyDetailsDto> propertyDetails = service.getAllList2();
			response.setReturnObject(propertyDetails);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/add")
	public ResponseEntity<ServiceResponse> save(@RequestBody PropertyDetailsSaveDto propertyDetailSave) {
		ServiceResponse response = new ServiceResponse();
		try {
			PropertyDetails propertyDetails = propertyDetailSave.insertDetails(propertyDetailSave);
			propertyDetails = service.save(propertyDetails);
			List<PropertyDetailNote> propertyDetailNotes = new ArrayList<PropertyDetailNote>();
			if (propertyDetails.getPropertyNotes() != null && propertyDetails.getPropertyNotes().size() > 0) {
				propertyDetailNotes = new ArrayList<PropertyDetailNote>(propertyDetails.getPropertyNotes());
				for (PropertyDetailNote propertyDetailNote : propertyDetailNotes) {
					User userData = userService.getUserId(propertyDetailNote.getCreatedBy());
					propertyDetailNote.setPropertyDetailId(propertyDetails.getId());
					propertyDetailNote.setCreatedUserDetails(userData.getFirstName() + " " + userData.getLastName());
					propertyDetailNote.setCreatedTs(Instant.now());
				}
				List<PropertyDetailNote> propDetailNote = proDetailNoteService.savePropertyDetail(propertyDetailNotes);
				propertyDetails.setPropertyNotes(new HashSet<PropertyDetailNote>(propDetailNote));
			}
			response.setReturnObject(propertyDetails);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<ServiceResponse> delete(@PathVariable("id") Long id) {
		ServiceResponse response = new ServiceResponse();
		try {
			service.deleteById(id);
			response.setReturnObject("Property Detail is removed successfully");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	// call location-property_details by propertyDetails_id
	@GetMapping(value = "/locationlist/{propertyManagement}")
	public ResponseEntity<ServiceResponse> getListOfLocationByPropertyDetailId(
			@PathVariable("propertyManagement") Integer propertyManagement) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<LocationPropertyDetails> locationPropertyDetails = locationPropertyDetailRepo
					.findPropDetailId(propertyManagement);
			response.setReturnObject(locationPropertyDetails);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

//	complete pacakge list 2/05/2020
	@GetMapping(value = "/locationmapp/list")
	public ResponseEntity<ServiceResponse> getListOfLocationMappProperty() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<LocationPropertyDetails> locationPropertyDetails = locationPropertyDetailRepo.findAll();
			response.setReturnObject(locationPropertyDetails);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/* SPRINT 7 DELETE property notes */
	@DeleteMapping(value = "/delete/notes/{propNoteId}")
	public ResponseEntity<ServiceResponse> deletePropertyNote(@PathVariable("propNoteId") Long propNoteId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean status = proDetailNoteService.deletePropertyNote(propNoteId);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/detail/{companyId}")
	public ResponseEntity<ServiceResponse> getPropertyDetails(@PathVariable("companyId") Long companyId) {
		ServiceResponse response = new ServiceResponse();
		try {
			PropertyDetailsDto propertyDetails = service.getSingleCompanyDetails(companyId);
			if (propertyDetails.getId() != null && propertyDetails.getId() > 0) {
				response.setReturnObject(propertyDetails);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
				response.setErrorMessage("Company Not Present");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

//	TODO: import XLXS file for Hauler company List
	@PostMapping(value = "/import/list/{userId}")
	public ResponseEntity<ServiceResponse> uploadingRequestAttachment(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = service.importXlXSheetList(userId, file);
			if(status.equalsIgnoreCase("Success")) {
				response.setReturnObject(status);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);	
			}else{
				response.setReturnObject("Please use 'dd-MMM-yy' date format");
				response.setErrorMessage(status);
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);	
			}
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
}
