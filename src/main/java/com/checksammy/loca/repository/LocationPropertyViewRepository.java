package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.checksammy.loca.model.LocationPropertyDetailView;

public interface LocationPropertyViewRepository extends JpaRepository<LocationPropertyDetailView, Serializable>{

	@Query(value="SELECT * FROM vlocationpropertydetails WHERE is_deleted = 0 ORDER BY created_ts DESC", nativeQuery = true)
	List<LocationPropertyDetailView> findBySortData();

}
