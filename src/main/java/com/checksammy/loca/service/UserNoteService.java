package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.UserNote;

public interface UserNoteService {

	List<UserNote> saveNotes(List<UserNote> userNotes);

	Boolean deleteUserNote(Long notesID);

}
