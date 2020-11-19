/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.BinContentReportDto;
import com.checksammy.loca.dto.BinExportReportDto;
import com.checksammy.loca.dto.CollectionWithFromToTodate;
import com.checksammy.loca.dto.FilterDataClassDto;
import com.checksammy.loca.dto.GoodsCollectionReportDto;
import com.checksammy.loca.dto.GoodsCollectionReportWithFromAndTodate;
import com.checksammy.loca.dto.NewCollectionReportListDto;
import com.checksammy.loca.model.BinContent;
import com.checksammy.loca.model.BinDetailsView;
import com.checksammy.loca.model.BinHistoryViewWeb;
import com.checksammy.loca.model.BinType;
import com.checksammy.loca.model.PropertyDetails;
import com.checksammy.loca.repository.BinContentRepository;
import com.checksammy.loca.repository.BinDetailViewRepository;
import com.checksammy.loca.repository.BinHistoryViewWebRepository;
import com.checksammy.loca.repository.BinTypeRespository;
import com.checksammy.loca.repository.PropertyDetailRepository;
import com.checksammy.loca.service.ReportService;
import com.checksammy.loca.utility.GlobalValues;
import com.checksammy.loca.utility.Utility;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class ReportServiceImpl implements ReportService {

	private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	private PropertyDetailRepository propertyDetailRepository;

	@Autowired
	private BinContentRepository binContentRepo;

	@Autowired
	private BinTypeRespository binTypeRepo;

	@Autowired
	private BinHistoryViewWebRepository binHistoryViewWebRepo;

	@Autowired
	private BinDetailViewRepository binDetailViewRepository;

	@SuppressWarnings("deprecation")
	@Override
	public String generateReport(CollectionWithFromToTodate binCollectionReport) throws IOException {
		List<BinExportReportDto> binExportReportDtos = new ArrayList<BinExportReportDto>();
		binExportReportDtos = binCollectionReport.getCollectionReport();

		Double totalCollectionReport = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");

		for (int i = 0; i < binExportReportDtos.size(); i++) {
			totalCollectionReport = totalCollectionReport + binExportReportDtos.get(i).getTotalCollected();
		}

		// Date maxDate = dateList.stream().map(u -> u).max(Date::compareTo).get();
		// Date minDate = dateList.stream().map(u -> u).min(Date::compareTo).get();

		Date maxDate = new Date(binCollectionReport.getToDate());
		Date minDate = new Date(binCollectionReport.getFromDate());

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Bin Details");

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setWrapText(true);
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldFont.setColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFont(boldFont);
		headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle stCellStyle = workbook.createCellStyle();
		stCellStyle.setWrapText(true);
		Font font = workbook.createFont();
		stCellStyle.setFont(font);
		stCellStyle.setBorderBottom(BorderStyle.THIN);
		stCellStyle.setBorderTop(BorderStyle.THIN);
		stCellStyle.setBorderLeft(BorderStyle.THIN);
		stCellStyle.setBorderRight(BorderStyle.THIN);
		stCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		Row row = sheet.createRow(0); // creating row 1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 19));

		row.createCell(0).setCellValue("CheckSammy Inc"); // row 1 column 1
		row.createCell(8).setCellValue("DC Collection Report"); // row 1 column 9
		row.createCell(18).setCellValue(new SimpleDateFormat("YYYY-MM-dd").format(new Date())); // row 1 column 19

		row = sheet.createRow(1); // creating row 2
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));

		row = sheet.createRow(2); // creating row 3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
		row.createCell(0).setCellValue("Report Collection Period: " + new SimpleDateFormat("MM/dd/YYYY").format(minDate)
				+ " -" + new SimpleDateFormat("MM/dd/YYYY").format(maxDate));

		row = sheet.createRow(3); // creating row 4
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
		Instant timeStamp = Instant.now();
		ZonedDateTime ptZone = timeStamp.atZone(ZoneId.of("America/Los_Angeles"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
		row.createCell(0).setCellValue("Report Dates EOB, " + ptZone.format(formatter) + " PT Vancouver");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(4); // creating row 5
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
		row.createCell(0).setCellValue("Collection Report in (lbs)");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(5); // creating row 6
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (lbs) - " + totalCollectionReport);
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(6); // creating row 7
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (tonnes) - " + totalCollectionReport / 2000);
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(7); // creating row 8
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 19));
		setBordersToMergedCells(sheet);
		int rowIndex = 8;
		for (int i = 0; i < binExportReportDtos.size();) {
			/* CostcodeReportDto costcodeReportDto = costcodeReportDtos.get(i); */
			row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue("Report Date");
			row.createCell(1).setCellValue("Total Collection(lbs)");

			row.createCell(2).setCellValue("Textile(%)");
			row.createCell(3).setCellValue("E-Waste(%)");
			row.createCell(4).setCellValue("Household(%)");
			row.createCell(5).setCellValue("Sport/Toys(%)");
			row.createCell(6).setCellValue("Garbage/Recycling(%)");
			row.createCell(7).setCellValue("Others(%)");

			row.createCell(8).setCellValue("Organics(%)");
			row.createCell(9).setCellValue("Plastics(%)");
			row.createCell(10).setCellValue("Glass(%)");
			row.createCell(11).setCellValue("Styrofoam(%)");
			row.createCell(12).setCellValue("Metals(%)");
			row.createCell(13).setCellValue("Others(%)");

			row.createCell(14).setCellValue("Mattress/Box Spring");
			row.createCell(15).setCellValue("Sofa");
			row.createCell(16).setCellValue("Table");
			row.createCell(17).setCellValue("Appliances");
			row.createCell(18).setCellValue("TV");
			row.createCell(19).setCellValue("Others");

			/* Header Style Part */
			for (int j = 0; j < 20; j++) {
				row.getCell(j).setCellStyle(headerCellStyle);
			}
			rowIndex++;
			break;
		}
		for (int i = 0; i < binExportReportDtos.size(); i++) {
			BinExportReportDto binExportReportRow = binExportReportDtos.get(i);
			row = sheet.createRow(rowIndex);

			row.createCell(0).setCellValue(
					new SimpleDateFormat("dd-MMM-YYYY").format(new Date(binExportReportRow.getForDate())));
			row.createCell(1).setCellValue(df.format(binExportReportRow.getTotalCollected()));
			row.createCell(2).setCellValue(df.format(binExportReportRow.getTextile()));
			row.createCell(3).setCellValue(df.format(binExportReportRow.getE_waste()));
			row.createCell(4).setCellValue(df.format(binExportReportRow.getHousehold()));
			row.createCell(5).setCellValue(df.format(binExportReportRow.getSports_toys()));
			row.createCell(6).setCellValue(df.format(binExportReportRow.getGarbage_recycling()));
			row.createCell(7).setCellValue(df.format(binExportReportRow.getOthers1()));

			row.createCell(8).setCellValue(df.format(binExportReportRow.getOrganics()));
			row.createCell(9).setCellValue(df.format(binExportReportRow.getPlastics()));
			row.createCell(10).setCellValue(df.format(binExportReportRow.getGlass()));
			row.createCell(11).setCellValue(df.format(binExportReportRow.getStyrofoam()));
			row.createCell(12).setCellValue(df.format(binExportReportRow.getMetals()));
			row.createCell(13).setCellValue(df.format(binExportReportRow.getOthers2()));

			row.createCell(14).setCellValue(df.format(binExportReportRow.getMattress_boxSpring()));
			row.createCell(15).setCellValue(df.format(binExportReportRow.getSofa()));
			row.createCell(16).setCellValue(df.format(binExportReportRow.getTable()));
			row.createCell(17).setCellValue(df.format(binExportReportRow.getAppliances()));
			row.createCell(18).setCellValue(df.format(binExportReportRow.getTv()));
			row.createCell(19).setCellValue(df.format(binExportReportRow.getOthers3()));

			/* Excel Sheet Style part */
			for (int j = 0; j < 20; j++) {
				row.getCell(j).setCellStyle(stCellStyle);
			}
			rowIndex++;
		}

		File dir = new File(GlobalValues.REPORTS_DIR_PATH);
		if (!dir.exists())
			dir.mkdir();

		LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
		String formattedTS = DateTimeFormatter.ofPattern("MMddyyyy_hhmmss").format(datetime);
		String fileName = Utility.getRandomNumber(8) + "_" + formattedTS + ".xlsx";
		File f = new File(GlobalValues.REPORTS_DIR_PATH + File.separator + fileName);

		FileOutputStream fileOut = new FileOutputStream(f);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		System.out.println("file Path => " + f.getName());
		return f.getName();
	}

	@Override
	public Resource getFile(String fileName) throws MalformedURLException {
		File dir = new File(GlobalValues.REPORTS_DIR_PATH + File.separator + fileName);
		Resource resource = null;

		if (!dir.exists())
			dir.mkdirs();
		Path file = Paths.get(dir.getAbsolutePath());
		resource = new UrlResource(file.toUri());
		return resource;

	}

	private void setBordersToMergedCells(XSSFSheet sheet) {
		int numMerged = sheet.getNumMergedRegions();

		for (int i = 4; i < numMerged; i++) {
			if (i != 9) {
				CellRangeAddress mergedRegions = sheet.getMergedRegion(i);
				RegionUtil.setBorderTop(BorderStyle.THICK, mergedRegions, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THICK, mergedRegions, sheet);
				RegionUtil.setBorderRight(BorderStyle.THICK, mergedRegions, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THICK, mergedRegions, sheet);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String generateGoodsCollectionReport(GoodsCollectionReportWithFromAndTodate goodsCollectionReport)
			throws IOException {

		List<GoodsCollectionReportDto> goodsCollectionExportReportDtos = new ArrayList<GoodsCollectionReportDto>();
		goodsCollectionExportReportDtos = goodsCollectionReport.getCollectionReport();

		Double totalCollectionReport = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");

		for (int i = 0; i < goodsCollectionExportReportDtos.size(); i++) {

			totalCollectionReport = totalCollectionReport + goodsCollectionExportReportDtos.get(i).getTotalCollected();
		}

//		Date maxDate = dateList.stream().map(u -> u).max(Date::compareTo).get();
//		Date minDate = dateList.stream().map(u -> u).min(Date::compareTo).get();

		Date maxDate = new Date(goodsCollectionReport.getToDate());
		Date minDate = new Date(goodsCollectionReport.getFromDate());

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Goods Collection Details");

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setWrapText(true);
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldFont.setColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFont(boldFont);
		headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle stCellStyle = workbook.createCellStyle();
		stCellStyle.setWrapText(true);
		Font font = workbook.createFont();
		stCellStyle.setFont(font);
		stCellStyle.setBorderBottom(BorderStyle.THIN);
		stCellStyle.setBorderTop(BorderStyle.THIN);
		stCellStyle.setBorderLeft(BorderStyle.THIN);
		stCellStyle.setBorderRight(BorderStyle.THIN);
		stCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		Row row = sheet.createRow(0); // creating row 1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 19));

		row.createCell(0).setCellValue("CheckSammy Inc"); // row 1 column 1
		row.createCell(8).setCellValue("DC Collection Report"); // row 1 column 9
		row.createCell(18).setCellValue(new SimpleDateFormat("YYYY-MM-dd").format(new Date())); // row 1 column 19

		row = sheet.createRow(1); // creating row 2
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));

		row = sheet.createRow(2); // creating row 3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
		row.createCell(0).setCellValue("Report Collection Period: " + new SimpleDateFormat("MM/dd/YYYY").format(minDate)
				+ " -" + new SimpleDateFormat("MM/dd/YYYY").format(maxDate));

		row = sheet.createRow(3); // creating row 4
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
		Instant timeStamp = Instant.now();
		ZonedDateTime ptZone = timeStamp.atZone(ZoneId.of("America/Los_Angeles"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
		row.createCell(0).setCellValue("Report Dates EOB, " + ptZone.format(formatter) + " PT Vancouver");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(4); // creating row 5
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
		row.createCell(0).setCellValue("Collection Report in (lbs)");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(5); // creating row 6
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (lbs) - " + totalCollectionReport);
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(6); // creating row 7
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (tonnes) - " + totalCollectionReport / 2000);
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(7); // creating row 8
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 19));
		setBordersToMergedCells(sheet);
		int rowIndex = 8;
		for (int i = 0; i < goodsCollectionExportReportDtos.size();) {
			/* CostcodeReportDto costcodeReportDto = costcodeReportDtos.get(i); */
			row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue("Report Date");

			row.createCell(1).setCellValue("Property Mgmt");
			row.createCell(2).setCellValue("Building Name");

			row.createCell(3).setCellValue("Total Collection(lbs)");

			row.createCell(4).setCellValue("Textile(%)");
			row.createCell(5).setCellValue("E-Waste(%)");
			row.createCell(6).setCellValue("Household(%)");
			row.createCell(7).setCellValue("Sport/Toys(%)");
			row.createCell(8).setCellValue("Garbage/Recycling(%)");
			row.createCell(9).setCellValue("Others(%)");

			row.createCell(10).setCellValue("Organics(%)");
			row.createCell(11).setCellValue("Plastics(%)");
			row.createCell(12).setCellValue("Glass(%)");
			row.createCell(13).setCellValue("Styrofoam(%)");
			row.createCell(14).setCellValue("Metals(%)");
			row.createCell(15).setCellValue("Others(%)");

			row.createCell(16).setCellValue("Mattress/Box Spring");
			row.createCell(17).setCellValue("Sofa");
			row.createCell(18).setCellValue("Table");
			row.createCell(19).setCellValue("Appliances");
			row.createCell(20).setCellValue("TV");
			row.createCell(21).setCellValue("Others");

			/* Header Style Part */
			for (int j = 0; j < 22; j++) {
				row.getCell(j).setCellStyle(headerCellStyle);
			}
			rowIndex++;
			break;
		}
		for (int i = 0; i < goodsCollectionExportReportDtos.size(); i++) {
			GoodsCollectionReportDto goodCollectionExportReportRow = goodsCollectionExportReportDtos.get(i);

			PropertyDetails propertyDetails = propertyDetailRepository
					.findByLocationId(goodCollectionExportReportRow.getPropertyManagement());

			row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue(
					new SimpleDateFormat("dd-MMM-YYYY").format(new Date(goodCollectionExportReportRow.getForDate())));

			String propertyMngtName = (propertyDetails != null) ? propertyDetails.getName() : "NA";
			row.createCell(1).setCellValue(propertyMngtName);
			row.createCell(2).setCellValue(goodCollectionExportReportRow.getPropertyName());

			row.createCell(3).setCellValue(goodCollectionExportReportRow.getTotalCollected());
			row.createCell(4).setCellValue(df.format(goodCollectionExportReportRow.getTextile()));
			row.createCell(5).setCellValue(df.format(goodCollectionExportReportRow.getE_waste()));
			row.createCell(6).setCellValue(df.format(goodCollectionExportReportRow.getHousehold()));
			row.createCell(7).setCellValue(df.format(goodCollectionExportReportRow.getSports_toys()));
			row.createCell(8).setCellValue(df.format(goodCollectionExportReportRow.getGarbage_recycling()));
			row.createCell(9).setCellValue(df.format(goodCollectionExportReportRow.getOthers1()));

			row.createCell(10).setCellValue(df.format(goodCollectionExportReportRow.getOrganics()));
			row.createCell(11).setCellValue(df.format(goodCollectionExportReportRow.getPlastics()));
			row.createCell(12).setCellValue(df.format(goodCollectionExportReportRow.getGlass()));
			row.createCell(13).setCellValue(df.format(goodCollectionExportReportRow.getStyrofoam()));
			row.createCell(14).setCellValue(df.format(goodCollectionExportReportRow.getMetals()));
			row.createCell(15).setCellValue(df.format(goodCollectionExportReportRow.getOthers2()));

			row.createCell(16).setCellValue(df.format(goodCollectionExportReportRow.getMattress_boxSpring()));
			row.createCell(17).setCellValue(df.format(goodCollectionExportReportRow.getSofa()));
			row.createCell(18).setCellValue(df.format(goodCollectionExportReportRow.getTable()));
			row.createCell(19).setCellValue(df.format(goodCollectionExportReportRow.getAppliances()));
			row.createCell(20).setCellValue(df.format(goodCollectionExportReportRow.getTv()));
			row.createCell(21).setCellValue(df.format(goodCollectionExportReportRow.getOthers3()));

			/* Excel Sheet Style part */
			for (int j = 0; j < 22; j++) {
				row.getCell(j).setCellStyle(stCellStyle);
			}
			rowIndex++;
		}

		File dir = new File(GlobalValues.REPORTS_DIR_PATH);
		if (!dir.exists())
			dir.mkdir();

		LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
		String formattedTS = DateTimeFormatter.ofPattern("MMddyyyy_hhmmss").format(datetime);
		String fileName = Utility.getRandomNumber(8) + "_" + formattedTS + ".xlsx";
		File f = new File(GlobalValues.REPORTS_DIR_PATH + File.separator + fileName);

		FileOutputStream fileOut = new FileOutputStream(f);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		System.out.println("file Path => " + f.getName());
		return f.getName();
	}

	/**
	 * NEW IMPLEMENTATION TODO: Newly generated bin management report
	 */
	@SuppressWarnings("deprecation")
	@Override
	public String newlyGenerateReport(List<NewCollectionReportListDto> newBinManagementReportDto) throws IOException {

		List<BinContent> binContentList = binContentRepo
				.getListByBinTypeId(newBinManagementReportDto.get(0).getBinTypeId().longValue());

		List<NewCollectionReportListDto> binExportReportDtos = new ArrayList<NewCollectionReportListDto>();
		binExportReportDtos = newBinManagementReportDto;

		Optional<BinType> binType = binTypeRepo.findById(newBinManagementReportDto.get(0).getBinTypeId().longValue());

		Double totalCollectionReport = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");

		for (int i = 0; i < binExportReportDtos.size(); i++) {
			totalCollectionReport = totalCollectionReport
					+ Double.parseDouble(binExportReportDtos.get(i).getBinWeight());
			for (BinContentReportDto binContentReportDto : binExportReportDtos.get(i).getContent()) {

				System.out.println();
				BinContent newBinContentReportDto = binContentList.stream()
						.filter(name -> name.getId().equals(binContentReportDto.getBinContentId())).findAny()
						.orElseThrow();
				System.out.println(newBinContentReportDto);
				binContentReportDto.setContentName(newBinContentReportDto.getName());
			}
		}

		// Date maxDate = dateList.stream().map(u -> u).max(Date::compareTo).get();
		// Date minDate = dateList.stream().map(u -> u).min(Date::compareTo).get();
		DecimalFormat numberFormat = new DecimalFormat("0.000");
		Date maxDate = newBinManagementReportDto.get(0).getEndDate();
		Date minDate = newBinManagementReportDto.get(0).getStartdate();

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Service Details");

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setWrapText(true);
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldFont.setColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFont(boldFont);
		headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle stCellStyle = workbook.createCellStyle();
		stCellStyle.setWrapText(true);
		Font font = workbook.createFont();
		stCellStyle.setFont(font);
		stCellStyle.setBorderBottom(BorderStyle.THIN);
		stCellStyle.setBorderTop(BorderStyle.THIN);
		stCellStyle.setBorderLeft(BorderStyle.THIN);
		stCellStyle.setBorderRight(BorderStyle.THIN);
		stCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		Row row = sheet.createRow(0); // creating row 1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 19));

		row.createCell(0).setCellValue("CheckSammy Inc"); // row 1 column 1
		row.createCell(8).setCellValue("DC Collection Report"); // row 1 column 9
		row.createCell(18).setCellValue(new SimpleDateFormat("YYYY-MM-dd").format(new Date())); // row 1 column 19

		row = sheet.createRow(1); // creating row 2
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));

		row = sheet.createRow(2); // creating row 3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
		row.createCell(0).setCellValue("Report Collection Period: " + new SimpleDateFormat("MM/dd/YYYY").format(minDate)
				+ " -" + new SimpleDateFormat("MM/dd/YYYY").format(maxDate));

		row = sheet.createRow(3); // creating row 4
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
		Instant timeStamp = Instant.now();
		ZonedDateTime ptZone = timeStamp.atZone(ZoneId.of("America/Los_Angeles"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
		row.createCell(0).setCellValue("Report Dates EOB, " + ptZone.format(formatter) + " PT Vancouver");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(4); // creating row 5
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
		row.createCell(0).setCellValue("Collection Report in (lbs)");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(5); // creating row 6
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (lbs) - " + totalCollectionReport);
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(6); // creating row 7
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (tonnes) - "
				+ String.format("%.03f", (totalCollectionReport / 2000)));
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(7); // creating row 8
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 19));
		setBordersToMergedCells(sheet);
		int rowIndex = 8;
		for (int i = 0; i < binExportReportDtos.size();) {
			/* CostcodeReportDto costcodeReportDto = costcodeReportDtos.get(i); */
			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(i).getContent();
			row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue("Report Date");
			row.createCell(1).setCellValue("Total Collection(lbs)");
			for (int j = 0; j < binExportReportRow.size(); j++) {
				row.createCell(j + 2).setCellValue(
						binExportReportRow.get(j).getContentName() + " " + binType.get().getContentValueScale());
			}

			/* Header Style Part */
			for (int j = 0; j < binContentList.size() + 2; j++) {
				row.getCell(j).setCellStyle(headerCellStyle);
			}
			rowIndex++;
			break;
		}
		for (int i = 0; i < binExportReportDtos.size(); i++) {
			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(i).getContent();
			row = sheet.createRow(rowIndex);

			row.createCell(0)
					.setCellValue(new SimpleDateFormat("dd-MMM-YYYY").format(binExportReportDtos.get(i).getDate()));
			row.createCell(1).setCellValue(binExportReportDtos.get(i).getBinWeight());
			for (int j = 0; j < binExportReportRow.size(); j++) {
				row.createCell(j + 2).setCellValue(df.format(binExportReportRow.get(j).getContent()));
			}

			/* Excel Sheet Style part */
			for (int j = 0; j < binContentList.size() + 2; j++) {
				row.getCell(j).setCellStyle(stCellStyle);
			}
			rowIndex++;
		}

		File dir = new File(GlobalValues.REPORTS_DIR_PATH);
		if (!dir.exists())
			dir.mkdir();

		LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
		String formattedTS = DateTimeFormatter.ofPattern("MMddyyyy_hhmmss").format(datetime);
		String fileName = Utility.getRandomNumber(8) + "_" + formattedTS + ".xlsx";
		File f = new File(GlobalValues.REPORTS_DIR_PATH + File.separator + fileName);

		FileOutputStream fileOut = new FileOutputStream(f);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		System.out.println("file Path => " + f.getName());
		return f.getName();
	}

	/* New generate goods collection report */
	@SuppressWarnings("deprecation")
	@Override
	public String newlyGoodsCollectionReport(List<NewCollectionReportListDto> goodsCollectionReport)
			throws IOException {

		List<BinContent> binContentList = binContentRepo
				.getListByBinTypeId(goodsCollectionReport.get(0).getBinTypeId().longValue());

		List<NewCollectionReportListDto> binExportReportDtos = new ArrayList<NewCollectionReportListDto>();
		binExportReportDtos = goodsCollectionReport;

		Optional<BinType> binType = binTypeRepo.findById(goodsCollectionReport.get(0).getBinTypeId().longValue());

		Double totalCollectionReport = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat numberFormat = new DecimalFormat("0.000");

		for (int i = 0; i < binExportReportDtos.size(); i++) {
			totalCollectionReport = totalCollectionReport
					+ Double.parseDouble(binExportReportDtos.get(i).getBinWeight());
			for (BinContentReportDto binContentReportDto : binExportReportDtos.get(i).getContent()) {
				BinContent newBinContentReportDto = binContentList.stream()
						.filter(name -> name.getId().equals(binContentReportDto.getBinContentId())).findAny()
						.orElseThrow();
				System.out.println(newBinContentReportDto);
				binContentReportDto.setContentName(newBinContentReportDto.getName());
			}
		}

		// Date maxDate = dateList.stream().map(u -> u).max(Date::compareTo).get();
		// Date minDate = dateList.stream().map(u -> u).min(Date::compareTo).get();

		Date maxDate = goodsCollectionReport.get(0).getEndDate();
		Date minDate = goodsCollectionReport.get(0).getStartdate();

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Service Details");

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setWrapText(true);
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldFont.setColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFont(boldFont);
		headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle stCellStyle = workbook.createCellStyle();
		stCellStyle.setWrapText(true);
		Font font = workbook.createFont();
		stCellStyle.setFont(font);
		stCellStyle.setBorderBottom(BorderStyle.THIN);
		stCellStyle.setBorderTop(BorderStyle.THIN);
		stCellStyle.setBorderLeft(BorderStyle.THIN);
		stCellStyle.setBorderRight(BorderStyle.THIN);
		stCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		Row row = sheet.createRow(0); // creating row 1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 19));

		row.createCell(0).setCellValue("CheckSammy Inc"); // row 1 column 1
		row.createCell(8).setCellValue("DC Collection Report"); // row 1 column 9
		row.createCell(18).setCellValue(new SimpleDateFormat("YYYY-MM-dd").format(new Date())); // row 1 column 19

		row = sheet.createRow(1); // creating row 2
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));

		row = sheet.createRow(2); // creating row 3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
		row.createCell(0).setCellValue("Report Collection Period: " + new SimpleDateFormat("MM/dd/YYYY").format(minDate)
				+ " -" + new SimpleDateFormat("MM/dd/YYYY").format(maxDate));

		row = sheet.createRow(3); // creating row 4
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
		Instant timeStamp = Instant.now();
		ZonedDateTime ptZone = timeStamp.atZone(ZoneId.of("America/Los_Angeles"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
		row.createCell(0).setCellValue("Report Dates EOB, " + ptZone.format(formatter) + " PT Vancouver");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(4); // creating row 5
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
		row.createCell(0).setCellValue("Collection Report in (lbs)");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(5); // creating row 6
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (lbs) - " + totalCollectionReport);
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(6); // creating row 7
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (tonnes) - "
				+ String.format("%.03f", (totalCollectionReport / 2000)));
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(7); // creating row 8
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 19));
		setBordersToMergedCells(sheet);
		int rowIndex = 8;
		for (int i = 0; i < binExportReportDtos.size();) {
			/* CostcodeReportDto costcodeReportDto = costcodeReportDtos.get(i); */
			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(i).getContent();
			row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue("Report Date");
			row.createCell(1).setCellValue("Total Collection(lbs)");
			row.createCell(2).setCellValue("Property management");
			for (int j = 0; j < binExportReportRow.size(); j++) {
				row.createCell(j + 3).setCellValue(
						binExportReportRow.get(j).getContentName() + " " + binType.get().getContentValueScale());
			}

			/* Header Style Part */
			for (int j = 0; j < binContentList.size() + 3; j++) {
				row.getCell(j).setCellStyle(headerCellStyle);
			}
			rowIndex++;
			break;
		}
		for (int i = 0; i < binExportReportDtos.size(); i++) {
			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(i).getContent();
			row = sheet.createRow(rowIndex);

			row.createCell(0)
					.setCellValue(new SimpleDateFormat("dd-MMM-YYYY").format(binExportReportDtos.get(i).getDate()));
			row.createCell(1).setCellValue(binExportReportDtos.get(i).getBinWeight());
			row.createCell(2).setCellValue(binExportReportDtos.get(i).getPropName());
			for (int j = 0; j < binExportReportRow.size(); j++) {
				row.createCell(j + 3).setCellValue(df.format(binExportReportRow.get(j).getContent()));
			}

			/* Excel Sheet Style part */
			for (int j = 0; j < binContentList.size() + 3; j++) {
				System.out.println(j);
				row.getCell(j).setCellStyle(stCellStyle);
			}
			rowIndex++;
		}

		File dir = new File(GlobalValues.REPORTS_DIR_PATH);
		if (!dir.exists())
			dir.mkdir();

		LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
		String formattedTS = DateTimeFormatter.ofPattern("MMddyyyy_hhmmss").format(datetime);
		String fileName = Utility.getRandomNumber(8) + "_" + formattedTS + ".xlsx";
		File f = new File(GlobalValues.REPORTS_DIR_PATH + File.separator + fileName);

		FileOutputStream fileOut = new FileOutputStream(f);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		System.out.println("file Path => " + f.getName());
		return f.getName();
	}

