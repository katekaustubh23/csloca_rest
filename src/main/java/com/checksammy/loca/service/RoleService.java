package com.checksammy.loca.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.checksammy.loca.model.Role;

public interface RoleService {
	
	void deleteRoleById(Long id);
	
	Role addRole(Role role);
	
	Page<Role> getAllRoles(Pageable pageable);
	
	List<Role> getActiveRoles();
	
	void changeStatus (Long userId, Boolean status);

	List<Role> getAllRoles();

}
