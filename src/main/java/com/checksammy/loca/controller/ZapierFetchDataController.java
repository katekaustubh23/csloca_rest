package com.checksammy.loca.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.dto.ZapierListDto;
import com.checksammy.loca.dto.ZapierQuoteDto;
import com.checksammy.loca.dto.ZapierUserDto;
import com.checksammy.loca.model.Quote;
import com.checksammy.loca.model.ZapierSample;
import com.checksammy.loca.service.QuoteService;
import com.checksammy.loca.service.QuotesInternalNotesService;
import com.checksammy.loca.service.QuotesProductAndServiceService;
import com.checksammy.loca.service.ZapierSampleService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/zapier/quote")
public class ZapierFetchDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(ZapierFetchDataController.class);
	
	@Autowired
	private QuoteService quoteService;
	
	@Autowired
	private QuotesInternalNotesService quInternalNotesService;

	@Autowired
	private QuotesProductAndServiceService quoProductAndServiceService;
	
	@Autowired
	private ZapierSampleService zapierSampleService;

	@PostMapping(value = "/save")
	public ResponseEntity<ServiceResponse> saveQuotes(@RequestBody ZapierQuoteDto zapierQuote) {
		ServiceResponse response = new ServiceResponse();
		try {
			ZapierSample zapierSample = new ZapierSample();
			zapierSample.setQuoteNumber(zapierQuote.getQuote_number());
			zapierSample.setStatus(zapierQuote.getState());
			zapierSample.setQuoteInstruction(zapierQuote.getJob_description());
			
			ZapierUserDto clientObj = (ZapierUserDto) zapierQuote.getClient().get(0);
			
			zapierSample.setClientFirstName(clientObj.getFirst_name());
			zapierSample.setClientLastname(clientObj.getLast_name());
			zapierSample.setCompanyName(clientObj.getCompany_name());
			zapierSample.setCreatedAt(clientObj.getCreated_at());
			zapierSample.setIsCompany(clientObj.getIs_company());
			zapierSample.setPrimaryEmail(clientObj.getPrimary_email());
			zapierSample.setPrimaryPhone(clientObj.getPrimary_phone());
			zapierSample.setUpdatedAt(clientObj.getUpdated_at());

			zapierSample = zapierSampleService.saveData(zapierSample);
			
			ZapierListDto listObj = (ZapierListDto) zapierQuote.getList();
			

			response.setReturnObject(zapierSample);
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
	
	@PostMapping(value = "/save2")
	public ResponseEntity<ServiceResponse> saveQuotes2(@RequestBody ZapierQuoteDto zapierQuote) {
		ServiceResponse response = new ServiceResponse();
		try {
			Quote quotes = quoteService.saveDataZapierTestLimitedData(zapierQuote);
			response.setReturnObject(quotes);
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
	
	@GetMapping(value = "/getkeys")
	public ResponseEntity<ServiceResponse> getDataKey() {
		ServiceResponse response = new ServiceResponse();
		try {
//			response.setReturnObject(quotes);
			List<HashMap<String, Object>> finalArray = new ArrayList<HashMap<String,Object>>();
			
			
			String[] finalObjectString = {"id", "quote_number","state","job_description","client","rating","scheduled_at", "line_items"};
			for (String finalObject : finalObjectString) {
				HashMap<String, Object> sendKeyData = new HashMap<String, Object>();
				
				
				switch (finalObject) {
				case "id":
					sendKeyData.put("key", finalObject);
					sendKeyData.put("type", "integer");
					break;
				case "quote_number":
					sendKeyData.put("key", finalObject);
					sendKeyData.put("type", "string");
					break;
				case "state":
					sendKeyData.put("key", finalObject);
					sendKeyData.put("type", "string");
					break;
				case "job_description":
					sendKeyData.put("key", finalObject);
					sendKeyData.put("type", "string");
					break;
				case "client":
					sendKeyData.put("key", finalObject);					
					List<HashMap<String, Object>> clientData = clientDataField();
					sendKeyData.put("children", clientData);
					break;
				case "rating":
					sendKeyData.put("key", finalObject);
					sendKeyData.put("type", "string");
					break;
				case "scheduled_at":
					sendKeyData.put("key", finalObject);
					sendKeyData.put("type", "string");
					break;
				case "line_items":
					sendKeyData.put("key", finalObject);					
					List<HashMap<String, Object>> listData = getListItems();
					sendKeyData.put("children", listData);
					break;
				default:
					break;
				}
				finalArray.add(sendKeyData);
			}

			response.setReturnObject(finalArray);
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

	private List<HashMap<String, Object>> clientDataField() {
		String[] clientObjectString = {"first_name", "last_name","company_name","is_company","created_at", "updated_at","primary_phone","primary_email"};
		List<HashMap<String, Object>> clientArrayList = new ArrayList<HashMap<String,Object>>();
		for (String clientObjKey : clientObjectString) {
			HashMap<String, Object> clientObject = new HashMap<String, Object>();
			switch (clientObjKey) {
			case "first_name":
				clientObject.put("key", clientObjKey);
				clientObject.put("type","string");
				break;
			case "last_name":
				clientObject.put("key", clientObjKey);
				clientObject.put("type","string");
				break;
			case "company_name":
				clientObject.put("key", clientObjKey);
				clientObject.put("type","string");
				break;
			case "is_company":
				clientObject.put("key", clientObjKey);
				clientObject.put("type","boolean");
				break;
			case "created_at":
				clientObject.put("key", clientObjKey);
				clientObject.put("type","string");
				break;
			case "updated_at":
				clientObject.put("key", clientObjKey);
				clientObject.put("type","string");
				break;
			case "primary_phone":
				clientObject.put("key", clientObjKey);
				clientObject.put("type","string");
//				List<HashMap<String, Object>> phoneObject = clientPhoneNumber();
//				clientObject.put("children", phoneObject);
				break;
			case "primary_email":
				clientObject.put("key", clientObjKey);
				clientObject.put("type","string");
//				List<HashMap<String, Object>> emailObject = clientEmailContact();
//				clientObject.put("children", emailObject);
				break;

			default:
				break;
			}
			clientArrayList.add(clientObject);
		}
		
		return clientArrayList;
	}

	private List<HashMap<String, Object>> clientEmailContact() {
		List<HashMap<String, Object>> phoneObjectList = new ArrayList<HashMap<String,Object>>();
		String[] primaryEmailString = {"id", "address","description","primary","created_at"};
		
		for (String primaryEmail : primaryEmailString) {
			HashMap<String, Object> primaryPhoneObject = new HashMap<String, Object>();
			switch (primaryEmail) {
			case "id":
				primaryPhoneObject.put("key", primaryEmail);
				primaryPhoneObject.put("type", "integer");
				break;
			case "address":
				primaryPhoneObject.put("key", primaryEmail);
				primaryPhoneObject.put("type", "string");
				break;
			case "description":
				primaryPhoneObject.put("key", primaryEmail);
				primaryPhoneObject.put("type", "string");
				break;
			case "primary":
				primaryPhoneObject.put("key", primaryEmail);
				primaryPhoneObject.put("type", "boolean");
				break;
			case "created_at":
				primaryPhoneObject.put("key", primaryEmail);
				primaryPhoneObject.put("type", "boolean");
				break;

			default:
				break;
			}
			phoneObjectList.add(primaryPhoneObject);
		}
		return phoneObjectList;
	}

	private List<HashMap<String, Object>> clientPhoneNumber() {
		List<HashMap<String, Object>> phoneObjectList = new ArrayList<HashMap<String,Object>>();
		String[] primaryPhoneString = {"id", "number","description","primary","sms_allowed","created_at"};
		
		for (String primaryPhone : primaryPhoneString) {
			HashMap<String, Object> primaryPhoneObject = new HashMap<String, Object>();
			switch (primaryPhone) {
			case "id":
				primaryPhoneObject.put("key", primaryPhone);
				primaryPhoneObject.put("type", "integer");
				break;
			case "number":
				primaryPhoneObject.put("key", primaryPhone);
				primaryPhoneObject.put("type", "string");
				break;
			case "description":
				primaryPhoneObject.put("key", primaryPhone);
				primaryPhoneObject.put("type", "string");
				break;
			case "primary":
				primaryPhoneObject.put("key", primaryPhone);
				primaryPhoneObject.put("type", "boolean");
				break;
			case "sms_allowed":
				primaryPhoneObject.put("key", primaryPhone);
				primaryPhoneObject.put("type", "boolean");
				break;
			case "created_at":
				primaryPhoneObject.put("key", primaryPhone);
				primaryPhoneObject.put("type", "date");
				break;

			default:
				break;
			}
			phoneObjectList.add(primaryPhoneObject);
		}
		return phoneObjectList;
	}
	
	private List<HashMap<String, Object>> getListItems() {
		/*
		 * "name": "Residential Junk Removal - 1/2 Truck",
            "description": "1/2 Truck - 8 cu. yds",
            "created_at": "2020-08-18T12:02:28Z",
            "updated_at": "2020-08-18T12:02:28Z",
            "qty": 1.0,
            "unit_cost": 255.0,
            "cost": 255.0,
            "taxable": "true"

		 * */
		List<HashMap<String, Object>> objectList = new ArrayList<HashMap<String,Object>>();
		String[] primaryListString = {"key", "name","description","created_at","updated_at","qty","unit_cost","cost","taxble"};
		
		for (String primaryList : primaryListString) {
			HashMap<String, Object> primaryListObject = new HashMap<String, Object>();
			switch (primaryList) {
			case "key" :
				primaryListObject.put("key", primaryList);
				primaryListObject.put("type", "string");
				break;
			case "name":
				primaryListObject.put("key", primaryList);
				primaryListObject.put("type", "string");
				break;
			case "description":
				primaryListObject.put("key", primaryList);
				primaryListObject.put("type", "string");
				break;
			case "created_at":
				primaryListObject.put("key", primaryList);
				primaryListObject.put("type", "string");
				break;
			case "updated_at":
				primaryListObject.put("key", primaryList);
				primaryListObject.put("type", "string");
				break;
			case "qty":
				primaryListObject.put("key", primaryList);
				primaryListObject.put("type", "integer");
				break;
			case "unit_cost":
				primaryListObject.put("key", primaryList);
				primaryListObject.put("type", "integer");
				break;
			case "cost":
				primaryListObject.put("key", primaryList);
				primaryListObject.put("type", "integer");
				break;
			case "taxble":
				primaryListObject.put("key", primaryList);
				primaryListObject.put("type", "boolean");
				break;
			default:
				break;
				
			}
			objectList.add(primaryListObject);
		}
		return  objectList;
	
	}
}
