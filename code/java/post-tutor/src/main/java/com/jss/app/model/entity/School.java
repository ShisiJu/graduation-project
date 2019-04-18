package com.jss.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.jss.app.model.dictionary.CommonDictionary;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "T_SCHOOL")
@Data
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class School extends CommonDictionary {

	private static final long serialVersionUID = 7457810010934020148L;

}
