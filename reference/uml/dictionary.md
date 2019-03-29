hibernate中实体的关联数据字典的字段在页面显示问题完美解决方案：

在设施实体类，设施类型fclyType存的是数据字典SysParams的paramsValue（但页面要显示paramsName）。

假如tb_facility_info表的一条记录的fcly_type字段存的值是01，
而在数据字典sys_params中paramsValue字段的值为01所对应的paramsName为“码头”，
现在要求在页面中这个设施的设施类型显示为‘码头’，而不是01



1.设施实体类

```
@Entity

@Table(name = "tb_facility_info")

public class FacilityInfo implements java.io.Serializable{

private static final long serialVersionUID = 1404633247921731005L;

 private Integer id;

 private String fclyType; //设施类型



@Id

@GeneratedValue(strategy = IDENTITY)

@Column(name = "id", unique = true, nullable = false)

public Integer getId() {

return id;

}

public void setId(Integer id) {

this.id = id;

}

@DictType(param="GNLB")

@JsonSerialize(using =net.framework.utils.json.DictJsonSerializer.class)

@Column(name = "fcly_type")

public String getFclyType() {

return fclyType;

}

public void setFclyType(String fclyType) {

this.fclyType = fclyType;

}

}
```




2.数据字典实体类

@Entity

@Table(name = "sys_params")

public class SysParams implements java.io.Serializable{

private Integer paramsId;

private String paramsName;

private String paramsType;

private String paramsValue;


// Property accessors

@Id

@GeneratedValue(strategy = IDENTITY)

@Column(name = "params_id", unique = true, nullable = false)

public Integer getParamsId() {

return this.paramsId;

}



public void setParamsId(Integer paramsId) {

this.paramsId = paramsId;

}



@Column(name = "params_name", length = 20)

public String getParamsName() {

return this.paramsName;

}

public void setParamsName(String paramsName) {

this.paramsName = paramsName;

}

@Column(name = "params_type", length = 32)

public String getParamsType() {

return this.paramsType;

}

public void setParamsType(String paramsType) {

this.paramsType = paramsType;

}

@Column(name = "params_value", length = 32)

public String getParamsValue() {

return this.paramsValue;

}

public void setParamsValue(String paramsValue) {

this.paramsValue = paramsValue;

}


}



3.处理类net.framework.utils.json.DictJsonSerializer

package net.framework.utils.json;

import java.io.IOException;

import net.framework.annotation.DictType;

import net.framework.utils.SpringWiredBean;

import com.bxsurvey.sys.params.model.SysParams;

import com.bxsurvey.sys.params.service.SysParamsServiceI;

import com.fasterxml.jackson.core.JsonGenerator;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.BeanProperty;


 
import com.fasterxml.jackson.databind.JsonMappingException;

import com.fasterxml.jackson.databind.JsonSerializer;

import com.fasterxml.jackson.databind.SerializerProvider;

import com.fasterxml.jackson.databind.ser.ContextualSerializer;

public class DictJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

private String param;


//必须要保留无参构造方法

   public DictJsonSerializer() {

       this("");

   }



   public DictJsonSerializer(String param) {

       this.param = param;

   }

@Override

public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws



JsonMappingException {

if (beanProperty != null) { //为空直接跳过

if (beanProperty.getType().getRawClass() == String.class) {

DictType dictType = beanProperty.getAnnotation(DictType.class);

            if(dictType == null){

            dictType =  beanProperty.getContextAnnotation(DictType.class);

            }

            if (dictType != null) { //如果能得到注解，就将注解的value传入ImageURLSerialize

                return new DictJsonSerializer(dictType.param());

            }

}

return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);

}

return serializerProvider.findNullValueSerializer(beanProperty);

}



@Override

public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException,

JsonProcessingException {

   SysParamsServiceI sysParamsService = (SysParamsServiceI)SpringWiredBean.getInstance().getBeanById



("sysParamsService");

   //根据params_type和params_value查询一条数据字典数据

   SysParams sp = sysParamsService.findByValueAndType(value,this.param);

   jgen.writeStartObject();

   jgen.writeStringField("paramsValue", sp.getParamsValue());

   jgen.writeStringField("paramsName", sp.getParamsName());

   jgen.writeEndObject();


}

}



5.自定义注解 @DictType,传递参数作用

package net.framework.annotation;

import java.lang.annotation.Documented;

import java.lang.annotation.Retention;

import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.RetentionPolicy;

@Documented

@Retention(RetentionPolicy.RUNTIME)

public @interface DictType {

   String param() default "";

}

