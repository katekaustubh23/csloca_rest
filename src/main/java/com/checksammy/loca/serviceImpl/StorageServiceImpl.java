
/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.exception.MyFileNotFoundException;
import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinContent;
import com.checksammy.loca.model.BinDestination;
import com.checksammy.loca.model.BinDetailsView;
import com.checksammy.loca.model.BinHistoryViewWeb;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.PickupRequest;
import com.checksammy.loca.model.ProductService;
import com.checksammy.loca.model.ServiceType;
import com.checksammy.loca.repository.BinContentRepository;
import com.checksammy.loca.repository.BinDestinationRepository;
import com.checksammy.loca.repository.ProductServiceRepository;
import com.checksammy.loca.repository.ServiceTypeRepository;
import com.checksammy.loca.service.BinDetailService;
import com.checksammy.loca.service.BinHistoryViewWebService;
import com.checksammy.loca.service.EmailService;
import com.checksammy.loca.service.LocationService;
import com.checksammy.loca.service.PickupRequestService;
import com.checksammy.loca.service.ReportService;
import com.checksammy.loca.service.StorageService;
import com.checksammy.loca.utility.ConvertFile;
import com.checksammy.loca.utility.GlobalValues;
import com.itextpdf.text.DocumentException;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class StorageServiceImpl implements StorageService {

	private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
	
	public static final String EMAIL_PICKUP = "ops@checksammy.com";

	@Autowired
	BinHistoryViewWebService binHistoryViewWebService;

	@Autowired
	LocationService locationService;

	@Autowired
	ReportService reportService;

	@Autowired
	private BinDetailService binDetailService;

	@Autowired
	private PickupRequestService pickupRequestService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	BinContentRepository binContentRepository;
	
	@Autowired
	ProductServiceRepository productServiceRepository;
	
	@Autowired
	ServiceTypeRepository serviceTypeRepository;
	
	@Autowired
	BinDestinationRepository binDestinationRepository;

	@Override
	public void uploadBinPhotos(MultipartFile[] files, Long binLocationId) throws IOException {
		logger.debug("inside StorageServiceImpl.uploadBinPhotos() Method");
		File dir = new File(GlobalValues.BIN_PHOTOS_DIR_PATH + File.separator + binLocationId);
		logger.debug("Directory Name: " + dir.getAbsolutePath());

		if (!dir.exists())
			dir.mkdirs();

		for (MultipartFile file : files) {
			storeFile(dir, file);
		}
	}

	@Override
	public void uploadPickupPhotos(MultipartFile[] files, Long pickupRequestId, String email)
			throws IOException, ResourceNotFoundException, MessagingException {
		logger.debug("inside StorageServiceImpl.PickUpPhotos() Method");
		File dir = new File(GlobalValues.PICKUP_DIR_PATH + File.separator + pickupRequestId);
		logger.debug("Directory Name: " + dir.getAbsolutePath());

		if (!dir.exists())
			dir.mkdirs();

		for (MultipartFile file : files) {
			storeFile(dir, file);
		}

		Optional<PickupRequest> pickupRequest = pickupRequestService.findById(pickupRequestId);
		Optional<Location> location = locationService.getLocationById(pickupRequest.get().getLocationId());

		String[] imageName = pickupRequest.get().getBinPhotos().split(",");

		String imageUrlPath = "";
		if(pickupRequest.get().getBinPhotos().isBlank() && pickupRequest.get().getBinPhotos().isEmpty()) {
			imageUrlPath="No attachment present";
		}else {
			for (int i = 0; i < imageName.length; i++) {
				imageUrlPath = imageUrlPath + "<img src =\"" + GlobalValues.PICKUP_IMAGE_PATH + pickupRequest.get().getId()
						+ "/" + imageName[i] + "\"><br>";
			}
		}
		

//		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//		Date dateobj = new Date();
//		System.out.println(df.format(dateobj));
		String emailContent = "<html>\r\n" + "\r\n" + "<h3>Hey checksammy,</h3>\r\n" + "\r\n" + "\r\n"
				+ "<p>Pickup request from property manager <b>" + email + "</b></p>\r\n" + "\r\n" + "\r\n"
				+ "<h4>Bin Details</h4>\r\n" + "<div style='margin: 10px'>\r\n" + "<p>Pickup location name : <b>"
				+ location.get().getPropertyName() + "</b> </p>\r\n" + "<p>Pickup date : <b>"
				+ pickupRequest.get().getPickupDate() + "</b> </p>\r\n" + "<p>Pickup Time Range: <b>"
				+ pickupRequest.get().getPickupTimerange() + "</b> </p>\r\n" + "<p>Status: <b>"
				+ pickupRequest.get().getStatus() + "</b></p>\r\n" + "</div>" + "<h4>Attachment</h4>\r\n"
				+ "<div>" + imageUrlPath + "\r\n" + "</div>\r\n" + "</html>";
		String emailStatus = emailService.sendSupportEmailHtmlFormatToUser(emailContent, EMAIL_PICKUP,
				email, "Pickup Request from " + email);

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

	@Override
	public void removeBinPhotos(Long binLocationId, String fileNames) throws IOException {
		if (fileNames.contains(",")) {
			String[] fileNameArr = fileNames.split(",");
			Arrays.stream(fileNameArr).forEach(fileName -> {
				File dir = new File(
						GlobalValues.BIN_PHOTOS_DIR_PATH + File.separator + binLocationId + File.separator + fileName);
				if (dir.exists())
					FileSystemUtils.deleteRecursively(dir);
			});
		} else {
			File dir = new File(
					GlobalValues.BIN_PHOTOS_DIR_PATH + File.separator + binLocationId + File.separator + fileNames);
			if (dir.exists())
				FileSystemUtils.deleteRecursively(dir);
		}
	}

	/**
	 * export BIN MANAGEMENT PDF file
	 */
	@Override
	public Resource getPDFByBinLocationId(Long locationId, Long binLocationId, HttpServletRequest request,
			Long timeZone) throws DocumentException, Exception {
		String fileName = "service_details_servicelocation_" + binLocationId + ".csv";
		String dest = GlobalValues.REPORTS_DIR_PATH + "\\" + fileName;

		BinHistoryViewWeb binHistoryViewWeb = binHistoryViewWebService.getDataByBinLocationId(binLocationId);
		List<BinDetailsView> binDetails = binDetailService.getBinDetailView(binHistoryViewWeb.getBinLocationId());

		List<BinContent> binContents = binContentRepository.getListByBinTypeId(binHistoryViewWeb.getBinTypeId());
		
		Optional<BinDestination> distinationLocation = binDestinationRepository
				.findByIdIsDeleted(binDetails.get(0).getBinDestinationId());
		
		if (binHistoryViewWeb != null) {
			try {
//				new BinDetailsExportPdf().createPdf(dest, binHistoryViewWeb, binDetails, timeZone, binContents, distinationLocation);
				new ConvertFile().createCSV(dest, binHistoryViewWeb, binDetails, timeZone, binContents, distinationLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		File file = new File(GlobalValues.REPORTS_DIR_PATH + "\\" + fileName);
		if (file.exists()) {
			Resource resource = loadFileAsResource(fileName);
			// Try to determine file's content type
			String contentType = null;

			try {
				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			} catch (IOException ex) {
			}

			// Fallback to the default content type if type could not be determined
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return resource;
		} else {
			return null;
		}

	}

	/**
	 * Load file
	 */
	public Resource loadFileAsResource(String fileName) {
		try {
			String dest = GlobalValues.REPORTS_DIR_PATH + "\\" + fileName;
			Path filePath = Paths.get(dest);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}
	
	public Resource loadFileAsForExportResource(String fileName) {
		try {
			String dest = GlobalValues.PRODUCT_IMAGE_FILE + "\\" + fileName;
			Path filePath = Paths.get(dest);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

	@Override
	public void uploadLocationImage(MultipartFile[] files, Long locationId) {
		logger.debug("inside StorageServiceImpl.uploadLocationImage() Method");
		File dir = new File(GlobalValues.LOCATION_IMAGE_PATH + File.separator + locationId);
		logger.debug("Directory Name: " + dir.getAbsolutePath());

		if (!dir.exists())
			dir.mkdirs();

		for (MultipartFile file : files) {
			storeFile(dir, file);
		}
	}

	@Override
	public void removeLocationPictures(Long locationId, String fileNames) {
		if (fileNames.contains(",")) {
			String[] fileNameArr = fileNames.split(",");
			Arrays.stream(fileNameArr).forEach(fileName -> {
				File dir = new File(
						GlobalValues.LOCATION_IMAGE_PATH + File.separator + locationId + File.separator + fileName);
				if (dir.exists())
					FileSystemUtils.deleteRecursively(dir);
			});
		} else {
			File dir = new File(
					GlobalValues.BIN_PHOTOS_DIR_PATH + File.separator + locationId + File.separator + fileNames);
			if (dir.exists())
				FileSystemUtils.deleteRecursively(dir);
		}		
	}

	@Override
	public Resource downloadProductServiceCSV(HttpServletRequest request) {
		String fileName = "export_product_service.csv";
		String dest = GlobalValues.PRODUCT_IMAGE_FILE + "\\" + fileName;

		List<ProductService> productServices = productServiceRepository.findAll();
		List<ServiceType> serviceTypes = serviceTypeRepository.findAll();

		
		if (productServices.size() > 0) {
			try {
//				new BinDetailsExportPdf().createPdf(dest, binHistoryViewWeb, binDetails, timeZone, binContents, distinationLocation);
				new ConvertFile().createForProductServiceCSV(dest,productServices,serviceTypes);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		File file = new File(GlobalValues.PRODUCT_IMAGE_FILE + "\\" + fileName);
		if (file.exists()) {
			Resource resource = loadFileAsForExportResource(fileName);
			// Try to determine file's content type
			String contentType = null;

			try {
				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			} catch (IOException ex) {
			}

			// Fallback to the default content type if type could not be determined
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return resource;
		} else {
			return null;
		}
	}

	@Override
	public void uploadUserChatImage(MultipartFile[] files, String userChatId, String messageId) {
		List<String> attachments = new ArrayList<String>();
		String status = "";
		try {
			String attachmentsName = "";
			File dir = new File(GlobalValues.SET_USER_CHAT_IMAGE + File.separator + userChatId +File.separator+messageId);
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				try {
					logger.info("Directory Name: " + dir.getAbsolutePath());
					if (!dir.exists())
						dir.mkdirs();
					logger.info("Directory created.");
//					File uploadFile = convert(file);
//					System.out.println("uploadFile.getAbsolutePath : " + uploadFile.getAbsolutePath());

//					String fileName = ATTACHMENTS + "_" + requestId + "_" + System.currentTimeMillis() + "." + getFileExtension(uploadFile);
					String fileName = file.getOriginalFilename();
					logger.info("File Name: "+fileName);
					byte[] bytes;
					String fileWithPath = dir.getAbsolutePath() + File.separator + fileName;
					logger.info("fileWithPath: "+fileWithPath);
					bytes = file.getBytes();
					Path path = Paths.get(fileWithPath);
					Files.write(path, bytes);

					attachments.add(fileName);
				} catch (Exception e) {
					logger.debug("Failed to upload " + file.getOriginalFilename() + " " + e.getMessage());
					status = "Failed to upload " + file.getOriginalFilename() + " " + e.getMessage();
//					return status;
				}
			}
//			if(existingAttchments !=null && existingAttchments != "") {
//				String[] elements = existingAttchments.split(",");  
//				for(String oldAtachment: elements) {
//					attachments.add(oldAtachment);
//				}
//			}
			attachmentsName = String.join(",", attachments);
			System.out.println(attachmentsName);
			if(attachmentsName.equals(null)) {
				status = "Attachments are not present";
			}else {
				status = attachmentsName + " Attachments are uploaded";
			}
			
		} catch (Exception e) {
			status = status + " Files not uploaded - " + e.getMessage();
		}
	}

	@Override
	public void uploadProfilePic(MultipartFile files, Long userId) {
		logger.debug("inside StorageServiceImpl.uploadBinPhotos() Method");
		File dir = new File(GlobalValues.UPLOAD_PROFILE_PIC + File.separator + userId);
		logger.debug("Directory Name: " + dir.getAbsolutePath());

		if (!dir.exists())
			dir.mkdirs();

//		for (MultipartFile file : files) {
			storeFile(dir, files);
//		}
	}

}

