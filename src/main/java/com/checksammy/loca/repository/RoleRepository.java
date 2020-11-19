package com.checksammy.loca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
	
	@Modifying
	@Transactional
	@Query(value="Update role set is_deleted=1 where id=?1", nativeQuery = true)
	void deleteRole(Long userId);
	
	@Query(value="Select * from role where is_deleted=0", nativeQuery = true)
	List<Role> getActiveRoles();
	
	@Modifying
	@Transactional
	@Query(value="Update role set is_deleted=?1 where id=?2", nativeQuery = true)
	void changeRoleStatus(Boolean status, Long userId);

}
