package com.checksammy.loca.serviceImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.FutureVisitMapDto;
import com.checksammy.loca.dto.VisitDatesDto;
import com.checksammy.loca.dto.VisitJobDateAssignUserDto;
import com.checksammy.loca.dto.VisitScheduleUpdateDto;
import com.checksammy.loca.dto.VisitSchedulerDateDto;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.RelatedUser;
import com.checksammy.loca.model.VisitJobProductAndServiceMap;
import com.checksammy.loca.model.VisitSchedulerDate;
import com.checksammy.loca.repository.VisitSchedulerDateRepository;
import com.checksammy.loca.service.JobProductServiceService;
import com.checksammy.loca.service.JobService;
import com.checksammy.loca.service.RelatedUserService;
import com.checksammy.loca.service.VisitJobProductAndServiceMapService;
import com.checksammy.loca.service.VisitSchedulerDateService;
import com.checksammy.loca.utility.DateServiceUtil;
import com.checksammy.loca.utility.EmailSenderUtil;

@Service
public class VisitSchedulerDateServiceImpl implements VisitSchedulerDateService {

	@Autowired
	private VisitSchedulerDateRepository visitSchedulerDateRepo;

	@Autowired
	private JobProductServiceService jobProductServiceService;

	@Autowired
	private VisitJobProductAndServiceMapService visitJobProAndServiceMapService;

	@Autowired
	private EmailSenderUtil emailSenderUtil;

	@Autowired
	private JobService jobService;

	@Autowired
	private RelatedUserService relatedUserService;

	@Override
	public List<VisitSchedulerDate> findByJobId(Long jobId) {
		return visitSchedulerDateRepo.findByJobId(jobId);
	}

	@Override
	public List<VisitSchedulerDate> getJobReportByVisitWithin(String startDate, String endDate) {
		// List<Job> jobList = jobRepository.findAll();
		List<VisitSchedulerDate> visitList = visitSchedulerDateRepo.findVisiterList(startDate, endDate);
		System.out.println("Before filter" + visitList);
		visitList = visitList.stream().filter(p -> p.getChecked() == true).collect(Collectors.toList());
		System.out.println("After filter" + visitList);
		return visitList;
	}

	@Override
	public List<VisitSchedulerDateDto> filterValidationOnVisitDates(List<VisitSchedulerDate> jobVisitSchedulerDates,
			Long timezone) {
		List<VisitSchedulerDateDto> visitSchedulerDateCompDtos = new ArrayList<VisitSchedulerDateDto>();
		List<VisitSchedulerDateDto> finalVisitList = new ArrayList<VisitSchedulerDateDto>();
		List<VisitSchedulerDateDto> visitSchedulerDateUnCompDtos = new ArrayList<VisitSchedulerDateDto>();
		if (jobVisitSchedulerDates.size() > 0) {
			List<VisitSchedulerDate> visitCompleted = jobVisitSchedulerDates.stream()
					.filter(visit -> visit.getChecked()).collect(Collectors.toList());
			for (VisitSchedulerDate visitSchedulerDate : visitCompleted) {
				VisitSchedulerDateDto visitSchedulerDateDto = new VisitSchedulerDateDto();

				Calendar currentDate = new GregorianCalendar();
				Calendar visitDate = new GregorianCalendar();
				Calendar completeDate = new GregorianCalendar();
				currentDate.setTime(new Date());
				currentDate.add(Calendar.MINUTE, -(timezone.intValue()));
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);
				currentDate.set(Calendar.SECOND, 0);
				currentDate.set(Calendar.MILLISECOND, 0);
				if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
					visitDate.setTime(Date.from(Instant.now()));
				} else {
					if (visitSchedulerDate.getSchLater() != null) {
						visitDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
					} else {
						visitDate.setTime(Date.from(Instant.now()));
					}
				}

				visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
				visitDate.set(Calendar.HOUR_OF_DAY, 0);
				visitDate.set(Calendar.MINUTE, 0);
				visitDate.set(Calendar.SECOND, 0);
				visitDate.set(Calendar.MILLISECOND, 0);
				if (visitSchedulerDate.getCheckedDate() != null) {
					completeDate.setTime(Date.from(visitSchedulerDate.getCheckedDate()));
				} else {
					completeDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				}

				completeDate.set(Calendar.HOUR_OF_DAY, 0);
				completeDate.set(Calendar.MINUTE, 0);
				completeDate.set(Calendar.SECOND, 0);
				completeDate.set(Calendar.MILLISECOND, 0);

				Integer month = visitDate.get(Calendar.MONTH);
				Integer year = visitDate.get(Calendar.YEAR);

				if (visitSchedulerDateCompDtos.size() > 0) {

					Optional<VisitSchedulerDateDto> optional = visitSchedulerDateCompDtos.stream()
							.filter(x -> x.getMonthNumber().equals(month) && x.getYear().equals(year)).findFirst();

					if (optional.isPresent()) {
						for (VisitSchedulerDateDto visitSchedulerDateDto2 : visitSchedulerDateCompDtos) {
							if (visitSchedulerDateDto2.getMonthNumber().equals(month)
									&& visitSchedulerDateDto2.getYear().equals(year)) {
								List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
								vistDummy.addAll(visitSchedulerDateDto2.getVisitSchedulerDate());
								VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
								List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
								try {
									List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
									driverUserList = relatedUserService.findByUserIds(driverUserIds);
								} catch (Exception e) {
									driverUserList = new ArrayList<RelatedUser>();
								}
								List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
								try {
									List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
									teamUserList = relatedUserService.findByUserIds(teamUserIds);
								} catch (Exception e) {
									teamUserList = new ArrayList<RelatedUser>();
								}
//								TODO: 
								visitJobDateAssignUserDto = visitJobDateAssignUserDto
										.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
								vistDummy.add(visitJobDateAssignUserDto);
								visitSchedulerDateDto2.setVisitSchedulerDate(vistDummy);
							}
						}
					}
					if (optional.isEmpty()) {
						if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
							visitSchedulerDateDto.setStatus("unschedule");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(14));
							visitSchedulerDateDto.setMonthNumber(13);
							visitSchedulerDateDto.setYear(year);
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
						} else if (currentDate.equals(completeDate)) {
							visitSchedulerDateDto.setStatus("Completed");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(13));
							visitSchedulerDateDto.setMonthNumber(14);
							visitSchedulerDateDto.setYear(year);
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
						} else {
							visitSchedulerDateDto.setStatus("Completed");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(month));
							visitSchedulerDateDto.setMonthNumber(month);
							visitSchedulerDateDto.setYear(year);
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
						}
					}
				} else {
					if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
						visitSchedulerDateDto.setStatus("unschedule");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(14));
						visitSchedulerDateDto.setMonthNumber(14);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
					} else if (currentDate.equals(completeDate)) {
						visitSchedulerDateDto.setStatus("Completed");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(13));
						visitSchedulerDateDto.setMonthNumber(13);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
					} else {
						visitSchedulerDateDto.setStatus("Completed");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(month));
						visitSchedulerDateDto.setMonthNumber(month);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
					}
				}
			}

			for (VisitSchedulerDateDto visitSchedulerDate2 : visitSchedulerDateCompDtos) {

			}