//	----------------------------------- BIN GOOD COLLECTION ---------------------------------
	@Override
	public List<NewCollectionReportListDto> filterGoodCollectionReportData(FilterDataClassDto dataFilterDto,
			Integer tmZone) throws IOException {

		List<NewCollectionReportListDto> filterDataToWebList = new ArrayList<NewCollectionReportListDto>();
		filterDataToWebList = getGoodCollectionFilterDataMethod(dataFilterDto, tmZone);
		return filterDataToWebList;
	}

	private List<NewCollectionReportListDto> getGoodCollectionFilterDataMethod(FilterDataClassDto dataFilterDto,
			Integer tmZone) {
		Date endDate = Date.from(dataFilterDto.getToDate());
		System.out.println(endDate);
		Calendar calendar = Calendar.getInstance();
//		System.out.println(calendar.getTime());

		calendar.setTime(endDate);
		calendar.add(Calendar.MINUTE, -(tmZone));

//		calendar.set(Calendar.HOUR, 23);
//		calendar.set(Calendar.MINUTE, 59);
//		calendar.set(Calendar.HOUR_OF_DAY, 0-23);
//		 calendar.add(Calendar.MINUTE, tmZone);
		endDate = calendar.getTime();
		Date newDate = calendar.getTime();
		
		Date compareStartDate = Date.from(dataFilterDto.getFromDate());
		
		Integer wholeDayTime = 24 * 60;
		wholeDayTime = wholeDayTime + (tmZone);
		Integer hour = wholeDayTime / 60;
		Integer minutes = wholeDayTime % 60;

		newDate.setHours(hour);
		newDate.setMinutes(minutes);

		endDate.setHours(23);
		endDate.setMinutes(59);
		logger.debug(endDate.toLocaleString());
		System.out.println(endDate);

		List<BinHistoryViewWeb> binHistoryViewWebs = binHistoryViewWebRepo
				.findByFormToDateAndBinType(dataFilterDto.getFromDate(), endDate, dataFilterDto.getBinType());

		if (dataFilterDto.getPropertyManagement() != null && dataFilterDto.getPropertyManagement() > 0) {
			List<BinHistoryViewWeb> binHistoryViewWebsNew = new ArrayList<BinHistoryViewWeb>();
			binHistoryViewWebsNew = binHistoryViewWebs.stream().filter(filterData -> filterData.getCompanyId() != null)
					.filter(filterData -> filterData.getCompanyId().equals(dataFilterDto.getPropertyManagement()))
					.collect(Collectors.toList());
			binHistoryViewWebs = binHistoryViewWebsNew;
		}

		if (dataFilterDto.getLocationId() != null && dataFilterDto.getLocationId() > 0) {
			List<BinHistoryViewWeb> binHistoryViewWebsNew = new ArrayList<BinHistoryViewWeb>();
			binHistoryViewWebsNew = binHistoryViewWebs.stream()
					.filter(filterData -> filterData.getLocationId().equals(dataFilterDto.getLocationId()))
					.collect(Collectors.toList());
			binHistoryViewWebs = binHistoryViewWebsNew;
		}

		if (dataFilterDto.getCreatedBy() != null && dataFilterDto.getCreatedBy() > 0) {
			List<BinHistoryViewWeb> binHistoryViewWebsNew = new ArrayList<BinHistoryViewWeb>();
			binHistoryViewWebsNew = binHistoryViewWebs.stream()
					.filter(filterData -> filterData.getDriverId().equals(dataFilterDto.getCreatedBy()))
					.collect(Collectors.toList());
			binHistoryViewWebs = binHistoryViewWebsNew;
		}

		List<NewCollectionReportListDto> filterDataToWebList = new ArrayList<NewCollectionReportListDto>();

		for (BinHistoryViewWeb binHistoryViewWeb : binHistoryViewWebs) {

			NewCollectionReportListDto filterDataToWeb = new NewCollectionReportListDto();

			List<BinContent> binContents = binContentRepo.getListByBinTypeId(binHistoryViewWeb.getBinTypeId());

			List<BinDetailsView> binViewDetails = binDetailViewRepository
					.getBinDetailsFromViewByBinLocationId(binHistoryViewWeb.getBinLocationId());
			System.out.println(binViewDetails);
			filterDataToWeb = filterDataToWeb.getFilterData(binHistoryViewWeb, binViewDetails, binContents,
					dataFilterDto, tmZone, newDate,compareStartDate);
			if (filterDataToWeb.getDate() != null) {
				filterDataToWebList.add(filterDataToWeb);
			}
		}
		return filterDataToWebList;
	}

