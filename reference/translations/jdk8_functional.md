# Java开发者的函数式编程

使用这五个函数式编程机巧使你的Java代码更加优秀。

Java 8 的函数式编程通过 lambda 表达式来介绍给大家。

这次Java发布的内容使Java开发者改变了认为Java编程是仅仅为了命令式，面向对象的观点。一个Java开发者也必须能够使用声明的函数式编程方式进行思考和编程。

这个教程展示了函数式编程的基本内容。我将从术语开始。然后我们将深入了解函数式编程的概念。
我将通过五个函数式编程的技术做一个总结。在这一部分的编码实例将你以纯正的，高阶的函数，延迟计算，闭包和科力化。

函数式编程正在上升阶段。
2018年，IEEE在最顶尖的25种编程语言中包含了函数式编程语言。并且谷歌现阶段也把函数式编程视为和面向对象编程同等重要。

很明显，函数式编程不能够被忽略。但是为什么它会如此流行呢？在其它方面，函数式编程可以简单地验证程序的正确性。它也是很简单地提供并发地编程。并发编程是提升应用性能的关键。

## 什么是函数式编程？

计算机典型地实现了冯·诺依曼结构，它是被数学家和物理学家冯·诺依曼发明并广泛地在计算机体系结构中被使用结构。
这种结构是以命令式编程为基础的，它一种使用声明来改变程序状态的编程范式，诸如C，C++，和 Java ，它们全部是命令式编程语言。

在1977年，著名的计算机科学家 John Backus，发表了一篇《冯·诺依曼结构下，程序能否无拘束？》。Backus维护了冯·诺依曼结构和它相关联的命令式语言是有功能性缺陷的。
提出来函数级编程语言作为解决方案。

澄清Backus，因为 Backus的演讲是在几十年前发表的，许多想法在当时是很难被理解的。2018年一月，Blogger Tomasz Jaskuła 在他的博客文章中添加了澄清和角标。

## 函数式编程的概念和术语

函数式编程是一种编程风格，在计算中被编码成函数式编程的函数。这些是被使用在表达上下文中的数学形式类似函数的概念。（例如 lambda函数）

函数式编程语言是声明式的。意味着它是不用描述控制流程的计算逻辑。在声明式的编程中，没有声明。与之对应的是编程着用表达式来告诉计算机，什么需要被做，但是没有告诉计算机如何完成任务。

如果你熟悉SQL或者正则表达式，那么你就有声明式编程的经验了。两者都使用表达式来描述什么要被做，而不是使用声明来描述如何去做。

在函数式编程中，一个计算被描述成一些表达式上下文的函数。这些函数不同于使用命令式编程的函数（诸如，Java方法返回一个值）。而是，一个函数式编程像一个数学函数一样。它产生了一个仅仅依靠它的参数的输出。

每一次一个函数式编程函数被称为使用同样的参数，并且得到同样的结果。函数在函数式编程中被认为展示了透明相关性。这意味这你不用改变计算的意义下用结果值替换函数调用。


函数式编程偏好不变性,这意味着状态不能改变.命令式编程通常不是这样的.一个命令式的函数会与状态连接到一起.(例如一个Java实例变量).
因为在这个实例中状态是可变的,所以称这个函数在不同时间中有相同的参数却可以有不同的返回值.

## 命令式和函数式编程的副作用

状态改变是命令式编程的一大副作用,阻碍了相关透明度.有许多其它的副作用也应该知道,尤其是当你决定是在你的程序中使用命令式还是函数式的风格.

在命令式编程中一个常见的副作用是当一个赋值语句通过改变存贮的值来改变变量.在函数式编程中的函数不支持这种变量赋值.因为一个变量初始值从不改变.函数式编程限制了这一个副作用.

另一个常见的副作用发生在修改基于抛出异常的函数的行为,这是一个对调用者可观察的交互.可以在Stack Overflow看到更多对《日益增长的异常副作用是什么？》


第三个常见的副作用是当I/O操作,输入不能读的文本，或输出不能写的文本.了解详情,可以在Stack Exchange中查看《在函数式编程中IO是如何造成副作用的？》.

去除掉副作用使程序更容易理解和预测计算的行为.这也是帮助代码更加适用于平行处理,通常能够提升应用的性能.然而这些副作用在函数式编程中,它们
通常少于命令式编程.使用函数式编程可以帮助你写容易理解方便测试和可读性的代码.

## 函数式编程的起源

函数式编程起源于lambda微积分,它是被Alonzo Church所提出的.另一个起源是组合逻辑,它被Moses Schönfinkel所提出,随后被Haskell Curry发展.


## 面向对象VS函数式编程

我创建了一个Java与命令式,面向对象和声明式相关的应用.函数式编程来写代码.学习下面代码,然后我将之处两个例子的不同之处.

Listing 1. Employees.java

```java
import java.util.ArrayList;
import java.util.List;
public class Employees
{
   static class Employee
   {
      private String name;
      private int age;
      Employee(String name, int age)
      {
         this.name = name;
         this.age = age;
      }

      int getAge()
      {
         return age;
      }

      @Override
      public String toString()
      {
         return name + ": " + age;
      }
   }

   public static void main(String[] args)
   {
      List<Employee> employees = new ArrayList<>();
      employees.add(new Employee("John Doe", 63));
      employees.add(new Employee("Sally Smith", 29));
      employees.add(new Employee("Bob Jone", 36));
      employees.add(new Employee("Margaret Foster", 53));
      printEmployee1(employees, 50);
      System.out.println();
      printEmployee2(employees, 50);
   }

   public static void printEmployee1(List<Employee> employees, int age)
   {
      for (Employee emp: employees)
         if (emp.getAge() < age)
            System.out.println(emp);
   }
   public static void printEmployee2(List<Employee> employees, int age)
   {
      employees.stream()
               .filter(emp -> emp.age < age)
               .forEach(emp -> System.out.println(emp));
   }
}
```


Listing 1 发布了一个 Employees 的应用,创建了一些Employee对象,然后输出了一组年龄小于50的Employee对象.这个代码从面向对象和函数式编程
两方面实现.

