package com.checksammy.loca.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.InvoiceInternalNotesDto;
import com.checksammy.loca.model.InvoiceInternalNotes;

public interface InvoiceInternalNotesService {

	InvoiceInternalNotes saveDataSeperatly(InvoiceInternalNotesDto invoiceInternalNotesDto);

	String saveRequestAttachments(Long internalNotesId, MultipartFile[] files);

	List<InvoiceInternalNotesDto> getRealtedNote(Long invoiceId);

}
