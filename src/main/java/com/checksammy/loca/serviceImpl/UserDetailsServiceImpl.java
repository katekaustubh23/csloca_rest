package com.checksammy.loca.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.checksammy.loca.model.UserRoleDetailsView;
import com.checksammy.loca.repository.UserRoleDetailsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRoleDetailsRepository repo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<UserRoleDetailsView> users = repo.findByUsername(username);
		
		if(users==null || users.size()<=0)
			throw new UsernameNotFoundException("Either user is not found in system or not authorized: " + username);
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRoleDetailsView user : users){
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRoleName()));
        }
		return new org.springframework.security.core.userdetails.User(users.get(0).getUsername(), users.get(0).getPassword(), grantedAuthorities);
	}
}
