package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.BinLocationNotes;
import com.checksammy.loca.repository.BinLocationNotesRepository;
import com.checksammy.loca.service.BinLocationNotesService;

@Service
public class BinLocationNotesServiceImpl implements BinLocationNotesService{

	private static final Logger logger = LoggerFactory.getLogger(BinLocationNotesServiceImpl.class);
	@Autowired
	private BinLocationNotesRepository binLocationNotesRepository;
	
	@Override
	public List<BinLocationNotes> saveList(List<BinLocationNotes> binLocationNotes) {
		return binLocationNotesRepository.saveAll(binLocationNotes);
	}

	@Override
	public String deleteBinLocationNotes(Long noteId) {
		String status;
		try {
			binLocationNotesRepository.deleteById(noteId);
			status = "true";
		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
			status = "false";
		}
		return status;
	}

}
