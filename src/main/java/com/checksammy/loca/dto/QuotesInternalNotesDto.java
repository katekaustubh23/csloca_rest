package com.checksammy.loca.dto;

import java.time.Instant;

import com.checksammy.loca.model.QuotesInternalNotes;
import com.checksammy.loca.model.User;

public class QuotesInternalNotesDto {

	private Long id;

	private Long quoteId;

	private String notes;

	private String attachment;

	private Long createdBy;

	private Instant createdTs;

	private Long updatedBy;

	private Instant updatedTs;
	
	private String linkNote;
	
	private String internalLink;
	
	private User relatedUser;

	public String getLinkNote() {
		return linkNote;
	}

	public void setLinkNote(String linkNote) {
		this.linkNote = linkNote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
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

	public String getInternalLink() {
		return internalLink;
	}

	public void setInternalLink(String internalLink) {
		this.internalLink = internalLink;
	}

	public QuotesInternalNotes mapWithQuoteInternalNotes(QuotesInternalNotesDto quoteInternalNoteDto) {
		QuotesInternalNotes quotesInternalNotes = new QuotesInternalNotes();
		quotesInternalNotes.setAttachment(quoteInternalNoteDto.getAttachment());
		quotesInternalNotes.setCreatedBy(quoteInternalNoteDto.getCreatedBy());
		quotesInternalNotes.setCreatedTs(quoteInternalNoteDto.getCreatedTs());
		if (quoteInternalNoteDto.getId() != null && quoteInternalNoteDto.getId() > 0) {
			quotesInternalNotes.setId(quoteInternalNoteDto.getId());
			quotesInternalNotes.setUpdatedBy(quoteInternalNoteDto.getCreatedBy());
			quotesInternalNotes.setUpdatedTs(Instant.now());
		}

		quotesInternalNotes.setNotes(quoteInternalNoteDto.getNotes());
		quotesInternalNotes.setQuoteId(quoteInternalNoteDto.getQuoteId());
		quotesInternalNotes.setInternalLink(quoteInternalNoteDto.getInternalLink());
		return quotesInternalNotes;
	}

	public QuotesInternalNotesDto addedRealtedUserwithData(User realtedUser, QuotesInternalNotes quotesInternalNotes2) {
		QuotesInternalNotesDto quotesInternalNotesDto = new QuotesInternalNotesDto();
		quotesInternalNotesDto.setAttachment(quotesInternalNotes2.getAttachment());
		quotesInternalNotesDto.setCreatedBy(quotesInternalNotes2.getCreatedBy());
		quotesInternalNotesDto.setCreatedTs(quotesInternalNotes2.getCreatedTs());
		quotesInternalNotesDto.setId(quotesInternalNotes2.getId());
		quotesInternalNotesDto.setNotes(quotesInternalNotes2.getNotes());
		quotesInternalNotesDto.setQuoteId(quotesInternalNotes2.getQuoteId());
		if(realtedUser != null) {
			quotesInternalNotesDto.setRelatedUser(realtedUser);	
		}
		quotesInternalNotesDto.setInternalLink(quotesInternalNotes2.getInternalLink());
//		quotesInternalNotesDto.setUpdatedBy(updatedBy);
//		quotesInternalNotesDto.setUpdatedTs(updatedTs);
		
		return quotesInternalNotesDto;
	}

}
