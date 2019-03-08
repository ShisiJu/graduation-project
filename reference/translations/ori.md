Functional programming for Java developers, Part 1
Optimize your Java code with these five functional programming techniques

Java 8 introduced Java developers to functional programming with lambda expressions. This Java release effectively notified developers that it's no longer sufficient to think about Java programming only from the imperative, object-oriented perspective. A Java developer must also be able to think and code using the declarative functional paradigm.

This tutorial presents the basics of functional programming. I'll start with terminology, then we'll dig into functional programming concepts. I will conclude by introducing you to five functional programming techniques. Code examples in these sections will get you started with pure functions, higher-order functions, lazy evaluation, closures, and currying.

Functional programming is on the rise
The Institute of Electrical and Electronics Engineers (IEEE) included functional programming languages in its top 25 programming languages for 2018, and Google Trends currently ranks functional programming as more popular than object-oriented programming.

Clearly, functional programming cannot be ignored, but why is it becoming more popular? Among other things, functional programming makes it easier to verify program correctness. It also simplifies the creation of concurrent programs. Concurrency (or parallel processing) is vital for improving application performance.

What is functional programming?
Computers typically implement the Von Neumann architecture, which is a widely-used computer architecture based on a 1945 description by the mathematician and physicist John von Neumann (and others). This architecture is biased toward imperative programming, which is a programming paradigm that uses statements to change a program's state. C, C++, and Java are all imperative programming languages.

In 1977, distinguished computer scientist John Backus (notable for his work on FORTRAN), gave a lecture titled "Can programming be liberated from the von Neumann style?." Backus asserted that the Von Neumann architecture and its associated imperative languages are fundamentally flawed, and presented a functional-level programming language (FP) as a solution.

Clarifying Backus
Because the Backus lecture was presented several decades ago, some of its ideas might be hard to grasp. Blogger Tomasz Jaskuła adds clarity and footnotes in his blog post from January 2018.

Functional programming concepts and terminology
Functional programming is a programming style in which computations are codified as functional programming functions. These are mathematical function-like constructs (e.g., lambda functions) that are evaluated in expression contexts.

Functional programming languages are declarative, meaning that a computation's logic is expressed without describing its control flow. In declarative programming, there are no statements. Instead, programmers use expressions to tell the computer what needs to be done, but not how to accomplish the task. If you are familiar with SQL or regular expressions, then you have some experience with the declarative style; both use expressions to describe what needs to be done, rather than using statements to describe how to do it.

A computation in functional programming is described by functions that are evaluated in expression contexts. These functions are not the same as the functions used in imperative programming, such as a Java method that returns a value. Instead, a functional programming function is like a mathematical function, which produces an output that typically depends only on its arguments. Each time a functional programming function is called with the same arguments, the same result is achieved. Functions in functional programming are said to exhibit referential transparency. This means you could replace a function call with its resulting value without changing the computation's meaning.

[ Learn Java from beginning concepts to advanced design patterns in this comprehensive 12-part course! ]
Functional programming favors immutability, which means the state cannot change. This is typically not the case in imperative programming, where an imperative function might be associated with state (such as a Java instance variable). Calling this function at different times with the same arguments might result in different return values because in this case state is mutable, meaning it changes.

Side effects in imperative and functional programming
State changes are a side effect of imperative programming, preventing referential transparency. There are many other side effects worth knowing about, especially as you evaluate whether to use the imperative or functional style in your programs.

One common side effect in imperative programming is when an assignment statement mutates a variable by changing its stored value. Functions in functional programming don't support variable assignments. Because a variable's initial value never changes, functional programming eliminates this side effect.

Another common side effect happens when modifying an imperative function's behavior based on a thrown exception, which is an observable interaction with the caller. For more information, see the Stack Overflow discussion, "Why is the raising of an exception a side effect?"

A third common side effect occurs when an I/O operation inputs text that cannot be unread, or outputs text that cannot be unwritten. See the Stack Exchange discussion "How can IO cause side effects in functional programming?" to learn more about this side effect.

Eliminating side effects makes it much easier to understand and predict computational behavior. It also helps make code more suitable for parallel processing, which often improves application performance. While there are side effects in functional programming, they are generally fewer than in imperative programming. Using functional programming can help you write code that's easier to understand, maintain, and test, and is also more reusable.

Origins (and originators) of functional programming
Functional programming originated in lambda calculus, which was introduced by Alonzo Church. Another origin is combinatory logic, which was introduced by Moses Schönfinkel and subsequently developed by Haskell Curry.

