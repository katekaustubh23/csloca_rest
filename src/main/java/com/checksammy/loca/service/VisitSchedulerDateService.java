package com.checksammy.loca.service;

import java.util.Date;
import java.util.List;

import com.checksammy.loca.dto.FutureVisitMapDto;
import com.checksammy.loca.dto.JobVisitReportDto;
import com.checksammy.loca.dto.VisitDatesDto;
import com.checksammy.loca.dto.VisitScheduleUpdateDto;
import com.checksammy.loca.dto.VisitSchedulerDateDto;
import com.checksammy.loca.model.VisitSchedulerDate;

public interface VisitSchedulerDateService {

	List<VisitSchedulerDate> findByJobId(Long jobId);

	List<VisitSchedulerDate> getJobReportByVisitWithin(String startDate, String endDate);

	List<VisitSchedulerDateDto> filterValidationOnVisitDates(List<VisitSchedulerDate> jobVisitSchedulerDates,
			Long timezone);


	VisitScheduleUpdateDto updateNewVisit(VisitScheduleUpdateDto visitScheduleUpdateDto, Long timezone);

	VisitDatesDto findVisitWithProductMap(Long visitId);

	void deleteByVisitId(Long visitId, Long jobId);

	FutureVisitMapDto updateForFutureVisit(FutureVisitMapDto futureVisit);

	List<VisitSchedulerDateDto> forUpComingVisit(List<VisitSchedulerDate> jobVisitSchedulerDates, Long timezone);

	List<VisitSchedulerDate> findVisitByUpComing(Long id, Date time);

	void updatePaidOn(Long visitId);

	List<VisitSchedulerDate> findByVisitIds(Long long1, List<Long> visitIds);

	void updatePaidOn(List<Long> visitId, Long long1);
}