printEmployee1() 方法展示了命令式,面向声明的方式.同样的,这个方法迭代了所有list中的employee,比较了employee的年龄,(如果年龄小于参数)
打印出employee的细节.

printEmployee2() 方法展示了声明式,面向表达的方式.在这个例子中使用了Streams API.替代了命令式的实现如何打印出employee,这个表达式指定了结果,并留下了如何用Java去实现的细节.

想想filter()作为函数式的相同的if声明,和forEach()做为相同for的声明.

你可以想下面一样编译 Listing 1
javac Employees.java

使用下面的命令来运行
java Employees

输出结果应该像这样
Sally Smith: 29
Bob Jone: 36
Sally Smith: 29
Bob Jone: 36

## 函数式编程实例

在下一节中,我们将探索5个在函数式编程汇总使用核心技术.

- 纯粹的函数
- 高阶函数
- 延迟实现
- 闭包
- 加脂法

这一节中的实例是用JavaScript写的,因为它简洁并且Java相关.将允许我们关注于技术.在Part2我们将重新回顾同样的技术用Java实现.


Listing 2 展示了RunScript的源代码,一个使用Java脚本API的Java应用来帮助运行JavaScript代码.Runscript将是下面实例运行的基础.



Listing 2. RunScript.java
 
```java
import java.io.FileReader;
import java.io.IOException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import static java.lang.System.*;

public class RunScript

{
	public static void main(String[] args) {
		if (args.length != 1){
			err.println("usage: java RunScript script");
			return;
		}
		ScriptEngineManager manager = new ScriptEngineManager();

		ScriptEngine engine = manager.getEngineByName("nashorn");
		try {

			engine.eval(new FileReader(args[0]));

		}

		catch (ScriptException se) {
			err.println(se.getMessage());
		}

		catch (IOException ioe) {
			err.println(ioe.getMessage());

		}
	}
}
```


这个 main() 方法在实例中首先验证单个的命令行参数被指定.然而,它展示惯例信息和终止应用.


设想这个参数的展示,main()实例化javax.script.ScriptEngineManager类,ScriptEngineManager是这个实体点进入Java脚本的API.

下一次,这个ScriptEngineManager对象的ScriptEngine的getEngineByName(String shortName)方法被调用遵循脚本引擎纠正希望的shortName的
值.Java 10支持这个 Nashorn引擎,它是遵循通过nashorn来getEngineByName().这个返回的对象的类实现了javax.script.ScriptEngine接口.

ScriptEngine 声明了几个eval()方法来实现一个脚本.main()调用Object eval(Reader reader) 方面来读取从java.io.FileReader对象参数和
(假设java.io.IOException没有抛出).之后实现这个脚本.
这个方法返回了任意脚本任意值.同样,当发生异常时,这个方法抛出javax.script.ScriptException.

编译 Listing 2 :

javac RunScript.java

我将向你展示如何在我展示的第一个脚本后运行这个应用.


## 纯粹的函数下的函数式编程

一个纯粹的函数是一个只依赖它的输入参数和没有额外声明的函数式编程函数.一个不纯粹的函数是一个函数式编程函数违反了这些需要.
因为纯粹的函数没有和外边的世界有交互.(除了调用其它纯粹的函数).一个纯粹的函数总是在相同的参数下,返回相同的结果.纯粹的函数也没有明显的副作用.


一个纯粹的函数能执行I/O吗?
如果I/O是一个副作用,一个纯粹的函数能执行I/O吗?答案是可以的.Haskell使用monads来解决这个问题.

纯粹的函数VS不纯粹的函数
这个在Listing 3中JavaScript中不纯粹的calculatebonus()函数与纯粹的calculatebonus2() 对比.

Listing 3. Comparing pure vs impure functions (script1.js)

```js
// impure bonus calculation
var limit = 100;
function calculatebonus(numSales) 
{
   return(numSales > limit) ? 0.10 * numSales : 0
}

print(calculatebonus(174))
// pure bonus calculation
function calculatebonus2(numSales)
{
   return (numSales > 100) ? 0.10 * numSales : 0
}
print(calculatebonus2(174))
```

calculatebonus()是非纯粹的,因为他读取了额外的limit变量.相反,calculatebonus2()是纯粹的,因为它遵守了纯粹的两个需求.
像下面一样运行 script1.js

java RunScript script1.js

Here's the output you should observe:

你应该看到下面的输出

```
17.400000000000002
17.400000000000002
```

支持calculatebonus2()是对calculatebonus(numSales)返回的重构.那calculatebonus2(numSales)还仍是纯粹的吗?
答案是不是.当一个纯粹的函数调用另一个不纯粹的函数,那么这个函数就变的不纯粹了


当两个纯粹的函数没有数据依赖时,它们可以被评估为在任意要求下不影响结果.使它们更加适合平行计算.这是它们函数式编程的好处之一.


更多关于非纯粹函数
不是所以的函数式编程函数需要纯粹.作为函数式编程:纯粹的函数式解释为它是可能的(有时是被期待的)来分离纯粹的,函数式的,外部应用值相关核心,命令式shell.


## 高阶函数式编程

一个高阶函数是像参数一样接受函数的数学的函数,返回一个函数给它的调用者或者都返回.一个例子是对不同的操作员的积分.d/dx,它返回f函数的派生.

优秀的函数是优秀的公民

近相关对数学的高阶函数感念来讲是一流的函数,它是函数式编程函数的采用其它函数式编程函数作为参数或者返回一个函数式编程的函数.
一流的函数是一流的公民,因为无论其它一流的程序实体,他们都可以出现.包括被视为标志的一个变量或者被通过的一个参数,又或者函数的返回.

The JavaScript in Listing 4 展示了通过匿名的比较函数来进行一流的排序函数

Listing 4  通过匿名的比较函数(script2.js)

