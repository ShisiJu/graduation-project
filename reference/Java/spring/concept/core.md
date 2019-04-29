# 推荐文章

下面的文章里有对DIP,IoC,DI的解释,有很多很好的例子与代码,这里就不重复说明了

强烈建议先阅读下面的文章

[DIP 依赖倒置](https://blog.csdn.net/hfreeman2008/article/details/52289571)
[浅析依赖倒置（DIP）、控制反转(IOC)和依赖注入(DI)](https://frank909.blog.csdn.net/article/details/75093382)
[Spring Boot配置方式](https://blog.csdn.net/webzhuce/article/details/54564019)




# DIP

Dependence Inversion Principle


# IoC

Inverse of Control

这种思想其实就是 IoC，IoC 是一种新的设计模式，
它对上层模块与底层模块进行了更进一步的解耦。控制反转的意思是反转了上层模块对于底层模块的依赖控制。 

IoC 少不了 IoC 容器，也就是实例化抽象的地方。


但作为 IoC 容器，无非是针对配置然后动态生成依赖关系。有的配置是开发者按照规则编写在 xml 格式文件中，有些配置则是利用 Java 中的反射与注解。

IoC 模式最核心的地方就是在于依赖方与被依赖方之间，也就是上文中说的上层模块与底层模块之间引入了第三方，这个第三方统称为 IoC 容器，因为 IoC 容器的介入，导致上层模块对于它的依赖的实例化控制权发生变化，也就是所谓的控制反转的意思。

总之，因为 IoC 容器的存在，使得开发者编写大型系统工程的时候极大地解放了生产力。


何时使用Java配置或者注解配置？

主要原则是：全局配置使用Java配置（如数据库相关配置、MVC相关配置），
业务Bean的配置使用注解配置。


Inner class names
If you want to configure a bean definition for a static nested class, 
you have to use the binary name of the nested class.

For example, if you have a class called SomeThing in the com.example package,
 and this SomeThing class has a static nested class called OtherThing, 
 the value of the class attribute on a bean definition would be com.example.`SomeThing$OtherThing`.

Notice the use of the `$` character in the name to separate the nested class name 
from the outer class name.


# DI

## conception

Dependency injection (DI) is a process whereby objects define their dependencies 
(that is, the other objects with which they work) only through constructor arguments,
 arguments to a factory method, or properties that are set on the object instance after 
 it is constructed or returned from a factory method. The container then injects those dependencies 
 when it creates the bean. This process is fundamentally the inverse (hence the name, Inversion of Control) 
 of the bean itself controlling the instantiation or location of its dependencies on 
 its own by using direct construction of classes or the Service Locator pattern.

Code is cleaner with the DI principle, 
and decoupling is more effective when objects are provided with their dependencies.
 
 The object does not look up its dependencies and does not know the location or class of the dependencies.
  
  As a result, your classes become easier to test, particularly when the dependencies are on 
  interfaces or abstract base classes, which allow for stub or mock implementations to be used in unit tests.

## Constructor-base Dependecy Injection

Constructor-based DI is accomplished by the container invoking a constructor with a number of arguments, 
each representing a dependency.
 Calling a static factory method with specific arguments to construct the bean is nearly equivalent, 
 and this discussion treats arguments to a constructor and to a static factory method similarly. 
 
 The following example shows a class that can only be dependency-injected with constructor injection

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- constructor injection using the nested ref element -->
    <constructor-arg>
        <ref bean="anotherExampleBean"/>
    </constructor-arg>

    <!-- constructor injection using the neater ref attribute -->
    <constructor-arg ref="yetAnotherBean"/>

    <constructor-arg type="int" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public ExampleBean(
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {
        this.beanOne = anotherBean;
        this.beanTwo = yetAnotherBean;
        this.i = i;
    }
}
```




## Setter-based Dependency Injection

Setter-based DI is accomplished by the container calling setter methods on your beans 
after invoking a no-argument constructor or a no-argument static factory method to instantiate your bean.

> If you use the `Setter-based Dependency Injection` , it's required to have a no-argument constructor.


The Spring team generally advocates constructor injection,
 as it lets you implement application components as immutable objects and
  ensures that required dependencies are not null.

Furthermore, constructor-injected components are always returned to the client (calling) code 
in a fully initialized state.

As a side note, a large number of  
constructor arguments is a bad code smell, implying that the class likely has too many responsibilities 
 and should be refactored to better address proper separation of concerns.


Setter injection should primarily only be used for optional dependencies that can be assigned reasonable 
default values within the class.

 Otherwise,
 not-null checks must be performed everywhere the code uses the dependency. 
 One benefit of setter injection is that setter methods make objects of that class amenable to 
 reconfiguration or re-injection later. 
 
 Management through JMX MBeans is therefore a compelling use case for setter injection.

Use the DI style that makes the most sense for a particular class.
Sometimes, when dealing with third-party classes for which you do not have the source,
the choice is made for you. For example, if a third-party class does not expose any setter methods,
then constructor injection may be the only available form of DI.

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public void setBeanOne(AnotherBean beanOne) {
        this.beanOne = beanOne;
    }

    public void setBeanTwo(YetAnotherBean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public void setIntegerProperty(int i) {
        this.i = i;
    }
}
```

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- setter injection using the nested ref element -->
    <property name="beanOne">
        <ref bean="anotherExampleBean"/>
    </property>

    <!-- setter injection using the neater ref attribute -->
    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```


### Circular dependencies

If you use predominantly constructor injection, 
it is possible to create an unresolvable circular dependency scenario.

For example: Class A requires an instance of class B through constructor injection, 
and class B requires an instance of class A through constructor injection. 

If you configure beans for classes A and B to be injected into each other, 
the Spring IoC container detects this circular reference at runtime, 
and throws a BeanCurrentlyInCreationException.

One possible solution is to edit the source code of some classes to be configured 
by setters rather than constructors. Alternatively, avoid constructor injection and use setter injection only.
 In other words, although it is not recommended, you can configure circular dependencies with setter injection.

Unlike the typical case (with no circular dependencies), 
a circular dependency between bean A and bean B forces one of the beans to 
be injected into the other prior to being fully initialized itself (a classic chicken-and-egg scenario).


### Why is singleton bean pre-instantiate 

Spring sets properties and resolves dependencies as late as possible,
when the bean is actually created. This means that a Spring container that 
has loaded correctly can later generate an exception when you request an object 
 
if there is a problem creating that object or one of its dependencies — 
for example, the bean throws an exception as a result of a missing or invalid property. 
This potentially delayed visibility of some configuration issues is
why ApplicationContext implementations by default pre-instantiate singleton beans. 

这个需要我们移交出对于依赖实例化的控制权，那么依赖怎么办？
Person 无法实例化依赖了，它就需要在外部（IoC 容器）赋值给它，
这个赋值的动作有个专门的术语叫做注入（injection），

需要注意的是在 IoC 概念中，这个注入依赖的地方被称为 IoC 容器，
但在依赖注入概念中，一般被称为注射器 （injector)。

### factory-method DI

自己写`public static`的工厂方法,然后交给spring让它来进行注入.

这样具体有注入时,可以进行一些其它的操作.

但这样需要自己手写相应的工厂方法,放弃了spring自带的方式,除非有比较特殊的要求,一般也不这么写.

```xml
<bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
    <constructor-arg ref="anotherExampleBean"/>
    <constructor-arg ref="yetAnotherBean"/>
    <constructor-arg value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {

    // a private constructor
    private ExampleBean(...) {
        ...
    }

    // a static factory method; the arguments to this method can be
    // considered the dependencies of the bean that is returned,
    // regardless of how those arguments are actually used.
    public static ExampleBean createInstance (
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {

        ExampleBean eb = new ExampleBean (...);
        // some other operations...
        return eb;
    }
}
```


### 小结

#### 构造器注入

- 有多少属性要注入,构造器就得有多少参数
- 参数顺序可以指定,如果类型唯一也可以不指定
- 不需要空参构造器
- 可能有循环依赖


#### setter 注入

- 类必须有空参构造器,因为spring会先通过空参构造器来创建对象,再使用setter进行DI
- 有相应的setter,才能对类中的属性进行注入



# AOP


## 动态代理


- 注意,第二个doctor.getClass().getInterfaces(),不能直接传入要实现的接口,否者就成了接口的接口了


```java
import java.lang.reflect.Proxy;

public class Receptionist {

	private Doctor doctor;

	public Receptionist() {
		this.doctor = new Dentist();
	}

	public Receptionist(Doctor doctor) {
		this.doctor = doctor;
	}

	public Doctor geProxytDoctor() {

		return (Doctor) Proxy.newProxyInstance(doctor.getClass().getClassLoader(), doctor.getClass().getInterfaces(),
				(proxy, method, args) -> {
					if(method.getName().equals("inquire")) {
						System.out.println("inquire: Can I help you?");
					}
					
					if(method.getName().equals("surgery")) {
						System.out.println("surgery: Are you sure?");
					}
					
					method.invoke(doctor, args);
					//这里如果要返回代理对象需要返回 proxy ,而非 this
					return null;
				});
	}

}

```


第一个参数 `proxy` 具体可以干嘛

[understanding-proxy-arguments-of-the-invoke-method-of-java-lang-reflect-invoca](https://stackoverflow.com/questions/22930195/understanding-proxy-arguments-of-the-invoke-method-of-java-lang-reflect-invoca)

## cglib


cglib是针对类来实现代理的，
它的原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，

但因为采用的是继承，所以不能对final修饰的类进行代理。 

Spring AOP的实现原理

```java
@Around("execution(* pojo.Landlord.service())")
public void around(ProceedingJoinPoint joinPoint) {
	System.out.println("带租客看房");
	System.out.println("谈价格");

	try {
		joinPoint.proceed();
	} catch (Throwable throwable) {
		throwable.printStackTrace();
	}

	System.out.println("交钥匙");
}
```



[spring-AOP](https://www.cnblogs.com/hongwz/p/5764917.html)
[面向切面编程（AOP模块）](https://www.jianshu.com/p/994027425b44)
Spring有两种实现AOP的方式：Java动态代理 和 Cglib。


spring 的 事务就是由AOP来实现的

