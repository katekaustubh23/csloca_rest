package com.checksammy.loca.serviceImpl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.LocationNote;
import com.checksammy.loca.repository.LocationNoteRepository;
import com.checksammy.loca.service.LocationNoteService;

@Service
public class LocationNoteServiceImpl implements LocationNoteService{
	
	private static final Logger logger = LoggerFactory.getLogger(LocationNoteServiceImpl.class);

	@Autowired
	private LocationNoteRepository locationNoteRepo;

	@Override
	public List<LocationNote> saveLocationNotes(Set<LocationNote> locationNotes) {
		return locationNoteRepo.saveAll(locationNotes);
	}

	@Override
	public Boolean deleteLocationNotes(Long noteId) {
		Boolean status = false;
		try {
			locationNoteRepo.deleteById(noteId);
			status = true;
		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
			status = false;
		}
		return status;
	}
}
