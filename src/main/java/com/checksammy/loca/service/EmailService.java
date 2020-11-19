package com.checksammy.loca.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.EmailDto;
import com.checksammy.loca.dto.EmailList;
import com.checksammy.loca.model.User;
import com.checksammy.loca.utility.GlobalValues;

/**
 *
 * @author Jeevan Kulkarni
 */

@Service("emailService")
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String supportEmail;

	@Value("${spring.mail.password}")
	private String supportPassword;

	@Value("${spring.mail.port}")
	private String supportPort;

	@Value("${spring.mail.host}")
	private String supportHost;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String supportAuth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String supportEnable;

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Async
	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);
	}

	public void sendEmailHtml(String htmlContains, String email, String subject) throws javax.mail.MessagingException {
		try {
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(htmlContains.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(email);
			mimeMessHelp.setSubject(subject);
			mailSender.send(mimemessage);
		} catch (Exception e) {
			logger.info("Something is wrong with email. Please try again.");
		}
	}

	public void sendSupportEmailHtml(String htmlContains, String fromEmail, String toEmail, String subject)
			throws javax.mail.MessagingException {
		try {
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(htmlContains.trim(), "text/html");
			mimeMessHelp.setFrom(fromEmail);
			mimeMessHelp.setTo(toEmail);
			mimeMessHelp.setSubject(subject);
			mailSender.send(mimemessage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Something is wrong with email. Please try again.");
		}
	}

	public void sendEmailToMutipleRecipient(EmailDto cateringEmailDto) {
		Integer senToEmailCount = cateringEmailDto.sendToEmails.size();
		Integer ccEmailCount = cateringEmailDto.ccEmails.size();
		Integer counter = 0;
		Integer counter2 = 0;
		try {
			InternetAddress recipientAddress[] = new InternetAddress[senToEmailCount];
			for (EmailList recipient : cateringEmailDto.sendToEmails) {
				recipientAddress[counter] = new InternetAddress(recipient.name.trim());
				counter++;
			}
			InternetAddress ccAddress2[] = new InternetAddress[ccEmailCount];
			for (EmailList recipient2 : cateringEmailDto.ccEmails) {
				ccAddress2[counter2] = new InternetAddress(recipient2.name.trim());
				counter2++;
			}
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(cateringEmailDto.editorHtml.trim(), "text/html");
			mimeMessHelp.setFrom("experienceamericacamps@gmail.com");
			mimeMessHelp.setTo(recipientAddress);
			mimeMessHelp.setCc(ccAddress2);
			mimeMessHelp.setSubject(cateringEmailDto.subject);
			mailSender.send(mimemessage);
		} catch (Exception e) {
			logger.info("Something is wrong with email. Please try again.");
		}

	}

	public void sendEmailToTour(EmailDto cateringEmailDto) {
		Integer senToEmailCount = cateringEmailDto.sendToEmails.size();
		Integer counter = 0;
		try {
			InternetAddress recipientAddress[] = new InternetAddress[senToEmailCount];
			for (EmailList recipient : cateringEmailDto.sendToEmails) {
				recipientAddress[counter] = new InternetAddress(recipient.name.trim());
				counter++;
			}
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(cateringEmailDto.getEditorHtml(), "text/html");
			mimeMessHelp.setFrom("experienceamericacamps@gmail.com");
			mimeMessHelp.setTo(recipientAddress);
			mimeMessHelp.setSubject(cateringEmailDto.subject);
			mailSender.send(mimemessage);
		} catch (Exception e) {
			logger.info("Something is wrong with email. Please try again.");
		}
	}

	public String sendSupportEmailHtmlFormatToUser(String emailContent, String fromEmail, String toEmail,
			String subject) {
		String status = "";
		try {

			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(emailContent.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(fromEmail);
			mimeMessHelp.setReplyTo(toEmail);
			mimeMessHelp.setSubject(subject);
			mailSender.send(mimemessage);
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Fail";
			logger.info("Something is wrong with email. Please try again.");
		}
		return status;
	}

	public String sendSupportEmailHtmlContactPage(String context, String fromEmail, String subject) {
		String status = "";
		try {
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(context.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(supportEmail);
			mimeMessHelp.setReplyTo(fromEmail);
			mimeMessHelp.setSubject(subject);
			mailSender.send(mimemessage);
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Fail";
			logger.info("Something is wrong with email. Please try again.");
		}
		return status;
	}

//	 Send Email To Team and assign member
	public void sendEmailToMutipleRecipientAssignUser(EmailDto cateringEmailDto) {
		Integer senToEmailCount = cateringEmailDto.sendToEmails.size();
//		Integer ccEmailCount = cateringEmailDto.ccEmails.size();
		Integer counter = 0;
//		Integer counter2 = 0;
		try {
			InternetAddress recipientAddress[] = new InternetAddress[senToEmailCount];
			for (EmailList recipient : cateringEmailDto.sendToEmails) {
				recipientAddress[counter] = new InternetAddress(recipient.name.trim());
				counter++;
			}
//			InternetAddress ccAddress2[] = new InternetAddress[ccEmailCount];
//			for (EmailList recipient2 : cateringEmailDto.ccEmails) {
//				ccAddress2[counter2] = new InternetAddress(recipient2.name.trim());
//			    counter2++;
//			}
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(cateringEmailDto.editorHtml.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(recipientAddress);
//			mimeMessHelp.setCc(ccAddress2);
			mimeMessHelp.setSubject(cateringEmailDto.subject);
			mailSender.send(mimemessage);
		} catch (Exception e) {
			logger.info("Something is wrong with email. Please try again.");
		}
	}

	public String sendMailToClientAsCompleted(String emailContent, String toUser, String toEmail, String subject) {
		String status = "";
		try {

			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(emailContent.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(toUser);
			mimeMessHelp.setReplyTo(supportEmail);
			mimeMessHelp.setSubject(subject);

			mailSender.send(mimemessage);
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Fail";
			logger.info("Something is wrong with email. Please try again.");
		}
		return status;
	}

	/**/
	public void sendMultipleRecipent(String emailContent, List<String> toEmail, List<String> ccEmail, String subject,
			MultipartFile[] multipartFiles, boolean b) {
		Integer senToEmailCount = toEmail.size();
		Integer ccEmailCount = ccEmail.size();
		Integer counter = 0;
		Integer counter2 = 0;

		try {
			InternetAddress recipientAddress[] = new InternetAddress[senToEmailCount];
			for (String recipient : toEmail) {
				recipientAddress[counter] = new InternetAddress(recipient.trim());
				counter++;
			}
			InternetAddress ccAddress2[] = new InternetAddress[ccEmailCount];
			for (String recipient2 : ccEmail) {
				ccAddress2[counter2] = new InternetAddress(recipient2.trim());
				counter2++;
			}
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(emailContent.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(recipientAddress);
			mimeMessHelp.setCc(ccAddress2);
			mimeMessHelp.setSubject(subject);

			Multipart multipartFileContent = new MimeMultipart();
			try {
				File dir = new File(GlobalValues.EMAIL_FILE_PATH + File.separator + "files");
				if (!b) {
					for (int i = 0; i < multipartFiles.length; i++) {
						MultipartFile file = multipartFiles[i];
						try {
							logger.info("Directory Name: " + dir.getAbsolutePath());
							if (!dir.exists())
								dir.mkdirs();
							logger.info("Directory created.");
//						File uploadFile = convert(file);
//						System.out.println("uploadFile.getAbsolutePath : " + uploadFile.getAbsolutePath());

//						String fileName = ATTACHMENTS + "_" + requestId + "_" + System.currentTimeMillis() + "." + getFileExtension(uploadFile);
							String fileName = file.getOriginalFilename();
							logger.info("File Name: " + fileName);
							byte[] bytes;
							String fileWithPath = dir.getAbsolutePath() + File.separator + fileName;
							logger.info("fileWithPath: " + fileWithPath);
							bytes = file.getBytes();
							Path path = Paths.get(fileWithPath);
							System.out.println("path ->" + path.getFileName());
							System.out.println("fileParent ->" + path.getParent());
							Files.write(path, bytes);

							BodyPart messageBodyPart = new MimeBodyPart();
							messageBodyPart = new MimeBodyPart();
							DataSource source = new FileDataSource(fileWithPath);
							messageBodyPart.setDataHandler(new DataHandler(source));
							messageBodyPart.setFileName(fileName);
							multipartFileContent.addBodyPart(messageBodyPart);

						} catch (Exception e) {
							logger.debug("Failed to upload " + file.getOriginalFilename() + " " + e.getMessage());
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			BodyPart messageBodyPart2 = new MimeBodyPart();
			String htmlText = emailContent.trim();
			messageBodyPart2.setContent(htmlText, "text/html");
			multipartFileContent.addBodyPart(messageBodyPart2);

			mimemessage.setContent(multipartFileContent);
			mailSender.send(mimemessage);
		} catch (Exception e) {
			logger.info("Something is wrong with email. Please try again.");
			System.out.println("find what happen -> " + e.getMessage());
		}
	}

//	TODO: Reminder mail
	public String sendReminderMailToTeam(String emailContent, List<String> teamUser, String subject) {
		String status = "";
		try {
			Integer senToEmailCount = teamUser.size();
			Integer counter = 0;

			InternetAddress recipientAddress[] = new InternetAddress[senToEmailCount];
			for (String recipient : teamUser) {
				recipientAddress[counter] = new InternetAddress(recipient.trim());
				counter++;
			}

			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(emailContent.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(recipientAddress);
			mimeMessHelp.setReplyTo(supportEmail);
			mimeMessHelp.setSubject(subject);

			mailSender.send(mimemessage);
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Fail";
			logger.info("Something is wrong with email. Please try again.");
		}
		return status;
	}

	public String sendSupportEmailFeedbackSummary(String context, String fromEmail, String subject) {
		String status = "";
		try {
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(context.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(fromEmail);
			mimeMessHelp.setReplyTo(fromEmail);
			mimeMessHelp.setSubject(subject);
			mailSender.send(mimemessage);
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Fail";
			logger.info("Something is wrong with email. Please try again.");
		}
		return status;
	}

	public void setEmailToSingleUser(String toEmail, String emailContent) {
		String subject = "Visits Report";
		try {		
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(emailContent.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(toEmail);
			mimeMessHelp.setSubject(subject);

			Multipart multipartFileContent = new MimeMultipart();
			try {
				File dir = new File(GlobalValues.VISIT_REPORT_FILE_PATH + File.separator + "files");
						try {
							String fileName = "Visits Report.xls";
							logger.info("File Name: " + fileName);
							//byte[] bytes;
							String fileWithPath = dir.getAbsolutePath() + File.separator + fileName;
							logger.info("fileWithPath: " + fileWithPath);
							
							BodyPart messageBodyPart = new MimeBodyPart();
							messageBodyPart = new MimeBodyPart();
							DataSource source = new FileDataSource(fileWithPath);
							messageBodyPart.setDataHandler(new DataHandler(source));
							messageBodyPart.setFileName(fileName);
							multipartFileContent.addBodyPart(messageBodyPart);

						} catch (Exception e) {
							logger.debug("Failed to upload Visits Report " + e.getMessage());
						}
			} catch (Exception e) {
				// TODO: handle exception
			}

			BodyPart messageBodyPart2 = new MimeBodyPart();
			String htmlText = emailContent.trim();
			messageBodyPart2.setContent(htmlText, "text/html");
			multipartFileContent.addBodyPart(messageBodyPart2);

			mimemessage.setContent(multipartFileContent);
			mailSender.send(mimemessage);
		} catch (Exception e) {
			logger.info("Something is wrong with email. Please try again.");
			System.out.println("find what happen -> " + e.getMessage());
		}
	}

	public void sendTransactionAttachmentEmail(String toEmail, String emailContent) {
		String subject = "Transaction Report";
		try {		
			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(emailContent.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(toEmail);
			mimeMessHelp.setSubject(subject);

			Multipart multipartFileContent = new MimeMultipart();
			try {
				File dir = new File(GlobalValues.TRANSACTION_REPORT_FILE_PATH + File.separator + "files");
						try {
							String fileName = "Transaction Report.xls";
							logger.info("File Name: " + fileName);
							//byte[] bytes;
							String fileWithPath = dir.getAbsolutePath() + File.separator + fileName;
							logger.info("fileWithPath: " + fileWithPath);
							
							BodyPart messageBodyPart = new MimeBodyPart();
							messageBodyPart = new MimeBodyPart();
							DataSource source = new FileDataSource(fileWithPath);
							messageBodyPart.setDataHandler(new DataHandler(source));
							messageBodyPart.setFileName(fileName);
							multipartFileContent.addBodyPart(messageBodyPart);

						} catch (Exception e) {
							logger.debug("Failed to upload Transaction Report " + e.getMessage());
						}
			} catch (Exception e) {
				// TODO: handle exception
			}

			BodyPart messageBodyPart2 = new MimeBodyPart();
			String htmlText = emailContent.trim();
			messageBodyPart2.setContent(htmlText, "text/html");
			multipartFileContent.addBodyPart(messageBodyPart2);

			mimemessage.setContent(multipartFileContent);
			mailSender.send(mimemessage);
		} catch (Exception e) {
			logger.info("Something is wrong with email. Please try again.");
			System.out.println("find what happen -> " + e.getMessage());
		}
	}

	public void sendEmailWithLinkOfPayment(String emailContent, String username, String subject) {
		String status = "";
		try {

			MimeMessage mimemessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessHelp = new MimeMessageHelper(mimemessage, true);
			mimemessage.setContent(emailContent.trim(), "text/html");
			mimeMessHelp.setFrom(supportEmail);
			mimeMessHelp.setTo(username);
			mimeMessHelp.setReplyTo(supportEmail);
			mimeMessHelp.setSubject(subject);

			mailSender.send(mimemessage);
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Fail";
			logger.info("Something is wrong with email. Please try again.");
		}
	}

}
