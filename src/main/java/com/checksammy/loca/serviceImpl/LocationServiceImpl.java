/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.LocationCustomFieldDto;
import com.checksammy.loca.dto.LocationDto;
import com.checksammy.loca.dto.LocationPropertyManagmentDto;
import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.LocationCustomField;
import com.checksammy.loca.model.LocationPropertyDetailView;
import com.checksammy.loca.model.LocationPropertyDetails;
import com.checksammy.loca.repository.FieldTypeInstanceRepository;
import com.checksammy.loca.repository.LocationCustomFieldRepository;
import com.checksammy.loca.repository.LocationManagerRepository;
import com.checksammy.loca.repository.LocationPropertyDetailRepository;
import com.checksammy.loca.repository.LocationPropertyViewRepository;
import com.checksammy.loca.repository.LocationRepository;
import com.checksammy.loca.service.LocationService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	LocationRepository repo;

	@Autowired
	LocationPropertyViewRepository locPropViewRepo;

	@Autowired
	LocationPropertyDetailRepository locPropRepo;

	@Autowired
	private LocationManagerRepository locationManagerRepository;

	@Autowired
	private LocationCustomFieldRepository locaCustomFieldRepository;
	
	@Autowired
	private FieldTypeInstanceRepository fieldTypeRepository;

	private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

	@Override
	public List<Location> getAllLocation() {

		return repo.findAll(Sort.by("propertyName"));
	}

	@Override
	public Optional<Location> getLocationById(Long id) throws ResourceNotFoundException {

		return repo.findById(id);
	}

	@Override
	public Location add(LocationPropertyManagmentDto locPropMgmtDto) throws DuplicateKeyException {
		Location location = new Location();
		locPropMgmtDto.getLocation()
				.setLocationManager(locationManagerRepository.save(locPropMgmtDto.getLocation().getLocationManager()));
		try {
			location = repo.save(locPropMgmtDto.getLocation());
			List<Long> locationCustomFieldIds = new ArrayList<Long>();
			if (location.getLocationCustomField() != null && location.getLocationCustomField().size() > 0) {
				for (LocationCustomField locationCustomField : location.getLocationCustomField()) {
					locationCustomField.setLocationId(location.getId());
					locationCustomFieldIds.add(locationCustomField.getId());
				}
				locaCustomFieldRepository.updatedLocationCustomLocId(location.getId(), locationCustomFieldIds);
			}

			if (locPropMgmtDto.getPropDetails() != null && locPropMgmtDto.getPropDetails().getId() != null) {
				LocationPropertyDetails locPropDetails = new LocationPropertyDetails();
				locPropDetails.setLocationId(location.getId());
				locPropDetails.setPropDetailId(locPropMgmtDto.getPropDetails().getId());
				locPropRepo.save(locPropDetails);
			}
			locationManagerRepository.updateLocationId(locPropMgmtDto.getLocation().getLocationManager().getId(),
					location.getId());
		} catch (Exception e) {
			locationManagerRepository.delete(locPropMgmtDto.getLocation().getLocationManager());
		}

		return location;
	}

	@Override
	public void deleteById(Long id) {
		locPropRepo.deleteByLocationId(id);
		repo.deleteLocationById(id);

	}

	@Override
	public List<LocationPropertyDetailView> getAllLocationPropertyDetails() {
		return locPropViewRepo.findBySortData();
	}

	@Override
	public Optional<LocationPropertyDetailView> getLocationPropertyDetailsById(Long locationId) {
		return locPropViewRepo.findById(locationId);
	}

	@Override
	public void deleteLocationPropertyMapping(Long locationId) {
		locPropRepo.deleteByLocationId(locationId);
	}

	@Override
	public Page<LocationPropertyDetailView> getPaginatedAllLocationPropertyDetails(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("propertyName"));
		return locPropViewRepo.findAll(pageable);
	}

	@Override
	public Long getTotalNoOfLocations() {
		return repo.count();
	}

	@Override
	public List<Location> getLocationByPropertyCompanyId(Long companyId) {
		return repo.finByCompanyId(companyId);
	}

	@Override
	public List<Location> addCompleteList(List<Location> newSaveLocationList, Long companyId) {
		try {
			newSaveLocationList = repo.saveAll(newSaveLocationList);
			for (Location location : newSaveLocationList) {
				LocationPropertyDetails locaProp = new LocationPropertyDetails();
				try {
					locaProp = locPropRepo.getByLocationAndPropId(location.getId(), companyId);
					if (locaProp != null) {
						continue;
					} else {
						locaProp = new LocationPropertyDetails();
						locaProp.setLocationId(location.getId());
						locaProp.setPropDetailId(companyId);
					}
				} catch (Exception e) {
					logger.debug(e.getLocalizedMessage());
				}
				locPropRepo.save(locaProp);
			}
		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}
		return newSaveLocationList;
	}

	@Override
	public List<Location> locationListByUserId(Long userId) {
		return repo.findByUserId(userId);
	}

	@Override
	public LocationDto getLocationByIdNew(Long locationId) {
		LocationDto locationDto = new LocationDto();
		Optional<Location> location = repo.findById(locationId);
		List<LocationCustomFieldDto> locationCustomFieldDtos = new ArrayList<LocationCustomFieldDto>();
		if(location.isPresent()) {
			
			for (LocationCustomField locationCustomField : location.get().getLocationCustomField()) {
				LocationCustomFieldDto locationCustomFieldDto = new LocationCustomFieldDto();
				Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
						.findById(locationCustomField.getFieldInstanceId());
				locationCustomFieldDto = locationCustomFieldDto.newSetLocationCustomField(fieldTypeInstance.get(), locationCustomField);
				locationCustomFieldDtos.add(locationCustomFieldDto);
			}	
		}		
		locationDto = locationDto.addLocationData(location.get(), locationCustomFieldDtos);
		return locationDto;
	}

}
