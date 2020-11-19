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

import com.checksammy.loca.dto.InvoiceInternalNotesDto;
import com.checksammy.loca.model.InvoiceInternalNotes;
import com.checksammy.loca.model.User;
import com.checksammy.loca.repository.InvoiceInternalNotesRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.service.InvoiceInternalNotesService;
import com.checksammy.loca.utility.GlobalValues;

@Service
public class InvoiceInternalNotesServiceImpl implements InvoiceInternalNotesService{
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceInternalNotesServiceImpl.class);
	
	@Autowired
	private InvoiceInternalNotesRepository internalNotesRepository;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public InvoiceInternalNotes saveDataSeperatly(InvoiceInternalNotesDto invoiceInternalNotesDto) {
		InvoiceInternalNotes invoiceInternalNotes = invoiceInternalNotesDto.mapWithInvoiceInternalNotes(invoiceInternalNotesDto);
		invoiceInternalNotes = internalNotesRepository.save(invoiceInternalNotes);
		return invoiceInternalNotes;
	}
//
	@Override
	public String saveRequestAttachments(Long internalNotesId, MultipartFile[] files) {
		List<String> attachments = new ArrayList<String>();
		String existingAttchments = internalNotesRepository.getRequestAttachment(internalNotesId);
		String status = "";
		try {
			String attachmentsName = "";
			File dir = new File(GlobalValues.INVOICE_INTERNAL_NOTES_ATTACHMENT + File.separator + internalNotesId);
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
			internalNotesRepository.updateAttachmentsById(internalNotesId, attachmentsName);
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
	public List<InvoiceInternalNotesDto> getRealtedNote(Long invoiceId) {
		List<InvoiceInternalNotesDto> invoiceInternalNotesDtos = new ArrayList<InvoiceInternalNotesDto>();
		List<InvoiceInternalNotes> invoiceInternalNotes = internalNotesRepository.findByInvoiceId(invoiceId);
		for (InvoiceInternalNotes invocieInternalNotes2 : invoiceInternalNotes) {
			InvoiceInternalNotesDto invoiceInternalNotesDto = new InvoiceInternalNotesDto();
			try {
				User realtedUser = userRepo.findByUserId(invocieInternalNotes2.getCreatedBy());
				invoiceInternalNotesDto = invoiceInternalNotesDto.addedRealtedUserwithData(realtedUser,
						invocieInternalNotes2);
				invoiceInternalNotesDtos.add(invoiceInternalNotesDto);
			} catch (Exception e) {
				logger.debug(e.getCause().getLocalizedMessage());
			}
		}
		return invoiceInternalNotesDtos;
	}

}
