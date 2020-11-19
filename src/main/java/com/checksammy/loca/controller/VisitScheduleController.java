package com.checksammy.loca.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.dto.FutureVisitMapDto;
import com.checksammy.loca.dto.VisitDatesDto;
import com.checksammy.loca.dto.VisitScheduleUpdateDto;
import com.checksammy.loca.service.JobService;
import com.checksammy.loca.service.VisitSchedulerDateService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/visit")
public class VisitScheduleController {

	private static final Logger logger = LoggerFactory.getLogger(VisitScheduleController.class);

	@Autowired
	private JobService jobService;

	@Autowired
	private VisitSchedulerDateService visitSchedulerDateService;

	/* Save invoice */
	@PostMapping("/update/{timezone}")
	public ResponseEntity<ServiceResponse> updateJobVisit(@RequestBody VisitScheduleUpdateDto visitScheduleUpdateDto,
			@PathVariable("timezone") Long timezone) {
		ServiceResponse response = new ServiceResponse();
		try {
			visitScheduleUpdateDto = visitSchedulerDateService.updateNewVisit(visitScheduleUpdateDto, timezone);
			response.setReturnObject(visitScheduleUpdateDto);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/find/{visitId}")
	public ResponseEntity<ServiceResponse> findByVisitId(@PathVariable("visitId") Long visitId) {
		ServiceResponse response = new ServiceResponse();
		try {
			VisitDatesDto visitSchedulerDate = visitSchedulerDateService.findVisitWithProductMap(visitId);
			response.setReturnObject(visitSchedulerDate);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/delete/{jobId}/{visitId}")
	public ResponseEntity<ServiceResponse> deleteByVisitId(@PathVariable("visitId") Long visitId,
			@PathVariable("jobId") Long jobId) {
		ServiceResponse response = new ServiceResponse();
		try {
			visitSchedulerDateService.deleteByVisitId(visitId, jobId);
			response.setReturnObject("deleted");
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
	@PostMapping("/future/visit")
	public ResponseEntity<ServiceResponse> futureVisitData(@RequestBody FutureVisitMapDto futureVisit) {
		ServiceResponse response = new ServiceResponse();
		try {
			futureVisit = visitSchedulerDateService.updateForFutureVisit(futureVisit);
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
