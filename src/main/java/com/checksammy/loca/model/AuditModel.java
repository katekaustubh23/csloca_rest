/**
 * 
 */
package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Abhishek Srivastava
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel implements Serializable {
	
	private static final long serialVersionUID = 1L;


	@Column(name = "created_by")
    private Long createdBy;    

	
    @Column(name = "created_ts")
    @CreatedDate
    private Instant createdTs;
    
    @Column(name = "updated_by")
    private Long updatedBy;

    
    @Column(name = "updated_ts")
    @LastModifiedDate
    private Instant updatedTs;   

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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
}
