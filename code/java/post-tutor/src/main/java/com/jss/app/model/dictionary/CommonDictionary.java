package com.jss.app.model.dictionary;

import javax.persistence.MappedSuperclass;
import com.jss.app.model.base.RowBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CommonDictionary extends RowBase{

	private static final long serialVersionUID = -1486739177093769006L;
	
	private String code;
	private String name;
}
