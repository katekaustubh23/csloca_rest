package com.checksammy.loca.service;

import com.checksammy.loca.exception.ResourceNotFoundException;

public interface UserRoleService {
	
	void deleteUserRole(Long userId)throws ResourceNotFoundException;

	void deleteUserRoleByRoleId(Long roleId);
}
