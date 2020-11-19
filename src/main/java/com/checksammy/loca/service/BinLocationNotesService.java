package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.BinLocationNotes;

public interface BinLocationNotesService {

	List<BinLocationNotes> saveList(List<BinLocationNotes> binLocationNotes);

	String deleteBinLocationNotes(Long noteId);
		

}
