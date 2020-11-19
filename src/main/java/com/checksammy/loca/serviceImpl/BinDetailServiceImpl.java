package com.checksammy.loca.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinDetail;
import com.checksammy.loca.model.BinDetailsView;
import com.checksammy.loca.repository.BinDetailRespository;
import com.checksammy.loca.repository.BinDetailViewRepository;
import com.checksammy.loca.repository.BinLocationRespository;
import com.checksammy.loca.service.BinDetailService;
import com.checksammy.loca.utility.GlobalValues;
@Service
public class BinDetailServiceImpl implements BinDetailService
{

private static final Logger logger = LoggerFactory.getLogger(BinDetailServiceImpl.class);
	
	@Autowired
	BinDetailRespository bindetailRepository;
	
	@Autowired
	BinLocationRespository binLocationRepository;
	
	@Autowired
	BinDetailViewRepository binDetailViewRepository;

	@Override
	public BinDetail save(@Valid BinDetail bindetail) {
		logger.debug("inside BinDetailServiceImpl.save() Method : Save bindetail ");
		return bindetailRepository.save(bindetail);
	}

	@Override
	public BinDetail findById(Long id) throws ResourceNotFoundException {
		logger.debug("inside BinDetailServiceImpl.findBindetailById() Method : Save bindetail");
		return bindetailRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("bindetail not found for this id :: " + id));
	}

	@Override
	public List<BinDetail> showList() {
		logger.debug("inside BinDetailServiceImpl.showList() Method : Get bindetail List");
		return bindetailRepository.findAll();
	}

	@Override
	public void removeById(Long binLocationId, Instant creationTS) throws ResourceNotFoundException {
		logger.debug("inside  BinDetailServiceImpl.removeById() method");		
		bindetailRepository.deleteBinDetailByBinLocationId(binLocationId, creationTS);
		binLocationRepository.deleteBinLocationById(binLocationId, creationTS);		
		
	}

	@Override
	public List<BinDetail> saveList(List<BinDetail> binDetails) {
		return bindetailRepository.saveAll(binDetails);
	}

	@Override
	public List<BinDetailsView> getBinDetailView(Long binLocationId) throws ResourceNotFoundException {
		return binDetailViewRepository.getBinDetailsFromViewByBinLocationId(binLocationId);
	}

	@Override
	public List<Resource> getBinPhotos(Long binLocationId) throws IOException, MalformedURLException{
		List<Resource> resources = new ArrayList<Resource>();
		String directoryPath = GlobalValues.BIN_PHOTOS_DIR_PATH + File.separator + binLocationId;
		File dir = new File(directoryPath);
		if(dir.exists()) {
			Stream<Path> walk = Files.walk(Paths.get(directoryPath));
			List<String> fileNames = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList());
			fileNames.forEach(fileName->{
				Path file = Paths.get(dir.getAbsolutePath()).resolve(fileName);
				try {
					resources.add(new UrlResource(file.toUri()));
				} catch (MalformedURLException e) {
					logger.error(e.getMessage(), e.fillInStackTrace());
				}
				
			});     
			walk.close();
		}
		
		return resources;
	}


	@Override
	public List<BinDetail> getBinDetailsByLocationId(Long binLocationId) {
		return bindetailRepository.findByLocationId(binLocationId);
	}

	@Override
	public void deleteAllByBinLocationId(List<BinDetail> binDetails) {
		bindetailRepository.deleteAll(binDetails);
	}
}
