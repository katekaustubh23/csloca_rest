package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.dto.BinLocationCustomInstanceDto;

public interface BinLocationCustomInstanceService {

	List<BinLocationCustomInstanceDto> getBinCustomField(Long binLocationId);

	void deleteFromBinCustom(Long fieldInstanceId);

}
