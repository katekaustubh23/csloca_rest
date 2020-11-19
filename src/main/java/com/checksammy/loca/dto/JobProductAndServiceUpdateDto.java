package com.checksammy.loca.dto;

import java.util.List;

import com.checksammy.loca.model.JobProductService;

public class JobProductAndServiceUpdateDto {

	private List<JobProductService> jobProductAndService;
	
	private Double subTotal;
	
	private Long jobId;
	
	private Double finalTotal;

	public List<JobProductService> getJobProductAndService() {
		return jobProductAndService;
	}

	public void setJobProductAndService(List<JobProductService> jobProductAndService) {
		this.jobProductAndService = jobProductAndService;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Double getFinalTotal() {
		return finalTotal;
	}

	public void setFinalTotal(Double finalTotal) {
		this.finalTotal = finalTotal;
	}

}
