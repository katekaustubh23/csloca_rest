/**
 * 
 */
package com.checksammy.loca.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.model.User;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

/**
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/driver")
public class DriverRegistrationController {
	private static final Logger logger = LoggerFactory.getLogger(DriverRegistrationController.class);

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<ServiceResponse> registerDriver(@RequestBody User user) {
		ServiceResponse response = new ServiceResponse();
		List<String> roles = new ArrayList<String>();
		User foundUser = null;
		System.out.println("role => " +  user.getRoles().toString());
		try {
			try {
				roles.add(ConstantUtil.DRIVER_ROLE);
				foundUser = userService.findByUsername(user.getUsername());
			} catch (UsernameNotFoundException ue) {
				user.setIsDeleted(Boolean.FALSE);
				Boolean status = userService.spUpsertUser(user, roles);
				response.setReturnObject("Driver registration status: " + status);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			}
			if (foundUser != null) {
				response.setReturnObject("Driver already exists: " + user.getUsername());
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);

	}

	@PostMapping("/update")
	public ResponseEntity<ServiceResponse> updateDriver(@RequestBody User user) {
		ServiceResponse response = new ServiceResponse();
		List<String> roles = new ArrayList<String>();
		try {
			roles.add(ConstantUtil.DRIVER_ROLE);
			Boolean status = userService.spUpsertUser(user, roles);
			response.setReturnObject("Driver update status: " + status);
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
