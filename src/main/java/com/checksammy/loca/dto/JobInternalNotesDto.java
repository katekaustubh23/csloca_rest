package com.checksammy.loca.dto;

import java.time.Instant;

import com.checksammy.loca.model.JobInternalNotes;
import com.checksammy.loca.model.User;

public class JobInternalNotesDto {

	private Long id;

	private Long jobId;

	private String notes;

	private String attachment;

	private Long createdBy;

	private Instant createdTs;

	private Long updatedBy;

	private Instant updatedTs;
	
	private String linkNote;
	
	private User relatedUser;
	
	private String internalLink;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
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

	public String getLinkNote() {
		return linkNote;
	}

	public void setLinkNote(String linkNote) {
		this.linkNote = linkNote;
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

	public JobInternalNotesDto addedRealtedUserwithData(User realtedUser, JobInternalNotes jobInternalNote) {
		JobInternalNotesDto jobInternalNotesDto = new JobInternalNotesDto();
		jobInternalNotesDto.setAttachment(jobInternalNote.getAttachment());
		jobInternalNotesDto.setCreatedBy(jobInternalNote.getCreatedBy());
		jobInternalNotesDto.setCreatedTs(jobInternalNote.getCreatedTs());
		jobInternalNotesDto.setId(jobInternalNote.getId());
		jobInternalNotesDto.setNotes(jobInternalNote.getNotes());
		jobInternalNotesDto.setJobId(jobInternalNote.getJobId());
		jobInternalNotesDto.setInternalLink(jobInternalNote.getInternalLink());
		if(realtedUser != null) {
			jobInternalNotesDto.setRelatedUser(realtedUser);	
		}
		return jobInternalNotesDto;
	}

	public JobInternalNotes mapWithJobInternalNotes(JobInternalNotesDto jobInternalNoteDto) {
		JobInternalNotes jobInternalNotes = new JobInternalNotes();
		jobInternalNotes.setAttachment(jobInternalNoteDto.getAttachment());
		jobInternalNotes.setCreatedBy(jobInternalNoteDto.getCreatedBy());
		jobInternalNotes.setCreatedTs(jobInternalNoteDto.getCreatedTs());
		if (jobInternalNoteDto.getId() != null && jobInternalNoteDto.getId() > 0) {
			jobInternalNotes.setId(jobInternalNoteDto.getId());
			jobInternalNotes.setUpdatedBy(jobInternalNoteDto.getCreatedBy());
			jobInternalNotes.setUpdatedTs(Instant.now());
		}

		jobInternalNotes.setNotes(jobInternalNoteDto.getNotes());
		jobInternalNotes.setJobId(jobInternalNoteDto.getJobId());
		jobInternalNotes.setInternalLink(jobInternalNoteDto.getInternalLink());

		return jobInternalNotes;
	}
	
}
