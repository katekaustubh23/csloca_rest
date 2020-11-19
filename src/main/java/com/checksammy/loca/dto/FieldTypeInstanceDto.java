package com.checksammy.loca.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.checksammy.loca.model.CustomFieldCategory;
import com.checksammy.loca.model.FieldType;
import com.checksammy.loca.model.FieldTypeInstance;

public class FieldTypeInstanceDto {

	private Long id;

	private FieldType fieldType;

	private CustomFieldCategory CustomFieldCategory;

	private String customer;

	private String fieldName;

	private Boolean isTransferable;

	private String defaultValue;

	private Boolean isDeleted;

	private List<HashMap<String, String>> defaultString;

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

	public Boolean getIsTransferable() {
		return isTransferable;
	}

	public void setIsTransferable(Boolean isTransferable) {
		this.isTransferable = isTransferable;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<HashMap<String, String>> getDefaultString() {
		return defaultString;
	}

	public void setDefaultString(List<HashMap<String, String>> defaultString) {
		this.defaultString = defaultString;
	}

	public FieldTypeInstanceDto getList(FieldTypeInstance fieldTypeInstance2) {
		FieldTypeInstanceDto fieldTypeInstanceDto = new FieldTypeInstanceDto();
		fieldTypeInstanceDto.setCustomer(fieldTypeInstance2.getCustomer());
		fieldTypeInstanceDto.setCustomFieldCategory(fieldTypeInstance2.getCustomFieldCategory());
		fieldTypeInstanceDto.setDefaultValue(fieldTypeInstance2.getDefaultValue());
		fieldTypeInstanceDto.setFieldName(fieldTypeInstance2.getFieldName());
		fieldTypeInstanceDto.setFieldType(fieldTypeInstance2.getFieldType());
		fieldTypeInstanceDto.setId(fieldTypeInstance2.getId());
		fieldTypeInstanceDto.setIsDeleted(fieldTypeInstance2.getIsDeleted());
		fieldTypeInstanceDto.setIsTransferable(fieldTypeInstance2.getIsTransferable());

		if (fieldTypeInstance2.getCustomFieldCategory().getCategoryName().equalsIgnoreCase("dropdown")) {
			List<HashMap<String, String>> stringArrayList = new ArrayList<HashMap<String,String>>();
			String[] elements = fieldTypeInstance2.getDefaultValue().split(",");
			for (int i = 0; i < elements.length; i++) {
				HashMap<String, String> defaultString2 = new HashMap<String, String>();
				System.out.println();
				defaultString2.put("value", elements[i]);
				stringArrayList.add(defaultString2);
			}
			fieldTypeInstanceDto.setDefaultString(stringArrayList);

		}

		return fieldTypeInstanceDto;
	}

}
