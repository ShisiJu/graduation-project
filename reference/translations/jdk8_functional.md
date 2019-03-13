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


函数式编程偏好不变性，这意味着状态不能改变。命令式编程通常不是这样的。一个命令式的函数会与状态连接到一起。(例如一个Java实例变量)。
因为在这个实例中状态是可变的，所以称这个函数在不同时间中有相同的参数却可以有不同的返回值。

## 命令式和函数式编程的副作用

状态改变是命令式编程的一大副作用，阻碍了相关透明度。有许多其它的副作用也应该知道，尤其是当你决定是在你的程序中使用命令式还是函数式的风格。

在命令式编程中一个常见的副作用是当一个赋值语句通过改变存贮的值来改变变量。在函数式编程中的函数不支持这种变量赋值。因为一个变量初始值从不改变。函数式编程限制了这一个副作用。

另一个常见的副作用发生在修改基于抛出异常的函数的行为，这是一个对调用者可观察的交互。可以在Stack Overflow看到更多对《日益增长的异常副作用是什么？》


第三个常见的副作用是当I/O操作，输入不能读的文本，或输出不能写的文本。了解详情，可以在Stack Exchange中查看《在函数式编程中IO是如何造成副作用的？》。

去除掉副作用使程序更容易理解和预测计算的行为。这也是帮助代码更加适用于平行处理，通常能够提升应用的性能。然而这些副作用在函数式编程中，它们
通常少于命令式编程。使用函数式编程可以帮助你写容易理解方便测试和可读性的代码。

## 函数式编程的起源

函数式编程起源于lambda微积分，它是被Alonzo Church所提出的。另一个起源是组合逻辑，它被Moses Schönfinkel所提出，随后被Haskell Curry发展。


## 面向对象VS函数式编程

我创建了一个Java与命令式，面向对象和声明式相关的应用。函数式编程来写代码。学习下面代码，然后我将之处两个例子的不同之处。

Listing 1 Employees.java

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


Listing 1 发布了一个 Employees 的应用，创建了一些Employee对象，然后输出了一组年龄小于50的Employee对象。这个代码从面向对象和函数式编程
两方面实现。

printEmployee1() 方法展示了命令式，面向声明的方式。同样的，这个方法迭代了所有list中的employee，比较了employee的年龄，(如果年龄小于参数)
打印出employee的细节。

printEmployee2() 方法展示了声明式，面向表达的方式。在这个例子中使用了Streams API。替代了命令式的实现如何打印出employee，这个表达式指定了结果，并留下了如何用Java去实现的细节。

想想filter()作为函数式的相同的if声明，和forEach()做为相同for的声明。

你可以想下面一样编译 Listing 1
javac Employees。java

使用下面的命令来运行
java Employees

输出结果应该像这样
Sally Smith: 29
Bob Jone: 36
Sally Smith: 29
Bob Jone: 36

## 函数式编程实例

在下一节中，我们将探索5个在函数式编程汇总使用核心技术。

- 纯粹的函数
- 高阶函数
- 延迟实现
- 闭包
- 加脂法

这一节中的实例是用JavaScript写的，因为它简洁并且Java相关。将允许我们关注于技术。在Part2我们将重新回顾同样的技术用Java实现。


Listing 2 展示了RunScript的源代码，一个使用Java脚本API的Java应用来帮助运行JavaScript代码。Runscript将是下面实例运行的基础。



Listing 2 RunScript。java
 
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


这个 main() 方法在实例中首先验证单个的命令行参数被指定。然而，它展示惯例信息和终止应用。


设想这个参数的展示，main()实例化javax。script。ScriptEngineManager类，ScriptEngineManager是这个实体点进入Java脚本的API。

下一次，这个ScriptEngineManager对象的ScriptEngine的getEngineByName(String shortName)方法被调用遵循脚本引擎纠正希望的shortName的
值。Java 10支持这个 Nashorn引擎，它是遵循通过nashorn来getEngineByName()。这个返回的对象的类实现了javax。script。ScriptEngine接口。

ScriptEngine 声明了几个eval()方法来实现一个脚本。main()调用Object eval(Reader reader) 方面来读取从java。io。FileReader对象参数和
(假设java。io。IOException没有抛出)。之后实现这个脚本。
这个方法返回了任意脚本任意值。同样，当发生异常时，这个方法抛出javax。script。ScriptException。

编译 Listing 2 :

javac RunScript。java

我将向你展示如何在我展示的第一个脚本后运行这个应用。


## 纯粹的函数下的函数式编程

一个纯粹的函数是一个只依赖它的输入参数和没有额外声明的函数式编程函数。一个不纯粹的函数是一个函数式编程函数违反了这些需要。
因为纯粹的函数没有和外边的世界有交互。(除了调用其它纯粹的函数)。一个纯粹的函数总是在相同的参数下，返回相同的结果。纯粹的函数也没有明显的副作用。


一个纯粹的函数能执行I/O吗?
如果I/O是一个副作用，一个纯粹的函数能执行I/O吗?答案是可以的。Haskell使用monads来解决这个问题。

