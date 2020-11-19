package com.checksammy.loca.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.EmailDto;
import com.checksammy.loca.dto.EmailList;
import com.checksammy.loca.dto.JobCustomInstanceDto;
import com.checksammy.loca.dto.JobDto;
import com.checksammy.loca.dto.JobInternalNotesDto;
import com.checksammy.loca.dto.JobSendDto;
import com.checksammy.loca.dto.JobVisitReportDto;
import com.checksammy.loca.dto.JobVisitReportFilterDto;
import com.checksammy.loca.dto.SendReminderMailDto;
import com.checksammy.loca.dto.VisitScheduleUpdateDto;
import com.checksammy.loca.dto.VisitSchedulerDateDto;
import com.checksammy.loca.firebase.FCMService;
import com.checksammy.loca.firebase2.AndroidPushNotificationsService;
import com.checksammy.loca.model.BinType;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.model.InvoiceSchedularDate;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.JobCustomInstance;
import com.checksammy.loca.model.JobDriverMapping;
import com.checksammy.loca.model.JobInternalNotes;
import com.checksammy.loca.model.JobProductService;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.PropertyDetails;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.VisitFrequencyJobMaster;
import com.checksammy.loca.model.VisitJobProductAndServiceMap;
import com.checksammy.loca.model.VisitSchedulerDate;
import com.checksammy.loca.repository.BinTypeRespository;
import com.checksammy.loca.repository.FieldTypeInstanceRepository;
import com.checksammy.loca.repository.InvoiceRepository;
import com.checksammy.loca.repository.InvoiceSchedulerDateRepository;
import com.checksammy.loca.repository.JobCustomInstanceRepository;
import com.checksammy.loca.repository.JobDriverMappingRepository;
import com.checksammy.loca.repository.JobInternalNotesRepository;
import com.checksammy.loca.repository.JobProductServiceRepository;
import com.checksammy.loca.repository.JobRepository;
import com.checksammy.loca.repository.LocationRepository;
import com.checksammy.loca.repository.PropertyDetailRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.repository.VisitFrequencyJobMasterRepository;
import com.checksammy.loca.repository.VisitJobProductAndServiceMapRepository;
import com.checksammy.loca.repository.VisitSchedulerDateRepository;
import com.checksammy.loca.service.EmailService;
import com.checksammy.loca.service.JobService;
import com.checksammy.loca.service.VisitJobProductAndServiceMapService;
import com.checksammy.loca.service.VisitSchedulerDateService;
import com.checksammy.loca.serviceImpl.dateschedular.ScheduleDateUtil;
import com.checksammy.loca.utility.GlobalValues;

@Service
public class JobServiceImpl implements JobService {

	private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

	public static final String DRIVER = "driver";

	public static final String TEAM = "team";

	/* Job Driver mapping Status */
	public static final String ACCEPT = "accept";

	public static final String PENDING = "pending";

//	Status on job

	private static final String LATE = "Late";

	private static final String UPCOMING = "Upcoming";

	private static final String NEXT = "New job offer";

	/* Server IP and PORT */
	private static final String IP_PORT = "http://3.96.13.253:8085/LocA/loca/api/job"; // http://3.96.13.253:8085/LocA

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private JobCustomInstanceRepository jobCustRepository;

	@Autowired
	private JobProductServiceRepository jobProdRepository;

	@Autowired
	private JobInternalNotesRepository jobNotesRepository;

	@Autowired
	private VisitSchedulerDateRepository visitSchedulerDateRepo;

	@Autowired
	private VisitJobProductAndServiceMapService visitAndServiceMapService;

	@Autowired
	private InvoiceSchedulerDateRepository invoiceSchedulerDateRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private LocationRepository locationRepo;

	@Autowired
	FieldTypeInstanceRepository fieldTypeRepository;

	@Autowired
	ScheduleDateUtil scheduleDateService;

	@Autowired
	EmailService emailService;

	@Autowired
	BinTypeRespository binTypeRespository;

	@Autowired
	private FCMService fcmService;

	@Autowired
	private VisitFrequencyJobMasterRepository vistJobMasterRepository;

	@Autowired
	private JobDriverMappingRepository jobDriMappingRepo;

	@Autowired
	private PropertyDetailRepository propertyDetailRepository;

	@Autowired
	private InvoiceRepository invoiceRepo;

	@Autowired
	private VisitSchedulerDateService visitSchedulerDateService;

//	FIREBASE SERVICE 
//	@Autowired
//	private PushNotificationService pushNotificationService;

	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;

	@Override
	public List<JobSendDto> getAllList() {
		List<JobSendDto> jobSendDtos = new ArrayList<JobSendDto>();
		List<Job> jobList = jobRepository.findAll();
		for (Job job : jobList) {
			try {
				JobSendDto jobSendDto = new JobSendDto();
				User user = userRepo.findByUserId(job.getUserId());

				List<JobInternalNotesDto> jobInternalNotesDtos = new ArrayList<JobInternalNotesDto>();
				List<JobInternalNotes> jobInternalNotes = new ArrayList<JobInternalNotes>();
				List<JobCustomInstanceDto> jobCustomInstanceDtos = new ArrayList<JobCustomInstanceDto>();

				if (job.getJobInternalNotes() != null && job.getJobInternalNotes().size() > 0) {
					jobInternalNotes = new ArrayList<JobInternalNotes>(job.getJobInternalNotes());
					for (JobInternalNotes jobInternalNote : jobInternalNotes) {
						JobInternalNotesDto jobInternalNotesDto = new JobInternalNotesDto();
						try {
							User realtedUser = userRepo.findByUserId(jobInternalNote.getCreatedBy());
							jobInternalNotesDto = jobInternalNotesDto.addedRealtedUserwithData(realtedUser,
									jobInternalNote);
							jobInternalNotesDtos.add(jobInternalNotesDto);
						} catch (Exception e) {
							logger.debug(e.getCause().getLocalizedMessage());
						}
					}
				}
				if (job.getJobCustomInstance() != null && job.getJobCustomInstance().size() > 0) {
					for (JobCustomInstance jobCustomInstance : job.getJobCustomInstance()) {
						JobCustomInstanceDto jobCustomInstanceDto = new JobCustomInstanceDto();
						Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
								.findById(jobCustomInstance.getFieldInstanceId());
						jobCustomInstanceDto = jobCustomInstanceDto.addCustomField(fieldTypeInstance.get(),
								jobCustomInstance);
						jobCustomInstanceDtos.add(jobCustomInstanceDto);
					}
				}
//				long[] numbers = null;
//				if (job.getAssignMemberIds() != null && job.getAssignMemberIds() != "") {
//					numbers = Arrays.asList(job.getAssignMemberIds().split(",")).stream().map(String::trim)
//							.mapToLong(Long::parseLong).toArray();
//				}
				List<User> assignUsers = new ArrayList<User>();
				List<User> asignTeam = new ArrayList<User>();
//				if (numbers != null && numbers.length > 0) {
//					assignUsers = userRepo.findUserByAssignIds(numbers);
//				}
				jobSendDto = jobSendDto.getJobData(job, jobInternalNotesDtos, user, jobCustomInstanceDtos, assignUsers,
						asignTeam);

				jobSendDtos.add(jobSendDto);
			} catch (Exception e) {
				logger.debug(e.getLocalizedMessage());
			}
		}
		return jobSendDtos;

	}

	/* Save Job Data */
	@Override
	public Job saveJobAndSchedule(JobDto saveJob, Integer timezone) throws InterruptedException, ExecutionException {
		Job newJob = saveJob.setJobData(saveJob);
		newJob = saveJobFunctionality(newJob, saveJob, timezone);
		return newJob;
	}

