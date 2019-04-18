package com.jss.app.model.m2m;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

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
@ToString(callSuper = true)
public class StudentCourse extends RowBase {
	private static final long serialVersionUID = -164015745985836181L;

	private Student student;

	private Course course;

	private Integer status;

}
