package com.checksammy.loca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.model.ProductService;

public interface ProductServiceService {

	List<ProductService> getAll();

	ProductService saveData(ProductService productService);

	Optional<ProductService> getProService(Long proServiceId);

	String saveRequestAttachments(Long requestID, MultipartFile[] files);

	Boolean deleteProductService(Long requestID);

	Long findNamePresentOrNot(String name);

	List<ProductService> saveAllData(List<ProductService> newProductServices);

}
