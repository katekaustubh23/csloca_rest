/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.Role;
import com.checksammy.loca.repository.RoleRepository;
import com.checksammy.loca.service.RoleService;
import com.checksammy.loca.service.UserRoleService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository repo;
	
	@Autowired
	UserRoleService userRoleService;

	@Override
	public void deleteRoleById(Long id) {
		userRoleService.deleteUserRoleByRoleId(id);
		repo.deleteRole(id);
	}

	@Override
	public Role addRole(Role role) {
		return repo.save(role);

	}

	@Override
	public Page<Role> getAllRoles(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<Role> getActiveRoles() {		
		return repo.getActiveRoles();
	}

	@Override
	public void changeStatus(Long userId, Boolean status) {
		repo.changeRoleStatus(status, userId);
		
	}

	@Override
	public List<Role> getAllRoles() {
		return repo.findAll();
	}

}