//others
			List<VisitSchedulerDate> visitUnCompleted = jobVisitSchedulerDates.stream()
					.filter(visit -> !visit.getChecked()).collect(Collectors.toList());
			for (VisitSchedulerDate visitSchedulerDate : visitUnCompleted) {
				VisitSchedulerDateDto visitSchedulerDateDto = new VisitSchedulerDateDto();

				Calendar currentDate = new GregorianCalendar();
				Calendar verifyCurrentDate = new GregorianCalendar();
				verifyCurrentDate.add(Calendar.MINUTE, -(timezone.intValue()));

				Calendar verifyVisitDate = new GregorianCalendar();

				if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
					verifyVisitDate.setTime(Date.from(Instant.now()));
				} else {
					verifyVisitDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				}

				verifyVisitDate.add(Calendar.MINUTE, -(timezone.intValue()));

				Calendar visitDate = new GregorianCalendar();
				currentDate.setTime(new Date());
				currentDate.add(Calendar.MINUTE, -(timezone.intValue()));
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);
				currentDate.set(Calendar.SECOND, 0);
				currentDate.set(Calendar.MILLISECOND, 0);

				if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
					visitDate.setTime(Date.from(Instant.now()));
				} else {
					visitDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				}
				visitDate.add(Calendar.MINUTE, -(timezone.intValue()));

				visitDate.set(Calendar.HOUR_OF_DAY, 0);
				visitDate.set(Calendar.MINUTE, 0);
				visitDate.set(Calendar.SECOND, 0);
				visitDate.set(Calendar.MILLISECOND, 0);

				Integer month = visitDate.get(Calendar.MONTH);
				Integer year = visitDate.get(Calendar.YEAR);
				Integer verfiyNumber = 0;
				if (visitSchedulerDateUnCompDtos.size() > 0) {
					Optional<VisitSchedulerDateDto> optional = visitSchedulerDateUnCompDtos.stream()
							.filter(x -> x.getMonthNumber().equals(month) && x.getYear().equals(year)).findFirst();

					if (optional.isPresent()) {
						for (VisitSchedulerDateDto visitSchedulerDateDto2 : visitSchedulerDateUnCompDtos) {

							if (visitSchedulerDateDto2.getMonthNumber().equals(month)
									&& visitSchedulerDateDto2.getYear().equals(year)) {

								List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
								VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
								List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
								try {
									List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
									driverUserList = relatedUserService.findByUserIds(driverUserIds);
								} catch (Exception e) {
									driverUserList = new ArrayList<RelatedUser>();
								}
								List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
								try {
									List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
									teamUserList = relatedUserService.findByUserIds(teamUserIds);
								} catch (Exception e) {
									teamUserList = new ArrayList<RelatedUser>();
								}
//								TODO: 
								visitJobDateAssignUserDto = visitJobDateAssignUserDto
										.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
								vistDummy.addAll(visitSchedulerDateDto2.getVisitSchedulerDate());
								vistDummy.add(visitJobDateAssignUserDto);

								visitSchedulerDateDto2.setVisitSchedulerDate(vistDummy);
							}
						}
					}
					if (optional.isEmpty()) {
						if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
							visitSchedulerDateDto.setStatus("unschedule");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(14));
							visitSchedulerDateDto.setMonthNumber(14);
							visitSchedulerDateDto.setVerifyNumber(14);
							visitSchedulerDateDto.setYear(year);
							verfiyNumber = 14;
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
						} else if (verifyCurrentDate.before(verifyVisitDate)) {
							visitSchedulerDateDto.setStatus("Uncompleted");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(month));
							visitSchedulerDateDto.setMonthNumber(month);
							visitSchedulerDateDto.setYear(year);
							verfiyNumber = month;
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateUnCompDtos.add(visitSchedulerDateDto);
						} else {
							visitSchedulerDateDto.setStatus("Uncompleted");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(12));
							visitSchedulerDateDto.setMonthNumber(month);
							visitSchedulerDateDto.setYear(year);
							verfiyNumber = 12;
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateUnCompDtos.add(visitSchedulerDateDto);
						}
					}
				} else {
					if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
						visitSchedulerDateDto.setStatus("unschedule");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(14));
						visitSchedulerDateDto.setMonthNumber(14);
						visitSchedulerDateDto.setYear(year);
						verfiyNumber = 14;
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
					} else if (verifyCurrentDate.before(verifyVisitDate)) {
						visitSchedulerDateDto.setStatus("Uncompleted");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(month));
						visitSchedulerDateDto.setMonthNumber(month);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateUnCompDtos.add(visitSchedulerDateDto);
					} else {
						visitSchedulerDateDto.setStatus("Uncompleted");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(12));
						visitSchedulerDateDto.setMonthNumber(month);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();

						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}

