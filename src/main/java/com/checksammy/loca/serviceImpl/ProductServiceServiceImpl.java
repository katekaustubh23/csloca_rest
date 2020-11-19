package com.checksammy.loca.serviceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.model.ProductService;
import com.checksammy.loca.repository.ProductServiceRepository;
import com.checksammy.loca.service.ProductServiceService;
import com.checksammy.loca.utility.GlobalValues;

@Service
public class ProductServiceServiceImpl implements ProductServiceService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceServiceImpl.class);

//	final private String PRODUCT = "product";

	@Autowired
	private ProductServiceRepository productServiceRepository;

	@Override
	public List<ProductService> getAll() {
		return productServiceRepository.findAll();
	}

	@Override
	public ProductService saveData(ProductService productService) {
		return productServiceRepository.save(productService);
	}

	@Override
	public Optional<ProductService> getProService(Long proServiceId) {
		return productServiceRepository.findById(proServiceId);
	}

	@Override
	public String saveRequestAttachments(Long requestID, MultipartFile[] files) {
		List<String> attachments = new ArrayList<String>();
		String status = "";
		String existingAttchments = productServiceRepository.findImageName(requestID);
		String attachmentsName = "";
		try {

			File dir = new File(GlobalValues.PRODUCT_IMAGE_FILE + File.separator + requestID);
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
					logger.info("File Name: " + fileName);
					byte[] bytes;
					String fileWithPath = dir.getAbsolutePath() + File.separator + fileName;
					logger.info("fileWithPath: " + fileWithPath);
					bytes = file.getBytes();
					Path path = Paths.get(fileWithPath);
					Files.write(path, bytes);
					attachmentsName = fileName;
					attachments.add(fileName);
					status = attachmentsName + " Attachments are uploaded";
				} catch (Exception e) {
					logger.debug("Failed to upload " + file.getOriginalFilename() + " " + e.getMessage());
					status = "Failed to upload " + file.getOriginalFilename() + " " + e.getMessage();
					return status;
				}
			}
			if(existingAttchments !=null && existingAttchments != "") {
				String[] elements = existingAttchments.split(",");  
				for(String oldAtachment: elements) {
					attachments.add(oldAtachment);
				}
			}
			attachmentsName = String.join(",", attachments);
			System.out.println(attachmentsName);
			productServiceRepository.updateAttachmentsById(requestID, attachmentsName);
		} catch (Exception e) {
			status = status + " Files not uploaded - " + e.getMessage();
		}
		return status;
	}

	@Override
	public Boolean deleteProductService(Long requestID) {
		Boolean status = false;
		try {
			productServiceRepository.deleteByChangeStatus(requestID);
			status = true;
		} catch (Exception e) {
			logger.debug(e.getCause().getMessage());
			status = false;
		}
		return status;
	}

	@Override
	public Long findNamePresentOrNot(String name) {
		return productServiceRepository.existsByNameAndDelete(name);
	}

	@Override
	public List<ProductService> saveAllData(List<ProductService> newProductServices) {
		return productServiceRepository.saveAll(newProductServices);
	}

}
