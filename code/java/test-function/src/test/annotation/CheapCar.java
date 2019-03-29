package test.annotation;

import java.lang.annotation.Annotation;

@Cheap("20w")
@Deprecated
public class CheapCar {
		// 注解通过reflect 来进行操作
	public static void main(String[] args) {
		Class<CheapCar> clazz = CheapCar.class;
	
		boolean annotationPresent = clazz.isAnnotationPresent(Cheap.class);
		Annotation[] annotations = clazz.getAnnotations();
		System.out.println(annotationPresent);
		
		for (Annotation annotation : annotations) {
			System.out.println("a : "+annotation);
		}
		
	
	
	
	}
}