```js
function sort(a, cmp)
{
   for (var pass = 0; pass < a.length - 1; pass++)
      for (var i = a.length - 1; i > pass; i--)
         if (cmp(a[i], a[pass]) < 0)
         {
            var temp = a[i]
            a[i] = a[pass]
            a[pass] = temp
         }
}
var a = [22, 91, 3, 45, 64, 67, -1]

sort(a, function(i, j)
		{
		   return i - j;
		})
		
a.forEach(function(entry) { print(entry) })
print('\n')

sort(a, function(i, j)
        {
           return j - i;
        })

a.forEach(function(entry) { print(entry) })
print('\n')

a = ["X", "E", "Q", "A", "P"]
sort(a, function(i, j)
        {
           return i < j ? -1 : i > j;
        })

a.forEach(function(entry) { print(entry) })
print('\n')

sort(a, function(i, j)
        {
           return i > j ? -1 : i < j;
        })
a.forEach(function(entry) { print(entry) })
```


在这个例子中,这个初始化的sort()调用了接受一个接收数组作为它第一个参数和一个匿名比较器的函数.
当调用时,这个匿名的比较器函数执行 return i - j; 来达到升序排序.通过反转i和j,第二个比较器函数达到了一个倒序排序.
第三个和第四个排序调用接收了匿名的排序函数是有点不同的,目的是进行正确的字符串值的比较.

运行 script2.js
java RunScript script2.js

输出应该是

```
-1 3 22 45 64 67 91 
91 67 64 45 22 3 -1
A E P Q X
X Q P E A
```
 
Filter和map
函数式编程语言通常提供了几个很有用的高阶函数.两个常见的例子是filter 和 map


一个filter处理一个list,在一些要求下产出一个包含在原始list中确切的元素中被认定为返回值为真的新list.

一个map应用在给出的函数中list里的每一个元素,以相同的顺序返回list的结果
JavaScript通过filter() and map()支持filtering 和 mapping高阶函数.

Listing 5 展示了这些函数筛选出奇数和映射数字到它们的立方.

Listing 5. Filtering and mapping (script3.js)

```js
print([1, 2, 3, 4, 5, 6].filter(function(num) { return num % 2 == 0 }))
print('\n')
print([3, 13, 22].map(function(num) { return num * 3 }))
```

用下列方式运行  script3.js :

java RunScript script3.js

你应该得到的结果

```
2,4,6
9,39,66
```

另一个常见的高阶函数是减少,它更多地以折叠被广泛了解.这个还是减少一系列单独的值

Listing 6 使用的JavaScript的reduce() 高阶还是来减少一个数组里的单独值,它随后通过数组的长度来得到一个平均值.

Listing 6. 减少数组的数字来得到单独的数字 (script4.js)

```js
var numbers = [22, 30, 43]
print(numbers.reduce(function(acc, curval) { return acc + curval })/ numbers.length)
```

以下列方式运行Listing 6
java RunScript script4.js

应该观察到的内容

```
31.666666666666668
```
你可能认为这个filter, map, 和 reduce 高阶函数避免了需要if-else和各种循环声明,你是正确的.他们内置的实现处理了决策和声明.
一个高阶的函数使用了递归来实现迭代.一个递归函数调用了它自己,允许一个操作来重复,知道它达到预期.你也可以在你的代码里利用递归来进行迭代.

## 函数式编程的延迟计算

另一个重要的函数式编程的特性是延迟计算.(也被称为非严格求值),它是尽可能慢进行表达式计算.延迟计算提供了几个好处,包括下面两个

- 昂贵的(费时的)计算可以延迟到它们真正被需要的时候
- 非绑定的集合是可能的.只要需要，他们就会一直交付元素

延迟计算是集成到Haskell的.他不会计算任何事情(包括在还是调用之前的一个函数的参数),除非它真正地需要这么做.

Java的Streams API 利用了延迟计算.一个stream的中级操作(例如 filer())通常是懒加载的.它们不会做任何事情,直到终点的操作被执行.(例如 forEach())


尽管延迟计算是函数语言的重要部分,但是在许多命令式语言中提供了许多装入式的形式的支持.
例如,许多编程语言在上下文中支持 short-circuit计算.Boolean AND 和 OR 计算符合,它们是懒惰的,当左边的运算结果是false(AND)或true(OR),就会拒绝计算它们右边的运算对象.

Listing 7 是一个延迟计算的例子 JavaScript 

```js
var a = false && expensiveFunction("1")
var b = true && expensiveFunction("2")
var c = false || expensiveFunction("3")
var d = true || expensiveFunction("4")
function expensiveFunction(id)
{
   print("expensiveFunction() called with " + id)
}
```

运行下列的代码

java RunScript script5.js

你应该观察到的结果
```
expensiveFunction() called with 2
expensiveFunction() called with 3
```

延迟计算通常和记忆化结合在一起.当出现相同的输入时,一个最优化的技术元素来提升程序的速度,是首先通过使用存储昂贵的函数调用和返回缓存的结果.

因为延迟计算没有副作用,(诸如产出异常和I/0的代码),命令式语言主要使用渴望的计算(也被称为严格计算),一个表达式尽可能快地执行.

更多关于延迟计算的记忆化

一个谷歌搜素将提供许多有关有没有记忆化延迟计算的讨论.一个例子是使用函数式编程最优化你的JavaScript代码.

## 函数式编程和闭环

一流的函数是和闭包的概念相关的,它是一个持久的范围,来处理本地变量即使在代码执行后也留下了块.

制作闭包
运作地,一个闭包是一个存储函数和它的环境的一条记录.环境将函数的每个自由变量(在本地使用的变量，但在封闭的范围中定义)与创建闭包时绑定变量名的值或引用进行映射。
这让这个函数通过这个闭包的值或应用的副本来读取这些获取到的变量,即使当这个函数是在它们的范围之外被调用的.




为了理清这个概念,Listing 8 展示了一个JavaScript脚本来介绍一个简单的闭环.这个脚本是基于下面的实例的.

Listing 8. 一个简单的闭包 (script6.js)

```js
function add(x)
{
   function partialAdd(y)
   {
      return y + x
   }
   return partialAdd
}
var add10 = add(10)
var add20 = add(20)
print(add10(5))
print(add20(5))
```


Listing 8 定义了一个名为 add() 一流的函数并且有一个参数x和一个内置的函数partialAdd().这个内置的partialAdd()能够读取x,因为x是在add()的词法范围.函数add()返回了一个包含一个partialAdd()的引用闭包和一个add()环境的副本.在其中x在一个特殊的调用add()中有被安排的值.

