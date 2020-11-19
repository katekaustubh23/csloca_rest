package com.checksammy.loca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.checksammy.loca.dto.LocationDto;
import com.checksammy.loca.dto.LocationPropertyManagmentDto;
import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.LocationPropertyDetailView;

public interface LocationService {

	public List<Location> getAllLocation();
	
	public Optional<Location> getLocationById(Long locationId) throws ResourceNotFoundException;
	
	public Location add(LocationPropertyManagmentDto locPropMgmtDto) throws DuplicateKeyException;
	
	public void deleteById(Long id);
	
	public List<LocationPropertyDetailView> getAllLocationPropertyDetails();
	
	public Page<LocationPropertyDetailView> getPaginatedAllLocationPropertyDetails(Pageable pageable);

	public Optional<LocationPropertyDetailView> getLocationPropertyDetailsById(Long locationId);

	public void deleteLocationPropertyMapping(Long locationId);	
	
	public Long getTotalNoOfLocations();

	public List<Location> getLocationByPropertyCompanyId(Long companyId);

	public List<Location> addCompleteList(List<Location> newSaveLocationList, Long companyId);

	public List<Location> locationListByUserId(Long userId);

	public LocationDto getLocationByIdNew(Long locationId);
	
}
