package com.checksammy.loca.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.BillingInvoiceDto;
import com.checksammy.loca.dto.InvoiceCustomInstanceDto;
import com.checksammy.loca.dto.InvoiceDto;
import com.checksammy.loca.dto.InvoiceInternalNotesDto;
import com.checksammy.loca.dto.JobVisitReportDto;
import com.checksammy.loca.dto.JobVisitReportFilterDto;
import com.checksammy.loca.dto.TransactionReportDto;
import com.checksammy.loca.model.BinType;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.InvoiceCustomInstance;
import com.checksammy.loca.model.InvoiceInternalNotes;
import com.checksammy.loca.model.InvoiceProductAndService;
import com.checksammy.loca.model.InvoiceSchedularDate;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.Quote;
import com.checksammy.loca.model.User;
import com.checksammy.loca.repository.BinTypeRespository;
import com.checksammy.loca.repository.FieldTypeInstanceRepository;
import com.checksammy.loca.repository.InvoiceCustomInstanceRepository;
import com.checksammy.loca.repository.InvoiceInternalNotesRepository;
import com.checksammy.loca.repository.InvoiceProductAndServiceRepository;
import com.checksammy.loca.repository.InvoiceRepository;
import com.checksammy.loca.repository.InvoiceSchedulerDateRepository;
import com.checksammy.loca.repository.JobRepository;
import com.checksammy.loca.repository.QuoteRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.service.EmailService;
import com.checksammy.loca.service.InvoiceService;
import com.checksammy.loca.service.QuoteService;
import com.checksammy.loca.utility.DateServiceUtil;
import com.checksammy.loca.utility.GlobalValues;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	/* Server IP and PORT */
	private static final String IP_PORT = "http://3.96.13.253:8085/LocA/loca/api/job"; // http://3.96.13.253:8085/LocA

	public static final String SMS_URL = "https://application.textline.com/api/conversations.json";

	public static final String EMAIL_PICKUP = "support@checksammy.com";

	private static final String EMAIL = "email";
	private static final String TEXT = "text";

	private static final String COMPLETED = "completed";

	private static final String DEPOSIT = "deposit";

	private static final String REFUND = "refund";

	private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private InvoiceCustomInstanceRepository customInstanceRepo;

	@Autowired
	private InvoiceProductAndServiceRepository producServiceRepository;

	@Autowired
	private InvoiceInternalNotesRepository internalNotesRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JobRepository jobRepo;

	@Autowired
	FieldTypeInstanceRepository fieldTypeRepository;

	@Autowired
	BinTypeRespository binTypeRespository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	DateServiceUtil dateServiceUtil;

	@Autowired
	private InvoiceSchedulerDateRepository invoiceSchedularDateRepo;

	@Override
	public InvoiceDto saveInoviceData(InvoiceDto invoiceSave) throws UnsupportedEncodingException, IOException {
		Invoice invoice = invoiceSave.setInvoiceData(invoiceSave);
		invoice = invoiceRepository.save(invoice);
		invoiceRepository.updateUserAndLocation(invoice.getId(), invoiceSave.getJobId(), invoiceSave.getUserId(),
				invoiceSave.getLocationId());

		List<InvoiceCustomInstance> invoiceCustomInstances = new ArrayList<InvoiceCustomInstance>();
		List<InvoiceProductAndService> invoiceProductAndServices = new ArrayList<InvoiceProductAndService>();
		List<InvoiceInternalNotes> invoiceInternalNotes = new ArrayList<InvoiceInternalNotes>();

		if (invoiceSave.getInoviceCustomInstance() != null && invoiceSave.getInoviceCustomInstance().size() > 0) {
			for (InvoiceCustomInstance invoiceCustomInstance : invoice.getInoviceCustomInstance()) {
				invoiceCustomInstance.setInvoiceId(invoice.getId());
				invoiceCustomInstances.add(invoiceCustomInstance);
			}
			invoiceCustomInstances = customInstanceRepo.saveAll(invoiceCustomInstances);
		}

		if (invoiceSave.getInvoiceProductService() != null && invoiceSave.getInvoiceProductService().size() > 0) {
			for (InvoiceProductAndService invoiceProductAndService : invoice.getInvoiceProductService()) {
				invoiceProductAndService.setInvoiceId(invoice.getId());
				invoiceProductAndServices.add(invoiceProductAndService);
			}
			invoiceProductAndServices = producServiceRepository.saveAll(invoiceProductAndServices);
		}

		if (invoiceSave.getInvoiceInternalNotes() != null && invoiceSave.getInvoiceInternalNotes().size() > 0) {
			for (InvoiceInternalNotes invoiceInternalNote : invoice.getInvoiceInternalNotes()) {
				invoiceInternalNote.setInvoiceId(invoice.getId());
				invoiceInternalNotes.add(invoiceInternalNote);
			}
			invoiceInternalNotes = internalNotesRepository.saveAll(invoiceInternalNotes);
		}

		if (invoiceSave.getSendStatus() != null && invoiceSave.getSendStatus().equalsIgnoreCase(EMAIL)) {
			Optional<Job> job = jobRepo.findById(invoiceSave.getJobId());
			User user = userRepo.findByUserId(invoiceSave.getUserId());

			Calendar currentSendDate = Calendar.getInstance();
			SimpleDateFormat DateFor = new SimpleDateFormat("MMM dd yyyy");
			String currentDate = DateFor.format(currentSendDate.getTime());

			User userAdmin = userRepo.findByUserId(job.get().getCreatedBy());
			String companyWebSite = (userAdmin.getDriverInfo() != null ? userAdmin.getDriverInfo().getCompanyWebsite()
					: "#");

			String comanyName = (userAdmin.getCompanyName() != null ? userAdmin.getCompanyName() : "");

			String emailContent = "<!doctype html>\r\n" + "<html>\r\n" + "<head>\r\n"
					+ "    <meta charset=\"utf-8\">\r\n" + "    <style>\r\n" + "	.btn{\r\n"
					+ "		background: #ffffff;\r\n" + "		border: 0px;\r\n" + "		text-decoration: none;\r\n"
					+ "		width:10px;\r\n" + "	}\r\n" + "	</style>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
					+ "    <div><h1>Thank You: Your service visit by CheckSammy" + " is complete </h1></Div><div>\r\n"
					+ " <p >Hello " + user.getFirstName() + " " + user.getLastName() + ", </p>\r\n" + " </div>\r\n"
					+ " <div >\r\n" + " <p >Re: , ref : #" + job.get().getJobNumber() + " Job ID </p>\r\n"
					+ " </div>\r\n" + " <div >\r\n" + " <p >Your job is now complete! </p>\r\n" + " </div>\r\n"
					+ " <div >\r\n"
					+ " <span >If you have any questions about the work or service that our team has provided you, please don't\r\n"
					+ " hesitate to contact us.</span>\r\n" + " </div>\r\n" + " <div >\r\n"
					+ " <span >We work really hard to provide the best experience for our customers and are always looking for ways\r\n"
					+ " to improve. If you have a second to answer our short survey below, we would appreciate your feedback.</span>\r\n"
					+ " </div>\r\n" + " <div >\r\n" + " <p >Thank you, The team at CheckSammy</p>\r\n" + " </div>\r\n"
					+ " <div>\r\n" + " <p >\r\n" + " <a href=\"https://www.checksammy.com"
					+ "\">www.checksammy.com</a>\r\n" + " </p>\r\n" + " </div>\r\n"
					+ " <div><p>On a scale of 0 to 10, how likely are you to recommend CheckSammy to a friend or co-worker?</p></div>\r\n"
					+ " <div style=\"border: solid 1px gray;\r\n" + "    width: 20%;\">\r\n"
					+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/" + currentDate
					+ "/0\">0</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId()
					+ "/" + currentDate + "/1\">1</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
					+ job.get().getId() + "/" + currentDate + "/2\">2</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT
					+ "/client/rating/" + job.get().getId() + "/" + currentDate + "/3\">3</a>\r\n"
					+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/" + currentDate
					+ "/4\">4</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId()
					+ "/" + currentDate + "/5\">5</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
					+ job.get().getId() + "/" + currentDate + "/6\">6</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT
					+ "/client/rating/" + job.get().getId() + "/" + currentDate + "/7\">7</a>\r\n"
					+ " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId() + "/" + currentDate
					+ "/8\">8</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/" + job.get().getId()
					+ "/" + currentDate + "/9\">9</a>\r\n" + " <a class=\"btn\" href=\"" + IP_PORT + "/client/rating/"
					+ job.get().getId() + "/" + currentDate + "/10\">10</a></div>\r\n" + "</body>\r\n" + "</html>";
			String emailStatus = emailService.sendSupportEmailHtmlFormatToUser(emailContent, user.getUsername(),
					user.getUsername(), "Invoice send to " + user.getUsername());
		} else if (invoiceSave.getSendStatus() != null && invoiceSave.getSendStatus().equalsIgnoreCase(TEXT)) {

//			sendTextInvoice(invoiceSave.getJobId());
		}
//		Optional<Invoice> invoiceSend = invoiceRepository.findById(invoice.getId());
		InvoiceDto invoiceDto = getById(invoice.getId());
		invoiceDto.setJobId(invoiceSave.getJobId());
		Optional<Job> job = jobRepo.findById(invoiceSave.getJobId());
		User userRelated = userRepo.findByUserId(invoiceSave.getUserId());
		invoiceDto.setJob(job.get());
		invoiceDto.setUser(userRelated);
		return invoiceDto;
	}

	/* FOR sprint 10 send sms */
	private void sendTextInvoice(Long jobId) {
		Optional<Job> job = jobRepo.findById(jobId);
		User user = userRepo.findByUserId(jobId);

		try {
			String userUUIDKey = null;

			URL url = new URL(SMS_URL);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("X-TGP-ACCESS-TOKEN", GlobalValues.SMS_ACCESS_TOKEN);
			con.setDoOutput(true);
			String jsonInputString = "{\"customer\": {\"phone_number\": \"+91" + user.getPhone() + "\",\"email\": \""
					+ user.getUsername() + "\",\"name\": \"" + user.getFirstName() + " " + user.getLastName()
					+ "\",\"notes\": \"some samples notes for the contact\",\"tags\": \"foo bar\"}}";

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				System.out.println(response.toString());

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public List<InvoiceDto> getAll() {
		List<InvoiceDto> newInvoiceDtoList = new ArrayList<InvoiceDto>();
		List<Invoice> invoceList = invoiceRepository.findAll();
		for (Invoice invoice : invoceList) {
			try {
				InvoiceDto invoiceDto = new InvoiceDto();
				List<InvoiceInternalNotesDto> invoiceInternalNotesDtos = new ArrayList<InvoiceInternalNotesDto>();
//				List<QuotesInternalNotes> quotesInternalNotes = quotesInternalNotesRepo.findByQuoteId(quoteId);
				List<InvoiceInternalNotes> invoiceInternalNotes = new ArrayList<InvoiceInternalNotes>();
				List<InvoiceCustomInstanceDto> invoiceCustomInstanceDtos = new ArrayList<InvoiceCustomInstanceDto>();
				/* Internal notes */
				if (invoice.getInvoiceInternalNotes() != null && invoice.getInvoiceInternalNotes().size() > 0) {
					invoiceInternalNotes = new ArrayList<InvoiceInternalNotes>(invoice.getInvoiceInternalNotes());

					for (InvoiceInternalNotes invoiceInternalNotes2 : invoiceInternalNotes) {
						InvoiceInternalNotesDto invoiceInternalNotesDto = new InvoiceInternalNotesDto();
						try {
							User realtedUser = userRepo.findByUserId(invoiceInternalNotes2.getCreatedBy());
							invoiceInternalNotesDto = invoiceInternalNotesDto.addedRealtedUserwithData(realtedUser,
									invoiceInternalNotes2);
							invoiceInternalNotesDtos.add(invoiceInternalNotesDto);
						} catch (Exception e) {
							logger.debug(e.getCause().getLocalizedMessage());
						}
					}
				}

				if (invoice.getInoviceCustomInstance() != null && invoice.getInoviceCustomInstance().size() > 0) {
					for (InvoiceCustomInstance invoiceCustomInstance : invoice.getInoviceCustomInstance()) {
						InvoiceCustomInstanceDto invoiceCustomInstanceDto = new InvoiceCustomInstanceDto();
						Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
								.findById(invoiceCustomInstance.getFieldInstanceId());

						invoiceCustomInstanceDto = invoiceCustomInstanceDto.addCustomField(fieldTypeInstance.get(),
								invoiceCustomInstance);
						invoiceCustomInstanceDtos.add(invoiceCustomInstanceDto);
					}
				}

//				TODO: Quotes Mapp
				invoiceDto = invoiceDto.getInvoiceData(invoice, invoiceInternalNotesDtos, invoiceCustomInstanceDtos);
				newInvoiceDtoList.add(invoiceDto);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return newInvoiceDtoList;
	}

	@Override
	public InvoiceDto getById(Long invoiceId) {
		InvoiceDto invoiceDto = new InvoiceDto();
		Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
		try {
			if (invoice.isPresent()) {
				List<InvoiceInternalNotesDto> invoiceInternalNotesDtos = new ArrayList<InvoiceInternalNotesDto>();
//				List<QuotesInternalNotes> quotesInternalNotes = quotesInternalNotesRepo.findByQuoteId(quoteId);
				List<InvoiceInternalNotes> invoiceInternalNotes = new ArrayList<InvoiceInternalNotes>();
				List<InvoiceCustomInstanceDto> invoiceCustomInstanceDtos = new ArrayList<InvoiceCustomInstanceDto>();
				/* Internal notes */
				if (invoice.get().getInvoiceInternalNotes() != null
						&& invoice.get().getInvoiceInternalNotes().size() > 0) {
					invoiceInternalNotes = new ArrayList<InvoiceInternalNotes>(invoice.get().getInvoiceInternalNotes());

					for (InvoiceInternalNotes invoiceInternalNotes2 : invoiceInternalNotes) {
						InvoiceInternalNotesDto invoiceInternalNotesDto = new InvoiceInternalNotesDto();
						try {
							User realtedUser = userRepo.findByUserId(invoiceInternalNotes2.getCreatedBy());
							invoiceInternalNotesDto = invoiceInternalNotesDto.addedRealtedUserwithData(realtedUser,
									invoiceInternalNotes2);
							invoiceInternalNotesDtos.add(invoiceInternalNotesDto);
						} catch (Exception e) {
							logger.debug(e.getCause().getLocalizedMessage());
						}
					}
				}

				if (invoice.get().getInoviceCustomInstance() != null
						&& invoice.get().getInoviceCustomInstance().size() > 0) {
					for (InvoiceCustomInstance invoiceCustomInstance : invoice.get().getInoviceCustomInstance()) {
						InvoiceCustomInstanceDto invoiceCustomInstanceDto = new InvoiceCustomInstanceDto();
						Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
								.findById(invoiceCustomInstance.getFieldInstanceId());

						if (fieldTypeInstance.isPresent()) {
							invoiceCustomInstanceDto = invoiceCustomInstanceDto.addCustomField(fieldTypeInstance.get(),
									invoiceCustomInstance);
							invoiceCustomInstanceDtos.add(invoiceCustomInstanceDto);
						}
					}
				}

				invoiceDto = invoiceDto.getInvoiceData(invoice.get(), invoiceInternalNotesDtos,
						invoiceCustomInstanceDtos);
				if (invoice.get().getServiceAsBintypeId() != null) {
					Optional<BinType> bin = binTypeRespository.findById(invoice.get().getServiceAsBintypeId());
					invoiceDto.setServiceAsBintype(bin.isPresent() ? bin.get().getName() : null);
				}

			}
		} catch (Exception e) {
			logger.debug(e.getCause().getLocalizedMessage());
		}

		return invoiceDto;
	}

	@Override
	public void updatePaymentStatus(Long invoiceId, String status, Long statusId, Double double1, Instant paidDate,
			String string) {
		invoiceRepository.updateInvoiceStatus(invoiceId, status, statusId, double1, paidDate, string);
	}

	@Override
	public void updatePaymentStatusOnly(Long invoiceId, String status, Long statusId) {
		// TODO Auto-generated method stub
		invoiceRepository.updatePaymentStatus(invoiceId, status, statusId);
	}

	@Override
	public List<Invoice> findInvoiceBy(Long jobId) {
		return invoiceRepository.findByJobId(jobId);
	}

	@Override
	public List<BillingInvoiceDto> filterInvoiceData(List<Invoice> invoiceRealtedToJob, Long timezone) {
		List<BillingInvoiceDto> finalBilling = new ArrayList<BillingInvoiceDto>();
		List<BillingInvoiceDto> billingInvoiceAsPaids = new ArrayList<BillingInvoiceDto>();
		List<BillingInvoiceDto> billingInvoiceAsUnPaids = new ArrayList<BillingInvoiceDto>();

		if (invoiceRealtedToJob.size() > 0) {
			List<Invoice> billingPaidData = invoiceRealtedToJob.stream()
					.filter(visit -> visit.getStatus().contains("Completed")).collect(Collectors.toList());
			for (Invoice invoice : billingPaidData) {
				BillingInvoiceDto billingInvoiceDto = new BillingInvoiceDto();

				Calendar currentDate = new GregorianCalendar();
				Calendar visitDate = new GregorianCalendar();
				Calendar completeDate = new GregorianCalendar();
				currentDate.setTime(new Date());
				currentDate.add(Calendar.MINUTE, -(timezone.intValue()));
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);
				currentDate.set(Calendar.SECOND, 0);
				currentDate.set(Calendar.MILLISECOND, 0);

				visitDate.setTime(Date.from(invoice.getCreatedTs()));
				visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
				visitDate.set(Calendar.HOUR_OF_DAY, 0);
				visitDate.set(Calendar.MINUTE, 0);
				visitDate.set(Calendar.SECOND, 0);
				visitDate.set(Calendar.MILLISECOND, 0);

				completeDate.setTime(Date.from(invoice.getLastPayDate()));
				completeDate.set(Calendar.HOUR_OF_DAY, 0);
				completeDate.set(Calendar.MINUTE, 0);
				completeDate.set(Calendar.SECOND, 0);
				completeDate.set(Calendar.MILLISECOND, 0);

				Integer month = visitDate.get(Calendar.MONTH);
				Integer year = visitDate.get(Calendar.YEAR);

				if (billingInvoiceAsPaids.size() > 0) {
					Optional<BillingInvoiceDto> optional = billingInvoiceAsPaids.stream()
							.filter(x -> x.getMonthNumber().equals(month) && x.getYear().equals(year)).findFirst();

					if (optional.isPresent()) {
						for (BillingInvoiceDto billingInvoiceAsPaid : billingInvoiceAsPaids) {
							if (billingInvoiceAsPaid.getMonthNumber().equals(month)
									&& billingInvoiceAsPaid.getYear().equals(year)) {
								List<Invoice> invoiceDummy = new ArrayList<Invoice>();
								invoiceDummy.addAll(billingInvoiceAsPaid.getInvoiceList());
								invoiceDummy.add(invoice);
								billingInvoiceAsPaid.setInvoiceList(invoiceDummy);
							}
						}
					}
					if (optional.isEmpty()) {
						if (currentDate.equals(completeDate)) {
							billingInvoiceDto.setStatus("Completed");
							billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(13));
							billingInvoiceDto.setMonthNumber(13);
							billingInvoiceDto.setYear(year);
							List<Invoice> vistDummy = new ArrayList<Invoice>();
							vistDummy.add(invoice);
							billingInvoiceDto.setInvoiceList(vistDummy);
							billingInvoiceAsPaids.add(billingInvoiceDto);
						} else {
							billingInvoiceDto.setStatus("Completed");
							billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
							billingInvoiceDto.setMonthNumber(month);
							billingInvoiceDto.setYear(year);
							List<Invoice> vistDummy = new ArrayList<Invoice>();
							vistDummy.add(invoice);
							billingInvoiceDto.setInvoiceList(vistDummy);
							billingInvoiceAsPaids.add(billingInvoiceDto);
						}
					}
				} else {
					if (currentDate.equals(completeDate)) {
						billingInvoiceDto.setStatus("Completed");
						billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(13));
						billingInvoiceDto.setMonthNumber(13);
						billingInvoiceDto.setYear(year);
						List<Invoice> vistDummy = new ArrayList<Invoice>();
						vistDummy.add(invoice);
						billingInvoiceDto.setInvoiceList(vistDummy);
						billingInvoiceAsPaids.add(billingInvoiceDto);
					} else {
						billingInvoiceDto.setStatus("Completed");
						billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
						billingInvoiceDto.setMonthNumber(month);
						billingInvoiceDto.setYear(year);
						List<Invoice> vistDummy = new ArrayList<Invoice>();
						vistDummy.add(invoice);
						billingInvoiceDto.setInvoiceList(vistDummy);
						billingInvoiceAsPaids.add(billingInvoiceDto);
					}
				}
			}

//			for un paid

//			Calendar verifyCurrentDate = new GregorianCalendar();
//			verifyCurrentDate.add(Calendar.MINUTE, -(timezone.intValue()));
//			
//			Calendar verifyVisitDate = new GregorianCalendar();
//
//			verifyVisitDate.setTime(Date.from(invoice.getCurrentDate()));
//			verifyVisitDate.add(Calendar.MINUTE, -(timezone.intValue()));

			List<Invoice> billingUnpaidData = invoiceRealtedToJob.stream()
					.filter(visit -> !visit.getStatus().contains("Completed")).collect(Collectors.toList());
			for (Invoice invoice : billingUnpaidData) {
				BillingInvoiceDto billingInvoiceDto = new BillingInvoiceDto();

				Calendar currentDate = new GregorianCalendar();
				Calendar visitDate = new GregorianCalendar();
				Calendar completeDate = new GregorianCalendar();
				currentDate.setTime(new Date());
				currentDate.add(Calendar.MINUTE, -(timezone.intValue()));
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);
				currentDate.set(Calendar.SECOND, 0);
				currentDate.set(Calendar.MILLISECOND, 0);

				visitDate.setTime(Date.from(invoice.getCreatedTs()));
				visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
				visitDate.set(Calendar.HOUR_OF_DAY, 0);
				visitDate.set(Calendar.MINUTE, 0);
				visitDate.set(Calendar.SECOND, 0);
				visitDate.set(Calendar.MILLISECOND, 0);

//				completeDate.setTime(Date.from(invoice.getLastPayDate()));
//				completeDate.set(Calendar.HOUR_OF_DAY, 0);
//				completeDate.set(Calendar.MINUTE, 0);
//				completeDate.set(Calendar.SECOND, 0);
//				completeDate.set(Calendar.MILLISECOND, 0);

				Integer month = visitDate.get(Calendar.MONTH);
				Integer year = visitDate.get(Calendar.YEAR);
				if (billingInvoiceAsPaids.size() > 0) {
					Optional<BillingInvoiceDto> optional = billingInvoiceAsPaids.stream()
							.filter(x -> x.getMonthNumber().equals(month) && x.getYear().equals(year)).findFirst();

					if (optional.isPresent()) {
						for (BillingInvoiceDto billingInvoiceAsPaid : billingInvoiceAsPaids) {
							if (billingInvoiceAsPaid.getMonthNumber().equals(month)
									&& billingInvoiceAsPaid.getYear().equals(year)) {
								List<Invoice> invoiceDummy = new ArrayList<Invoice>();
								invoiceDummy.addAll(billingInvoiceAsPaid.getInvoiceList());
								invoiceDummy.add(invoice);
								billingInvoiceAsPaid.setInvoiceList(invoiceDummy);
							}
						}
					}

					if (optional.isEmpty()) {
						billingInvoiceDto.setStatus(invoice.getStatus());
						billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
						billingInvoiceDto.setMonthNumber(month);
						billingInvoiceDto.setYear(year);
						List<Invoice> vistDummy = new ArrayList<Invoice>();
						vistDummy.add(invoice);
						billingInvoiceDto.setInvoiceList(vistDummy);
						billingInvoiceAsUnPaids.add(billingInvoiceDto);
					}

				} else {
//					if (currentDate.equals(completeDate)) {
//						billingInvoiceDto.setStatus(invoice.getStatus());
//						billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(13));
//						billingInvoiceDto.setMonthNumber(13);
//						billingInvoiceDto.setYear(year);
//						List<Invoice> vistDummy = new ArrayList<Invoice>();
//						vistDummy.add(invoice);
//						billingInvoiceDto.setInvoiceList(vistDummy);
//						billingInvoiceAsPaids.add(billingInvoiceDto);
//					}else {
					billingInvoiceDto.setStatus(invoice.getStatus());
					billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
					billingInvoiceDto.setMonthNumber(month);
					billingInvoiceDto.setYear(year);
					List<Invoice> vistDummy = new ArrayList<Invoice>();
					vistDummy.add(invoice);
					billingInvoiceDto.setInvoiceList(vistDummy);
					billingInvoiceAsUnPaids.add(billingInvoiceDto);
//					}
				}
			}
		}
		finalBilling.addAll(billingInvoiceAsPaids);
		finalBilling.addAll(billingInvoiceAsUnPaids);
		return finalBilling;
	}

	@Override
	public List<BillingInvoiceDto> findInvoiceSchedule(Long jobId, Long timezone) {
		List<InvoiceSchedularDate> reminderData = new ArrayList<InvoiceSchedularDate>();
		reminderData = invoiceSchedularDateRepo.findInvoiceSchedule(jobId);
		List<BillingInvoiceDto> billingInvoiceDtos = new ArrayList<BillingInvoiceDto>();
		for (InvoiceSchedularDate obj : reminderData) {
			BillingInvoiceDto billingInvoiceDto = new BillingInvoiceDto();

			Calendar currentDate = new GregorianCalendar();
			Calendar visitDate = new GregorianCalendar();
			Calendar completeDate = new GregorianCalendar();
			currentDate.setTime(new Date());
			currentDate.add(Calendar.MINUTE, -(timezone.intValue()));
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			currentDate.set(Calendar.MILLISECOND, 0);

			visitDate.setTime(Date.from(obj.getInvStartDate()));
			visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
			visitDate.set(Calendar.HOUR_OF_DAY, 0);
			visitDate.set(Calendar.MINUTE, 0);
			visitDate.set(Calendar.SECOND, 0);
			visitDate.set(Calendar.MILLISECOND, 0);

			Integer month = visitDate.get(Calendar.MONTH);
			Integer year = visitDate.get(Calendar.YEAR);

			if (billingInvoiceDtos.size() > 0) {
				Optional<BillingInvoiceDto> optional = billingInvoiceDtos.stream()
						.filter(x -> x.getMonthNumber().equals(month) && x.getYear().equals(year)).findFirst();
				if (optional.isPresent()) {
					for (BillingInvoiceDto billingInvoiceAsPaid : billingInvoiceDtos) {
						if (billingInvoiceAsPaid.getMonthNumber().equals(month)
								&& billingInvoiceAsPaid.getYear().equals(year)) {
							List<InvoiceSchedularDate> invoiceDummy = new ArrayList<InvoiceSchedularDate>();
							invoiceDummy.addAll(billingInvoiceAsPaid.getInvoiceScheduleList());
							invoiceDummy.add(obj);
							billingInvoiceAsPaid.setInvoiceScheduleList(invoiceDummy);
						}
					}
				}

				if (optional.isEmpty()) {
					if (currentDate.getTime().toInstant().isAfter(obj.getInvStartDate())) {
						visitDate.setTime(Date.from(obj.getInvStartDate()));
						visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
						visitDate.set(Calendar.HOUR_OF_DAY, 0);
						visitDate.set(Calendar.MINUTE, 0);
						visitDate.set(Calendar.SECOND, 0);
						visitDate.set(Calendar.MILLISECOND, 0);

						billingInvoiceDto.setStatus("Overdue");
						billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
						billingInvoiceDto.setMonthNumber(month);
						billingInvoiceDto.setYear(year);
						List<InvoiceSchedularDate> vistDummy = new ArrayList<InvoiceSchedularDate>();
						vistDummy.add(obj);
						billingInvoiceDto.setInvoiceScheduleList(vistDummy);
					} else if (currentDate.getTime().toInstant().isBefore(obj.getInvStartDate())) {
						visitDate.setTime(Date.from(obj.getInvStartDate()));
						visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
						visitDate.set(Calendar.HOUR_OF_DAY, 0);
						visitDate.set(Calendar.MINUTE, 0);
						visitDate.set(Calendar.SECOND, 0);
						visitDate.set(Calendar.MILLISECOND, 0);

						billingInvoiceDto.setStatus("pending");
						billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
						billingInvoiceDto.setMonthNumber(month);
						billingInvoiceDto.setYear(year);
						List<InvoiceSchedularDate> vistDummy = new ArrayList<InvoiceSchedularDate>();
						vistDummy.add(obj);
						billingInvoiceDto.setInvoiceScheduleList(vistDummy);
					} else if (obj.getInvoiceDate() != null) {
						visitDate.setTime(Date.from(obj.getInvStartDate()));
						visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
						visitDate.set(Calendar.HOUR_OF_DAY, 0);
						visitDate.set(Calendar.MINUTE, 0);
						visitDate.set(Calendar.SECOND, 0);
						visitDate.set(Calendar.MILLISECOND, 0);

						billingInvoiceDto.setStatus("complete");
						billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
						billingInvoiceDto.setMonthNumber(month);
						billingInvoiceDto.setYear(year);
						List<InvoiceSchedularDate> vistDummy = new ArrayList<InvoiceSchedularDate>();
						vistDummy.add(obj);
						billingInvoiceDto.setInvoiceScheduleList(vistDummy);
					}
					billingInvoiceDtos.add(billingInvoiceDto);
				}
			} else {
				if (currentDate.getTime().toInstant().isAfter(obj.getInvStartDate())) {
					visitDate.setTime(Date.from(obj.getInvStartDate()));
					visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
					visitDate.set(Calendar.HOUR_OF_DAY, 0);
					visitDate.set(Calendar.MINUTE, 0);
					visitDate.set(Calendar.SECOND, 0);
					visitDate.set(Calendar.MILLISECOND, 0);

					billingInvoiceDto.setStatus("Overdue");
					billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
					billingInvoiceDto.setMonthNumber(month);
					billingInvoiceDto.setYear(year);
					List<InvoiceSchedularDate> vistDummy = new ArrayList<InvoiceSchedularDate>();
					vistDummy.add(obj);
					billingInvoiceDto.setInvoiceScheduleList(vistDummy);
				} else if (currentDate.getTime().toInstant().isBefore(obj.getInvStartDate())) {
					visitDate.setTime(Date.from(obj.getInvStartDate()));
					visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
					visitDate.set(Calendar.HOUR_OF_DAY, 0);
					visitDate.set(Calendar.MINUTE, 0);
					visitDate.set(Calendar.SECOND, 0);
					visitDate.set(Calendar.MILLISECOND, 0);

					billingInvoiceDto.setStatus("pending");
					billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
					billingInvoiceDto.setMonthNumber(month);
					billingInvoiceDto.setYear(year);
					List<InvoiceSchedularDate> vistDummy = new ArrayList<InvoiceSchedularDate>();
					vistDummy.add(obj);
					billingInvoiceDto.setInvoiceScheduleList(vistDummy);
				} else if (obj.getInvoiceDate() != null) {
					visitDate.setTime(Date.from(obj.getInvStartDate()));
					visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
					visitDate.set(Calendar.HOUR_OF_DAY, 0);
					visitDate.set(Calendar.MINUTE, 0);
					visitDate.set(Calendar.SECOND, 0);
					visitDate.set(Calendar.MILLISECOND, 0);

					billingInvoiceDto.setStatus("complete");
					billingInvoiceDto.setMonth(DateServiceUtil.getMonthName(month));
					billingInvoiceDto.setMonthNumber(month);
					billingInvoiceDto.setYear(year);
					List<InvoiceSchedularDate> vistDummy = new ArrayList<InvoiceSchedularDate>();
					vistDummy.add(obj);
					billingInvoiceDto.setInvoiceScheduleList(vistDummy);
				}
				billingInvoiceDtos.add(billingInvoiceDto);
			}

		}
		return billingInvoiceDtos;
	}

	@Override
	public List<Invoice> findAllInvoiceWithFilter(String startDate, String endDate, String type) {
		List<Invoice> invoiceCurrentList = invoiceRepository.findFilteredData(startDate, endDate, COMPLETED);

		switch (type.toLowerCase()) {
		case COMPLETED:
			invoiceCurrentList = invoiceCurrentList.stream().filter(x -> !x.getIsDeposit())
					.collect(Collectors.toList());
			break;
		case DEPOSIT:
			invoiceCurrentList = invoiceCurrentList.stream().filter(x -> x.getIsDeposit()).collect(Collectors.toList());
			break;
		case REFUND:
			invoiceCurrentList = invoiceRepository.findFilteredData(startDate, endDate, REFUND);
			break;
		default:
			break;
		}

		return invoiceCurrentList;
	}

	@Override
	public List<TransactionReportDto> filterAllDataMapp(List<Invoice> iteratorTransactionList,
			JobVisitReportFilterDto filterDto) {
		List<TransactionReportDto> transactionReportDtos = new ArrayList<TransactionReportDto>();

		try {
			for (Invoice iteratorTransaction : iteratorTransactionList) {

				if (iteratorTransaction.getId() == 115) {
					logger.debug("what is the invoice Id -> " + iteratorTransaction.getId());
				}
				TransactionReportDto transactionReportDto = new TransactionReportDto();
				User user = userRepo.findByUserId(iteratorTransaction.getUserId().getUserId());
				Optional<Quote> quote = null;
				if (iteratorTransaction.getJobId().getQuoteId() != null) {
					quote = quoteRepository.findById(iteratorTransaction.getJobId().getQuoteId());
				}
				transactionReportDto = transactionReportDto.mapData(iteratorTransaction, filterDto, user, quote);
				transactionReportDtos.add(transactionReportDto);
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			logger.debug(e.getCause().toString());
		}

		return transactionReportDtos;
	}

	@Override
	public void exportReportToEmail(List<TransactionReportDto> transactionReportDtos, JobVisitReportFilterDto filterDto,
			Integer timezone) {
		try {
			String emailContent = "<html>\r\n" + "<head>\r\n" + "    <meta charset=\"utf-8\">\r\n" + "    <h3>Hello "
					+ filterDto.getUserName() + ",</h3>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
					+ "<p>As per your request, PFA Transaction report.</p>\r\n" + "<p>Sincerely,</p>\r\n"
					+ "<p>Checksammy Team</p>\r\n" + "</body>\r\n" + "</html>";
//					+ "<p>Attachment - Excel sheet with either of two options selected by user from the field 'Receive Excel Copy'.</p>\r\n"
			createXLX(transactionReportDtos, filterDto.getColumnArray(), timezone);
			emailService.sendTransactionAttachmentEmail(filterDto.getToEmail(), emailContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createXLX(List<TransactionReportDto> jobVisitReportDtos, int[] columnArray, Integer timezone)
			throws IOException {
		File dir = new File(GlobalValues.TRANSACTION_REPORT_FILE_PATH + File.separator + "files");
		logger.info("Directory Name: " + dir.getAbsolutePath());
		if (!dir.exists())
			dir.mkdirs();
		logger.info("Directory created.");
		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Transaction Report");

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
				headerCell.setCellValue("Type");
				headerCell.setCellStyle(headerStyle);
				break;
			case 5:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Paid With");
				headerCell.setCellStyle(headerStyle);
				break;
			case 6:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Paid Through");
				headerCell.setCellStyle(headerStyle);
				break;
			case 7:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Total $");
				headerCell.setCellStyle(headerStyle);
				break;
			case 8:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Fee $");
				headerCell.setCellStyle(headerStyle);
				break;
			case 9:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Note");
				headerCell.setCellStyle(headerStyle);
				break;
			case 10:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Card Ending #");
				headerCell.setCellStyle(headerStyle);
				break;
			case 11:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Card Type");
				headerCell.setCellStyle(headerStyle);
				break;
			case 12:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Invoice #");
				headerCell.setCellStyle(headerStyle);
				break;
			case 13:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Quote #");
				headerCell.setCellStyle(headerStyle);
				break;
			case 14:
				headerCell = header.createCell(i);
				headerCell.setCellValue("Payout #");
				headerCell.setCellStyle(headerStyle);
				break;
			case 15:
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
			List<TransactionReportDto> jobVisitReportDto = new ArrayList<TransactionReportDto>();
			jobVisitReportDto.add(jobVisitReportDtos.get(i));
			for (int j = 0; j <= jobVisitReportDto.size(); j++) {
				for (int k = 0; k <= columnArray.length - 1; k++) {
					switch (columnArray[k]) {
					case 1:
						// String date = getDateInFormat(jobVisitReportDtos.get(i).getDate());
						try {
							Calendar startDate1 = new GregorianCalendar();
							startDate1.setTime(Date.from(jobVisitReportDtos.get(i).getDate()));
							String formattedDate1 = "MM/dd/yyyy";
							startDate1.add(Calendar.MINUTE, -(timezone));
							DateFormat dateFormat1 = new SimpleDateFormat(formattedDate1);
							String formattedDate2 = dateFormat1.format(startDate1.getTime());
							cell = row.createCell(k);
							cell.setCellValue(formattedDate2);
							cell.setCellStyle(style);
						} catch (Exception e) {
							cell = row.createCell(k);
							cell.setCellValue("");
							cell.setCellStyle(style);
						}

						break;
					case 2:
						// String time =
						// getTimeFromDate(jobVisitReportDtos.get(i).getDate().toString());
						try {
							String formattedDate = "";
							String strDateFormat = "hh:mm a";

							Calendar time = new GregorianCalendar();

							time.setTime(Date.from(jobVisitReportDtos.get(i).getTime()));
							time.add(Calendar.MINUTE, -(timezone));

							DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
							// formattedDate = dateFormat.format(time.getTime());
							cell = row.createCell(k);
							cell.setCellValue(dateFormat.format(time.getTime()));
							cell.setCellStyle(style);
						} catch (Exception e) {
							cell = row.createCell(k);
							cell.setCellValue("");
							cell.setCellStyle(style);
						}

						break;
					case 3:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getClinetName());
						cell.setCellStyle(style);
						break;
					case 4:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getType());
						cell.setCellStyle(style);
						break;
					case 5:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getPaidWith());
						cell.setCellStyle(style);
						break;
					case 6:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getPaidThrough());
						cell.setCellStyle(style);
						break;
					case 7:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getTotal());
						cell.setCellStyle(style);
						break;
					case 8:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getFee());
						cell.setCellStyle(style);
						break;
					case 9:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getNote());
						cell.setCellStyle(style);
						break;
					case 10:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getCardEnding());
						cell.setCellStyle(style);
						break;
					case 11:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getCardType());
						cell.setCellStyle(style);
						break;
					case 12:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getInvoice());
						cell.setCellStyle(style);
						break;
					case 13:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getQuote());
						cell.setCellStyle(style);
						break;
					case 14:
						cell = row.createCell(k);
						cell.setCellValue(jobVisitReportDtos.get(i).getPayout());
						cell.setCellStyle(style);
						break;
					case 15:
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
		FileOutputStream outputStream = new FileOutputStream(dir + File.separator + "Transaction Report.xls");
		workbook.write(outputStream);
		workbook.close();
	}

	@Override
	public Optional<Invoice> findInvoiceById(Long invoiceId) {

		return invoiceRepository.findById(invoiceId);
	}

}
