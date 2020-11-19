package com.checksammy.loca.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.dto.CollectionWithFromToTodate;
import com.checksammy.loca.dto.FilterDataClassDto;
import com.checksammy.loca.dto.GoodsCollectionReportWithFromAndTodate;
import com.checksammy.loca.dto.JobVisitReportDto;
import com.checksammy.loca.dto.JobVisitReportFilterDto;
import com.checksammy.loca.dto.NewCollectionReportListDto;
import com.checksammy.loca.dto.TransactionReportDto;
import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.VisitSchedulerDate;
import com.checksammy.loca.service.BinHistoryViewWebService;
import com.checksammy.loca.service.InvoiceService;
import com.checksammy.loca.service.JobService;
import com.checksammy.loca.service.ReportService;
import com.checksammy.loca.service.VisitSchedulerDateService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/report")
public class ReportController {

	@Autowired
	BinHistoryViewWebService binHistoryViewWebService;

	@Autowired
	ReportService reportService;

	@Autowired
	private JobService jobService;

	@Autowired
	private VisitSchedulerDateService visitService;

	@Autowired
	private InvoiceService invoiceService;

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@PostMapping(value = "/export")
	public ResponseEntity<ServiceResponse> generateReport(@RequestBody CollectionWithFromToTodate binCollectionReport) {
		logger.debug("call method -- generateReport()");

		ServiceResponse response = new ServiceResponse();
		try {
			String fileName = reportService.generateReport(binCollectionReport);
			response.setReturnObject(fileName);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/exportfile")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@RequestParam String filename) {
		Resource resource = null;
		try {
			resource = reportService.getFile(filename);

		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

//	 for goods collection report 29-JAN-2020
	@PostMapping(value = "/goods/collection/export")
	public ResponseEntity<ServiceResponse> generateGoodsCollectionReport(
			@RequestBody GoodsCollectionReportWithFromAndTodate goodsCollectionReport) {
		logger.debug("call method -- generateReport()");

		ServiceResponse response = new ServiceResponse();
		try {
			String fileName = reportService.generateGoodsCollectionReport(goodsCollectionReport);
			response.setReturnObject(fileName);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * TODO: working on new bin management report NEW IMPLEMENT new Bin management
	 * report
	 */
	@PostMapping(value = "/new/export")
	public ResponseEntity<ServiceResponse> newGenerateReport(
			@RequestBody List<NewCollectionReportListDto> newBinManagementReportDto) {
		logger.debug("call method -- generateReport()");

		ServiceResponse response = new ServiceResponse();
		try {
			String fileName = reportService.newlyGenerateReport(newBinManagementReportDto);
			response.setReturnObject(fileName);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

//	 for goods collection report 29-JAN-2020
	@PostMapping(value = "/new/goods/collection/export")
	public ResponseEntity<ServiceResponse> newGoodsCollectionReport(
			@RequestBody List<NewCollectionReportListDto> newGoodsReportDto) {
		logger.debug("call method -- generateReport()");

		ServiceResponse response = new ServiceResponse();
		try {
			String fileName = reportService.newlyGoodsCollectionReport(newGoodsReportDto);
			response.setReturnObject(fileName);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

//	 From server End 20-07-2020 ----------------------------------------------------------------
	@PostMapping(value = "/filter/good/collection/report/{tmZone}/{export}")
	public ResponseEntity<ServiceResponse> filterGoodCollectionReportData(@PathVariable("tmZone") Integer tmZone,
			@PathVariable("export") Boolean export, @RequestBody FilterDataClassDto dataFilterDto) {
		logger.debug("call method -- generateReport()");

		ServiceResponse response = new ServiceResponse();
		try {
			if (export) {
				String fileName = reportService.filterGoodCollectionExport(dataFilterDto, tmZone, export);
				response.setReturnObject(fileName);
			} else {
				List<NewCollectionReportListDto> fileName = reportService.filterGoodCollectionReportData(dataFilterDto,
						tmZone);
				response.setReturnObject(fileName);
			}
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/filter/collection/report/{tmZone}/{export}")
	public ResponseEntity<ServiceResponse> filterCollectionReportData(@PathVariable("tmZone") Integer tmZone,
			@PathVariable("export") Boolean export, @RequestBody FilterDataClassDto dataFilterDto) {
		logger.debug("call method -- filterCollectionReportData()");

		ServiceResponse response = new ServiceResponse();
		try {
			if (export) {
				String fileName = reportService.filterCollectionExport(dataFilterDto, tmZone, export);
				response.setReturnObject(fileName);
			} else {
				List<NewCollectionReportListDto> fileName = reportService.filterCollectionReportData(dataFilterDto,
						tmZone);
				response.setReturnObject(fileName);
			}

			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	// Get visit report By Id
	@PostMapping("visit/report")
	public ResponseEntity<ServiceResponse> getVisitReportById(@RequestBody JobVisitReportFilterDto filterDto) {
		ServiceResponse response = new ServiceResponse();
		long currJobId = 0;
		long prevJobId = 0;
		List<JobVisitReportDto> jobVisitReportDtos = new ArrayList<JobVisitReportDto>();
		JobVisitReportDto jobVisitReportDto = new JobVisitReportDto();
		try {

			if (filterDto.getVisitWithin() != null || filterDto.getVisitWithin().length() != 0) {
				List<VisitSchedulerDate> jobReportData = visitService.getJobReportByVisitWithin(
						getVisitWithin(filterDto.getVisitWithin(), "start", filterDto),
						getVisitWithin(filterDto.getVisitWithin(), "end", filterDto));
				for (VisitSchedulerDate data : jobReportData) {

					logger.debug("visit Ids -----> " + data.getId());
					if (data.getId() == 471) {
						logger.debug("lets find");
					}
					currJobId = data.getJobId();
					if (currJobId != prevJobId) {
						jobVisitReportDto = jobService.getJobDataForReport(currJobId, filterDto.getAssignTo(),
								filterDto.getLineItem());

					}
					jobVisitReportDto.setDate(data.getCurrentDate());
					jobVisitReportDto.setTime(data.getCurrentDate());

					if (jobVisitReportDto.getAssignTo() == null && filterDto.getAssignTo().length() == 0
							&& filterDto.getLineItem().length() == 0) {
						jobVisitReportDtos.add(jobVisitReportDto);
					} else if (jobVisitReportDto.getLineItems() == null && filterDto.getLineItem().length() == 0
							&& filterDto.getAssignTo().length() == 0) {
						jobVisitReportDtos.add(jobVisitReportDto);
					} else if (jobVisitReportDto.getAssignTo() != null && filterDto.getLineItem().length() == 0) {
						jobVisitReportDtos.add(jobVisitReportDto);
					} else if (jobVisitReportDto.getLineItems() != null && filterDto.getAssignTo().length() == 0) {
						jobVisitReportDtos.add(jobVisitReportDto);
					} else if (jobVisitReportDto.getAssignTo() != null && jobVisitReportDto.getLineItems() != null) {
						jobVisitReportDtos.add(jobVisitReportDto);
					}
					prevJobId = currJobId;
				}
			}
			response.setReturnObject(jobVisitReportDtos);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			// createXl();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	private String getVisitWithin(String param, String type, JobVisitReportFilterDto filterDto) {
		String startDate, endDate;
		if (param.toLowerCase().equalsIgnoreCase("this week")) {
			if (type.toLowerCase().equalsIgnoreCase("start")) {
				return startDate = getWeekStartDate();
			}
			if (type.toLowerCase().equalsIgnoreCase("end")) {
				return endDate = getWeekEndDate();
			}
		}
		if (param.toLowerCase().equalsIgnoreCase("this month")) {
			if (type.toLowerCase().equalsIgnoreCase("start")) {
				return startDate = getCurrentMonthDate(type);
			}
			if (type.toLowerCase().equalsIgnoreCase("end")) {
				return endDate = getCurrentMonthDate(type);
			}
		}
		if (param.toLowerCase().equalsIgnoreCase("last 12 months")) {
			if (type.toLowerCase().equalsIgnoreCase("start")) {
				GregorianCalendar calendarExpected = new GregorianCalendar();
				SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate.setCalendar(calendarExpected);
				calendarExpected.add(GregorianCalendar.DATE, -365);
				logger.debug("StartDate :: " + formattedDate.format(calendarExpected.getTime()));
				return startDate = formattedDate.format(calendarExpected.getTime());
			}
			if (type.toLowerCase().equalsIgnoreCase("end")) {
				GregorianCalendar calendarExpected = new GregorianCalendar();
				SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate.setCalendar(calendarExpected);
				// calendarExpected.add(GregorianCalendar.DATE, -365);
				logger.debug("EndDate :: " + formattedDate.format(calendarExpected.getTime()));
				return endDate = formattedDate.format(calendarExpected.getTime());
			}
			// System.out.println("Last 12 Months :: " +calendarExpected.getTime());
		}
		if (param.toLowerCase().equalsIgnoreCase("last 30 days")) {
			if (type.toLowerCase().equalsIgnoreCase("start")) {
				GregorianCalendar calendarExpected = new GregorianCalendar();
				SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate.setCalendar(calendarExpected);
				calendarExpected.add(GregorianCalendar.DATE, -30);
				logger.debug("StartDate :: " + formattedDate.format(calendarExpected.getTime()));
				return startDate = formattedDate.format(calendarExpected.getTime());
			}
			if (type.toLowerCase().equalsIgnoreCase("end")) {
				GregorianCalendar calendarExpected = new GregorianCalendar();
				SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate.setCalendar(calendarExpected);
				// calendarExpected.add(GregorianCalendar.DATE, -30);
				logger.debug("EndDate :: " + formattedDate.format(calendarExpected.getTime()));
				return endDate = formattedDate.format(calendarExpected.getTime());
			}
		}
		if (param.toLowerCase().equalsIgnoreCase("this calender year")) {
			if (type.toLowerCase().equalsIgnoreCase("start")) {
				return startDate = getCurrentYear(type);
			}
			if (type.toLowerCase().equalsIgnoreCase("end")) {
				return endDate = getCurrentYear(type);
			}
		}

		if (param.toLowerCase().equalsIgnoreCase("custom range")) {
			if (type.toLowerCase().equalsIgnoreCase("start")) {
				String[] splitDate = filterDto.getCustStartDate().split("-");
				GregorianCalendar calendarExpected = new GregorianCalendar(Integer.parseInt(splitDate[0]),
						Integer.parseInt(splitDate[1]) - 1, Integer.parseInt(splitDate[2]));
				SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate.setCalendar(calendarExpected);
				logger.debug("StartDate :: " + formattedDate.format(calendarExpected.getTime()));
				return startDate = formattedDate.format(calendarExpected.getTime());
			}
			if (type.toLowerCase().equalsIgnoreCase("end")) {
				String[] splitDate = filterDto.getCustEndDate().split("-");
				GregorianCalendar calendarExpected = new GregorianCalendar(Integer.parseInt(splitDate[0]),
						Integer.parseInt(splitDate[1]) - 1, Integer.parseInt(splitDate[2]));
				SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate.setCalendar(calendarExpected);
				logger.debug("StartDate :: " + formattedDate.format(calendarExpected.getTime()));
				return endDate = formattedDate.format(calendarExpected.getTime());
			}
		}

		return null;
	}

	public static String getWeekStartDate() {
		GregorianCalendar calendarExpected = new GregorianCalendar();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate.setCalendar(calendarExpected);
		while (calendarExpected.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendarExpected.add(Calendar.DATE, -1);
		}
		logger.debug("StartDate :: " + formattedDate.format(calendarExpected.getTime()));
		return formattedDate.format(calendarExpected.getTime());
	}

	public static String getWeekEndDate() {
		GregorianCalendar calendarExpected = new GregorianCalendar();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate.setCalendar(calendarExpected);
		while (calendarExpected.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendarExpected.add(Calendar.DATE, -1);
		}
		calendarExpected.add(Calendar.DATE, 6);
		logger.debug("EndDate :: " + formattedDate.format(calendarExpected.getTime()));
		return formattedDate.format(calendarExpected.getTime());
	}

	public static String getCurrentMonthDate(String type) {
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate.setCalendar(cal);
		if (type.toLowerCase().equalsIgnoreCase("start")) {
			cal.add(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date monthFirstDay = cal.getTime();
			logger.debug("StartDate :: " + formattedDate.format(monthFirstDay));
			return formattedDate.format(monthFirstDay);
		}
		if (type.toLowerCase().equalsIgnoreCase("end")) {
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date monthLastDay = cal.getTime();
			logger.debug("EndDate :: " + formattedDate.format(monthLastDay));
			return formattedDate.format(monthLastDay);
		}
		return null;
	}

	public static String getCurrentYear(String type) {
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate.setCalendar(cal);
		if (type.toLowerCase().equalsIgnoreCase("start")) {
			cal.set(Calendar.DAY_OF_YEAR, 1);
			Date yearStartDate = cal.getTime();
			logger.debug("StartDate :: " + formattedDate.format(yearStartDate));
			return formattedDate.format(yearStartDate);
		}
		if (type.toLowerCase().equalsIgnoreCase("end")) {
			cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
			Date yearEndDate = cal.getTime();
			logger.debug("EndDate :: " + formattedDate.format(yearEndDate));
			return formattedDate.format(yearEndDate);
		}
		return null;
	}

	@PostMapping("visit/report/email/{timezone}")
	private ResponseEntity<ServiceResponse> emailReport(@RequestBody JobVisitReportFilterDto filterDto,
			@PathVariable Integer timezone) throws IOException {
		ServiceResponse response = new ServiceResponse();
		long currJobId = 0;
		long prevJobId = 0;
		List<JobVisitReportDto> jobVisitReportDtos = new ArrayList<JobVisitReportDto>();
		JobVisitReportDto jobVisitReportDto = new JobVisitReportDto();
		try {

			if (filterDto.getVisitWithin() != null || filterDto.getVisitWithin().length() != 0) {
				List<VisitSchedulerDate> jobReportData = visitService.getJobReportByVisitWithin(
						getVisitWithin(filterDto.getVisitWithin(), "start", filterDto),
						getVisitWithin(filterDto.getVisitWithin(), "end", filterDto));
				for (VisitSchedulerDate data : jobReportData) {
					currJobId = data.getJobId();
					logger.debug("visit Ids -----> " + data.getId());
					if (currJobId != prevJobId) {
						jobVisitReportDto = jobService.getJobDataForReport(currJobId, filterDto.getAssignTo(),
								filterDto.getLineItem());

					}
					jobVisitReportDto.setDate(data.getCurrentDate());
					jobVisitReportDto.setTime(data.getCurrentDate());
					jobVisitReportDtos.add(jobVisitReportDto);
					prevJobId = currJobId;
				}
			}
			jobService.sendEmail(jobVisitReportDtos, filterDto, timezone);
			// response.setReturnObject(jobVisitReportDtos);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			// createXl();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * TRANSACTION REPORT
	 * 
	 * @param filterDto
	 * @param timezone
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/transaction/report")
	private ResponseEntity<ServiceResponse> transactionReport(@RequestBody JobVisitReportFilterDto filterDto)
			throws IOException {
		ServiceResponse response = new ServiceResponse();
		List<TransactionReportDto> transactionReportDtos = new ArrayList<TransactionReportDto>();

		try {
			if (filterDto.getVisitWithin() != null || filterDto.getVisitWithin().length() != 0) {
				List<Invoice> transactionReportData = invoiceService.findAllInvoiceWithFilter(
						getVisitWithin(filterDto.getVisitWithin(), "start", filterDto),
						getVisitWithin(filterDto.getVisitWithin(), "end", filterDto), filterDto.getType());

				transactionReportDtos = invoiceService.filterAllDataMapp(transactionReportData, filterDto);
			}
//			jobService.sendEmail(jobVisitReportDtos, filterDto, timezone);
			response.setReturnObject(transactionReportDtos);
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
	 * Export Transaction Report
	 * 
	 * @param filterDto
	 * @param timezone
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/export/transaction/report/{timezone}")
	private ResponseEntity<ServiceResponse> transactionReportExportEmail(@RequestBody JobVisitReportFilterDto filterDto,
			@PathVariable Integer timezone) throws IOException {
		ServiceResponse response = new ServiceResponse();
		List<TransactionReportDto> transactionReportDtos = new ArrayList<TransactionReportDto>();

		try {
			if (filterDto.getVisitWithin() != null || filterDto.getVisitWithin().length() != 0) {
				List<Invoice> transactionReportData = invoiceService.findAllInvoiceWithFilter(
						getVisitWithin(filterDto.getVisitWithin(), "start", filterDto),
						getVisitWithin(filterDto.getVisitWithin(), "end", filterDto), filterDto.getType());

				transactionReportDtos = invoiceService.filterAllDataMapp(transactionReportData, filterDto);
			}
			invoiceService.exportReportToEmail(transactionReportDtos, filterDto, timezone);
			response.setReturnObject(transactionReportDtos);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* EXPORT */
//	@PostMapping(value = "/filter/good/collection/export/{tmZone}/{export}")
//	public ResponseEntity<ServiceResponse> filterGoodCollectionExport(@PathVariable("tmZone") Integer tmZone,
//			@PathVariable("export") Boolean export,	@RequestBody List<NewCollectionReportListDto> exportData) {
//		logger.debug("call method -- filterGoodCollectionExport()");
//
//		ServiceResponse response = new ServiceResponse();
//		try {
//			String fileName = reportService.filterGoodCollectionExport(exportData);
//			response.setReturnObject(fileName);
//			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e.fillInStackTrace());
//			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
//			response.setReturnObject(e.getMessage());
//		}
//		return ResponseEntity.ok().body(response);
//	}
////
//	@PostMapping(value = "/filter/collection/export")
//	public ResponseEntity<ServiceResponse> filterCollectionExport(
//			@RequestBody List<NewCollectionReportListDto> exportData) {
//		logger.debug("call method -- generateReport()");
//
//		ServiceResponse response = new ServiceResponse();
//		try {
//			String fileName = reportService.filterCollectionExport(exportData);
//			response.setReturnObject(fileName);
//			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e.fillInStackTrace());
//			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
//			response.setReturnObject(e.getMessage());
//		}
//		return ResponseEntity.ok().body(response);
//	}

}
