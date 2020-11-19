package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.UserFieldInstance;
import com.checksammy.loca.repository.CustomFieldInstanceRepository;
import com.checksammy.loca.service.CustomFieldInstanceService;

@Service
public class CustomFieldInstanceServiceImpl implements CustomFieldInstanceService{

	@Autowired
	private CustomFieldInstanceRepository custInstanceRepository;

	@Override
	public List<UserFieldInstance> saveAllList(List<UserFieldInstance> customFieldInstance) {
		return custInstanceRepository.saveAll(customFieldInstance);
	}

	@Override
	public void deleteFromUser(Long fieldInstanceId) {
		custInstanceRepository.deleteUserFieldByFTI(fieldInstanceId);
	}
}
