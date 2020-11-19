package com.checksammy.loca.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.CustomFieldCategory;
import com.checksammy.loca.repository.CustomFieldCategoryRepository;
import com.checksammy.loca.service.CustomFieldCategoryService;

@Service
public class CustomFieldCategoryServiceImpl implements CustomFieldCategoryService{

	@Autowired
	private CustomFieldCategoryRepository custFieldRepo;

	@Override
	public List<CustomFieldCategory> getAllList() {
		return custFieldRepo.findAll();
	}

	@Override
	public Optional<CustomFieldCategory> getByCustomFieldId(Long customId) {
		return custFieldRepo.findById(customId);
	}
}
