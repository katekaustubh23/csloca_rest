package com.checksammy.loca.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

import com.checksammy.loca.dto.BillingInvoiceDto;
import com.checksammy.loca.dto.InvoiceDetailDto;
import com.checksammy.loca.dto.InvoiceSchedulerDateDto;
import com.checksammy.loca.dto.JobDto;
import com.checksammy.loca.dto.JobForInvoiceRequestDto;
import com.checksammy.loca.dto.JobForSchedulerDto;
import com.checksammy.loca.dto.JobInternalNotesDto;
import com.checksammy.loca.dto.JobNotificationActionHistoryDto;
import com.checksammy.loca.dto.JobProductAndServiceUpdateDto;
import com.checksammy.loca.dto.JobSendDto;
import com.checksammy.loca.dto.SendReminderMailDto;
import com.checksammy.loca.dto.VisitSchedulerDateDto;
import com.checksammy.loca.model.BinType;
import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.InvoiceSchedularDate;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.JobExpenses;
import com.checksammy.loca.model.JobInternalNotes;
import com.checksammy.loca.model.JobNotificationActionHistory;
import com.checksammy.loca.model.JobProductService;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.VisitFrequencyJobMaster;
import com.checksammy.loca.model.VisitSchedulerDate;
import com.checksammy.loca.repository.BinTypeRespository;
import com.checksammy.loca.repository.InvoiceSchedulerDateRepository;
import com.checksammy.loca.service.InvoiceService;
import com.checksammy.loca.service.JobExpensesService;
import com.checksammy.loca.service.JobForSchedulerService;
import com.checksammy.loca.service.JobInternalNotesService;
import com.checksammy.loca.service.JobNotificationActionHistoryService;
import com.checksammy.loca.service.JobProductServiceService;
import com.checksammy.loca.service.JobService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.VisitFrequencyJobMasterService;
import com.checksammy.loca.service.VisitSchedulerDateService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/job")
public class JobController {

	private static final Logger logger = LoggerFactory.getLogger(JobController.class);

	@Autowired
	private JobService jobService;

	@Autowired
	private JobProductServiceService jobProductServiceService;

	@Autowired
	private JobInternalNotesService jobNotesService;

	@Autowired
	private VisitFrequencyJobMasterService visitFreqJobMasterService;

	@Autowired
	private JobExpensesService jobExpensesService;

	@Autowired
	private JobNotificationActionHistoryService jobNotificationActionHistoryService;

	@Autowired
	private VisitSchedulerDateService visitSchedulerDateService;

	@Autowired
	private SimpMessagingTemplate template;
	/* Get Job List for mobile */
	@Autowired
	private JobForSchedulerService jobForSchedulerService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private BinTypeRespository binTypeRespository;

	@Autowired
	private InvoiceSchedulerDateRepository invoiceSchedularDateRepo;
	
	@Autowired
	private UserService userService;

