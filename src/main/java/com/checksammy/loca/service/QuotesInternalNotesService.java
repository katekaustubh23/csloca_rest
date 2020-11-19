package com.checksammy.loca.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.InterLinkInternalNotesDto;
import com.checksammy.loca.dto.QuotesInternalNotesDto;
import com.checksammy.loca.model.QuotesInternalNotes;

public interface QuotesInternalNotesService {

	String saveRequestAttachments(Long internalNotesId, MultipartFile[] files);

	QuotesInternalNotes saveDataSeperatly(QuotesInternalNotesDto quoteInternalNoteDto);

	List<QuotesInternalNotesDto> getRealtedNote(Long quoteId);

	List<QuotesInternalNotes> findNotesBy(Long quoteId, String type);

	void updateQuoteNote(InterLinkInternalNotesDto interLinkInternalNotesDto);


}
