/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.repository.UserRoleRepository;
import com.checksammy.loca.service.UserRoleService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
	UserRoleRepository userRoleRepo;

	@Override
	public void deleteUserRole(Long userId) throws ResourceNotFoundException{
		userRoleRepo.deleteByUserId(userId);
	}

	@Override
	public void deleteUserRoleByRoleId(Long roleId) {
		userRoleRepo.deleteByRoleId(roleId);
		
	}

}
