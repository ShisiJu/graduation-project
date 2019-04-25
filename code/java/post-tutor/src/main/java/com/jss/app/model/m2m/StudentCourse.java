package com.jss.app.model.m2m;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.jss.app.model.base.RowBase;
import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "T_STUDENT_COURSE")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class StudentCourse extends RowBase {
	private static final long serialVersionUID = -164015745985836181L;

	@ManyToOne
	private Student student;
	@ManyToOne
	private Course course;
	@ColumnDefault(value = "0")
	private Integer status;

}