纯粹的函数VS不纯粹的函数
这个在Listing 3中JavaScript中不纯粹的calculatebonus()函数与纯粹的calculatebonus2() 对比。

Listing 3。 Comparing pure vs impure functions (script1。js)

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

calculatebonus()是非纯粹的，因为他读取了额外的limit变量。相反，calculatebonus2()是纯粹的，因为它遵守了纯粹的两个需求。
像下面一样运行 script1。js

java RunScript script1。js

Here's the output you should observe:

你应该看到下面的输出

```
17.400000000000002
17.400000000000002
```

支持calculatebonus2()是对calculatebonus(numSales)返回的重构。那calculatebonus2(numSales)还仍是纯粹的吗?
答案是不是。当一个纯粹的函数调用另一个不纯粹的函数，那么这个函数就变的不纯粹了


当两个纯粹的函数没有数据依赖时，它们可以被评估为在任意要求下不影响结果。使它们更加适合平行计算。这是它们函数式编程的好处之一。


更多关于非纯粹函数
不是所以的函数式编程函数需要纯粹。作为函数式编程:纯粹的函数式解释为它是可能的(有时是被期待的)来分离纯粹的，函数式的，外部应用值相关核心，命令式shell。


## 高阶函数式编程

一个高阶函数是像参数一样接受函数的数学的函数，返回一个函数给它的调用者或者都返回。一个例子是对不同的操作员的积分。d/dx，它返回f函数的派生。

优秀的函数是优秀的公民

近相关对数学的高阶函数感念来讲是一流的函数，它是函数式编程函数的采用其它函数式编程函数作为参数或者返回一个函数式编程的函数。
一流的函数是一流的公民，因为无论其它一流的程序实体，他们都可以出现。包括被视为标志的一个变量或者被通过的一个参数，又或者函数的返回。

The JavaScript in Listing 4 展示了通过匿名的比较函数来进行一流的排序函数

Listing 4  通过匿名的比较函数(script2。js)

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


在这个例子中，这个初始化的sort()调用了接受一个接收数组作为它第一个参数和一个匿名比较器的函数。
当调用时，这个匿名的比较器函数执行 return i - j; 来达到升序排序。通过反转i和j，第二个比较器函数达到了一个倒序排序。
第三个和第四个排序调用接收了匿名的排序函数是有点不同的，目的是进行正确的字符串值的比较。

运行 script2。js
java RunScript script2。js

输出应该是

```
-1 3 22 45 64 67 91 
91 67 64 45 22 3 -1
A E P Q X
X Q P E A
```
 
Filter和map
函数式编程语言通常提供了几个很有用的高阶函数。两个常见的例子是filter 和 map


一个filter处理一个list，在一些要求下产出一个包含在原始list中确切的元素中被认定为返回值为真的新list。

一个map应用在给出的函数中list里的每一个元素，以相同的顺序返回list的结果
JavaScript通过filter() and map()支持filtering 和 mapping高阶函数。

Listing 5 展示了这些函数筛选出奇数和映射数字到它们的立方。

Listing 5。 Filtering and mapping (script3。js)

```js
print([1, 2, 3, 4, 5, 6].filter(function(num) { return num % 2 == 0 }))
print('\n')
print([3, 13, 22].map(function(num) { return num * 3 }))
```

用下列方式运行  script3。js :

java RunScript script3。js

你应该得到的结果

```
2,4,6
9,39,66
```

另一个常见的高阶函数是减少，它更多地以折叠被广泛了解。这个还是减少一系列单独的值

Listing 6 使用的JavaScript的reduce() 高阶还是来减少一个数组里的单独值，它随后通过数组的长度来得到一个平均值。

Listing 6。 减少数组的数字来得到单独的数字 (script4。js)

```js
var numbers = [22, 30, 43]
print(numbers.reduce(function(acc, curval) { return acc + curval })/ numbers.length)
```

以下列方式运行Listing 6
java RunScript script4。js

应该观察到的内容

```
31.666666666666668
```
你可能认为这个filter， map， 和 reduce 高阶函数避免了需要if-else和各种循环声明，你是正确的。他们内置的实现处理了决策和声明。
一个高阶的函数使用了递归来实现迭代。一个递归函数调用了它自己，允许一个操作来重复，知道它达到预期。你也可以在你的代码里利用递归来进行迭代。

## 函数式编程的延迟计算

另一个重要的函数式编程的特性是延迟计算。(也被称为非严格求值)，它是尽可能慢进行表达式计算。延迟计算提供了几个好处，包括下面两个

- 昂贵的(费时的)计算可以延迟到它们真正被需要的时候
- 非绑定的集合是可能的。只要需要，他们就会一直交付元素

延迟计算是集成到Haskell的。他不会计算任何事情(包括在还是调用之前的一个函数的参数)，除非它真正地需要这么做。

Java的Streams API 利用了延迟计算。一个stream的中级操作(例如 filer())通常是懒加载的。它们不会做任何事情，直到终点的操作被执行。(例如 forEach())


