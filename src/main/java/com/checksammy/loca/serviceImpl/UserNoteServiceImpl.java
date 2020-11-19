package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.UserNote;
import com.checksammy.loca.repository.UserNoteRepository;
import com.checksammy.loca.service.UserNoteService;

@Service
public class UserNoteServiceImpl implements UserNoteService{
	
	@Autowired
	private UserNoteRepository userNoteRepository;

	@Override
	public List<UserNote> saveNotes(List<UserNote> userNotes) {
		return userNoteRepository.saveAll(userNotes);
	}

	@Override
	public Boolean deleteUserNote(Long notesID) {
		Boolean status = false;
		try {
			userNoteRepository.deleteById(notesID);
			status = true;
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

}
