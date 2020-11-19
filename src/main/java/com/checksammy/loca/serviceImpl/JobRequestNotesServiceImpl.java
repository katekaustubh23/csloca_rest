package com.checksammy.loca.serviceImpl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.InterLinkInternalNotesDto;
import com.checksammy.loca.model.JobRequestNotes;
import com.checksammy.loca.repository.JobRequestNotesRepository;
import com.checksammy.loca.service.JobRequestNotesService;

@Service
public class JobRequestNotesServiceImpl implements JobRequestNotesService {

	@Autowired
	private JobRequestNotesRepository jobNotesRepository;

	@Override
	public List<JobRequestNotes> saveJobRequestNote(List<JobRequestNotes> jobRequestNotes) {
		return jobNotesRepository.saveAll(jobRequestNotes);
	}

	@Override
	public Boolean deleteRequest(Long jobReqId) {
		Boolean status = false;
		try {
			jobNotesRepository.deleteById(jobReqId);
			status = true;
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	@Override
	public List<JobRequestNotes> findInternalNoteByRequestId(Long requestId, String type) {
		String type2 = "%"+type+"%";
		return jobNotesRepository.findByJobRequestId(requestId, type2);
	}

	@Override
	public void updateRequestNote(InterLinkInternalNotesDto interLinkInternalNotesDto) {
		Instant updateDate = Instant.now();
		jobNotesRepository.updateNoteData(interLinkInternalNotesDto.getId(), interLinkInternalNotesDto.getNotes(),interLinkInternalNotesDto.getUpdatedBy(), updateDate);
	}

	@Override
	public JobRequestNotes saveRequestNote(JobRequestNotes jobRequestNotes) {
		return jobNotesRepository.save(jobRequestNotes);
	}
}
