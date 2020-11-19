package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobProductService;

@Repository
public interface JobProductServiceRepository extends JpaRepository<JobProductService, Serializable>{

	@Modifying
	@Transactional
	@Query(value="UPDATE job_product_service SET is_deleted =1 WHERE id =?1", nativeQuery = true)
	void deleteToUpdate(Long productId);

	@Query(value="select * from job_product_service where job_id=?1 and product_service_id =?2", nativeQuery = true)
	JobProductService getLineItemData(long currJobId, long lineItem);

	@Query(value="SELECT * FROM job_product_service WHERE id IN (SELECT job_product_id FROM visit_job_product_map WHERE visit_id = ?1)", nativeQuery = true)
	List<JobProductService> findDataByVistiIds(Long visitId);

	@Query(value="SELECT * FROM job_product_service WHERE job_id IN (SELECT job_id FROM visit_scheduler_dates WHERE id = ?1)", nativeQuery = true)
	List<JobProductService> findByVisitByJob(Long visitId);

	@Query(value="SELECT * FROM job_product_service WHERE job_id IN (SELECT job_id FROM visit_scheduler_dates WHERE job_id = ?1 AND checked = 1 AND paid_on_flag = 0)", nativeQuery = true)
	List<JobProductService> findByJobIdCompleted(Long jobId);

}