//	 UPDATE New filter BIN report
	@Override
	public List<NewCollectionReportListDto> filterCollectionReportData(FilterDataClassDto dataFilterDto, Integer tmZone)
			throws IOException {

		List<NewCollectionReportListDto> filterDataToWebList = new ArrayList<NewCollectionReportListDto>();
		filterDataToWebList = getFilterServiceDataMethod(dataFilterDto, tmZone);
		return filterDataToWebList;
	}

	/* GET FILTER DATA METHOD() */
	private List<NewCollectionReportListDto> getFilterServiceDataMethod(FilterDataClassDto dataFilterDto,
			Integer tmZone) {
		Date endDate = Date.from(dataFilterDto.getToDate());
		System.out.println(endDate);

		Calendar calendar = Calendar.getInstance();
//		System.out.println(calendar.getTime());
		calendar.setTime(endDate);
		calendar.add(Calendar.MINUTE, -(tmZone));
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.HOUR, 23);
//		calendar.set(Calendar.MINUTE, 59);
//		 calendar.add(Calendar.MINUTE, tmZone);
		endDate = calendar.getTime();
		Date newDate = calendar.getTime();
		Date compareStartDate = Date.from(dataFilterDto.getFromDate());
		Integer wholeDayTime = 24 * 60;
		wholeDayTime = wholeDayTime + (tmZone);
		Integer hour = wholeDayTime / 60;
		Integer minutes = wholeDayTime % 60;

		newDate.setHours(hour);
		newDate.setMinutes(minutes);

		endDate.setHours(23);
		endDate.setMinutes(59);
		System.out.println(endDate);

		List<BinHistoryViewWeb> binHistoryViewWebs = binHistoryViewWebRepo
				.findByFormToDateAndBinType(dataFilterDto.getFromDate(), endDate, dataFilterDto.getBinType());

		if (dataFilterDto.getLocationId() != null && dataFilterDto.getLocationId() > 0) {
			List<BinHistoryViewWeb> binHistoryViewWebsNew = new ArrayList<BinHistoryViewWeb>();
			binHistoryViewWebsNew = binHistoryViewWebs.stream()
					.filter(filterData -> filterData.getLocationId().equals(dataFilterDto.getLocationId()))
					.collect(Collectors.toList());
			binHistoryViewWebs = binHistoryViewWebsNew;
		}

		if (dataFilterDto.getCreatedBy() != null && dataFilterDto.getCreatedBy() > 0) {
			List<BinHistoryViewWeb> binHistoryViewWebsNew = new ArrayList<BinHistoryViewWeb>();
			binHistoryViewWebsNew = binHistoryViewWebs.stream()
					.filter(filterData -> filterData.getDriverId().equals(dataFilterDto.getCreatedBy()))
					.collect(Collectors.toList());
			binHistoryViewWebs = binHistoryViewWebsNew;
		}

		List<NewCollectionReportListDto> filterDataToWebList = new ArrayList<NewCollectionReportListDto>();

		for (BinHistoryViewWeb binHistoryViewWeb : binHistoryViewWebs) {

			NewCollectionReportListDto filterDataToWeb = new NewCollectionReportListDto();

			List<BinContent> binContents = binContentRepo.getListByBinTypeId(binHistoryViewWeb.getBinTypeId());

			List<BinDetailsView> binViewDetails = binDetailViewRepository
					.getBinDetailsFromViewByBinLocationId(binHistoryViewWeb.getBinLocationId());
			System.out.println(binViewDetails);
			filterDataToWeb = filterDataToWeb.getFilterData(binHistoryViewWeb, binViewDetails, binContents,
					dataFilterDto, tmZone, newDate, compareStartDate);
			if (filterDataToWeb.getDate() != null) {
				filterDataToWebList.add(filterDataToWeb);
			}
		}
		return filterDataToWebList;
	}