尽管延迟计算是函数语言的重要部分，但是在许多命令式语言中提供了许多装入式的形式的支持。
例如，许多编程语言在上下文中支持 short-circuit计算。Boolean AND 和 OR 计算符合，它们是懒惰的，当左边的运算结果是false(AND)或true(OR)，就会拒绝计算它们右边的运算对象。

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

java RunScript script5。js

你应该观察到的结果
```
expensiveFunction() called with 2
expensiveFunction() called with 3
```

延迟计算通常和记忆化结合在一起。当出现相同的输入时，一个最优化的技术元素来提升程序的速度，是首先通过使用存储昂贵的函数调用和返回缓存的结果。

因为延迟计算没有副作用，(诸如产出异常和I/0的代码)，命令式语言主要使用渴望的计算(也被称为严格计算)，一个表达式尽可能快地执行。

更多关于延迟计算的记忆化

一个谷歌搜素将提供许多有关有没有记忆化延迟计算的讨论。一个例子是使用函数式编程最优化你的JavaScript代码。

## 函数式编程和闭环

一流的函数是和闭包的概念相关的，它是一个持久的范围，来处理本地变量即使在代码执行后也留下了块。

制作闭包
运作地，一个闭包是一个存储函数和它的环境的一条记录。环境将函数的每个自由变量(在本地使用的变量，但在封闭的范围中定义)与创建闭包时绑定变量名的值或引用进行映射。
这让这个函数通过这个闭包的值或应用的副本来读取这些获取到的变量，即使当这个函数是在它们的范围之外被调用的。

为了理清这个概念，Listing 8 展示了一个JavaScript脚本来介绍一个简单的闭环。这个脚本是基于下面的实例的。

Listing 8。 一个简单的闭包 (script6。js)

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


Listing 8 定义了一个名为 add() 一流的函数并且有一个参数x和一个内置的函数partialAdd()。这个内置的partialAdd()能够读取x，因为x是在add()的词法范围。函数add()返回了一个包含一个partialAdd()的引用闭包和一个add()环境的副本。在其中x在一个特殊的调用add()中有被安排的值。

因为add()返回了要给函数的类型值，变量 add10 和 add20 也有这个函数类型。这个add10(5)调用后，返回15，因为这个调用partialAdd()在使用5来对参数y赋值

使用一个对partialAdd()使用一个保存x是10的环境。这个add20(5)调用返回25，因为现在使用了另一个保存x是20的环境，尽管它也把y赋值为5并且调用
partialAdd()。

因此，进过add10()和add20()使用相同的函数partialAdd()，这个相关的环境是不同的，在两个不同的调用中，调用这个闭包将x绑定到不同的两个值上。
计算出两个不同的结果

像下面这样运行 Listing 8 的脚本

java RunScript script6。js

你将观察到下面的输出
```
15
25
```


## 函数式编程的分化

分化是一个翻译一个多参数的函数到一个单一参数函数的相同的序列。
例如，一个函数调用两个参数 x 和 y。分化转变这个函数到只使用x并且返回一个只调用y的函数。分化是一个相关的但不是相同的partial应用
它是调整一个函数参数数量一个过程，产出另一个更少参数数量的函数。


Listing 9 展示一个说明分化的JavaScript脚本

Listing 9  分化  (script7.js)
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

这个脚本展示一个非分化的两个参数的 multiply() 函数，后面跟着一个一流的函数 curried_multiply()，并且返回一个包含匿名函数的引用的闭包和一个有关 curried_multiply()的一个副本。在调用 curried_multiply()在其中 x 有被赋予的值。

剩下的脚本使用两个参数第一个调用multiply()并且打印出结果。它用下列两种方式接着调用curried_multiply()

curried_multiply(6)(7)会先执行curried_multiply(6)。这个返回的闭包执行一个保存了x值为6的匿名函数，然后与7相乘。

var mul_by_4 = curried_multiply(4) 执行了 curried_multiply(4)并且安置了闭包到 mul_by_4。mul_by_4(2)执行了闭包值为4的匿名函数并且
通过了参数 2

像下列一样运行 Listing 9 的脚本
java RunScript script7。js

你将观察到下面的输出

```
42
42
8
```

为什么使用分化?
在Hugh Jackson 的《为什么分化有帮助》中说道"一小部分可以简介地被配置和轻松的复用"。 Quora的《分化在函数式编程中的优点是什么》中描述了
分化是一个廉价依赖注入的形式。这减轻了mapping/filtering/folding的过程。(和一些更高级的函数)。这个问答也之处了分化帮助我们创建抽象函数。

## 第一部分总结
在这个教程中，你已经学习了一些函数式编程的基本。我们使用几个JavaScript的案例来学习了5个核心的函数式编程的技术。我们将探索更多的Java相关的代码内容。
此外，了解Java 8 的函数式编程强大，这个教程的下半场将帮助你开始用函数式地思考，通过一个面向对象的Java代码到一个相应的函数式代码。

