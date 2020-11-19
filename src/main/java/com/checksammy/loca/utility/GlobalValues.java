/**
 * 
 */
package com.checksammy.loca.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Abhishek Srivastava
 *
 */
@Component
public class GlobalValues {

	public static String BIN_PHOTOS_DIR_PATH;
	public static String REPORTS_DIR_PATH;
	
	public static String PICKUP_DIR_PATH;
	
	public static String PICKUP_IMAGE_PATH;
	
	public static String LOCATION_IMAGE_PATH;
	public static String BINPHOTO_IMAGE_PATH;
	
	public static String EXTERNAL_FILE_PATH;
	public static String PRODUCT_IMAGE_FILE;
	
	public static String IMPORT_CSV_FILE;
	
	public static String INTERNAL_NOTES_ATTACHMENT;
	
	public static String INVOICE_INTERNAL_NOTES_ATTACHMENT;
	
	public static String QUOTES_PRODUCT_ATTACHMENT;
	
	public static String INVOICE_PRODUCT_ATTACHMENT;
	
	public static String JOB_INTERNAL_NOTES_ATTACHMENT;
	
	public static String JOB_EXPENSES_ATTACHMENT;
	
	public static String SET_USER_CHAT_IMAGE;
	
	public static String SAVE_HAULER_COMPANY_PATH;
	
	public static String UPLOAD_PROFILE_PIC;
	
	public static String EMAIL_FILE_PATH;
	
	public static String SMS_ACCESS_TOKEN;
	
	public static String VISIT_REPORT_FILE_PATH;
	
	public static String TRANSACTION_REPORT_FILE_PATH;
	
	
	@Value("${loca.binphotos.dir.path}")
	public void setLocABinPhotosDirPath(String binPhotosDirPath) {
		BIN_PHOTOS_DIR_PATH = binPhotosDirPath;
	}
	
	@Value("${loca.reports.dir.path}")
	public void setReportsDirPath(String reportsDirPath) {
		REPORTS_DIR_PATH = reportsDirPath;
	}
	
	@Value("${loca.propmanager.pickup.dir.path}")
	public void setPickupDirPath(String pickupDirPath) {
		PICKUP_DIR_PATH = pickupDirPath;
	}
	
	@Value("${loca.propmanager.pickup.image.path}")
	public void setPickupImagePath(String pickupImagePath) {
		PICKUP_IMAGE_PATH = pickupImagePath;
	}
	
	@Value("${loca.location.dir.path}")
	public void setLocationImagePath(String locationImagePath) {
		LOCATION_IMAGE_PATH = locationImagePath;
	}
	
	@Value("${loca.binphotos.path}")
	public void setNewImagePathBinPhoto(String binPhotoSet) {
		BINPHOTO_IMAGE_PATH = binPhotoSet;
	}
	
	@Value("${loca.request.dir.path}")
	public void setRequestAttachmentPath(String attachment) {
		EXTERNAL_FILE_PATH = attachment;
	}
	
	@Value("${loca.request.csv.path}")
	public void setImportCsvFile(String importCsvFile) {
		IMPORT_CSV_FILE = importCsvFile;
	}
	
	@Value("${loca.product.dir.path}")
	public void setProductImageFile(String productFile) {
		PRODUCT_IMAGE_FILE = productFile;
	}
	
	@Value("${loca.internote.dir.path}")
	public void setInternalNotesAttachment(String internalNotes) {
		INTERNAL_NOTES_ATTACHMENT = internalNotes;
	}
	
	@Value("${loca.quoteproduct.dir.path}")
	public void setQuotesProductAttachment(String internalNotes) {
		QUOTES_PRODUCT_ATTACHMENT = internalNotes;
	}
//	JOb Internal Note
	@Value("${loca.job.internote.dir.path}")
	public void setJObInternalNotesAttachment(String jobInternalNotes) {
		JOB_INTERNAL_NOTES_ATTACHMENT = jobInternalNotes;
	}
	
	@Value("${loca.job.expenses.dir.path}")
	public void setJObExpensesAttachment(String jobExpenses) {
		JOB_EXPENSES_ATTACHMENT = jobExpenses;
	}
	
	@Value("${loca.user.chat.path}")
	public void setUserChatImage(String userChatSaveImage) {
		SET_USER_CHAT_IMAGE = userChatSaveImage;
	}
	
	@Value("${loca.hauler.company.path}")
	public void setHaulerCampanyExport(String haulerCompany) {
		SAVE_HAULER_COMPANY_PATH = haulerCompany;
	}
	
	@Value("${loca.invoice.product.path}")
	public void setInvoiceProductAttachment(String invoiceProductAttachment) {
		INVOICE_PRODUCT_ATTACHMENT = invoiceProductAttachment;
	}
	
	@Value("${loca.invoice.note.path}")
	public void setInvoiceNoteAttachment(String invoiceNotesAttachment) {
		INVOICE_INTERNAL_NOTES_ATTACHMENT = invoiceNotesAttachment;
	}
	
	@Value("${loca.profile.pic.path}")
	public void setProfilePicImage(String profilePicPath) {
		UPLOAD_PROFILE_PIC = profilePicPath;
	}
	
	@Value("${loca.email.file.path}")
	public void setEmailFilePath(String emailFilePath) {
		EMAIL_FILE_PATH = emailFilePath;
	}
	
	@Value("${loca.sms.access.token}")
	public void smsAccessTocken(String accessTocken) {
		SMS_ACCESS_TOKEN = accessTocken;
	}
	
	@Value("${loca.visit.report.file.path}")
	public void setVisitReportFilePath(String visitReportFilePath) {
		VISIT_REPORT_FILE_PATH = visitReportFilePath;
	}
	
	@Value("${loca.transaction.report.file.path}")
	public void setTransactionReportFilePath(String visitReportFilePath) {
		TRANSACTION_REPORT_FILE_PATH = visitReportFilePath;
	}
	
}
