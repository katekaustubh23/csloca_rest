package com.checksammy.loca.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author kartik.thakre
 *
 */
@Entity
@Table(name = "bin_detail")
public class BinDetail extends AuditModel {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "bin_location_id")
	private Long binLocationId;
	
	@Column(name = "bin_content_id")
	private Long binContentId;
	
	@Column(name = "content_value")
	private Long contentValue;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;	

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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
