package com.checksammy.loca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.ProductService;

@Repository
public interface ProductServiceRepository extends JpaRepository<ProductService, Long>{

	@Modifying
	@Transactional
	@Query(value="UPDATE product_service SET picture =?2 WHERE id=?1", nativeQuery = true)
	void updateAttachmentsById(Long requestID, String attachmentsName);

	@Modifying
	@Transactional
	@Query(value="UPDATE product_service SET is_deleted = 1 WHERE id=?1", nativeQuery = true)
	void deleteByChangeStatus(Long requestID);

	@Query(value="SELECT picture FROM product_service WHERE id =?1", nativeQuery = true)
	String findImageName(Long requestID);

	@Query(value="SELECT EXISTS(SELECT * FROM product_service WHERE name = ?1 AND is_deleted = 0)", nativeQuery = true)
	Long existsByNameAndDelete(String name);
}
