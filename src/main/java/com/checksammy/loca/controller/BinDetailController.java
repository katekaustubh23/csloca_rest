package com.checksammy.loca.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.dto.BinDetailDto;
import com.checksammy.loca.dto.BinLocationCustomInstanceDto;
import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinDetail;
import com.checksammy.loca.model.BinDetailsView;
import com.checksammy.loca.model.BinHistoryCardView;
import com.checksammy.loca.model.BinHistoryViewWeb;
import com.checksammy.loca.model.BinLocation;
import com.checksammy.loca.model.BinLocationNotes;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.User;
import com.checksammy.loca.service.BinDetailService;
import com.checksammy.loca.service.BinHistoryCardViewService;
import com.checksammy.loca.service.BinHistoryViewWebService;
import com.checksammy.loca.service.BinLocationCustomInstanceService;
import com.checksammy.loca.service.BinLocationNotesService;
import com.checksammy.loca.service.BinLocationService;
import com.checksammy.loca.service.LocationService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

/**
 * 
 * @author kartik.thakre
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/bindetail")
public class BinDetailController {

	@Autowired
	BinDetailService bindetailservice;

	@Autowired
	BinLocationService binLocationService;
	
	@Autowired
	UserService userService;

	@Autowired
	LocationService locationService;

	@Autowired
	BinHistoryCardViewService binHistoryCardViewService;

	@Autowired
	BinHistoryViewWebService binHistoryViewWebService;
	@Autowired
	BinLocationNotesService binLocationNotesService;
	
	@Autowired
	BinLocationCustomInstanceService binCustomInstanceService;

	private static final Logger logger = LoggerFactory.getLogger(BinDetailController.class);

	@PostMapping("/save")
	public ResponseEntity<ServiceResponse> saveBinDetailWithLocation(@Valid @RequestBody BinDetailDto bindetailDto) {
		logger.debug("inside BinDetailController.saveBindetailData() method");
		ServiceResponse response = new ServiceResponse();
		try {
			BinLocation binLocation = bindetailDto.getBinLocationDetails(bindetailDto);
			binLocation = binLocationService.save(binLocation);
			bindetailDto.setId(binLocation.getId());
			List<BinLocationNotes> binLocationNotes = new ArrayList<BinLocationNotes>();
			if(binLocation.getBinLocationNotes() != null && binLocation.getBinLocationNotes().size()>0) {
				binLocationNotes = new ArrayList<BinLocationNotes>(binLocation.getBinLocationNotes());
				for (BinLocationNotes binLocationNotes2 : binLocationNotes) {
					User userData = userService.getUserId(binLocation.getCreatedBy());
					binLocationNotes2.setBinLocationId(binLocation.getId());
					binLocationNotes2.setCreatedUserDetails(userData.getFirstName()+" "+ userData.getLastName());
					binLocationNotes2.setCreatedBy(binLocation.getCreatedBy());
					binLocationNotes2.setCreatedTs(Instant.now());
				}
				binLocationNotes = binLocationNotesService.saveList(binLocationNotes);
				bindetailDto.setBinLocationNotes(new HashSet<BinLocationNotes>(binLocationNotes));
			}
			
			List<BinDetail> binDetails = new ArrayList<BinDetail>();
			for (BinDetail binDetail : bindetailDto.getBinDetails()) {
				binDetail.setBinLocationId(binLocation.getId());
				binDetail.setIsDeleted(binLocation.getIsDeleted());
				binDetail.setCreatedBy(binLocation.getCreatedBy());
				binDetail.setCreatedTs(binLocation.getCreatedTs());
				binDetail.setUpdatedBy(binLocation.getUpdatedBy());
				binDetail.setUpdatedTs(binLocation.getUpdatedTs());
				binDetails.add(binDetail);
			}
			binDetails = bindetailservice.saveList(binDetails);
			bindetailDto.setBinDetails(binDetails);
			response.setReturnObject(bindetailDto);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);

	}

	@GetMapping("/list")
	public ResponseEntity<ServiceResponse> showList() {
		logger.debug("inside BinDetailController.showPage() method");
		ServiceResponse response = new ServiceResponse();
		try {
			List<BinDetail> pageInfo = bindetailservice.showList();
			response.setReturnObject(pageInfo);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/extend/location/{locationId}")
	public ResponseEntity<ServiceResponse> getLocationDetails(@PathVariable("locationId") Long locationId)
			throws ResourceNotFoundException {
		logger.debug("inside BinDetailController.getLocationDetails() method");
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<Location> location = locationService.getLocationById(locationId);
			response.setReturnObject(location);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/extend/binphotos/{binLocationId}")
	public ResponseEntity<ServiceResponse> getBinPhotosDetails(@PathVariable("binLocationId") Long binLocationId)
			throws ResourceNotFoundException {
		logger.debug("inside BinDetailController.getLocationDetails() method");
		ServiceResponse response = new ServiceResponse();
		try {
			String binPhotos = binLocationService.getBinPhotosByBinLocationId(binLocationId);
			response.setReturnObject(binPhotos);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/extend/bindetail/{binLocationId}")
	public ResponseEntity<ServiceResponse> getBinDetail(@PathVariable("binLocationId") Long binLocationId)
			throws ResourceNotFoundException {
		logger.debug("inside BinDetailController.getLocationDetails() method");
		ServiceResponse response = new ServiceResponse();
		try {
			List<BinDetailsView> binDetails = bindetailservice.getBinDetailView(binLocationId);
			response.setReturnObject(binDetails);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{bindetailid}")
	public ResponseEntity<ServiceResponse> getBindetailById(@PathVariable("bindetailid") Long id)
			throws ResourceNotFoundException {
		logger.debug("inside BindetailController.getBindetailById() method");
		ServiceResponse response = new ServiceResponse();
		try {
			BinDetail binDetail = bindetailservice.findById(id);
			response.setReturnObject(binDetail);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	// make is_deleted flag true for bin_detail and bin_location table for selected
	// binLocationId and createdTS
	@DeleteMapping("/delete/{binLocationId}/{lastModifiedTS}")
	public ResponseEntity<ServiceResponse> removeBin(@PathVariable("binLocationId") Long binLocationId,
			@PathVariable("lastModifiedTS") String lastModifiedTS) throws ResourceNotFoundException {
		logger.debug("inside BindetailController.delete() method");
		ServiceResponse response = new ServiceResponse();

		try {
			Instant obj = Instant.parse(lastModifiedTS);
			bindetailservice.removeById(binLocationId, obj);
			response.setReturnObject("Bin deleted successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/history/{userId}")
	public ResponseEntity<ServiceResponse> getBinHistoryCard(Pageable pageable, @PathVariable("userId") Long userId) {
		ServiceResponse response = new ServiceResponse();
		try {
			// Page<BinHistoryCardView> binHistoryCards =
			// binHistoryCardViewService.getBinHistoryCardByUser(pageable, userId);
			Page<BinHistoryCardView> binHistoryCards = binHistoryCardViewService.getBinHistoryCards(pageable, userId);
			response.setReturnObject(binHistoryCards);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* New Complete history List **/
	@GetMapping("/history/list")
	public ResponseEntity<ServiceResponse> getBinHistoryCardList(Pageable pageable) {
		ServiceResponse response = new ServiceResponse();
		try {
			// Page<BinHistoryCardView> binHistoryCards =
			// binHistoryCardViewService.getBinHistoryCardByUser(pageable, userId);
			List<BinHistoryViewWeb> binHistoryCards = binHistoryViewWebService.getWebBinHistoryList();
			response.setReturnObject(binHistoryCards);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/binphotos/{binLocationId}")
	public ResponseEntity<ServiceResponse> getBinPhotosByBinLocationId(
			@PathVariable("binLocationId") Long binLocationId) {
		ServiceResponse response = new ServiceResponse();
		try {
			String binPhotos = binLocationService.getBinPhotosByBinLocationId(binLocationId);
			response.setReturnObject(binPhotos);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/web/count")
	public ResponseEntity<ServiceResponse> getBinCount() {
		ServiceResponse response = new ServiceResponse();
		try {
			Long totalCount = binHistoryViewWebService.getTotalNoOfBins();
			response.setReturnObject(totalCount);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/delete/binContent/{binLocationId}")
	public ResponseEntity<ServiceResponse> deleteBinContent(@PathVariable("binLocationId") Long binLocationId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<BinDetail> binDetails = bindetailservice.getBinDetailsByLocationId(binLocationId);
			if (binDetails.size() > 0) {
				bindetailservice.deleteAllByBinLocationId(binDetails);
				response.getReturnObject();
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

//	TODO: create API for update rating on bin location 
//	@PutMapping("/update/rating/{binLocationId}/{binTypeId}/{rating}")
//	public ResponseEntity<ServiceResponse> updateBinTypeRating(@PathVariable("binLocationId") Long binLocationId,
//			@PathVariable("binTypeId") Long binTypeId, @PathVariable("rating") Long rating) {
//		ServiceResponse response = new ServiceResponse();
//		try {
//			Boolean status = binLocationService.updateRating(binLocationId, rating);
//			if (status) {
//				
//				binLocationService.updateAvgBinTypeRating(binTypeId);
//				
//				response.setReturnObject(status);
//				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
//			} else {
//				response.setReturnObject(status);
//				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e.fillInStackTrace());
//			response.setErrorMessage(e.getMessage());
//			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
//		}
//		return ResponseEntity.ok().body(response);
//	}
	
	/* SPRINT 7 - delete location notes */
	@DeleteMapping("/delete/notes/{noteId}")
	public ResponseEntity<ServiceResponse> deleteLocationNotes(@PathVariable("noteId") Long noteId) {
		ServiceResponse response = new ServiceResponse();
		String status;
		try {
			status = binLocationNotesService.deleteBinLocationNotes(noteId);
			response.setReturnObject(status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
//	TODO: method for 
	@GetMapping("/bin/custom/field/{binLocationId}")
	public ResponseEntity<ServiceResponse> getBinLocationCustomField(@PathVariable("binLocationId") Long binLocationId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<BinLocationCustomInstanceDto> binCustoms = binCustomInstanceService.getBinCustomField(binLocationId);
			response.setReturnObject(binCustoms);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
}
