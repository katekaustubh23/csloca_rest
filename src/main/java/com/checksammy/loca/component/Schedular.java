package com.checksammy.loca.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.checksammy.loca.model.JobForScheduler;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.VisitSchedulerDate;
import com.checksammy.loca.repository.JobDriverMappingRepository;
import com.checksammy.loca.repository.JobForSchedulerRepository;
import com.checksammy.loca.repository.JobRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.repository.VisitSchedulerDateRepository;
import com.checksammy.loca.service.EmailService;

@Component
public class Schedular {
	private static final Logger log = LoggerFactory.getLogger(Schedular.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a");

	@Autowired
	private UserRepository userRepo;

//	@Autowired
//	private JobRepository jobRepository;

	@Autowired
	private VisitSchedulerDateRepository visiDateRepository;

	@Autowired
	private JobDriverMappingRepository jobMappingRepository;

	@Autowired
	private JobForSchedulerRepository jobForSchedulerRepo;

	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "0 */2 * ? * *")
	public void sendNotificationOnCronJob() {

		Calendar startDate = new GregorianCalendar();
		Calendar endDate = new GregorianCalendar();
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
//		startDate
		List<VisitSchedulerDate> visitSchedulerDates = visiDateRepository.findByCurrentDate();
		try {
			for (VisitSchedulerDate visitSchedulerDate : visitSchedulerDates) {

				Calendar CurrentDate = new GregorianCalendar();
				Calendar visitDate = new GregorianCalendar();
				visitDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				/* Just to display */
				startDate.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
				startDate.add(Calendar.MINUTE,
						-((visitSchedulerDate.getTimeZone() != null) ? visitSchedulerDate.getTimeZone().intValue()
								: 0));

				/*-- -----------*/
				CurrentDate.add(Calendar.MINUTE,
						-((visitSchedulerDate.getTimeZone() != null) ? visitSchedulerDate.getTimeZone().intValue()
								: 0));
				visitDate.add(Calendar.MINUTE,
						-((visitSchedulerDate.getTimeZone() != null) ? visitSchedulerDate.getTimeZone().intValue()
								: 0));
				System.out.println("current date-> " + CurrentDate.getTime());
				System.out.println("visit  date-> " + visitDate.getTime());

				long diffInMillies = visitDate.getTime().getTime() - CurrentDate.getTime().getTime();
				long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
				System.out.println("time diff ->" + diff);

				Optional<JobForScheduler> jobForschOptional = jobForSchedulerRepo
						.findById(visitSchedulerDate.getJobId());
				if (jobForschOptional.isPresent()) {
					System.out.println();
					if (!visitSchedulerDate.getNotify()) {
						if (jobForschOptional.get().getTeamUserReminder()) {
							long[] teamMember = null;
							if (diff > 0 && diff <= Long.valueOf(jobForschOptional.get().getTeamSendNotify())) {
								if (jobForschOptional.get().getTeamAssignMemberIds() != null
										&& jobForschOptional.get().getTeamAssignMemberIds() != "") {
									teamMember = Arrays
											.asList(jobForschOptional.get().getTeamAssignMemberIds().split(","))
											.stream().map(String::trim).mapToLong(Long::parseLong).toArray();
									List<User> teamMemberUser = userRepo.findUserByAssignIds(teamMember);
									List<String> userEmail = new ArrayList<String>();
									User createdUser = userRepo.findByUserId(jobForschOptional.get().getCreatedBy());
									for (User user : teamMemberUser) {
										userEmail.add(user.getUsername());
									}

									String formattedDate = "";
									String strDateFormat = "hh:mm a";
									DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
									formattedDate = dateFormat.format(visitDate.getTime());

									String sendDate = "";
									SimpleDateFormat DateFor = new SimpleDateFormat("MMM dd yyyy");
									sendDate = DateFor.format(visitDate.getTime());
//									System.out.println(sendDate);

									String emailContent = "<html>\r\n" + "\r\n" + "<body>\r\n" + "<div >\r\n"
											+ "		<p >Hello, " + ", </p>\r\n" + "	</div>\r\n" + "	<div >\r\n"
											+ "		<p >Just a friendly reminder that we have an upcoming appointment.</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p>Date: " + sendDate + " </p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p>Where: "
											+ jobForschOptional.get().getLocationId().getStreetNumber() + " "
											+ jobForschOptional.get().getLocationId().getStreetName() + " "
											+ jobForschOptional.get().getLocationId().getCity() + " "
											+ jobForschOptional.get().getLocationId().getCountry() + " "
											+ jobForschOptional.get().getLocationId().getPinCode() + "</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p >Time: " + formattedDate
											+ " </p>\r\n" + "	</div>\r\n" + "	<div >\r\n"
											+ "		<p >Our team will arrive within a 2 hour window of your scheduled time.</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n"
											+ "		<p >If you have any questions or concerns, please don't\r\n"
											+ "			hesitate to get in touch with us at operations@checksammy.com. </p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p >Sincerely,<br> "
											+ (createdUser != null ? createdUser.getFirstName() : "") + "</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p >\r\n"
											+ "			CheckSammy Inc.<br>Reminder Email\r\n" + "		</p>\r\n"
											+ "	</div>\r\n" + "</html>";
									String subject = "Reminder mail";
									emailService.sendReminderMailToTeam(emailContent, userEmail, subject);
									visiDateRepository.updateCompleteReminder(visitSchedulerDate.getId());
								}
							}
						}
					}

//					-------------------------------------------------------------
					if (visitSchedulerDate.getDriverNotify() != null && !visitSchedulerDate.getDriverNotify()) {
						if (jobForschOptional.get().getDriverUserReminder() != null && jobForschOptional.get().getDriverUserReminder()) {
							long[] driverMember = null;
							if (diff > 0 && diff <= Long.valueOf(jobForschOptional.get().getDriverEmailNotify())) {
								if (jobForschOptional.get().getAssignMemberIds() != null
										&& jobForschOptional.get().getAssignMemberIds() != "") {
									driverMember = Arrays
											.asList(jobForschOptional.get().getAssignMemberIds().split(",")).stream()
											.map(String::trim).mapToLong(Long::parseLong).toArray();
									List<User> driverMemberUser = userRepo.findUserByAssignIds(driverMember);
									List<String> userEmail = new ArrayList<String>();
									User createdUser = userRepo.findByUserId(jobForschOptional.get().getCreatedBy());
									for (User user : driverMemberUser) {
										userEmail.add(user.getUsername());
									}
									String formattedDate = "";
									String strDateFormat = "hh:mm a";
									DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
									formattedDate = dateFormat.format(visitDate.getTime());

									String sendDate = "";
									SimpleDateFormat DateFor = new SimpleDateFormat("MMM dd yyyy");
									sendDate = DateFor.format(visitDate.getTime());
//									System.out.println(sendDate);

									String emailContent = "<html>\r\n" + "\r\n" + "<body>\r\n" + "<div >\r\n"
											+ "		<p >Hello, " + ", </p>\r\n" + "	</div>\r\n" + "	<div >\r\n"
											+ "		<p >Just a friendly reminder that we have an upcoming appointment.</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p>Date: " + sendDate + " </p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p>Where: "
											+ jobForschOptional.get().getLocationId().getStreetNumber() + " "
											+ jobForschOptional.get().getLocationId().getStreetName() + " "
											+ jobForschOptional.get().getLocationId().getCity() + " "
											+ jobForschOptional.get().getLocationId().getCountry() + " "
											+ jobForschOptional.get().getLocationId().getPinCode() + "</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p >Time: " + formattedDate
											+ " </p>\r\n" + "	</div>\r\n" + "	<div >\r\n"
											+ "		<p >Our team will arrive within a 2 hour window of your scheduled time.</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n"
											+ "		<p >If you have any questions or concerns, please don't\r\n"
											+ "			hesitate to get in touch with us at operations@checksammy.com. </p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p >Sincerely,<br> "
											+ (createdUser != null ? createdUser.getFirstName() : "") + "</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p >\r\n"
											+ "			CheckSammy Inc.<br>Reminder Email\r\n" + "		</p>\r\n"
											+ "	</div>\r\n" + "</html>";
									String subject = "Reminder mail";
									emailService.sendReminderMailToTeam(emailContent, userEmail, subject);
									visiDateRepository.updateCompleteDriverReminder(visitSchedulerDate.getId());
								}
							}
						}
					}
//					-------------------------------------------------------------
					if (jobForschOptional.get().getAssignUserReminderType() != null
							&& !jobForschOptional.get().getAssignUserReminderType().equalsIgnoreCase("")) {

						if (jobForschOptional.get().getAssignUserReminderType()
								.equalsIgnoreCase("2_hour_before_text")) {
							if (!visitSchedulerDate.getDriverReminder().equalsIgnoreCase("1")) {
								if (diff > 0 && diff <= Long.valueOf(120)) {

								}
							}

						}
						if (jobForschOptional.get().getAssignUserReminderType().equalsIgnoreCase("email_on_date")) {
							List<String> userEmail = new ArrayList<String>();
							System.out.println("current Date into " + CurrentDate.getTime());
							if (!visitSchedulerDate.getDriverReminder().equalsIgnoreCase("2")) {
								if (diff > 0 && diff <= Long.valueOf(1440)) {
									User user = userRepo.findByUserId(jobForschOptional.get().getUserId());
									User createdUser = userRepo.findByUserId(jobForschOptional.get().getCreatedBy());
									userEmail.add(user.getUsername());
									String formattedDate = "";
									String strDateFormat = "hh:mm a";
									DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
									formattedDate = dateFormat.format(visitDate.getTime());

									String sendDate = "";
									SimpleDateFormat DateFor = new SimpleDateFormat("MMM dd yyyy");
									sendDate = DateFor.format(visitDate.getTime());
//									System.out.println(sendDate);

									String emailContent = "<html>\r\n" + "\r\n" + "<body>\r\n" + "<div >\r\n"
											+ "		<p >Hello " + user.getFirstName() + " " + user.getLastName()
											+ ", </p>\r\n" + "	</div>\r\n" + "	<div >\r\n"
											+ "		<p >Just a friendly reminder that we have an upcoming appointment.</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p>Date: " + sendDate + " </p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p>Where: "
											+ jobForschOptional.get().getLocationId().getStreetNumber() + " "
											+ jobForschOptional.get().getLocationId().getStreetName() + " "
											+ jobForschOptional.get().getLocationId().getCity() + " "
											+ jobForschOptional.get().getLocationId().getCountry() + " "
											+ jobForschOptional.get().getLocationId().getPinCode() + "</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p >Time: " + formattedDate
											+ " </p>\r\n" + "	</div>\r\n" + "	<div >\r\n"
											+ "		<p >Our team will arrive within a 2 hour window of your scheduled time.</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n"
											+ "		<p >If you have any questions or concerns, please don't\r\n"
											+ "			hesitate to get in touch with us at operations@checksammy.com. </p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p >Sincerely,<br> "
											+ (createdUser != null ? createdUser.getFirstName() : "") + "</p>\r\n"
											+ "	</div>\r\n" + "	<div >\r\n" + "		<p >\r\n"
											+ "			CheckSammy Inc.<br>Reminder Email\r\n" + "		</p>\r\n"
											+ "	</div>\r\n" + "</html>";
									String subject = "Reminder mail";
									emailService.sendReminderMailToTeam(emailContent, userEmail, subject);
									visiDateRepository.updateCustomerReminder(visitSchedulerDate.getId());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}

//		List<Job> jobList = jobRepository.findByJobListByTodaysDate(startDate.getTime(), endDate.getTime());

		log.info("The time is now {}", dateFormat.format(startDate.getTime()));
	}
}
