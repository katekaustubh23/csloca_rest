package com.checksammy.loca.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.JobRequestDto;
import com.checksammy.loca.model.JobRequest;

public interface JobRequestService {

	List<JobRequest> getAllList();

	JobRequest saveJobRequest(JobRequest jobRequest);

	JobRequestDto getRequestJob(Long requestId);

	void deleteRequest(Long requestId);

	String saveRequestAttachments(Long id, MultipartFile[] files);

	void updateUserIdOnRequest(Long requestId, Long userId);

	Boolean changeStatusByType(Long requestId, String status, String type);

	void updateLocationId(Long id, Long locationId);

}
