package com.checksammy.loca.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.BinLocationCustomInstanceDto;
import com.checksammy.loca.model.BinLocationCustomInstance;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.repository.BinLocationCustomInstanceRepository;
import com.checksammy.loca.repository.FieldTypeInstanceRepository;
import com.checksammy.loca.service.BinLocationCustomInstanceService;

@Service
public class BinLocationCustomInstanceServiceImpl implements BinLocationCustomInstanceService{

	@Autowired
	BinLocationCustomInstanceRepository binCustomInstanceRepository;
	
	@Autowired
	private FieldTypeInstanceRepository fieldTypeRepository;

	@Override
	public List<BinLocationCustomInstanceDto> getBinCustomField(Long binLocationId) {
		List<BinLocationCustomInstance> binLocationCustomInstances = binCustomInstanceRepository.findByBinLocationId(binLocationId);
		List<BinLocationCustomInstanceDto> binLocationCustomInstanceDtos = new ArrayList<BinLocationCustomInstanceDto>();
		for (BinLocationCustomInstance binLocationCustomInstance : binLocationCustomInstances) {
			BinLocationCustomInstanceDto binLocationCustomInstanceDto = new BinLocationCustomInstanceDto();
			Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
					.findById(binLocationCustomInstance.getFieldInstanceId());
			binLocationCustomInstanceDto = binLocationCustomInstanceDto.getBinCustomFieldWithArray(fieldTypeInstance.get(), binLocationCustomInstance);
			binLocationCustomInstanceDtos.add(binLocationCustomInstanceDto);
		}
		return binLocationCustomInstanceDtos;
	}

	@Override
	public void deleteFromBinCustom(Long fieldInstanceId) {
		binCustomInstanceRepository.updateDelete(fieldInstanceId);
	}
}