//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateUnCompDtos.add(visitSchedulerDateDto);
					}
				}
			}
		}
		finalVisitList.addAll(visitSchedulerDateCompDtos);
		finalVisitList.addAll(visitSchedulerDateUnCompDtos);
		return finalVisitList;
	}

	private List<Long> getTeamListFromVisit(VisitSchedulerDate visitSchedulerDate) {
		List<Long> teamList = new ArrayList<Long>();
		try {
			long[] teamIds = Arrays.asList(visitSchedulerDate.getTeamMembers().split(",")).stream().map(String::trim)
					.mapToLong(Long::parseLong).toArray();
			if (teamIds.length > 0) {
				teamList = Arrays.stream(teamIds).boxed().collect(Collectors.toList());
			}
		} catch (Exception e) {
		}

		return teamList;
	}

	private List<Long> getDriverListFromVisit(VisitSchedulerDate visitSchedulerDate) {
		List<Long> driverList = new ArrayList<Long>();
		try {
			long[] driverIds = Arrays.asList(visitSchedulerDate.getDriverMembers().split(",")).stream()
					.map(String::trim).mapToLong(Long::parseLong).toArray();
			if (driverIds.length > 0) {
				driverList = Arrays.stream(driverIds).boxed().collect(Collectors.toList());
			}
		} catch (Exception e) {
		}

		return driverList;
	}

	@Override
	public VisitScheduleUpdateDto updateNewVisit(VisitScheduleUpdateDto visitScheduleUpdateDto, Long timezone) {
		VisitSchedulerDate visitSchedulerDate = new VisitSchedulerDate();

		Optional<Job> job = jobService.findById(visitScheduleUpdateDto.getJobId());

		if (visitScheduleUpdateDto.getVisitId() != null && visitScheduleUpdateDto.getVisitId() > 0) {
			Optional<VisitSchedulerDate> visitData = visitSchedulerDateRepo
					.findById(visitScheduleUpdateDto.getVisitId());
			visitSchedulerDate = visitData.get();
			visitSchedulerDate.setVisitTitle(visitScheduleUpdateDto.getVisitTitle());
			visitSchedulerDate.setVisitInstruction(visitScheduleUpdateDto.getVisitInstruction());
			visitSchedulerDate.setTeamMembers(visitScheduleUpdateDto.getAssignTeam());
			visitSchedulerDate.setDriverMembers(visitScheduleUpdateDto.getAssignDriver());

			if (visitScheduleUpdateDto.getAnySchedule() != null && !visitScheduleUpdateDto.getAnySchedule()) {
//				visitSchedulerDate.setStartDate(visitScheduleUpdateDto.getStartDate());
				visitSchedulerDate.setCurrentDate(visitScheduleUpdateDto.getStartDate());
				visitSchedulerDate.setEndDate(visitScheduleUpdateDto.getEndDate());
				visitSchedulerDate.setSchLater(visitScheduleUpdateDto.getAnySchedule());
			} else {
				if (visitScheduleUpdateDto.getAnySchedule() != null) {
					visitSchedulerDate.setSchLater(visitScheduleUpdateDto.getAnySchedule());
					visitSchedulerDate.setCurrentDate(null);
					visitSchedulerDate.setStartDate(null);
					visitSchedulerDate.setEndDate(null);
				} else {
					visitSchedulerDate.setCurrentDate(visitScheduleUpdateDto.getStartDate());
					visitSchedulerDate.setEndDate(visitScheduleUpdateDto.getEndDate());
					visitSchedulerDate.setSchLater(visitScheduleUpdateDto.getAnySchedule());
				}

			}
			if (visitScheduleUpdateDto.getIfAny() != null && !visitScheduleUpdateDto.getIfAny()) {
				visitSchedulerDate.setAnyTime(visitScheduleUpdateDto.getIfAny());
				visitSchedulerDate.setStartTime(visitScheduleUpdateDto.getStartTime());
				visitSchedulerDate.setEndTime(visitScheduleUpdateDto.getEndTime());
				Calendar cal = new GregorianCalendar();
				if (visitSchedulerDate.getCurrentDate() != null) {
					cal.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
					cal.add(Calendar.HOUR_OF_DAY, Date.from(visitScheduleUpdateDto.getStartTime()).getHours());
					cal.add(Calendar.MINUTE, Date.from(visitScheduleUpdateDto.getStartTime()).getMinutes());
					visitSchedulerDate.setCurrentDate(cal.getTime().toInstant());
				} else {
					cal.setTime((Date.from(visitSchedulerDate.getStartTime() != null ? visitSchedulerDate.getStartTime()
							: new Date().toInstant())));
				}

			} else {
				visitSchedulerDate.setAnyTime(visitScheduleUpdateDto.getIfAny());
				visitSchedulerDate.setStartTime(null);
				visitSchedulerDate.setEndTime(null);
			}

		} else {
			visitSchedulerDate.setVisitTitle(visitScheduleUpdateDto.getVisitTitle());
			visitSchedulerDate.setVisitInstruction(visitScheduleUpdateDto.getVisitInstruction());
			visitSchedulerDate.setTeamMembers(visitScheduleUpdateDto.getAssignTeam());
			visitSchedulerDate.setDriverMembers(visitScheduleUpdateDto.getAssignDriver());
			visitSchedulerDate.setJobId(visitScheduleUpdateDto.getJobId());
			visitSchedulerDate.setChecked(false);
			visitSchedulerDate.setCheckedDate(null);
			visitSchedulerDate.setCurrentDate(visitScheduleUpdateDto.getStartDate());
			visitSchedulerDate.setDriverNotify(false);
			visitSchedulerDate.setDriverReminder("0");
			visitSchedulerDate.setEndDate(visitScheduleUpdateDto.getEndDate());
			if (visitScheduleUpdateDto.getIfAny() != null && !visitScheduleUpdateDto.getIfAny()) {
				visitSchedulerDate.setStartTime(visitScheduleUpdateDto.getStartTime());
				visitSchedulerDate.setEndTime(visitScheduleUpdateDto.getEndTime());
			}

			visitSchedulerDate.setJobType(visitScheduleUpdateDto.getJobType());
			visitSchedulerDate.setNotify(false);
			visitSchedulerDate.setStartDate(visitScheduleUpdateDto.getStartDate());
			visitSchedulerDate.setCurrentDate(visitScheduleUpdateDto.getStartDate());
			visitSchedulerDate.setJobType(visitScheduleUpdateDto.getJobType());
			visitSchedulerDate.setStatus(null);
			visitSchedulerDate.setTimeZone(timezone);
			visitSchedulerDate.setAnyTime(visitScheduleUpdateDto.getIfAny());
			visitSchedulerDate.setSchLater(visitScheduleUpdateDto.getAnySchedule());

			if (visitScheduleUpdateDto.getIfAny() != null && !visitScheduleUpdateDto.getIfAny()) {
				Calendar cal = new GregorianCalendar();
				cal.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				cal.add(Calendar.HOUR_OF_DAY, Date.from(visitScheduleUpdateDto.getStartTime()).getHours());
				cal.add(Calendar.MINUTE, Date.from(visitScheduleUpdateDto.getStartTime()).getMinutes());
				visitSchedulerDate.setCurrentDate(cal.getTime().toInstant());
			}
		}

		visitSchedulerDate = visitSchedulerDateRepo.save(visitSchedulerDate);
		visitScheduleUpdateDto.setVisitId(visitSchedulerDate.getId());
		if (visitScheduleUpdateDto.getJobProductService().size() > 0) {
			List<VisitJobProductAndServiceMap> visitJobProMap = jobProductServiceService.saveAllDataWithVisitMapping(
					visitScheduleUpdateDto.getJobProductService(), visitSchedulerDate.getId());
		}
		long[] driverIds = null;

		if (visitScheduleUpdateDto.getAssignDriver() != null && visitScheduleUpdateDto.getAssignDriver() != "") {
			driverIds = Arrays.asList(visitScheduleUpdateDto.getAssignDriver().split(",")).stream().map(String::trim)
					.mapToLong(Long::parseLong).toArray();
		}

		if (visitScheduleUpdateDto.getDriverEmailFlag()) {

			emailSenderUtil.sendEmailMethod(driverIds, job.get());
		}

		if (visitScheduleUpdateDto.getTeamEmailFlag()) {
			long[] teamIds = null;
			teamIds = Arrays.asList(visitScheduleUpdateDto.getAssignTeam().split(",")).stream().map(String::trim)
					.mapToLong(Long::parseLong).toArray();
			emailSenderUtil.sendEmailMethodTeamByVisit(teamIds, job.get(), driverIds, timezone.intValue());
		}

//		if (visitScheduleUpdateDto.getFutureVisit()) {
//			List<VisitSchedulerDate> allFutureVisitList = visitSchedulerDateRepo.findFutureVisit(
//					visitScheduleUpdateDto.getJobId(), Date.from(visitScheduleUpdateDto.getStartDate()));
//			for (VisitSchedulerDate visitSchedulerDate2 : allFutureVisitList) {
//				visitSchedulerDate2.setAnyTime(visitScheduleUpdateDto.getIfAny());
//				if (visitScheduleUpdateDto.getIfAny()) {
//					visitSchedulerDate2.setCurrentDate(visitSchedulerDate2.getStartDate());
//					visitSchedulerDate2.setStartTime(null);
//					visitSchedulerDate2.setEndTime(null);
//				} else {
//					try {
//						Calendar shiftTime = new GregorianCalendar();
//						shiftTime.setTime(Date.from(visitSchedulerDate2.getStartDate()));
//
//						Calendar startTime = new GregorianCalendar();
//						startTime.setTime(Date.from(visitScheduleUpdateDto.getStartTime()));
//
//						Calendar endTime = new GregorianCalendar();
//						endTime.setTime(Date.from(visitSchedulerDate2.getStartDate()));
//
//						shiftTime.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
//						shiftTime.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
//
//						Calendar endDate = new GregorianCalendar();
//						endDate.setTime(Date.from(visitSchedulerDate2.getEndTime()));
//
//						endTime.set(Calendar.HOUR_OF_DAY, endDate.get(Calendar.HOUR_OF_DAY));
//						endTime.set(Calendar.MINUTE, endDate.get(Calendar.MINUTE));
//
//						visitSchedulerDate2.setCurrentDate(shiftTime.toInstant());
//						visitSchedulerDate2.setStartTime(shiftTime.toInstant());
//
//						visitSchedulerDate2.setEndTime(endTime.toInstant());
//					} catch (Exception e) {
//						System.out.println(e.getLocalizedMessage());
//					}
//				}
//			}
//			visitSchedulerDateRepo.saveAll(allFutureVisitList);
//		}

		return visitScheduleUpdateDto;
	}

	@Override
	public VisitDatesDto findVisitWithProductMap(Long visitId) {
		Optional<VisitSchedulerDate> visitData = visitSchedulerDateRepo.findById(visitId);
		VisitDatesDto visitDatesDto = new VisitDatesDto();

		if (visitData.isPresent()) {
			List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
			try {
				List<Long> driverUserIds = getDriverListFromVisit(visitData.get());
				driverUserList = relatedUserService.findByUserIds(driverUserIds);
			} catch (Exception e) {
				driverUserList = new ArrayList<RelatedUser>();
			}
			List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
			try {
				List<Long> teamUserIds = getTeamListFromVisit(visitData.get());
				teamUserList = relatedUserService.findByUserIds(teamUserIds);
			} catch (Exception e) {
				teamUserList = new ArrayList<RelatedUser>();
			}
			List<VisitJobProductAndServiceMap> visitJobProMap = visitJobProAndServiceMapService.findByVisitId(visitId);
			visitDatesDto = visitDatesDto.mapData(visitData.get(), visitJobProMap, driverUserList, teamUserList);
		}
		return visitDatesDto;
	}

	@Override
	public void deleteByVisitId(Long visitId, Long jobId) {
		visitSchedulerDateRepo.deleteByVisitId(visitId);
		visitJobProAndServiceMapService.deleteMapping(jobId, visitId);
	}

	@Override
	public FutureVisitMapDto updateForFutureVisit(FutureVisitMapDto futureVisit) {
		Optional<VisitSchedulerDate> visitSchedulerDate = visitSchedulerDateRepo.findById(futureVisit.getVisitId());

		List<VisitSchedulerDate> forFutureVisit = visitSchedulerDateRepo.findFutureVisit(
				visitSchedulerDate.get().getJobId(), Date.from(visitSchedulerDate.get().getCurrentDate()));

		for (VisitSchedulerDate visitSchedulerDate2 : forFutureVisit) {

			if (futureVisit.getTimeOfDay() != null && futureVisit.getTimeOfDay()) {
				visitSchedulerDate2.setAnyTime(visitSchedulerDate.get().getAnyTime());
				if (visitSchedulerDate.get().getAnyTime()) {
					visitSchedulerDate2.setCurrentDate(visitSchedulerDate2.getStartDate());
					visitSchedulerDate2.setStartTime(null);
					visitSchedulerDate2.setEndTime(null);
				} else {
					try {
						Calendar shiftTime = new GregorianCalendar();
						shiftTime.setTime(Date.from(visitSchedulerDate2.getStartDate()));

						Calendar startTime = new GregorianCalendar();
						startTime.setTime(Date.from(visitSchedulerDate.get().getStartTime()));

						Calendar endTime = new GregorianCalendar();
						endTime.setTime(Date.from(visitSchedulerDate2.getStartDate()));

						shiftTime.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
						shiftTime.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));

						Calendar endDate = new GregorianCalendar();
						endDate.setTime(Date.from(visitSchedulerDate.get().getEndTime()));

						endTime.set(Calendar.HOUR_OF_DAY, endDate.get(Calendar.HOUR_OF_DAY));
						endTime.set(Calendar.MINUTE, endDate.get(Calendar.MINUTE));

						visitSchedulerDate2.setCurrentDate(shiftTime.toInstant());
						visitSchedulerDate2.setStartTime(shiftTime.toInstant());

						visitSchedulerDate2.setEndTime(endTime.toInstant());
					} catch (Exception e) {
						System.out.println(e.getLocalizedMessage());
					}
				}
			}

			if (futureVisit.getAssignTeam() != null && futureVisit.getAssignTeam()) {
				visitSchedulerDate2.setTeamMembers(visitSchedulerDate.get().getTeamMembers());
			}

			if (futureVisit.getAssignDriver() != null && futureVisit.getAssignDriver()) {
				visitSchedulerDate2.setDriverMembers(visitSchedulerDate.get().getDriverMembers());
			}

			if (futureVisit.getLineItem() != null && futureVisit.getLineItem()) {
				visitJobProAndServiceMapService.deleteMapping(visitSchedulerDate.get().getJobId(),
						visitSchedulerDate2.getId());
				List<VisitJobProductAndServiceMap> visitJobProMap = visitJobProAndServiceMapService
						.findByVisitId(futureVisit.getVisitId());
				for (int i = 0; i < visitJobProMap.size(); i++) {
					visitJobProMap.get(i).setId(null);
					visitJobProMap.get(i).setVisitId(futureVisit.getVisitId());
				}

				visitJobProMap = visitJobProAndServiceMapService.saveMapping(visitJobProMap);
			}
			visitSchedulerDateRepo.saveAll(forFutureVisit);
		}
		return futureVisit;
	}

	@Override
	public List<VisitSchedulerDateDto> forUpComingVisit(List<VisitSchedulerDate> jobVisitSchedulerDates,
			Long timezone) {

		List<VisitSchedulerDateDto> visitSchedulerDateCompDtos = new ArrayList<VisitSchedulerDateDto>();
		List<VisitSchedulerDateDto> finalVisitList = new ArrayList<VisitSchedulerDateDto>();
		List<VisitSchedulerDateDto> visitSchedulerDateUnCompDtos = new ArrayList<VisitSchedulerDateDto>();
		if (jobVisitSchedulerDates.size() > 0) {
			List<VisitSchedulerDate> visitCompleted = jobVisitSchedulerDates.stream()
					.filter(visit -> visit.getChecked()).collect(Collectors.toList());
			int i = 0;
			for (VisitSchedulerDate visitSchedulerDate : visitCompleted) {
				System.out.println("visit -Id " + visitSchedulerDate.getId());
				VisitSchedulerDateDto visitSchedulerDateDto = new VisitSchedulerDateDto();

				Calendar currentDate = new GregorianCalendar();
				Calendar visitDate = new GregorianCalendar();
				Calendar completeDate = new GregorianCalendar();
				currentDate.setTime(new Date());
				currentDate.add(Calendar.MINUTE, -(timezone.intValue()));
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);
				currentDate.set(Calendar.SECOND, 0);
				currentDate.set(Calendar.MILLISECOND, 0);
				if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
					visitDate.setTime(Date.from(Instant.now()));
				} else {
					visitDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				}

				visitDate.add(Calendar.MINUTE, -(timezone.intValue()));
				visitDate.set(Calendar.HOUR_OF_DAY, 0);
				visitDate.set(Calendar.MINUTE, 0);
				visitDate.set(Calendar.SECOND, 0);
				visitDate.set(Calendar.MILLISECOND, 0);
				if (visitSchedulerDate.getCheckedDate() != null) {
					completeDate.setTime(Date.from(visitSchedulerDate.getCheckedDate()));
				} else {
					completeDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				}

				completeDate.set(Calendar.HOUR_OF_DAY, 0);
				completeDate.set(Calendar.MINUTE, 0);
				completeDate.set(Calendar.SECOND, 0);
				completeDate.set(Calendar.MILLISECOND, 0);

				Integer month = visitDate.get(Calendar.MONTH);
				Integer year = visitDate.get(Calendar.YEAR);
				Integer completeCount = 1;
				if (visitSchedulerDateCompDtos.size() > 0) {

					Optional<VisitSchedulerDateDto> optional = visitSchedulerDateCompDtos.stream()
							.filter(x -> x.getMonthNumber().equals(month) && x.getYear().equals(year)).findFirst();

					if (optional.isPresent()) {
						for (VisitSchedulerDateDto visitSchedulerDateDto2 : visitSchedulerDateCompDtos) {
							if (visitSchedulerDateDto2.getMonthNumber().equals(month)
									&& visitSchedulerDateDto2.getYear().equals(year)) {
								List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
								vistDummy.addAll(visitSchedulerDateDto2.getVisitSchedulerDate());
								VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
								List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
								try {
									List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
									driverUserList = relatedUserService.findByUserIds(driverUserIds);
								} catch (Exception e) {
									driverUserList = new ArrayList<RelatedUser>();
								}
								List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
								try {
									List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
									teamUserList = relatedUserService.findByUserIds(teamUserIds);
								} catch (Exception e) {
									teamUserList = new ArrayList<RelatedUser>();
								}
//								TODO: 
								visitJobDateAssignUserDto = visitJobDateAssignUserDto
										.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
								vistDummy.add(visitJobDateAssignUserDto);
								visitSchedulerDateDto2.setVisitSchedulerDate(vistDummy);
							}
						}
					}
					if (optional.isEmpty()) {
						if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
							visitSchedulerDateDto.setStatus("unschedule");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(14));
							visitSchedulerDateDto.setMonthNumber(13);
							visitSchedulerDateDto.setYear(year);

							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
						} else if (currentDate.equals(completeDate)) {
							visitSchedulerDateDto.setStatus("Completed");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(13));
							visitSchedulerDateDto.setMonthNumber(14);
							visitSchedulerDateDto.setYear(year);
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
						} else {
							visitSchedulerDateDto.setStatus("Completed");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(month));
							visitSchedulerDateDto.setMonthNumber(month);
							visitSchedulerDateDto.setYear(year);
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
						}
					}
				} else {
					if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
						visitSchedulerDateDto.setStatus("unschedule");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(14));
						visitSchedulerDateDto.setMonthNumber(14);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
					} else if (currentDate.equals(completeDate)) {
						visitSchedulerDateDto.setStatus("Completed");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(13));
						visitSchedulerDateDto.setMonthNumber(13);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
					} else {
						visitSchedulerDateDto.setStatus("Completed");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(month));
						visitSchedulerDateDto.setMonthNumber(month);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
					}
				}
			}

