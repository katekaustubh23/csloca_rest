package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.List;

import com.checksammy.loca.model.TaxGroupMaster;

public class TaxGroupMasterDto {
	
	private Long id;
	
	private String taxGroupName;
	
	private String taxDescription;
	
	private Long createdBy;
	
	private Instant createdTs;
	
	private List<Long> taxListId;
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTaxGroupName() {
		return taxGroupName;
	}


	public void setTaxGroupName(String taxGroupName) {
		this.taxGroupName = taxGroupName;
	}


	public String getTaxDescription() {
		return taxDescription;
	}


	public void setTaxDescription(String taxDescription) {
		this.taxDescription = taxDescription;
	}


	public Long getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}


	public Instant getCreatedTs() {
		return createdTs;
	}


	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}


	public List<Long> getTaxListId() {
		return taxListId;
	}


	public void setTaxListId(List<Long> taxListId) {
		this.taxListId = taxListId;
	}


	public TaxGroupMaster setTaxGroupMaster(TaxGroupMasterDto taxGroupMasterDto) {
		TaxGroupMaster taxGroupMaster = new TaxGroupMaster();
		taxGroupMaster.setId(taxGroupMasterDto.getId());
		taxGroupMaster.setCreatedBy(taxGroupMasterDto.getCreatedBy());
		taxGroupMaster.setCreatedTs(taxGroupMasterDto.getCreatedTs());
		taxGroupMaster.setTaxDescription(taxGroupMasterDto.getTaxDescription());
		taxGroupMaster.setTaxGroupName(taxGroupMasterDto.getTaxGroupName());
		
		return taxGroupMaster;
	}

}