	/* VISIT FREQ JOB Master */
	@GetMapping("/visit/freq/master")
	public ResponseEntity<ServiceResponse> getAllVisitFrequecyMaster() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<VisitFrequencyJobMaster> visitFreqJonMaster = visitFreqJobMasterService.getAllList();
			response.setReturnObject(visitFreqJonMaster);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* GET JOB LIST */
	@GetMapping("/alllist")
	public ResponseEntity<ServiceResponse> getAllJobList() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobSendDto> jobList = jobService.getAllList();
			response.setReturnObject(jobList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/save/{timezone}")
	public ResponseEntity<ServiceResponse> saveJob(@RequestBody JobDto saveJob, @PathVariable Integer timezone) {
		ServiceResponse response = new ServiceResponse();
		try {
			Job job = jobService.saveJobAndSchedule(saveJob, timezone);
			response.setReturnObject(job);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Save Update Job fields
	 * 
	 * @param jobId
	 * @return
	 */
	@PostMapping("/edited/{timezone}")
	public ResponseEntity<ServiceResponse> saveUpdateFieldOfJob(@RequestBody JobDto saveJob,
			@PathVariable Integer timezone) {
		ServiceResponse response = new ServiceResponse();
		try {
			Job job = jobService.editJobAndSchedule(saveJob, timezone);
			response.setReturnObject(job);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

//	Get Job By Id
	@GetMapping("/{jobId}/{timezone}")
	public ResponseEntity<ServiceResponse> getJobDataById(@PathVariable("jobId") Long jobId,
			@PathVariable("timezone") Long timezone) {
		ServiceResponse response = new ServiceResponse();
		try {
			JobSendDto jobSendData = jobService.getJobById(jobId);
			if (jobSendData != null) {
				List<JobExpenses> jobExpenseList = jobExpensesService.getAllListByJobId(jobId);
				List<VisitSchedulerDate> jobVisitSchedulerDates = visitSchedulerDateService.findByJobId(jobId);
				List<VisitSchedulerDateDto> finalVisitList = new ArrayList<VisitSchedulerDateDto>();
				List<BillingInvoiceDto> billingInvoiceDtos = new ArrayList<BillingInvoiceDto>();
				List<InvoiceSchedulerDateDto> invoiceSchedulerDateDtos = new ArrayList<InvoiceSchedulerDateDto>();
				List<InvoiceSchedularDate> invoiceSchedulerDate = new ArrayList<InvoiceSchedularDate>();
				if (jobExpenseList.size() > 0) {
					jobSendData.setExpenses(jobExpenseList);
				}

				finalVisitList = visitSchedulerDateService.filterValidationOnVisitDates(jobVisitSchedulerDates,
						timezone);
				List<Invoice> invoiceRealtedToJob = invoiceService.findInvoiceBy(jobId);

//				 billingInvoiceDtos = invoiceService.filterInvoiceData(invoiceRealtedToJob,
//				 timezone);
				billingInvoiceDtos = invoiceService.findInvoiceSchedule(jobId, timezone);

				jobSendData.setVisitSchedulerDates(finalVisitList);
				jobSendData.setBillingInvoiceData(billingInvoiceDtos);
				response.setReturnObject(jobSendData);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				response.setReturnObject(jobSendData);
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* Job List by Driver id */
	@GetMapping("/list/by/{customerId}")
	public ResponseEntity<ServiceResponse> getJobListByDriverId(@PathVariable("customerId") Long customerId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobSendDto> jobList = jobService.getJobListByDriverId(customerId);

			response.setReturnObject(jobList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* Delete Job */
	@DeleteMapping(value = "/delete/{jobId}")
	public ResponseEntity<ServiceResponse> deleteJobData(@PathVariable("jobId") Long jobId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean status = jobService.deleteById(jobId);
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

	/* Attachment for Internal Notes By List */

//	 Save internal notes add
	@PostMapping(value = "/save/internalnote")
	public ResponseEntity<ServiceResponse> saveQuotesInternalNotesAttachment(
			@RequestBody JobInternalNotesDto jobInternalNoteDto) throws JsonProcessingException {
		ServiceResponse response = new ServiceResponse();		
		try {
			JobInternalNotes jobInternalFinalValue = jobNotesService.saveDataSeperatly(jobInternalNoteDto);
			response.setReturnObject(jobInternalFinalValue);
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

	@GetMapping(value = "/call/internalnote/{JobId}")
	public ResponseEntity<ServiceResponse> getInternalNotesByQuoteId(@PathVariable("JobId") Long JobId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobInternalNotesDto> quotesFinalValue = jobNotesService.getRealtedNote(JobId);
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

	/* ADD Attachment */
	@PostMapping(value = "/internal/attachments/{internalNotesId}")
	public ResponseEntity<ServiceResponse> uploadingRequestAttachment(@PathVariable Long internalNotesId,
			@RequestParam("files") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = jobNotesService.saveRequestAttachments(internalNotesId, files);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Job Product and Service
	 */

	@PostMapping("/product/service/save")
	public ResponseEntity<ServiceResponse> saveProductAndService(
			@RequestBody JobProductAndServiceUpdateDto saveJobProAndSer) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobProductService> jobProAndService = jobProductServiceService
					.saveAndUpdateJObProAndService(saveJobProAndSer);
			response.setReturnObject(jobProAndService);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* Delete Product and Service From Job */
	@DeleteMapping(value = "/delete/product/{productId}")
	public ResponseEntity<ServiceResponse> deleteJobProductAndService(@PathVariable("productId") Long productId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean status = jobProductServiceService.deleteById(productId);
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

	/**
	 * Job Expenses
	 */

	@GetMapping("/expenses/list/{jobId}")
	public ResponseEntity<ServiceResponse> getJobExpensesList(@PathVariable("jobId") Long jobId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobExpenses> jobExpenseList = jobExpensesService.getAllListByJobId(jobId);
			response.setReturnObject(jobExpenseList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/expense/save")
	public ResponseEntity<ServiceResponse> saveJobExpenses(@RequestBody JobExpenses saveJobExpenses) {
		ServiceResponse response = new ServiceResponse();
		try {
			JobExpenses jobExpenses = jobExpensesService.saveJobExpenses(saveJobExpenses);
			response.setReturnObject(jobExpenses);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/expenses/{expenseId}")
	public ResponseEntity<ServiceResponse> getJobExpensesById(@PathVariable("expenseId") Long expenseId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<JobExpenses> jobExpense = jobExpensesService.getExpensesById(expenseId);
			response.setReturnObject(jobExpense.get());
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

//	ATTACHMENT
	@PostMapping(value = "/expense/attachment/{expenseId}")
	public ResponseEntity<ServiceResponse> uploadExpensesAttachment(@PathVariable Long internalNotesId,
			@RequestParam("files") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = jobExpensesService.saveRequestAttachments(internalNotesId, files);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Driver Accept And Reject assign Job
	 * 
	 * @RequestBody JobNotificationActionHistory jobNotificationActionHistory
	 */
	@PostMapping("/notification/driver/response")
	public ResponseEntity<ServiceResponse> notificationDriverResponse(
			@RequestBody JobNotificationActionHistoryDto jobNotificationActionHistoryDto) {
		ServiceResponse response = new ServiceResponse();
		try {

			Optional<Job> jobOptional = jobService.findById(jobNotificationActionHistoryDto.getJobId());

			List<JobNotificationActionHistory> jobNotificationActionHistoryList = jobNotificationActionHistoryService
					.saveData(jobNotificationActionHistoryDto);

			template.convertAndSend("/topic/notification/" + jobOptional.get().getCreatedBy() + "",
					jobNotificationActionHistoryList);
			String status = "";
			if (jobNotificationActionHistoryDto.getAction()) {
				status = "Job has been accepted";
			} else {
				status = "Job has been rejected";
			}
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * NOTIFICATION
	 * 
	 * @param jobId
	 * @param driverId
	 * @return
	 */
//	Open Notification 
	@GetMapping("/notification/disable/{jobId}/{driverId}")
	public ResponseEntity<ServiceResponse> disableNotification(@PathVariable("jobId") Long jobId,
			@PathVariable("driverId") Long driverId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean status = jobNotificationActionHistoryService.disableNotification(jobId, driverId);
			if (status) {
				response.setReturnObject(status);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				response.setReturnObject(status);
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/getall/notification/{adminId}")
	public ResponseEntity<ServiceResponse> getAllNotification(@PathVariable("adminId") Long adminId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobNotificationActionHistory> jobNotificationActionHistoryList = jobNotificationActionHistoryService
					.getDataByAdminId(adminId);
			response.setReturnObject(jobNotificationActionHistoryList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

//	Dummy Api For push notification
	@PostMapping("/dummy/notify/{jobId}")
	public ResponseEntity<ServiceResponse> forDummyPushNotify(@PathVariable("jobId") Long jobId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Job job = jobService.saveJobAndScheduleDummy(jobId);
			response.setReturnObject(job);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

//	TODO: Create service to "complete" status "Mark As Completed"
	@PutMapping("/status/change/{jobId}/{changeStatus}")
	public ResponseEntity<ServiceResponse> saveUpdateJobStatusWithEmail(@PathVariable("jobId") Long jobId,
			@PathVariable("changeStatus") String changeStatus) {
		ServiceResponse response = new ServiceResponse();
		try {
			Job job = jobService.changeJobStatusAndSendMail(jobId, changeStatus);
			response.setReturnObject(job);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* TODO: Send Reminder mail" */
	@PostMapping("/send/reminder/{timeZone}/{sendType}")
	public ResponseEntity<ServiceResponse> sendReminder(@PathVariable("timeZone") Long timeZone,
			@PathVariable("sendType") String sendType, @RequestParam("userId") Long userId,
			@RequestParam("jobId") Long jobId, @RequestParam("toUserName") String toUserName,
			@RequestParam("toUser") List<String> toUser, @RequestParam("ccUsers") List<String> ccUsers,
			@RequestParam("subject") String subject, @RequestParam("content") String content,
			@RequestParam("sendCopyUser") Boolean sendCopyUser,
			@RequestParam("includeFeedback") Boolean includeFeedback, @RequestParam("files") MultipartFile[] files,
			HttpServletRequest request) {
		ServiceResponse response = new ServiceResponse();
		SendReminderMailDto sendReminderMailDto = new SendReminderMailDto();
		sendReminderMailDto = sendReminderMailDto.setAllData(userId, jobId, toUserName, toUser, ccUsers, subject,
				content, sendCopyUser, includeFeedback);
		try {
			Boolean status = false;
			if (sendType.equalsIgnoreCase("reminder")) {
				status = jobService.resendClientReminder(sendReminderMailDto, timeZone, files);
			} else if (sendType.equalsIgnoreCase("followup")) {
				status = jobService.sendFollowUpMail(sendReminderMailDto, timeZone, files);
			}

			if (status) {
				response.setReturnObject(status);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				response.setReturnObject(status);
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * update driver user reminder type
	 * 
	 * @param jobId
	 * @param assignUserReminderType
	 * @return
	 */
	@PutMapping("/customer/reminder/{jobId}/{assignUserReminderType}")
	public ResponseEntity<ServiceResponse> updateReminderStatus(@PathVariable Long jobId,
			@PathVariable String assignUserReminderType) {
		ServiceResponse response = new ServiceResponse();
		String status = "";
		try {
			jobService.updateDriverReminderField(jobId, assignUserReminderType);
			status = "Updated";
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			status = "Updated";
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());

			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Update job scheduler mark as completed update "check" field
	 * 
	 * @param jobId
	 * @param assignUserReminderType
	 * @return
	 */
	@PutMapping("/scheduler/aschecked/{visitSchedulerId}/{checked}/{userId}")
	public ResponseEntity<ServiceResponse> updateSchedulerChecked(@PathVariable Long visitSchedulerId,
			@PathVariable Boolean checked, @PathVariable Long userId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Instant checkDate = Instant.now();
			if (checked) {
				jobService.updateSchedulerVisitMark(visitSchedulerId, checked, checkDate, userId);
			} else {
				jobService.updateSchedulerVisitMarkUncheck(visitSchedulerId, checked, userId);
			}

			Job job = jobService.findJobByVisitId(visitSchedulerId);

			response.setReturnObject(job);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());

			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Rating Insert
	 * 
	 * @param visitSchedulerId
	 * @return
	 */
	@GetMapping("/client/rating/{jobId}/{dateString}/{rating}")
	public ResponseEntity<ServiceResponse> updateRating(@PathVariable Long jobId, @PathVariable String dateString,
			@PathVariable Long rating) {
		ServiceResponse response = new ServiceResponse();
		String status = "";
		try {
			jobService.changeJobRating(jobId, dateString, rating);
			status = "Updated";
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			status = "error";
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());

			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * ---------------------------- For Mobile View -----------------------------
	 * Job List by Driver id
	 */
	@GetMapping("/status/list/on/{driverId}")
	public ResponseEntity<ServiceResponse> getJobListByDriverIdOnDevice(@PathVariable("driverId") Long driverId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobSendDto> jobList = jobService.getJobListByDriverIdOnDevice(driverId);

			response.setReturnObject(jobList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * GetJob HistoryList
	 * 
	 * @param driverId
	 * @return
	 */
	@GetMapping("/history/list/{driverId}")
	public ResponseEntity<ServiceResponse> getJobHistoryList(@PathVariable("driverId") Long driverId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobForSchedulerDto> jobList = jobForSchedulerService.getJobHistoryList(driverId);

			response.setReturnObject(jobList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/topay/invoice/{userId}/{timezone}")
	public ResponseEntity<ServiceResponse> getJobListForInvoiceByUserId(@PathVariable("userId") Long userId,
			@PathVariable("timezone") Long timezone) {
		ServiceResponse response = new ServiceResponse();
		try {
			InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
			
			List<JobSendDto> jobSendDatas = new ArrayList<JobSendDto>();
			List<Job> jobList = jobService.getJobListByUserAssign(userId);
			User user =userService.getUserId(jobList.size() > 0? jobList.get(0).getUserId():0);
			invoiceDetailDto.setUser(user);
			for (Job job : jobList) {

				JobSendDto jobSendData = new JobSendDto();
				
				Optional<BinType> binType = binTypeRespository
						.findById(job.getServiceAsBintypeId() != null ? job.getServiceAsBintypeId() : 0L);
				jobSendData = jobSendData.getJobDataOnDevice(job, binType, null);

//				List<JobExpenses> jobExpenseList = jobExpensesService.getAllListByJobId(job.getId());
				List<VisitSchedulerDateDto> finalVisitList = new ArrayList<VisitSchedulerDateDto>();
				List<BillingInvoiceDto> billingInvoiceDtos = new ArrayList<BillingInvoiceDto>();
//				if (jobExpenseList.size() > 0) {
//					jobSendData.setExpenses(jobExpenseList);
//				}

				Calendar currentDate = new GregorianCalendar();
				currentDate.setTime(new Date());
				currentDate.set(Calendar.DATE, currentDate.getActualMaximum(Calendar.DATE));
//				System.out.println(currentDate.getTime());
				List<VisitSchedulerDate> jobVisitSchedulerDates = new ArrayList<VisitSchedulerDate>();

				jobVisitSchedulerDates = visitSchedulerDateService.findVisitByUpComing(job.getId(),
						currentDate.getTime());

				List<VisitSchedulerDate> jobVisitSchedulerDates2 = new ArrayList<VisitSchedulerDate>();
				;
				if (jobVisitSchedulerDates.size() > 0) {
					int month = 0;
					Boolean flag = false;
					do {
						currentDate.add(Calendar.MONTH, month);
						jobVisitSchedulerDates2 = jobVisitSchedulerDates.stream()
								.filter(x -> (x.getCurrentDate() != null && Date.from(x.getCurrentDate()).before(currentDate.getTime())))
								.collect(Collectors.toList());
//						System.out.println(" Final end Date " + Date.from(job.getSchEndDate()));
//						System.out.println("Current " + currentDate.getTime());
						if (Date.from(job.getSchEndDate() != null? job.getSchEndDate(): Instant.now()).before(currentDate.getTime())) {
							flag = true;
						}
						month++;
					} while (jobVisitSchedulerDates2.size() <= 0 || !flag);
				}

//				System.out.println(jobVisitSchedulerDates.size());

				finalVisitList = visitSchedulerDateService.forUpComingVisit(jobVisitSchedulerDates2, timezone);
//				List<Invoice> invoiceRealtedToJob = invoiceService.findInvoiceBy(job.getId());
//
//				billingInvoiceDtos = invoiceService.filterInvoiceData(invoiceRealtedToJob, timezone);

				if (jobVisitSchedulerDates2.size() > 0) {
					long count = jobVisitSchedulerDates2.stream().filter(c -> c.getChecked()).count();
					jobSendData.setCompleteVisitCount(count);
				}

				jobSendData.setVisitSchedulerDates(finalVisitList);
//				jobSendData.setBillingInvoiceData(billingInvoiceDtos);
//				response.setReturnObject(jobSendData);
//				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
				jobSendDatas.add(jobSendData);
			}
			invoiceDetailDto.setSendData(jobSendDatas);
			response.setReturnObject(invoiceDetailDto);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (Exception e) {
//			e.printStackTrace();
			e.getStackTrace()[0].getLineNumber();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

//	TODO: Billing Data:
	@GetMapping("/billingdata/reminder/{jobId}/{timeZone}")
	public ResponseEntity<ServiceResponse> findAllBillingDataReminder(@PathVariable Long jobId,
			@PathVariable Long timeZone) {
		ServiceResponse response = new ServiceResponse();
		try {
			
			BillingAndReminderDto billingAndReminderDto = new BillingAndReminderDto();
			List<BillingInvoiceDto> filterInvoiceList = new ArrayList<BillingInvoiceDto>();
			List<BillingInvoiceDto> reminderInvocieList = new ArrayList<BillingInvoiceDto>();
			List<Invoice> invoiceRealtedToJob = invoiceService.findInvoiceBy(jobId);

			filterInvoiceList = invoiceService.filterInvoiceData(invoiceRealtedToJob, timeZone);

			reminderInvocieList = invoiceService.findInvoiceSchedule(jobId, timeZone);

			Date currentDate = new Date();

			List<InvoiceSchedularDate> reminderData = invoiceSchedularDateRepo.findInvoiceScheduleAndUpComingDate(jobId,
					currentDate);

			billingAndReminderDto
					.setNextReminder(reminderData.size() > 0 ? reminderData.get(0).getInvStartDate() : null);
			billingAndReminderDto.setBillingInoviceList(filterInvoiceList);
			billingAndReminderDto.setReminderList(reminderInvocieList);

			response.setReturnObject(billingAndReminderDto);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());

			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/data/for/invoice")
	public ResponseEntity<ServiceResponse> getJobForInvoice(
			@RequestBody JobForInvoiceRequestDto jobForInvoiceRequestDto) {
		ServiceResponse response = new ServiceResponse();
		try {
			JobSendDto jobSendDto = new JobSendDto();
			Optional<Job> job = jobService.findById(jobForInvoiceRequestDto.getJobId());
			User user = userService.getUserId(job.get().getUserId());
			List<VisitSchedulerDate> visitSchedulerDates = new ArrayList<VisitSchedulerDate>();
			try {
				visitSchedulerDates = visitSchedulerDateService
						.findByVisitIds(jobForInvoiceRequestDto.getJobId(), (jobForInvoiceRequestDto.getVisitIds().size() > 0 )?jobForInvoiceRequestDto.getVisitIds():new ArrayList<Long>());
			} catch (Exception e) {
				visitSchedulerDates = new ArrayList<VisitSchedulerDate>();
			}

			List<JobProductService> jobProductServicesList = new ArrayList<JobProductService>();
			
			
			for (VisitSchedulerDate visitSchedulerDate : visitSchedulerDates) {
				List<JobProductService> jobProductServices = jobProductServiceService
						.findDataByVistiIds(visitSchedulerDate.getId());
				if(jobProductServices.size() > 0) {
					jobProductServicesList.addAll(jobProductServices);	
				}
			}
			
//			if(jobProductServicesList.size() <= 0 ) {
//				
//				for (Long visitId : jobForInvoiceRequestDto.getVisitIds()) {
//					List<JobProductService> jobProductServices = new ArrayList<JobProductService>();
//					jobProductServices = jobProductServiceService.findByVisitandJob(visitId);
//					jobProductServicesList.addAll(jobProductServices);
//				}
//			}
			
//			For Completed 
//			List<JobProductService> jobProductServices3 = new ArrayList<JobProductService>();
			
			
//			jobProductServices3 = jobProductServiceService.findByCompleteJObId(jobForInvoiceRequestDto.getJobId());
			
//			jobProductServicesList.addAll(jobProductServices3);
//			Set<JobProductService> jobProductServicesSet = new HashSet<JobProductService>(jobProductServicesList);
			jobSendDto = jobSendDto.mapDataJObAndService(job.get(), jobProductServicesList, user);
			response.setReturnObject(jobSendDto);
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
