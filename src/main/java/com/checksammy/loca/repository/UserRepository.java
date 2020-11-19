package com.checksammy.loca.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByUsername(String username);
	
	@Modifying
	@Transactional
	@Query(value="Update user set otp=?1 where email_id=?2", nativeQuery = true)
	void setOTPByUserName(String otp, String username);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE user SET is_deleted=?1 WHERE id=?2", nativeQuery = true)
	void changeUserStatus(Boolean status, Long userId);
	
	@Modifying
	@Transactional
	@Query(value="Update user set password=?2 where email_id=?1", nativeQuery = true)
	void changePassword(String userName, String password);

	@Modifying
	@Transactional
	@Query(value="UPDATE user SET driver_info_id =?2, user_status =?3, is_active =?4, fcm_token =?5 WHERE id =?1", nativeQuery = true)
	void updateDriverInfoId(Long userId, Long driverInfoId, Boolean userStatus, Boolean isActive, String fcmToken);

	@Query(value=" SELECT * FROM user WHERE id =?1 AND is_deleted = 0", nativeQuery = true)
	User findByUserId(Long createdBy);

	@Query(value="SELECT * FROM user WHERE id IN (SELECT user_id FROM user_role WHERE role_id = ?1) AND is_deleted = 0", nativeQuery = true)
	List<User> findUserByRole(Long roleId);

	@Modifying
	@Transactional
	@Query(value="UPDATE user SET is_active =?2 WHERE id =?1", nativeQuery = true)
	void changeActiveInactive(Long userId, Boolean status);

	@Query(value=" SELECT * FROM user WHERE id IN (?1) AND is_deleted = 0", nativeQuery = true)
	List<User> findUserByAssignIds(long[] numbers);

	@Modifying
	@Transactional
	@Query(value="UPDATE user SET fcm_token =?2 WHERE id =?1", nativeQuery = true)
	void updateFcmToken(Long userId, String fcmToken);

	@Query(value=" SELECT * FROM user WHERE first_name =?1 AND email_id =?2 AND is_deleted = 0", nativeQuery = true)
	Optional<User> findUserData(String first_name, Object address);

	@Modifying
	@Transactional
	@Query(value="UPDATE user SET first_name =?2, last_name =?3,user_status =?4, profile_pic =?5 WHERE id =?1", nativeQuery = true)
	void updateUserFieldFromDevice(Long userId, String firstName, String lastName, Boolean boolean1, String profilePic);

	@Modifying
	@Transactional
	@Query(value="UPDATE user SET uuid_key_sms =?2 WHERE id =?1", nativeQuery = true)
	void updateUUIDKeyOfSms(Long userId, String uuidSMSKey);

	@Query(value=" SELECT user_status FROM user WHERE id =?1 AND is_deleted = 0", nativeQuery = true)
	Boolean findCheckAvailablity(Long driverId);

	@Modifying
	@Transactional
	@Query(value="UPDATE user SET user_status =?2 WHERE id =?1", nativeQuery = true)
	void changeUserStatusDriver(Long userId, Boolean status);

	@Modifying
	@Transactional
	@Query(value="UPDATE user SET card_customer_id =?2 WHERE id =?1", nativeQuery = true)
	void updateCustomerCardId(Long userId, String customerId);

	@Query(value="SELECT * FROM user WHERE id IN (?1) AND is_deleted = 0", nativeQuery = true)
	List<User> findByUserIds(List<Integer> teamIdList);

}
