package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.VisitJobProductAndServiceMap;

@Repository
public interface VisitJobProductAndServiceMapRepository extends JpaRepository<VisitJobProductAndServiceMap, Serializable>{

	@Modifying
	@Transactional
	@Query(value="DELETE FROM visit_job_product_map WHERE job_id = ?1 AND visit_id =?2 AND checked = 0", nativeQuery = true)
	void deleteByJobIdAndVisitId(Long jobId, Long visitId);

	List<VisitJobProductAndServiceMap> findByVisitId(Long visitId);

	@Modifying
	@Transactional
	@Query(value="UPDATE visit_job_product_map SET checked =?2 WHERE visit_id = ?1", nativeQuery = true)
	void updateStatus(Long visitId, Boolean checked);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM visit_job_product_map WHERE job_id = ?1 AND checked = 0", nativeQuery = true)
	void deleteByJobIdWhereUnChecked(Long id);

}
