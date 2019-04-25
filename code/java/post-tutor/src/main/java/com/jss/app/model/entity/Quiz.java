package com.jss.app.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

public class Quiz extends RowBase {
	private static final long serialVersionUID = 2233445329770672168L;
//	@ManyToOne
//	private Student student;
//	@ManyToOne
//	private Course course;
	@OneToOne
	private StudentCourse studentCourse;
	@ColumnDefault(value = "0")
	private Integer template;
	@ColumnDefault(value = "0")
	private Integer anonymous;
}
