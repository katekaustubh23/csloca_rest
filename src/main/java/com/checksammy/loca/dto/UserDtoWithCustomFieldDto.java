package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.checksammy.loca.model.DriverInfo;
import com.checksammy.loca.model.Role;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.UserNote;

public class UserDtoWithCustomFieldDto {

	private Long userId;

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private String phone;

	private String companyName;

	private String otp;

	private String token;

	private Boolean isDeleted;

	private Set<Role> roles;
    
	private DriverInfo driverInfo;
    
	private Boolean userStatus;
	
	private Boolean isActive;
	
	private List<UserCustomFieldInstanceDto> customFieldInstance;
	
	private Set<UserNote> userNotes = new HashSet<>();
	
    private Long createdBy;    

	
    private Instant createdTs;
    
    private Long updatedBy;

    
    private Instant updatedTs;   

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public DriverInfo getDriverInfo() {
		return driverInfo;
	}

	public void setDriverInfo(DriverInfo driverInfo) {
		this.driverInfo = driverInfo;
	}

	public Boolean getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<UserCustomFieldInstanceDto> getCustomFieldInstance() {
		return customFieldInstance;
	}

	public void setCustomFieldInstance(List<UserCustomFieldInstanceDto> customFieldInstance) {
		this.customFieldInstance = customFieldInstance;
	}

	public Set<UserNote> getUserNotes() {
		return userNotes;
	}

	public void setUserNotes(Set<UserNote> userNotes) {
		this.userNotes = userNotes;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Instant getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Instant updatedTs) {
		this.updatedTs = updatedTs;
	}

	public UserDtoWithCustomFieldDto addNewUserWithOther(User usernew,
			List<UserCustomFieldInstanceDto> newCustomFieldDto) {
		
		UserDtoWithCustomFieldDto userDtoWithCustomFieldDto = new UserDtoWithCustomFieldDto();
		userDtoWithCustomFieldDto.setCompanyName(usernew.getCompanyName());
		userDtoWithCustomFieldDto.setCustomFieldInstance(newCustomFieldDto);
		userDtoWithCustomFieldDto.setDriverInfo(usernew.getDriverInfo());
		userDtoWithCustomFieldDto.setFirstName(usernew.getFirstName());
		userDtoWithCustomFieldDto.setIsActive(usernew.getIsActive());
		userDtoWithCustomFieldDto.setIsDeleted(usernew.getIsDeleted());
		userDtoWithCustomFieldDto.setLastName(usernew.getLastName());
		userDtoWithCustomFieldDto.setOtp(usernew.getOtp());
		userDtoWithCustomFieldDto.setPassword(usernew.getPassword());
		userDtoWithCustomFieldDto.setPhone(usernew.getPhone());
		userDtoWithCustomFieldDto.setRoles(usernew.getRoles());
		userDtoWithCustomFieldDto.setToken(usernew.getToken());
		userDtoWithCustomFieldDto.setUserId(usernew.getUserId());
		userDtoWithCustomFieldDto.setUsername(usernew.getUsername());
		userDtoWithCustomFieldDto.setUserNotes(usernew.getUserNotes());
		userDtoWithCustomFieldDto.setUserStatus(usernew.getUserStatus());
		userDtoWithCustomFieldDto.setCreatedBy(usernew.getCreatedBy());
		userDtoWithCustomFieldDto.setCreatedTs(usernew.getCreatedTs());
		
		return userDtoWithCustomFieldDto;
	}
	
}
