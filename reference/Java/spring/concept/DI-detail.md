
`idref` 是注入字符串,是具体某个bean的ID
`ref` 是bean的应用

```xml
<bean id="something" class="things.ThingOne">
    <property name="fred.bob.sammy" value="123" />
</bean>
```


The something bean has a fred property,
which has a bob property, which has a sammy property,
and that final sammy property is being set to a value of 123. 

In order for this to work, the fred property of something and the bob property of fred 
must not be null after the bean is constructed. Otherwise, a NullPointerException is thrown.

### depends-on

```
<bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
    <property name="manager" ref="manager" />
</bean>

<bean id="manager" class="ManagerBean" />
<bean id="accountDao" class="x.y.jdbc.JdbcAccountDao" />
```

### lazy-init

```
<bean id="lazy" class="com.something.ExpensiveToCreateBean" lazy-init="true"/>
```

### Lifecycle Mechanisms
As of Spring 2.5, you have three options for controlling bean lifecycle behavior:

Multiple lifecycle mechanisms configured for the same bean, 
with different initialization methods, are called as follows:

Methods annotated with @PostConstruct

afterPropertiesSet() as defined by the InitializingBean callback interface

A custom configured init() method

Destroy methods are called in the same order:

Methods annotated with @PreDestroy

destroy() as defined by the DisposableBean callback interface

A custom configured destroy() method


## annotation


n the exclusive case of @Resource usage with no explicit name specified,
 and similar to @Autowired, @Resource finds a primary type match instead of a specific named bean 
 and resolves well known resolvable dependencies: the BeanFactory, ApplicationContext, ResourceLoader, 
 ApplicationEventPublisher, and MessageSource interfaces.

Thus, in the following example, the customerPreferenceDao field first looks for a bean 
named "customerPreferenceDao" and then falls back to a primary type match for the type CustomerPreferenceDao:

```
public class MovieRecommender {

    @Resource
    private CustomerPreferenceDao customerPreferenceDao;

    @Resource
    private ApplicationContext context; 

    public MovieRecommender() {
    }

    // ...
}
```

### package scan

The use of <context:component-scan> implicitly enables the functionality of 
<context:annotation-config>. There is usually no need to include the <context:annotation-config>
 element when using <context:component-scan>
 
 
 ### Full @Configuration vs “lite” @Bean mode?

When @Bean methods are declared within classes that are not annotated with @Configuration, 
they are referred to as being processed in a “lite” mode. Bean methods declared in a @Component or 
even in a plain old class are considered to be “lite”, 
with a different primary purpose of the containing class and a @Bean method being a sort of bonus there. 

For example, service components may expose management views to the container through an additional 
@Bean method on each applicable component class. In such scenarios, @Bean methods are a general-purpose 
factory method mechanism.

Unlike full @Configuration, lite @Bean methods cannot declare inter-bean dependencies. 
Instead, they operate on their containing component’s internal state and, optionally, on arguments that 
they may declare. Such a @Bean method should therefore not invoke other @Bean methods. 

Each such method is literally only a factory method for a particular bean reference, 
without any special runtime semantics. The positive side-effect here is that no CGLIB subclassing 
has to be applied at runtime, so there are no limitations in terms of class design (that is, 
the containing class may be final and so forth).

In common scenarios, @Bean methods are to be declared within @Configuration classes, 
ensuring that “full” mode is always used and that cross-method references therefore get redirected to
 the container’s lifecycle management. This prevents the same @Bean method from accidentally 
 being invoked through a regular Java call, which helps to reduce subtle bugs 
 that can be hard to track down when operating in “lite” mode.