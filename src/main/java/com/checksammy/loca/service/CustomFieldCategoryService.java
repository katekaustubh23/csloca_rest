package com.checksammy.loca.service;

import java.util.List;
import java.util.Optional;

import com.checksammy.loca.model.CustomFieldCategory;

public interface CustomFieldCategoryService {

	List<CustomFieldCategory> getAllList();

	Optional<CustomFieldCategory> getByCustomFieldId(Long customId);

}
