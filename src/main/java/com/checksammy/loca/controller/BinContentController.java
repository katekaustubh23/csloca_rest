/**
 * 
 */
package com.checksammy.loca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.model.BinContent;
import com.checksammy.loca.service.BinContentService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

/**
 * This controller handles all requests associated with BinContent
 * 
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/bincontent")
public class BinContentController {

	private static final Logger logger = LoggerFactory.getLogger(BinContentController.class);

	@Autowired
	BinContentService service;

//	@GetMapping(value="/listall")
//	public ResponseEntity<ServiceResponse> getAll(Pageable pageable){
//		ServiceResponse response = new ServiceResponse();
//		try {
//			Page<BinContent> binContents = service.getAll(pageable);
//			response.setReturnObject(binContents);
//			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
//		}catch(Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e.fillInStackTrace());
//			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
//			response.setErrorMessage(e.getMessage());
//		}
//		return ResponseEntity.ok().body(response);
//	}

	/*
	 * To update/delete bin content to/from a bin type When adding new one set
	 * is_deleted = false and while deleting set is_deleted = true
	 * 
	 */
	@PostMapping(value = "/update")
	public ResponseEntity<ServiceResponse> save(@RequestBody BinContent binContent) {
		ServiceResponse response = new ServiceResponse();
		try {
			binContent = service.save(binContent);
			response.setReturnObject(binContent);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/*
	 * This delete API can be called when admin is removing content one by one from bin type.
	 * It accepts the Bin Content's id and bin_type_id and set the is_deleted to true.
	 * 
	 */
	@DeleteMapping(value = "/delete/{id}/{binTypeId}")
	public ResponseEntity<ServiceResponse> delete(@PathVariable("id") Long id,
			@PathVariable("binTypeId") Long binTypeId) {
		ServiceResponse response = new ServiceResponse();
		try {
			service.deleteByIdAndBinTypeId(id, binTypeId);
			response.setReturnObject("Bin Content is removed successfully");
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
