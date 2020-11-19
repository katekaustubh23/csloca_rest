package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "expenses")
@Where(clause = "is_deleted=0")
public class JobExpenses implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "job_id")
	private Long jobId;
	
	@Column(name = "expense_date")
	private Instant expenseDate;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "details")
	private String details;

	@Column(name = "total")
	private Double total;

	@Column(name = "reimburse_id")
	private Long reimburseId;

	@Column(name = "reumburse_name")
	private String reumburseName;

	@Column(name = "accounting_code_id")
	private Long accountingCodeId;

	@Column(name = "accounting_code_name")
	private String accountingCodeName;
	
	@Column(name = "attachment")
	private String attachment;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "created_ts")
	private Instant createdTs;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Column(name = "updated_ts")
	private Instant updatedTs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Long getReimburseId() {
		return reimburseId;
	}

	public void setReimburseId(Long reimburseId) {
		this.reimburseId = reimburseId;
	}

	public String getReumburseName() {
		return reumburseName;
	}

	public void setReumburseName(String reumburseName) {
		this.reumburseName = reumburseName;
	}

	public Long getAccountingCodeId() {
		return accountingCodeId;
	}

	public void setAccountingCodeId(Long accountingCodeId) {
		this.accountingCodeId = accountingCodeId;
	}

	public String getAccountingCodeName() {
		return accountingCodeName;
	}

	public void setAccountingCodeName(String accountingCodeName) {
		this.accountingCodeName = accountingCodeName;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Instant getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Instant updatedTs) {
		this.updatedTs = updatedTs;
	}

	public Instant getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Instant expenseDate) {
		this.expenseDate = expenseDate;
	}
	
}
