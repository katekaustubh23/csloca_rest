package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.LocationNote;

@Repository
public interface LocationNoteRepository extends JpaRepository<LocationNote, Long>{

}
