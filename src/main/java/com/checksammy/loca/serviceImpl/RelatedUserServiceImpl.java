package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.RelatedUser;
import com.checksammy.loca.repository.RelatedUserRepository;
import com.checksammy.loca.service.RelatedUserService;

@Service
public class RelatedUserServiceImpl implements RelatedUserService{

	@Autowired
	private RelatedUserRepository relatedUserRepository;

	@Override
	public List<RelatedUser> findByUserIds(List<Long> driverUserIds) {
//		System.out.println("hello");
		return relatedUserRepository.findById(driverUserIds);
	}
}
