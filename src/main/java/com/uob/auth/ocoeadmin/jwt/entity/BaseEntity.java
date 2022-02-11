package com.uob.auth.ocoeadmin.jwt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	
	private String createdBy;
	
	@Column(name = "CREATED_TS", nullable=false, length = 100)
    private Date createdDateTime;
	
    private String updatedBy;
	
    @Column(name = "UPDATED_TS", nullable=false)
    private Date updatedDateTime;

	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	//@Column(name = "CREATED_TS", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	@Column(name = "UPDATED_BY", nullable=false, length = 100)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	
	//@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

}