Object-oriented versus functional programming
I've created a Java application that contrasts the imperative, object-oriented and declarative, functional programming approaches to writing code. Study the code below and then I'll point out differences between the two examples.

Listing 1. Employees.java


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
Listing 1 reveals an Employees application that creates a few Employee objects, then prints a list of all employees who are younger than 50. This code demonstrates both object-oriented and functional programming styles.

The printEmployee1() method reveals the imperative, statement-oriented approach. As specified, this method iterates over a list of employees, compares each employee's age against an argument value, and (if the age is less than the argument), prints the employee's details.

The printEmployee2() method reveals the declarative, expression-oriented approach, in this case implemented with the Streams API. Instead of imperatively specifying how to print the employees (step-by-step), the expression specifies the desired outcome and leaves the details of how to do it to Java. Think of filter() as the functional equivalent of an if statement, and forEach() as functionally equivalent to the for statement.

You can compile Listing 1 as follows:



javac Employees.java
Use the following command to run the resulting application:



java Employees
The output should look something like this:



Sally Smith: 29

Bob Jone: 36

Sally Smith: 29

Bob Jone: 36
Functional programming examples
In the next sections, we'll explore five core techniques used in functional programming: pure functions, higher-order functions, lazy evaluation, closures, and currying. Examples in this section are coded in JavaScript because its simplicity, relative to Java, will allow us to focus on the techniques. In Part 2 we'll revisit these same techniques using Java code.

download
Download the code
Get code samples for the five functional programming techniques demonstrated in the next sections. Created by Jeff Friesen for JavaWorld.
Listing 2 presents the source code to RunScript, a Java application that uses Java's Scripting API to facilitate running JavaScript code. RunScript will be the base program for all of the forthcoming examples.

Listing 2. RunScript.java
 
import java.io.FileReader;

import java.io.IOException;

import javax.script.ScriptEngine;

import javax.script.ScriptEngineManager;

import javax.script.ScriptException;

import static java.lang.System.*;

public class RunScript

{

   public static void main(String[] args)

   {

      if (args.length != 1)

      {

         err.println("usage: java RunScript script");

         return;

      }

      ScriptEngineManager manager = 

         new ScriptEngineManager();

      ScriptEngine engine = 

         manager.getEngineByName("nashorn");

      try

      {

         engine.eval(new FileReader(args[0]));

      }

      catch (ScriptException se)

      {

         err.println(se.getMessage());

      }

      catch (IOException ioe)

      {

         err.println(ioe.getMessage());

      }      

   }

}
The main() method in this example first verifies that a single command-line argument (the name of a script file) has been specified. Otherwise, it displays usage information and terminates the application.

Assuming the presence of this argument, main() instantiates the javax.script.ScriptEngineManager class. ScriptEngineManager is the entry-point into Java's Scripting API.

Next, the ScriptEngineManager object's ScriptEngine getEngineByName(String shortName) method is called to obtain a script engine corresponding to the desired shortName value. Java 10 supports the Nashorn script engine, which is obtained by passing "nashorn" to getEngineByName(). The returned object's class implements the javax.script.ScriptEngine interface.

