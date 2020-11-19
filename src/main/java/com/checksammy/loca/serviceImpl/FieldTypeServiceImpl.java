package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.FieldType;
import com.checksammy.loca.repository.FieldTypeRepository;
import com.checksammy.loca.service.FieldTypeService;

@Service
public class FieldTypeServiceImpl implements FieldTypeService{

	@Autowired
	private FieldTypeRepository fieldTypeRepository;

	@Override
	public FieldType saveFieldType(FieldType fieldType) {
		return fieldTypeRepository.save(fieldType);
	}

	@Override
	public FieldType findByFieldName(String displayName) {
		return fieldTypeRepository.findByDisplayName(displayName);
	} 
}
