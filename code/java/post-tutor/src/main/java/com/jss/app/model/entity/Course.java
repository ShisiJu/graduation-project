package com.jss.app.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jss.app.model.dictionary.CommonDictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "T_COURSE")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class Course extends CommonDictionary {
	private static final long serialVersionUID = -840690077388663221L;
	@ManyToOne
	private Tutor tutor;
	@ManyToMany
	@JoinTable(name="T_COURSE_GROUP")
	private List<Group> group;
	private Integer academic_year;
	private Integer term;

}