package com.checksammy.loca.service;

import java.util.List;
import java.util.Set;

import com.checksammy.loca.model.LocationNote;

public interface LocationNoteService {

	List<LocationNote> saveLocationNotes(Set<LocationNote> locationNotes);

	Boolean deleteLocationNotes(Long noteId);

}