//others
			List<VisitSchedulerDate> visitUnCompleted = jobVisitSchedulerDates.stream()
					.filter(visit -> !visit.getChecked()).collect(Collectors.toList());
			for (VisitSchedulerDate visitSchedulerDate : visitUnCompleted) {
				VisitSchedulerDateDto visitSchedulerDateDto = new VisitSchedulerDateDto();

//				System.out.println("===================================="+i);
//				i++;
				Calendar currentDate = new GregorianCalendar();
				Calendar verifyCurrentDate = new GregorianCalendar();
				verifyCurrentDate.add(Calendar.MINUTE, -(timezone.intValue()));

				Calendar verifyVisitDate = new GregorianCalendar();

				if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
					verifyVisitDate.setTime(Date.from(Instant.now()));
				} else {
					verifyVisitDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				}

				verifyVisitDate.add(Calendar.MINUTE, -(timezone.intValue()));

				Calendar visitDate = new GregorianCalendar();
				currentDate.setTime(new Date());
				currentDate.add(Calendar.MINUTE, -(timezone.intValue()));
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);
				currentDate.set(Calendar.SECOND, 0);
				currentDate.set(Calendar.MILLISECOND, 0);

				if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
					visitDate.setTime(Date.from(Instant.now()));
				} else {
					visitDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				}
				visitDate.add(Calendar.MINUTE, -(timezone.intValue()));

				visitDate.set(Calendar.HOUR_OF_DAY, 0);
				visitDate.set(Calendar.MINUTE, 0);
				visitDate.set(Calendar.SECOND, 0);
				visitDate.set(Calendar.MILLISECOND, 0);

				Integer month = visitDate.get(Calendar.MONTH);
				Integer year = visitDate.get(Calendar.YEAR);
				if (visitSchedulerDateUnCompDtos.size() > 0) {
					Optional<VisitSchedulerDateDto> optional = visitSchedulerDateUnCompDtos.stream()
							.filter(x -> x.getMonthNumber().equals(month) && x.getYear().equals(year)).findFirst();

					if (optional.isPresent()) {
						for (VisitSchedulerDateDto visitSchedulerDateDto2 : visitSchedulerDateUnCompDtos) {

							if (visitSchedulerDateDto2.getMonthNumber().equals(month)
									&& visitSchedulerDateDto2.getYear().equals(year)) {

								List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
								vistDummy.addAll(visitSchedulerDateDto2.getVisitSchedulerDate());
								VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
								List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
								try {
									List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
									driverUserList = relatedUserService.findByUserIds(driverUserIds);
								} catch (Exception e) {
									driverUserList = new ArrayList<RelatedUser>();
								}
								List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
								try {
									List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
									teamUserList = relatedUserService.findByUserIds(teamUserIds);
								} catch (Exception e) {
									teamUserList = new ArrayList<RelatedUser>();
								}
//								TODO: 
								visitJobDateAssignUserDto = visitJobDateAssignUserDto
										.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
								vistDummy.add(visitJobDateAssignUserDto);
								visitSchedulerDateDto2.setVisitSchedulerDate(vistDummy);
							}
						}
					}
					if (optional.isEmpty()) {
						if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
							visitSchedulerDateDto.setStatus("unschedule");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(14));
							visitSchedulerDateDto.setMonthNumber(14);
							visitSchedulerDateDto.setYear(year);
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
						} else if (verifyCurrentDate.before(verifyVisitDate)) {
							visitSchedulerDateDto.setStatus("Upcoming");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(month));
							visitSchedulerDateDto.setMonthNumber(month);
							visitSchedulerDateDto.setYear(year);
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateUnCompDtos.add(visitSchedulerDateDto);
						} else {
							visitSchedulerDateDto.setStatus("Uncompleted");
							visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(12));
							visitSchedulerDateDto.setMonthNumber(month);
							visitSchedulerDateDto.setYear(year);
							List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//							TODO: new bug input
							VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
							List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
								driverUserList = relatedUserService.findByUserIds(driverUserIds);
							} catch (Exception e) {
								driverUserList = new ArrayList<RelatedUser>();
							}
							List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
							try {
								List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
								teamUserList = relatedUserService.findByUserIds(teamUserIds);
							} catch (Exception e) {
								teamUserList = new ArrayList<RelatedUser>();
							}
