/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.UserRoleDetailsView;
import com.checksammy.loca.repository.UserRoleDetailsRepository;
import com.checksammy.loca.service.UserRoleDetailsViewService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class UserRoleDetailsViewServiceImpl implements UserRoleDetailsViewService {

	@Autowired
	private UserRoleDetailsRepository repo;
	
	@Override
	public List<UserRoleDetailsView> findByUsername(String username) throws UsernameNotFoundException{
		
		return repo.findByUsername(username);
	}

	@Override
	public List<UserRoleDetailsView> getAll() {
		
		return repo.findAll(Sort.by("firstName", "lastName"));
	}

}
