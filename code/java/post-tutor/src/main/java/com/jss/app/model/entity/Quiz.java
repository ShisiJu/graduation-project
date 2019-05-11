package com.jss.app.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.jss.app.model.base.RowBase;
import com.jss.app.model.m2m.StudentCourse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "T_QUIZ")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class Quiz extends RowBase  implements Serializable{
	private static final long serialVersionUID = 2233445329770672168L;
	@OneToOne(cascade=CascadeType.REMOVE)
	private StudentCourse studentCourse;
	@ColumnDefault(value = "0")
	private Integer template;
	@ColumnDefault(value = "0")
	private Integer anonymous;
}
