package com.checksammy.loca.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.QuoteDto;
import com.checksammy.loca.dto.QuoteSendDto;
import com.checksammy.loca.dto.QuotesInternalNotesDto;
import com.checksammy.loca.model.Quote;
import com.checksammy.loca.model.QuotesInternalNotes;
import com.checksammy.loca.service.QuoteService;
import com.checksammy.loca.service.QuotesInternalNotesService;
import com.checksammy.loca.service.QuotesProductAndServiceService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/quote")
public class QuotesController {

	private static final Logger logger = LoggerFactory.getLogger(QuotesController.class);

	@Autowired
	private QuoteService quoteService;
	
	@Autowired
	private QuotesInternalNotesService quInternalNotesService;

	@Autowired
	private QuotesProductAndServiceService quoProductAndServiceService;

	@GetMapping(value = "/all")
	public ResponseEntity<ServiceResponse> getAll() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<QuoteSendDto> quotes = quoteService.getAll();
			response.setReturnObject(quotes);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<ServiceResponse> saveQuotes(@RequestBody QuoteDto quote) {
		ServiceResponse response = new ServiceResponse();
		try {
			Quote quotes = quoteService.saveData(quote);
			response.setReturnObject(quotes);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setReturnObject(e.getCause().getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/{QuotesId}")
	public ResponseEntity<ServiceResponse> getQuotesData(@PathVariable("QuotesId") Long QuotesId) {
		ServiceResponse response = new ServiceResponse();
		try {
			QuoteSendDto quotes = quoteService.getById(QuotesId);
			response.setReturnObject(quotes);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(value = "/delete/{QuotesId}")
	public ResponseEntity<ServiceResponse> deleteQuotesData(@PathVariable("QuotesId") Long QuotesId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean status = quoteService.deleteById(QuotesId);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/* Change Quotes Status */
	@GetMapping(value = "/{QuotesId}/{statusId}")
	public ResponseEntity<ServiceResponse> changeQuotesStatus(@PathVariable("QuotesId") Long QuotesId,
			@PathVariable("statusId") Long statusId) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = quoteService.changeStatus(QuotesId, statusId);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/* Upload Internal Notes attachment */

//	@PostMapping(value = "/internal/notes")
//	public ResponseEntity<ServiceResponse> saveInternalNotes(@RequestBody QuotesInternalNotes quotesInternalNotes) {
//		ServiceResponse response = new ServiceResponse();
//		try {
//			quotesInternalNotes = quInternalNotesService.saveData(quotesInternalNotes);
//			response.setReturnObject(quotesInternalNotes);
//			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e.fillInStackTrace());
//			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
//			response.setErrorMessage(e.getMessage());
//		}
//		return ResponseEntity.ok().body(response);
//	}

	/* Attachment for Internal Notes By List */
	/* ADD Attachment */
	@PostMapping(value = "/internal/attachments/{internalNotesId}")
	public ResponseEntity<ServiceResponse> uploadingRequestAttachment(@PathVariable Long internalNotesId,
			@RequestParam("files") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = quInternalNotesService.saveRequestAttachments(internalNotesId, files);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/*Quotes Product*/
	@PostMapping(value = "/product/attachments/{quoteProductId}/{quotesId}")
	public ResponseEntity<ServiceResponse> uploadQuoteProductServiceAttachment(@PathVariable Long quoteProductId,
			@PathVariable Long quotesId, @RequestParam("files") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = quoProductAndServiceService.saveRequestAttachments(quotesId, quoteProductId, files);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
//	 Save internal notes add
	@PostMapping(value = "/save/internalnote")
	public ResponseEntity<ServiceResponse> saveQuotesInternalNotesAttachment(@RequestBody QuotesInternalNotesDto quoteInternalNoteDto) {
		ServiceResponse response = new ServiceResponse();
		try {
			QuotesInternalNotes quotesFinalValue = quInternalNotesService.saveDataSeperatly(quoteInternalNoteDto);
			response.setReturnObject(quotesFinalValue);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setReturnObject(e.getCause().getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	
	
	@GetMapping(value = "/call/internalnote/{quoteId}")
	public ResponseEntity<ServiceResponse> getInternalNotesByQuoteId(@PathVariable("quoteId") Long quoteId ) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<QuotesInternalNotesDto> quotesFinalValue = quInternalNotesService.getRealtedNote(quoteId);
			response.setReturnObject(quotesFinalValue);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setReturnObject(e.getCause().getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
//	DELETE Quote Product service
	
	@DeleteMapping(value = "/quotes/product/{quoProductId}")
	public ResponseEntity<ServiceResponse> deleteQuoteProduct(@PathVariable("quoProductId") Long quoProductId) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = quoProductAndServiceService.deleteById(quoProductId);
			if(status.equalsIgnoreCase("true")) {
				response.setReturnObject(status);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);	
			}else {
				response.setReturnObject(status);
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
}