	/* Save Job Functionality */
	private Job saveJobFunctionality(Job newJob, JobDto saveJob, Integer timezone) {
		long[] driverIdsList = null;
		Optional<Job> jobVerify = null;
		if (newJob.getId() != null && newJob.getId() > 0) {
			jobVerify = jobRepository.findById(newJob.getId());
		}

		newJob = jobRepository.save(newJob);

		if (jobVerify != null && jobVerify.isPresent()) {
			if (!jobVerify.get().getSchStartDate().equals(saveJob.getSchStartDate())
					|| jobVerify.get().getSchVisitFrequencyId() != saveJob.getSchVisitFrequencyId()
					|| (jobVerify.get().getSchDuration() != null
							&& !jobVerify.get().getSchDuration().equalsIgnoreCase(saveJob.getSchDuration()))
					|| !jobVerify.get().getSchDurationType().equalsIgnoreCase(saveJob.getSchDurationType())) {
				/* Create visit Schedule date List */
				visitSchedulerDateRepo.deleteByJobId(newJob.getId());
			}
		} else {
			visitSchedulerDateRepo.deleteByJobId(newJob.getId());
		}

		if (newJob.getSchLater()) {
			visitSchedulerDateRepo.deleteByJobId(newJob.getId());
		}

		jobDriMappingRepo.deleteByJobId(newJob.getId());/* Delete driver mapping list */

		if (!newJob.getSchLater()) {
			List<VisitFrequencyJobMaster> vistJobMasters = vistJobMasterRepository.findAll();

			if (jobVerify != null && jobVerify.isPresent()) {
				if (!jobVerify.get().getSchStartDate().equals(saveJob.getSchStartDate())
						|| jobVerify.get().getSchVisitFrequencyId() != saveJob.getSchVisitFrequencyId()
						|| (jobVerify.get().getSchDuration() != null
								&& !jobVerify.get().getSchDuration().equalsIgnoreCase(saveJob.getSchDuration()))
						|| (jobVerify.get().getSchDuration() != null && !jobVerify.get().getSchDurationType()
								.equalsIgnoreCase(saveJob.getSchDurationType()))) {
					/* Create visit Schedule date List */
					List<VisitSchedulerDate> vistSchedulerDates = scheduleDateService.createScheduleTime(newJob,
							vistJobMasters, timezone);
//					System.out.println("final List ->" + vistSchedulerDates);

					if (vistSchedulerDates.size() > 0) {
						vistSchedulerDates = visitSchedulerDateRepo.saveAll(vistSchedulerDates);
						visitAndServiceMapService.deleteByJobIdWithOutChecked(newJob.getId());
						List<VisitJobProductAndServiceMap> visitJobProductAndServiceMaps = new ArrayList<VisitJobProductAndServiceMap>();
						for (VisitSchedulerDate visitSchedulerDate : vistSchedulerDates) {
							for (JobProductService jobProductService : newJob.getJobProductService()) {
								VisitJobProductAndServiceMap visitJobProductAndServiceMap = new VisitJobProductAndServiceMap();
								visitJobProductAndServiceMap.setJobId(newJob.getId());
								visitJobProductAndServiceMap.setJobProductId(jobProductService.getId());
								visitJobProductAndServiceMap.setQty(jobProductService.getQuantity().doubleValue());
								visitJobProductAndServiceMap.setUnitCost(jobProductService.getUnitCost());
								visitJobProductAndServiceMap.setVisitId(visitSchedulerDate.getId());
								visitJobProductAndServiceMap.setFinalCost(jobProductService.getTotalCost());
								visitJobProductAndServiceMap.setChecked(false);
								visitJobProductAndServiceMaps.add(visitJobProductAndServiceMap);
							}
						}
						if (visitJobProductAndServiceMaps.size() > 0) {
							visitAndServiceMapService.saveMapping(visitJobProductAndServiceMaps);
						}
					}
				}
			} else {
				List<VisitSchedulerDate> vistSchedulerDates = scheduleDateService.createScheduleTime(newJob,
						vistJobMasters, timezone);
//				System.out.println("final List ->" + vistSchedulerDates);

				if (vistSchedulerDates.size() > 0) {
					vistSchedulerDates = visitSchedulerDateRepo.saveAll(vistSchedulerDates);

					visitAndServiceMapService.deleteByJobIdWithOutChecked(newJob.getId());
					List<VisitJobProductAndServiceMap> visitJobProductAndServiceMaps = new ArrayList<VisitJobProductAndServiceMap>();
					for (VisitSchedulerDate visitSchedulerDate : vistSchedulerDates) {
						for (JobProductService jobProductService : newJob.getJobProductService()) {
							VisitJobProductAndServiceMap visitJobProductAndServiceMap = new VisitJobProductAndServiceMap();
							visitJobProductAndServiceMap.setJobId(newJob.getId());
							visitJobProductAndServiceMap.setJobProductId(jobProductService.getId());
							visitJobProductAndServiceMap.setQty(jobProductService.getQuantity().doubleValue());
							visitJobProductAndServiceMap.setUnitCost(jobProductService.getUnitCost());
							visitJobProductAndServiceMap.setVisitId(visitSchedulerDate.getId());
							visitJobProductAndServiceMap.setFinalCost(jobProductService.getTotalCost());
							visitJobProductAndServiceMap.setChecked(false);
							visitJobProductAndServiceMaps.add(visitJobProductAndServiceMap);
						}
					}
					if (visitJobProductAndServiceMaps.size() > 0) {
						visitAndServiceMapService.saveMapping(visitJobProductAndServiceMaps);
					}
				}
			}

		}

		if (!newJob.getSchLater()) {
			if (jobVerify != null && jobVerify.isPresent()) {
				invoiceSchedulerDateRepo.deleteByJobId(newJob.getId());
			}
			List<InvoiceSchedularDate> invoiceSchedulerDates = scheduleDateService.createInvoiceScheduleTime(newJob,
					timezone);
			System.out.println("final List ->" + invoiceSchedulerDates);

			if (invoiceSchedulerDates.size() > 0) {
				invoiceSchedulerDateRepo.saveAll(invoiceSchedulerDates);
			}
		}

		/*--------------------------------*/
		jobRepository.updateLocationId(newJob.getId(), saveJob.getLocationId());

		List<JobCustomInstance> jobCustomInstances = new ArrayList<JobCustomInstance>();
		List<JobProductService> jobProductAndServices = new ArrayList<JobProductService>();
		List<JobInternalNotes> jobInternalNotes = new ArrayList<JobInternalNotes>();

		if (saveJob.getJobCustomInstance() != null && saveJob.getJobCustomInstance().size() > 0) {
			for (JobCustomInstance jobCustomInstance : saveJob.getJobCustomInstance()) {
				jobCustomInstance.setJobId(newJob.getId());
				jobCustomInstances.add(jobCustomInstance);
			}
			jobCustomInstances = jobCustRepository.saveAll(jobCustomInstances);
		}

		if (saveJob.getJobProductService() != null && saveJob.getJobProductService().size() > 0) {
			for (JobProductService jobProductService : saveJob.getJobProductService()) {
				jobProductService.setJobId(newJob.getId());
				jobProductAndServices.add(jobProductService);
			}
			jobProductAndServices = jobProdRepository.saveAll(jobProductAndServices);
		}

		if (saveJob.getJobInternalNotes() != null && saveJob.getJobInternalNotes().size() > 0) {
			for (JobInternalNotes jobInternalNotes2 : saveJob.getJobInternalNotes()) {
				jobInternalNotes2.setJobId(newJob.getId());
				jobInternalNotes.add(jobInternalNotes2);
			}
			jobInternalNotes = jobNotesRepository.saveAll(jobInternalNotes);
		}

		if (saveJob.getAssignMemberIds() != null && !saveJob.getAssignMemberIds().equalsIgnoreCase("")) {
			driverIdsList = Arrays.asList(saveJob.getAssignMemberIds().split(",")).stream().map(String::trim)
					.mapToLong(Long::parseLong).toArray();
			if (newJob.getDriverEmail()) {
				sendEmailMethod(driverIdsList, DRIVER, saveJob, newJob);
			}
		}
		if (newJob.getTeamEmail()) {
			long[] teamIds = null;
			if (saveJob.getTeamAssignMemberIds() != null && !saveJob.getTeamAssignMemberIds().equalsIgnoreCase("")) {
				teamIds = Arrays.asList(saveJob.getTeamAssignMemberIds().split(",")).stream().map(String::trim)
						.mapToLong(Long::parseLong).toArray();

				sendEmailMethodTeam(teamIds, TEAM, saveJob, newJob, driverIdsList, timezone);
			}
		}
		long[] driverIds2 = null;

		if (saveJob.getAssignMemberIds() != null && !saveJob.getAssignMemberIds().equalsIgnoreCase("")) {
			driverIds2 = Arrays.asList(saveJob.getAssignMemberIds().split(",")).stream().map(String::trim)
					.mapToLong(Long::parseLong).toArray();

			/* Save driver job mapping in other table */
			List<JobDriverMapping> jobDriverMappings = new ArrayList<JobDriverMapping>();
			for (long driverIdMap : driverIds2) {
				JobDriverMapping jobDriverMapping = new JobDriverMapping();
				jobDriverMapping.setCreatedBy(Instant.now());
				jobDriverMapping.setJobId(newJob.getId());
				jobDriverMapping.setDriverId(driverIdMap);
				jobDriverMapping.setIsMailSend(newJob.getDriverEmail());
				jobDriverMapping.setStatus(PENDING);
				jobDriverMappings.add(jobDriverMapping);
			}
			jobDriMappingRepo.saveAll(jobDriverMappings);

			List<User> emailUser = userRepo.findUserByAssignIds(driverIds2);
//				try {
//					sendPushNotificationOnMobile(newJob, emailUser);
//				} catch (Exception e) {
//					logger.error(e.getLocalizedMessage());
//				}

			newSendPushNotificationOnMobile(newJob, emailUser);

		}
		newJob.setJobInternalNotes(new HashSet<JobInternalNotes>(jobInternalNotes));
		return newJob;
	}