//							TODO: 
							visitJobDateAssignUserDto = visitJobDateAssignUserDto
									.mappingDataWithVisit(visitSchedulerDate, driverUserList, teamUserList);
//							==========
							vistDummy.add(visitJobDateAssignUserDto);
							visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
							visitSchedulerDateUnCompDtos.add(visitSchedulerDateDto);
						}
					}
				} else {
					if (visitSchedulerDate.getSchLater() != null && visitSchedulerDate.getSchLater()) {
						visitSchedulerDateDto.setStatus("unschedule");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(14));
						visitSchedulerDateDto.setMonthNumber(14);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateCompDtos.add(visitSchedulerDateDto);
					} else if (verifyCurrentDate.before(verifyVisitDate)) {
						visitSchedulerDateDto.setStatus("Upcoming");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(month));
						visitSchedulerDateDto.setMonthNumber(month);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateUnCompDtos.add(visitSchedulerDateDto);
//					
					} else {
						visitSchedulerDateDto.setStatus("Uncompleted");
						visitSchedulerDateDto.setMonth(DateServiceUtil.getMonthName(12));
						visitSchedulerDateDto.setMonthNumber(month);
						visitSchedulerDateDto.setYear(year);
						List<VisitJobDateAssignUserDto> vistDummy = new ArrayList<VisitJobDateAssignUserDto>();
//						TODO: new bug input
						VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
						List<RelatedUser> driverUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> driverUserIds = getDriverListFromVisit(visitSchedulerDate);
							driverUserList = relatedUserService.findByUserIds(driverUserIds);
						} catch (Exception e) {
							driverUserList = new ArrayList<RelatedUser>();
						}
						List<RelatedUser> teamUserList = new ArrayList<RelatedUser>();
						try {
							List<Long> teamUserIds = getTeamListFromVisit(visitSchedulerDate);
							teamUserList = relatedUserService.findByUserIds(teamUserIds);
						} catch (Exception e) {
							teamUserList = new ArrayList<RelatedUser>();
						}
