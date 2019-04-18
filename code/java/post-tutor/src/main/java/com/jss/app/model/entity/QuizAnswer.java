package com.jss.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jss.app.model.base.RowBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "T_QUIZ_ANSWER")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class QuizAnswer extends RowBase {
	private static final long serialVersionUID = 5620892169388232271L;
	@ManyToOne
	private Quiz quiz;
	private Integer question;
	private Integer type;
	private String answer;

}
