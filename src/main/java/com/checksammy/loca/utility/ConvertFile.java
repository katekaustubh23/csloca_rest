package com.checksammy.loca.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinContent;
import com.checksammy.loca.model.BinDestination;
import com.checksammy.loca.model.BinDetailsView;
import com.checksammy.loca.model.BinHistoryViewWeb;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.ProductService;
import com.checksammy.loca.model.ServiceType;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

public class ConvertFile {

	// Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

//	private static String URL_FOR_DOC = "http://3.88.196.126:8081/static/LocA/BinPhotos/";
	private static String URL_FOR_DOC = GlobalValues.BINPHOTO_IMAGE_PATH;

	// private static final String url = "C:/LocA/BinPhotos/"; GlobalValues

	private static final Logger logger = LoggerFactory.getLogger(ConvertFile.class);

	public void createPdf(String dest, BinHistoryViewWeb binHistoryViewWeb, List<BinDetailsView> binDetails,
			Long timeZone, List<BinContent> binContents, Optional<Location> distinationLocation)
			throws DocumentException, IOException, ResourceNotFoundException {
		try {

			Instant timeStamp = binHistoryViewWeb.getLastModifiedTS().minus(timeZone, ChronoUnit.MINUTES);
			DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
					.withZone(ZoneOffset.UTC);

//			System.out.println(DATE_TIME_FORMATTER.format(timeStamp));

			String[] attachmentFileNames = null;

			attachmentFileNames = binHistoryViewWeb.getAttachments().split(",");

			Font regular = new Font(FontFamily.HELVETICA, 11);
			Font bigBold = new Font(FontFamily.HELVETICA, 21, Font.BOLD);
			Font bold = new Font(FontFamily.HELVETICA, 11, Font.BOLD);
			System.out.println("locationId ==> " + binHistoryViewWeb.getLocationId());
			System.out.println("binLocationId ==> " + binHistoryViewWeb.getBinLocationId());

			OutputStream file = new FileOutputStream(new File(dest));

			Document document = new Document(PageSize.A4.rotate());
			PdfWriter.getInstance(document, file);

			Paragraph upperTitle = new Paragraph();
			Paragraph pUpload = new Paragraph();
			Paragraph pBinType = new Paragraph();
			Paragraph pLocation = new Paragraph();
			Paragraph pDesLocation = new Paragraph();

			Paragraph pBinContentTitle = new Paragraph();
			Paragraph pBinContentValues = new Paragraph();
			Paragraph totalWeight = new Paragraph();
			Paragraph attachment = new Paragraph();

			Chunk title = new Chunk("Bin Details ", bigBold);
			upperTitle.add(title);

			Chunk uploadBy = new Chunk("Uploaded By: ", bold);
			Chunk uploadname = new Chunk(
					binHistoryViewWeb.getUserFirstname() + " " + binHistoryViewWeb.getUserLastname(), regular);
			pUpload.add(uploadBy);
			pUpload.add(uploadname);

			Chunk binTypeTitle = new Chunk("Bin Type: ", bold);
			Chunk binType = new Chunk(binHistoryViewWeb.getBinTypeName(), regular);
//		System.out.println("Time zone -----> " + timeStamp);
			Chunk dateTitle = new Chunk(DATE_TIME_FORMATTER.format(timeStamp).toString(), bold);

			pBinType.add(binTypeTitle);
			pBinType.add(binType);
			Chunk shiftRight = new Chunk(new VerticalPositionMark());
			pBinType.add(shiftRight);
			pBinType.add(dateTitle);

			/* 2 middle */
			// TODO: update on bin type list

			List<Chunk> listOfTitle = new ArrayList<Chunk>();
			List<Chunk> listOfValue = new ArrayList<Chunk>();
			for (BinDetailsView viewDetails : binDetails) {
				BinContent binContent = binContents.stream()
						.filter(value -> value.getId() == viewDetails.getBinContentId()).findAny().orElseThrow();
				listOfTitle.add(new Chunk(binContent.getName() + "- ", bold));
				listOfValue.add(new Chunk(viewDetails.getContentValue().toString() + " ", regular));
			}

			for (int i = 0; i < listOfTitle.size(); i++) {
				pBinContentValues.add(listOfTitle.get(i));
				pBinContentValues.add(listOfValue.get(i));
			}

			Chunk totalBinWeight = new Chunk("Total Bin Weight: ", bold);
			Chunk totalBinWeightValue = new Chunk(
					binHistoryViewWeb.getBinWeight().toString() + " " + binHistoryViewWeb.getUnit(), bold);

			totalWeight.add(totalBinWeight);
			totalWeight.add(totalBinWeightValue);

			Chunk attachmentTitle = new Chunk("Attachments: ", bold);
			attachment.add(attachmentTitle);

			Chunk binContentDetails = new Chunk("Bin Content Details: ", bold);

			pBinContentTitle.add(binContentDetails);

			// location
			Chunk binLocationTitle = new Chunk("Bin Location: ", bold);
			Chunk binLocation = new Chunk(binHistoryViewWeb.getPropertyName(), regular);
			pLocation.add(binLocationTitle);
			pLocation.add(binLocation);

//			Destination location
			Chunk desLocationTitle = new Chunk("Bin Destination Location: ", bold);
			Chunk desLocation = new Chunk(distinationLocation.get().getPropertyName(), regular);
			pDesLocation.add(desLocationTitle);
			pDesLocation.add(desLocation);

			// Now Insert Every Thing Into PDF Document
			document.open();// PDF document opened........

			/* Title */
			document.add(Chunk.NEWLINE);
			document.add(upperTitle);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(pUpload);
			document.add(pBinType);
			document.add(Chunk.NEWLINE);
			// add line separator
			document.add(new LineSeparator(0.5f, 100, null, 0, -5));

			/* Middle */
			document.add(Chunk.NEWLINE);
			document.add(pLocation);
			document.add(Chunk.NEWLINE);
			document.add(pDesLocation);
			document.add(Chunk.NEWLINE);

			/* 2 Middle */
			document.add(new LineSeparator(0.5f, 100, null, 0, -5));
			document.add(Chunk.NEWLINE);
			document.add(pBinContentTitle);
			document.add(pBinContentValues);

			document.add(Chunk.NEWLINE);
			document.add(totalWeight);

			document.add(Chunk.NEWLINE);
			document.add(new LineSeparator(0.5f, 100, null, 0, -5));
			document.add(Chunk.NEWLINE);
			document.add(attachment);

			for (String attach : attachmentFileNames) {
				try {
					Image image = Image.getInstance(URL_FOR_DOC + binHistoryViewWeb.getBinLocationId() + "/" + attach);
					image.scaleToFit(500, 500);
					if (image != null) {
						document.add(image);
					}
				} catch (Exception e) {
					continue;
				}
			}

			/*---------- END LINE --------------*/
			document.newPage();

			document.close();

			file.close();
			logger.debug("Pdf created successfully..." + dest);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void createCSV(String dest, BinHistoryViewWeb binHistoryViewWeb, List<BinDetailsView> binDetails,
			Long timeZone, List<BinContent> binContents, Optional<BinDestination> distinationLocation)
			throws DocumentException, IOException, ResourceNotFoundException {

		Instant timeStamp = binHistoryViewWeb.getLastModifiedTS().minus(timeZone, ChronoUnit.MINUTES);
		DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
				.withZone(ZoneOffset.UTC);

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(dest);

			String[] attachmentFileNames = null;

			attachmentFileNames = binHistoryViewWeb.getAttachments().split(",");

			// Write the CSV file header
			fileWriter.append("Service Details,");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(DATE_TIME_FORMATTER.format(timeStamp).toString());
			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			fileWriter.append("Uploaded By,");
			fileWriter.append(binHistoryViewWeb.getUserFirstname() + " " + binHistoryViewWeb.getUserLastname());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(NEW_LINE_SEPARATOR);

			fileWriter.append("Service Type,");
			fileWriter.append(binHistoryViewWeb.getBinTypeName());

			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
//            ----------------------
			fileWriter.append("Service Location,");
			fileWriter.append(binHistoryViewWeb.getPropertyName());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);

			fileWriter.append("Service Destination Location,");
			fileWriter.append(distinationLocation.get().getName());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);

			fileWriter.append("Service Content Details:,");
			for (BinDetailsView viewDetails : binDetails) {
				BinContent binContent = binContents.stream()
						.filter(value -> value.getId().equals(viewDetails.getBinContentId())).findAny().orElseThrow();
				fileWriter.append(binContent.getName() + ": ");
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(viewDetails.getContentValue().toString());
				fileWriter.append(COMMA_DELIMITER);
			}
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("Total Service Weight,");
			fileWriter.append(binHistoryViewWeb.getBinWeight().toString() + " " + binHistoryViewWeb.getUnit());
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("Attachments,");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(NEW_LINE_SEPARATOR);
			for (String attach : attachmentFileNames) {
				try {
					if (!attach.equals(null) && !attach.equals("")) {
						String imageLink = URL_FOR_DOC + binHistoryViewWeb.getBinLocationId() + "/" + attach;
						System.out.println(imageLink.toString());
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(imageLink);
						fileWriter.append(NEW_LINE_SEPARATOR);
					}
				} catch (Exception e) {
					continue;
				}
			}
			System.out.println("done!");
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}

	}

