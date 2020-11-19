package com.checksammy.loca.service;

import java.util.List;
import java.util.Optional;

import com.checksammy.loca.dto.FieldTypeInstanceDto;
import com.checksammy.loca.model.FieldTypeInstance;

public interface FieldTypeInstanceService {

	List<FieldTypeInstance> getAllFieldTypeInstance();

	FieldTypeInstance saveField(FieldTypeInstance fieldTypeInstance);

	Optional<FieldTypeInstance> getFieldTypeInstanceById(Long instanceId);

	List<FieldTypeInstanceDto> getListCustomBy(String customBy);

	void deleteField(Long fieldInstanceId);

	List<FieldTypeInstance> findByFieldIdAndCustomer(Long id, String stringCustomer);

}
