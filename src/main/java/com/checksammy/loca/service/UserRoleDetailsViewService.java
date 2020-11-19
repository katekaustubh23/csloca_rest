package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.UserRoleDetailsView;

public interface UserRoleDetailsViewService {
	
	List<UserRoleDetailsView> findByUsername(String username);
	
	List<UserRoleDetailsView> getAll();

}
