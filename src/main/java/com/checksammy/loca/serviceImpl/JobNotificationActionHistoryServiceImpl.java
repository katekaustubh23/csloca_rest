package com.checksammy.loca.serviceImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.JobNotificationActionHistoryDto;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.JobNotificationActionHistory;
import com.checksammy.loca.model.User;
import com.checksammy.loca.repository.JobDriverMappingRepository;
import com.checksammy.loca.repository.JobNotificationActionHistoryRepository;
import com.checksammy.loca.repository.JobRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.service.JobNotificationActionHistoryService;

@Service
public class JobNotificationActionHistoryServiceImpl implements JobNotificationActionHistoryService {
	
	/*Job Driver mapping Status*/
	public static final String ACCEPT = "accept";
	
	public static final String PENDING = "pending";

	@Autowired
	private JobNotificationActionHistoryRepository jobNotificationActionHistoryRepo;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JobDriverMappingRepository jobMappingRepository;

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<JobNotificationActionHistory> saveData(
			JobNotificationActionHistoryDto jobNotificationActionHistoryDto) {
		JobNotificationActionHistory newJobNotificationActionHistory = jobNotificationActionHistoryDto
				.setNotificationData(jobNotificationActionHistoryDto);
		Optional<Job> jobOptional = jobRepository.findById(newJobNotificationActionHistory.getJobId());

		if (!newJobNotificationActionHistory.getAction()) {
			int[] driverIds = null;
			if (jobOptional.get().getAssignMemberIds() != null
					&& !jobOptional.get().getAssignMemberIds().equalsIgnoreCase("")) {
				driverIds = Arrays.asList(jobOptional.get().getAssignMemberIds().split(",")).stream().map(String::trim)
						.mapToInt(Integer::parseInt).toArray();

				List<Integer> result = IntStream.of(driverIds) // IntStream
						.boxed().collect(Collectors.toList());

				result.removeIf(n -> (n.equals(jobNotificationActionHistoryDto.getDriverId().intValue())));
				String joinedList = result.stream().map(String::valueOf).collect(Collectors.joining(","));
				jobRepository.updateAssignUserNewList(jobOptional.get().getId(), joinedList);
				jobMappingRepository.deleteByJobAndDriverId(jobOptional.get().getId(), jobNotificationActionHistoryDto.getDriverId());
			}
		}else {
			jobMappingRepository.updateUserActionStatus(jobOptional.get().getId(), jobNotificationActionHistoryDto.getDriverId(), ACCEPT);
		}

		newJobNotificationActionHistory.setAdminId(jobOptional.get().getCreatedBy());
		newJobNotificationActionHistory.setJobNumber(jobOptional.get().getJobNumber());
		newJobNotificationActionHistory.setCreatedDate(Instant.now());
		if(newJobNotificationActionHistory.getAction()) {
			newJobNotificationActionHistory.setContent("has accepted the job #" + jobOptional.get().getJobNumber()
					+ ". Notifications will be sent to driver prior to start of the job.");
		}else {
			newJobNotificationActionHistory.setContent("has rejected the job #" + jobOptional.get().getJobNumber()
					+ ". This job needs to be reassigned.");
		}
		
		newJobNotificationActionHistory = jobNotificationActionHistoryRepo.save(newJobNotificationActionHistory);
		jobNotificationActionHistoryRepo.updateDriverId(newJobNotificationActionHistory.getId(),
				jobNotificationActionHistoryDto.getDriverId());
		List<JobNotificationActionHistory> jobNotificationActionHistories = jobNotificationActionHistoryRepo
				.findByAdminId(newJobNotificationActionHistory.getAdminId());
		for (JobNotificationActionHistory jobNotificationActionHistory : jobNotificationActionHistories) {
			try {
				if (jobNotificationActionHistory.getDriverId() == null) {
					User user = userRepository.findByUserId(jobNotificationActionHistoryDto.getDriverId());
					jobNotificationActionHistory.setDriverId(user);	
				}	
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
		}
		return jobNotificationActionHistories;
	}

	@Override
	public Boolean disableNotification(Long jobId, Long driverId) {
		Boolean status = false;
		try {
			jobNotificationActionHistoryRepo.updateNotificationAll(jobId, driverId);
			status = true;
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	@Override
	public List<JobNotificationActionHistory> getDataByAdminId(Long adminId) {
		List<JobNotificationActionHistory> jobNotificationActionHistories = new ArrayList<JobNotificationActionHistory>();
		jobNotificationActionHistories = jobNotificationActionHistoryRepo.findByAdminId(adminId);
		return jobNotificationActionHistories;
	}
}