学习更多的函数式编程
我推荐一本书 《Introduction to Functional Programming》 (Richard Bird and Philip Wadler， Prentice Hall International Series in Computing Science， 1992)。


# Java中的函数式代码

用函数式技术重写Java代码。从lambda，方法引用，方法接口和Stream API开始吧。

欢迎回到第二部分关于函数式编程的Java部分的教程。在第一部分，我使用了JavaScript的例子来使你从5个函数式编程技术开始。
存储的函数，高阶的函数，延迟技术，闭包和分化。展示了这些在JavaScript中的例子允许我们用简单的语法并集中在技术上。不用管Java复杂的语法。

在第二部分，我们将使用Java 8 代码来回顾这些技术。你将看到，这些代码是函数式的，但不是很容易理解和编写。我也将介绍给你一些新的函数式编程的特性。

它们在Java 8 中是集成到Java语言环境中的。也就是说，lambda，方法引用，函数接口和Streams API是被集成好的。

通过这个教程，我们将从第一部分回顾JavaScript和Java实例的不同。你将看到我用函数式语言的特性来更新一些Java 8 之前的例子。(例如使用
lambdas或者方法引用)。最后，这教程包括了一个工具栏的设计帮助你练习函数式的思考。那里你将通过沾边一小部分的面向对象的Java代码来进入相同的函数式代码。


## 使用Java来进行函数式编程

许多开发者没有意识到，但在Java 8 之前写函数式代码是可能的。为了用Java得到一个丰满的效果。让我们快速重温一下Java 8 之前的函数式编程的特性。

自从你理解了它们，你将有会更加欣赏在Java 8 中介绍的新特性。(诸如lambda和函数引用)

Java对函数式编程有限的支持
即使在Java 8 中函数式编程有了提升，Java仍是命令式，面向对象的编程语言。这是一个缺少范围的类型和其它特性将使它更加函数化。Java也蹒跚地通过
主格打字来前进。这是一个每种类型必须命名的条约。不管这些限制，拥抱Java函数式特性的开发者仍然能从写更多简介的，可复用的，可读的代码中获益。

## Java 8 之前的函数式编程
接口，闭包，匿名的内部类是三个老的支持老版本Java的特性。
一名内部类让你通过函数性(通过接口来描述)到达一个方法。
函数接口是描述函数的接口
闭包让你在范围之外读取变量

在下面这节中，我们将回顾5个在第一部分介绍过的技术，但是使用Java语法。您将看到这些功能技术在Java 8之前是如何实现的。

在Java中写纯粹的函数

Listing 1 展示了案例的源码，DaysInMonth，这是使用匿名内部类和一个函数式接口书写的。这个应用展示了如何写一个纯粹的函数，它是在Java 8之前能够实现的。

Listing 1。 一个在Java中的纯粹的函数 (DaysInMonth。java)

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

在Listing 1 中所描述的有一个单独参数的T和返回类型R的一般的函数接口。函数接口声明一个R apply(T T)方法，该方法将该函数应用于给定的参数

这个main()方法实例化一个匿名内部类，该内部类实现了这个Function接口。这个 apply()方法拆箱了 month 并且使用它来当作数组days-in-month的
索引。在这个索引中整型是被返回的。(我忽略了闰年)

main() 下一次通过调用apply()执行两次这个函数来返回4月和8月的天数。这是记录被依次地打印。
我们已经创建一个函数，一个纯粹的函数。重新调用一个只依赖于参数且没有额外的状态。这是没有副作用的。

如下编译 Listing 1

javac DaysInMonth。java
java DaysInMonth

你应该观察到下面的输出

```
April: 30
August: 31
```


## 在Java中高级的函数

下面，我们将看到高阶的函数，也被称为第一流的函数。记住高阶的函数接收函数参数或返回一个函数结果。Java使用一个方法来连接一个函数，它是在一个匿名内部类中被定义的。一个类的实例是通过的或着从另一个像高阶函数一样返回的Java方法。下面基于文件的代码碎片展示了一个通过函数来达到一个高阶的函数。

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
 
这个代码碎片通过了一个基于java。io。FileFilter函数接口的函数来达到java。io。File 类的File[] listFiles(FileFilter filter)方法。告诉他
只返回txt拓展名的文件

Listing 2 展示了另一个方式在Java中使用高阶的函数。在这个例子中，这个代码通过一个比较器函数 ，高阶的函数sort()来取得升序的排列，第二个比较器函数取得一个降序的结果。

Listing 2。 Java中的高阶函数 (Sort。java)

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

Listing 2 引入了java。util。Comparator函数式接口，它描述了一个可以实现在任意的两个相同对象的比较。

代码中有意义的两个部分 sort()方法(它实现了冒泡排序算法)和sort()调用了main()方法。尽管 sort()远不是函数化的。它展示了一个高阶的函数，它接收一个比较器函数作为参数。
它通过调用compare()执行这个函数。这个函数中的两个实例在main()的sort()中通过。


像下面一样编译 Listing 2 
javac Sort。java
java Sort