//						TODO: 
						visitJobDateAssignUserDto = visitJobDateAssignUserDto.mappingDataWithVisit(visitSchedulerDate,
								driverUserList, teamUserList);
//						==========
						vistDummy.add(visitJobDateAssignUserDto);
						visitSchedulerDateDto.setVisitSchedulerDate(vistDummy);
						visitSchedulerDateUnCompDtos.add(visitSchedulerDateDto);
					}
				}
			}
		}
		finalVisitList.addAll(visitSchedulerDateCompDtos);
		finalVisitList.addAll(visitSchedulerDateUnCompDtos);
		return finalVisitList;
	}

	@Override
	public List<VisitSchedulerDate> findVisitByUpComing(Long jobId, Date time) {
		return visitSchedulerDateRepo.findVisitByUpcoming(jobId, time);
	}

	@Override
	public void updatePaidOn(List<Long> visitId, Long jobId) {
		visitSchedulerDateRepo.updatePaidOn(visitId);
		visitSchedulerDateRepo.updatePaidOnForCompleted(jobId);
	}

	@Override
	public List<VisitSchedulerDate> findByVisitIds(Long jobId, List<Long> visitIds) {
		return visitSchedulerDateRepo.findAllVisitWithComplete(visitIds);
	}

	@Override
	public void updatePaidOn(Long visitId) {
		visitSchedulerDateRepo.updatePaidOnSingle(visitId);
	}

}
