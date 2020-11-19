package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.RelatedUser;

public interface RelatedUserService {

	List<RelatedUser> findByUserIds(List<Long> driverUserIds);

}
