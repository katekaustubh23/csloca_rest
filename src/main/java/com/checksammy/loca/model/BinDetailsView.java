/**
 * 
 */
package com.checksammy.loca.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Abhishek Srivastava
 *
 */
@Entity
@Table(name = "vgetbindetails")
public class BinDetailsView {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "bin_location_id")
	private Long binLocationId;
	
	@Column(name="bin_content_id")
	private Long binContentId;
	
	@Column(name="content_value")
	private Long contentValue;
	
	@Column(name="bin_type_id")
	private Long binTypeId;
	
	@Column(name="bin_weight")
	private Long binWeight;
	
	@Column(name="truck_fill_weight")
	private Long truckFillWeight;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="bin_destination_id")
	private Long binDestinationId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBinLocationId() {
		return binLocationId;
	}

	public void setBinLocationId(Long binLocationId) {
		this.binLocationId = binLocationId;
	}

	public Long getBinContentId() {
		return binContentId;
	}

	public void setBinContentId(Long binContentId) {
		this.binContentId = binContentId;
	}

	public Long getContentValue() {
		return contentValue;
	}

	public void setContentValue(Long contentValue) {
		this.contentValue = contentValue;
	}

	public Long getBinTypeId() {
		return binTypeId;
	}

	public void setBinTypeId(Long binTypeId) {
		this.binTypeId = binTypeId;
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

	public Long getBinDestinationId() {
		return binDestinationId;
	}

	public void setBinDestinationId(Long binDestinationId) {
		this.binDestinationId = binDestinationId;
	}

	public Long getTruckFillWeight() {
		return truckFillWeight;
	}

	public void setTruckFillWeight(Long truckFillWeight) {
		this.truckFillWeight = truckFillWeight;
	}
	
}
