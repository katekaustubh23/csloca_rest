package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.MobPickupRequestView;

@Repository
public interface MobPickupRequestViewRepository extends JpaRepository<MobPickupRequestView, Serializable>{

	@Query(value="SELECT * FROM vmobpickuprequest WHERE created_by =?1 AND is_deleted = 0", nativeQuery = true)
	List<MobPickupRequestView> findAllByUserId(Long userId);

}
