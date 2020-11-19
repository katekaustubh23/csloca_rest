/**
 * 
 */
package com.checksammy.loca.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.model.BinDestination;
import com.checksammy.loca.service.BinDestinationService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

/**
 * This controller handles all request associated with Bin Destination
 * 
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/bindestination")
public class BinDestinationController {

	private static final Logger logger = LoggerFactory.getLogger(BinDestinationController.class);
	
	@Autowired
	BinDestinationService binDestinationSvc;
	
	/*
	 * This API retrieves all paginated Bin Destinations
	 */
	@GetMapping("/listall")
	public ResponseEntity<ServiceResponse> getAll(Pageable pageable){
		ServiceResponse response = new ServiceResponse();
		try {
			Page<BinDestination> binDestinations = binDestinationSvc.getAllBinDestinations(pageable);
			response.setReturnObject(binDestinations);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/web/list")
	public ResponseEntity<ServiceResponse> getfindAll(){
		ServiceResponse response = new ServiceResponse();
		try {
			List<BinDestination> binDestinations = binDestinationSvc.getFindAll();
			response.setReturnObject(binDestinations);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
	/*
	 * This API Add New Bin Destination/Update/Delete base on the entity being set in request payload
	 * For logical delete, just set is_deleted=1 in request payload.
	 * 
	 */
	@PostMapping("/save")
	public ResponseEntity<ServiceResponse> save(@RequestBody BinDestination binDestination){
		ServiceResponse response = new ServiceResponse();
		try {
			binDestination = binDestinationSvc.add(binDestination);
			response.setReturnObject(binDestination);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
	/*
	 * @DeleteMapping("/delete/{id}") public ResponseEntity<ServiceResponse>
	 * delete(@PathVariable Long id){ ServiceResponse response = new
	 * ServiceResponse(); try { binDestinationSvc.deleteById(id);
	 * response.setReturnObject("Bin Destination is deleted successfully.");
	 * response.setStatus(ConstantUtil.RESPONSE_SUCCESS); }catch(Exception e) {
	 * e.printStackTrace(); logger.error(e.getMessage(), e.fillInStackTrace());
	 * response.setErrorMessage(e.getMessage());
	 * response.setStatus(ConstantUtil.RESPONSE_FAILURE); } return
	 * ResponseEntity.ok().body(response); }
	 */
}
