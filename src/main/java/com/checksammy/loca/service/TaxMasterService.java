package com.checksammy.loca.service;

import java.util.List;
import java.util.Optional;

import com.checksammy.loca.model.TaxMaster;

public interface TaxMasterService {

	List<TaxMaster> getAll();

	List<TaxMaster> saveData(List<TaxMaster> taxMasters2);

	Optional<TaxMaster> getSingleTax(Long taxId);

	void deleteTax(Long taxId);

}
