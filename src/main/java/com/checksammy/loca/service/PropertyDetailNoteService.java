package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.PropertyDetailNote;

public interface PropertyDetailNoteService {

	List<PropertyDetailNote> savePropertyDetail(List<PropertyDetailNote> propDetailNote);

	Boolean deletePropertyNote(Long propNoteId);

}
