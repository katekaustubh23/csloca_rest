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
@Table(name="bin_type")
@Where(clause = "is_deleted=0")
public class BinType extends AuditModel{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "content_value_scale")
	private String contentValueScale;
	
	@Column(name="is_deleted")
	private Boolean isDeleted;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "binTypeId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
    private Set<BinContent> binContents = new HashSet<>();
	
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
	
	public String getContentValueScale() {
		return contentValueScale;
	}

	public void setContentValueScale(String contentValueScale) {
		this.contentValueScale = contentValueScale;
	}

	public Set<BinContent> getBinContents() {
		return binContents;
	}

	public void setBinContents(Set<BinContent> binContents) {
		this.binContents = binContents;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
