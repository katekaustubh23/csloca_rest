package com.checksammy.loca.dto;

import java.time.Instant;

import com.checksammy.loca.model.JobInternalNotes;
import com.checksammy.loca.model.JobRequestNotes;
import com.checksammy.loca.model.QuotesInternalNotes;
import com.checksammy.loca.model.User;

public class InterLinkInternalNotesDto {
	
	private Long id;
	
	private Long propertyId;
	
	private String noteFrom;
	
	private Long invoiceId;
	
	private String notes;
	
	private String attachment;
	
	private Long createdBy;
	
	private Instant createdTs;
	
	private Long updatedBy;
	
	private Instant updatedTs;
	
	private String createdUserDetails;
	
	private User relatedUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	

	public String getCreatedUserDetails() {
		return createdUserDetails;
	}

	public void setCreatedUserDetails(String createdUserDetails) {
		this.createdUserDetails = createdUserDetails;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public String getNoteFrom() {
		return noteFrom;
	}

	public void setNoteFrom(String noteFrom) {
		this.noteFrom = noteFrom;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public static InterLinkInternalNotesDto mappRequestData(JobRequestNotes requestNote) {
		InterLinkInternalNotesDto interLinkInternalNotesDto = new InterLinkInternalNotesDto();
		interLinkInternalNotesDto.setId(requestNote.getId());
//		interLinkInternalNotesDto.setAttachment(requestNote.getA);
		interLinkInternalNotesDto.setCreatedBy(requestNote.getCreatedBy());
		interLinkInternalNotesDto.setCreatedTs(requestNote.getCreatedTs());

		interLinkInternalNotesDto.setNotes(requestNote.getNotes());
		interLinkInternalNotesDto.setCreatedUserDetails(requestNote.getCreatedUserDetails());
		
		return interLinkInternalNotesDto;
	}

	public static InterLinkInternalNotesDto mappQuoteData(QuotesInternalNotes quoteInternalNote) {
		InterLinkInternalNotesDto interLinkInternalNotesDto = new InterLinkInternalNotesDto();
		interLinkInternalNotesDto.setId(quoteInternalNote.getId());
//		interLinkInternalNotesDto.setAttachment(requestNote.getA);
		interLinkInternalNotesDto.setCreatedBy(quoteInternalNote.getCreatedBy());
		interLinkInternalNotesDto.setCreatedTs(quoteInternalNote.getCreatedTs());
		interLinkInternalNotesDto.setUpdatedBy(quoteInternalNote.getUpdatedBy());
		interLinkInternalNotesDto.setUpdatedTs(quoteInternalNote.getUpdatedTs());
		interLinkInternalNotesDto.setNotes(quoteInternalNote.getNotes());
		return interLinkInternalNotesDto;
	}

	public static InterLinkInternalNotesDto mappJobData(JobInternalNotes jobInternalNote) {
		InterLinkInternalNotesDto interLinkInternalNotesDto = new InterLinkInternalNotesDto();
		interLinkInternalNotesDto.setId(jobInternalNote.getId());
//		interLinkInternalNotesDto.setAttachment(requestNote.getA);
		interLinkInternalNotesDto.setCreatedBy(jobInternalNote.getCreatedBy());
		interLinkInternalNotesDto.setCreatedTs(jobInternalNote.getCreatedTs());
		interLinkInternalNotesDto.setUpdatedBy(jobInternalNote.getUpdatedBy());
		interLinkInternalNotesDto.setUpdatedTs(jobInternalNote.getUpdatedTs());
		interLinkInternalNotesDto.setNotes(jobInternalNote.getNotes());
		return interLinkInternalNotesDto;
	}

	
	
}
