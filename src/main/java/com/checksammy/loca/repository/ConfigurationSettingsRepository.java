/**
 * 
 */
package com.checksammy.loca.repository;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.ConfigurationSettings;

/**
 * @author Abhishek Srivastava
 *
 */
@Repository
public interface ConfigurationSettingsRepository extends JpaRepository<ConfigurationSettings, Serializable>{

	@Query(value="select value from configuration_settings where key_name=?1 and is_deleted=0", nativeQuery = true)
	String getValueByKey(String keyName);
	
	@Modifying
	@Transactional
	@Query(value="update configuration_settings set value=?2 where key_name=?1", nativeQuery = true)
	int updateValueByKey(String keyName, String value);

}
