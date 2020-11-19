/**
 * 
 */
package com.checksammy.loca.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.PropertyDetailsDto;
import com.checksammy.loca.model.PropertyDetails;

/**
 * @author Abhishek Srivastava
 *
 */
public interface PropertyDetailsService {
	
	Page<PropertyDetails> getAll(Pageable pageable);

	PropertyDetails save(PropertyDetails propertyDetails);

	void deleteById(Long id);

	List<PropertyDetails> getAllList();

	PropertyDetailsDto getSingleCompanyDetails(Long companyId);

	List<PropertyDetailsDto> getAllList2();

	String importXlXSheetList(Long userId, MultipartFile file) throws IOException, ParseException ;

}
