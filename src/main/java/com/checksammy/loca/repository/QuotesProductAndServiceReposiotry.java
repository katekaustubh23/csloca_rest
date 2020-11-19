package com.checksammy.loca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.QuotesProductAndService;

@Repository
public interface QuotesProductAndServiceReposiotry extends JpaRepository<QuotesProductAndService, Long>{

	@Query(value="SELECT picture FROM quotes_product_service WHERE id =?1", nativeQuery = true)
	String getRequestAttachment(Long quoteProductId);

	@Modifying
	@Transactional
	@Query(value="UPDATE quotes_product_service SET picture = ?2 WHERE id =?1", nativeQuery = true)
	void updateAttachmentsById(Long quoteProductId, String attachmentsName);

}
