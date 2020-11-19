package com.checksammy.loca.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.model.MobPickupRequestView;
import com.checksammy.loca.model.PickupRequest;
import com.checksammy.loca.service.PickupRequestService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/propmanager")
public class PropertyManagerController {

	private static final Logger logger = LoggerFactory.getLogger(PropertyManagerController.class);

	@Autowired
	UserService userService;

	@Autowired
	PickupRequestService pickupRequestService;

//	@PostMapping("/register")
//	public ResponseEntity<ServiceResponse> register(@RequestBody UserRoleDto userRoleDto) {
//		ServiceResponse response = new ServiceResponse();
//		User foundUser = null;
//		try {
//			try {
//				foundUser = userService.findByUsername(userRoleDto.getUser().getUsername());
//			} catch (UsernameNotFoundException ue) {
//				userRoleDto.getUser().setIsDeleted(Boolean.FALSE); 
//				Boolean status = userService.spUpsertUser(userRoleDto.getUser(), userRoleDto.getRoles());
//				response.setReturnObject("User registration status: " + status);
//				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
//			}
//			if (foundUser != null) {
//				response.setReturnObject("User already exists: " + userRoleDto.getUser().getUsername());
//				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e.fillInStackTrace());
//			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
//			response.setReturnObject(e.getMessage());
//		}
//		return ResponseEntity.ok().body(response);
//
//	}
//
//		
//	@PostMapping("/update")
//	public ResponseEntity<ServiceResponse> updateUser(@RequestBody UserRoleDto userRoleDto) {
//		ServiceResponse response = new ServiceResponse();
//		try {
//			Boolean status = userService.spUpsertUser(userRoleDto.getUser(), userRoleDto.getRoles());
//			response.setReturnObject("User update status: " + status);
//			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e.fillInStackTrace());
//			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
//			response.setReturnObject(e.getMessage());
//		}
//		return ResponseEntity.ok().body(response);
//
//	}

	@PostMapping("/save")
	public ResponseEntity<ServiceResponse> savePickupRequest(@RequestBody PickupRequest pickupRequest) {
		ServiceResponse response = new ServiceResponse();
		try {
			pickupRequest = pickupRequestService.savePickupRequest(pickupRequest);
			response.setReturnObject(pickupRequest);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);

	}

	@GetMapping("/propmanagerpickups/{userId}")
	public ResponseEntity<ServiceResponse> getAllPickUpReqeustForPropManager(@PathVariable("userId") Long userId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<MobPickupRequestView> mobPickupRequests = pickupRequestService.getAllPickUpReqeustForPropManager(userId);
			response.setReturnObject(mobPickupRequests);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/pickup/{id}")
	public ResponseEntity<ServiceResponse> getPickupRequestById(@PathVariable("id") Long id) {
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<MobPickupRequestView> mobPickupRequest = pickupRequestService.getPickupRequestById(id);
			response.setReturnObject(mobPickupRequest);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	
	@DeleteMapping("/pickup/delete/{id}")
	public ResponseEntity<ServiceResponse> deletePickUpRequestById(@PathVariable("id") Long id) {
		ServiceResponse response = new ServiceResponse();
		try {
			pickupRequestService.deletePickUpRequestById(id);
			response.setReturnObject("Pick up deleted successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
}
