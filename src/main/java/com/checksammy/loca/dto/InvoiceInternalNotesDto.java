package com.checksammy.loca.dto;

import java.time.Instant;

import com.checksammy.loca.model.InvoiceInternalNotes;
import com.checksammy.loca.model.JobInternalNotes;
import com.checksammy.loca.model.User;

public class InvoiceInternalNotesDto {

	private Long id;
	
	private Long invoiceId;
	
	private String notes;
	
	private String attachment;
	
	private Long createdBy;
	
	private Instant createdTs;
	
	private Long updatedBy;
	
	private Instant updatedTs;
	
	private User relatedUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Instant getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Instant updatedTs) {
		this.updatedTs = updatedTs;
	}

	public User getRelatedUser() {
		return relatedUser;
	}

	public void setRelatedUser(User relatedUser) {
		this.relatedUser = relatedUser;
	}

	public InvoiceInternalNotesDto addedRealtedUserwithData(User realtedUser,
			InvoiceInternalNotes invoiceInternalNotes2) {
		InvoiceInternalNotesDto invoiceInternalNotesDto = new InvoiceInternalNotesDto();
		invoiceInternalNotesDto.setAttachment(invoiceInternalNotes2.getAttachment());
		invoiceInternalNotesDto.setCreatedBy(invoiceInternalNotes2.getCreatedBy());
		invoiceInternalNotesDto.setCreatedTs(invoiceInternalNotes2.getCreatedTs());
		invoiceInternalNotesDto.setId(invoiceInternalNotes2.getId());
		invoiceInternalNotesDto.setNotes(invoiceInternalNotes2.getNotes());
		invoiceInternalNotesDto.setInvoiceId(invoiceInternalNotes2.getInvoiceId());
		if(realtedUser != null) {
			invoiceInternalNotesDto.setRelatedUser(realtedUser);	
		}
		
//		quotesInternalNotesDto.setUpdatedBy(updatedBy);
//		quotesInternalNotesDto.setUpdatedTs(updatedTs);
		
		return invoiceInternalNotesDto;
	}

	public InvoiceInternalNotes mapWithInvoiceInternalNotes(InvoiceInternalNotesDto invoiceInternalNotesDto) {
		InvoiceInternalNotes invoiceInternalNotes = new InvoiceInternalNotes();
		invoiceInternalNotes.setAttachment(invoiceInternalNotesDto.getAttachment());
		invoiceInternalNotes.setCreatedBy(invoiceInternalNotesDto.getCreatedBy());
		invoiceInternalNotes.setCreatedTs(invoiceInternalNotesDto.getCreatedTs());
		if (invoiceInternalNotesDto.getId() != null && invoiceInternalNotesDto.getId() > 0) {
			invoiceInternalNotes.setId(invoiceInternalNotesDto.getId());
			invoiceInternalNotes.setUpdatedBy(invoiceInternalNotesDto.getCreatedBy());
			invoiceInternalNotes.setUpdatedTs(Instant.now());
		}

		invoiceInternalNotes.setNotes(invoiceInternalNotesDto.getNotes());
		invoiceInternalNotes.setInvoiceId(invoiceInternalNotesDto.getInvoiceId());

		return invoiceInternalNotes;
	}
	
	
}