ScriptEngine declares several eval() methods for evaluating a script. main() invokes the Object eval(Reader reader) method to read the script from its java.io.FileReader object argument and (assuming that java.io.IOException isn't thrown) then evaluate the script. This method returns any script return value, which I ignore. Also, this method throws javax.script.ScriptException when an error occurs in the script.

Compile Listing 2 as follows:



javac RunScript.java
I'll show you how to run this application after I have presented the first script.

Functional programming with pure functions
A pure function is a functional programming function that depends only on its input arguments and no external state. An impure function is a functional programming function that violates either of these requirements. Because pure functions have no interaction with the outside world (apart from calling other pure functions), a pure function always returns the same result for the same arguments. Pure functions also have no observable side effects.

Can a pure function perform I/O?
If I/O is a side effect, can a pure function perform I/O? The answer is yes. Haskell uses monads to address this problem. See "Pure Functions and I/O" for more about pure functions and I/O.

Pure functions versus impure functions
The JavaScript in Listing 3 contrasts an impure calculatebonus() function with a pure calculatebonus2() function.

Listing 3. Comparing pure vs impure functions (script1.js)


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
calculatebonus() is impure because it accesses the external limit variable. In contrast, calculatebonus2() is pure because it obeys both requirements for purity. Run script1.js as follows:



java RunScript script1.js
Here's the output you should observe:



17.400000000000002

17.400000000000002

Suppose calculatebonus2() was refactored to return calculatebonus(numSales). Would calculatebonus2() still be pure? The answer is no: when a pure function invokes an impure function, the "pure function" becomes impure.

When no data dependency exists between pure functions, they can be evaluated in any order without affecting the outcome, making them suitable for parallel execution. This is one of functional programming's benefits.

More about impure functions
Not all functional programming functions need to be pure. As Functional Programming: Pure Functions explains, it is possible (and sometimes desirable) to "separate the pure, functional, value based core of your application from an outer, imperative shell."

Functional programming with higher-order functions
A higher-order function is a mathematical function that receives functions as arguments, returns a function to its caller, or both. One example is calculus's differential operator, d/dx, which returns the derivative of function f.

First-class functions are first-class citizens
Closely related to the mathematical higher-order function concept is the first-class function, which is a functional programming function that takes other functional programming functions as arguments and/or returns a functional programming function. First-class functions are first-class citizens because they can appear wherever other first-class program entities (e.g., numbers) can, including being assigned to a variable or being passed as an argument to or returned from a function.

The JavaScript in Listing 4 demonstrates passing anonymous comparison functions to a first-class sorting function.

Listing 4. Passing anonymous comparison functions (script2.js)


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
In this example, the initial sort() call receives an array as its first argument, followed by an anonymous comparison function. When called, the anonymous comparison function executes return i - j; to achieve an ascending sort. By reversing i and j, the second comparison function achieves a descending sort. The third and fourth sort() calls receive anonymous comparison functions that are slightly different in order to properly compare string values.

Run the script2.js example as follows:



java RunScript script2.js
Here's the expected output:



-1

3

22

45

64

67

91

91

67

64

45

22

3

-1

A

E

P

Q

X

X

Q

P

E

A
Filter and map
Functional programming languages typically provide several useful higher-order functions. Two common examples are filter and map.

A filter processes a list in some order to produce a new list containing exactly those elements of the original list for which a given predicate (think Boolean expression) returns true.
A map applies a given function to each element of a list, returning a list of results in the same order.
JavaScript supports filtering and mapping functionality via the filter() and map() higher-order functions. Listing 5 demonstrates these functions for filtering out odd numbers and mapping numbers to their cubes.

Listing 5. Filtering and mapping (script3.js)


print([1, 2, 3, 4, 5, 6].filter(function(num) { return num % 2 == 0 }))

print('\n')

print([3, 13, 22].map(function(num) { return num * 3 }))
Run the script3.js example as follows:



java RunScript script3.js
You should observe the following output:



2,4,6

9,39,66
Reduce
Another common higher-order function is reduce, which is more commonly known as a fold. This function reduces a list to a single value.

Listing 6 uses JavaScript's reduce() higher-order function to reduce an array of numbers to a single number, which is then divided by the array's length to obtain an average.

Listing 6. Reducing an array of numbers to a single number (script4.js)


var numbers = [22, 30, 43]

print(numbers.reduce(function(acc, curval) { return acc + curval })

      / numbers.length)
Run Listing 6's script (in script4.js) as follows:



java RunScript script4.js
You should observe the following output:



31.666666666666668
You might think that the filter, map, and reduce higher-order functions obviate the need for if-else and various looping statements, and you would be right. Their internal implementations take care of decisions and iteration.

A higher-order function uses recursion to achieve iteration. A recursive function invokes itself, allowing an operation to repeat until it reaches a base case. You can also leverage recursion to achieve iteration in your functional code.

Functional programming with lazy evaluation
Another important functional programming feature is lazy evaluation (also known as nonstrict evaluation), which is the deferral of expression evaluation for as long as possible. Lazy evaluation offers several benefits, including these two:

Expensive (timewise) calculations can be deferred until they're absolutely necessary.
Unbounded collections are possible. They'll keep delivering elements for as long as they're requested to do so.
Lazy evaluation is integral to Haskell. It won't calculate anything (including a function's arguments before the function is called) unless it's strictly necessary to do so.

Java's Streams API capitalizes on lazy evaluation. A stream's intermediate operations (e.g., filter()) are always lazy; they don't do anything until a terminal operation (e.g., forEach()) is executed.