你应该看到下面的输出
```
Mercury Venus Earth Mars 
Earth Mars Mercury Venus 
Venus Mercury Mars Earth
```


## Java中的延迟计算

延迟计算另一个对Java8 来讲不算新的函数式编程。这个技术将表达式的计算延迟到真正需要值的时候。
Java是饥饿式地计算一个绑定到变量的值。Java通过下面的特殊的语法支持延迟计算。

这个Boolean && 和 || 运算符，它将不计算它右边的值，如果当左边的参数是false(&&)或true(||)。

这个 ?: 操作符，它计算一个布尔表达式并通过的表达式结果的真假来顺序地计算两者之一。

函数式编程鼓励以表达式为基准的编程，因此你将想避免使用很多的声明。例如，支持你通过ifThenElse()方法来替代 if-else。Listing 3 展示了第一个尝试。

Listing 3 Java中一个饥饿计算的例子

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


Listing 3 定义了一个ifThenElse()方法，它有一个布尔类型的predicate和一组整型。当predicate是true时返回onTrue。否者返回onFasle。

Listing 3 也定义了一个 cube()和square()方法。相应地，这些方式 cube或者 square 一个返回一个整型的结果。

这个main() 方法调用了 ifThenElse(true， square(4)， cube(4)) ，它应该只调用square(4)，后面跟着ifThenElse(false， square(4)， cube(4))，它应该只调用cube(4)。

编译 Listing 3 

javac EagerEval。java
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

这个输出显示了两种方法的执行中每一个ifThenElse()调用的结果，无关这个布尔表达式。
我们不能利用 ?:操作符的懒加载。因为Java是饥饿式地计算方法的参数。


尽管没有方式来避免函数参数的饥饿运算。我们任然能从?:的延迟计算中来确定square()和cube()只被调用一次。

Listing 4 展示了具体的实现

Listing 4。 Java中的延迟计算 (LazyEval。java)

```java
interface Function<T, R> {
	R apply(T t);
}

public class LazyEval {
	public static void main(String[] args) {
		Function<Integer, Integer> square = new Function<Integer, Integer>() {
			{
				System.out.println("SQUARE");
			}

			@Override
			public Integer apply(Integer t) {
				System.out.println("in square");
				return t * t;
			}
		};
		Function<Integer, Integer> cube = new Function<Integer, Integer>() {
			{
				System.out.println("CUBE");
			}

			@Override
			public Integer apply(Integer t) {
				System.out.println("in cube");
				return t * t * t;
			}
		};
		System.out.printf("%d%n", ifThenElse(true, square, cube, 4));
		System.out.printf("%d%n", ifThenElse(false, square, cube, 4));
	}

	static <T, R> R ifThenElse(boolean predicate, Function<T, R> onTrue, Function<T, R> onFalse, T t) {
		return (predicate ? onTrue.apply(t) : onFalse.apply(t));
	}
}
```


Listing 4 把 ifThenElse()转变成一个通过声明一个接收函数参数的高阶函数。当通过ifThenElse()时，尽管这些参数是饥饿式执行地。这个?:操作符
造成了这些函数唯一执行。(通过apply())。在你编译和运行这个应用是，你可以看到无论是饥饿式的还是延迟的计算。

如下编译 Listing 4

javac LazyEval。java
java LazyEval

你应该观察到的输出

```
SQUARE
CUBE
in square
16
in cube
64
```


一个延迟的迭代和其它

Neal Ford的怠惰 Part 1中探索了Java中的延迟计算。它提供了更多延迟计算的细节。这个作者展示了一个基于Java的延迟的迭代器。

## Java中的闭包

一个匿名的内部类实例是与闭包相关联的。外部范围变量必须声明为final，或者(从Java 8开始)有效地声明为final(即初始化后未修改)，以便能够访问。

看一下 Listing 5

Listing 5 在Java中的一个闭包实例 (PartialAdd。java)

```java
interface Function<T, R> {
	R apply(T t);
}

public class PartialAdd {
	Function<Integer, Integer> add(final int x) {
		Function<Integer, Integer> partialAdd = new Function<Integer, Integer>() {
			@Override
			public Integer apply(Integer y) {
				return y + x;
			}
		};
		return partialAdd;
	}

	public static void main(String[] args) {
		PartialAdd pa = new PartialAdd();
		Function<Integer, Integer> add10 = pa.add(10);
		Function<Integer, Integer> add20 = pa.add(20);
		System.out.println(add10.apply(5));
		System.out.println(add20.apply(5));
	}
}
```

Listing 5 是和我之前用JavaScript中演示的闭包是相同效果的。这个代码展示了一个高阶函数 add()。它为实现内部应用返回了一个add()函数。这个apply()方法读取了在外部的变量x。它在Java8之前必须被声明fianl。这块代码和在JavaScript中的几乎一样。


像下面这样编译 Listing 5
javac PartialAdd。java
java PartialAdd

你应该观察到下面的结果

```
15
25
```


## Java中的分化

