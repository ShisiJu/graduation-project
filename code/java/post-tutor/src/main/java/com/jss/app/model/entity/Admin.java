package com.jss.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.jss.app.model.base.RowBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "T_ADMIN")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class Admin extends RowBase {
	private static final long serialVersionUID = 8945426178675295698L;
	@ColumnDefault(value = "0")
	private Long studno ;
}
