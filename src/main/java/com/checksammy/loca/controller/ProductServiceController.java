package com.checksammy.loca.controller;

import java.time.Instant;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.model.ProductService;
import com.checksammy.loca.model.ServiceType;
import com.checksammy.loca.service.ProductServiceService;
import com.checksammy.loca.service.ServiceTypeService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/product/service")
public class ProductServiceController {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceController.class);

	@Autowired
	private ProductServiceService proServiceService;

	@Autowired
	private ServiceTypeService serviceTypeService;

	@GetMapping(value = "/all")
	public ResponseEntity<ServiceResponse> getAll() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<ProductService> productService = proServiceService.getAll();
			response.setReturnObject(productService);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<ServiceResponse> saveData(@RequestBody ProductService productService) {
		ServiceResponse response = new ServiceResponse();
		try {
			if(productService.getId() != null && productService.getId() > 0) {
				productService.setUpdatedBy(productService.getCreatedBy());
				productService.setUpdatedTs(Instant.now());
			}
			productService = proServiceService.saveData(productService);
			response.setReturnObject(productService);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/{proServiceId}")
	public ResponseEntity<ServiceResponse> getProductService(@PathVariable("proServiceId") Long proServiceId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<ProductService> productService = proServiceService.getProService(proServiceId);
			response.setReturnObject(productService.get());
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/servicetype")
	public ResponseEntity<ServiceResponse> getServiceType() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<ServiceType> serviceType = serviceTypeService.getAll();
			response.setReturnObject(serviceType);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	
	/*PRODUCT Attachment*/
	@PostMapping(value = "/attachments/{requestID}")
	public ResponseEntity<ServiceResponse> uploadingRequestAttachment(@PathVariable Long requestID,
			@RequestParam("file") MultipartFile[] file) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = proServiceService.saveRequestAttachments(requestID, file);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
	/*Delete APi*/
	@DeleteMapping(value = "/delete/{requestID}")
	public ResponseEntity<ServiceResponse> deleteProductAndService(@PathVariable Long requestID) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean status = proServiceService.deleteProductService(requestID);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
}
