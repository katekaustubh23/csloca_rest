package com.checksammy.loca.service;

import com.checksammy.loca.model.TaxGroupMapping;

public interface TaxGroupMappingService {

	void deleteMappingByTaxGrpId(Long id);

	TaxGroupMapping saveMapping(TaxGroupMapping taxGroupMapping);

}
