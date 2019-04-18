package com.jss.app.model.entity;

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

@Entity
@Table(name="T_INSTITUTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Institute extends CommonDictionary{
	private static final long serialVersionUID = 6873272756110322397L;
	@ManyToOne
	private School school;
	
}
