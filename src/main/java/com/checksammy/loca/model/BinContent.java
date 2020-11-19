package com.checksammy.loca.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

/**
 * 
 * @author kartik.thakre
 *
 */
@Entity
@Table(name="bin_content")
@Where(clause = "is_deleted=0")
public class BinContent extends AuditModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;	
	
	@Column(name = "bin_type_id")
    private Long binTypeId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getBinTypeId() {
		return binTypeId;
	}
	public void setBinTypeId(Long binTypeId) {
		this.binTypeId = binTypeId;
	}
	
	

	
}
