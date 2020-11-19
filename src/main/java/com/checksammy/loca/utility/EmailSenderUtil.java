package com.checksammy.loca.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.EmailDto;
import com.checksammy.loca.dto.EmailList;
import com.checksammy.loca.dto.JobDto;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.User;
import com.checksammy.loca.repository.LocationRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.service.EmailService;
import com.checksammy.loca.serviceImpl.JobServiceImpl;

@Service
public class EmailSenderUtil {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private LocationRepository locationRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

	public void sendEmailMethod(long[] userIds, Job newJob) {
		try {

			List<User> emailUser = userRepo.findUserByAssignIds(userIds);
			EmailDto emailDto = new EmailDto();

			for (User user : emailUser) {
				List<EmailList> emailLists = new ArrayList<EmailList>();
				EmailList emailList = new EmailList();
				emailList.setName(user.getUsername());
				emailLists.add(emailList);

				String title = "";
				if (newJob.getId() != null && newJob.getId() > 0) {
					title = "New job assign";
				} else {
					title = "Job updated";
				}

//				if (userType.equalsIgnoreCase(DRIVER)) {
//					Instant timeStamp = Instant.now();
				try {
					String emailContent = "<html>\r\n" + "<div><h3>Hi " + user.getFirstName() + " " + user.getLastName()
							+ ", </h3>\r\n" + "</div>\r\n" + "<div>\r\n" + "<h3>A new job - #" + newJob.getJobNumber()
							+ " - has been assigned to you. Please go to the CS LocA app to accept or reject the job. </h3>\r\n<p><b>Kind Regards</b><br><b>CheckSammy Operations</b></p>\r\n"
							+ "</div>\r\n" + "</html>";
					emailDto.setEditorHtml(emailContent);
					emailDto.setSubject("Job Assigned - CS LocA");
					emailDto.setSendToEmails(emailLists);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage());
				}
//				}
				emailService.sendEmailToMutipleRecipientAssignUser(emailDto);
			}

		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}
	}
	
	public void sendEmailMethodTeamByVisit(long[] teamIds, Job saveJob, long[] driverIdsList,
			Integer timezone) {
		try {

			List<User> emailUser = userRepo.findUserByAssignIds(teamIds);
			EmailDto emailDto = new EmailDto();
			for (User user : emailUser) {
				List<EmailList> emailLists = new ArrayList<EmailList>();
				EmailList emailList = new EmailList();
				emailList.setName(user.getUsername());
				emailLists.add(emailList);

//			if (team2.equalsIgnoreCase(TEAM)) {
//				Instant timeStamp = Instant.now();
				try {
					List<User> driverList = userRepo.findUserByAssignIds(driverIdsList);
					List<String> driverNameList = new ArrayList<String>();
					for (User driver : driverList) {
						driverNameList.add(driver.getFirstName() + " " + driver.getLastName());
					}
					String completeDriverName = String.join(",", driverNameList);
					Optional<Location> locatiOptional = locationRepo.findById(saveJob.getLocationId().getId());

					Calendar forTime = new GregorianCalendar();
					try {
						forTime.setTime(Date.from(saveJob.getSchStartTime()));
						forTime.add(Calendar.MINUTE, -(timezone));
					} catch (Exception e) {
					}

					String formattedDate = "";
					try {
						String strDateFormat = "hh:mm a";
						DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
						formattedDate = dateFormat.format(forTime.getTime());
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (saveJob.getSchLater()) {

					}
					Calendar calendar = new GregorianCalendar();
					try {
						calendar.setTime(Date.from(saveJob.getSchStartDate()));
						calendar.add(Calendar.MINUTE, -(timezone));
					} catch (Exception e) {
						// TODO: handle exception
					}

					String emailContent = "<html>\r\n" + "<div><h3>Hi " + user.getFirstName() + " " + user.getLastName()
							+ ", </h3>\r\n" + "</div>\r\n" + "<div>\r\n" + "<h3>A new job - #" + saveJob.getJobNumber()
							+ " - has been assigned to you. Driver, " + completeDriverName
							+ ", has been assigned for this job.</h2>\r\n" + "<p>Date for the job - "
							+ (saveJob.getSchLater() ? "unschedule"
									: new SimpleDateFormat("MM/dd/YYYY").format(calendar.getTime()))
							+ "</p>\r\n" + "<p>Time of the job - " + formattedDate + "</p>\r\n" + "<p>Location - "
							+ locatiOptional.get().getPropertyName() + "</p>\r\n" + "<p>Thanks</p>\r\n" + "</div>\r\n"
							+ "</html>";
//					
					emailDto.setEditorHtml(emailContent);
					emailDto.setSubject("Job Assigned - CS LocA");
					emailDto.setSendToEmails(emailLists);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage());
				}
//			}
				emailService.sendEmailToMutipleRecipientAssignUser(emailDto);
			}

		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}

	}
}
