package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.dto.InterLinkInternalNotesDto;
import com.checksammy.loca.model.JobRequestNotes;

public interface JobRequestNotesService {

	List<JobRequestNotes> saveJobRequestNote(List<JobRequestNotes> jobRequestNotes);

	Boolean deleteRequest(Long jobReqId);

	List<JobRequestNotes> findInternalNoteByRequestId(Long requestId, String type);

	void updateRequestNote(InterLinkInternalNotesDto interLinkInternalNotesDto);

	JobRequestNotes saveRequestNote(JobRequestNotes jobRequestNotes);

}
