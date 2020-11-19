package com.checksammy.loca.repository;

import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobRequestNotes;

@Repository
public interface JobRequestNotesRepository extends JpaRepository<JobRequestNotes, Long> {

	@Query(value = "SELECT * FROM job_request_notes WHERE job_request_id =?1 AND internal_link LIKE ?2 ", nativeQuery = true)
	List<JobRequestNotes> findByJobRequestId(Long requestId, String type);

	@Modifying
	@Transactional
	@Query(value = "UPDATE job_request_notes SET notes = ?2, created_by =?3, created_ts=?4 WHERE id =?1", nativeQuery = true)
	void updateNoteData(Long id, String notes, Long createdBy, Instant updateDate);

}
