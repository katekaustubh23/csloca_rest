package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.PropertyDetailNote;
import com.checksammy.loca.repository.PropertyDetailNoteRepository;
import com.checksammy.loca.service.PropertyDetailNoteService;

@Service
public class PropertyDetailNoteServiceImpl implements PropertyDetailNoteService{
	
	@Autowired
	private PropertyDetailNoteRepository repoNoteRepository;

	@Override
	public List<PropertyDetailNote> savePropertyDetail(List<PropertyDetailNote> propDetailNote) {
		return repoNoteRepository.saveAll(propDetailNote);
	}

	@Override
	public Boolean deletePropertyNote(Long propNoteId) {
		Boolean status = false;
		try {
			repoNoteRepository.deleteById(propNoteId);
			status = true;
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

}
