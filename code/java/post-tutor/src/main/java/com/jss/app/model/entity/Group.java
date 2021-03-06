package com.jss.app.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jss.app.model.dictionary.CommonDictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Steven Ju
 * @date 2019年3月28日
 */

@Entity
@Table(name = "T_GROUP")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Group extends CommonDictionary implements Serializable{
	private static final long serialVersionUID = -8088773384139496113L;

	private Integer academicYear;
	@ManyToOne
	private Institute institute;

}
