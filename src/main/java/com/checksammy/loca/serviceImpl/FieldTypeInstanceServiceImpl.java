package com.checksammy.loca.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.FieldTypeInstanceDto;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.repository.CustomFieldInstanceRepository;
import com.checksammy.loca.repository.FieldTypeInstanceRepository;
import com.checksammy.loca.service.FieldTypeInstanceService;

@Service
public class FieldTypeInstanceServiceImpl implements FieldTypeInstanceService {

	@Autowired
	private FieldTypeInstanceRepository fieldTypeInsRepo;

	@Autowired
	private CustomFieldInstanceRepository custoRepository;

	@Override
	public List<FieldTypeInstance> getAllFieldTypeInstance() {
		return fieldTypeInsRepo.findAll();
	}

	@Override
	public FieldTypeInstance saveField(FieldTypeInstance fieldTypeInstance) {

		return fieldTypeInsRepo.save(fieldTypeInstance);
	}

	@Override
	public Optional<FieldTypeInstance> getFieldTypeInstanceById(Long instanceId) {
		return fieldTypeInsRepo.findById(instanceId);
	}

	@Override
	public List<FieldTypeInstanceDto> getListCustomBy(String customBy) {
		List<FieldTypeInstanceDto> fileDtos = new ArrayList<FieldTypeInstanceDto>();
		String customName = "%" + customBy + "%";
		List<FieldTypeInstance> fieldTypeInstance = fieldTypeInsRepo.findByCustomName(customName);
		for (FieldTypeInstance fieldTypeInstance2 : fieldTypeInstance) {
			FieldTypeInstanceDto fieldTypeInstanceDto = new FieldTypeInstanceDto();
			fieldTypeInstanceDto = fieldTypeInstanceDto.getList(fieldTypeInstance2);
			fileDtos.add(fieldTypeInstanceDto);
		}
		return fileDtos;
	}

	@Override
	public void deleteField(Long fieldInstanceId) {
		fieldTypeInsRepo.deletedById(fieldInstanceId);
		custoRepository.deleteUserFieldByFTI(fieldInstanceId);
	}

	@Override
	public List<FieldTypeInstance> findByFieldIdAndCustomer(Long instanceId, String stringCustomer) {
		String likeString = "%" + stringCustomer + "%";
		return fieldTypeInsRepo.findByFieldIdAnd(instanceId, likeString);
	}
}