你可能有注意到在 Listing 5 中声明的PartialAdd不仅仅是闭包。它也还展示了分化。它是把多参数函数的计算翻译成有相同顺序的单参数函数的一中方式。在Listing 5中 pa。add(10) 和 pa。add(20)返回了一个有参数(10或20)和函数的闭包。函数展示了第二个参数(5)是通过add10。apply(5)或add20。apply(5)的。


分化让我们计算函数参数1，每一步产生了一个有较少参数的新的函数。在PartialAdd应用中，我们可以分化下面的函数。
f(x， y) = x + y

我们可以同时应用两个参数
f(10， 5) = 10 + 5

而然，通过分化，我们可以只用第一个参数。如下

f(10， y) = g(y) = 10 + y

我们现在有一个单独的函数，g，它只有一个参数。当我们调用apply()时，这是将要执行的函数。

局部的应用，不是局部的添加
名为PartialAdd函数代表了add()函数局部的应用。他不是代表局部的添加。分化是关于执行一个函数局部应用。它不是关于局部的计算。

你可能会拒绝使用我使用的短语局部的应用。特别是，因为我开始在第一部分说分化不同于局部应用。它是将多个参数固定到一个函数的过程。产生出了另一个有更少参数的函数。通过局部应用，你能产出多于一个参数的函数，但是由于分化，每个函数必须有确切的一个参数。

Listing 5 展示了一个基于Java 8 之前的一个分化的例子。现在考虑一下这个在Listing 6的CurriedCalc应用

Listing 6。 Java代码中的分化 (CurriedCalc。java)

```java
public class CurriedCalc {
	public static void main(String[] args) {
		System.out.println(calc(1).apply(2).apply(3).apply(4));
	}

	static Function<Integer, Function<Integer, Function<Integer, Integer>>> calc(final Integer a) {
		return new Function<Integer, Function<Integer, Function<Integer, Integer>>>() {
			@Override
			public Function<Integer, Function<Integer, Integer>> apply(final Integer b) {
				return new Function<Integer, Function<Integer, Integer>>() {
					@Override
					public Function<Integer, Integer> apply(final Integer c) {
						return new Function<Integer, Integer>() {
							@Override
							public Integer apply(Integer d) {
								return (a + b) * (c + d);
							}
						};
					}
				};
			}
		};
	}
}
```

Listing 6 使用了分化来计算函数f(a， b， c， d) = (a + b) * (c + d)。给了的表达式 calc(1)。apply(2)。apply(3)。apply(4)，这个函数分化如下

```
f(1, b, c, d) = g(b, c, d) = (1 + b) * (c + d)
g(2, c, d) = h(c, d) = (1 + 2) * (c + d)
h(3, d) = i(d) = (1 + 2) * (3 + d)
i(4) = (1 + 2) * (3 + 4)
```

编译Listing 6:

javac CurriedCalc。java
java CurriedCalc

你应该观察到如下的输出

```
21
```

因为分化是关于执行局部函数的一个应用。参数应用的顺序并不重要。例如，通过calc()和d来替代最多内嵌的apply()方法。(它执行了计算)，我们能反转这些参数名。这将把 d c b a 替换为 a b c d，但是它将达到同样的结果 21。

## Java 8 里的函数式编程

函数式编程在Java 8 之前不是很好。需要写太多的代码，通过一个函数。从一个第一流的函数返回一个函数。之前的Java版本也缺乏预定义的函数接口和第一流的函数，诸如filter和map。

Java 8 通过发布lambdas和方法引用大量减少了冗余。它也提供了预定义的函数接口，并且通过Stream来使filter，map，reduce，和其它的课重复使用的一流的函数。

下一节，让我们一起看看这些实现。

使用Java代码写lambda

一个lambda是一个通过描述的表示一个函数接口实现的函数的表达式。这里有一个例子。
```
() -> System.out.println("my first lambda")
```

从左到右，() 确定了这个lambda的正式参数列表。(这里是无参数的)， -> 代表了一个lambad表达式。System。out。println("my first lambda") 是一个lambda的表达体。(将要执行的代码)

lambda有一个类型，它是lambda为其实现的任何函数接口。举例，java。lang。Runnable的接口，因为Runnable的 void run()方法也有一个空的参数列表。

```java
Runnable r = () -> System.out.println("my first lambda");
```

你可以在任何地方通过这个lambda，它需要必须要一个参数。例如，Thread(Runnable r)构造器。考虑到之前发生了赋值，你可以通过r到这个构造器。如下

```java
new Thread(r);
```

二者选一，你可以通过一个lambda直接到构造器

```java
new Thread(() -> System.out.println("my first lambda"));
```

这是被Java 8之前版本所兼容的

```java
new Thread(new Runnable()
{
  @Override
  public void run()
  {
	 System.out.println("my first lambda");
  }
});
```

一个基于lambda的文件过滤器

我之前的展示的高阶函数展示了一个基于匿名内部类的文件过滤器。这里使用lambda。

```java
File[] txtFiles = new File(".").listFiles(p -> p.getAbsolutePath().endsWith("txt"));
```

