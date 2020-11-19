package com.checksammy.loca.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.dto.InterLinkInternalNotesDto;
import com.checksammy.loca.dto.InternalNoteFindDto;
import com.checksammy.loca.dto.InvoiceDto;
import com.checksammy.loca.dto.JobInternalNotesDto;
import com.checksammy.loca.dto.QuotesInternalNotesDto;
import com.checksammy.loca.model.JobInternalNotes;
import com.checksammy.loca.model.JobRequestNotes;
import com.checksammy.loca.model.QuotesInternalNotes;
import com.checksammy.loca.model.User;
import com.checksammy.loca.service.JobInternalNotesService;
import com.checksammy.loca.service.JobRequestNotesService;
import com.checksammy.loca.service.QuotesInternalNotesService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/internal/note")
public class InternalNoteController {

	public static final String REQUEST = "request";
	public static final String QUOTE = "quote";
	public static final String JOB = "job";
	public static final String INVOICE = "invoice";

	private static final Logger logger = LoggerFactory.getLogger(InternalNoteController.class);

	@Autowired
	private JobRequestNotesService requestNotesService;

	@Autowired
	private QuotesInternalNotesService quotesInternalNotesService;

	@Autowired
	private JobInternalNotesService jobInternalNotesService;

	@Autowired
	private UserService userService;

	/* Internal Notes */
	@PostMapping("/list")
	public ResponseEntity<ServiceResponse> findAllInternalNoteList(
			@RequestBody InternalNoteFindDto internalNoteFindDto) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobRequestNotes> requestNotes = new ArrayList<JobRequestNotes>();
			List<QuotesInternalNotes> quoteInternalNotes = new ArrayList<QuotesInternalNotes>();
			List<JobInternalNotes> jobInternalNotes = new ArrayList<JobInternalNotes>();

			List<InterLinkInternalNotesDto> interLinkInternalNotesDtos = new ArrayList<InterLinkInternalNotesDto>();

			if (internalNoteFindDto.getRequestId() != null && internalNoteFindDto.getRequestId() > 0) {
				requestNotes = requestNotesService.findInternalNoteByRequestId(internalNoteFindDto.getRequestId(),
						internalNoteFindDto.getType());
				for (JobRequestNotes requestNote : requestNotes) {
					User user = userService.findById(requestNote.getCreatedBy());
					InterLinkInternalNotesDto interLinkInternalNotesDto = new InterLinkInternalNotesDto();
					interLinkInternalNotesDto = InterLinkInternalNotesDto.mappRequestData(requestNote);
					interLinkInternalNotesDto.setNoteFrom(REQUEST);
					interLinkInternalNotesDto.setRelatedUser(user);
					interLinkInternalNotesDto.setPropertyId(requestNote.getJobRequestId());
					interLinkInternalNotesDtos.add(interLinkInternalNotesDto);
				}
			}

			if (internalNoteFindDto.getQuoteId() != null && internalNoteFindDto.getQuoteId() > 0) {
				quoteInternalNotes = quotesInternalNotesService.findNotesBy(internalNoteFindDto.getQuoteId(),
						internalNoteFindDto.getType());
				for (QuotesInternalNotes quoteInternalNote : quoteInternalNotes) {
					InterLinkInternalNotesDto interLinkInternalNotesDto = new InterLinkInternalNotesDto();
					interLinkInternalNotesDto = InterLinkInternalNotesDto.mappQuoteData(quoteInternalNote);

					User user = userService.getUserId(quoteInternalNote.getCreatedBy());
					interLinkInternalNotesDto.setNoteFrom(QUOTE);
					interLinkInternalNotesDto.setPropertyId(quoteInternalNote.getQuoteId());
					interLinkInternalNotesDto.setRelatedUser(user != null ? user : null);
					interLinkInternalNotesDtos.add(interLinkInternalNotesDto);
				}
			}

			if (internalNoteFindDto.getJobId() != null && internalNoteFindDto.getJobId() > 0) {
				jobInternalNotes = jobInternalNotesService.findNotesBy(internalNoteFindDto.getJobId(),
						internalNoteFindDto.getType());
				for (JobInternalNotes jobInternalNote : jobInternalNotes) {
					InterLinkInternalNotesDto interLinkInternalNotesDto = new InterLinkInternalNotesDto();
					interLinkInternalNotesDto = InterLinkInternalNotesDto.mappJobData(jobInternalNote);
					interLinkInternalNotesDto.setNoteFrom(JOB);
					interLinkInternalNotesDto.setPropertyId(jobInternalNote.getJobId());
					User user = userService.getUserId(jobInternalNote.getCreatedBy());
					interLinkInternalNotesDto.setRelatedUser(user != null ? user : null);
					interLinkInternalNotesDtos.add(interLinkInternalNotesDto);
				}
			}

			response.setReturnObject(interLinkInternalNotesDtos);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* Save invoice */
	@PostMapping("/save")
	public ResponseEntity<ServiceResponse> updateInternalNotes(
			@RequestBody List<InterLinkInternalNotesDto> interLinkInternalNotesDtos) {
		ServiceResponse response = new ServiceResponse();
		try {
			for (InterLinkInternalNotesDto interLinkInternalNotesDto : interLinkInternalNotesDtos) {

				switch (interLinkInternalNotesDto.getNoteFrom()) {
				case REQUEST:
					requestNotesService.updateRequestNote(interLinkInternalNotesDto);
					break;
				case QUOTE:
					quotesInternalNotesService.updateQuoteNote(interLinkInternalNotesDto);
					break;
				case JOB:
					jobInternalNotesService.updateJobNote(interLinkInternalNotesDto);
					break;

				default:
					break;
				}

			}
			response.setReturnObject(interLinkInternalNotesDtos);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
	
	/* Save invoice */
	@PostMapping("/update")
	public ResponseEntity<ServiceResponse> UpdateNoteByOne(
			@RequestBody InterLinkInternalNotesDto interLinkInternalNotesDto) {
		ServiceResponse response = new ServiceResponse();
		try {
			switch (interLinkInternalNotesDto.getNoteFrom()) {
			case REQUEST:
				requestNotesService.updateRequestNote(interLinkInternalNotesDto);
				break;
			case QUOTE:
				quotesInternalNotesService.updateQuoteNote(interLinkInternalNotesDto);
				break;
			case JOB:
				jobInternalNotesService.updateJobNote(interLinkInternalNotesDto);
				break;

			default:
				break;
			}
			response.setReturnObject(interLinkInternalNotesDto);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

}
