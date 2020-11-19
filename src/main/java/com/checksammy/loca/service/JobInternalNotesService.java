package com.checksammy.loca.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.InterLinkInternalNotesDto;
import com.checksammy.loca.dto.JobInternalNotesDto;
import com.checksammy.loca.dto.QuotesInternalNotesDto;
import com.checksammy.loca.model.JobInternalNotes;

public interface JobInternalNotesService {

	String saveRequestAttachments(Long internalNotesId, MultipartFile[] files);

	JobInternalNotes saveDataSeperatly(JobInternalNotesDto jobInternalNoteDto);

	List<JobInternalNotesDto> getRealtedNote(Long jobId);

	List<JobInternalNotes> findNotesBy(Long jobId, String type);

	void updateJobNote(InterLinkInternalNotesDto interLinkInternalNotesDto);


}
