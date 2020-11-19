package com.checksammy.loca.serviceImpl.dateschedular;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.InvoiceSchedularDate;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.VisitFrequencyJobMaster;
import com.checksammy.loca.model.VisitSchedulerDate;
import com.checksammy.loca.utility.StringUtil;

@Service("scheduleDateService")
public class ScheduleDateUtil {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleDateUtil.class);

	private static final Long WEEKLY_ON_MONDAY = Long.valueOf(2);
	private static final Long EVERY_2_WEEK_ON_MONDAY = Long.valueOf(3);
	private static final Long MONTHLY_10TH_DAY = Long.valueOf(4);
	private static final Long CUSTOM = Long.valueOf(5);	

	private static final String DAYS = "days";
	private static final String WEEKS = "weeks";
	private static final String MONTHS = "months";
	private static final String YEARS = "years";

	public List<VisitSchedulerDate> createScheduleTime(Job newJob, List<VisitFrequencyJobMaster> vistJobMasters,
			Integer timezone) {
		List<VisitSchedulerDate> visitSchedularDates = new ArrayList<VisitSchedulerDate>();
		try {

			logger.debug("createScheduleTime() method , with job #" + newJob.getJobNumber());
			VisitFrequencyJobMaster visOptional = new VisitFrequencyJobMaster();
			if (newJob.getSchVisitFrequencyId() != null) {
				visOptional = vistJobMasters.stream()
						.filter(filterData -> (filterData.getId().equals(newJob.getSchVisitFrequencyId()))).findAny()
						.orElseThrow();
			}

			if (newJob.getJobScheduleType().equalsIgnoreCase("one-off job")) {
				VisitSchedulerDate visitSchedularDatesDto = new VisitSchedulerDate();
				Calendar start = new GregorianCalendar();
				start.setTime(Date.from(newJob.getSchStartDate()));
				if (!newJob.getSchStartTime().equals(null)) {
					start.set(Calendar.HOUR_OF_DAY, Date.from(newJob.getSchStartTime()).getHours());
					start.set(Calendar.MINUTE, Date.from(newJob.getSchStartTime()).getMinutes());
				}
//				start.add(Calendar.MINUTE, -(timezone));
				

				visitSchedularDatesDto.setStartDate(newJob.getSchStartDate());
				visitSchedularDatesDto.setEndDate(newJob.getSchEndDate());
				visitSchedularDatesDto.setCurrentDate(start.toInstant());
				visitSchedularDatesDto.setStartTime(newJob.getSchStartTime());
				visitSchedularDatesDto.setEndTime(newJob.getSchEndTime());
				visitSchedularDatesDto.setJobId(newJob.getId());
				visitSchedularDatesDto.setStatus(null);
				visitSchedularDatesDto.setChecked(false);
				visitSchedularDatesDto.setNotify(false);
				visitSchedularDatesDto.setTimeZone(timezone.longValue());
				visitSchedularDatesDto.setDriverReminder("0");
				visitSchedularDatesDto.setJobType("one-off job");
				
				visitSchedularDatesDto.setAnyTime(false);
				visitSchedularDatesDto.setDriverNotify(false);
				visitSchedularDatesDto.setTeamMembers(newJob.getTeamAssignMemberIds());
				visitSchedularDatesDto.setDriverMembers(newJob.getAssignMemberIds());
				visitSchedularDatesDto.setVisitTitle(newJob.getJobTitle()+"- #"+newJob.getJobNumber());
				visitSchedularDatesDto.setPaidOnFlag(false);
				
				visitSchedularDates.add(visitSchedularDatesDto);
			} else {
				if (visOptional != null) {
					if (visOptional.getId() != null && visOptional.getId() > 0) {

						Calendar start = new GregorianCalendar();
						Calendar end = new GregorianCalendar();
						Calendar actualEndDate = new GregorianCalendar();
						start.setTime(Date.from(newJob.getSchStartDate()));
						start.set(Calendar.HOUR_OF_DAY, 0);
						start.set(Calendar.MINUTE, 0);

						actualEndDate.setTime(Date.from(newJob.getSchEndDate()));
//						actualEndDate.set(Calendar.HOUR, Date.from(newJob.getSchEndTime()).getHours());
//						actualEndDate.set(Calendar.MINUTE, Date.from(newJob.getSchEndTime()).getMinutes());
						actualEndDate.getTime().setHours(23);
						actualEndDate.getTime().setMinutes(59);
						/* FILTER BY DUSRATION TYPE */
						switch (newJob.getSchDurationType()) {
						case DAYS:
							end.setTime(Date.from(newJob.getSchStartDate()));
							end.add(Calendar.DATE, Integer.valueOf(newJob.getSchDuration()));
//							System.out.println("current Value Date -> "+end.getTime());
							break;
						case WEEKS:
							end.setTime(Date.from(newJob.getSchStartDate()));
							end.add(Calendar.WEEK_OF_YEAR, Integer.valueOf(newJob.getSchDuration()));
//							System.out.println("current Value Date -> "+end.getTime());
							break;
						case MONTHS:
							end.setTime(Date.from(newJob.getSchStartDate()));
							end.add(Calendar.MONTH, (Integer.valueOf(newJob.getSchDuration())));
//							System.out.println("current Value Date -> "+end.getTime());
							break;
						case YEARS:
							end.setTime(Date.from(newJob.getSchStartDate()));
							end.add(Calendar.YEAR, Integer.valueOf(newJob.getSchDuration()));
//							System.out.println("current Value Date -> "+end.getTime());
							break;

						default:
							break;
						}
						System.out.println("start Date -> " + start.getTime());
						System.out.println("End Date -> " + end.getTime());

						/* FOR WEEKLY ON MONDAY */
						if (visOptional.getId().equals(WEEKLY_ON_MONDAY)) {

//							end.setTime(Date.from(newJob.getSchEndDate()));

//							Date localStartDate = start.getTime();
//							Date localEndDate = end.getTime();

//							Calendar cal = new GregorianCalendar();
//							cal.setTime(localStartDate);
							List<Date> locatDate = new ArrayList<Date>();

							Calendar localStartDate = new GregorianCalendar();
							localStartDate.setTime(start.getTime());
							int i = 0;
							while (localStartDate.before(end)) {
								localStartDate.setTime(start.getTime());
//								localStartDate.set(Calendar.HOUR, 0);
//								localStartDate.set(Calendar.MINUTE, 0);
								localStartDate.add(Calendar.WEEK_OF_YEAR, i);
								localStartDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//								System.out.println("Date " + localStartDate.getTime());
								if (localStartDate.after(start) && localStartDate.before(actualEndDate)) {
									locatDate.add(localStartDate.getTime());
									localStartDate.set(Calendar.HOUR_OF_DAY,
											Date.from(newJob.getSchStartTime()).getHours());
									localStartDate.set(Calendar.MINUTE,
											Date.from(newJob.getSchStartTime()).getMinutes());
									VisitSchedulerDate visitSchedularDatesDto = new VisitSchedulerDate();
									visitSchedularDatesDto.setStartDate(newJob.getSchStartDate());
									visitSchedularDatesDto.setCurrentDate(localStartDate.getTime().toInstant());
									visitSchedularDatesDto.setEndDate(newJob.getSchEndDate());
									visitSchedularDatesDto.setStartTime(newJob.getSchStartTime());
									visitSchedularDatesDto.setEndTime(newJob.getSchEndTime());
									visitSchedularDatesDto.setJobId(newJob.getId());
									visitSchedularDatesDto.setStatus(null);
									visitSchedularDatesDto.setChecked(false);
									visitSchedularDatesDto.setNotify(false);
									visitSchedularDatesDto.setTimeZone(timezone.longValue());
									visitSchedularDatesDto.setDriverReminder("0");
									visitSchedularDatesDto.setJobType("recurring job");
									
									visitSchedularDatesDto.setAnyTime(false);
									visitSchedularDatesDto.setDriverNotify(false);
									visitSchedularDatesDto.setTeamMembers(newJob.getTeamAssignMemberIds());
									visitSchedularDatesDto.setDriverMembers(newJob.getAssignMemberIds());
									visitSchedularDatesDto.setVisitTitle(newJob.getJobTitle()+"- #"+newJob.getJobNumber());
									visitSchedularDatesDto.setPaidOnFlag(false);
									logger.debug("Current Date -> " + visitSchedularDatesDto.getCurrentDate());
									visitSchedularDates.add(visitSchedularDatesDto);
								}
								i++;
							}
//							System.out.println(i);
//							System.out.println(StringUtil.stringify(visitSchedularDates));
						}

						/* 2ND WEEK MONDAY */
						if (visOptional.getId().equals(EVERY_2_WEEK_ON_MONDAY)) {
//							Calendar localTestDate = new GregorianCalendar();
							Calendar weekCount = new GregorianCalendar();
							weekCount.setTime(Date.from(newJob.getSchStartDate()));

//							start.setTime(Date.from(newJob.getSchStartDate()));

//							end.setTime(Date.from(newJob.getSchEndDate()));
							List<Date> locatDateList = new ArrayList<Date>();

//							int weeks = 0;
//					        while (weekCount.getTime().before(end.getTime())) {
//					        	weekCount.add(Calendar.WEEK_OF_YEAR, 1);
//					            weeks++;
//					        }
							int i = 0;
							Calendar localDate = new GregorianCalendar();
							localDate.setTime(start.getTime());

							if (!newJob.getSchStartTime().equals(null)) {
								localDate.set(Calendar.HOUR_OF_DAY, Date.from(newJob.getSchStartTime()).getHours());
								localDate.set(Calendar.MINUTE, Date.from(newJob.getSchStartTime()).getMinutes());
							}

							System.out.println("start DAte -> " + localDate.getTime());
							System.out.println("Actual Start DAte -> " + start.getTime());
							while (localDate.before(end)) {
								/*
								 * for(int j = 0; j < Integer.valueOf(newJob.getSchDuration()); j++) {
								 * System.out.println("Current Date -> " + start.getTime()); Calendar localDate
								 * = new GregorianCalendar(); localDate.setTime(start.getTime());
								 * localDate.set(Calendar.DATE, 01); localDate.add(localDate.MONTH, j);
								 * System.out.println("Current Date -> " + localDate.getTime());
								 * System.out.println("current week of the month -> " +
								 * localDate.get(Calendar.WEEK_OF_MONTH));
								 * System.out.println("Week of Month -> " + localDate.WEEK_OF_MONTH);
								 * localDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
								 * System.out.println("Monday Date -> " +localDate.getTime());
								 * localDate.add(Calendar.DAY_OF_MONTH, 14);
								 * System.out.println("current week monday's date -> " + localDate.getTime());
								 * // localDate.set(Calendar.WEEK_OF_MONTH, 2); //
								 * localDate.set(Calendar.DAY_OF_WEEK, 2);
								 * System.out.println("Monday Date Of 2nd week Monday - > " +
								 * localDate.getTime());
								 */
//					        	localTestDate.setTime(start.getTime());
//					        	localDate.setTime(start.getTime());
								System.out.println("At start Date " + start.getTime());
								System.out.println(localDate.getTime());
								if (i > 0) {
									localDate.add(Calendar.WEEK_OF_YEAR, 2);
								} else {
									localDate.add(Calendar.WEEK_OF_YEAR, 0);
								}

								localDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
								System.out.println("Date " + localDate.getTime());
								System.out.println("actualEndDate" + actualEndDate.getTime());

								if (localDate.after(start) && localDate.before(actualEndDate)) {
									locatDateList.add(localDate.getTime());
									localDate.set(Calendar.HOUR_OF_DAY, Date.from(newJob.getSchStartTime()).getHours());
									localDate.set(Calendar.MINUTE, Date.from(newJob.getSchStartTime()).getMinutes());
									VisitSchedulerDate visitSchedularDatesDto = new VisitSchedulerDate();
									visitSchedularDatesDto.setStartDate(newJob.getSchStartDate());
									visitSchedularDatesDto.setCurrentDate(localDate.getTime().toInstant());
									visitSchedularDatesDto.setEndDate(newJob.getSchEndDate());
									visitSchedularDatesDto.setStartTime(newJob.getSchStartTime());
									visitSchedularDatesDto.setEndTime(newJob.getSchEndTime());
									visitSchedularDatesDto.setJobId(newJob.getId());
									visitSchedularDatesDto.setStatus(null);
									visitSchedularDatesDto.setChecked(false);
									visitSchedularDatesDto.setNotify(false);
									visitSchedularDatesDto.setTimeZone(timezone.longValue());
									visitSchedularDatesDto.setDriverReminder("0");
									visitSchedularDatesDto.setJobType("recurring job");
									
									visitSchedularDatesDto.setAnyTime(false);
									visitSchedularDatesDto.setDriverNotify(false);
									visitSchedularDatesDto.setTeamMembers(newJob.getTeamAssignMemberIds());
									visitSchedularDatesDto.setDriverMembers(newJob.getAssignMemberIds());
									visitSchedularDatesDto.setVisitTitle(newJob.getJobTitle()+"- #"+newJob.getJobNumber());
									visitSchedularDatesDto.setPaidOnFlag(false);
									logger.debug("Aspected Date ------> " + visitSchedularDatesDto.getCurrentDate());
									visitSchedularDates.add(visitSchedularDatesDto);
								}
								i++;
							}
						}
						/* TODO: Create 10th Day of Month */
						if (visOptional.getId().equals(MONTHLY_10TH_DAY)) {
//							Calendar start = new GregorianCalendar();
//							Calendar end = new GregorianCalendar();
							Calendar localTestDate = new GregorianCalendar();

							start.setTime(Date.from(newJob.getSchStartDate()));

//							end.setTime(Date.from(newJob.getSchEndDate()));
							List<Date> locatDateList = new ArrayList<Date>();
//							Date localStartDate = start.getTime();
//							Date localEndDate = end.getTime();
							localTestDate.setTime(start.getTime());

							int monthsDiff = localTestDate.get(Calendar.MONTH) - end.get(Calendar.MONTH);
							System.out.println("Total Month betwween Date -> " + monthsDiff);

							for (int j = 0; j < Integer.valueOf(newJob.getSchDuration()); j++) {
								System.out.println("Current Date -> " + start.getTime());
								Calendar localDate = new GregorianCalendar();
								localDate.setTime(start.getTime());
								if (!newJob.getSchStartTime().equals(null)) {
									localDate.set(Calendar.HOUR_OF_DAY, Date.from(newJob.getSchStartTime()).getHours());
									localDate.set(Calendar.MINUTE, Date.from(newJob.getSchStartTime()).getMinutes());
								}
								localDate.set(Calendar.DATE, 01);
								localDate.add(localDate.MONTH, j);
//								System.out.println("Current Date -> " + localDate.getTime());
//								System.out.println(
//										"current week of the month -> " + localDate.get(Calendar.WEEK_OF_MONTH));
//								System.out.println("Week of Month -> " + localDate.WEEK_OF_MONTH);
//								localDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//								System.out.println("Monday Date -> " + localDate.getTime());
								localDate.add(Calendar.DAY_OF_MONTH, 9);
//								System.out.println("current week monday's date -> " + localDate.getTime());
//								localDate.set(Calendar.WEEK_OF_MONTH, 2);
//								localDate.set(Calendar.DAY_OF_WEEK, 2);
//								System.out.println("Monday Date Of 2nd week Monday - > " + localDate.getTime());

								if (localDate.after(start) && localDate.before(actualEndDate)) {
									locatDateList.add(localDate.getTime());
									VisitSchedulerDate visitSchedularDatesDto = new VisitSchedulerDate();
									visitSchedularDatesDto.setStartDate(newJob.getSchStartDate());
									visitSchedularDatesDto.setCurrentDate(localDate.getTime().toInstant());
									visitSchedularDatesDto.setEndDate(newJob.getSchEndDate());
									visitSchedularDatesDto.setStartTime(newJob.getSchStartTime());
									visitSchedularDatesDto.setEndTime(newJob.getSchEndTime());
									visitSchedularDatesDto.setJobId(newJob.getId());
									visitSchedularDatesDto.setStatus(null);
									visitSchedularDatesDto.setChecked(false);
									visitSchedularDatesDto.setNotify(false);
									visitSchedularDatesDto.setTimeZone(timezone.longValue());
									visitSchedularDatesDto.setDriverReminder("0");
									visitSchedularDatesDto.setJobType("recurring job");
									
									visitSchedularDatesDto.setAnyTime(false);
									visitSchedularDatesDto.setDriverNotify(false);
									visitSchedularDatesDto.setTeamMembers(newJob.getTeamAssignMemberIds());
									visitSchedularDatesDto.setDriverMembers(newJob.getAssignMemberIds());
									visitSchedularDatesDto.setVisitTitle(newJob.getJobTitle()+"- #"+newJob.getJobNumber());
									visitSchedularDatesDto.setPaidOnFlag(false);
									logger.debug("-> " + visitSchedularDatesDto.getCurrentDate());
									visitSchedularDates.add(visitSchedularDatesDto);
								}
							}
						}

						/* TODO: Custom Field */
						if (visOptional.getId().equals(CUSTOM)) {

						}

					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getCause().getLocalizedMessage());
		}
		return visitSchedularDates;
	}

	public static Date nthWeekdayOfMonth(Date startDate, int dayOfWeek, int month, int year, int week) {
		Calendar calendar = new GregorianCalendar();
		System.out.println("Current Date ->> " + startDate);
		calendar.setTime(startDate);
//		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		calendar.set(Calendar.WEEK_OF_MONTH, week);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, week);
		calendar.set(Calendar.MONTH, month);
//		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}

	public static Date resetTime(Date d) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public List<InvoiceSchedularDate> createInvoiceScheduleTime(Job newJob, Integer timezone) {
		List<InvoiceSchedularDate> invoiceSchedularDates = new ArrayList<InvoiceSchedularDate>();
		if(newJob.getInvoWhen() != null && newJob.getInvoWhen().toLowerCase().equalsIgnoreCase("1")) {
			Calendar start = new GregorianCalendar();
			Calendar end = new GregorianCalendar();
			Calendar actualEndDate = new GregorianCalendar();
			start.setTime(Date.from(newJob.getInvoStartDate()));
			start.set(Calendar.HOUR_OF_DAY, 0);
			start.set(Calendar.MINUTE, 0);
			actualEndDate.setTime(Date.from(newJob.getInvoEndDate()));
//			actualEndDate.set(Calendar.HOUR, Date.from(newJob.getSchEndTime()).getHours());
//			actualEndDate.set(Calendar.MINUTE, Date.from(newJob.getSchEndTime()).getMinutes());
			actualEndDate.getTime().setHours(23);
			actualEndDate.getTime().setMinutes(59);
			/* FILTER BY DUSRATION TYPE */
			switch (newJob.getSchDurationType()) {
			case DAYS:
				end.setTime(Date.from(newJob.getSchStartDate()));
				end.add(Calendar.DATE, Integer.valueOf(newJob.getSchDuration()));
//				System.out.println("current Value Date -> "+end.getTime());
				break;
			case WEEKS:
				end.setTime(Date.from(newJob.getSchStartDate()));
				end.add(Calendar.WEEK_OF_YEAR, Integer.valueOf(newJob.getSchDuration()));
//				System.out.println("current Value Date -> "+end.getTime());
				break;
			case MONTHS:
				end.setTime(Date.from(newJob.getSchStartDate()));
				end.add(Calendar.MONTH, (Integer.valueOf(newJob.getSchDuration())));
//				System.out.println("current Value Date -> "+end.getTime());
				break;
			case YEARS:
				end.setTime(Date.from(newJob.getSchStartDate()));
				end.add(Calendar.YEAR, Integer.valueOf(newJob.getSchDuration()));
//				System.out.println("current Value Date -> "+end.getTime());
				break;

			default:
				break;
			}
//			System.out.println("start Date -> " + start.getTime());
//			System.out.println("End Date -> " + end.getTime());
			
			Calendar localTestDate = new GregorianCalendar();

			start.setTime(Date.from(newJob.getInvoStartDate()));

			List<Date> locatDateList = new ArrayList<Date>();

			localTestDate.setTime(start.getTime());

			int monthsDiff = localTestDate.get(Calendar.MONTH) - end.get(Calendar.MONTH);
			System.out.println("Total Month between Date -> " + monthsDiff);

			for (int j = 0; j < Integer.valueOf(newJob.getSchDuration()); j++) {
//				System.out.println("Current Date -> " + start.getTime());
				Calendar localDate = new GregorianCalendar();
				localDate.setTime(start.getTime());
				if (!newJob.getSchStartTime().equals(null)) {
					localDate.set(Calendar.HOUR_OF_DAY, Date.from(newJob.getSchStartTime()).getHours());
					localDate.set(Calendar.MINUTE, Date.from(newJob.getSchStartTime()).getMinutes());
				}
				localDate.set(Calendar.DATE, 01);
				localDate.add(localDate.MONTH, j);
//				System.out.println("Current Date -> " + localDate.getTime());
//				System.out.println(
//						"current week of the month -> " + localDate.get(Calendar.WEEK_OF_MONTH));
//				System.out.println("Week of Month -> " + localDate.WEEK_OF_MONTH);

//				System.out.println("Monday Date -> " + localDate.getTime());
//				SimpleDateFormat formatter = new SimpleDateFormat();
				//String formattedDate = formatter.format(myDate);
				//localDate.add(Calendar.DAY_OF_MONTH, 9);
				localDate.set(Calendar.DATE, localDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			    //String monthLastDay = formatter.format(localDate.getTime());
//				System.out.println("current month end date -> " + localDate.getTime());

				//System.out.println("Monday Date Of 2nd week Monday - > " + localDate.getTime());
					if (localDate.after(start) && localDate.before(actualEndDate)) {
					locatDateList.add(localDate.getTime());
					InvoiceSchedularDate invoiceSchedularDatesDto = new InvoiceSchedularDate();
					invoiceSchedularDatesDto.setInvStartDate(localDate.getTime().toInstant());
					invoiceSchedularDatesDto.setInvStartTime(newJob.getInvoStartDate());
					invoiceSchedularDatesDto.setInvEndDate(newJob.getInvoEndDate());
					invoiceSchedularDatesDto.setInvEndTime(newJob.getSchEndTime());
					invoiceSchedularDatesDto.setJobId(newJob.getId());
					invoiceSchedularDatesDto.setInvStatus("pending");
					invoiceSchedularDatesDto.setChecked(false);
					invoiceSchedularDatesDto.setTimeZone(timezone.longValue());
					invoiceSchedularDatesDto.setJobType("recurring job");
					invoiceSchedularDatesDto.setInvoiceDate(null);
					invoiceSchedularDatesDto.setPeriodicFlag("Y");
					
					invoiceSchedularDatesDto.setTeamMember(newJob.getTeamAssignMemberIds());
					invoiceSchedularDatesDto.setDriverMember(newJob.getAssignMemberIds());
					invoiceSchedularDatesDto.setTeamNotify(false);
					invoiceSchedularDatesDto.setDriverNotify(false);
					invoiceSchedularDates.add(invoiceSchedularDatesDto);
					}
				}
			
		}
		return invoiceSchedularDates;
	}

}