在lambda中返回一个声明

在第一部分中，我提到了函数式编程语言和表达式相反的是声明。Java 8 之前，你可能总是消除在函数式编程里的声明。但是你不能消除返回的声明。

之前的代码碎片展示了lambda不需要一个返回声明给返回值。(一个布尔值 true或false)。你会看到没有返回分号的表达式。然而，为了多声明的lambda，你将仍然需要返回声明。在下面的例子中，你一定替换下面的lambda的结构体，在下面的分支里面。(不要忘记给声明加上分号)

```java
File[] txtFiles = new File(".").listFiles(p -> { return p.getAbsolutePath().endsWith("txt"); });
```

### lambda和函数接口

我还有两个例子来说明lambda的简洁性。第一，我们从Listing 2 中的Sort重温一下main()方法。


```java
public static void main(String[] args)
{
   String[] innerplanets = { "Mercury", "Venus", "Earth", "Mars" };
   dump(innerplanets);
   sort(innerplanets, (e1, e2) -> e1.compareTo(e2));
   dump(innerplanets);
   sort(innerplanets, (e1, e2) -> e2.compareTo(e1));
   dump(innerplanets);
}
```

我们也可以更新 Listing 6 的 CurriedCalc中 calc()方法
```
static Function<Integer, Function<Integer, Function<Integer, Integer>>> 
   calc(Integer a)
{
   return b -> c -> d -> (a + b) * (c + d);
}
```

Runnable， FileFilter， and Comparator 都是函数接口的实例。它们展示了函数。Java 8 通过需要一个函数式接口正式化了这些概念。也可以通过java。lang。FunctionalInterface 注解类型 ( @FunctionalInterface)。一个接口被注解后必须声明只用一个抽象方法。

你可以使用，Java预定的函数接口。(稍后讨论)或者你可以自定义一个。如下

```java
@FunctionalInterface
interface Function<T, R>
{
   R apply(T t);
}
```

你可以使用下面的函数式接口

```java
public static void main(String[] args)
{
   System.out.println(getValue(t -> (int) (Math.random() * t), 10));
   System.out.println(getValue(x -> x * x, 20));
}
static Integer getValue(Function<Integer, Integer> f, int x)
{
   return f.apply(x);
}
```

现在轮到了lambda了

如果你是刚刚开始了解lambda，你可能需要了解一下更多关于这些例子的背景。在这种情况下，列出我很早之前对lambda和函数接口的介绍。在Java 101: java语言的基本特性中你可以得到更多有帮助的博客。其中有个是使用Java8 来进行函数编程。作者是 Edwin Dalorzo 展示了如何Java 8中使用lambad表达式和匿名的函数。

lambda的结构

每个lambda最终都是幕后生成的某个类的实例。探索下面的资源来学习lambda结构。

"How lambdas and anonymous inner classes work" (Martin Farrell， DZone)
"Lambdas in Java: A peek under the hood" (Brian Goetz， GOTO)
"Why are Java 8 lambdas invoked using invokedynamic?" (Stack Overflow)

我想你将找找Java语言结构Brian Goetz的视频。它格外生动地展示了与下面的lambda。

Some lambdas only invoke an existing method。 For example， the following lambda invokes System。out's void println(s) method on the lambda's single argument:

(String s) -> System。out。println(s)
The lambda presents (String s) as its formal parameter list and a code body whose System。out。println(s) expression prints s's value to the standard output stream。


Java中的方法引用

一些lambda只调用了一个存在的方法。例如，下面的lambda在lambda的单个参数中调用System。out的 void println(s)方法。


为了保存使用键盘击键，你可以使用lambda来替换应用参数。它是一个对存在方法的紧凑的应用。例如，你可以如下替换之前的代码碎片。

```java
System.out::println
```

这里 :: 表示 System。out的 void println(String s)方法被引用了。这个方法引用用较比我们之前用lambda实现执行了更少的代码。

Sort一个方法引用

我之前展示在Listing 2 中lambda版本的。这里是用方法引用替代了。

```
public static void main(String[] args)
{
   String[] innerplanets = { "Mercury", "Venus", "Earth", "Mars" };
   dump(innerplanets);
   sort(innerplanets, String::compareTo);
   dump(innerplanets);
   sort(innerplanets, Comparator.comparing(String::toString).reversed());
   dump(innerplanets);
}
```


这个 String::compareTo 方法引用版本要比lambda版本的 (e1， e2) -> e1。compareTo(e2) 更短。而然注意，较长的表达式需要创建一个同等的反序sort，他将包括一个方法引用 String::toString。替换指定的 String::toString，我可能有指定同样效果的s -> s。toString() lambda表达式。


更多关于方法引用

有许多比我在有限篇幅中覆盖的关于函数引用的内容。如果想了解更多可以看一下我写的关于静态方法，非静态方法和构造器的文章。


预定义的函数引用

Java8引用了预定义的函数接口 (java。util。function)，因此开发者不用为了常见的任务，而自己创建我们的函数接口。这里有几个例子。

