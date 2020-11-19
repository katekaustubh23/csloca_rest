package com.checksammy.loca.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.JobDto;
import com.checksammy.loca.dto.JobSendDto;
import com.checksammy.loca.dto.JobVisitReportDto;
import com.checksammy.loca.dto.JobVisitReportFilterDto;
import com.checksammy.loca.dto.SendReminderMailDto;
import com.checksammy.loca.dto.VisitScheduleUpdateDto;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.VisitSchedulerDate;

public interface JobService {

	List<JobSendDto> getAllList();

	Job saveJobAndSchedule(JobDto saveJob, Integer timezone) throws InterruptedException, ExecutionException;

	JobSendDto getJobById(Long jobId);

	Boolean deleteById(Long quotesId);

	Optional<Job> findById(Long jobId);

	Job saveJobAndScheduleDummy(Long jobId);

	List<JobSendDto> getJobListByDriverId(Long driverId);

	List<JobSendDto> getJobListByDriverIdOnDevice(Long driverId);

	Job editJobAndSchedule(JobDto saveJob, Integer timezone);

	Job changeJobStatusAndSendMail(Long jobId, String changeStatus);

	Boolean resendClientReminder(SendReminderMailDto sendReminderMailDto, Long timeZone, MultipartFile[] files);

	Boolean sendFollowUpMail(SendReminderMailDto sendReminderMailDto, Long timeZone, MultipartFile[] files);

	void updateDriverReminderField(Long jobId, String assignUserReminderType);

	void updateSchedulerVisitMark(Long jobScheduleId, Boolean checked, Instant checkDate, Long userId);

	void changeJobRating(Long jobId, String dateString, Long rating);

	JobVisitReportDto getJobDataForReport(long currJobId, String assignTo, String lineItem);

	void sendEmail(List<JobVisitReportDto> jobVisitReportDtos, JobVisitReportFilterDto filterDto, Integer timezone);

	Job updateFieldFromVisitUpdate(VisitScheduleUpdateDto visitScheduleUpdateDto, Long timezone);

	List<Job> getJobListByUserAssign(Long userId);

	void updateSchedulerVisitMarkUncheck(Long visitSchedulerId, Boolean checked, Long userId);

	Job findJobByVisitId(Long visitSchedulerId);


}
