package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.TaxGroupMaster;

public interface TaxGroupMasterService {

	List<TaxGroupMaster> getAllTaxGroup();

	TaxGroupMaster saveData(TaxGroupMaster taxMaster);

}
