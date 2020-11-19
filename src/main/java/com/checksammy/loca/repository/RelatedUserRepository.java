package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.RelatedUser;

@Repository
public interface RelatedUserRepository extends JpaRepository<RelatedUser, Serializable>{

	@Query(value="SELECT * FROM user WHERE id IN (?1) AND is_deleted = 0", nativeQuery = true)
	List<RelatedUser> findById(List<Long> driverUserIds);

}
