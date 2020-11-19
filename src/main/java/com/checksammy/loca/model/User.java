package com.checksammy.loca.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

/**
 * 
 * @author kartik.thakre
 *
 */
@Entity
@Table(name = "user")
@Where(clause = "is_deleted=0")
public class User extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long userId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email_id")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "phone")
	private String phone;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "otp")
	private String otp;

	@Column(name = "token")
	private String token;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@JoinColumn(name = "driver_info_id", referencedColumnName = "id")
	@ManyToOne()
	private DriverInfo driverInfo;

	@Column(name = "user_status")
	private Boolean userStatus;

	@Column(name = "is_active")
	private Boolean isActive;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<UserFieldInstance> customFieldInstance = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userId")
//	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<UserNote> userNotes = new HashSet<>();

	@Column(name = "fcm_token")
	private String FCMToken;
	
	@Column(name = "profile_pic")
	private String profilePic;
	
	@Column(name = "uuid_key_sms")
	private String uuidKeySms;
	
	@Column(name = "card_customer_id")
	private String cardCustomerId;

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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public Set<UserFieldInstance> getCustomFieldInstance() {
		return customFieldInstance;
	}

	public void setCustomFieldInstance(Set<UserFieldInstance> customFieldInstance) {
		this.customFieldInstance = customFieldInstance;
	}

	public Set<UserNote> getUserNotes() {
		return userNotes;
	}

	public void setUserNotes(Set<UserNote> userNotes) {
		this.userNotes = userNotes;
	}

	public String getFCMToken() {
		return FCMToken;
	}

	public void setFCMToken(String fCMToken) {
		FCMToken = fCMToken;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getUuidKeySms() {
		return uuidKeySms;
	}

	public void setUuidKeySms(String uuidKeySms) {
		this.uuidKeySms = uuidKeySms;
	}

	public String getCardCustomerId() {
		return cardCustomerId;
	}

	public void setCardCustomerId(String cardCustomerId) {
		this.cardCustomerId = cardCustomerId;
	}

}