因为add()返回了要给函数的类型值,变量 add10 和 add20 也有这个函数类型.这个add10(5)调用后,返回15,因为这个调用partialAdd()在使用5来对参数y赋值

使用一个对partialAdd()使用一个保存x是10的环境.这个add20(5)调用返回25,因为现在使用了另一个保存x是20的环境,尽管它也把y赋值为5并且调用
partialAdd().

因此,进过add10()和add20()使用相同的函数partialAdd(),这个相关的环境是不同的,在两个不同的调用中,调用这个闭包将x绑定到不同的两个值上.
计算出两个不同的结果

像下面这样运行 Listing 8 的脚本

java RunScript script6.js

你将观察到下面的输出
```
15
25
```


## 函数式编程的currying

currying是一个翻译一个多参数的函数到一个单一参数函数的相同的序列.
例如,一个函数调用两个参数 x 和 y.currying转变这个函数到只使用x并且返回一个只调用y的函数.currying是一个相关的但不是相同的partial应用
它是调整一个函数参数数量一个过程,产出另一个更少参数数量的函数.


Listing 9 展示一个说明currying的JavaScript脚本

Listing 9. Currying  (script7.js)
```js
function multiply(x, y)
{
   return x * y
}

function curried_multiply(x)
{
   return function(y)
   {
      return x * y
   }
}
print(multiply(6, 7))
print(curried_multiply(6)(7))

var mul_by_4 = curried_multiply(4)
print(mul_by_4(2))
```

这个脚本展示一个非分化的两个参数的 multiply() 函数,后面跟着一个一流的函数 curried_multiply(),并且返回一个包含匿名函数的引用的闭包和一个有关 curried_multiply()的一个副本.在调用 curried_multiply()在其中 x 有被赋予的值.

剩下的脚本使用两个参数第一个调用multiply()并且打印出结果.它用下列两种方式接着调用curried_multiply()

curried_multiply(6)(7)会先执行curried_multiply(6).这个返回的闭包执行一个保存了x值为6的匿名函数,然后与7相乘.

var mul_by_4 = curried_multiply(4) 执行了 curried_multiply(4)并且安置了闭包到 mul_by_4.mul_by_4(2)执行了闭包值为4的匿名函数并且
通过了参数 2

像下列一样运行 Listing 9 的脚本
java RunScript script7.js

你将观察到下面的输出

```
42
42
8
```

为什么使用分化?
在Hugh Jackson 的《为什么分化有帮助》中说道"一小部分可以简介地被配置和轻松的复用". Quora的《分化在函数式编程中的优点是什么》中描述了
分化是一个廉价依赖注入的形式.这减轻了mapping/filtering/folding的过程.(和一些更高级的函数).这个问答也之处了分化帮助我们创建抽象函数.

## 第一部分总结
在这个教程中,你已经学习了一些函数式编程的基本.我们使用几个JavaScript的案例来学习了5个核心的函数式编程的技术.我们将探索更多的Java相关的代码内容.
此外,了解Java 8 的函数式编程强大,这个教程的下半场将帮助你开始用函数式地思考,通过一个面向对象的Java代码到一个相应的函数式代码.

学习更多的函数式编程
我推荐一本书 《Introduction to Functional Programming》 (Richard Bird and Philip Wadler, Prentice Hall International Series in Computing Science, 1992)。


# Java中的函数式代码

用函数式技术重写Java代码。从lambda，方法引用，方法接口和Stream API开始吧。

欢迎回到第二部分关于函数式编程的Java部分的教程。在第一部分，我使用了JavaScript的例子来使你从5个函数式编程技术开始。
存储的函数，高阶的函数，延迟技术，闭包和分化。展示了这些在JavaScript中的例子允许我们用简单的语法并集中在技术上。不用管Java复杂的语法。

在第二部分，我们将使用Java 8 代码来回顾这些技术。你将看到，这些代码是函数式的，但不是很容易理解和编写。我也将介绍给你一些新的函数式编程的特性。

它们在Java 8 中是集成到Java语言环境中的。也就是说，lambda，方法引用，函数接口和Streams API是被集成好的。

通过这个教程，我们将从第一部分回顾JavaScript和Java实例的不同。你将看到我用函数式语言的特性来更新一些Java 8 之前的例子。(例如使用
lambdas或者方法引用).最后,这教程包括了一个工具栏的设计帮助你练习函数式的思考.那里你将通过沾边一小部分的面向对象的Java代码来进入相同的函数式代码.


## 使用Java来进行函数式编程

许多开发者没有意识到,但在Java 8 之前写函数式代码是可能的.为了用Java得到一个丰满的效果.让我们快速重温一下Java 8 之前的函数式编程的特性.

自从你理解了它们,你将有会更加欣赏在Java 8 中介绍的新特性.(诸如lambda和函数引用)

Java对函数式编程有限的支持
即使在Java 8 中函数式编程有了提升,Java仍是命令式,面向对象的编程语言.这是一个缺少范围的类型和其它特性将使它更加函数化.Java也蹒跚地通过
主格打字来前进.这是一个每种类型必须命名的条约.不管这些限制,拥抱Java函数式特性的开发者仍然能从写更多简介的,可复用的,可读的代码中获益.

## Java 8 之前的函数式编程
接口,闭包,匿名的内部类是三个老的支持老版本Java的特性.
一名内部类让你通过函数性(通过接口来描述)到达一个方法.
函数接口是描述函数的接口
闭包让你在范围之外读取变量

在下面这节中,我们将回顾5个在第一部分介绍过的技术,但是使用Java语法.您将看到这些功能技术在Java 8之前是如何实现的。

在Java中写纯粹的函数

Listing 1 展示了案例的源码,DaysInMonth,这是使用匿名内部类和一个函数式接口书写的.这个应用展示了如何写一个纯粹的函数,它是在Java 8之前能够实现的.

Listing 1. 一个在Java中的纯粹的函数 (DaysInMonth.java)

