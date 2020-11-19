package com.checksammy.loca.controller;

import java.util.ArrayList;
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

import com.checksammy.loca.dto.FieldTypeInstanceDto;
import com.checksammy.loca.model.CustomFieldCategory;
import com.checksammy.loca.model.FieldType;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.model.TransFlow;
import com.checksammy.loca.service.BinLocationCustomInstanceService;
import com.checksammy.loca.service.CompanyCustomFieldService;
import com.checksammy.loca.service.CustomFieldCategoryService;
import com.checksammy.loca.service.CustomFieldInstanceService;
import com.checksammy.loca.service.FieldTypeInstanceService;
import com.checksammy.loca.service.FieldTypeService;
import com.checksammy.loca.service.InvoiceCustomInstanceService;
import com.checksammy.loca.service.JobCustomInstanceService;
import com.checksammy.loca.service.JobRequestCustomFieldService;
import com.checksammy.loca.service.LocationCustomFieldService;
import com.checksammy.loca.service.QuotesCustomInstanceService;
import com.checksammy.loca.service.TransFlowService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/custom")
public class CustomFieldController {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationSettingsController.class);

	@Autowired
	private CustomFieldCategoryService customFieldCategoryService;

	@Autowired
	private FieldTypeInstanceService fieldTypeInstanceService;

	@Autowired
	private FieldTypeService fieldTypeService;

	@Autowired
	private TransFlowService transFlowService;
	
	/*All Custom field table from user, location, property(Company), request*/
	
	@Autowired
	private CustomFieldInstanceService customFieldInstanceService;
	
	@Autowired
	private JobRequestCustomFieldService requestCustomFieldService;
	
	@Autowired
	private QuotesCustomInstanceService quotesCustomInstanceService;
	
	@Autowired
	private LocationCustomFieldService locationCustomFieldService;
	
	@Autowired
	private CompanyCustomFieldService companyCustomFieldService;
	
	@Autowired
	private BinLocationCustomInstanceService binCusInstanceService;
	
	@Autowired
	private JobCustomInstanceService jobCustomInstanceService;
	
	@Autowired
	private InvoiceCustomInstanceService invoiceCustomInstanceService;

	/* CUSTOM FIELD CATEGORY */
	@GetMapping("/field/category/master")
	public ResponseEntity<ServiceResponse> getAllCustomFieldCategory() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<CustomFieldCategory> customFieldCategorys = customFieldCategoryService.getAllList();
			response.setReturnObject(customFieldCategorys);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/field/category/master/{customId}")
	public ResponseEntity<ServiceResponse> getCustomFieldCategory(@PathVariable("customId") Long customId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<CustomFieldCategory> customFieldCategory = customFieldCategoryService.getByCustomFieldId(customId);
			response.setReturnObject(customFieldCategory);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

//	 FIELD INSTANCE BY CUSTOMER TYPE

	@GetMapping("/field/list/{customBy}")
	public ResponseEntity<ServiceResponse> getListByCustomBy(@PathVariable("customBy") String customBy) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<FieldTypeInstanceDto> fieldInstanceList = fieldTypeInstanceService.getListCustomBy(customBy);
			response.setReturnObject(fieldInstanceList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* DELETED FIELD INSTANCE */
	@DeleteMapping("/delete/field/{fieldInstanceId}")
	public ResponseEntity<ServiceResponse> deleteFieldInstance(@PathVariable("fieldInstanceId") Long fieldInstanceId) {
		ServiceResponse response = new ServiceResponse();
		try {
			fieldTypeInstanceService.deleteField(fieldInstanceId);
			customFieldInstanceService.deleteFromUser(fieldInstanceId);
			requestCustomFieldService.deleteFromRequestCustom(fieldInstanceId);
			quotesCustomInstanceService.deleteFromQuoteCustom(fieldInstanceId);
			locationCustomFieldService.deleteFromLocationCustom(fieldInstanceId);
			companyCustomFieldService.deleteFromComapnyCustom(fieldInstanceId);
			binCusInstanceService.deleteFromBinCustom(fieldInstanceId);
			jobCustomInstanceService.deleteByFieldInstanceId(fieldInstanceId);
			invoiceCustomInstanceService.deleteByFieldInstanceId(fieldInstanceId);
			response.setReturnObject("Field Deleted");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* SAVE FIELD TYPE AND INSTANCE */
	@GetMapping("/field/instance/master")
	public ResponseEntity<ServiceResponse> getFieldTypeInstance() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<FieldTypeInstance> fieldTypeInstance = fieldTypeInstanceService.getAllFieldTypeInstance();
			response.setReturnObject(fieldTypeInstance);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	@PostMapping("/field/instance/master")
	public ResponseEntity<ServiceResponse> saveFieldTypeInstance(@RequestBody FieldTypeInstance fieldTypeInstance) {
		ServiceResponse response = new ServiceResponse();
		try {

			if (fieldTypeInstance.getFieldType() != null) {
				if (fieldTypeInstance.getFieldType().getId() != null && fieldTypeInstance.getFieldType().getId() > 0) {
					fieldTypeInstance.setFieldType(fieldTypeService.saveFieldType(fieldTypeInstance.getFieldType()));
				} else {
					FieldType fieldType = fieldTypeService
							.findByFieldName(fieldTypeInstance.getFieldType().getDisplayName());
					if (fieldType != null) {
						fieldTypeInstance.setFieldType(fieldType);
					} else {
						fieldTypeInstance
								.setFieldType(fieldTypeService.saveFieldType(fieldTypeInstance.getFieldType()));
					}
				}
			} else {
				response.setReturnObject("Field name not present");
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
				return ResponseEntity.ok().body(response);
			}
			String[] stringCustomer = fieldTypeInstance.getCustomer().split(",");
			List<FieldTypeInstance> findFieldTypeInstanceList = new ArrayList<FieldTypeInstance>();
			if (fieldTypeInstance.getId() != null && fieldTypeInstance.getId() > 0) {
				fieldTypeInstance = fieldTypeInstanceService.saveField(fieldTypeInstance);
				response.setReturnObject(fieldTypeInstance);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				findFieldTypeInstanceList = fieldTypeInstanceService
						.findByFieldIdAndCustomer(fieldTypeInstance.getFieldType().getId(), stringCustomer[0]);
				if (findFieldTypeInstanceList.size() > 0) {
					logger.error("Field Name Already Present");
					response.setReturnObject("Field name already present for this module");
					response.setErrorMessage("Field name already present for this module");
					response.setStatus(ConstantUtil.RESPONSE_FAILURE);
//					return ResponseEntity.ok().body(response);
				} else {
					fieldTypeInstance = fieldTypeInstanceService.saveField(fieldTypeInstance);
					response.setReturnObject(fieldTypeInstance);
					response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* SAVE FIELD TYPE AND INSTANCE */
	@GetMapping("/field/instance/master/{instanceId}")
	public ResponseEntity<ServiceResponse> getFieldTypeInstanceById(@PathVariable("instanceId") Long instanceId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeInstanceService
					.getFieldTypeInstanceById(instanceId);
			response.setReturnObject(fieldTypeInstance);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	@GetMapping("/transflow/list")
	public ResponseEntity<ServiceResponse> getAllTransFlow() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<TransFlow> transFlows = transFlowService.getAllList();
			response.setReturnObject(transFlows);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
}
