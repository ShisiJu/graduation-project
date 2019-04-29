# Design Philosophy

When you learn about a framework, 
it’s important to know not only what it does but what principles it follows.
Here are the guiding principles of the Spring Framework:

Provide choice at every level. Spring lets you defer design decisions as late as possible.
For example, you can switch persistence providers through configuration without changing your code. 
The same is true for many other infrastructure concerns and integration with third-party APIs.

Accommodate diverse perspectives. 
Spring embraces flexibility and is not opinionated about how things should be done.
 It supports a wide range of application needs with different perspectives.

Maintain strong backward compatibility. 
Spring’s evolution has been carefully managed to force few breaking changes between versions.
Spring supports a carefully chosen range of JDK versions and third-party libraries to
facilitate maintenance of applications and libraries that depend on Spring.

Care about API design. 
The Spring team puts a lot of thought and time into making APIs that are intuitive and that 
hold up across many versions and many years.

Set high standards for code quality. 
The Spring Framework puts a strong emphasis on meaningful, current, and accurate javadoc. 
It is one of very few projects that can claim clean code structure 
with no circular dependencies between packages.


## Are annotations better than XML for configuring Spring?

The introduction of annotation-based configuration raised the question of 
whether this approach is “better” than XML. The short answer is “it depends.” 

The long answer is that each approach has its pros and cons, and, usually, 
it is up to the developer to decide which strategy suits them better. 
Due to the way they are defined, annotations provide a lot of context in their declaration, 
leading to shorter and more concise configuration. 

However, XML excels at wiring up components without touching their source code or recompiling them. 
Some developers prefer having the wiring close to the source while others argue that 
annotated classes are no longer POJOs and, furthermore, 
that the configuration becomes decentralized and harder to control.

No matter the choice, Spring can accommodate both styles and even mix them together. 
It is worth pointing out that through its JavaConfig option, 
Spring lets annotations be used in a non-invasive way, without touching the target components source code and that,
 in terms of tooling, all configuration styles are supported by the Spring Tool Suite.
 
## Bean Naming Conventions
 
The convention is to use the standard Java convention for instance field names when naming beans. 
That is, bean names start with a lowercase letter and are camel-cased from there. 
Examples of such names include accountManager, accountService, userDao, loginController, and so forth.

Naming beans consistently makes your configuration easier to read and understand.
 Also, if you use Spring AOP, it helps a lot when applying advice to a set of beans related by name.
 