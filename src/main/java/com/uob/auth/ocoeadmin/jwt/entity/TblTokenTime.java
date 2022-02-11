package com.uob.auth.ocoeadmin.jwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_token_time")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class TblTokenTime extends BaseEntity {
	
	@Id
	private Long id;
	
	 @Column(name = "token_name", nullable=false)
	private String tokenName;
	 @Column(name = "token_duration", nullable=false)
	private Long tokenDuration;

}
