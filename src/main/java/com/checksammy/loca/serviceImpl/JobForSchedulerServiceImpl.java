package com.checksammy.loca.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.JobForSchedulerDto;
import com.checksammy.loca.dto.JobInternalNotesDto;
import com.checksammy.loca.model.BinType;
import com.checksammy.loca.model.JobForScheduler;
import com.checksammy.loca.model.JobInternalNotes;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.VisitSchedulerDate;
import com.checksammy.loca.repository.BinTypeRespository;
import com.checksammy.loca.repository.JobForSchedulerRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.repository.VisitSchedulerDateRepository;
import com.checksammy.loca.service.JobForSchedulerService;

@Service
public class JobForSchedulerServiceImpl implements JobForSchedulerService {
	
	private static final Logger logger = LoggerFactory.getLogger(JobForSchedulerServiceImpl.class);

	@Autowired
	private JobForSchedulerRepository jobForSchedulerRepository;

	@Autowired
	private VisitSchedulerDateRepository vistDateRepository;

	@Autowired
	private BinTypeRespository binTypeRespository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<JobForScheduler> findById(Long jobId) {
		return jobForSchedulerRepository.findById(jobId);
	}

	@Override
	public List<JobForSchedulerDto> getJobHistoryList(Long driverId) {
		List<JobForSchedulerDto> jobForSchedulerDtos = new ArrayList<JobForSchedulerDto>();
		List<JobForScheduler> jobForSchedulers = jobForSchedulerRepository.findJobHistoryListByDriverId(driverId);

		for (JobForScheduler jobForScheduler : jobForSchedulers) {
			
			List<JobInternalNotesDto> jobInternalNotesDtos = new ArrayList<JobInternalNotesDto>();
			List<JobInternalNotes> jobInternalNotes = new ArrayList<JobInternalNotes>();
			
			if (jobForScheduler.getJobInternalNotes() != null && jobForScheduler.getJobInternalNotes().size() > 0) {
				 jobInternalNotes = new ArrayList<JobInternalNotes>(jobForScheduler.getJobInternalNotes());
				for (JobInternalNotes jobInternalNote : jobInternalNotes) {
					JobInternalNotesDto jobInternalNotesDto = new JobInternalNotesDto();
					try {
						User realtedUser = userRepository.findByUserId(jobInternalNote.getCreatedBy());
						jobInternalNotesDto = jobInternalNotesDto.addedRealtedUserwithData(realtedUser,
								jobInternalNote);
						jobInternalNotesDtos.add(jobInternalNotesDto);
					} catch (Exception e) {
						logger.debug(e.getCause().getLocalizedMessage());
					}
				}
			}
			
			JobForSchedulerDto jobForSchedulerDto = new JobForSchedulerDto();
			Optional<BinType> binType = binTypeRespository.findById(
					jobForScheduler.getServiceAsBintypeId() != null ? jobForScheduler.getServiceAsBintypeId() : 0L);
			List<VisitSchedulerDate> visitSchedulerDates = vistDateRepository
					.findByJobIdOnlyCompleted(jobForScheduler.getId());
			jobForSchedulerDto = jobForSchedulerDto.setSchedulerIntoData(jobForScheduler, visitSchedulerDates, binType, jobInternalNotesDtos);
			jobForSchedulerDtos.add(jobForSchedulerDto);
		}

		return jobForSchedulerDtos;
	}

}
