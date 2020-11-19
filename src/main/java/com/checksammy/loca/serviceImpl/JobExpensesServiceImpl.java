package com.checksammy.loca.serviceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.model.JobExpenses;
import com.checksammy.loca.repository.JobExpensesRepository;
import com.checksammy.loca.service.JobExpensesService;
import com.checksammy.loca.utility.GlobalValues;

@Service
public class JobExpensesServiceImpl implements JobExpensesService{
	private static final Logger logger = LoggerFactory.getLogger(JobExpensesServiceImpl.class);
	
	@Autowired
	private JobExpensesRepository jonExpensesRepository;

	@Override
	public List<JobExpenses> getAllListByJobId(Long jobId) {
		return jonExpensesRepository.findListByJobId(jobId);
	}

	@Override
	public JobExpenses saveJobExpenses(JobExpenses saveJobExpenses) {
		if(saveJobExpenses.getId() != null && saveJobExpenses.getId() > 0) {
			saveJobExpenses.setUpdatedTs(Instant.now());
		}
		return jonExpensesRepository.save(saveJobExpenses);
	}

	@Override
	public Optional<JobExpenses> getExpensesById(Long expenseId) {
		return jonExpensesRepository.findById(expenseId);
	}

	@Override
	public String saveRequestAttachments(Long expenseId, MultipartFile[] files) {
		List<String> attachments = new ArrayList<String>();
//		String existingAttchments = quNotesRepository.getRequestAttachment(internalNotesId);
		String status = "";
		try {
			String attachmentsName = "";
			File dir = new File(GlobalValues.JOB_EXPENSES_ATTACHMENT + File.separator + expenseId);
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
			jonExpensesRepository.updateAttachmentsById(expenseId, attachmentsName);
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
}
