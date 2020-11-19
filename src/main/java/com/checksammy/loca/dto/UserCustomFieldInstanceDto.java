package com.checksammy.loca.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.model.UserFieldInstance;

public class UserCustomFieldInstanceDto {

	private Long id;
	
	private Long userId;
	
	private Long fieldInstanceId;
	
	private String customer;
	
	private String fieldName;
	
	private String categoryType;
	
	private String defaultValue;
	
	private Boolean isTransferable;
	
	private String transferableField;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Boolean isDeleted;
	
	private List<HashMap<String, String>> defaultValueList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFieldInstanceId() {
		return fieldInstanceId;
	}

	public void setFieldInstanceId(Long fieldInstanceId) {
		this.fieldInstanceId = fieldInstanceId;
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

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
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

	public String getTransferableField() {
		return transferableField;
	}

	public void setTransferableField(String transferableField) {
		this.transferableField = transferableField;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<HashMap<String, String>> getDefaultValueList() {
		return defaultValueList;
	}

	public void setDefaultValueList(List<HashMap<String, String>> defaultValueList) {
		this.defaultValueList = defaultValueList;
	}

	public UserCustomFieldInstanceDto addCustomRecord(UserFieldInstance customFieldInstance, FieldTypeInstance fieldTypeInstance) {
		UserCustomFieldInstanceDto userCustomFieldInstanceDto = new UserCustomFieldInstanceDto();
		userCustomFieldInstanceDto.setCategoryType(fieldTypeInstance.getCustomFieldCategory().getType());
		userCustomFieldInstanceDto.setCreatedBy(customFieldInstance.getCreatedBy());
		userCustomFieldInstanceDto.setCreatedDate(customFieldInstance.getCreatedDate());
		userCustomFieldInstanceDto.setCustomer(customFieldInstance.getCustomer());
		userCustomFieldInstanceDto.setDefaultValue(customFieldInstance.getDefaultValue());
		userCustomFieldInstanceDto.setFieldInstanceId(customFieldInstance.getFieldInstanceId());
		userCustomFieldInstanceDto.setFieldName(fieldTypeInstance.getFieldName());
		userCustomFieldInstanceDto.setId(customFieldInstance.getId());
		userCustomFieldInstanceDto.setIsDeleted(customFieldInstance.getIsDeleted());
		userCustomFieldInstanceDto.setIsTransferable(customFieldInstance.getIsTransferable());
		userCustomFieldInstanceDto.setTransferableField(customFieldInstance.getTransferableField());
		userCustomFieldInstanceDto.setUserId(customFieldInstance.getUserId());
		
		if (fieldTypeInstance.getCustomFieldCategory().getCategoryName().equalsIgnoreCase("dropdown")) {
			List<HashMap<String, String>> stringArrayList = new ArrayList<HashMap<String,String>>();
			String[] elements = fieldTypeInstance.getDefaultValue().split(",");
			for (int i = 0; i < elements.length; i++) {
				HashMap<String, String> defaultString2 = new HashMap<String, String>();
				System.out.println();
				defaultString2.put("value", elements[i]);
				stringArrayList.add(defaultString2);
			}
			userCustomFieldInstanceDto.setDefaultValueList(stringArrayList);
		}
		return userCustomFieldInstanceDto;
	}
	
}
