package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.UserCardMapping;
import com.stripe.model.Charge.PaymentMethodDetails.Card;

public interface UserCardMappingService {

	UserCardMapping createUserCard(Long userId, String id, Card dto);

	List<UserCardMapping> getUserCardList(Long userId);

}
