/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.ConfigurationSettings;
import com.checksammy.loca.repository.ConfigurationSettingsRepository;
import com.checksammy.loca.service.ConfigurationSettingsService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class ConfigurationSettingsImpl implements ConfigurationSettingsService {

	@Autowired
	ConfigurationSettingsRepository repo;
	
	@Override
	public String getValueByKey(String keyName) {
		return repo.getValueByKey(keyName);
	}

	@Override
	public List<ConfigurationSettings> getAllSettings() {
		return repo.findAll();
	}

	@Override
	public Integer updateValue(String keyName, String value) {
		return repo.updateValueByKey(keyName, value);
	}

}
