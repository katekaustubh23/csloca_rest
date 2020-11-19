package com.checksammy.loca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.checksammy.loca.model.MobPickupRequestView;
import com.checksammy.loca.model.PickupRequest;

public interface PickupRequestService {
	
	public PickupRequest savePickupRequest(PickupRequest pickupRequest);
	
	public List<MobPickupRequestView>getAllPickUpReqeustForPropManager(Long userId);
	
	public Page<MobPickupRequestView>getAllPickUpReqeustForAdmin(Pageable pageable);
	
	public List<MobPickupRequestView>getAllPickUpReqeustForAdmin();
	
	public Optional<MobPickupRequestView> getPickupRequestById(Long id);
	
	public void deletePickUpRequestById(Long id);

	public Optional<PickupRequest> findById(Long pickupRequestId);

}
