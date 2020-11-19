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

import com.checksammy.loca.dto.TaxGroupMasterDto;
import com.checksammy.loca.model.InvoiceTaxNumberDisplay;
import com.checksammy.loca.model.TaxGroupMapping;
import com.checksammy.loca.model.TaxGroupMaster;
import com.checksammy.loca.model.TaxMaster;
import com.checksammy.loca.service.InvoiceTaxNumberDisplayService;
import com.checksammy.loca.service.TaxGroupMappingService;
import com.checksammy.loca.service.TaxGroupMasterService;
import com.checksammy.loca.service.TaxMasterService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/tax")
public class TaxMasterController {

	private static final Logger logger = LoggerFactory.getLogger(TaxMasterController.class);

	@Autowired
	private TaxMasterService taxMasterService;

	@Autowired
	private TaxGroupMasterService taxGroupMasterService;

	@Autowired
	private TaxGroupMappingService taxGroupMappingService;
	
	@Autowired
	private InvoiceTaxNumberDisplayService invNumberDisplayService;

	@GetMapping(value = "/all")
	public ResponseEntity<ServiceResponse> getAllTaxList() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<TaxMaster> taxMasters = taxMasterService.getAll();
			response.setReturnObject(taxMasters);
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
	public ResponseEntity<ServiceResponse> saveTaxMaster(@RequestBody List<TaxMaster> taxMasters2) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<TaxMaster> taxMasters = taxMasterService.saveData(taxMasters2);
			response.setReturnObject(taxMasters);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setReturnObject(e.getCause().getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/find/{taxId}")
	public ResponseEntity<ServiceResponse> getTaxSingle(@PathVariable("taxId") Long taxId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<TaxMaster> taxMaster = taxMasterService.getSingleTax(taxId);
			response.setReturnObject(taxMaster.get());
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping(value = "/delete/{taxId}")
	public ResponseEntity<ServiceResponse> deleteTax(@PathVariable("taxId") Long taxId) {
		ServiceResponse response = new ServiceResponse();
		try {
			taxMasterService.deleteTax(taxId);
			response.setReturnObject(null);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

//	--------------------------------------------------------------

	@GetMapping(value = "/all/groupmaster")
	public ResponseEntity<ServiceResponse> getAllTaxGroupList() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<TaxGroupMaster> taxMasters = taxGroupMasterService.getAllTaxGroup();
			response.setReturnObject(taxMasters);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/group/save")
	public ResponseEntity<ServiceResponse> saveTaxGroupMaster(@RequestBody TaxGroupMasterDto taxGroupMasterDto) {
		ServiceResponse response = new ServiceResponse();
		try {
			if (taxGroupMasterDto.getId() != null && taxGroupMasterDto.getId() > 0) {
				taxGroupMappingService.deleteMappingByTaxGrpId(taxGroupMasterDto.getId());
			}
			TaxGroupMaster taxMaster = taxGroupMasterDto.setTaxGroupMaster(taxGroupMasterDto);
			taxMaster = taxGroupMasterService.saveData(taxMaster);
			for (Long taxId : taxGroupMasterDto.getTaxListId()) {
				TaxGroupMapping taxGroupMapping = new TaxGroupMapping();
				taxGroupMapping.setTaxGroupId(taxMaster.getId());
				taxGroupMapping.setTaxId(taxId);
				taxGroupMapping = taxGroupMappingService.saveMapping(taxGroupMapping);
			}
			response.setReturnObject(taxMaster);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setReturnObject(e.getCause().getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/invoice/display")
	public ResponseEntity<ServiceResponse> displayTaxOnInvoice() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<InvoiceTaxNumberDisplay> displayInvoiceTax = invNumberDisplayService.getAll();
			response.setReturnObject(displayInvoiceTax);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/invoice/display/save")
	public ResponseEntity<ServiceResponse> displayTaxOnInvoiceSave(@RequestBody InvoiceTaxNumberDisplay invoiceTaxNumberDisplay) {
		ServiceResponse response = new ServiceResponse();
		try {
			invoiceTaxNumberDisplay = invNumberDisplayService.saveData(invoiceTaxNumberDisplay);
			response.setReturnObject(invoiceTaxNumberDisplay);
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
