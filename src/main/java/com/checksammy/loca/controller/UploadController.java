/**
 * 
 */
package com.checksammy.loca.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.CsvUploadModelDto;
import com.checksammy.loca.dto.ProductServiceDto;
import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.ProductService;
import com.checksammy.loca.repository.LocationPropertyDetailRepository;
import com.checksammy.loca.service.BinHistoryViewWebService;
import com.checksammy.loca.service.LocationService;
import com.checksammy.loca.service.ProductServiceService;
import com.checksammy.loca.service.ReportService;
import com.checksammy.loca.service.StorageService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;
import com.checksammy.loca.utility.GlobalValues;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

/**
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api")
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	StorageService storageService;

	@Autowired
	BinHistoryViewWebService binHistoryViewWebService;

	@Autowired
	LocationService locationService;

	@Autowired
	ReportService reportService;

	@Autowired
	LocationPropertyDetailRepository locationPropRepo;

	@Autowired
	ProductServiceService productServiceService;

	@PostMapping("/uploadBinPhotos/{binLocationId}")
	public ResponseEntity<ServiceResponse> uploadMultipleFiles(@PathVariable("binLocationId") Long binLocationId,
			@RequestParam("file") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			storageService.uploadBinPhotos(files, binLocationId);
			response.setReturnObject(files.length + " photos is/are added successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/binphotos/delete/{binLocationId}/{fileNames}")
	public ResponseEntity<ServiceResponse> deleteBinPhotosById(@PathVariable("binLocationId") Long binLocationId,
			@PathVariable("fileNames") String fileNames) {
		ServiceResponse response = new ServiceResponse();
		try {
			storageService.removeBinPhotos(binLocationId, fileNames);
			response.setReturnObject("Photos are removed successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/* new export PDF for bin details */
	@GetMapping("/binmanagement/export_pdf/{locationId}/{binLocationId}/{timeZone}")
	public ResponseEntity<Resource> exportPDF(@PathVariable("locationId") Long locationId,
			@PathVariable("binLocationId") Long binLocationId, @PathVariable("timeZone") Long timeZone,
			HttpServletRequest request) throws ResourceNotFoundException, FileNotFoundException, Exception {
		
		Resource file = null;
		try {
			file = storageService.getPDFByBinLocationId(locationId, binLocationId, request, timeZone);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(file);
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	/**
	 * UPLOAD Pick up images
	 * 
	 * @param pickUpId
	 * @param files
	 * @return
	 */
	@PostMapping("/uploadPickupImages/{pickUpId}")
	public ResponseEntity<ServiceResponse> uploadPickUpImages(@PathVariable("pickUpId") Long pickUpId,
			@RequestParam("email") String email, @RequestParam("file") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			storageService.uploadPickupPhotos(files, pickUpId, email);
			response.setReturnObject(files.length + " photos is/are added successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/uploadLocationImage/{locationId}")
	public ResponseEntity<ServiceResponse> uploacLocationImage(@PathVariable("locationId") Long locationId,
			@RequestParam("file") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			storageService.uploadLocationImage(files, locationId);
			response.setReturnObject(files.length + " photos is/are added successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/locationimage/delete/{locationId}/{fileNames}")
	public ResponseEntity<ServiceResponse> deleteLocationPhotoById(@PathVariable("locationId") Long locationId,
			@PathVariable("fileNames") String fileNames) {
		ServiceResponse response = new ServiceResponse();
		try {
			storageService.removeLocationPictures(locationId, fileNames);
			response.setReturnObject("Photos are removed successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/* Upload CSV FIle */
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@PostMapping("/service/url/{companyId}/{userId}")
	public ResponseEntity<ServiceResponse> uploadCsv(@PathVariable("companyId") Long companyId,
			@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<Location> presentLocationList = locationService.getLocationByPropertyCompanyId(companyId);
			String[] fileType = file.getOriginalFilename().split("\\.(?=[^\\.]+$)");
			String splitName = fileType[1];
			System.out.println(splitName);
			Map<String, String> mapping = new HashMap<String, String>();

			mapping.put("CASS Site Number", "cassSiteNumber");
			mapping.put("Brand", "brand");
			mapping.put("Store Number", "storeNumber");
			mapping.put("Center Name", "centerName");
			mapping.put("Location Address", "locationAddress");
			mapping.put("Location City", "locationCity");
			mapping.put("Location State/Prov", "locationState_Prov");
			mapping.put("Location Zip", "locationZip");
			mapping.put("Country", "locationCountry");
			File file2 = convertMultiPartToFile(file);
			if (!file.isEmpty() && splitName.equals("csv")) {
				HeaderColumnNameTranslateMappingStrategy<CsvUploadModelDto> strategy = new HeaderColumnNameTranslateMappingStrategy<CsvUploadModelDto>();
				strategy.setType(CsvUploadModelDto.class);
				strategy.setColumnMapping(mapping);
				CSVReader csvReader = null;
				try {
					csvReader = new CSVReader(new FileReader(file2));
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage(), e.fillInStackTrace());
					response.setStatus(ConstantUtil.RESPONSE_FAILURE);
					return ResponseEntity.ok().body(response);
				}
				CsvToBean csvToBean = new CsvToBean();
				List<CsvUploadModelDto> list = csvToBean.parse(strategy, csvReader);
				List<Location> newSaveLocationList = new ArrayList<Location>();
				if (list.size() <= 0) {
					response.setStatus(ConstantUtil.RESPONSE_FAILURE);
					response.setReturnObject("File is not in proper format.");
					return ResponseEntity.ok().body(response);
				}
				for (CsvUploadModelDto csvUploadModelDto : list) {
					Location newLocation = new Location();
					try {

						Location presentLocation = presentLocationList.stream().filter(
								name -> name.getPropertyName().equalsIgnoreCase(csvUploadModelDto.getCenterName()))
								.findAny().orElseThrow();
						if (presentLocation != null) {
							presentLocation.setStreetName(csvUploadModelDto.getLocationAddress());
							presentLocation.setCity(csvUploadModelDto.getLocationCity());
							presentLocation.setPinCode(csvUploadModelDto.getLocationZip());
							presentLocation.setState(csvUploadModelDto.getLocationState_Prov());
							presentLocation.setCountry(csvUploadModelDto.getLocationCountry());
							presentLocation.setNumberOfUnits(Integer.parseInt(csvUploadModelDto.getCassSiteNumber()));
							presentLocation.setCreatedBy(userId);
							presentLocation.setCreatedTs(Instant.now());
							presentLocation.setUpdatedBy(userId);
							presentLocation.setUpdatedTs(Instant.now());
							newSaveLocationList.add(presentLocation);
						} else {
							newLocation = csvUploadModelDto.convertToLocationModel(csvUploadModelDto, userId);
							newSaveLocationList.add(newLocation);
//							LocationPropertyDetails locationProperty = locationPropRepo.delete(propMgmtCompId);
						}
					} catch (Exception e) {
						logger.debug(e.getLocalizedMessage());
						if (csvUploadModelDto.getCenterName() != null) {
							newLocation = csvUploadModelDto.convertToLocationModel(csvUploadModelDto, userId);
							newSaveLocationList.add(newLocation);
						} else {
							response.setStatus(ConstantUtil.RESPONSE_FAILURE);
							response.setReturnObject("File is not in proper format.");
							return ResponseEntity.ok().body(response);
						}
					}
					/* TODO: implement location Save */
				}
				newSaveLocationList = locationService.addCompleteList(newSaveLocationList, companyId);
				response.setReturnObject(newSaveLocationList);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
				response.setReturnObject("file is not a .csv format");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject("File is not in proper format.");
		}

		return ResponseEntity.ok().body(response);
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		System.out.println("FILE : " + file.getName());

		File dir = new File(GlobalValues.IMPORT_CSV_FILE + File.separator + file.getName());
		logger.debug("Directory Name: " + dir.getAbsolutePath());
		if (!dir.exists())
			dir.mkdirs();
		storeFile(dir, file);
//		File convFile = new File(file.getOriginalFilename());
		File convFile = new File(dir.getAbsolutePath() + File.separator + file.getName());

		System.out.println("convFile : " + dir);
		System.out.println("convFile.getAbsolutePath : " + dir.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private Boolean storeFile(File dir, MultipartFile file) {

		byte[] bytes;
		try {
			bytes = file.getBytes();

			String fileWithPath = dir.getAbsolutePath() + File.separator + file.getOriginalFilename();
			Path path = Paths.get(fileWithPath);
			Files.write(path, bytes);
			logger.debug("inside StorageServiceImpl.storeMultipleFile() Method- Files uploaded");
			return true;
		} catch (IOException e) {
			logger.error(e.getMessage(), e.getCause());
			return false;
		}
	}

//	TODO: Import export functionality SPRINT 7 FOR PRODUCT AND SERVICEs
	/* Upload CSV FIle */
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@PostMapping("/import/product/services/{userId}")
	public ResponseEntity<ServiceResponse> uploadProductServiceCsv(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		ServiceResponse response = new ServiceResponse();
		try {
			String[] fileType = file.getOriginalFilename().split("\\.(?=[^\\.]+$)");
			String splitName = fileType[1];
			System.out.println(splitName);
			Map<String, String> mapping = new HashMap<String, String>();

			mapping.put("Name", "name");
			mapping.put("Description", "description");
			mapping.put("Cost", "cost");
			mapping.put("Category", "category");
			mapping.put("Taxable", "taxable");
			mapping.put("Active", "active");

			File file2 = convertMultiPartToFile(file);
			if (!file.isEmpty() && splitName.equals("csv")) {
				HeaderColumnNameTranslateMappingStrategy<ProductServiceDto> strategy = new HeaderColumnNameTranslateMappingStrategy<ProductServiceDto>();
				strategy.setType(ProductServiceDto.class);
				strategy.setColumnMapping(mapping);
				CSVReader csvReader = null;
				try {
					csvReader = new CSVReader(new FileReader(file2));
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage(), e.fillInStackTrace());
					response.setStatus(ConstantUtil.RESPONSE_FAILURE);
					return ResponseEntity.ok().body(response);
				}
				CsvToBean csvToBean = new CsvToBean();
				List<ProductServiceDto> list = csvToBean.parse(strategy, csvReader);
				
				if (list.size() <= 0) {
					response.setStatus(ConstantUtil.RESPONSE_FAILURE);
					response.setReturnObject("File is not in proper format.");
					return ResponseEntity.ok().body(response);
				}
				List<String> productName = new ArrayList<String>();
				List<ProductService> newProductServices = new ArrayList<ProductService>();
				for (ProductServiceDto productServiceDto : list) {
					Long presentStatus = productServiceService.findNamePresentOrNot(productServiceDto.getName());
					if (presentStatus.equals(Long.valueOf(0))) {
						ProductService productService = new ProductService();
						productService.setName(productServiceDto.getName());
						productService.setDescription(productServiceDto.getDescription());
						productService.setServiceTypeId(
								(productServiceDto.getCategory().equalsIgnoreCase("Service")) ? Long.valueOf(2)
										: Long.valueOf(1));
						
						productService.setUnitCost(Double.parseDouble(productServiceDto.getCost()));
						productService.setIsTaxApply(Boolean.valueOf(productServiceDto.getTaxable()));
						productService.setIsDeleted((Boolean.valueOf(productServiceDto.getActive()))?false:true);
						productService.setCreatedBy(userId);
						productService.setCreatedTs(Instant.now());
						newProductServices.add(productService);
					}else {
						productName.add(productServiceDto.getName());
					}
				}
				newProductServices = productServiceService.saveAllData(newProductServices);
				response.setReturnObject(newProductServices);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
				
				String existingNameList = String.join(",", productName);
				response.setErrorMessage((existingNameList != null && existingNameList !="" )?existingNameList +" This is already Present":null);
			} else {
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
				response.setReturnObject("file is not a .csv format");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject("File is not in proper format.");
		}

		return ResponseEntity.ok().body(response);
	}
	
	
	/* new export PDF for bin details */
	@GetMapping("/export/product/service/csv")
	public ResponseEntity<Resource> exportProductServiceCSV(HttpServletRequest request) throws ResourceNotFoundException, FileNotFoundException, Exception {
		Resource file = null;
		try {
			file = storageService.downloadProductServiceCSV(request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(file);
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	/* Upload Image for Chat module */
	@PostMapping("/user/chat/{userChatId}/{messageId}")
	public ResponseEntity<ServiceResponse> uploadModuleUserChat(@PathVariable("userChatId") String userChatId,@PathVariable("messageId") String messageId,
			@RequestParam("file") MultipartFile[] file) {
		ServiceResponse response = new ServiceResponse();
		try {
			storageService.uploadUserChatImage(file, userChatId, messageId);
			response.setReturnObject(file.length + " photos is/are added successfully.");
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
