package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.InvoiceInternalNotes;

@Repository
public interface InvoiceInternalNotesRepository extends JpaRepository<InvoiceInternalNotes, Serializable>{

	@Query(value="SELECT attachment FROM invoice_internal_notes WHERE id =?1", nativeQuery = true)
	String getRequestAttachment(Long internalNotesId);

	@Modifying
	@Transactional
	@Query(value="UPDATE invoice_internal_notes SET attachment = ?2 WHERE id =?1", nativeQuery = true)
	void updateAttachmentsById(Long internalNotesId, String attachmentsName);

	List<InvoiceInternalNotes> findByInvoiceId(Long invoiceId);


}
