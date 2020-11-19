package com.checksammy.loca.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.JobRequestCustomFieldDto;
import com.checksammy.loca.dto.JobRequestDto;
import com.checksammy.loca.model.BinType;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.model.JobRequest;
import com.checksammy.loca.model.JobRequestCustomField;
import com.checksammy.loca.model.User;
import com.checksammy.loca.repository.BinTypeRespository;
import com.checksammy.loca.repository.FieldTypeInstanceRepository;
import com.checksammy.loca.repository.JobRepository;
import com.checksammy.loca.repository.JobRequestCustomFieldRepository;
import com.checksammy.loca.repository.JobRequestRepository;
import com.checksammy.loca.repository.QuoteRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.service.JobRequestService;
import com.checksammy.loca.utility.GlobalValues;

@Service
public class JobRequestServiceImpl implements JobRequestService {

	private static final Logger logger = LoggerFactory.getLogger(JobRequestServiceImpl.class);

	final private String ATTACHMENTS = "attachment";

	final private String REQUEST = "request";

	final private String JOB = "job";

	final private String QUOTE = "quote";

	@Autowired
	private JobRequestRepository jobRequestRepository;
	
	@Autowired
	private QuoteRepository quoteRepository;
	
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRequestCustomFieldRepository reqCustomFieldRepo;

	@Autowired
	private FieldTypeInstanceRepository fieldTypeRepository;

	@Autowired
	BinTypeRespository binTypeRespository;

	@Override
	public List<JobRequest> getAllList() {
		return jobRequestRepository.findAll();
	}

	@Override
	public JobRequest saveJobRequest(JobRequest jobRequest) {
		jobRequest = jobRequestRepository.save(jobRequest);
		List<Long> reqCustomFieldIds = new ArrayList<Long>();
		if (jobRequest.getRequestCustomField() != null && jobRequest.getRequestCustomField().size() > 0) {
			for (JobRequestCustomField requestCustomField : jobRequest.getRequestCustomField()) {
				requestCustomField.setRequestId(jobRequest.getId());
				reqCustomFieldIds.add(requestCustomField.getId());
			}
			reqCustomFieldRepo.updateRequestCustomField(jobRequest.getId(), reqCustomFieldIds);
		}
		return jobRequest;
	}

	@Override
	public JobRequestDto getRequestJob(Long requestId) {
		JobRequestDto jobRequestDto = new JobRequestDto();
		Optional<JobRequest> jobRequest = jobRequestRepository.findById(requestId);
		if (jobRequest.isPresent()) {
			Optional<User> user = userRepository.findById(jobRequest.get().getUserId().getUserId());
			List<JobRequestCustomFieldDto> jobRequestCustomFieldDtos = new ArrayList<JobRequestCustomFieldDto>();
			for (JobRequestCustomField iterable_element : jobRequest.get().getRequestCustomField()) {
				JobRequestCustomFieldDto jobRequestCustomFieldDto = new JobRequestCustomFieldDto();
				Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
						.findById(iterable_element.getFieldInstanceId());
				jobRequestCustomFieldDto = jobRequestCustomFieldDto.setRequestCustomField(fieldTypeInstance.get(),
						iterable_element);
				jobRequestCustomFieldDtos.add(jobRequestCustomFieldDto);
			}

			jobRequestDto = jobRequestDto.getMappingField(jobRequest.get(), user.get(), jobRequestCustomFieldDtos);
			if (jobRequest.get().getServiceAsBintypeId() != null) {
				Optional<BinType> bin = binTypeRespository.findById(jobRequest.get().getServiceAsBintypeId());
				jobRequestDto.setServiceAsBintype(bin.isPresent() ? bin.get().getName() : null);
			}
		} else {
			jobRequestDto = null;
		}

		return jobRequestDto;
	}

	@Override
	public void deleteRequest(Long requestId) {
		jobRequestRepository.updateDeleteTag(requestId);
	}

	@Override
	public String saveRequestAttachments(Long requestId, MultipartFile[] files) {
		List<String> attachments = new ArrayList<String>();
		String existingAttchments = jobRequestRepository.getRequestAttachment(requestId);
		String status = "";
		try {
			String attachmentsName = "";
			File dir = new File(
					GlobalValues.EXTERNAL_FILE_PATH + File.separator + ATTACHMENTS + File.separator + requestId);
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
			if (existingAttchments != null && existingAttchments != "") {
				String[] elements = existingAttchments.split(",");
				for (String oldAtachment : elements) {
					attachments.add(oldAtachment);
				}
			}
			attachmentsName = String.join(",", attachments);
			System.out.println(attachmentsName);
			jobRequestRepository.updateAttachmentsById(requestId, attachmentsName);
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

	public File convert(MultipartFile file) throws IOException {

		FileOutputStream fos = null;
		// System.out.println(file.getContentType());
		File convFile = new File(file.getOriginalFilename());
		System.out.println("convFile : " + convFile);
		String mimetype = file.getContentType();
		String type = mimetype.split("/")[0];
		// if(file.getOriginalFilename().contains("pdf"))
		if (type.equals("image")) {
			InputStream is = new BufferedInputStream(file.getInputStream());
			BufferedImage image = ImageIO.read(is);
			convFile.createNewFile();
			System.out.println("convFile in if part: " + convFile);
			fos = new FileOutputStream(convFile);
			ImageIO.write(image, getFileExtension(convFile), fos);
			is.close();
			return convFile;
		} else {
			convFile.createNewFile();
			fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());

		}
		fos.close();
		return convFile;
	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	@Override
	public void updateUserIdOnRequest(Long requestId, Long userId) {
		jobRequestRepository.updateRequestRowByUserId(requestId, userId);
	}

	@Override
	public Boolean changeStatusByType(Long mainId, String status, String type) {
		Boolean completeStatus = true;
		switch (type.toLowerCase()) {
		case REQUEST:
			jobRequestRepository.updateStatus(mainId, status);
			break;
		case JOB:
			jobRepository.changeStatus(mainId, status);
			break;
		case QUOTE:
			quoteRepository.changeStatus(mainId, status);
			break;
		default:
			completeStatus = false;
			break;
		}
		return completeStatus;
	}

	@Override
	public void updateLocationId(Long id, Long locationId) {
		jobRequestRepository.updateLocationId(id, locationId);		
	}

}