```java
interface Function<T, R>
{
   R apply(T t);
}
public class DaysInMonth
{
   public static void main(String[] args)
   {
      Function<Integer, Integer> dim = new Function<Integer, Integer>()
      {
         @Override
         public Integer apply(Integer month)
         {
            return new Integer[] { 31, 28, 31, 30, 31, 30,
                                   31, 31, 30, 31, 30, 31 }[month];
         }
      };
      System.out.printf("April: %d%n", dim.apply(3));
      System.out.printf("August: %d%n", dim.apply(7));
   }
}
```

在Listing 1 中所描述的有一个单独参数的T和返回类型R的一般的函数接口.函数接口声明一个R apply(T T)方法，该方法将该函数应用于给定的参数

这个main()方法实例化一个匿名内部类,该内部类实现了这个Function接口.这个 apply()方法拆箱了 month 并且使用它来当作数组days-in-month的
索引.在这个索引中整型是被返回的.(我忽略了闰年)

main() 下一次通过调用apply()执行两次这个函数来返回4月和8月的天数.这是记录被依次地打印.
我们已经创建一个函数,一个纯粹的函数.重新调用一个只依赖于参数且没有额外的状态.这是没有副作用的.

如下编译 Listing 1

javac DaysInMonth.java
java DaysInMonth

你应该观察到下面的输出

```
April: 30
August: 31
```


## 在Java中高级的函数

下面,我们将看到高阶的函数,也被称为第一流的函数.记住高阶的函数接收函数参数或返回一个函数结果.Java使用一个方法来连接一个函数,它是在一个匿名内部类中被定义的.一个类的实例是通过的或着从另一个像高阶函数一样返回的Java方法.下面基于文件的代码碎片展示了一个通过函数来达到一个高阶的函数.

```java
File[] txtFiles = 
   new File(".").listFiles(new FileFilter() 
   {
	  @Override
	  public boolean accept(File pathname) 
	  {
		 return pathname.getAbsolutePath().endsWith("txt");
	  }
   });
```
 
这个代码碎片通过了一个基于java.io.FileFilter函数接口的函数来达到java.io.File 类的File[] listFiles(FileFilter filter)方法.告诉他
只返回txt拓展名的文件

Listing 2 展示了另一个方式在Java中使用高阶的函数.在这个例子中,这个代码通过一个比较器函数 ,高阶的函数sort()来取得升序的排列,第二个比较器函数取得一个降序的结果.

Listing 2. Java中的高阶函数 (Sort.java)

```java
public class Sort {
	public static void main(String[] args) {
		String[] innerplanets = { "Mercury", "Venus", "Earth", "Mars" };
		dump(innerplanets);
		sort(innerplanets, new Comparator<String>() {
			@Override
			public int compare(String e1, String e2) {
				return e1.compareTo(e2);
			}
		});
		dump(innerplanets);
		sort(innerplanets, new Comparator<String>() {
			@Override
			public int compare(String e1, String e2) {
				return e2.compareTo(e1);
			}
		});
		dump(innerplanets);
	}

	static <T> void dump(T[] array) {
		for (T element : array)
			System.out.println(element);
		System.out.println();
	}

	static <T> void sort(T[] array, Comparator<T> cmp) {
		for (int pass = 0; pass < array.length - 1; pass++)
			for (int i = array.length - 1; i > pass; i--)
				if (cmp.compare(array[i], array[pass]) < 0)
					swap(array, i, pass);
	}

	static <T> void swap(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}

```

Listing 2 引入了java.util.Comparator函数式接口,它描述了一个可以实现在任意的两个相同对象的比较.

代码中有意义的两个部分 sort()方法(它实现了冒泡排序算法)和sort()调用了main()方法.尽管 sort()远不是函数化的.它展示了一个高阶的函数,它接收一个比较器函数作为参数.
它通过调用compare()执行这个函数.这个函数中的两个实例在main()的sort()中通过.


像下面一样编译 Listing 2 
javac Sort.java
java Sort

你应该看到下面的输出
```
Mercury Venus Earth Mars 
Earth Mars Mercury Venus 
Venus Mercury Mars Earth
```


## Java中的延迟计算

延迟计算另一个对Java8 来讲不算新的函数式编程.这个技术将表达式的计算延迟到真正需要值的时候.
Java是饥饿式地计算一个绑定到变量的值.Java通过下面的特殊的语法支持延迟计算.

这个Boolean && 和 || 运算符,它将不计算它右边的值,如果当左边的参数是false(&&)或true(||).

这个 ?: 操作符,它计算一个布尔表达式并通过的表达式结果的真假来顺序地计算两者之一.

函数式编程鼓励以表达式为基准的编程,因此你将想避免使用很多的声明.例如,支持你通过ifThenElse()方法来替代 if-else.Listing 3 展示了第一个尝试.

Listing 3. Java中一个饥饿计算的例子

```java
public class EagerEval {
	public static void main(String[] args) {
		System.out.printf("%d%n", ifThenElse(true, square(4), cube(4)));
		System.out.printf("%d%n", ifThenElse(false, square(4), cube(4)));
	}

	static int cube(int x) {
		System.out.println("in cube");
		return x * x * x;
	}

	static int ifThenElse(boolean predicate, int onTrue, int onFalse) {
		return (predicate) ? onTrue : onFalse;
	}

	static int square(int x) {
		System.out.println("in square");
		return x * x;
	}
}
```


Listing 3 定义了一个ifThenElse()方法,它有一个布尔类型的predicate和一组整型.当predicate是true时返回onTrue.否者返回onFasle.

Listing 3 也定义了一个 cube()和square()方法.相应地,这些方式 cube或者 square 一个返回一个整型的结果.

这个main() 方法调用了 ifThenElse(true, square(4), cube(4)) ,它应该只调用square(4),后面跟着ifThenElse(false, square(4), cube(4)),它应该只调用cube(4).

编译 Listing 3 

javac EagerEval.java
java EagerEval

应该观察到下面的输出

```
in square
in cube
16
in square
in cube
64
```

The output shows that each ifThenElse() call results in both methods executing, irrespective of the Boolean expression. We cannot leverage the ?: operator's laziness because Java eagerly evaluates the method's arguments.

Although there's no way to avoid eager evaluation of method arguments, we can still take advantage of ?:'s lazy evaluation to ensure that only square() or cube() is called. Listing 4 shows how.

