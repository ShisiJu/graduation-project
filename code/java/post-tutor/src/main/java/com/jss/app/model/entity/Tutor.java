package com.jss.app.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.jss.app.model.base.RowBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="T_TUTOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class Tutor extends RowBase implements Serializable{
	
	private String name;
	private Integer sex;
	private Long tutor_id;

}
