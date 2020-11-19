/**
 * 
 */
package com.checksammy.loca.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.itextpdf.text.DocumentException;

/**
 * @author Abhishek Srivastava
 *
 */
public interface StorageService {
	
	public void uploadBinPhotos(MultipartFile[] files, Long binLocationId) throws IOException;
	
	public void removeBinPhotos(Long binLocationId, String fileName) throws IOException;

	public Resource getPDFByBinLocationId(Long locationId, Long binLocationId, HttpServletRequest request, Long timeZone) throws DocumentException, Exception;
	
	public void uploadPickupPhotos(MultipartFile[] files, Long pickupRequestId, String email) throws IOException, ResourceNotFoundException, MessagingException;

	public void uploadLocationImage(MultipartFile[] files, Long locationId);

	public void removeLocationPictures(Long binLocationId, String fileNames);

	public Resource downloadProductServiceCSV(HttpServletRequest request);

	public void uploadUserChatImage(MultipartFile[] file, String userChatId, String messageId);

	public void uploadProfilePic(MultipartFile files, Long userId);

}
