/**
 * 
 */
package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.ConfigurationSettings;

/**
 * @author Abhishek Srivastava
 *
 */
public interface ConfigurationSettingsService {
	
	String getValueByKey(String keyName) throws ResourceNotFoundException;
	
	List<ConfigurationSettings> getAllSettings();
	
	Integer updateValue(String keyName, String value);

}
