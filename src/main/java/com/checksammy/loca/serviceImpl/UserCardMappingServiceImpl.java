package com.checksammy.loca.serviceImpl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.UserCardMapping;
import com.checksammy.loca.repository.UserCardMappingRepository;
import com.checksammy.loca.service.UserCardMappingService;
import com.stripe.model.Charge.PaymentMethodDetails.Card;

@Service
public class UserCardMappingServiceImpl implements UserCardMappingService{

	@Autowired
	private UserCardMappingRepository userCardMappingRepository;

	@Override
	public UserCardMapping createUserCard(Long userId, String id,Card dto) {
		UserCardMapping userCardMapping = new UserCardMapping();
		userCardMapping.setUserId(userId);
		userCardMapping.setUserCardCustomerId(id);
		userCardMapping.setCreatedTs(Instant.now());
		userCardMapping.setCardLast4(dto.getLast4());
		userCardMapping.setExpMonth(dto.getExpMonth().toString());
		userCardMapping.setExpYear(dto.getExpYear().toString());
		userCardMapping = userCardMappingRepository.save(userCardMapping);
		return userCardMapping;
	}

	@Override
	public List<UserCardMapping> getUserCardList(Long userId) {
		List<UserCardMapping> userCardMappings = userCardMappingRepository.findByUserId(userId);
		return userCardMappings;
	}
}
