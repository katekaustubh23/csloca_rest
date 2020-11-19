package com.checksammy.loca.repository;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.CsServicesRequest;

@Repository
public interface CsServicesRequestRepository extends JpaRepository<CsServicesRequest, Serializable>{

	@Modifying
	@Transactional
	@Query(value="DELETE FROM cs_service_request WHERE request_id =?1", nativeQuery = true)
	void deleteByRequestId(Long id);

}
