package com.checksammy.loca.serviceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.repository.QuotesProductAndServiceReposiotry;
import com.checksammy.loca.service.QuotesProductAndServiceService;
import com.checksammy.loca.utility.GlobalValues;

@Service
public class QuotesProductAndServiceServiceImpl implements QuotesProductAndServiceService {

	private static final Logger logger = LoggerFactory.getLogger(QuotesProductAndServiceServiceImpl.class);

	@Autowired
	private QuotesProductAndServiceReposiotry quotAndServiceReposiotry;

	@Override
	public String saveRequestAttachments(Long QuoteId, Long quoteProductId, MultipartFile[] files) {
		List<String> attachments = new ArrayList<String>();
//		String existingAttchments = quotAndServiceReposiotry.getRequestAttachment(quoteProductId);
		String status = "";
		try {
			String attachmentsName = "";
			File dir = new File(GlobalValues.QUOTES_PRODUCT_ATTACHMENT + File.separator + quoteProductId);
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

					attachments.add(fileName);
				} catch (Exception e) {
					logger.debug("Failed to upload " + file.getOriginalFilename() + " " + e.getMessage());
					status = "Failed to upload " + file.getOriginalFilename() + " " + e.getMessage();
					return status;
				}
			}
//			if (existingAttchments != null && existingAttchments != "") {
//				String[] elements = existingAttchments.split(",");
//				for (String oldAtachment : elements) {
//					attachments.add(oldAtachment);
//				}
//			}
			attachmentsName = String.join(",", attachments);
			System.out.println(attachmentsName);
			quotAndServiceReposiotry.updateAttachmentsById(quoteProductId, attachmentsName);
			if (attachmentsName.equals(null)) {
				status = "Attachments are not present";
			} else {
				status = attachmentsName + " Attachments are uploaded";
			}

		} catch (Exception e) {
			status = status + " Files not uploaded - " + e.getMessage();
		}
		return status;
	}

	@Override
	public String deleteById(Long quoProductId) {
		String status = "";
		try {
			quotAndServiceReposiotry.deleteById(quoProductId);
			status = "true";
		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
			status = "false";
		}

		return status;
	}

}
