package com.checksammy.loca.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author kartik.thakre
 *
 */
@Entity
@Table(name="user_role")
public class UserRole 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;	

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name="approver")
	private Long approverUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(Long approverUserId) {
		this.approverUserId = approverUserId;
	}

}
