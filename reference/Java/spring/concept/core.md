# 推荐文章

下面的文章里有对DIP,IoC,DI的解释,有很多很好的例子与代码,这里就不重复说明了

强烈建议先阅读下面的文章

[DIP 依赖倒置](https://blog.csdn.net/hfreeman2008/article/details/52289571)
[浅析依赖倒置（DIP）、控制反转(IOC)和依赖注入(DI)](https://frank909.blog.csdn.net/article/details/75093382)


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

# DI

Dependency Injection


这个需要我们移交出对于依赖实例化的控制权，那么依赖怎么办？
Person 无法实例化依赖了，它就需要在外部（IoC 容器）赋值给它，
这个赋值的动作有个专门的术语叫做注入（injection），

需要注意的是在 IoC 概念中，这个注入依赖的地方被称为 IoC 容器，
但在依赖注入概念中，一般被称为注射器 （injector)。



但是缺乏spring方面的细节



# AOP


[spring-AOP](https://www.cnblogs.com/hongwz/p/5764917.html)
Spring有两种实现AOP的方式：Java动态代理 和 Cglib。