//	TODO: PRODUCT AND SERVICE
	public void createForProductServiceCSV(String dest, List<ProductService> productServices,
			List<ServiceType> serviceTypes) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(dest);
			// Write the CSV file header
			fileWriter.append("Name");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Description");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Cost");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Category");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Taxable");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Active");
			fileWriter.append(NEW_LINE_SEPARATOR);

			for (ProductService productService : productServices) {
				fileWriter.append(productService.getName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(productService.getDescription());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(productService.getUnitCost().toString());
				fileWriter.append(COMMA_DELIMITER);
				for (ServiceType serviceType : serviceTypes) {
					if (serviceType.getId().equals(productService.getServiceTypeId())) {
						fileWriter.append(serviceType.getName());
						fileWriter.append(COMMA_DELIMITER);
					}
				}

				fileWriter.append(productService.getIsTaxApply().toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append((productService.getIsDeleted()) ? "FALSE" : "TRUE");
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}
	}
	
	public static File convertMultiPartToFile(MultipartFile file) throws IOException {
		System.out.println("FILE : " + file.getName());

		File dir = new File(GlobalValues.SAVE_HAULER_COMPANY_PATH + File.separator + file.getName());
		logger.debug("Directory Name: " + dir.getAbsolutePath());
		if (!dir.exists())
			dir.mkdirs();
		storeFile(dir, file);
//		File convFile = new File(file.getOriginalFilename());
		File convFile = new File(dir.getAbsolutePath() + File.separator + file.getName());

		System.out.println("convFile : " + dir);
		System.out.println("convFile.getAbsolutePath : " + dir.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	
	private static Boolean storeFile(File dir, MultipartFile file) {

		byte[] bytes;
		try {
			bytes = file.getBytes();

			String fileWithPath = dir.getAbsolutePath() + File.separator + file.getOriginalFilename();
			Path path = Paths.get(fileWithPath);
			Files.write(path, bytes);
			logger.debug("inside StorageServiceImpl.storeMultipleFile() Method- Files uploaded");
			return true;
		} catch (IOException e) {
			logger.error(e.getMessage(), e.getCause());
			return false;
		}
	}

}
