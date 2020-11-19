package com.checksammy.loca.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.JobRequestDto;
import com.checksammy.loca.dto.SaveRequestDto;
import com.checksammy.loca.model.CsServicesRequest;
import com.checksammy.loca.model.JobRequest;
import com.checksammy.loca.model.JobRequestNotes;
import com.checksammy.loca.model.User;
import com.checksammy.loca.service.CsServicesRequestService;
import com.checksammy.loca.service.JobRequestNotesService;
import com.checksammy.loca.service.JobRequestService;
import com.checksammy.loca.service.LocationService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/request")
public class RequestController {

	private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

	@Autowired
	private JobRequestService jobRequestService;

	@Autowired
	private UserService userService;

	@Autowired
	private JobRequestNotesService jobRequestNoteService;

	@Autowired
	private CsServicesRequestService csServicesRequestService;
	
	@Autowired
	private LocationService locationService;

	/* JOB REQUEST List */
	@GetMapping("/joblist")
	public ResponseEntity<ServiceResponse> getAllRequestList() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<JobRequest> jobRequestList = jobRequestService.getAllList();
			response.setReturnObject(jobRequestList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/save")
	public ResponseEntity<ServiceResponse> saveJobRequest(@RequestBody SaveRequestDto jobRequest) {
		ServiceResponse response = new ServiceResponse();
		try {
			JobRequest newJobRequest = new JobRequest();
			newJobRequest = newJobRequest.insertBindingData2(jobRequest);
			newJobRequest = jobRequestService.saveJobRequest(newJobRequest);
			
			csServicesRequestService.deleteData(newJobRequest);
			jobRequestService.updateUserIdOnRequest(newJobRequest.getId(), jobRequest.getUserId().getUserId());
			List<JobRequestNotes> jobRequestNotesList = new ArrayList<JobRequestNotes>();
			if (newJobRequest.getJobRequestNotes() != null && newJobRequest.getJobRequestNotes().size() > 0) {
				jobRequestNotesList = new ArrayList<JobRequestNotes>(newJobRequest.getJobRequestNotes());
				for (JobRequestNotes jobRequestNote : jobRequestNotesList) {
					User userData = userService.getUserId(jobRequestNote.getCreatedBy());
					jobRequestNote.setJobRequestId(newJobRequest.getId());
					jobRequestNote.setCreatedUserDetails(userData.getFirstName() + " " + userData.getLastName());
					jobRequestNote.setCreatedTs(Instant.now());
				}
				List<JobRequestNotes> jobRequestNotes = jobRequestNoteService.saveJobRequestNote(jobRequestNotesList);
				newJobRequest.setJobRequestNotes(new HashSet<JobRequestNotes>(jobRequestNotes));
				newJobRequest.setUserId(jobRequest.getUserId());
			}
			List<CsServicesRequest> newServiceList = new ArrayList<CsServicesRequest>();
			for (CsServicesRequest csServicesRequest : newJobRequest.getCsServiceList()) {
				csServicesRequest.setRequestId(newJobRequest.getId());
				newServiceList.add(csServicesRequest);
			}
			csServicesRequestService.saveData(newServiceList);
			jobRequestService.updateLocationId(newJobRequest.getId(),jobRequest.getLocationId() );
			newJobRequest.setCsServiceList(new HashSet<CsServicesRequest>(newServiceList));
			response.setReturnObject(newJobRequest);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/job/{requestId}")
	public ResponseEntity<ServiceResponse> getRequest(@PathVariable("requestId") Long requestId) {
		ServiceResponse response = new ServiceResponse();
		try {
			JobRequestDto jobRequest = jobRequestService.getRequestJob(requestId);
			response.setReturnObject(jobRequest);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* DELETE REQUEST Sprint 6 */

	@DeleteMapping("/job/deleted/{requestId}")
	public ResponseEntity<ServiceResponse> deleteRequest(@PathVariable("requestId") Long requestId) {
		ServiceResponse response = new ServiceResponse();
		try {
			jobRequestService.deleteRequest(requestId);
			response.setReturnObject("Request Deleted");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* ADD Attachment */
	@PostMapping(value = "/attachments/{requestID}")
	public ResponseEntity<ServiceResponse> uploadingRequestAttachment(@PathVariable Long requestID,
			@RequestParam("files") MultipartFile[] files) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = jobRequestService.saveRequestAttachments(requestID, files);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* SPRINT 7 delete api for job request notes */
	@DeleteMapping("/delete/notes/{jobReqId}")
	public ResponseEntity<ServiceResponse> deleteJobRequestNote(@PathVariable("jobReqId") Long jobReqId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean status = jobRequestNoteService.deleteRequest(jobReqId);
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

//	change Request status;

	@PutMapping("/change/status/{requestId}/{status}/{type}")
	public ResponseEntity<ServiceResponse> changeStatus(@PathVariable("requestId") Long requestId,
			@PathVariable("status") String status, @PathVariable("type") String type) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean complete = jobRequestService.changeStatusByType(requestId, status, type);
			
			if(complete) {
				response.setReturnObject("updated");
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);	
			}else {
				response.setErrorMessage("update fail, provide proper type");
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
	
	@PostMapping(value = "/save/note/{requestId}")
	public ResponseEntity<ServiceResponse> saveInternalNotes(@PathVariable Long requestId, @RequestBody JobRequestNotes jobRequestNotes) {
		ServiceResponse response = new ServiceResponse();
		try {
			jobRequestNotes.setJobRequestId(requestId);
			jobRequestNotes = jobRequestNoteService.saveRequestNote(jobRequestNotes);
			response.setReturnObject(jobRequestNotes);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setReturnObject(ex.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
}