Although lazy evaluation is an important part of functional languages, even many imperative languages provide builtin support for some forms of laziness. For example, most programming languages support short-circuit evaluation in the context of the Boolean AND and OR operators. These operators are lazy, refusing to evaluate their right-hand operands when the left-hand operand is false (AND) or true (OR).

Listing 7 is an example of lazy evaluation in a JavaScript script.

Listing 7. Lazy evaluation in JavaScript (script5.js)


var a = false && expensiveFunction("1")

var b = true && expensiveFunction("2")

var c = false || expensiveFunction("3")

var d = true || expensiveFunction("4")

function expensiveFunction(id)

{

   print("expensiveFunction() called with " + id)

}
Run the code in script5.js as follows:



java RunScript script5.js
You should observe the following output:



expensiveFunction() called with 2

expensiveFunction() called with 3
Lazy evaluation is often combined with memoization, an optimization technique used primarily to speed up computer programs by storing the results of expensive function calls and returning the cached result when the same inputs reoccur.

Because lazy evaluation doesn't work with side effects (such as code that produces exceptions and I/O), imperative languages mainly use eager evaluation (also known as strict evaluation), where an expression is evaluated as soon as it's bound to a variable.

More about lazy evaluation and memoization
A Google search will reveal many useful discussions of lazy evaluation with or without memoization. One example is "Optimizing your JavaScript with functional programming."

Functional programming with closures
First-class functions are associated with the concept of a closure, which is a persistent scope that holds onto local variables even after the code execution has left the block in which the local variables were defined.

Crafting closures
Operationally, a closure is a record that stores a function and its environment. The environment maps each of the function's free variables (variables used locally, but defined in an enclosing scope) with the value or reference to which the variable's name was bound when the closure was created. It lets the function access those captured variables through the closure's copies of their values or references, even when the function is invoked outside their scope.

To help clarify this concept, Listing 8 presents a JavaScript script that introduces a simple closure. The script is based on the example presented here.

Listing 8. A simple closure (script6.js)


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
Listing 8 defines a first-class function named add() with a parameter x and a nested function partialAdd(). The nested function partialAdd() has access to x because x is in add()'s lexical scope. Function add() returns a closure that contains a reference to partialAdd() and a copy of the environment around add(), in which x has the value assigned to it in a specific invocation of add().

Because add() returns a value of function type, variables add10 and add20 also have function type. The add10(5) invocation returns 15 because the invocation assigns 5 to parameter y in the call to partialAdd(), using the saved environment for partialAdd() where x is 10. The add20(5) invocation returns 25 because, although it also assigns 5 to y in the call to partialAdd(), it's now using another saved environment for partialAdd() where x is 20. Thus, while add10() and add20() use the same function partialAdd(), the associated environments differ and invoking the closures will bind x to two different values in the two invocations, evaluating the function to two different results.

Run Listing 8's script (in script6.js) as follows:



java RunScript script6.js
You should observe the following output:



15

25
Functional programming with currying
Currying is a way to translate the evaluation of a multi-argument function into the evaluation of an equivalent sequence of single-argument functions. For example, a function takes two arguments: x and y. Currying transforms the function into taking only x and returning a function that takes only y. Currying is related to but is not the same as partial application, which is the process of fixing a number of arguments to a function, producing another function of smaller arity.

Listing 9 presents a JavaScript script that demonstrates currying.

Listing 9. Currying in JavaScript (script7.js)


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
The script presents a noncurried two-argument multiply() function, followed by a first-class curried_multiply() function that receives multiplicand argument x and returns a closure containing a reference to an anonymous function (that receives multiplier argument y) and a copy of the environment around curried_multiply(), in which x has the value assigned to it in an invocation of curried_multiply().

The rest of the script first invokes multiply() with two arguments and prints the result. It then invokes curried_multiply() in two ways:

curried_multiply(6)(7) results in curried_multiply(6) executing first. The returned closure executes the anonymous function with the closure's saved x value 6 being multiplied by 7.
var mul_by_4 = curried_multiply(4) executes curried_multiply(4) and assigns the closure to mul_by_4. mul_by_4(2) executes the anonymous function with the closure's 4 value and the passed argument 2.
Run Listing 9's script (in script7.js) as follows:



java RunScript script7.js
You should observe the following output:



42

42

8
Why use currying?
In his blog post "Why curry helps," Hugh Jackson observes that "little pieces can be configured and reused with ease, without clutter." Quora's "What are the advantages of currying in functional programming?" describes currying as "a cheap form of dependency injection," that eases the process of mapping/filtering/folding (and higher order functions generally). This Q&A also notes that currying "helps us create abstract functions."

