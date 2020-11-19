package com.checksammy.loca.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.checksammy.loca.model.BinContent;
import com.checksammy.loca.model.BinDetailsView;
import com.checksammy.loca.model.BinHistoryViewWeb;

public class NewCollectionReportListDto {
	private String drivername;

	private String locationName;

	private Date startdate;

	private Date endDate;

	private Date date;

	private Integer binTypeId;

	private String binTypeName;

	private String propName;

	private String binWeight;

	private String truckFillWeight;

	private List<BinContentReportDto> content;

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getBinTypeId() {
		return binTypeId;
	}

	public void setBinTypeId(Integer binTypeId) {
		this.binTypeId = binTypeId;
	}

	public String getBinTypeName() {
		return binTypeName;
	}

	public void setBinTypeName(String binTypeName) {
		this.binTypeName = binTypeName;
	}

	public String getBinWeight() {
		return binWeight;
	}

	public void setBinWeight(String binWeight) {
		this.binWeight = binWeight;
	}

	public List<BinContentReportDto> getContent() {
		return content;
	}

	public void setContent(List<BinContentReportDto> content) {
		this.content = content;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getTruckFillWeight() {
		return truckFillWeight;
	}

	public void setTruckFillWeight(String truckFillWeight) {
		this.truckFillWeight = truckFillWeight;
	}

	public NewCollectionReportListDto getFilterData(BinHistoryViewWeb binHistoryViewWeb,
			List<BinDetailsView> binViewDetails, List<BinContent> binContents, FilterDataClassDto dataFilterDto,
			Integer tmZone, Date endDate2, Date compareStartDate) {
		NewCollectionReportListDto filterData = new NewCollectionReportListDto();

		Date stateDate = new Date();
		Calendar calendar = Calendar.getInstance();
//		System.out.println(calendar.getTime());
		calendar.setTime(Date.from(binHistoryViewWeb.getCreatedTS()));
//		calendar.add(Calendar.MINUTE, (tmZone));
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.HOUR, 23);
//		calendar.set(Calendar.MINUTE, 59);
//		 calendar.add(Calendar.MINUTE, tmZone);
		stateDate = calendar.getTime();
		System.out.println(compareStartDate);
		System.out.println(stateDate);
		System.out.println(endDate2);

		if (endDate2.compareTo(stateDate) > 0 && compareStartDate.compareTo(stateDate) < 0) {
			filterData.setDrivername(
					(binHistoryViewWeb.getUserFirstname() != null ? binHistoryViewWeb.getUserFirstname() : "") + " "
							+ (binHistoryViewWeb.getUserLastname() != null ? binHistoryViewWeb.getUserLastname() : ""));
			filterData.setLocationName(binHistoryViewWeb.getPropertyName());
			filterData.setDate(stateDate);
			filterData.setBinTypeId(binHistoryViewWeb.getBinTypeId().intValue());
			filterData.setBinTypeName(binHistoryViewWeb.getBinTypeName());
			filterData.setPropName(binHistoryViewWeb.getCompanyName());
			filterData.setBinWeight(binHistoryViewWeb.getBinWeight());
			filterData.setTruckFillWeight(binHistoryViewWeb.getTruckFillWeight());
			filterData.setStartdate(Date.from(dataFilterDto.getFromDate()));
			filterData.setEndDate(Date.from(dataFilterDto.getToDate()));
			List<BinContentReportDto> content = new ArrayList<BinContentReportDto>();
			for (int i = 0; i < binContents.size(); i++) {
				Integer i2 = i;
				BinContentReportDto fillContentData = new BinContentReportDto();
				try {
					BinDetailsView newData = binViewDetails.stream().filter(
							filterContent -> filterContent.getBinContentId().equals(binContents.get(i2).getId()))
							.findAny().orElseThrow();
					fillContentData.setContent(newData.getContentValue().intValue());
					fillContentData.setBinContentId(newData.getBinContentId());
					fillContentData.setContentName(binContents.get(i).getName());
				} catch (Exception e) {
					fillContentData.setContent(0);
					fillContentData.setBinContentId(binContents.get(i).getId());
					fillContentData.setContentName(binContents.get(i).getName());
				}
				content.add(fillContentData);
			}

			filterData.setContent(content);
		}

		return filterData;
	}

}
