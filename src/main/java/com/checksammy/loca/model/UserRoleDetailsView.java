/**
 * 
 */
package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

/**
 * @author Abhishek Srivastava
 *
 */
@Entity
@Table(name = "vgetuserandrole")
@Where(clause = "is_user_deleted=0")
public class UserRoleDetailsView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "phone")
	private String phone;

	@Column(name = "company_name")
	private String company_name;

	@Column(name = "otp")
	private String otp;

	@Column(name = "token")
	private String token;

	@Column(name = "is_user_deleted")
	private Boolean isUserDeleted;

	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "is_role_deleted")
	private Boolean isRoleDeleted;

	@Column(name = "role_approver")
	private Long roleApprovedBy;

	@JoinColumn(name = "driver_info_id", referencedColumnName = "id")
	@ManyToOne()
	private DriverInfo driverInfo;

	@Column(name = "user_status")
	private Boolean userStatus;

	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "created_ts")
	private Instant createdTs;
	
	@Column(name = "updated_ts")
	private Instant updatedTs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
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

	public Boolean getIsUserDeleted() {
		return isUserDeleted;
	}

	public void setIsUserDeleted(Boolean isUserDeleted) {
		this.isUserDeleted = isUserDeleted;
	}

	public Boolean getIsRoleDeleted() {
		return isRoleDeleted;
	}

	public void setIsRoleDeleted(Boolean isRoleDeleted) {
		this.isRoleDeleted = isRoleDeleted;
	}

	public Long getRoleApprovedBy() {
		return roleApprovedBy;
	}

	public void setRoleApprovedBy(Long roleApprovedBy) {
		this.roleApprovedBy = roleApprovedBy;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Instant getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}

	public Instant getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Instant updatedTs) {
		this.updatedTs = updatedTs;
	}

}