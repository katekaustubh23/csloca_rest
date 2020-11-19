package com.checksammy.loca.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
/**
 * 
 * @author kartik.thakre
 *
 */
@Entity
@Table(name="bin_location")
public class BinLocation extends AuditModel {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "bin_type_id")
	private Long binTypeId;
	
	@Column(name = "location_id")
	private Long locationId;	
	
	@Column(name = "bin_weight")
	private Long binWeight;
	
	@Column(name = "truck_fill_weight")
	private Long truckFillWeight;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "attachments")
	private String attachments;
	
	@Column(name = "bin_destination_id")
	private Long binDestinationId;
	
	@Column(name="is_deleted")
	private Boolean isDeleted;
	
//	@Column(name="rating")
//	private Long rating;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "binLocationId")
//	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<BinLocationNotes> binLocationNotes = new HashSet<>();
	
	/* Custom field */
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "binLocationId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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
