package com.checksammy.loca.dto;

import java.util.List;

public class JobForInvoiceRequestDto {

	private Long jobId;
	
	private List<Long> visitIds;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public List<Long> getVisitIds() {
		return visitIds;
	}

	public void setVisitIds(List<Long> visitIds) {
		this.visitIds = visitIds;
	}
	
}
