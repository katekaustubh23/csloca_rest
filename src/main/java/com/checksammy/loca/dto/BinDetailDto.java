package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.checksammy.loca.model.BinDetail;
import com.checksammy.loca.model.BinLocation;
import com.checksammy.loca.model.BinLocationCustomInstance;
import com.checksammy.loca.model.BinLocationNotes;

public class BinDetailDto {

	private Long id;

	private Long binTypeId;

	private Long locationId;
	private Long binWeight;
	private Long truckFillWeight;
	private String unit;
	private String attachments;
	private Long binDestinationId;
	private Boolean isDeleted;
	private Long createdBy;
	private Long modifiedBy;
	private Instant createdTS;
	private Instant modifiedTS;
	private Set<BinLocationNotes> binLocationNotes;

	private List<BinDetail> binDetails;
	
	private Set<BinLocationCustomInstance> binCustomField = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBinTypeId() {
		return binTypeId;
	}

	public void setBinTypeId(Long binTypeId) {
		this.binTypeId = binTypeId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getBinWeight() {
		return binWeight;
	}

	public void setBinWeight(Long binWeight) {
		this.binWeight = binWeight;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public Long getBinDestinationId() {
		return binDestinationId;
	}

	public void setBinDestinationId(Long binDestinationId) {
		this.binDestinationId = binDestinationId;
	}

	public List<BinDetail> getBinDetails() {
		return binDetails;
	}

	public void setBinDetails(List<BinDetail> binDetails) {
		this.binDetails = binDetails;
	}

	public BinLocation getBinLocationDetails(@Valid BinDetailDto bindetailDto) {
		// Instant currentTs = Instant.now();
		BinLocation binLocation = new BinLocation();
		binLocation.setId(bindetailDto.getId());
		binLocation.setBinTypeId(bindetailDto.getBinTypeId());
		binLocation.setLocationId(bindetailDto.getLocationId());
		binLocation.setBinWeight(bindetailDto.getBinWeight());
		binLocation.setTruckFillWeight(bindetailDto.getTruckFillWeight());
		binLocation.setUnit(bindetailDto.getUnit());
		binLocation.setAttachments(bindetailDto.getAttachments());
		binLocation.setBinDestinationId(bindetailDto.getBinDestinationId());
		binLocation.setIsDeleted(Boolean.FALSE);
		binLocation.setCreatedBy(bindetailDto.getCreatedBy());
		binLocation.setCreatedTs(bindetailDto.getCreatedTS());
		binLocation.setBinLocationNotes((bindetailDto.getBinLocationNotes() != null && bindetailDto.getBinLocationNotes().size() > 0)?bindetailDto.getBinLocationNotes(): null);
		binLocation.setBinCustomField((bindetailDto.getBinCustomField() != null && bindetailDto.getBinCustomField().size() > 0)?bindetailDto.getBinCustomField():null);
//		binLocation.setRating(0L);

		if (bindetailDto.getId() == null) {
			binLocation.setUpdatedBy(0L);
			binLocation.setUpdatedTs(bindetailDto.getCreatedTS());
		} else {
			binLocation.setUpdatedBy(bindetailDto.getModifiedBy());
			binLocation.setUpdatedTs(bindetailDto.getModifiedTS());
		}
		return binLocation;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Instant getCreatedTS() {
		return createdTS;
	}

	public void setCreatedTS(Instant createdTS) {
		this.createdTS = createdTS;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Instant getModifiedTS() {
		return modifiedTS;
	}

	public void setModifiedTS(Instant modifiedTS) {
		this.modifiedTS = modifiedTS;
	}

	public Long getTruckFillWeight() {
		return truckFillWeight;
	}

	public void setTruckFillWeight(Long truckFillWeight) {
		this.truckFillWeight = truckFillWeight;
	}

	public Set<BinLocationNotes> getBinLocationNotes() {
		return binLocationNotes;
	}

	public void setBinLocationNotes(Set<BinLocationNotes> binLocationNotes) {
		this.binLocationNotes = binLocationNotes;
	}

	public Set<BinLocationCustomInstance> getBinCustomField() {
		return binCustomField;
	}

	public void setBinCustomField(Set<BinLocationCustomInstance> binCustomField) {
		this.binCustomField = binCustomField;
	}
	
}
