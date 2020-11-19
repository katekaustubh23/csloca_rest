/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.MobPickupRequestView;
import com.checksammy.loca.model.PickupRequest;
import com.checksammy.loca.repository.MobPickupRequestViewRepository;
import com.checksammy.loca.repository.PickupRequestRepository;
import com.checksammy.loca.service.PickupRequestService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class PickupRequestServiceImpl implements PickupRequestService {
	
	@Autowired
	PickupRequestRepository repo;
	
	@Autowired
	MobPickupRequestViewRepository mobRepo;

	/**
	 * 
	 */
	public PickupRequestServiceImpl() {

	}

	@Override
	public PickupRequest savePickupRequest(PickupRequest pickupRequest) {
		if(pickupRequest.getId() != null && pickupRequest.getId() > 0) {
			pickupRequest.setUpdatedTs(Instant.now());
			pickupRequest.setUpdatedBy(pickupRequest.getCreatedBy());
		}else {
			pickupRequest.setCreatedTs(Instant.now());
		}
		return repo.save(pickupRequest);
	}

	@Override
	public List<MobPickupRequestView> getAllPickUpReqeustForPropManager(Long userId) {
//		ExampleMatcher isDeletedMatcher = ExampleMatcher.matching().withIgnorePaths("id").withMatcher("is_deleted",
//				GenericPropertyMatchers.ignoreCase());
//		MobPickupRequestView request = new MobPickupRequestView();
//		request.setIsDeleted(Boolean.FALSE);
//		Example<MobPickupRequestView> example = Example.of(request, isDeletedMatcher);
		return mobRepo.findAllByUserId(userId);
	}

	@Override
	public Page<MobPickupRequestView> getAllPickUpReqeustForAdmin(Pageable pageable) {
		return mobRepo.findAll(pageable);
	}

	@Override
	public Optional<MobPickupRequestView> getPickupRequestById(Long id) {
		return mobRepo.findById(id);
	}

	@Override
	public void deletePickUpRequestById(Long id) {
		repo.deleteByPickupReqestId(id);

	}

	@Override
	public List<MobPickupRequestView> getAllPickUpReqeustForAdmin() {
		return mobRepo.findAll();
	}

	@Override
	public Optional<PickupRequest> findById(Long pickupRequestId) {
		// TODO Auto-generated method stub
		return repo.findById(pickupRequestId);
	}

}