Listing 4. An example of lazy evaluation in Java (LazyEval.java)

interface Function<T, R>
{
   R apply(T t);
}
public class LazyEval
{
   public static void main(String[] args)
   {
      Function<Integer, Integer> square = new Function<Integer, Integer>()
                                          {
                                             {
                                                System.out.println("SQUARE");
                                             }
                                             @Override
                                             public Integer apply(Integer t)
                                             {
                                                System.out.println("in square");
                                                return t * t;
                                             }
                                          };
      Function<Integer, Integer> cube = new Function<Integer, Integer>()
                                        {
                                           {
                                              System.out.println("CUBE");
                                           }
                                           @Override
                                           public Integer apply(Integer t)
                                           {
                                              System.out.println("in cube");
                                              return t * t * t;
                                           }
                                        };
      System.out.printf("%d%n", ifThenElse(true, square, cube, 4));
      System.out.printf("%d%n", ifThenElse(false, square, cube, 4));
   }
   static <T, R> R ifThenElse(boolean predicate, Function<T, R> onTrue,
                              Function<T, R> onFalse, T t)
   {
      return (predicate ? onTrue.apply(t) : onFalse.apply(t));
   }
}

Listing 4 turns ifThenElse() into a higher-order function by declaring this method to receive a pair of Function arguments. Although these arguments are eagerly evaluated when passed to ifThenElse(), the ?: operator causes only one of these functions to execute (via apply()). You can see both eager and lazy evaluation at work when you compile and run the application.

Compile Listing 4 as follows:


javac LazyEval.java
Run the resulting application as follows:


java LazyEval
You should observe the following output:


SQUARE
CUBE
in square
16
in cube
64
A lazy iterator and more
Neal Ford's "Laziness, Part 1: Exploring lazy evaluation in Java" provides more insight into lazy evaluation. The author presents a Java-based lazy iterator along with a couple of lazy-oriented Java frameworks.

Closures in Java
An anonymous inner class instance is associated with a closure. Outer scope variables must be declared final or (starting in Java 8) effectively final (meaning unmodified after initialization) in order to be accessible. Consider Listing 5.

Listing 5. An example of closures in Java (PartialAdd.java)

interface Function<T, R>
{
   R apply(T t);
}
public class PartialAdd
{
   Function<Integer, Integer> add(final int x)
   {
      Function<Integer, Integer> partialAdd = new Function<Integer, Integer>()
                                              {
                                                 @Override
                                                 public Integer apply(Integer y)
                                                 {
                                                    return y + x;
                                                 }
                                              };
      return partialAdd;
   }
   public static void main(String[] args)
   {
      PartialAdd pa = new PartialAdd();
      Function<Integer, Integer> add10 = pa.add(10);
      Function<Integer, Integer> add20 = pa.add(20);
      System.out.println(add10.apply(5));
      System.out.println(add20.apply(5));
   }
}
Listing 5 is the Java equivalent of the closure I previously presented in JavaScript (see Part 1, Listing 8). This code declares an add() higher-order function that returns a function for performing partial application of the add() function. The apply() method accesses variable x in the outer scope of add(), which must be declared final prior to Java 8. The code behaves pretty much the same as the JavaScript equivalent.

Compile Listing 5 as follows:


javac PartialAdd.java
Run the resulting application as follows:


java PartialAdd
You should observe the following output:


15
25
Currying in Java
You might have noticed that the PartialAdd in Listing 5 demonstrates more than just closures. It also demonstrates currying, which is a way to translate a multi-argument function's evaluation into the evaluation of an equivalent sequence of single-argument functions. Both pa.add(10) and pa.add(20) in Listing 5 return a closure that records an operand (10 or 20, respectively) and a function that performs the addition--the second operand (5) is passed via add10.apply(5) or add20.apply(5).

Currying lets us evaluate function arguments one at a time, producing a new function with one less argument on each step. For example, in the PartialAdd application, we are currying the following function:


f(x, y) = x + y
We could apply both arguments at the same time, yielding the following:


f(10, 5) = 10 + 5
However, with currying, we apply only the first argument, yielding this:


f(10, y) = g(y) = 10 + y
We now have a single function, g, that takes only a single argument. This is the function that will be evaluated when we call the apply() method.

Partial application, not partial addition
The name PartialAdd stands for partial application of the add() function. It doesn't stand for partial addition. Currying is about performing partial application of a function. It's not about performing partial calculations.

You might be confused by my use of the phrase "partial application," especially because I stated in Part 1 that currying isn't the same as partial application, which is the process of fixing a number of arguments to a function, producing another function of smaller arity. With partial application, you can produce functions with more than one argument, but with currying, each function must have exactly one argument.

Listing 5 presents a small example of Java-based currying prior to Java 8. Now consider the CurriedCalc application in Listing 6.

Listing 6. Currying in Java code (CurriedCalc.java)

interface Function<T, R>
{
   R apply(T t);
}
public class CurriedCalc
{
   public static void main(String[] args)
   {
      System.out.println(calc(1).apply(2).apply(3).apply(4));
   }
   static Function<Integer, Function<Integer, Function<Integer, Integer>>> 
      calc(final Integer a)
   {
      return new Function<Integer, 
                          Function<Integer, Function<Integer, Integer>>>()
             {
                @Override
                public Function<Integer, Function<Integer, Integer>> 
                   apply(final Integer b)
                {
                   return new Function<Integer, Function<Integer, Integer>>()
                          {
                             @Override
                             public Function<Integer, Integer> 
                                apply(final Integer c)
                             {
                                return new Function<Integer, Integer>()
                                {
                                   @Override
                                   public Integer apply(Integer d)
                                   {
                                      return (a + b) * (c + d);
                                   }
                                };
                             }
                          };
                }
             };
   }
}
Listing 6 uses currying to evaluate the function f(a, b, c, d) = (a + b) * (c + d). Given expression calc(1).apply(2).apply(3).apply(4), this function is curried as follows:

f(1, b, c, d) = g(b, c, d) = (1 + b) * (c + d)
g(2, c, d) = h(c, d) = (1 + 2) * (c + d)
h(3, d) = i(d) = (1 + 2) * (3 + d)
i(4) = (1 + 2) * (3 + 4)
Compile Listing 6:


javac CurriedCalc.java
Run the resulting application:


java CurriedCalc
You should observe the following output:


21
Because currying is about performing partial application of a function, it doesn't matter in what order the arguments are applied. For example, instead of passing a to calc() and d to the most-nested apply() method (which performs the calculation), we could reverse these parameter names. This would result in d c b a instead of a b c d, but it would still achieve the same result of 21. (The source code for this tutorial includes the alternative version of CurriedCalc.)

Functional programming in Java 8
Functional programming before Java 8 isn't pretty. Too much code is required to create, pass a function to, and/or return a function from a first-class function. Prior versions of Java also lack predefined functional interfaces and first-class functions such as filter and map.

Java 8 reduces verbosity largely by introducing lambdas and method references to the Java language. It also offers predefined functional interfaces, and it makes filter, map, reduce, and other reusable first-class functions available via the Streams API.

We'll look at these improvements together in the next sections.

Writing lambdas in Java code
A lambda is an expression that describes a function by denoting an implementation of a functional interface. Here's an example:


() -> System.out.println("my first lambda")
From left to right, () identifies the lambda's formal parameter list (there are no parameters), -> signifies a lambda expression, and System.out.println("my first lambda") is the lambda's body (the code to be executed).

A lambda has a type, which is any functional interface for which the lambda is an implementation. One such type is java.lang.Runnable, because Runnable's void run() method also has an empty formal parameter list:


Runnable r = () -> System.out.println("my first lambda");
You can pass the lambda anywhere that a Runnable argument is required; for example, the Thread(Runnable r) constructor. Assuming that the previous assignment has occurred, you could pass r to this constructor, as follows:


new Thread(r);
Alternatively, you could pass the lambda directly to the constructor:


new Thread(() -> System.out.println("my first lambda"));
This is definitely more compact than the pre-Java 8 version:


new Thread(new Runnable()
           {
              @Override
              public void run()
              {
                 System.out.println("my first lambda");
              }
           });
A lambda-based file filter
My previous demonstration of higher-order functions presented a file filter based on an anonymous inner class. Here's the lambda-based equivalent:


File[] txtFiles = new File(".").listFiles(p -> p.getAbsolutePath().endsWith("txt"));
Return statements in lambda expressions
In Part 1, I mentioned that functional programming languages work with expressions as opposed to statements. Prior to Java 8, you could largely eliminate statements in functional programming, but you couldn't eliminate the return statement.

The above code fragment shows that a lambda doesn't require a return statement to return a value (a Boolean true/false value, in this case): you just specify the expression without return [and add] a semicolon. However, for multi-statement lambdas, you'll still need the return statement. In these cases you must place the lambda's body between braces as follows (don't forget the semicolon to terminate the statement):


File[] txtFiles = new File(".").listFiles(p -> { return p.getAbsolutePath().endsWith("txt"); });
Lambdas with functional interfaces
I have two more examples to illustrate the conciseness of lambdas. First, let's revisit the main() method from the Sort application shown in Listing 2:


public static void main(String[] args)
{
   String[] innerplanets = { "Mercury", "Venus", "Earth", "Mars" };
   dump(innerplanets);
   sort(innerplanets, (e1, e2) -> e1.compareTo(e2));
   dump(innerplanets);
   sort(innerplanets, (e1, e2) -> e2.compareTo(e1));
   dump(innerplanets);
}
We can also update the calc() method from the CurriedCalc application shown in Listing 6:


static Function<Integer, Function<Integer, Function<Integer, Integer>>> 
   calc(Integer a)
{
   return b -> c -> d -> (a + b) * (c + d);
}
Runnable, FileFilter, and Comparator are examples of functional interfaces, which describe functions. Java 8 formalized this concept by requiring a functional interface to be annotated with the java.lang.FunctionalInterface annotation type, as in @FunctionalInterface. An interface that is annotated with this type must declare exactly one abstract method.

You can use Java's pre-defined functional interfaces (discussed later), or you can easily specify your own, as follows:


@FunctionalInterface
interface Function<T, R>
{
   R apply(T t);
}
You might then use this functional interface as shown here:


public static void main(String[] args)
{
   System.out.println(getValue(t -> (int) (Math.random() * t), 10));
   System.out.println(getValue(x -> x * x, 20));
}
static Integer getValue(Function<Integer, Integer> f, int x)
{
   return f.apply(x);
}
New to lambdas?
If you're new to lambdas, you might need more background in order to understand these examples. In that case, check out my further introduction to lambdas and functional interfaces in "Java 101: The essential Java language features tour, Part 6." You'll also find numerous helpful blog posts on this topic. One example is "Functional programming with Java 8 functions," in which author Edwin Dalorzo shows how to use lambda expressions and anonymous functions in Java 8.

Architecture of a lambda
Every lambda is ultimately an instance of some class that's generated behind the scenes. Explore the following resources to learn more about lambda architecture:

"How lambdas and anonymous inner classes work" (Martin Farrell, DZone)
"Lambdas in Java: A peek under the hood" (Brian Goetz, GOTO)
"Why are Java 8 lambdas invoked using invokedynamic?" (Stack Overflow)
I think you'll find Java Language Architect Brian Goetz's video presentation of what's going on under the hood with lambdas especially fascinating.

Method references in Java
Some lambdas only invoke an existing method. For example, the following lambda invokes System.out's void println(s) method on the lambda's single argument:


(String s) -> System.out.println(s)
The lambda presents (String s) as its formal parameter list and a code body whose System.out.println(s) expression prints s's value to the standard output stream.

To save keystrokes, you could replace the lambda with a method reference, which is a compact reference to an existing method. For example, you could replace the previous code fragment with the following:


System.out::println
Here, :: signifies that System.out's void println(String s) method is being referenced. The method reference results in much shorter code than we achieved with the previous lambda.

A method reference for Sort
I previously showed a lambda version of the Sort application from Listing 2. Here is that same code written with a method reference instead:


public static void main(String[] args)
{
   String[] innerplanets = { "Mercury", "Venus", "Earth", "Mars" };
   dump(innerplanets);
   sort(innerplanets, String::compareTo);
   dump(innerplanets);
   sort(innerplanets, Comparator.comparing(String::toString).reversed());
   dump(innerplanets);
}

