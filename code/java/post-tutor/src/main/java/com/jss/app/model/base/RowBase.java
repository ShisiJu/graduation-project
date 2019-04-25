package com.jss.app.model.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert(true)
@DynamicUpdate
@Accessors(chain = true)
public class RowBase implements Serializable {

	private static final long serialVersionUID = -8386266958781724931L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false)
	@CreationTimestamp
	private Date createTime;
	@UpdateTimestamp
	private Date updateTime;
}
