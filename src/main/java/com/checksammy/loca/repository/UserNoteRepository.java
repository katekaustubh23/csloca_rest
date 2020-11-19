package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.UserNote;

@Repository
public interface UserNoteRepository extends JpaRepository<UserNote, Long>{

}
