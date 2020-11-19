/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.UserCustomFieldInstanceDto;
import com.checksammy.loca.dto.UserDtoWithCustomFieldDto;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.UserFieldInstance;
import com.checksammy.loca.repository.FieldTypeInstanceRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.service.UserService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repo;

	@Autowired
	FieldTypeInstanceRepository fieldTypeRepository;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Boolean spUpsertUser(User user, List<String> roleNames) {
		StoredProcedureQuery query = null;
		Boolean status = false;
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		for (String roleName : roleNames) {
			query = em.createStoredProcedureQuery("sp_upsert_user", User.class);
			query.registerStoredProcedureParameter("p_firstName", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("p_lastName", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("p_emailId", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("p_phone", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("p_companyName", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("p_roleName", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("p_isDeleted", Boolean.class, ParameterMode.IN);

			query.setParameter("p_firstName", user.getFirstName());

			query.setParameter("p_lastName", user.getLastName());

			query.setParameter("p_emailId", user.getUsername());
			query.setParameter("p_password", encodedPassword);
			query.setParameter("p_phone", user.getPhone());
			query.setParameter("p_companyName", user.getCompanyName());
			query.setParameter("p_userId",
					(user.getUpdatedBy() != null && user.getUpdatedBy() > 0) ? user.getUpdatedBy()
							: user.getCreatedBy());
			query.setParameter("p_roleName", roleName);
			query.setParameter("p_isDeleted", user.getIsDeleted());
			status = query.execute();
		}

		return status;
	}

	@Override
	public Boolean isExistingUser(String emailId) {
		ExampleMatcher emailIdMatcher = ExampleMatcher.matching().withIgnorePaths("id").withMatcher("email_id",
				GenericPropertyMatchers.ignoreCase());
		User user = new User();
		user.setUsername(emailId);
		Example<User> example = Example.of(user, emailIdMatcher);
		return repo.exists(example);
	}

	@Override
	public void changeStatus(Long userId, Boolean status) {
		repo.changeUserStatus(status, userId);

	}

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {

		return repo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + ": does not exist."));
	}

	@Override
	public void setOTPByUserName(String otp, String username) {
		repo.setOTPByUserName(otp, username);
	}

	@Override
	public User updateUser(User user) {
		return repo.save(user);

	}

	@Override
	public Long getTotalNoOfUsers() {
		return repo.count();
	}

	@Override
	public void changePassword(String userName, String password) {
		repo.changePassword(userName, password);

	}

	@Override
	public void updateUserDriverInfoId(Long userId, Long driverInfoId, Boolean userStatus, Boolean isActive,
			String fcmToken) {
		repo.updateDriverInfoId(userId, driverInfoId, userStatus, isActive, fcmToken);
	}

	@Override
	public User getUserId(Long createdBy) {
		return repo.findByUserId(createdBy);
	}

	@Override
	public List<User> findUserByRole(Long roleId) {
		return repo.findUserByRole(roleId);
	}

	@Override
	public UserDtoWithCustomFieldDto findByUsernameNew(String userName) {
		User usernew = repo.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException(userName + ": does not exist."));
		UserDtoWithCustomFieldDto userDtoWithCustomFieldDto = new UserDtoWithCustomFieldDto();
		if (usernew != null) {
			List<UserCustomFieldInstanceDto> newCustomFieldDto = new ArrayList<UserCustomFieldInstanceDto>();
			for (UserFieldInstance customFieldInstance : usernew.getCustomFieldInstance()) {
				UserCustomFieldInstanceDto customFieldInstanceDto = new UserCustomFieldInstanceDto();
				Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
						.findById(customFieldInstance.getFieldInstanceId());
				customFieldInstanceDto = customFieldInstanceDto.addCustomRecord(customFieldInstance,
						fieldTypeInstance.get());
				newCustomFieldDto.add(customFieldInstanceDto);
			}
			userDtoWithCustomFieldDto = userDtoWithCustomFieldDto.addNewUserWithOther(usernew, newCustomFieldDto);
		}
		return userDtoWithCustomFieldDto;
	}

	@Override
	public void changeActiveInactive(Long userId, Boolean status) {
		repo.changeActiveInactive(userId, status);
	}

	@Override
	public void updateFcmToken(Long userId, String fcmToken) {
		repo.updateFcmToken(userId, fcmToken);
	}

	@Override
	public User updateUserFromDeviceProfile(User user) {
		repo.updateUserFieldFromDevice(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserStatus(),
				user.getProfilePic());
		user = repo.findByUserId(user.getUserId());
		return user;
	}

	@Override
	public void updateUserUUIDSmsKey(Long userId, String uuidSMSKey) {
		repo.updateUUIDKeyOfSms(userId, uuidSMSKey);
	}

	@Override
	public Boolean checkAvailablity(Long driverId) {
		return repo.findCheckAvailablity(driverId);
	}

	@Override
	public void changeUserActiveInactive(Long userId, Boolean status) {
		repo.changeUserStatusDriver(userId, status);
	}

	@Override
	public void updateCardCustomerId(Long userId, String customerId) {
		repo.updateCustomerCardId(userId, customerId);
		
	}

	@Override
	public List<User> findByUserIds(List<Integer> teamIdList) {
		// TODO Auto-generated method stub
		return repo.findByUserIds(teamIdList);
	}

	@Override
	public User findById(Long createdBy) {
		// TODO Auto-generated method stub
		return repo.findByUserId(createdBy);
	}

}
