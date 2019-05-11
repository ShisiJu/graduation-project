package com.jss.app.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.jss.app.model.dictionary.CommonDictionary;
import com.jss.app.model.dictionary.Term;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Proxy(lazy = false)
@Entity
@Table(name = "T_COURSE")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
@Accessors(chain = true)
public class Course extends CommonDictionary implements Serializable {
	private static final long serialVersionUID = -840690077388663221L;
	@ManyToOne
	private Tutor tutor;
	@ManyToMany
	@JoinTable(name = "T_COURSE_GROUP")
	private List<Group> group;
	private Integer academicYear;
	@Enumerated
	private Term term;

}
