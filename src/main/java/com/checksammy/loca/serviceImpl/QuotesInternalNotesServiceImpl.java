package com.checksammy.loca.serviceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.InterLinkInternalNotesDto;
import com.checksammy.loca.dto.QuotesInternalNotesDto;
import com.checksammy.loca.model.QuotesInternalNotes;
import com.checksammy.loca.model.User;
import com.checksammy.loca.repository.QuoteRepository;
import com.checksammy.loca.repository.QuotesInternalNotesRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.service.QuotesInternalNotesService;
import com.checksammy.loca.utility.GlobalValues;

@Service
public class QuotesInternalNotesServiceImpl implements QuotesInternalNotesService {

	private static final Logger logger = LoggerFactory.getLogger(QuotesInternalNotesServiceImpl.class);

	@Autowired
	private QuotesInternalNotesRepository quNotesRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public String saveRequestAttachments(Long internalNotesId, MultipartFile[] files) {
		List<String> attachments = new ArrayList<String>();
		String existingAttchments = quNotesRepository.getRequestAttachment(internalNotesId);
		String status = "";
		try {
			String attachmentsName = "";
			File dir = new File(GlobalValues.INTERNAL_NOTES_ATTACHMENT + File.separator + internalNotesId);
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
			quNotesRepository.updateAttachmentsById(internalNotesId, attachmentsName);
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
	public QuotesInternalNotes saveDataSeperatly(QuotesInternalNotesDto quoteInternalNoteDto) {
		QuotesInternalNotes quotesInternalNotes = quoteInternalNoteDto.mapWithQuoteInternalNotes(quoteInternalNoteDto);
		quotesInternalNotes = quNotesRepository.save(quotesInternalNotes);
//		quoteRepository.updatelinkNote(quoteInternalNoteDto.getQuoteId(), quoteInternalNoteDto.getLinkNote());
		return quotesInternalNotes;
	}

	@Override
	public List<QuotesInternalNotesDto> getRealtedNote(Long quoteId) {
		List<QuotesInternalNotesDto> quotesInternalNotesDtos = new ArrayList<QuotesInternalNotesDto>();
		List<QuotesInternalNotes> quotesInternalNotes = quNotesRepository.findByQuoteId(quoteId);
		for (QuotesInternalNotes quotesInternalNotes2 : quotesInternalNotes) {
			QuotesInternalNotesDto quotesInternalNotesDto = new QuotesInternalNotesDto();
			try {
				User realtedUser = userRepository.findByUserId(quotesInternalNotes2.getCreatedBy());
				quotesInternalNotesDto = quotesInternalNotesDto.addedRealtedUserwithData(realtedUser,
						quotesInternalNotes2);
				quotesInternalNotesDtos.add(quotesInternalNotesDto);
			} catch (Exception e) {
				logger.debug(e.getCause().getLocalizedMessage());
			}

		}
		return quotesInternalNotesDtos;
	}

	@Override
	public List<QuotesInternalNotes> findNotesBy(Long quoteId, String type) {
		String type2 = "%"+type+"%";
		return quNotesRepository.findByQuoteAndType(quoteId, type2);
	}

	@Override
	public void updateQuoteNote(InterLinkInternalNotesDto interLinkInternalNotesDto) {
		Instant updateDate = Instant.now();
		quNotesRepository.updateNoteData(interLinkInternalNotesDto.getId(), interLinkInternalNotesDto.getNotes(),interLinkInternalNotesDto.getAttachment(), interLinkInternalNotesDto.getUpdatedBy(), updateDate);		
	}
}
