package com.jss.app.model.dictionary;

import java.lang.annotation.Documented;

@Documented
public @interface DictType {
	 String value() default "";
}