Consumer<T> 是一个代表了接受一个单独输入参数和无返回结果的函数接口。它的 void accept(T t) 方法在参数t中执行了。

Function<T， R> 是一个代表了接受一个参数和返回一个结果的方法。它的R apply(T t) 方法使用了参数t和返回result的函数。

Predicate<T>是一个展示一个预定义一个参数的(布尔值的函数)的函数式接口。它的boolean test(T t) method 计算了它预定义参数t和返回true或false。

The Supplier<T> 是一个展示了一个supplier的结果。它的T get()方法接受没有参数，但却返回一个result。

Listing 1的DaysInMonth应用中展示了一个完整的函数接口。从Java 8 开始，你可以移除这个接口并导入确定的预定义函数接口。

More about predefined functional interfaces
"Java 101: The essential Java language features tour， Part 6" provides examples of the Consumer and Predicate functional interfaces。 Check out the blog post "Java 8 -- Lazy argument evaluation" to discover an interesting use for Supplier。

Additionally， while the predefined functional interfaces are useful， they also present some issues。 Blogger Pierre-Yves Saumont explains why。

更多关于预定义接口

可以参考在博文《Java 8 懒惰参数执行》中更多Supplier的有趣使用。

函数式 API:Stream

Java 8 引入了Streams API 来帮助顺序的和平行的进行数据处理。它的API基于流，其中流是源元素的序列，支持顺序和并行聚合操作。一个元存储了元素(诸如一个集合)或者普通的元素(诸如一个随机数生成器)。一个聚集是一个从多输入值的结果。

一个流支持中间的和终止的操作。一个中间的操作返回一个新的stream。然而，一个终止操作产出了stream。操作是被连接到管道的(通过方法链)。这个管道从源开始，它是后面跟着零或则更多中间的操作，并以一个终止的操作为结束。

Stream 是一个函数式API的一个例子。它提供了 filter， map， reduce和其它可重复使用的第一流函数。我简短地Employees在展示一下这个API。(在Part 1， Listing 1。 Listing 7 有其它例子)

Listing 7。 Stream函数式编程 (StreamFP。java)

```java
import java.util.Random;
import java.util.stream.IntStream;

public class StreamFP {
	public static void main(String[] args) {
		new Random().ints(0, 11).limit(10).filter(x -> x % 2 == 0).forEach(System.out::println);
		System.out.println();
		String[] cities = { "New York", "London", "Paris", "Berlin", "BrasÌlia", "Tokyo", "Beijing", "Jerusalem",
				"Cairo", "Riyadh", "Moscow" };
		IntStream.range(0, 11).mapToObj(i -> cities[i]).forEach(System.out::println);
		System.out.println();
		System.out.println(IntStream.range(0, 10).reduce(0, (x, y) -> x + y));
		System.out.println(IntStream.range(0, 10).reduce(0, Integer::sum));
	}
}
```

这个main()方法第一次创建了一个伪随机数的一个stream，从0开始以10结束。这个stram是被限定在10的整数。这个filter()一流的函数接受一个lambda作为它的预定义参数。这个预定义函数移除了stream中的奇数。最终，这个forEach() 一流的函数通过标准的System。out::println方法引用输出打印出每一个整型。

这个main()方法下一次创建一个整型stream，它产生了一个有序的从0开始到10结束的整型范围。这个mapToObj() 一流的函数接受了一个lambda来映射在城市数组中的与整型索引相同的字符串。城市名是之后通过forEach()函数和它的System。out::println 方法引用发送到标准输出的。

最后，main()展示了这个reduce() 一流的函数。一个整型stream产出了相同范围的整型在之前的例子中减少它们中的总和，顺序地输出。

确定中间的和终止的操作

每一个limit()， filter()， range()和 mapToObj() 是中间的操作，然而forEach() 和 reduce()是最终的操作

如下编译Listing 7 

javac StreamFP。java 

java StreamFP


你将观察到如下的输出

```
0 2 10 6 0 8 10
New York London Paris Berlin BrasÌlia Tokyo
Beijing Jerusalem Cairo Riyadh Moscow
45 45
```

你可能期待在输出开始时10替代7伪随机数偶数整数(范围从0到10，由于范围是(0，11))。总之，limit(10)看起来是指示10整数将被输出。然而，这不是那种情况。尽管limit(10)在stream中调用有10个整数，filter(x -> x % 2 == 0)调用造成奇数被移除了stream。

更多关于Stream

如果你不熟悉 Stream ，可以查看我的Java SE 8's new Streams API 教程。有更多关于函数式的API。

总结

许多Java开发者在类似Haskell的语言不会暂停去使用纯粹的函数式编程，因为它与熟悉的命令式，面向对象的编程方式大有不同。Java 8的函数式编程的能力被设计成缝隙的桥。能够让Java开发者写出更加容易理解，维护和测试的代码。函数式编程的代码在Java中同样是可重复利用和更加适合平行处理的。在这些诱因下，没有理由不去在你的Java代码中使用Java的函数式编程。

