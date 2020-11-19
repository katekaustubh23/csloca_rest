package com.checksammy.loca.repository;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.InvoiceProductAndService;

@Repository
public interface InvoiceProductAndServiceRepository extends JpaRepository<InvoiceProductAndService, Serializable>{

	@Modifying
	@Transactional
	@Query(value="UPDATE invoice_product_service SET picture = ?2 WHERE id =?1", nativeQuery = true)
	void updateAttachmentsById(Long invoiceProductId, String attachmentsName);

}
