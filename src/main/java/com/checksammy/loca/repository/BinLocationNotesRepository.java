package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.BinLocationNotes;

@Repository
public interface BinLocationNotesRepository extends JpaRepository<BinLocationNotes, Long>{

}
