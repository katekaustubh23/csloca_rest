package com.checksammy.loca.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.checksammy.loca.dto.UserDtoWithCustomFieldDto;
import com.checksammy.loca.model.User;

public interface UserService 
{
	//void addUser(User user);
	
	Boolean spUpsertUser(User user, List<String> roleName);
	
	Boolean isExistingUser(String emailId);
	
	User findByUsername(String username) throws UsernameNotFoundException;
	
	void changeStatus (Long userId, Boolean status);
	
	void setOTPByUserName(String otp, String username);
	
	User updateUser(User user);
	
	Long getTotalNoOfUsers();
	
	void changePassword(String userName, String password);

	void updateUserDriverInfoId(Long userId, Long id, Boolean userStatus, Boolean isActive, String fcmToken);

	User getUserId(Long createdBy);

	List<User> findUserByRole(Long roleId);

	UserDtoWithCustomFieldDto findByUsernameNew(String userName);

	void changeActiveInactive(Long userId, Boolean status);

	void updateFcmToken(Long userId, String fcmToken);

	User updateUserFromDeviceProfile(User user);

	void updateUserUUIDSmsKey(Long userId, String uuidSMSKey);

	Boolean checkAvailablity(Long driverId);

	void changeUserActiveInactive(Long userId, Boolean status);

	void updateCardCustomerId(Long userId, String id);

	List<User> findByUserIds(List<Integer> teamIdList);

	User findById(Long createdBy);

}
