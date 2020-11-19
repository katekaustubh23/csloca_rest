package com.checksammy.loca.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;

import com.checksammy.loca.dto.CollectionWithFromToTodate;
import com.checksammy.loca.dto.FilterDataClassDto;
import com.checksammy.loca.dto.GoodsCollectionReportWithFromAndTodate;
import com.checksammy.loca.dto.NewCollectionReportListDto;

public interface ReportService {
	
	public String generateReport(CollectionWithFromToTodate binCollectionReport) throws IOException;
	
	public Resource getFile(String fileName) throws MalformedURLException;

	public String generateGoodsCollectionReport(GoodsCollectionReportWithFromAndTodate goodsCollectionReport) throws IOException;

	public String newlyGenerateReport(List<NewCollectionReportListDto> newBinManagementReportDto)throws IOException;
	
	public String newlyGoodsCollectionReport(List<NewCollectionReportListDto> goodsCollectionReport) throws IOException;

	public List<NewCollectionReportListDto> filterGoodCollectionReportData(FilterDataClassDto dataFilterDto, Integer tmZone) throws IOException;

	public List<NewCollectionReportListDto> filterCollectionReportData(FilterDataClassDto dataFilterDto, Integer tmZone) throws IOException;

	public String filterGoodCollectionExport(FilterDataClassDto dataFilterDto, Integer tmZone, Boolean export)throws IOException;

	public String filterCollectionExport(FilterDataClassDto dataFilterDto, Integer tmZone, Boolean export)throws IOException ;

}
