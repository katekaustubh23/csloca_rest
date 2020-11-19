package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobExpenses;

@Repository
public interface JobExpensesRepository extends JpaRepository<JobExpenses, Serializable>{

	@Query(value="SELECT * FROM expenses WHERE job_id =?1 AND is_deleted = 0", nativeQuery = true)
	List<JobExpenses> findListByJobId(Long jobId);

	@Modifying
	@Transactional
	@Query(value="UPDATE expenses SET attachment = ?2 WHERE id =?1", nativeQuery = true)
	void updateAttachmentsById(Long expenseId, String attachmentsName);

}
