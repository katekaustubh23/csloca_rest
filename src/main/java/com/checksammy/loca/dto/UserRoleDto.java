/**
 * 
 */
package com.checksammy.loca.dto;

import java.util.List;

import com.checksammy.loca.model.UserFieldInstance;
import com.checksammy.loca.model.DriverInfo;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.UserNote;

/**
 * @author Abhishek Srivastava
 *
 */
public class UserRoleDto {
	
	public User user;
	
	public List<String> roles;
	
	public DriverInfo driverInfo;
	
	public List<UserFieldInstance> customFieldInstance;
	
	public List<UserNote> userNotes;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public DriverInfo getDriverInfo() {
		return driverInfo;
	}

	public void setDriverInfo(DriverInfo driverInfo) {
		this.driverInfo = driverInfo;
	}

	public List<UserFieldInstance> getCustomFieldInstance() {
		return customFieldInstance;
	}

	public void setCustomFieldInstance(List<UserFieldInstance> customFieldInstance) {
		this.customFieldInstance = customFieldInstance;
	}

	public List<UserNote> getUserNotes() {
		return userNotes;
	}

	public void setUserNotes(List<UserNote> userNotes) {
		this.userNotes = userNotes;
	}
	
}
