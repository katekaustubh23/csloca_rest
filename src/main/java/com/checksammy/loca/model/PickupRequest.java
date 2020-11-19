/**
 * 
 */
package com.checksammy.loca.model;

import java.time.Instant;

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
@Table(name="pickup_request")
public class PickupRequest extends AuditModel{

	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PickupRequest() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name="location_id")
	private Long locationId;
	
	@Column(name="pickup_date")
	private Instant pickupDate;
	
	@Column(name="pickup_timerange")
	private String pickupTimerange;

	@Column(name="bin_photos")
	private String binPhotos;

	@Column(name="status")
	private String status;

	@Column(name="is_deleted")
	private Boolean isDeleted;
	
	@Column(name="bin_weight")
	private Long binWeight;
	
	@Column(name="bin_type")
	private Long binTypeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Instant getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Instant pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getPickupTimerange() {
		return pickupTimerange;
	}

	public void setPickupTimerange(String pickupTimerange) {
		this.pickupTimerange = pickupTimerange;
	}

	public String getBinPhotos() {
		return binPhotos;
	}

	public void setBinPhotos(String binPhotos) {
		this.binPhotos = binPhotos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getBinWeight() {
		return binWeight;
	}

	public void setBinWeight(Long binWeight) {
		this.binWeight = binWeight;
	}

	public Long getBinTypeId() {
		return binTypeId;
	}

	public void setBinType(Long binTypeId) {
		this.binTypeId = binTypeId;
	}

}