//	@Override
//	public String filterCollectionExport(List<NewCollectionReportListDto> exportData) throws IOException {
//
//		List<BinContent> binContentList = binContentRepo
//				.getListByBinTypeId(exportData.get(0).getBinTypeId().longValue());
//
//		List<NewCollectionReportListDto> binExportReportDtos = new ArrayList<NewCollectionReportListDto>();
//		binExportReportDtos = exportData;
//
//		Optional<BinType> binType = binTypeRepo.findById(exportData.get(0).getBinTypeId().longValue());
//
//		Double totalCollectionReport = 0.00;
//		DecimalFormat df = new DecimalFormat("0.00");
//
//		for (int i = 0; i < binExportReportDtos.size(); i++) {
//			totalCollectionReport = totalCollectionReport + Double.parseDouble(binExportReportDtos.get(i).getBinWeight());
//			for (BinContentReportDto binContentReportDto : binExportReportDtos.get(i).getContent()) {
//
//				System.out.println();
//				BinContent newBinContentReportDto = binContentList.stream()
//						.filter(name -> name.getId().equals(binContentReportDto.getBinContentId())).findAny()
//						.orElseThrow();
//				System.out.println(newBinContentReportDto);
//				binContentReportDto.setContentName(newBinContentReportDto.getName());
//			}
//		}
//
//		// Date maxDate = dateList.stream().map(u -> u).max(Date::compareTo).get();
//		// Date minDate = dateList.stream().map(u -> u).min(Date::compareTo).get();
//		DecimalFormat numberFormat = new DecimalFormat("0.000");
//		Date maxDate = new Date(exportData.get(0).getEndDate());
//		Date minDate = new Date(exportData.get(0).getStartdate());
//
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet sheet = workbook.createSheet("Service Details");
//
//		CellStyle headerCellStyle = workbook.createCellStyle();
//		headerCellStyle.setWrapText(true);
//		Font boldFont = workbook.createFont();
//		boldFont.setBold(true);
//		boldFont.setColor(IndexedColors.BLACK.getIndex());
//		headerCellStyle.setFont(boldFont);
//		headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
//		headerCellStyle.setBorderBottom(BorderStyle.THIN);
//		headerCellStyle.setBorderTop(BorderStyle.THIN);
//		headerCellStyle.setBorderLeft(BorderStyle.THIN);
//		headerCellStyle.setBorderRight(BorderStyle.THIN);
//		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
//		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
//		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//
//		CellStyle stCellStyle = workbook.createCellStyle();
//		stCellStyle.setWrapText(true);
//		Font font = workbook.createFont();
//		stCellStyle.setFont(font);
//		stCellStyle.setBorderBottom(BorderStyle.THIN);
//		stCellStyle.setBorderTop(BorderStyle.THIN);
//		stCellStyle.setBorderLeft(BorderStyle.THIN);
//		stCellStyle.setBorderRight(BorderStyle.THIN);
//		stCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
//		stCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//		stCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
//		stCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//
//		Row row = sheet.createRow(0); // creating row 1
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 19));
//
//		row.createCell(0).setCellValue("CheckSammy Inc"); // row 1 column 1
//		row.createCell(8).setCellValue("DC Collection Report"); // row 1 column 9
//		row.createCell(18).setCellValue(new SimpleDateFormat("YYYY-MM-dd").format(new Date())); // row 1 column 19
//
//		row = sheet.createRow(1); // creating row 2
//		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));
//
//		row = sheet.createRow(2); // creating row 3
//		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
//		row.createCell(0).setCellValue("Report Collection Period: " + new SimpleDateFormat("MM/dd/YYYY").format(minDate)
//				+ " -" + new SimpleDateFormat("MM/dd/YYYY").format(maxDate));
//
//		row = sheet.createRow(3); // creating row 4
//		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
//		Instant timeStamp = Instant.now();
//		ZonedDateTime ptZone = timeStamp.atZone(ZoneId.of("America/Los_Angeles"));
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
//		row.createCell(0).setCellValue("Report Dates EOB, " + ptZone.format(formatter) + " PT Vancouver");
//		row.getCell(0).setCellStyle(stCellStyle);
//
//		row = sheet.createRow(4); // creating row 5
//		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
//		row.createCell(0).setCellValue("Collection Report in (lbs)");
//		row.getCell(0).setCellStyle(stCellStyle);
//
//		row = sheet.createRow(5); // creating row 6
//		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
//		row.createCell(0).setCellValue("Total Collected Reporting Period (lbs) - " + totalCollectionReport);
//		row.getCell(0).setCellStyle(stCellStyle);
//
//		row = sheet.createRow(6); // creating row 7
//		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
//		row.createCell(0).setCellValue("Total Collected Reporting Period (tonnes) - "
//				+ String.format("%.03f", (totalCollectionReport / 2000)));
//		row.getCell(0).setCellStyle(stCellStyle);
//
//		row = sheet.createRow(7); // creating row 8
//		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 19));
//		setBordersToMergedCells(sheet);
//		int rowIndex = 8;
//		for (int i = 0; i < binExportReportDtos.size();) {
//			/* CostcodeReportDto costcodeReportDto = costcodeReportDtos.get(i); */
//			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(i).getContent();
//			row = sheet.createRow(rowIndex);
//			row.createCell(0).setCellValue("Report Date");
//			row.createCell(1).setCellValue("Total Collection(lbs)");
//			row.createCell(2).setCellValue("Truck Fill Weight");
//			for (int j = 0; j < binExportReportRow.size(); j++) {
//				row.createCell(j + 3).setCellValue(
//						binExportReportRow.get(j).getContentName() + " " + binType.get().getContentValueScale());
//			}
//
//			/* Header Style Part */
//			for (int j = 0; j < binContentList.size() + 3; j++) {
//				row.getCell(j).setCellStyle(headerCellStyle);
//			}
//			rowIndex++;
//			break;
//		}
//		for (int i = 0; i < binExportReportDtos.size(); i++) {
//			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(i).getContent();
//			row = sheet.createRow(rowIndex);
//
//			row.createCell(0)
//					.setCellValue(new SimpleDateFormat("dd-MMM-YYYY").format(binExportReportDtos.get(i).getDate()));
//			row.createCell(1).setCellValue(binExportReportDtos.get(i).getBinWeight());
//			row.createCell(2).setCellValue(binExportReportDtos.get(i).getTruckFillWeight());
//			for (int j = 0; j < binExportReportRow.size(); j++) {
//				row.createCell(j + 3).setCellValue(df.format(binExportReportRow.get(j).getContent()));
//			}
//
//			/* Excel Sheet Style part */
//			for (int j = 0; j < binContentList.size() + 3; j++) {
//				row.getCell(j).setCellStyle(stCellStyle);
//			}
//			rowIndex++;
//		}
//
//		File dir = new File(GlobalValues.REPORTS_DIR_PATH);
//		if (!dir.exists())
//			dir.mkdir();
//
//		LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
//		String formattedTS = DateTimeFormatter.ofPattern("MMddyyyy_hhmmss").format(datetime);
//		String fileName = Utility.getRandomNumber(8) + "_" + formattedTS + ".xlsx";
//		File f = new File(GlobalValues.REPORTS_DIR_PATH + File.separator + fileName);
//
//		FileOutputStream fileOut = new FileOutputStream(f);
//		workbook.write(fileOut);
//		fileOut.close();
//		workbook.close();
//		System.out.println("file Path => " + f.getName());
//		return f.getName();
//	}

	@Override
	public String filterGoodCollectionExport(FilterDataClassDto dataFilterDto, Integer tmZone, Boolean export)
			throws IOException {
		List<NewCollectionReportListDto> filterDataToWebList = new ArrayList<NewCollectionReportListDto>();
		filterDataToWebList = getGoodCollectionFilterDataMethod(dataFilterDto, tmZone);

//		List<BinContent> binContentList = binContentRepo
//				.getListByBinTypeId(filterDataToWebList.get(0).getBinTypeId().longValue());

		List<NewCollectionReportListDto> binExportReportDtos = new ArrayList<NewCollectionReportListDto>();
		binExportReportDtos = filterDataToWebList;

		Optional<BinType> binType = binTypeRepo.findById(filterDataToWebList.get(0).getBinTypeId().longValue());

		Double totalCollectionReport = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat numberFormat = new DecimalFormat("0.000");
		for (NewCollectionReportListDto newCollectionReportListDto : binExportReportDtos) {
			totalCollectionReport +=Double.valueOf(newCollectionReportListDto.getBinWeight());
		}

		// Date maxDate = dateList.stream().map(u -> u).max(Date::compareTo).get();
		// Date minDate = dateList.stream().map(u -> u).min(Date::compareTo).get();
//		Date dataDate = addTimeZone(filterDataToWebList.get(0).getEndDate(), tmZone);
		Date maxDate = changeDateTime(filterDataToWebList.get(0).getEndDate(), tmZone);
		Date minDate = changeDateTime(filterDataToWebList.get(0).getStartdate(), tmZone);

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Service Details");

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setWrapText(true);
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldFont.setColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFont(boldFont);
		headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle stCellStyle = workbook.createCellStyle();
		stCellStyle.setWrapText(true);
		Font font = workbook.createFont();
		stCellStyle.setFont(font);
		stCellStyle.setBorderBottom(BorderStyle.THIN);
		stCellStyle.setBorderTop(BorderStyle.THIN);
		stCellStyle.setBorderLeft(BorderStyle.THIN);
		stCellStyle.setBorderRight(BorderStyle.THIN);
		stCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		Row row = sheet.createRow(0); // creating row 1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 19));

		row.createCell(0).setCellValue("CheckSammy Inc"); // row 1 column 1
		row.createCell(8).setCellValue("DC Collection Report"); // row 1 column 9
		row.createCell(18).setCellValue(new SimpleDateFormat("YYYY-MM-dd").format(new Date())); // row 1 column 19

		row = sheet.createRow(1); // creating row 2
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));

		row = sheet.createRow(2); // creating row 3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
		row.createCell(0).setCellValue("Report Collection Period: " + new SimpleDateFormat("MM/dd/YYYY").format(minDate)
				+ " -" + new SimpleDateFormat("MM/dd/YYYY").format(maxDate));

		row = sheet.createRow(3); // creating row 4
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
		Instant timeStamp = Instant.now();
		ZonedDateTime ptZone = timeStamp.atZone(ZoneId.of("America/Los_Angeles"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
		row.createCell(0).setCellValue("Report Dates EOB, " + ptZone.format(formatter) + " PT Vancouver");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(4); // creating row 5
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
		row.createCell(0).setCellValue("Collection Report in (lbs)");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(5); // creating row 6
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (lbs) - " + totalCollectionReport);
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(6); // creating row 7
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (tonnes) - "
				+ String.format("%.03f", (totalCollectionReport / 2000)));
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(7); // creating row 8
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 19));
		setBordersToMergedCells(sheet);
		int rowIndex = 8;

		/* ------ Higher Heading Data------------- */
		int higherArrayLenght = 0;
		int indexOfLargeTitle = 0;
		int test = 0;
		for (NewCollectionReportListDto binExportReportDto : binExportReportDtos) {
			int currentLength = binExportReportDto.getContent().size();
			if (currentLength > higherArrayLenght) {
				System.out.println(indexOfLargeTitle);
				test = indexOfLargeTitle;
				higherArrayLenght = currentLength;
			}
			indexOfLargeTitle++;
		}
		System.out.println("higherArrayLenght - " + higherArrayLenght);

		for (int i = 0; i < binExportReportDtos.size();) {
			/* CostcodeReportDto costcodeReportDto = costcodeReportDtos.get(i); */
			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(test).getContent();
			row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue("Report Date");
			row.createCell(1).setCellValue("Total Collection in (lbs)");
			row.createCell(2).setCellValue("Truck Fill in Yards");
			row.createCell(3).setCellValue("Property management");
			row.createCell(4).setCellValue("Property Name");
			for (int j = 0; j < binExportReportRow.size(); j++) {
				row.createCell(j + 5).setCellValue(
						binExportReportRow.get(j).getContentName() + " " + binType.get().getContentValueScale());
			}

			/* Header Style Part */
			for (int j = 0; j < higherArrayLenght + 5; j++) {
				row.getCell(j).setCellStyle(headerCellStyle);
			}
			rowIndex++;
			break;
		}
		for (int i = 0; i < binExportReportDtos.size(); i++) {
			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(i).getContent();
			row = sheet.createRow(rowIndex);
			Date dataDate = addTimeZone(binExportReportDtos.get(i).getDate(), tmZone);
			row.createCell(0).setCellValue(new SimpleDateFormat("dd-MMM-YYYY").format(dataDate));
			row.createCell(1).setCellValue(binExportReportDtos.get(i).getBinWeight());
			row.createCell(2).setCellValue(binExportReportDtos.get(i).getTruckFillWeight());
			row.createCell(3).setCellValue(binExportReportDtos.get(i).getPropName());
			row.createCell(4).setCellValue(binExportReportDtos.get(i).getLocationName());
			for (int j = 0; j < higherArrayLenght; j++) {
//				System.out.println(binExportReportRow.get(j));
				try {
					row.createCell(j + 5).setCellValue(df.format(binExportReportRow.get(j).getContent()));
				} catch (Exception e) {
					row.createCell(j + 5).setCellValue("0.00");
				}
			}

			/* Excel Sheet Style part */
			for (int j = 0; j < higherArrayLenght + 5; j++) {
				System.out.println(j);
				row.getCell(j).setCellStyle(stCellStyle);
			}
			rowIndex++;
		}

		File dir = new File(GlobalValues.REPORTS_DIR_PATH);
		if (!dir.exists())
			dir.mkdir();

		LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
		String formattedTS = DateTimeFormatter.ofPattern("MMddyyyy_hhmmss").format(datetime);
		String fileName = Utility.getRandomNumber(8) + "_" + formattedTS + ".xlsx";
		File f = new File(GlobalValues.REPORTS_DIR_PATH + File.separator + fileName);

		FileOutputStream fileOut = new FileOutputStream(f);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		System.out.println("file Path => " + f.getName());

		return f.getName();
	}

	private Date addTimeZone(Date date, Integer tmZone) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -(tmZone));
		return calendar.getTime();
	}

	@Override
	public String filterCollectionExport(FilterDataClassDto dataFilterDto, Integer tmZone, Boolean export)
			throws IOException {
		List<NewCollectionReportListDto> filterDataToWebList = new ArrayList<NewCollectionReportListDto>();
		filterDataToWebList = getFilterServiceDataMethod(dataFilterDto, tmZone);

//		List<BinContent> binContentList = binContentRepo
//				.getListByBinTypeId(filterDataToWebList.get(0).getBinTypeId().longValue());

		List<NewCollectionReportListDto> binExportReportDtos = new ArrayList<NewCollectionReportListDto>();
		binExportReportDtos = filterDataToWebList;

		Optional<BinType> binType = binTypeRepo.findById(filterDataToWebList.get(0).getBinTypeId().longValue());

		Double totalCollectionReport = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		
		for (NewCollectionReportListDto newCollectionReportListDto : binExportReportDtos) {
			totalCollectionReport +=Double.valueOf(newCollectionReportListDto.getBinWeight());
		}

		// Date maxDate = dateList.stream().map(u -> u).max(Date::compareTo).get();
		// Date minDate = dateList.stream().map(u -> u).min(Date::compareTo).get();
		DecimalFormat numberFormat = new DecimalFormat("0.000");
		Date maxDate = changeDateTime(filterDataToWebList.get(0).getEndDate(), tmZone);
		Date minDate = changeDateTime(filterDataToWebList.get(0).getStartdate(), tmZone);

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Service Details");

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setWrapText(true);
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldFont.setColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFont(boldFont);
		headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle stCellStyle = workbook.createCellStyle();
		stCellStyle.setWrapText(true);
		Font font = workbook.createFont();
		stCellStyle.setFont(font);
		stCellStyle.setBorderBottom(BorderStyle.THIN);
		stCellStyle.setBorderTop(BorderStyle.THIN);
		stCellStyle.setBorderLeft(BorderStyle.THIN);
		stCellStyle.setBorderRight(BorderStyle.THIN);
		stCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		stCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		Row row = sheet.createRow(0); // creating row 1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 19));

		row.createCell(0).setCellValue("CheckSammy Inc"); // row 1 column 1
		row.createCell(8).setCellValue("DC Collection Report"); // row 1 column 9
		row.createCell(18).setCellValue(new SimpleDateFormat("YYYY-MM-dd").format(new Date())); // row 1 column 19

		row = sheet.createRow(1); // creating row 2
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));

		row = sheet.createRow(2); // creating row 3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
		row.createCell(0).setCellValue("Report Collection Period: " + new SimpleDateFormat("MM/dd/YYYY").format(minDate)
				+ " -" + new SimpleDateFormat("MM/dd/YYYY").format(maxDate));

		row = sheet.createRow(3); // creating row 4
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
		Instant timeStamp = Instant.now();
		ZonedDateTime ptZone = timeStamp.atZone(ZoneId.of("America/Los_Angeles"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
		row.createCell(0).setCellValue("Report Dates EOB, " + ptZone.format(formatter) + " PT Vancouver");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(4); // creating row 5
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
		row.createCell(0).setCellValue("Collection Report in (lbs)");
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(5); // creating row 6
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (lbs) - " + totalCollectionReport);
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(6); // creating row 7
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
		row.createCell(0).setCellValue("Total Collected Reporting Period (tonnes) - "
				+ String.format("%.03f", (totalCollectionReport / 2000)));
		row.getCell(0).setCellStyle(stCellStyle);

		row = sheet.createRow(7); // creating row 8
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 19));
		setBordersToMergedCells(sheet);
		int rowIndex = 8;

		/* ------ Higher Heading Data------------- */
		int higherArrayLenght = 0;
		int indexOfLargeTitle = 0;
		int test = 0;
		for (NewCollectionReportListDto binExportReportDto : binExportReportDtos) {
			int currentLength = binExportReportDto.getContent().size();

//			higherArrayLenght = currentLength;
			if (currentLength > higherArrayLenght) {
				System.out.println(indexOfLargeTitle);
				test = indexOfLargeTitle;
				higherArrayLenght = currentLength;
			}
			indexOfLargeTitle++;
		}
		System.out.println("test - " + higherArrayLenght);
		System.out.println("test - " + test);
		for (int i = 0; i < binExportReportDtos.size();) {
			/* CostcodeReportDto costcodeReportDto = costcodeReportDtos.get(i); */
			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(test).getContent();
			row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue("Report Date");
			row.createCell(1).setCellValue("Total Collection in (lbs)");
			row.createCell(2).setCellValue("Truck Fill in Yards");
			for (int j = 0; j < binExportReportRow.size(); j++) {
				row.createCell(j + 3).setCellValue(
						binExportReportRow.get(j).getContentName() + " " + binType.get().getContentValueScale());
			}

			/* Header Style Part */
			for (int j = 0; j < higherArrayLenght + 3; j++) {
				row.getCell(j).setCellStyle(headerCellStyle);
			}
			rowIndex++;
			break;
		}
		for (int i = 0; i < binExportReportDtos.size(); i++) {
			List<BinContentReportDto> binExportReportRow = binExportReportDtos.get(i).getContent();
			row = sheet.createRow(rowIndex);

//			Calendar calendar = new GregorianCalendar();
//			calendar.setTime(binExportReportDtos.get(i).getDate());
////			System.out.println("calender start- "+ calendar.getTime());
////			System.out.println("calender  zone - "+ calendar.getTimeZone());
////			calendar.add(Calendar.MINUTE, -(tmZone));
////			System.out.println("calender Final TIme- "+ calendar.getTime());
			Date simpleDate = addTimeZone(binExportReportDtos.get(i).getDate(), tmZone);

			row.createCell(0).setCellValue(new SimpleDateFormat("dd-MMM-YYYY").format(simpleDate));
			row.createCell(1).setCellValue(binExportReportDtos.get(i).getBinWeight());
			row.createCell(2).setCellValue(binExportReportDtos.get(i).getTruckFillWeight());
			for (int j = 0; j < higherArrayLenght; j++) {
				try {
					row.createCell(j + 3).setCellValue(df.format(binExportReportRow.get(j).getContent()));
				} catch (Exception e) {
					row.createCell(j + 3).setCellValue("0.00");
				}

			}

			/* Excel Sheet Style part */
			for (int j = 0; j < higherArrayLenght + 3; j++) {
				row.getCell(j).setCellStyle(stCellStyle);
			}
			rowIndex++;
		}

		File dir = new File(GlobalValues.REPORTS_DIR_PATH);
		if (!dir.exists())
			dir.mkdir();

		LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
		String formattedTS = DateTimeFormatter.ofPattern("MMddyyyy_hhmmss").format(datetime);
		String fileName = Utility.getRandomNumber(8) + "_" + formattedTS + ".xlsx";
		File f = new File(GlobalValues.REPORTS_DIR_PATH + File.separator + fileName);

		FileOutputStream fileOut = new FileOutputStream(f);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		System.out.println("file Path => " + f.getName());
		return f.getName();
	}

	private Date changeDateTime(Date date, Integer tmZone) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
//		System.out.println("calender start- "+ calendar.getTime());
//		System.out.println("calender  zone - "+ calendar.getTimeZone());
		calendar.add(Calendar.MINUTE, -(tmZone));
//		System.out.println("calender Final TIme- "+ calendar.getTime());
		return calendar.getTime();
	}
}