	private void sendEmailMethodTeam(long[] teamIds, String team2, JobDto saveJob, Job newJob, long[] driverIdsList,
			Integer timezone) {
		try {

			List<User> emailUser = userRepo.findUserByAssignIds(teamIds);
			EmailDto emailDto = new EmailDto();
			for (User user : emailUser) {
				List<EmailList> emailLists = new ArrayList<EmailList>();
				EmailList emailList = new EmailList();
				emailList.setName(user.getUsername());
				emailLists.add(emailList);

//			if (team2.equalsIgnoreCase(TEAM)) {
//				Instant timeStamp = Instant.now();
				try {
					List<User> driverList = userRepo.findUserByAssignIds(driverIdsList);
					List<String> driverNameList = new ArrayList<String>();
					for (User driver : driverList) {
						driverNameList.add(driver.getFirstName() + " " + driver.getLastName());
					}
					String completeDriverName = String.join(",", driverNameList);
					Optional<Location> locatiOptional = locationRepo.findById(saveJob.getLocationId());

					Calendar forTime = new GregorianCalendar();
					try {
						forTime.setTime(Date.from(saveJob.getSchStartTime()));
						forTime.add(Calendar.MINUTE, -(timezone));
					} catch (Exception e) {
					}

					String formattedDate = "";
					try {
						String strDateFormat = "hh:mm a";
						DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
						formattedDate = dateFormat.format(forTime.getTime());
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (saveJob.getSchLater()) {

					}
					Calendar calendar = new GregorianCalendar();
					try {
						calendar.setTime(Date.from(saveJob.getSchStartDate()));
						calendar.add(Calendar.MINUTE, -(timezone));
					} catch (Exception e) {
						// TODO: handle exception
					}

					String emailContent = "<html>\r\n" + "<div><h3>Hi " + user.getFirstName() + " " + user.getLastName()
							+ ", </h3>\r\n" + "</div>\r\n" + "<div>\r\n" + "<h3>A new job - #" + saveJob.getJobNumber()
							+ " - has been assigned to you. Driver, " + completeDriverName
							+ ", has been assigned for this job.</h2>\r\n" + "<p>Date for the job - "
							+ (saveJob.getSchLater() ? "unschedule"
									: new SimpleDateFormat("MM/dd/YYYY").format(calendar.getTime()))
							+ "</p>\r\n" + "<p>Time of the job - " + formattedDate + "</p>\r\n" + "<p>Location - "
							+ locatiOptional.get().getPropertyName() + "</p>\r\n" + "<p>Thanks</p>\r\n" + "</div>\r\n"
							+ "</html>";
//					
					emailDto.setEditorHtml(emailContent);
					emailDto.setSubject("Job Assigned - CS LocA");
					emailDto.setSendToEmails(emailLists);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage());
				}
//			}
				emailService.sendEmailToMutipleRecipientAssignUser(emailDto);
			}

		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}

	}

//	New Send Notification on multiple data
	private void newSendPushNotificationOnMobile(Job newJob, List<User> emailUser) {

		for (User user : emailUser) {
			if (user.getFCMToken() != null && !user.getFCMToken().equalsIgnoreCase("")) {

				JSONObject body = new JSONObject();
				body.put("to", user.getFCMToken());

				JSONObject notification = new JSONObject();
				notification.put("title", "New Assign Job");
				notification.put("body", "Confirm Please");
				notification.put("priority", "high");

				JSONObject data = new JSONObject();
				data.put("jobId", (newJob.getId() != null) ? newJob.getId().toString() : "0");
				data.put("jobNumber", newJob.getJobNumber());
				data.put("userId", user.getUserId().toString());
				data.put("startDate", newJob.getSchStartDate().toString());
				if (newJob.getSchDuration() != null) {
					data.put("duration", newJob.getSchDuration().toString());
					data.put("durationType", newJob.getSchDurationType().toString());
				} else {
					data.put("duration", "NULL");
					data.put("durationType", newJob.getSchDurationType().toString());
				}
				body.put("notification", notification);
				body.put("data", data);

				HttpEntity<String> request = new HttpEntity<>(body.toString());

				CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
				CompletableFuture.allOf(pushNotification).join();
				try {
					String firebaseResponse = pushNotification.get();
					System.out.println(firebaseResponse);
//			      return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}

	}

//	Send Email To respective user email
	private void sendEmailMethod(long[] userIds, String userType, JobDto saveJob, Job newJob) {
		try {

			List<User> emailUser = userRepo.findUserByAssignIds(userIds);
			EmailDto emailDto = new EmailDto();

			for (User user : emailUser) {
				List<EmailList> emailLists = new ArrayList<EmailList>();
				EmailList emailList = new EmailList();
				emailList.setName(user.getUsername());
				emailLists.add(emailList);

				String title = "";
				if (newJob.getId() != null && newJob.getId() > 0) {
					title = "New job assign";
				} else {
					title = "Job updated";
				}

//				if (userType.equalsIgnoreCase(DRIVER)) {
//					Instant timeStamp = Instant.now();
				try {
					String emailContent = "<html>\r\n" + "<div><h3>Hi " + user.getFirstName() + " " + user.getLastName()
							+ ", </h3>\r\n" + "</div>\r\n" + "<div>\r\n" + "<h3>A new job - #" + newJob.getJobNumber()
							+ " - has been assigned to you. Please go to the CS LocA app to accept or reject the job. </h3>\r\n<p><b>Kind Regards</b><br><b>CheckSammy Operations</b></p>\r\n"
							+ "</div>\r\n" + "</html>";
					emailDto.setEditorHtml(emailContent);
					emailDto.setSubject("Job Assigned - CS LocA");
					emailDto.setSendToEmails(emailLists);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage());
				}
//				}
				emailService.sendEmailToMutipleRecipientAssignUser(emailDto);
			}

		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}
	}

//	private void sendPushNotificationOnMobile(Job newJob, List<User> emailUser)
//			throws InterruptedException, ExecutionException {
//		PushNotificationRequest request = new PushNotificationRequest();
//		for (User user : emailUser) {
//			if (user.getFCMToken() != null && !user.getFCMToken().equalsIgnoreCase("")) {
//				request.setToken(user.getFCMToken());
//				request.setTitle("Assign Job");
//				request.setMessage("New job has Assign");
//				request.setTopic("new_assign_job");
//				try {
//					fcmService.sendMessage(getSamplePayloadData(newJob, user), request);
//				} catch (Exception e) {
//					logger.debug(e.getCause().getLocalizedMessage());
//				}
//
//			}
//		}
//
//	}

	/* GET Job Data */
	@Override
	public JobSendDto getJobById(Long jobId) {
		JobSendDto jobSendDto = new JobSendDto();
		Optional<Job> job = jobRepository.findById(jobId);
		try {
			if (job.isPresent()) {
				User user = userRepo.findByUserId(job.get().getUserId());

				List<JobInternalNotesDto> jobInternalNotesDtos = new ArrayList<JobInternalNotesDto>();
				List<JobInternalNotes> jobInternalNotes = new ArrayList<JobInternalNotes>();
				List<JobCustomInstanceDto> jobCustomInstanceDtos = new ArrayList<JobCustomInstanceDto>();

				if (job.get().getJobInternalNotes() != null && job.get().getJobInternalNotes().size() > 0) {
					jobInternalNotes = new ArrayList<JobInternalNotes>(job.get().getJobInternalNotes());
					for (JobInternalNotes jobInternalNote : jobInternalNotes) {
						JobInternalNotesDto jobInternalNotesDto = new JobInternalNotesDto();
						try {
							User realtedUser = userRepo.findByUserId(jobInternalNote.getCreatedBy());
							jobInternalNotesDto = jobInternalNotesDto.addedRealtedUserwithData(realtedUser,
									jobInternalNote);
							jobInternalNotesDtos.add(jobInternalNotesDto);
						} catch (Exception e) {
							logger.debug(e.getCause().getLocalizedMessage());
						}
					}
				}
				if (job.get().getJobCustomInstance() != null && job.get().getJobCustomInstance().size() > 0) {
					for (JobCustomInstance jobCustomInstance : job.get().getJobCustomInstance()) {
						JobCustomInstanceDto jobCustomInstanceDto = new JobCustomInstanceDto();
						Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
								.findById(jobCustomInstance.getFieldInstanceId());
						jobCustomInstanceDto = jobCustomInstanceDto.addCustomField(fieldTypeInstance.get(),
								jobCustomInstance);
						jobCustomInstanceDtos.add(jobCustomInstanceDto);
					}
				}
				long[] driverCount = null;
				if (job.get().getAssignMemberIds() != null && !job.get().getAssignMemberIds().equalsIgnoreCase("")) {
					driverCount = Arrays.asList(job.get().getAssignMemberIds().split(",")).stream().map(String::trim)
							.mapToLong(Long::parseLong).toArray();
				}

				long[] teamCount = null;
				if (job.get().getTeamAssignMemberIds() != null
						&& !job.get().getTeamAssignMemberIds().equalsIgnoreCase("")) {
					teamCount = Arrays.asList(job.get().getTeamAssignMemberIds().split(",")).stream().map(String::trim)
							.mapToLong(Long::parseLong).toArray();
				}
				List<User> assignUsers = new ArrayList<User>();
				List<User> asignTeam = new ArrayList<User>();
				if (driverCount != null && driverCount.length > 0) {
					assignUsers = userRepo.findUserByAssignIds(driverCount);
				}

				if (teamCount != null && teamCount.length > 0) {
					asignTeam = userRepo.findUserByAssignIds(teamCount);
				}

//				List<VisitSchedulerDate> vistList = visitSchedulerDateRepo.findByJobId(job.get().getId());

				jobSendDto = jobSendDto.getJobData(job.get(), jobInternalNotesDtos, user, jobCustomInstanceDtos,
						assignUsers, asignTeam);
				if (job.get().getServiceAsBintypeId() != null) {
					Optional<BinType> bin = binTypeRespository.findById(job.get().getServiceAsBintypeId());
					jobSendDto.setServiceAsBintype(bin.isPresent() ? bin.get().getName() : null);
				}

//				jobSendDto.setVisitSchedulerDates(vistList);
			} else {
				jobSendDto = null;
			}
		} catch (Exception e) {
			jobSendDto = null;
			logger.debug(e.getLocalizedMessage());
		}
		return jobSendDto;
	}

	@Override
	public Boolean deleteById(Long jobId) {
		Boolean status = false;
		try {
			jobRepository.deleteToUpdate(jobId);
			invoiceRepo.deleteByJobId(jobId);
			status = true;
		} catch (Exception e) {
			logger.debug(e.getCause().getMessage());
			status = false;
		}
		return status;
	}

//	Extra Field Data
//	private Map<String, String> getSamplePayloadData(Job newJob, User user) {
//		Map<String, String> pushData = new HashMap<>();
//		pushData.put("jobId", (newJob.getId() != null) ? newJob.getId().toString() : "0");
//		pushData.put("jobNumber", newJob.getJobNumber());
//		pushData.put("userId", user.getUserId().toString());
//		pushData.put("startDate", newJob.getSchStartDate().toString());
//		pushData.put("duration", newJob.getSchDuration().toString());
//		pushData.put("durationType", newJob.getSchDurationType().toString());
//		return pushData;
//	}

	@Override
	public Optional<Job> findById(Long jobId) {
		return jobRepository.findById(jobId);
	}

	/* Dummy URL */
	@Override
	public Job saveJobAndScheduleDummy(Long jobId) {
		Optional<Job> job = jobRepository.findById(jobId);
		if (job.isPresent()) {
			long[] driverIds2 = null;
			if (job.get().getAssignMemberIds() != null && !job.get().getAssignMemberIds().equalsIgnoreCase("")) {
				driverIds2 = Arrays.asList(job.get().getAssignMemberIds().split(",")).stream().map(String::trim)
						.mapToLong(Long::parseLong).toArray();

				List<User> emailUser = userRepo.findUserByAssignIds(driverIds2);
//					try {
//						sendPushNotificationOnMobile(newJob, emailUser);
//					} catch (Exception e) {
//						logger.error(e.getLocalizedMessage());
//					}

				newSendPushNotificationOnMobile(job.get(), emailUser);

			}
		}
		return null;
	}

	@Override
	public List<JobSendDto> getJobListByDriverId(Long customerId) {
		List<JobSendDto> jobSendDtos = new ArrayList<JobSendDto>();
		List<Job> getJobList = jobRepository.findByUserId(customerId);
		for (Job job : getJobList) {
			try {
				JobSendDto jobSendDto = new JobSendDto();
				User user = userRepo.findByUserId(job.getUserId());

				List<JobInternalNotesDto> jobInternalNotesDtos = new ArrayList<JobInternalNotesDto>();
				List<JobInternalNotes> jobInternalNotes = new ArrayList<JobInternalNotes>();
				List<JobCustomInstanceDto> jobCustomInstanceDtos = new ArrayList<JobCustomInstanceDto>();

				if (job.getJobInternalNotes() != null && job.getJobInternalNotes().size() > 0) {
					jobInternalNotes = new ArrayList<JobInternalNotes>(job.getJobInternalNotes());
					for (JobInternalNotes jobInternalNote : jobInternalNotes) {
						JobInternalNotesDto jobInternalNotesDto = new JobInternalNotesDto();
						try {
							User realtedUser = userRepo.findByUserId(jobInternalNote.getCreatedBy());
							jobInternalNotesDto = jobInternalNotesDto.addedRealtedUserwithData(realtedUser,
									jobInternalNote);
							jobInternalNotesDtos.add(jobInternalNotesDto);
						} catch (Exception e) {
							logger.debug(e.getCause().getLocalizedMessage());
						}
					}
				}
				if (job.getJobCustomInstance() != null && job.getJobCustomInstance().size() > 0) {
					for (JobCustomInstance jobCustomInstance : job.getJobCustomInstance()) {
						JobCustomInstanceDto jobCustomInstanceDto = new JobCustomInstanceDto();
						Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
								.findById(jobCustomInstance.getFieldInstanceId());
						jobCustomInstanceDto = jobCustomInstanceDto.addCustomField(fieldTypeInstance.get(),
								jobCustomInstance);
						jobCustomInstanceDtos.add(jobCustomInstanceDto);
					}
				}
//				long[] numbers = null;
//				if (job.getAssignMemberIds() != null && job.getAssignMemberIds() != "") {
//					numbers = Arrays.asList(job.getAssignMemberIds().split(",")).stream().map(String::trim)
//							.mapToLong(Long::parseLong).toArray();
//				}
				List<User> assignUsers = new ArrayList<User>();
				List<User> asignTeam = new ArrayList<User>();
//				if (numbers != null && numbers.length > 0) {
//					assignUsers = userRepo.findUserByAssignIds(numbers);
//				}
				jobSendDto = jobSendDto.getJobData(job, jobInternalNotesDtos, user, jobCustomInstanceDtos, assignUsers,
						asignTeam);

				jobSendDtos.add(jobSendDto);
			} catch (Exception e) {
				logger.debug(e.getLocalizedMessage());
			}
		}
		return jobSendDtos;
	}

	@Override
	public List<JobSendDto> getJobListByDriverIdOnDevice(Long driverId) {
		List<JobSendDto> jobSendDtos = new ArrayList<JobSendDto>();
		List<JobDriverMapping> jobDriverMappings = jobDriMappingRepo.findByDriverId(driverId);
		List<Job> getJobList = jobRepository.findByAssignDriverId(driverId);
		try {
			for (Job job : getJobList) {

				List<JobInternalNotesDto> jobInternalNotesDtos = new ArrayList<JobInternalNotesDto>();
				List<JobInternalNotes> jobInternalNotes = new ArrayList<JobInternalNotes>();

				if (job.getJobInternalNotes() != null && job.getJobInternalNotes().size() > 0) {
					jobInternalNotes = new ArrayList<JobInternalNotes>(job.getJobInternalNotes());
					for (JobInternalNotes jobInternalNote : jobInternalNotes) {
						JobInternalNotesDto jobInternalNotesDto = new JobInternalNotesDto();
						try {
							User realtedUser = userRepo.findByUserId(jobInternalNote.getCreatedBy());
							jobInternalNotesDto = jobInternalNotesDto.addedRealtedUserwithData(realtedUser,
									jobInternalNote);
							jobInternalNotesDtos.add(jobInternalNotesDto);
						} catch (Exception e) {
							logger.debug(e.getCause().getLocalizedMessage());
						}
					}
				}
				JobSendDto jobSendDto = new JobSendDto();

				Optional<BinType> binType = binTypeRespository
						.findById(job.getServiceAsBintypeId() != null ? job.getServiceAsBintypeId() : 0L);

				jobSendDto = jobSendDto.getJobDataOnDevice(job, binType, jobInternalNotesDtos);

				Calendar currentDate = Calendar.getInstance();
				Calendar upComingNext = Calendar.getInstance();
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);

				upComingNext.add(Calendar.DATE, 2);
				upComingNext.set(Calendar.HOUR_OF_DAY, 0);
				upComingNext.set(Calendar.MINUTE, 0);

				Optional<JobDriverMapping> jobDriverMapping = jobDriverMappings.stream()
						.filter(filterData -> filterData.getJobId().equals(job.getId())).findAny();

//				if(jobSendDto.getId() == 191) {
//					System.out.println("hello");
//					System.out.println(Date.from(jobSendDto.getSchStartDate()));
//					System.out.println(currentDate.getTime());
//				}
				if (!jobSendDto.getSchStartDate().equals(null) && jobDriverMapping.get().getStatus() != null) {
					if (Date.from(jobSendDto.getSchStartDate()).before(currentDate.getTime())
							&& jobDriverMapping.get().getStatus().equalsIgnoreCase(ACCEPT)) {
						jobSendDto.setStatePosition(LATE);
					} else if (Date.from(jobSendDto.getSchStartDate()).after(currentDate.getTime())
							&& jobDriverMapping.get().getStatus().equalsIgnoreCase(PENDING)) {
						jobSendDto.setStatePosition(NEXT);
//						jobSendDtos.add(jobSendDto);
					} else if (Date.from(jobSendDto.getSchStartDate()).after(currentDate.getTime())
							&& jobDriverMapping.get().getStatus().equalsIgnoreCase(ACCEPT)) {
						jobSendDto.setStatePosition(UPCOMING);
//						jobSendDtos.add(jobSendDto);
					} else {
						continue;
					}
					List<VisitSchedulerDate> jobVisitSchedulerDates = visitSchedulerDateService
							.findByJobId(jobSendDto.getId());
					if (jobVisitSchedulerDates.size() > 0) {
						List<VisitSchedulerDateDto> finalVisitList = new ArrayList<VisitSchedulerDateDto>();
						finalVisitList = visitSchedulerDateService.filterValidationOnVisitDates(jobVisitSchedulerDates,
								-330L);
						jobSendDto.setVisitSchedulerDates(finalVisitList);
					}

					jobSendDtos.add(jobSendDto);
				}
			}
		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}

		return jobSendDtos;
	}

	@Override
	public Job editJobAndSchedule(JobDto saveJob, Integer timezone) {
		Optional<Job> newJob = jobRepository.findById(saveJob.getId());
		newJob.get().setTeamAssignMemberIds(saveJob.getTeamAssignMemberIds());
		newJob.get().setTeamEmail(saveJob.getTeamEmail());
		newJob.get().setTeamUserReminder(saveJob.getTeamUserReminder());
		newJob.get().setTeamSendNotify(saveJob.getTeamSendNotify());
		/* update Driver Also */
		newJob.get().setDriverEmail(saveJob.getDriverEmail());
		newJob.get().setDriverEmailNotify(saveJob.getDriverEmailNotify());
		newJob.get().setDriverUserReminder(saveJob.getDriverUserReminder());
		newJob.get().setAssignMemberIds(saveJob.getAssignMemberIds());

		newJob.get().setSchStartDate(saveJob.getSchStartDate());
		newJob.get().setSchLater(saveJob.getSchLater());
		newJob.get().setJobProductService(saveJob.getJobProductService());
		newJob.get().setFinalTotal(saveJob.getFinalTotal());

		newJob.get().setJobTitle(saveJob.getJobTitle());
		newJob.get().setJobInstruction(saveJob.getJobInstruction());
		newJob.get().setSchEndDate(saveJob.getSchEndDate());
		if (!saveJob.getAnyTime()) {
			newJob.get().setSchStartTime(saveJob.getSchStartTime());
			newJob.get().setSchEndTime(saveJob.getSchEndTime());
		}

//		saveJob.setAssignMemberIds(newJob.get().getAssignMemberIds());
		saveJob.setJobCustomInstance(newJob.get().getJobCustomInstance());
//		saveJob.setJobProductService(newJob.get().getJobProductService());
		saveJob.setJobInternalNotes(newJob.get().getJobInternalNotes());
		saveJob.setJobNumber(newJob.get().getJobNumber());
		saveJob.setLocationId(newJob.get().getLocationId().getId());

		/* Save Job Method */
		Job newJob2 = saveJobFunctionality(newJob.get(), saveJob, timezone);
		return newJob2;
	}

	@Override
	public Job changeJobStatusAndSendMail(Long jobId, String changeStatus) {
		Boolean success = false;
		Job jobResult = new Job();
		try {
			Optional<Job> job = jobRepository.findById(jobId);
			if (job.isPresent()) {
				jobResult = job.get();
				jobRepository.changeStatus(jobId, changeStatus);

				User user = userRepo.findByUserId(job.get().getUserId());
				PropertyDetails propertyDetails = propertyDetailRepository
						.findByLocationId(job.get().getLocationId().getId().intValue());
				if (changeStatus.equalsIgnoreCase("completed")) {
					String emailContent = "<html>\r\n" + "<head>\r\n" + "    <meta charset=\"utf-8\">\r\n"
							+ "    <h3>Hello " + user.getFirstName() + " " + user.getLastName() + ",</h3>\r\n"
							+ "</head>\r\n" + "\r\n" + "<body>\r\n" + "<p>Re: , ref #: #" + job.get().getJobNumber()
							+ "</p>\r\n" + "<p>Your job is now complete!</p>\r\n"
							+ "<p>If you have any questions about the work or service that our team has provided you, please don't hesitate to contact us.</p>\r\n"
							+ "<p>We work really hard to provide the best experience for our customers and are always looking for ways to improve. If you have a second to answer our short survey below, we would appreciate your feedback.</p>\r\n"
							+ "<p>Thank you, The team at " + propertyDetails.getName() + ".</p>\r\n" + "<p><a>"
							+ propertyDetails.getCompanyWebsite() + "</a></p>\r\n" + "</body>\r\n" + "</html>";
					String subject = "";
					String status = emailService.sendMailToClientAsCompleted(emailContent, user.getUsername(),
							user.getUsername(), subject);
				}
			}
			success = true;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		return jobResult;
	}

	@Override
	public Boolean resendClientReminder(SendReminderMailDto sendReminderMailDto, Long timeZone, MultipartFile[] files) {
		Boolean success = false;
		try {
			Optional<Job> job = jobRepository.findById(sendReminderMailDto.getJobId());
			if (job.isPresent()) {

				String formattedDate = "";
				String strDateFormat = "hh:mm a";

				Calendar startDate = new GregorianCalendar();
				Calendar startTime = new GregorianCalendar();
				startDate.setTime(Date.from(job.get().getSchStartDate()));
				startDate.add(Calendar.MINUTE, -(timeZone.intValue()));

				startTime.setTime(Date.from(job.get().getSchStartTime()));
				startTime.add(Calendar.MINUTE, -(timeZone.intValue()));

				DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
				formattedDate = dateFormat.format(startTime.getTime());

				String sendDate = "";
				SimpleDateFormat DateFor = new SimpleDateFormat("MMM dd yyyy");
				sendDate = DateFor.format(startDate.getTime());

				String emailContent = "<html>\r\n" + "\r\n" + "<body>\r\n" + "<div >\r\n" + "		<p >Hello "
						+ sendReminderMailDto.getToUserName() + "," + "</p>\r\n" + "	</div>\r\n" + "	<div >\r\n"
						+ "		<p >Just a friendly reminder that we have an upcoming\r\n" + "			appointment."
						+ "	</div>\r\n" + "	<div >\r\n" + "		<span>Date: " + sendDate + " </span>\r\n"
						+ "	</div>\r\n" + "	<div >\r\n" + "		<span>Where: "
						+ job.get().getLocationId().getStreetNumber() + " " + job.get().getLocationId().getStreetName()
						+ ", " + job.get().getLocationId().getCity() + ", " + job.get().getLocationId().getState()
						+ ", " + job.get().getLocationId().getCountry() + ", " + job.get().getLocationId().getPinCode()
						+ " </span>\r\n" + "	</div>\r\n" + "	<div >\r\n" + "		<span >Time: " + formattedDate
						+ " </span>\r\n" + "	</div>\r\n" + "	<div >\r\n"
						+ "		<p >Our team will arrive within a 2 hour window of your\r\n"
						+ "			scheduled time.</p>\r\n" + "	</div>\r\n" + "	<div >\r\n"
						+ "		<p >If you have any questions or concerns, please don't\r\n"
						+ "			hesitate to get in touch with us at operations@checksammy.com. </p>\r\n"
						+ "	</div>\r\n" + "	<div >\r\n" + "		<span >Sincerely,</span>\r\n"
						+ "	</div>\r\n<div><p>Kris</p></div>" + "	<div >\r\n" + "		<span >\r\n"
						+ "			CheckSammy Inc.\r\n" + "		</span>\r\n" + "	</div>\r\n" + "</html>";
				List<String> toEmail = sendReminderMailDto.getToUser();
				List<String> ccEmail = sendReminderMailDto.getCcUsers();
				if (sendReminderMailDto.getSendCopyUser()) {
					User user = userRepo.findByUserId(job.get().getCreatedBy());
					ccEmail.add(user.getUsername());
				}

				emailService.sendMultipleRecipent(emailContent, toEmail, ccEmail, sendReminderMailDto.getSubject(),
						files, false);

			}
			success = true;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return success;
	}

	@Override
	public Boolean sendFollowUpMail(SendReminderMailDto sendReminderMailDto, Long timeZone, MultipartFile[] files) {
		Boolean success = false;
		try {
			Optional<Job> job = jobRepository.findById(sendReminderMailDto.getJobId());
			if (job.isPresent()) {
				User user = userRepo.findByUserId(sendReminderMailDto.getUserId());

				String feedBackMail = "";
				if (sendReminderMailDto.getIncludeFeedback()) {
					User userAdmin = userRepo.findByUserId(job.get().getCreatedBy());
					String companyWebSite = (userAdmin.getDriverInfo() != null
							? userAdmin.getDriverInfo().getCompanyWebsite()
							: "#");

					String comanyName = (userAdmin.getCompanyName() != null ? userAdmin.getCompanyName() : "");

					Calendar currentSendDate = Calendar.getInstance();
					SimpleDateFormat DateFor = new SimpleDateFormat("MMM dd yyyy");
					String currentDate = DateFor.format(currentSendDate.getTime());
					feedBackMail = " <div><p>On a scale of 0 to 10, how likely are you to recommend CheckSammy to a friend or co-worker?</p></div>\r\n"
							+ " <div style=\"border: solid 1px gray;\r\n" + "    width: 15%;\">\r\n"
							+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/"
							+ currentDate + "/0\">0</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
							+ job.get().getId() + "/" + currentDate + "/1\">1</a>\r\n" + " <a class=\"btn\" href=\""
							+ IP_PORT + "/client/rating/" + job.get().getId() + "/" + currentDate + "/2\">2</a>\r\n"
							+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/"
							+ currentDate + "/3\">3</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
							+ job.get().getId() + "/" + currentDate + "/4\">4</a>\r\n" + " <a class=\"btn\" href=\""
							+ IP_PORT + "/client/rating/" + job.get().getId() + "/" + currentDate + "/5\">5</a>\r\n"
							+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/"
							+ currentDate + "/6\">6</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
							+ job.get().getId() + "/" + currentDate + "/7\">7</a>\r\n" + " <a class=\"btn\" href=\""
							+ IP_PORT + "/client/rating/" + job.get().getId() + "/" + currentDate + "/8\">8</a>\r\n"
							+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/"
							+ currentDate + "/9\">9</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
							+ job.get().getId() + "/" + currentDate + "/10\">10</a></div>\r\n";
				} else {
					feedBackMail = "";
				}

				String emailContent = "<html>\r\n" + "<head>\r\n" + "    <meta charset=\"utf-8\">\r\n" + "    \r\n"
						+ "</head>\r\n" + "\r\n" + "<body>\r\n" + "    <div>\r\n" + " <p >Hello "
						+ sendReminderMailDto.getToUserName() + ", </p>\r\n" + " </div>\r\n" + " <div >\r\n"
						+ " <p >Re: , ref : #" + job.get().getJobNumber() + " Job ID </p>\r\n" + " </div>\r\n"
						+ " <div >\r\n" + " <p fxFlex.gt-xs=\"100\" class=\"mb-1\">Your job is now complete! </p>\r\n"
						+ " </div>\r\n" + " <div >\r\n"
						+ " <p >If you have any questions about the work or service that our team has provided you, please don't\r\n"
						+ " hesitate to contact us.</p>\r\n" + " </div>\r\n" + " <div >\r\n"
						+ " <p >We work really hard to provide the best experience for our customers and are always looking for ways\r\n"
						+ " to improve. If you have a second to answer our short survey below, we would appreciate your feedback.</p>\r\n"
						+ " </div>\r\n" + " <div >\r\n" + " <p >Thank you, The team at CheckSammy</p>\r\n"
						+ " </div>\r\n" + " <div>\r\n" + " <p >\r\n" + " www.checksammy.com\r\n" + " </p>\r\n"
						+ " </div>\r\n" + feedBackMail + "</body>\r\n" + "</html>";
				List<String> toEmail = sendReminderMailDto.getToUser();
				List<String> ccEmail = sendReminderMailDto.getCcUsers();
				if (sendReminderMailDto.getSendCopyUser()) {

					User aminUser = userRepo.findByUserId(job.get().getCreatedBy());
					ccEmail.add(aminUser.getUsername());

				}

				emailService.sendMultipleRecipent(emailContent, toEmail, ccEmail, sendReminderMailDto.getSubject(),
						files, false);

//				if (sendReminderMailDto.getIncludeFeedback()) {
//					User userAdmin = userRepo.findByUserId(job.get().getCreatedBy());
//					String companyWebSite = (userAdmin.getDriverInfo() != null
//							? userAdmin.getDriverInfo().getCompanyWebsite()
//							: "#");
//
//					String comanyName = (userAdmin.getCompanyName() != null ? userAdmin.getCompanyName() : "");
//					if (!sendReminderMailDto.getSendCopyUser()) {
//						toEmail.add(user.getUsername());
//					}
//					Calendar currentSendDate = Calendar.getInstance();
//					SimpleDateFormat DateFor = new SimpleDateFormat("MMM dd yyyy");
//					String currentDate = DateFor.format(currentSendDate.getTime());
//
//					String feedBackMail = "<!doctype html>\r\n" + "<html>\r\n" + "<head>\r\n"
//							+ "    <meta charset=\"utf-8\">\r\n" + "    <style>\r\n" + "	.btn{\r\n"
//							+ "		background: #ffffff;\r\n" + "		border: 0px;\r\n"
//							+ "		text-decoration: none;\r\n" + "		width:10px;\r\n" + "	}\r\n"
//							+ "	</style>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
//							+ "    <div><h1>Thank You: Your service visit by CheckSammy"
//							+ " is complete </h1></Div><div>\r\n" + " <p >Hello " + sendReminderMailDto.getToUserName()
//							+ ", </p>\r\n" + " </div>\r\n" + " <div >\r\n" + " <p >Re: , ref : #"
//							+ job.get().getJobNumber() + " Job ID </p>\r\n" + " </div>\r\n" + " <div >\r\n"
//							+ " <p >Your job is now complete! </p>\r\n" + " </div>\r\n" + " <div >\r\n"
//							+ " <span >If you have any questions about the work or service that our team has provided you, please don't\r\n"
//							+ " hesitate to contact us.</span>\r\n" + " </div>\r\n" + " <div >\r\n"
//							+ " <span >We work really hard to provide the best experience for our customers and are always looking for ways\r\n"
//							+ " to improve. If you have a second to answer our short survey below, we would appreciate your feedback.</span>\r\n"
//							+ " </div>\r\n" + " <div >\r\n" + " <p >Thank you, The team at CheckSammy</p>\r\n"
//							+ " </div>\r\n" + " <div>\r\n" + " <p >\r\n" + " <a href=\"https://https://www.checksammy.com"
//							+ "\">www.checksammy.com</a>\r\n" + " </p>\r\n" + " </div>\r\n"
//							+ " <div><p>On a scale of 0 to 10, how likely are you to recommend CheckSammy to a friend or co-worker?</p></div>\r\n"
//							+ " <div style=\"border: solid 1px gray;\r\n" + "    width: 15%;\">\r\n"
//							+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/"
//							+ currentDate + "/0\">0</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
//							+ job.get().getId() + "/" + currentDate + "/1\">1</a>\r\n" + " <a class=\"btn\" href=\""
//							+ IP_PORT + "/client/rating/" + job.get().getId() + "/" + currentDate + "/2\">2</a>\r\n"
//							+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/"
//							+ currentDate + "/3\">3</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
//							+ job.get().getId() + "/" + currentDate + "/4\">4</a>\r\n" + " <a class=\"btn\" href=\""
//							+ IP_PORT + "/client/rating/" + job.get().getId() + "/" + currentDate + "/5\">5</a>\r\n"
//							+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/"
//							+ currentDate + "/6\">6</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
//							+ job.get().getId() + "/" + currentDate + "/7\">7</a>\r\n" + " <a class=\"btn\" href=\""
//							+ IP_PORT + "/client/rating/" + job.get().getId() + "/" + currentDate + "/8\">8</a>\r\n"
//							+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/"
//							+ currentDate + "/9\">9</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
//							+ job.get().getId() + "/" + currentDate + "/10\">10</a></div>\r\n" + "</body>\r\n"
//							+ "</html>";
////					List<String> toEmail2 =new ArrayList<String>();
////					toEmail2.add(user.getUsername());
//					String subject = " Thank You: Your service visit by " + comanyName + " is complete";
//					emailService.sendMultipleRecipent(feedBackMail, toEmail, ccEmail, subject, files, true);
//				}
			}
			success = true;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return success;
	}

	@Override
	public void updateDriverReminderField(Long jobId, String assignUserReminderType) {
		jobRepository.updateJobDriverReminder(jobId, assignUserReminderType);
	}

	@Override
	public void updateSchedulerVisitMark(Long jobScheduleId, Boolean checked, Instant checkDate, Long userId) {
		User user = userRepo.findByUserId(userId);
		String name = "";
		if (user != null) {
			name = user.getFirstName() + user.getLastName();
		}
		visitSchedulerDateRepo.updateCheckMark(jobScheduleId, checked, checkDate, userId, name);
		visitAndServiceMapService.updateMarkFlag(jobScheduleId, checked);
	}

	@Override
	public void changeJobRating(Long jobId, String dateString, Long rating) {
		Double oldRating = jobRepository.findRatingByJobId(jobId);
		System.out.println(oldRating);
		System.out.println(dateString);

		Calendar currentSendDate = Calendar.getInstance();
		DateFormat Date = DateFormat.getDateInstance();
		String currentDate = Date.format(currentSendDate.getTime());

		SimpleDateFormat DateFor = new SimpleDateFormat("MMM dd yyyy");
		currentDate = DateFor.format(currentSendDate.getTime());
		System.out.println(currentDate);

		Double newRating = (oldRating + rating.doubleValue());
		Optional<Job> job = jobRepository.findById(jobId);
		if (job.isPresent()) {
			User user = userRepo.findByUserId(job.get().getUserId());
			User adminUser = userRepo.findByUserId(job.get().getCreatedBy());
			if (user != null) {
				String emailContent = "<html>\r\n" + "<head>\r\n" + "    <meta charset=\"utf-8\">\r\n"
						+ "    <style>\r\n" + "	.btn{\r\n" + "		background: #ffffff;\r\n"
						+ "		border: 0px;\r\n" + "		text-decoration: none;\r\n" + "		width:10px;\r\n"
						+ "	}\r\n" + "	</style>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
						+ "<div><h1>Feedback Summary </h1></Div>\r\n" + "    <div>\r\n" + " <h4 >sent </h4>\r\n"
						+ " <span >" + dateString + "</span>\r\n" + " </div>\r\n" + " \r\n" + " <div >\r\n"
						+ " <h4 >Received </h4>\r\n" + " <span>" + currentDate + "</span>\r\n" + " </div>\r\n" + " \r\n"
						+ " <div >\r\n" + " <h4 >Score </h4>\r\n" + " <span>" + rating + "</span>\r\n" + " </div>\r\n"
						+ " \r\n" + " <div >\r\n" + " <h4 >Message </h4>\r\n"
						+ " <p>Very satisfied. I'll be recommending to my parents as well </p>\r\n" + " </div>\r\n"
						+ " \r\n" + " <div >\r\n" + " <h3 >Feedback for </h3>\r\n" + " <div>Job #"
						+ job.get().getJobNumber() + " </div>\r\n" + " </div>\r\n" + " \r\n" + " <div >\r\n"
						+ " <h4 >Address </h4>\r\n" + " <span>"
						+ (job.get().getLocationId().getStreetNumber() != null
								? job.get().getLocationId().getStreetNumber()
								: "")
						+ " "
						+ (job.get().getLocationId().getStreetName() != null ? job.get().getLocationId().getStreetName()
								: "")
						+ "</span><br>\r\n" + " <span>" + job.get().getLocationId().getCity() + ", "
						+ job.get().getLocationId().getState() + " " + job.get().getLocationId().getPinCode()
						+ " </span>\r\n" + " </div>\r\n" + " \r\n" + " <div >\r\n" + " <h4 >Contact details  </h4>\r\n"
						+ " <span>" + user.getFirstName() + " " + user.getLastName() + "</span></br>\r\n"
						+ " <span>Phone: " + (user.getPhone() != null ? user.getPhone() : "") + "</span><br>\r\n"
						+ " <span>Email: <a  href=\"#\">" + (user.getUsername() != null ? user.getUsername() : "")
						+ "</a></span>\r\n" + " </div>\r\n" + "\r\n" + "</body>\r\n" + "</html>";
				String subject = "You have received feedback from a client";
				String fromUserEmail = adminUser.getUsername();
				String status = emailService.sendSupportEmailFeedbackSummary(emailContent, fromUserEmail, subject);
			}
		}
	}

	@Override
	public JobVisitReportDto getJobDataForReport(long currJobId, String assignTo, String lineItem) {
		// return jobRepository.findById(currJobId);
		JobVisitReportDto jobVisitReportDto = new JobVisitReportDto();
		Optional<Job> job = jobRepository.findById(currJobId);
		String[] assignIds = (job.isPresent() && job.get().getAssignMemberIds() != null)
				? job.get().getAssignMemberIds().split(",")
				: new String[0];
		if (!job.isEmpty()) {
			Location location = job.get().getLocationId();
			User user = userRepo.findByUserId(job.get().getUserId());
			if (assignTo.length() > 0) {
				String[] assignId = assignTo.split(",");
				for (int i = 0; i <= assignIds.length - 1; i++) {
					if (assignIds[i].equals(assignId[0])) {
						User assignUser = userRepo.findByUserId(Long.parseLong(assignId[0]));
						jobVisitReportDto.setAssignTo(assignUser.getFirstName() + " " + assignUser.getLastName());
					}
				}
			}
			if (lineItem.length() > 0) {
				JobProductService jobProductService = jobProdRepository.getLineItemData(currJobId,
						Long.parseLong(lineItem));
				if (jobProductService != null) {
					jobVisitReportDto.setLineItems(jobProductService.getNotes());
					jobVisitReportDto.setVisitBased(jobProductService.getTotalCost());
				}
			}
			jobVisitReportDto.setClinetName(user.getFirstName());
			jobVisitReportDto.setPhone(user.getPhone());
			jobVisitReportDto.setClientEmail(user.getUsername());
			jobVisitReportDto.setCity(location.getCity());
			jobVisitReportDto.setStreet(location.getStreetName());
			jobVisitReportDto.setState(location.getState());
			jobVisitReportDto.setZipCode(location.getPinCode());
			jobVisitReportDto.setJobDetails(job.get().getJobInstruction());
			jobVisitReportDto.setDuration(job.get().getSchDuration() + " " + job.get().getSchDurationType());
			if (job.get().getJobScheduleType().toLowerCase().equalsIgnoreCase("one-off job")) {
				jobVisitReportDto.setNoOfJobs(job.get().getFinalTotal());
			} else {
				jobVisitReportDto.setNoOfJobs(null);
			}
		}
		return jobVisitReportDto;
	}

	@Override
	public void sendEmail(List<JobVisitReportDto> jobVisitReportDtos, JobVisitReportFilterDto filterDto,
			Integer timezone) {
		try {
			String emailContent = "<html>\r\n" + "<head>\r\n" + "    <meta charset=\"utf-8\">\r\n" + "    <h3>Hello "
					+ filterDto.getUserName() + ",</h3>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
					+ "<p>As per your request, PFA visits report.</p>\r\n" + "<p>Sincerely,</p>\r\n"
					+ "<p>Checksammy Team</p>\r\n" + "</body>\r\n" + "</html>";
//					+ "<p>Attachment - Excel sheet with either of two options selected by user from the field 'Receive Excel Copy'.</p>\r\n"
			createXLX(jobVisitReportDtos, filterDto.getColumnArray(), timezone);
			emailService.setEmailToSingleUser(filterDto.getToEmail(), emailContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createXLX(List<JobVisitReportDto> jobVisitReportDtos, int[] columnArray, Integer timezone)
			throws IOException {
		File dir = new File(GlobalValues.VISIT_REPORT_FILE_PATH + File.separator + "files");
		logger.info("Directory Name: " + dir.getAbsolutePath());
		if (!dir.exists())
			dir.mkdirs();
		logger.info("Directory created.");
		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Visit Report");

		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 14);
		font.setBold(true);
		headerStyle.setFont(font);

		for (int i = 0; i <= columnArray.length - 1; i++) {
			Cell headerCell;
			switch (columnArray[i]) {
			case 1:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Date");
				headerCell.setCellStyle(headerStyle);
				break;
			case 2:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Time");
				headerCell.setCellStyle(headerStyle);
				break;
			case 3:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Client Name");
				headerCell.setCellStyle(headerStyle);
				break;
			case 4:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Street");
				headerCell.setCellStyle(headerStyle);
				break;
			case 5:
				headerCell = header.createCell(i);
				headerCell.setCellValue("City");
				headerCell.setCellStyle(headerStyle);
				break;
			case 6:
				headerCell = header.createCell(i);
				headerCell.setCellValue("State");
				headerCell.setCellStyle(headerStyle);
				break;
			case 7:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Zip Code");
				headerCell.setCellStyle(headerStyle);
				break;
			case 8:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Assigned To");
				headerCell.setCellStyle(headerStyle);
				break;
			case 9:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Job Details");
				headerCell.setCellStyle(headerStyle);
				break;
			case 10:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Line Items");
				headerCell.setCellStyle(headerStyle);
				break;
			case 11:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Duration");
				headerCell.setCellStyle(headerStyle);
				break;
			case 12:
				headerCell = header.createCell(i);
				headerCell.setCellValue("One Of Jobs $");
				headerCell.setCellStyle(headerStyle);
				break;
			case 13:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Visit Based $");
				headerCell.setCellStyle(headerStyle);
				break;
			case 14:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Phone");
				headerCell.setCellStyle(headerStyle);
				break;
			case 15:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Client Email");
				headerCell.setCellStyle(headerStyle);
				break;
			case 16:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Open");
				headerCell.setCellStyle(headerStyle);
				break;
			default:
				break;
			}
		}

		// Write content
		CellStyle style = workbook.createCellStyle();
		// style.setWrapText(true);

		Row row;
		Cell cell;
		for (int i = 0; i <= jobVisitReportDtos.size() - 1; i++) {
			row = sheet.createRow(i + 1);
			List<JobVisitReportDto> jobVisitReportDto = new ArrayList<JobVisitReportDto>();
			jobVisitReportDto.add(jobVisitReportDtos.get(i));
			for (int j = 0; j <= jobVisitReportDto.size(); j++) {
				for (int k = 0; k <= columnArray.length - 1; k++) {
					switch (columnArray[k]) {
					case 1:
						// String date = getDateInFormat(jobVisitReportDtos.get(i).getDate());
						Calendar startDate1 = new GregorianCalendar();
						startDate1.setTime(Date.from(jobVisitReportDtos.get(i).getDate()));
						String formattedDate1 = "MM/dd/yyyy";
						startDate1.add(Calendar.MINUTE, -(timezone));
						DateFormat dateFormat1 = new SimpleDateFormat(formattedDate1);
						String formattedDate2 = dateFormat1.format(startDate1.getTime());
						cell = row.createCell(k);
						cell.setCellValue(formattedDate2);
						cell.setCellStyle(style);
						break;
					case 2:
						// String time =
						// getTimeFromDate(jobVisitReportDtos.get(i).getDate().toString());
						String formattedDate = "";
						String strDateFormat = "hh:mm a";

						Calendar time = new GregorianCalendar();

						time.setTime(Date.from(jobVisitReportDtos.get(i).getDate()));
						time.add(Calendar.MINUTE, -(timezone));

						DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
						// formattedDate = dateFormat.format(time.getTime());
						cell = row.createCell(k);
						cell.setCellValue(dateFormat.format(time.getTime()));
						cell.setCellStyle(style);
						break;
					case 3:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getClinetName());
						cell.setCellStyle(style);
						break;
					case 4:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getStreet());
						cell.setCellStyle(style);
						break;
					case 5:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getCity());
						cell.setCellStyle(style);
						break;
					case 6:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getState());
						cell.setCellStyle(style);
						break;
					case 7:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getZipCode());
						cell.setCellStyle(style);
						break;
					case 8:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getAssignTo());
						cell.setCellStyle(style);
						break;
					case 9:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getJobDetails());
						cell.setCellStyle(style);
						break;
					case 10:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getLineItems());
						cell.setCellStyle(style);
						break;
					case 11:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getDuration());
						cell.setCellStyle(style);
						break;
					case 12:
						cell = row.createCell(k);
						if (jobVisitReportDtos.get(i).getNoOfJobs() == null) {
							cell.setCellValue(0);
						} else {
							cell.setCellValue(jobVisitReportDtos.get(i).getNoOfJobs());
						}
						cell.setCellStyle(style);
						break;
					case 13:
						cell = row.createCell(k);
						if (jobVisitReportDtos.get(i).getVisitBased() == null) {
							cell.setCellValue(0);
						} else {
							cell.setCellValue(jobVisitReportDtos.get(i).getVisitBased());
						}
						cell.setCellStyle(style);
						break;
					case 14:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getPhone());
						cell.setCellStyle(style);
						break;
					case 15:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getClientEmail());
						cell.setCellStyle(style);
						break;
					case 16:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getOpen());
						cell.setCellStyle(style);
						break;
					default:
						break;
					}
				}
				jobVisitReportDto.clear();
			}

		}
		// GlobalValues.EMAIL_FILE_PATH + File.separator + "files"
		FileOutputStream outputStream = new FileOutputStream(dir + File.separator + "Visits Report.xls");
		workbook.write(outputStream);
		workbook.close();
	}

	@Override
	public Job updateFieldFromVisitUpdate(VisitScheduleUpdateDto visitScheduleUpdateDto, Long timezone) {
		jobRepository.updateJobTitleAndInstuction(visitScheduleUpdateDto.getJobId(),
				visitScheduleUpdateDto.getVisitTitle(), visitScheduleUpdateDto.getVisitInstruction(),
				visitScheduleUpdateDto.getAssignDriver(), visitScheduleUpdateDto.getAssignTeam());
		long[] teamIds = null;
		long[] drivers = null;
//			teams = visitScheduleUpdateDto.getAssignTeam().stream().map(String::toLowerCase)
//					.collect(Collectors.joining(","));
		Optional<Job> job = jobRepository.findById(visitScheduleUpdateDto.getJobId());
		if (visitScheduleUpdateDto.getAssignDriver() != null
				&& !visitScheduleUpdateDto.getAssignDriver().equalsIgnoreCase("")) {
			drivers = Arrays.asList(visitScheduleUpdateDto.getAssignDriver().split(",")).stream().map(String::trim)
					.mapToLong(Long::parseLong).toArray();
			if (visitScheduleUpdateDto.getDriverEmailFlag()) {

				sendEmailMethod(drivers, DRIVER, new JobDto(), job.get());
			}
		}

		if (visitScheduleUpdateDto.getAssignTeam() != null
				&& !visitScheduleUpdateDto.getAssignTeam().equalsIgnoreCase("")) {
			teamIds = Arrays.asList(visitScheduleUpdateDto.getAssignTeam().split(",")).stream().map(String::trim)
					.mapToLong(Long::parseLong).toArray();
			if (visitScheduleUpdateDto.getTeamEmailFlag()) {
				sendEmailMethodTeamByVisit(teamIds, TEAM, job.get(), drivers, timezone.intValue());
			}
		}

//		Save product and service
		jobProdRepository.saveAll(visitScheduleUpdateDto.getJobProductService());

		return job.get();
	}

	private void sendEmailMethodTeamByVisit(long[] teamIds, String team2, Job saveJob, long[] driverIdsList,
			Integer timezone) {
		try {

			List<User> emailUser = userRepo.findUserByAssignIds(teamIds);
			EmailDto emailDto = new EmailDto();
			for (User user : emailUser) {
				List<EmailList> emailLists = new ArrayList<EmailList>();
				EmailList emailList = new EmailList();
				emailList.setName(user.getUsername());
				emailLists.add(emailList);

//			if (team2.equalsIgnoreCase(TEAM)) {
//				Instant timeStamp = Instant.now();
				try {
					List<User> driverList = userRepo.findUserByAssignIds(driverIdsList);
					List<String> driverNameList = new ArrayList<String>();
					for (User driver : driverList) {
						driverNameList.add(driver.getFirstName() + " " + driver.getLastName());
					}
					String completeDriverName = String.join(",", driverNameList);
					Optional<Location> locatiOptional = locationRepo.findById(saveJob.getLocationId().getId());

					Calendar forTime = new GregorianCalendar();
					try {
						forTime.setTime(Date.from(saveJob.getSchStartTime()));
						forTime.add(Calendar.MINUTE, -(timezone));
					} catch (Exception e) {
					}

					String formattedDate = "";
					try {
						String strDateFormat = "hh:mm a";
						DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
						formattedDate = dateFormat.format(forTime.getTime());
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (saveJob.getSchLater()) {

					}
					Calendar calendar = new GregorianCalendar();
					try {
						calendar.setTime(Date.from(saveJob.getSchStartDate()));
						calendar.add(Calendar.MINUTE, -(timezone));
					} catch (Exception e) {
						// TODO: handle exception
					}

					String emailContent = "<html>\r\n" + "<div><h3>Hi " + user.getFirstName() + " " + user.getLastName()
							+ ", </h3>\r\n" + "</div>\r\n" + "<div>\r\n" + "<h3>A new job - #" + saveJob.getJobNumber()
							+ " - has been assigned to you. Driver, " + completeDriverName
							+ ", has been assigned for this job.</h2>\r\n" + "<p>Date for the job - "
							+ (saveJob.getSchLater() ? "unschedule"
									: new SimpleDateFormat("MM/dd/YYYY").format(calendar.getTime()))
							+ "</p>\r\n" + "<p>Time of the job - " + formattedDate + "</p>\r\n" + "<p>Location - "
							+ locatiOptional.get().getPropertyName() + "</p>\r\n" + "<p>Thanks</p>\r\n" + "</div>\r\n"
							+ "</html>";
//					
					emailDto.setEditorHtml(emailContent);
					emailDto.setSubject("Job Assigned - CS LocA");
					emailDto.setSendToEmails(emailLists);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage());
				}
//			}
				emailService.sendEmailToMutipleRecipientAssignUser(emailDto);
			}

		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}

	}

	@Override
	public List<Job> getJobListByUserAssign(Long userId) {
		return jobRepository.findByUserAndNonDeleteList(userId);
	}

	@Override
	public void updateSchedulerVisitMarkUncheck(Long visitSchedulerId, Boolean checked, Long userId) {
		User user = userRepo.findByUserId(userId);
		String name = "";
		if (user != null) {
			name = user.getFirstName() + user.getLastName();
		}
		visitSchedulerDateRepo.updateUnchecked(visitSchedulerId, checked, userId, name);// TODO Auto-generated method
																						// stub
		visitAndServiceMapService.updateMarkFlag(visitSchedulerId, checked);
	}

	@Override
	public Job findJobByVisitId(Long visitSchedulerId) {
		return jobRepository.findByVisitId(visitSchedulerId);
	}
}
