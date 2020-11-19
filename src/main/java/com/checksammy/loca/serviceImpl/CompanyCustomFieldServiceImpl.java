package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.repository.CompanyCustomFieldRepository;
import com.checksammy.loca.service.CompanyCustomFieldService;

@Service
public class CompanyCustomFieldServiceImpl implements CompanyCustomFieldService{

	@Autowired
	private CompanyCustomFieldRepository companyCustomFieldRepo;

	@Override
	public void deleteFromComapnyCustom(Long fieldInstanceId) {
		companyCustomFieldRepo.deleteByFieldInstanceId(fieldInstanceId);
	}
}
