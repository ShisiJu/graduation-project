package com.jss.app.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.jss.app.model.base.RowBase;
import com.jss.app.model.dictionary.Sex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "T_STUDENT")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Student extends RowBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	@Enumerated
	private Sex sex;
	private Long student_id;
	private Long class_id;
}
