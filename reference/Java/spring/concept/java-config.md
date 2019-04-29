# spring 配置

>本文大部分内容来源spring官方文档,但有些自己的解释与说明.

都继承自 `ApplicationContext`

## 常见配置

- xml配置
- 注解配置
- java配置



## java配置

### @Configuration

```java
public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.register(AppConfig.class, OtherConfig.class);
    ctx.register(AdditionalConfig.class);
    ctx.refresh();
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
}
```


```
@Configuration
@ComponentScan(basePackages = "com.acme") 
public class AppConfig  {
    ...
}
```

```xml
<beans>
    <context:component-scan base-package="com.acme"/>
</beans>
```


```java
public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.scan("com.acme");
    ctx.refresh();
    MyService myService = ctx.getBean(MyService.class);
}
```

### @Bean

```
@Configuration
public class AppConfig {

    @Bean
    public TransferServiceImpl transferService() {
        return new TransferServiceImpl();
    }
}
```


```xml
<beans>
    <bean id="transferService" class="com.acme.TransferServiceImpl"/>
</beans>
```

默认情况下,bean的名字与方法名一致.
 `transferService()` 方法 bean的id即为`transferService`


你也可以返回一个 接口类型

```
@Configuration
public class AppConfig {

    @Bean
    public TransferService transferService() {
        return new TransferServiceImpl();
    }
}
```

However, this limits the visibility for advance type prediction to the specified interface type
 (TransferService). Then, with the full type (TransferServiceImpl) known to the container only once,
  the affected singleton bean has been instantiated. Non-lazy singleton beans get instantiated according 
  to their declaration order, so you may see different type matching results depending on 
  
  when another component tries to match by a non-declared type (such as @Autowired TransferServiceImpl, 
  which resolves only once the transferService bean has been instantiated).
  
  
  
```java
@Configuration
public class AppConfig {

    @Bean
    public TransferService transferService(AccountRepository accountRepository) {
        return new TransferServiceImpl(accountRepository);
    }
}
```

transferService(AccountRepository accountRepository) 中的 `accountRepository` 

如果在spring容器中,存在就可以直接调用


注入内部依赖


```java
@Configuration
public class AppConfig {

    @Bean
    public BeanOne beanOne() {
        return new BeanOne(beanTwo());
    }

    @Bean
    public BeanTwo beanTwo() {
        return new BeanTwo();
    }
}
```

当然,也可以写成


```
@Configuration
public class AppConfig {

    @Bean
    public BeanOne beanOne(BeanTwo beanTwo) {
        return new BeanOne(beanTwo);
    }

    @Bean
    public BeanTwo beanTwo() {
        return new BeanTwo();
    }
}
```

#### 寻找方法注入

As noted earlier, lookup method injection is an advanced feature that you should use rarely.
 It is useful in cases where a singleton-scoped bean has a dependency on a prototype-scoped bean.
  Using Java for this type of configuration provides a natural means for implementing this pattern.
   The following example shows how to use lookup method injection:
   
   
```java
public abstract class CommandManager {
    public Object process(Object commandState) {
        // grab a new instance of the appropriate Command interface
        Command command = createCommand();
        // set the state on the (hopefully brand new) Command instance
        command.setState(commandState);
        return command.execute();
    }

    // okay... but where is the implementation of this method?
    protected abstract Command createCommand();
}
```


```java
@Bean
@Scope("prototype")
public AsyncCommand asyncCommand() {
    AsyncCommand command = new AsyncCommand();
    // inject dependencies here as required
    return command;
}

@Bean
public CommandManager commandManager() {
    // return new anonymous implementation of CommandManager with createCommand()
    // overridden to return a new prototype Command object
    return new CommandManager() {
        protected Command createCommand() {
            return asyncCommand();
        }
    }
}
```


### 更多有关Java配置的内部调用

```
@Configuration
public class AppConfig {

    @Bean
    public ClientService clientService1() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setClientDao(clientDao());
        return clientService;
    }

    @Bean
    public ClientService clientService2() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setClientDao(clientDao());
        return clientService;
    }

    @Bean
    public ClientDao clientDao() {
        return new ClientDaoImpl();
    }
}
```

`clientDao()` 只会调用一次

clientDao() has been called once in clientService1() and once in clientService2(). Since this method creates a new instance of ClientDaoImpl and returns it, you would normally expect to have two instances (one for each service). That definitely would be problematic: In Spring, instantiated beans have a singleton scope by default. This is where the magic comes in: All @Configuration classes are subclassed at startup-time with CGLIB. In the subclass, the child method checks the container first for any cached (scoped) beans before it calls the parent method and creates a new instance.


[Spring 条件注解（@Conditional）](https://blog.csdn.net/xiaolyuh123/article/details/64124828)




### lifecycle 

```

public class BeanOne {

    public void init() {
        // initialization logic
    }
}

public class BeanTwo {

    public void cleanup() {
        // destruction logic
    }
}

@Configuration
public class AppConfig {

    @Bean(initMethod = "init")
    public BeanOne beanOne() {
        return new BeanOne();
    }

    @Bean(destroyMethod = "cleanup")
    public BeanTwo beanTwo() {
        return new BeanTwo();
    }
}
```



默认情况下,`destroyMethod`可以使用名为 `public void close()` 或 `public void shutdown()` 方法
不用指定即可执行


```java

@Configuration
public class AppConfig {
    @Bean
    public BeanOne beanOne() {
        BeanOne beanOne = new BeanOne();
        beanOne.init();
        return beanOne;
    }

}

```


###  @Scope

我们都知道,spring创建bean,默认是 `singleton` 也就是单例模式

但是我们有时也需要多例, `@Scope` 就可以指明创建的模式,如果没有`@Scope` ,则默认单例


```java
@Configuration
public class MyConfiguration {

    @Bean
    @Scope("prototype")
    public Encryptor encryptor() {
        // ...
    }
}
```


### 引入xml

java配置虽好,但是有时候我们还是需要使用xml配置的

我们可以定义一个配置类,使用`@ImportResource("classpath:xxx.xml")`在里面引入外部的xml配置



```java
@Configuration
@ImportResource("classpath:/com/acme/properties-config.xml")
public class AppConfig {

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(url, username, password);
    }
}
```




在配置的`properties-config.xml`中,就可以使用外部properties里面的值了


```xml
<beans>
    <context:property-placeholder location="classpath:/com/acme/jdbc.properties"/>
</beans>
```