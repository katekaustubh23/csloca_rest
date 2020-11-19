package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.FieldType;

@Repository
public interface FieldTypeRepository extends JpaRepository<FieldType, Long>{

	FieldType findByDisplayName(String displayName);

}
