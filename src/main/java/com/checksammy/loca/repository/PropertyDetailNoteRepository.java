package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.PropertyDetailNote;

@Repository
public interface PropertyDetailNoteRepository extends JpaRepository<PropertyDetailNote, Long>{

}