The String::compareTo method reference version is shorter than the lambda version of (e1, e2) -> e1.compareTo(e2). Note, however, that a longer expression is required to create an equivalent reverse-order sort, which also includes a method reference: String::toString. Instead of specifying String::toString, I could have specified the equivalent s -> s.toString() lambda.

More about method references
There's much more to method references than I could cover in a limited space. To learn more, check out my introduction to writing method references for static methods, non-static methods, and constructors in "Java 101: The essential Java language features tour, Part 7."

Predefined functional interfaces
Java 8 introduced predefined functional interfaces (java.util.function) so that developers don't have create our own functional interfaces for common tasks. Here are a few examples:

The Consumer<T> functional interface represents an operation that accepts a single input argument and returns no result. Its void accept(T t) method performs this operation on argument t.
The Function<T, R> functional interface represents a function that accepts one argument and returns a result. Its R apply(T t) method applies this function to argument t and returns the result.
The Predicate<T> functional interface represents a predicate (Boolean-valued function) of one argument. Its boolean test(T t) method evaluates this predicate on argument t and returns true or false.
The Supplier<T> functional interface represents a supplier of results. Its T get() method receives no argument(s) but returns a result.
The DaysInMonth application in Listing 1 revealed a complete Function interface. Starting with Java 8, you can remove this interface and import the identical predefined Function interface.

More about predefined functional interfaces
"Java 101: The essential Java language features tour, Part 6" provides examples of the Consumer and Predicate functional interfaces. Check out the blog post "Java 8 -- Lazy argument evaluation" to discover an interesting use for Supplier.

Additionally, while the predefined functional interfaces are useful, they also present some issues. Blogger Pierre-Yves Saumont explains why.

Functional APIs: Streams
Java 8 introduced the Streams API to facilitate sequential and parallel processing of data items. This API is based on streams, where a stream is a sequence of elements originating from a source and supporting sequential and parallel aggregate operations. A source stores elements (such as a collection) or generates elements (such as a random number generator). An aggregate is a result calculated from multiple input values.

A stream supports intermediate and terminal operations. An intermediate operation returns a new stream, whereas a terminal operation consumes the stream. Operations are connected into a pipeline (via method chaining). The pipeline starts with a source, which is followed by zero or more intermediate operations, and ends with a terminal operation.

Streams is an example of a functional API. It offers filter, map, reduce, and other reusable first-class functions. I briefly demonstrated this API in the Employees application shown in Part 1, Listing 1. Listing 7 offers another example.

Listing 7. Functional programming with Streams (StreamFP.java)

import java.util.Random;
import java.util.stream.IntStream;
public class StreamFP
{
   public static void main(String[] args)
   {
      new Random().ints(0, 11).limit(10).filter(x -> x % 2 == 0)
                  .forEach(System.out::println);
      System.out.println();
      String[] cities = 
      {
         "New York",
         "London",
         "Paris",
         "Berlin",
         "BrasÌlia",
         "Tokyo",
         "Beijing",
         "Jerusalem",
         "Cairo",
         "Riyadh",
         "Moscow"
      };
      IntStream.range(0, 11).mapToObj(i -> cities[i])
               .forEach(System.out::println);
      System.out.println();
      System.out.println(IntStream.range(0, 10).reduce(0, (x, y) -> x + y));
      System.out.println(IntStream.range(0, 10).reduce(0, Integer::sum));
   }
}
The main() method first creates a stream of pseudorandom integers starting at 0 and ending at 10. The stream is limited to exactly 10 integers. The filter() first-class function receives a lambda as its predicate argument. The predicate removes odd integers from the stream. Finally, the forEach() first-class function prints each even integer to the standard output via the System.out::println method reference.

The main() method next creates an integer stream that produces a sequential range of integers starting at 0 and ending at 10. The mapToObj() first-class function receives a lambda that maps an integer to the equivalent string at the integer index in the cities array. The city name is then sent to the standard output via the forEach() first-class function and its System.out::println method reference.

Lastly, main() demonstrates the reduce() first-class function. An integer stream that produces the same range of integers as in the previous example is reduced to a sum of their values, which is subsequently output.

Identifying the intermediate and terminal operations
Each of limit(), filter(), range(), and mapToObj() are intermediate operations, whereas forEach() and reduce() are terminal operations.

Compile Listing 7 as follows:


javac StreamFP.java
Run the resulting application as follows:


java StreamFP
I observed the following output from one run:


0
2
10
6
0
8
10
New York
London
Paris
Berlin
BrasÌlia
Tokyo
Beijing
Jerusalem
Cairo
Riyadh
Moscow
45
45
You might have expected 10 instead of 7 pseudorandom even integers (ranging from 0 through 10, thanks to range(0, 11)) to appear at the beginning of the output. After all, limit(10) seems to indicate that 10 integers will be output. However, this isn't the case. Although the limit(10) call results in a stream of exactly 10 integers, the filter(x -> x % 2 == 0) call results in odd integers being removed from the stream.

More about Streams
If you're unfamiliar with Streams, check out my tutorial introducing Java SE 8's new Streams API for more about this functional API.

In conclusion
Many Java developers won't pursue pure functional programming in a language like Haskell because it differs so greatly from the familiar imperative, object-oriented paradigm. Java 8's functional programming capabilities are designed to bridge that gap, enabling Java developers to write code that's easier to understand, maintain, and test. Functional code is also more reusable and more suitable for parallel processing in Java. With all of these incentives, there's really no reason not to incorporate Java's functional programming options into your Java code.

Sidebar: Write a functional Bubble Sort application
Functional thinking is a term coined by Neal Ford, which refers to the cognitive shift from the object-oriented paradigm to the functional programming paradigm. As you've seen in this tutorial, it's possible to learn a lot about functional programming by rewriting object-oriented code using functional techniques.

Cap off what you've learned so far by revisiting the Sort application from Listing 2. In this quick sidebar, I'll show you how to write a purely functional Bubble Sort, first using pre-Java 8 techniques, and then using Java 8's functional features.