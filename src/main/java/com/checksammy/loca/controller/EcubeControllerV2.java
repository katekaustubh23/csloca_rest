/**
 * 
 */
package com.checksammy.loca.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.checksammy.loca.ecube.model.ProductCCN;
import com.checksammy.loca.ecube.model.ProductCCNs;
import com.checksammy.loca.model.CCNProductsDetails;
import com.checksammy.loca.service.CCNProductsDetailsService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.Utility;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class handles Ecube API requests originating from mobile app and send
 * back the response.
 * 
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/ecube/v2")
public class EcubeControllerV2 {

	@Autowired
	private CCNProductsDetailsService ccnProductsDetailsService;

	private final String ECUBE_API_URL = "https://api.cleancitynetworks.com/v2";
	private final String ECUBE_API_TOKEN = "e8d92e1828c24e951e1ed7172950f1c7";

	RestTemplate restTemplate;
	HttpEntity<String> entity;
	ResponseEntity<String> apiResult;
	ObjectMapper mapper;
	ServiceResponse serviceResponse = new ServiceResponse();

	private static final Logger logger = LoggerFactory.getLogger(EcubeControllerV2.class);

	public EcubeControllerV2() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", ECUBE_API_TOKEN);
		entity = new HttpEntity<String>("parameters", headers);
	}

	/*
	 * @GetMapping("/products") public ResponseEntity<ServiceResponse> getProducts()
	 * { logger.info("Fetching Ecube products ..."); try { restTemplate = new
	 * RestTemplate(); apiResult = restTemplate.exchange(ECUBE_API_URL+"/products",
	 * HttpMethod.GET, entity, String.class); mapper = new ObjectMapper(); Products
	 * products = mapper.readValue(apiResult.getBody(), Products.class);
	 * logger.info(Utility.stringify(products));
	 * serviceResponse.setStatus("SUCCESS");
	 * serviceResponse.setReturnObject(products);
	 * 
	 * } catch (Exception e) { serviceResponse.setStatus("FAILURE");
	 * serviceResponse.setReturnObject(e.getLocalizedMessage());
	 * logger.error(e.getMessage(),e); } return
	 * ResponseEntity.ok().body(serviceResponse);
	 * 
	 * }
	 */

	/* FROM DB */
	@GetMapping("/products/details")
	public ResponseEntity<ServiceResponse> getProductsAndDetails() {
		logger.info("Fetching Ecube products and details ...");
		try {
			ProductCCNs productsResponse = new ProductCCNs();
			List<ProductCCN> productCCNList = new ArrayList<ProductCCN>();
			List<CCNProductsDetails> ccnProductsDetailList = ccnProductsDetailsService.getAll();
			for (CCNProductsDetails ccnProduct : ccnProductsDetailList) {
				productCCNList.add(productsResponse.addSaveData(ccnProduct));
			}
			productsResponse.setProducts(productCCNList);

			logger.info(Utility.stringify(productsResponse));
			serviceResponse.setStatus("SUCCESS");
			serviceResponse.setReturnObject(productsResponse);

		} catch (Exception e) {
			serviceResponse.setStatus("FAILURE");
			serviceResponse.setReturnObject(e.getLocalizedMessage());
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(serviceResponse);
	}

//	TODO: Create save data from CCN fetch request

	@GetMapping("/from/ccnetwork")
	public ResponseEntity<ServiceResponse> getProductsAndDetailsFromDB() {
		logger.info("Fetching Ecube products and details ...");
		try {
			ccnProductsDetailsService.deleteAllList();
			restTemplate = new RestTemplate();
			apiResult = restTemplate.exchange(ECUBE_API_URL + "/products/details", HttpMethod.GET, entity,
					String.class);
			mapper = new ObjectMapper();
			mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			ProductCCNs products = mapper.readValue(apiResult.getBody(), ProductCCNs.class);
			ProductCCNs productsResponse = new ProductCCNs();
			List<ProductCCN> productCCNList = new ArrayList<ProductCCN>();
			int i = 0;
			List<CCNProductsDetails> ccnProductsDetailList = new ArrayList<CCNProductsDetails>();
			for (ProductCCN productCCN : products.getProducts()) {
				System.out.println(i++);
				CCNProductsDetails ccnProductsDetails = products.getCCNProductDataFetch(productCCN);
				ccnProductsDetailList.add(ccnProductsDetails);
//				ccnProductsDetails = ccnProductsDetailsService.save(ccnProductsDetails);
				productCCNList.add(products.addSaveData(ccnProductsDetails));
			}
			ccnProductsDetailList = ccnProductsDetailsService.saveList(ccnProductsDetailList);
			productsResponse.setProducts(productCCNList);
			logger.info(Utility.stringify(productsResponse));
			serviceResponse.setStatus("SUCCESS");
			serviceResponse.setReturnObject(productsResponse);

		} catch (Exception e) {
			serviceResponse.setStatus("FAILURE");
			serviceResponse.setReturnObject(e.getLocalizedMessage());
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(serviceResponse);
	}

}