In conclusion
In this tutorial you've learned some basics of functional programming. We've used examples in JavaScript to study five core functional programming techniques, which we'll further explore using Java code in Part 2. In addition to touring Java 8's functional programming capabilities, the second half of this tutorial will help you begin to think functionally, by converting an example of object-oriented Java code to its functional equivalent.

Learn more about functional programming
I found the book Introduction to Functional Programming (Richard Bird and Philip Wadler, Prentice Hall International Series in Computing Science, 1992) helpful in learning the basics of functional programming.

>Rewrite object-oriented code using functional techniques. Get started with lambdas, method references, functional interfaces, and the Streams API in Java

Welcome back to this two-part tutorial introducing functional programming in a Java context. In Part 1 I used JavaScript examples to get you started with five functional programming techniques: pure functions, higher-order functions, lazy evaluation, closures, and currying. Presenting those examples in JavaScript allowed us to focus on the techniques in a simpler syntax, without getting into Java's more complex functional programming capabilities.

In Part 2 we'll revisit those techniques using Java code that pre-dates Java 8. As you'll see, this code is functional, but it's not easy to write or read. You'll also be introduced to the new functional programming features that were fully integrated into the Java language in Java 8; namely, lambdas, method references, functional interfaces, and the Streams API.

Throughout this tutorial we'll revisit examples from Part 1 to see how the JavaScript and Java examples compare. You'll also see what happens when I update some of the pre-Java 8 examples with functional language features like lambdas and method references. Finally, this tutorial includes a sidebar designed to help you practice functional thinking, which you'll do by transforming a piece of object-oriented Java code into its functional equivalent.

Functional programming with Java
Many developers don't realize it, but it was possible to write functional programs in Java before Java 8. In order to have a well-rounded view of functional programming in Java, let's quickly review functional programming features that predate Java 8. Once you've got those down, you'll likely have more appreciation for how new features introduced in Java 8 (like lambdas and functional interfaces) have simplified Java's approach to functional programming.

Limits of Java's support for functional programming
Even with functional programming improvements in Java 8, Java remains an imperative, object-oriented programming language. It's missing range types and other features that would make it more functional. Java is also hobbled by nominative typing, which is the stipulation that every type must have a name. Despite these limitations, developers who embrace Java's functional features still benefit from being able to write more concise, reusable, and readable code.

Functional programming before Java 8
Anonymous inner classes along with interfaces and closures are three older features that support functional programming in older versions of Java:

Anonymous inner classes let you pass functionality (described by interfaces) to methods.
Functional interfaces are interfaces that describe a function.
Closures let you access variables in their outer scopes.
In the sections that follow we'll revisit the five techniques introduced in Part 1, but using Java syntax. You'll see how each of these functional techniques was possible prior to Java 8.

Writing pure functions in Java
Listing 1 presents the source code to an example application, DaysInMonth, that is written using an an anonymous inner class and a functional interface. This application demonstrates how to write a pure function, which was achievable in Java long before Java 8.

[ Learn Java from beginning concepts to advanced design patterns in this comprehensive 12-part course! ]
Listing 1. A pure function in Java (DaysInMonth.java)

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
The generic Function interface in Listing 1 describes a function with a single parameter of type T and a return type of type R. The Function interface declares an R apply(T t) method that applies this function to the given argument.

