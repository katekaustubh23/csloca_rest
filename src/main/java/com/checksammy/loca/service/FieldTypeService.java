package com.checksammy.loca.service;

import com.checksammy.loca.model.FieldType;

public interface FieldTypeService {

	FieldType saveFieldType(FieldType fieldType);

	FieldType findByFieldName(String displayName);

}
