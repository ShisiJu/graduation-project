## spring transaction 

### 建议

Spring团队的建议是你在具体的类（或类的方法）上使用 `@Transactional` 注解，
而不要使用在类所要实现的任何接口上。你当然可以在接口上使用 `@Transactional` 注解，
但是这将只能当你设置了基于接口的代理时它才生效。

因为注解是不能继承的，
这就意味着如果你正在使用基于类的代理时，那么事务的设置将不能被基于类的代理所识别，
而且对象也将不会被事务代理所包装（将被确认为严重的）。

因此请接受Spring团队的建议并且在具体的类上使用 `@Transactional` 注解。

## 事务无法使用的可能原因

### 导入spring的事务注解

应该是 `org.springframework.transaction.annotation.Transactional`
而不是 `javax.transaction.Transactional`

### 是否开启了对注解的解析:

- xml 文件配置
 
```xml
<tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true"/>
```

- springboot 

注解开启自动扫描  `@SpringBootApplicationspringboot`  

### spring是否扫描到你使用注解事务的这个类所在的包

- 配置xml 

```xml
<context:component-scan base-package="com.xxx.xxx" ></context:component-scan>
```

- springboot 开启事务 

 `@EnableTransactionManagement` 


### 数据库引擎要支持事务

 如果是mysql，注意表要使用支持事务的引擎，比如InnoDB，如果是myisam，事务是不起作用的

springboot的配置

```xml
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
```

### 检查方法是不是public的

- `@Transactional` 仅仅在 `public` 方法，才能进行事务管理。

![photo_1](jpa_files/1.jpg)

这是因为在使用 Spring AOP 代理时，
Spring 在调用在图中的 TransactionInterceptor 在目标方法执行前后进行拦截之前(图中是cglib代理)

>DynamicAdvisedInterceptor（CglibAopProxy 的内部类）的的 intercept 方法或 JdkDynamicAopProxy 的
invoke 方法会间接调用 AbstractFallbackTransactionAttributeSource ,而会去调用computeTransactionAttribute 方法。
 
```java
protected TransactionAttribute computeTransactionAttribute(Method method,
    Class<?> targetClass) {
        // Don't allow no-public methods as required.
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
			return null;
		}
	}
```

这个方法会判断如果不是 `public` 则会返回 null


### 异常类型是不是unchecked异常

- 默认,只有unchecked异常时才回滚该事务

spring只有在抛出的异常为运行时unchecked异常时才回滚该事务，
也就是抛出的异常为RuntimeException的子类(Errors也会导致事务回滚).

而抛出checked异常则不会导致事务回滚。可以明确的配置在抛出那些异常时回滚事务，
包括checked异常。也可以明确定义那些异常抛出时不回滚事务。


- 如果想让checked异常也回滚，在注解上面写明异常类型即可:

`@Transactional (rollbackFor=Exception.class)`

- noRollbackFor 自定义不回滚的异常



### 异常是不是被catch住了

- 在`Service层`捕捉异常后，发现事务不生效。

在`Service层`手工捕捉并处理了异常（try..catch）等于把异常吃掉了，
Spring自然不知道这里有错，更不会主动去回滚数据。推荐做法是在`Service层`统一抛出异常，
然后在`Controll层`统一处理。


```java
//在类上@Transactional 说明,所以public都是有事务的
@Service
@Transactional
public class StudentService {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private InstituteRepository instituteRepository;

	public void initStudent() {
		Institute institute = Institute.builder().build();
		institute.setCode("TEST4");
		instituteRepository.save(institute);
		
		// 这里自己处理异常,spring不会知道存在异常,无法进行事务回滚
		try {
			throw new RuntimeException("运行时异常----------看事务是否起作用");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
```


修改成如下代码

```java
public void initStudent() throws Exception{
		Institute institute = Institute.builder().build();
		institute.setCode("TEST4");
		instituteRepository.save(institute);
		groupRepository.save(group);
		
		//不进行异常处理,而是把异常抛出
		throw new RuntimeException("运行时异常----------看事务是否起作用");
	}
```


### 避免 Spring 的 AOP 的自调用问题

检查是不是同一个类中的方法调用(如a方法调用同一个类中的b方法),从而避免 Spring 的 AOP 的自调用问题

这是因为在 Spring 的 AOP 代理下，只有目标方法由外部调用，所以类内部互相做方法调用时是不会经过代理类增强的

若同一类中的其他没有`@Transactional` 注解的方法内部调用有`@Transactional` 注解的方法，
有`@Transactional` 注解的方法的事务被忽略，不会发生回滚。

```
@Service
public class StudentService {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private InstituteRepository instituteRepository;
	
	//initStudent() 加上@Transactional(),则会回滚
	public void initStudent() throws Exception{
		Institute institute = Institute.builder().build();
		institute.setCode("TEST4");
		instituteRepository.save(institute);
		
		//虽然 initGroup() 有 @Transactional() 但是事务还是没起作用
		initGroup();
		throw new RuntimeException("运行时异常----------看事务是否起作用");
	}
	
	@Transactional()
	public void initGroup() {
		Group group = Group.builder().academic_year(2015).build();
		group.setCode("ELSE1");
		groupRepository.save(group);
	}
}
```


- AspectJ 取代 Spring AOP 代理



### 参考文章

[Spring Boot 事务的使用](https://blog.csdn.net/catoop/article/details/50595702)

[Spring事务处理时自我调用的解决方案及一些实现方式的风险](https://jinnianshilongnian.iteye.com/blog/1487235)

[spring声明式事务@Transaction的坑](https://blog.csdn.net/y943623901/article/details/50847334)

[Spring 中`@Transactional` 的使用](https://www.ibm.com/developerworks/cn/java/j-master-spring-transactional-use/index.html)

[SpringBoot 快速开启事务中 @Transaction注解不生效的问题](https://blog.csdn.net/qq_21508727/article/details/82705028)