The main() method instantiates an anonymous inner class that implements the Function interface. The apply() method unboxes month and uses it to index an array of days-in-month integers. The integer at this index is returned. (I'm ignoring leap years for simplicity.)

main() next executes this function twice by invoking apply() to return the day counts for the months of April and August. These counts are subsequently printed.

We've managed to create a function, and a pure function at that! Recall that a pure function depends only on its arguments and no external state. There are no side effects.

Compile Listing 1 as follows:


javac DaysInMonth.java
Run the resulting application as follows:


java DaysInMonth
You should observe the following output:


April: 30
August: 31
Writing higher-order functions in Java
Next, we'll look at higher-order functions, also known as first-class functions. Remember that a higher-order function receives function arguments and/or returns a function result. Java associates a function with a method, which is defined in an anonymous inner class. An instance of this class is passed to or returned from another Java method that serves as the higher-order function. The following file-oriented code fragment demonstrates passing a function to a higher-order function:


File[] txtFiles = 
   new File(".").listFiles(new FileFilter() 
                           {
                              @Override
                              public boolean accept(File pathname) 
                              {
                                 return pathname.getAbsolutePath().endsWith("txt");
                              }
                           });
 
This code fragment passes a function based on the java.io.FileFilter functional interface to the java.io.File class's File[] listFiles(FileFilter filter) method, telling it to return only those files with txt extensions.

Listing 2 shows another way to work with higher-order functions in Java. In this case, the code passes a comparator function to a sort() higher-order function for an ascending-order sort, and a second comparator function to sort() for a descending-order sort.

Listing 2. A higher-order function in Java (Sort.java)

import java.util.Comparator;
public class Sort
{
   public static void main(String[] args)
   {
      String[] innerplanets = { "Mercury", "Venus", "Earth", "Mars" };
      dump(innerplanets);
      sort(innerplanets, new Comparator<String>()
                         {
                            @Override
                            public int compare(String e1, String e2)
                            {
                               return e1.compareTo(e2);
                            }
                         });
      dump(innerplanets);
      sort(innerplanets, new Comparator<String>()
                         {
                            @Override
                            public int compare(String e1, String e2)
                            {
                               return e2.compareTo(e1);
                            }
                         });
      dump(innerplanets);
   }
   static <T> void dump(T[] array)
   {
      for (T element: array)
         System.out.println(element);
      System.out.println();
   }
   static <T> void sort(T[] array, Comparator<T> cmp)
   {
      for (int pass = 0; pass < array.length - 1; pass++)
         for (int i = array.length - 1; i > pass; i--)
            if (cmp.compare(array[i], array[pass]) < 0)
               swap(array, i, pass);
   }
   static <T> void swap(T[] array, int i, int j)
   {
      T temp = array[i];
      array[i] = array[j];
      array[j] = temp;
   }
}
Listing 2 imports the java.util.Comparator functional interface, which describes a function that can perform a comparison on two objects of arbitrary but identical type.

Two significant parts of this code are the sort() method (which implements the Bubble Sort algorithm) and the sort() invocations in the main() method. Although sort() is far from being functional, it demonstrates a higher-order function that receives a function--the comparator--as an argument. It executes this function by invoking its compare() method. Two instances of this function are passed in two sort() calls in main().

Compile Listing 2 as follows:


javac Sort.java
Run the resulting application as follows:


java Sort
You should observe the following output:


Mercury
Venus
Earth
Mars
Earth
Mars
Mercury
Venus
Venus
Mercury
Mars
Earth
Lazy evaluation in Java
Lazy evaluation is another functional programming technique that is not new to Java 8. This technique delays the evaluation of an expression until its value is needed. In most cases, Java eagerly evaluates an expression that is bound to a variable. Java supports lazy evaluation for the following specific syntax:

The Boolean && and || operators, which will not evaluate their right operand when the left operand is false (&&) or true (||).
The ?: operator, which evaluates a Boolean expression and subsequently evaluates only one of two alternative expressions (of compatible type) based on the Boolean expression's true/false value.
Functional programming encourages expression-oriented programming, so you'll want to avoid using statements as much as possible. For example, suppose you want to replace Java's if-else statement with an ifThenElse() method. Listing 3 shows a first attempt.

Listing 3. An example of eager evaluation in Java (EagerEval.java)

public class EagerEval
{
   public static void main(String[] args)
   {
      System.out.printf("%d%n", ifThenElse(true, square(4), cube(4)));
      System.out.printf("%d%n", ifThenElse(false, square(4), cube(4)));
   }
   static int cube(int x)
   {
      System.out.println("in cube");
      return x * x * x;
   }
   static int ifThenElse(boolean predicate, int onTrue, int onFalse)
   {
      return (predicate) ? onTrue : onFalse;
   }
   static int square(int x)
   {
      System.out.println("in square");
      return x * x;
   }
}
Listing 3 defines an ifThenElse() method that takes a Boolean predicate and a pair of integers, returning the onTrue integer when the predicate is true and the onFalse integer otherwise.

Listing 3 also defines cube() and square() methods. Respectively, these methods cube and square an integer and return the result.

The main() method invokes ifThenElse(true, square(4), cube(4)), which should invoke only square(4), followed by ifThenElse(false, square(4), cube(4)), which should invoke only cube(4).

Compile Listing 3 as follows:


javac EagerEval.java
Run the resulting application as follows:


java EagerEval
You should observe the following output:


in square
in cube
16
in square
in cube
64
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