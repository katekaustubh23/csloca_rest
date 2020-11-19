/**
 * 
 */
package com.checksammy.loca.controller;

import java.util.ArrayList;
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

import com.checksammy.loca.dto.BinTypeDto;
import com.checksammy.loca.model.BinContent;
import com.checksammy.loca.model.BinType;
import com.checksammy.loca.service.BinContentService;
import com.checksammy.loca.service.BinTypeService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

/**
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/bintype")
public class BinTypeController {
	
	private static final Logger logger = LoggerFactory.getLogger(BinTypeController.class);

	@Autowired
	BinTypeService service;
	
	@Autowired
	BinContentService binContentService;
	
	@GetMapping(value="/listall")
	public ResponseEntity<ServiceResponse> getAll(Pageable pageable){
		ServiceResponse response = new ServiceResponse();
		try {
			Page<BinType> binTypes = service.getAll(pageable);
			response.setReturnObject(binTypes);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value="/web/list")
	public ResponseEntity<ServiceResponse> getAllList(){
		ServiceResponse response = new ServiceResponse();
		try {
			List<BinType> binTypes = service.getFindAll();
			response.setReturnObject(binTypes);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	/*
	 * To add/delete call the same API. 
	 * When adding, set is_deleted=false and while deleting set is_deleted=true
	 */
	@PostMapping(value="/save")
	public ResponseEntity<ServiceResponse> save(@RequestBody BinTypeDto binTypeDto){
		ServiceResponse response = new ServiceResponse();
		try {
			BinType binType = service.save(binTypeDto.getBinType());
			List<BinContent> binContents = new ArrayList<BinContent>();
			for(BinContent binContent: binTypeDto.getBinContents()) {
				binContent.setBinTypeId(binType.getId());
				binContent.setCreatedBy(binType.getCreatedBy());
				binContent.setCreatedTs(binType.getCreatedTs());
				binContent.setIsDeleted(binContent.getIsDeleted());
				binContent.setUpdatedBy(binType.getUpdatedBy());
				binContent.setUpdatedTs(binType.getUpdatedTs());
				binContents.add(binContent);
			}
			binContentService.saveAll(binContents);
			binTypeDto.setBinType(binType);
			binTypeDto.setBinContents(binContents);
			response.setReturnObject(binTypeDto);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	/*
	 * @DeleteMapping(value="/delete/{id}") public ResponseEntity<ServiceResponse>
	 * delete(@PathVariable("id") Long id){ ServiceResponse response = new
	 * ServiceResponse(); try { service.deleteById(id);
	 * response.setReturnObject("Bin Type is removed successfully");
	 * response.setStatus(ConstantUtil.RESPONSE_SUCCESS); }catch(Exception e) {
	 * e.printStackTrace(); logger.error(e.getMessage(), e.fillInStackTrace());
	 * response.setStatus(ConstantUtil.RESPONSE_FAILURE);
	 * response.setErrorMessage(e.getMessage()); } return
	 * ResponseEntity.ok().body(response); }
	 */
}
