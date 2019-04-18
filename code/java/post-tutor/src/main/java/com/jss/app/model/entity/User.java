package com.jss.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.jss.app.model.base.RowBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "T_USER")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends RowBase{
	private static final long serialVersionUID = 7589484456455745036L;
	private String username;
	private Long studno;
	@ColumnDefault(value="123456")
	private String pwd;
	private Integer type;
	
}
