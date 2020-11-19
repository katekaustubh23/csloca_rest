package com.checksammy.loca.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.InvoiceDto;
import com.checksammy.loca.dto.InvoiceInternalNotesDto;
import com.checksammy.loca.dto.InvoiceSchedularDateDto;
import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.InvoiceInternalNotes;
import com.checksammy.loca.model.InvoiceSchedularDate;
import com.checksammy.loca.model.User;
import com.checksammy.loca.service.InvoiceInternalNotesService;
import com.checksammy.loca.service.InvoiceProductAndServiceService;
import com.checksammy.loca.service.InvoiceSchedularDateService;
import com.checksammy.loca.service.InvoiceService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.VisitSchedulerDateService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/invoice")
public class InvoiceController {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private UserService userService;

	@Autowired
	private InvoiceProductAndServiceService invProdAndServiceService;

	@Autowired
	private InvoiceInternalNotesService invInternalNotesService;

	@Autowired
	private InvoiceSchedularDateService invoiceSchedularDateService;
	
	@Autowired
	private VisitSchedulerDateService visitDateService;

	@GetMapping(value = "/all")
	public ResponseEntity<ServiceResponse> getAll() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<InvoiceDto> invoiceList = invoiceService.getAll();
			response.setReturnObject(invoiceList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/* Save invoice */
	@PostMapping("/save")
	public ResponseEntity<ServiceResponse> saveJob(@RequestBody InvoiceDto invoiceSave) {
		ServiceResponse response = new ServiceResponse();
		try {
			InvoiceDto invoice = invoiceService.saveInoviceData(invoiceSave);
			if(invoiceSave.getVisitId() != null && invoiceSave.getVisitId().size() > 0) {
				visitDateService.updatePaidOn(invoiceSave.getVisitId(), invoice.getJobId());
			}
			response.setReturnObject(invoice);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/{invoiceId}")
	public ResponseEntity<ServiceResponse> getQuotesData(@PathVariable("invoiceId") Long invoiceId) {
		ServiceResponse response = new ServiceResponse();
		try {
			InvoiceDto invoiceDto = invoiceService.getById(invoiceId);
			response.setReturnObject(invoiceDto);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/job/{jobId}")
	public ResponseEntity<ServiceResponse> getByJObId(@PathVariable("jobId") Long jobId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<Invoice> invoiceDto = invoiceService.findInvoiceBy(jobId);
			response.setReturnObject(invoiceDto);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/* invoice Product & service Attachment */
	/* Quotes Product */
	@PostMapping(value = "/product/attachments/{invoiceProductId}/{invoiceId}")
	public ResponseEntity<ServiceResponse> uploadQuoteProductServiceAttachment(@PathVariable Long invoiceProductId,
			@PathVariable Long invoiceId, @RequestParam("files") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = invProdAndServiceService.saveRequestAttachments(invoiceId, invoiceProductId, files);
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
	public ResponseEntity<ServiceResponse> saveQuotesInternalNotesAttachment(
			@RequestBody InvoiceInternalNotesDto invoiceInternalNotesDto) {
		ServiceResponse response = new ServiceResponse();
		try {
			InvoiceInternalNotes InvoiceFinalValue = invInternalNotesService.saveDataSeperatly(invoiceInternalNotesDto);
			response.setReturnObject(InvoiceFinalValue);
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

	@GetMapping(value = "/call/internalnote/{invoiceId}")
	public ResponseEntity<ServiceResponse> getInternalNotesByQuoteId(@PathVariable("invoiceId") Long invoiceId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<InvoiceInternalNotesDto> invoiceInternalNotesDto = invInternalNotesService.getRealtedNote(invoiceId);
			response.setReturnObject(invoiceInternalNotesDto);
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

	/* Attachment for Internal Notes By List */
	/* ADD Attachment */
	@PostMapping(value = "/internal/attachments/{internalNotesId}")
	public ResponseEntity<ServiceResponse> uploadingRequestAttachment(@PathVariable Long internalNotesId,
			@RequestParam("files") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = invInternalNotesService.saveRequestAttachments(internalNotesId, files);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	// TODO: Delete Product and Service
	@DeleteMapping(value = "/delete/product/{productId}")
	public ResponseEntity<ServiceResponse> deleteQuoteProduct(@PathVariable("productId") Long productId) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = invProdAndServiceService.deleteById(productId);
			if (status.equalsIgnoreCase("true")) {
				response.setReturnObject(status);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
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

//	 Update Job status
	@PutMapping("/change/status/{invoiceId}/{status}/{statusId}")
	public ResponseEntity<ServiceResponse> updateInvoiceStatus(@PathVariable Long invoiceId,
			@PathVariable String status, @PathVariable Long statusId) {
		ServiceResponse response = new ServiceResponse();
		String statusResponse = "";
		try {
			invoiceService.updatePaymentStatusOnly(invoiceId, status, statusId);
			statusResponse = "Updated";
			response.setReturnObject(statusResponse);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			statusResponse = "error";
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());

			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

//	TODO: Save Invoice Remainder and data
	@PostMapping(value = "/save/reminder")
	public ResponseEntity<ServiceResponse> saveReminderInvoiceData(
			@RequestBody InvoiceSchedularDate invoiceSchedularDate) {
		ServiceResponse response = new ServiceResponse();
		try {
			invoiceSchedularDate = invoiceSchedularDateService.saveData(invoiceSchedularDate);
			response.setReturnObject(invoiceSchedularDate);
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

	@GetMapping(value = "/find/reminder/{remindId}")
	public ResponseEntity<ServiceResponse> findRemindData(@PathVariable("remindId") Long remindId) {
		ServiceResponse response = new ServiceResponse();
		try {
			InvoiceSchedularDateDto invoiceSchedularDateDto = new InvoiceSchedularDateDto();
			Optional<InvoiceSchedularDate> invoiceSchedularDate = invoiceSchedularDateService.findById(remindId);

			List<Integer> teamIdList = new ArrayList<Integer>();
			List<Integer> driverIdList = new ArrayList<Integer>();
			List<User> driverList = new ArrayList<User>();
			List<User> teamList = new ArrayList<User>();
			if (invoiceSchedularDate.get().getTeamMember() != null
					&& invoiceSchedularDate.get().getTeamMember() != "") {

				String[] teams = invoiceSchedularDate.get().getTeamMember().split(",");
				List<String> list = Arrays.asList(teams);
				teamIdList = list.stream().map(Integer::parseInt).collect(Collectors.toList());
				teamList = userService.findByUserIds(teamIdList);
			}
			if (invoiceSchedularDate.get().getDriverMember() != null
					&& invoiceSchedularDate.get().getDriverMember() != "") {
				String[] drivers = invoiceSchedularDate.get().getDriverMember().split(",");
				List<String> list = Arrays.asList(drivers);
				driverIdList = list.stream().map(Integer::parseInt).collect(Collectors.toList());
				driverList = userService.findByUserIds(driverIdList);
			}

			invoiceSchedularDateDto = invoiceSchedularDateDto.mapData(invoiceSchedularDate.get(), driverList, teamList);
			response.setReturnObject(invoiceSchedularDateDto);
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
	
	@DeleteMapping(value = "/delete/reminder/{reminderId}")
	public ResponseEntity<ServiceResponse> deleteReminder(@PathVariable Long reminderId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean status = invoiceSchedularDateService.deleteReminder(reminderId);
			response.setReturnObject(status);
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


}
