package com.checksammy.loca.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="field_type_instance")
@Where(clause = "is_deleted=0")
public class FieldTypeInstance implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@JoinColumn(name = "field_type_id", referencedColumnName = "id")
	@ManyToOne()
	private FieldType fieldType;
	
	@JoinColumn(name = "custom_field_category_id", referencedColumnName = "id")
	@ManyToOne()
	private CustomFieldCategory CustomFieldCategory;
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "field_name")
	private String fieldName;
	
	@Column(name = "is_transferable")
	private Boolean isTransferable;
	
	@Column(name = "default_value")
	private String defaultValue;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public CustomFieldCategory getCustomFieldCategory() {
		return CustomFieldCategory;
	}

	public void setCustomFieldCategory(CustomFieldCategory customFieldCategory) {
		CustomFieldCategory = customFieldCategory;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getIsTransferable() {
		return isTransferable;
	}

	public void setIsTransferable(Boolean isTransferable) {
		this.isTransferable = isTransferable;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
