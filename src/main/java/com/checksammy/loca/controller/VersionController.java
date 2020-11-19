package com.checksammy.loca.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.model.ApplicationVersion;
import com.checksammy.loca.service.ApplicationVersionService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/versioncheck")
public class VersionController {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	ApplicationVersionService appService;

	@GetMapping("")
	public ResponseEntity<ServiceResponse> getAllPickUpReqeustForPropManager() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<ApplicationVersion> applicationVersion = appService.getList();
			response.setReturnObject(applicationVersion);
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
