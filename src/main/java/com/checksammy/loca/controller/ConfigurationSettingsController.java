package com.checksammy.loca.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.model.ConfigurationSettings;
import com.checksammy.loca.model.WorkStatus;
import com.checksammy.loca.service.ConfigurationSettingsService;
import com.checksammy.loca.service.WorkStatusService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/settings")
public class ConfigurationSettingsController {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationSettingsController.class);

	@Autowired
	ConfigurationSettingsService service;

	@Autowired
	WorkStatusService workStatusService;

	@GetMapping("/{keyname}")
	public ResponseEntity<ServiceResponse> getSettings(@PathVariable("keyname") String keyName) {
		ServiceResponse response = new ServiceResponse();
		try {
			String value = service.getValueByKey(keyName);
			response.setReturnObject(value);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("")
	public ResponseEntity<ServiceResponse> getAllSettings() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<ConfigurationSettings> configurationSettings = service.getAllSettings();
			response.setReturnObject(configurationSettings);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/update")
	public ResponseEntity<ServiceResponse> updateValueByKey(@RequestBody ConfigurationSettings settings) {
		ServiceResponse response = new ServiceResponse();
		try {
			Integer result = service.updateValue(settings.getKey(), settings.getValue());
			if (result > 0) {
				response.setReturnObject("Setting is updated successfully for key: " + settings.getKey());
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
				response.setErrorMessage("There is some problem in modifying the setting.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	/*FOR WORK STATUS FOR WORK - Request, Quotes, Jobs, Invoice*/
	@GetMapping("/workstatus")
	public ResponseEntity<ServiceResponse> getWorkStatus() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<WorkStatus> workStatus = workStatusService.getAllWorkStatus();
			response.setReturnObject(workStatus);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/workstatus/{StatusId}")
	public ResponseEntity<ServiceResponse> getWorkStatusSingle(@PathVariable("StatusId") Long StatusId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<WorkStatus> workStatus = workStatusService.getDataById(StatusId);
			response.setReturnObject(workStatus.get());
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

}
