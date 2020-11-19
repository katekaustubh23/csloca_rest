package com.checksammy.loca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.LocationCustomField;

@Repository
public interface LocationCustomFieldRepository extends JpaRepository<LocationCustomField, Long>{

	@Modifying
	@Transactional
	@Query(value="UPDATE location_custom_field SET location_id = ?1 WHERE id IN ?2", nativeQuery = true)
	void updatedLocationCustomLocId(Long id, List<Long> locationCustomFieldIds);

	@Modifying
	@Transactional
	@Query(value="UPDATE location_custom_field SET is_deleted = 1 WHERE field_instance_id =?1", nativeQuery = true)
	void deleteByFieldInstanceId(Long fieldInstanceId);

}
