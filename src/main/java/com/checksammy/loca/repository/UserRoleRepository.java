package com.checksammy.loca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.UserRole;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>
{

	@Modifying
	@Transactional
	@Query(value="delete FROM user_role WHERE user_id =?1", nativeQuery = true)
	void deleteByUserId(Long userId);
	
	@Modifying
	@Transactional
	@Query(value="delete FROM user_role WHERE role_id =?1", nativeQuery = true)
	void deleteByRoleId(Long roleId);
	
}
