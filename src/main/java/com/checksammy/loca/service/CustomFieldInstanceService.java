package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.UserFieldInstance;

public interface CustomFieldInstanceService {

	List<UserFieldInstance> saveAllList(List<UserFieldInstance> customFieldInstance);

	void deleteFromUser(Long fieldInstanceId);

}
