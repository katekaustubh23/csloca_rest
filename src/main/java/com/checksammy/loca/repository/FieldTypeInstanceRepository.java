package com.checksammy.loca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.FieldTypeInstance;

@Repository
public interface FieldTypeInstanceRepository extends JpaRepository<FieldTypeInstance, Long>{

	@Query(value="SELECT * FROM field_type_instance WHERE customer LIKE ?1 AND is_deleted = 0", nativeQuery = true)
	List<FieldTypeInstance> findByCustomName(String customName);

	@Modifying
	@Transactional
	@Query(value="UPDATE field_type_instance SET is_deleted = 1 WHERE id =?1", nativeQuery = true)
	void deletedById(Long fieldInstanceId);

	@Query(value="SELECT * FROM field_type_instance WHERE field_type_id =?1 And customer LIKE ?2 AND is_deleted = 0", nativeQuery = true)
	List<FieldTypeInstance> findByFieldIdAnd(Long instanceId, String likeString);

}
