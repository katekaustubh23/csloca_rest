package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.CustomFieldCategory;

@Repository
public interface CustomFieldCategoryRepository extends JpaRepository<CustomFieldCategory, Long>{

}
