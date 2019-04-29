
org.springframework.beans.factory.BeanCreationException: 
Error creating bean with name 'entityManagerFactory' 
defined in class path resource 
[org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: 
Invocation of init method failed; nested exception is org.hibernate.AnnotationException: 
Illegal attempt to map a non collection as a @OneToMany, @ManyToMany or @CollectionOfElements:
 com.jss.app.model.m2m.StudentCourse.student_id
