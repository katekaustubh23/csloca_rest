package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.dto.JobNotificationActionHistoryDto;
import com.checksammy.loca.model.JobNotificationActionHistory;

public interface JobNotificationActionHistoryService {

	List<JobNotificationActionHistory> saveData(JobNotificationActionHistoryDto jobNotificationActionHistoryDto);

	Boolean disableNotification(Long jobId, Long driverId);

	List<JobNotificationActionHistory> getDataByAdminId(Long adminId);

}
