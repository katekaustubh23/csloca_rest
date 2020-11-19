package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="location_manager_maintenance_details")
public class LocationManager implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "location_id")
	private Long locationId;
	
	@Column(name = "manager_name")
	private String managerName;
	
	@Column(name = "manager_contact")
	private String managerContact;
	
	@Column(name = "manager_office_contact")
	private String managerOfficeContact;
	
	@Column(name = "manager_email")
	private String managerEmail;
	
	@Column(name = "maintenance_manager_name")
	private String maintenanceManagerName;
	
	@Column(name = "maintenance_manager_contact")
	private String maintenanceManagerContact;
	
	@Column(name = "maintenance_manager_office_contact")
	private String maintenanceManagerOfficeContact;
	
	@Column(name = "maintenance_manager_email")
	private String maintenanceManagerEmail;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "created_ts")
	private Instant createTs;

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

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerContact() {
		return managerContact;
	}

	public void setManagerContact(String managerContact) {
		this.managerContact = managerContact;
	}

	public String getManagerOfficeContact() {
		return managerOfficeContact;
	}

	public void setManagerOfficeContact(String managerOfficeContact) {
		this.managerOfficeContact = managerOfficeContact;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getMaintenanceManagerName() {
		return maintenanceManagerName;
	}

	public void setMaintenanceManagerName(String maintenanceManagerName) {
		this.maintenanceManagerName = maintenanceManagerName;
	}

	public String getMaintenanceManagerContact() {
		return maintenanceManagerContact;
	}

	public void setMaintenanceManagerContact(String maintenanceManagerContact) {
		this.maintenanceManagerContact = maintenanceManagerContact;
	}

	public String getMaintenanceManagerOfficeContact() {
		return maintenanceManagerOfficeContact;
	}

	public void setMaintenanceManagerOfficeContact(String maintenanceManagerOfficeContact) {
		this.maintenanceManagerOfficeContact = maintenanceManagerOfficeContact;
	}

	public String getMaintenanceManagerEmail() {
		return maintenanceManagerEmail;
	}

	public void setMaintenanceManagerEmail(String maintenanceManagerEmail) {
		this.maintenanceManagerEmail = maintenanceManagerEmail;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Instant createTs) {
		this.createTs = createTs;
	}

}
