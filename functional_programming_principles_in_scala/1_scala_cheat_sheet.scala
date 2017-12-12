// 1) Evaluation Rules
// ex1. def, val, lazy val
def example = 2           // evaluated when called
val example = 2           // evaluated immediately
lazy val example = 2      // evaluated once when needed
// 1.1) Call by value: evaluates the function arguments before calling the function
// ex2. call-by-call
def square(x: Double)     // call by value
// 1.2) Call by name: evaluates the function first, and then evaluates the arguments if need be
// ex3. call-by-name
def square(x: => Double)  // call by name
// ex4. varying # of arguments
def myFct(bindings: Int*) = { ... } // bindings is a sequence of int, containing a varying # of arguments

// 2) Higher order functions
//    functions that take a function as a parameter or return functions
// ex1. higher-order method sum() returns a Function2 that takes two integers and returns an integer  
def sum(f: Int => Int): (Int, Int) => Int = {  
  def sumf(a: Int, b: Int): Int = { f(a) + f(b) }
  sumf
} 
sum(x => x * x * x)(1, 10) // sum of cubes from 1 to 10

// ex2. curried method, it is the same as the above, but its type is (Int => Int) => (Int, Int) => Int  
def sum(f: Int => Int)(a: Int, b: Int): Int = { f(a) + f(b) } 
// Called like this
sum((x: Int) => x * x * x) // the parameter is an nnonymous function, i.e. it does not have a name  
sum(x => x * x * x)        // same as the above anonymous function but with type inferred
sum(x => x * x * x)(1, 10) // sum of cubes from 1 to 10

def cube(x: Int) = x * x * x  
sum(cube)(1, 10)           // same as above   

// 3) Currying
//    convert method with multiple arguments into a function with fewer arguments (i.e. returns another function)
def f(a: Int, b: Int): Int // uncurried method (type is (Int, Int) => Int)
def f(a: Int)(b: Int): Int // curried method (type is Int => Int => Int)

// 4) Classes
//    a scala class body is the primary constructor, which is called when use the "new" operator
//    its parameters is just like a public final field in Java (i.e. val (immutable) and public)
// ex1.
class MyClass(x: Int, y: Int) {           // Defines a new type MyClass with a constructor  
  require(y > 0, "y must be positive")    // precondition, triggering an IllegalArgumentException if not met  
  def this(x: Int) = { this(x, 0) }       // auxiliary constructor
  def nb1 = x                             // public method computed every time it is called  
  def nb2 = y  
  private def test(a: Int): Int = { ... } // private method  
  val nb3 = x + y                         // computed only once, called during instantiation 
  override def toString =                 // overridden method  
    member1 + ", " + member2 
}

new MyClass(1, 2)                         // creates a new object of type MyClass
// ex2.
assert(condition)                       // throws AssertionError if condition is not met

// 4) Operators
// ex1. infix notation
myObject myMethod 1        // this is the same as calling myObject.myMethod(1)
//    operator (i.e. method) names can be alphanumeric, symbolic (ex. x1, *, +?%&, vector_++, counter_=)
3 + 4                      // this is the same as calling 3.+(4)
//    the precedence of an operator is determined by its first character, with the following increasing priority
// ex2. (all letters)
//      |
//      ^
//      &
//      < >
//      = !
//      :
//      + -
//      * / %
//      (all other special characters)

// the associativity of an operator is determined by its last character
//   Right-associative if ending with :
//   ex. (a: Int) => a + 1
//       1: 2: Nil
//   Left-associative otherwise
// note: assignment operators have lowest precedence

// 5) Class hierarchies
// ex1. abstract class & singleton (object)
abstract class TopLevel {            // abstract class: cannot be instantiated
  def method1(x: Int): Int           // abstract method, a non-implemented method 
  def method2(x: Int): Int = { ... } // implemented method 
}

class Level1 extends TopLevel {      // concrete class 
  def method1(x: Int): Int = { ... } // implement the non-implemented method here
  override def method2(x: Int): Int = { ...} // TopLevel's method2 needs to be explicitly overridden  
}

object MyObject extends TopLevel { ... } // defines a singleton object. No other instance can be created

// ex2. create a runnable application in Scala
object Hello {  
  def main(args: Array[String]) = println("Hello world")  
}
// or
object Hello extends App { // the singleton's body is executed during instantiation
  println("Hello World")
}

// 6) Class Organization
// Classes and objects are organized in packages
// ex1.
package myPackage

// Classes and objects in a package can be referenced through import statements
// ex2.
import myPackage.MyClass              // import a class
import myPackage._                    // import everything public in a package
import myPackage.{MyClass1, MyClass2} // import two classes
import myPackage.{MyClass1 => A}      // import a class and give it an alias

// they can also be directly referenced in the code with the fully qualified name
// ex3.
new myPackage.MyClass1

// the following are automatically imported in Scala
//   all members of packages scala
//   all members of java.lang
//   all members of the object scala.Predef

// Traits are similar to Java interfaces, except they can have non-abstract members
// ex4.
trait Planar { ... }
class Square extends Shape with Planar

// General object hierarchy
//   scala.Any:    base type of all types
//     it has methods hashCode() and toString() that can be overridden
//   scala.AnyVal: base type of all primitive types
//     ex. scala.Double, scala.Float, etc.
//   scala.AnyRef: base type of all reference types
//     i.e. alias of java.lang.Object, supertype of java.lang.String, scala.List, any user-defined class
//   scala.Null:   a subtype of any scala.AnyRef
//     null is the only instance of type scala.Null
//     scala.Nothing is a subtype of any other type without any instance

// 7) Type Parameters
// conceptually similar to C++ templates or Java generics
// it can be used when defining classes, traits or functions, etc.
// ex1.
class MyClass[T](arg1: T) { ... }  
new MyClass[Int](1)  
new MyClass(1)   // the type is being inferred, i.e. determined based on the value arguments  

// restrict the type being used
// ex2.
def myFct[T <: TopLevel](arg: T): T = { ... } // T must derive from TopLevel or be TopLevel
def myFct[T >: Level1](arg: T): T = { ... }   // T must be a supertype of Level1
def myFct[T >: Level1 <: Top Level](arg: T): T = { ... }

// 8) Variance
// Given A <: B
//   If C[A] <: C[B], C is covariant
//   If C[A] >: C[B], C is contravariant
//   Otherwise C is nonvariant
// ex.
class C[+A] { ... }     // C is covariant
class C[-A] { ... }     // C is contravariant
class C[A]  { ... }     // C is nonvariant
// ex.
//   For a function, if A2 <: A1 and B1 <: B2, then (A1 => B1) <: (A2 => B2)

// Functions must be contravariant in their argument types and covariant in their result types
// ex.
trait Function1[-T, +U] {
  def apply(x: T): U
}                       // this definition is OK because T is contravariant and U is covariant

class Array[+T] {
  def update(x: T)
}                       // this definition is NOT OK 

// 9) Pattern Matching
// pattern matching is used for decomposing data structures (unwrapping objects)
// ex1.
unknownObject match {
  case MyClass(n) => ...
  case MyClass2(a, b) => ...
}

// ex2.
(someList: List[T]) match {
  case Nil => ...          // empty list
  case x :: Nil => ...     // list with only one element
  case List(x) => ...      // same as above
  case x :: xs => ...      // a list with at least one element. x is bound to the head, and
                           // xs to the tail. xs could be Nil or some other list
  case 1 :: 2 :: cs => ... // lists that starts with 1 and then 2
  case (x, y) :: ps => ... // a list where the head element is a pair (a sub-pattern)
  case _ => ...            // default case if none of the above matches
}

// 9) Options
//    some functions (ex. Map.get) return a value of type Option[T] which is either Some[T] or None
// ex. Options used with pattern matching
val myMap = Map("a" -> 42, "b" -> 43)
def getMapValue(s: String): String = {
  myMap get s match {
    case Some(nb) => "Value found in Map: " + nb
    case None => "No value found in Map"
  }
}
getMapValue("a")  // "Value found in Map: 42"
getMapValue("c")  // "No value found in Map"
// ex. the function getMapValue can be defined using combinator methods of Option class, ex. map(), getOrElse()
def getMapValue(s: String): String =
  myMap.get(s).map("Value found in Map: " + _).getOrElse("No value found in Map")
  // Some(x).map(x => f(x)) returns Some(f(x)), thus Some(x).map(x => f(x)).getOrElse() returns f(x)
  // None.map(x => f(x)) returns None,          thus None.map(x => f(x)).getOrElse([expr]) returns [expr]

// 10) Pattern Matching in Anonymous Functions
// Pattern matches are also used quite often in anonymous functions
// ex.
val pairs: List[(Char, Int)] = ('a', 2) :: ('b', 3) :: Nil
val chars: List[Char] = pairs.map(
  pair => pair match {
    case (ch, num) => ch
  }
) // List('a', 'b'), i.e. 'a' :: 'b' :: Nil

// a shorthand for the above partial function defintion
val chars: List[Char] = pairs map {
  case (ch, num) => ch
} // List('a', 'b'), i.e. 'a' :: 'b' :: Nil 

// 11) Collections
// Base Classes of scala.collection.mutable and scala.collection.immutable:
//   Iterable -> Seq
//            -> Set
//            -> Map
//   (Iterable is collections you can iterate on, Seq is ordered sequences, and Map is lookup data structure)
// 11.1) scala.collection.mutable (Mutable Collections)
//                         -> Seq -> IndexedSeq
//                                -> LinearSeq
// Traversable -> Iterable -> Set -> SortedSet
//                                -> BitSet
//                         -> Map -> SortedMap
// Array: Scala arrays are native JVM arrays at runtime, therefore they are very performant
// Array[T] is Scala's representation for Java's T[]
final class Array[T] extends java.io.Serializable with java.lang.Cloneable

// Array is mutable, indexed collections of values
val numbers = Array(1, 2, 3, 4) // Array is mutable, indexed collections of values
val first = numbers(0)          // read the first element
numbers(3) = 100                // replace the 4th array element with 100

// scala.collection.mutable.IndexedSeq
trait IndexedSeq[A] extends Seq[A] with collection.IndexedSeq[A]
val numbers = scala.collection.mutable.IndexedSeq(1, 2, 3, 4)  // scala.collection.mutable.IndexedSeq[Int] = ArrayBuffer(1, 2, 3, 4)
numbers(3) = 100                                               // scala.collection.mutable.IndexedSeq[Int] = ArrayBuffer(1, 2, 3, 100)

//   Scala also has mutable Map and Set: these should only be used if there are performance issues with immutable types
val myMap = scala.collection.mutable.Map("a" -> 42, "b" -> 43) // scala.collection.mutable.Map[String,Int] = Map(b -> 43, a -> 42)
myMap("a") = 100                                               // scala.collection.mutable.Map[String,Int] = Map(b -> 43, a -> 100)
//   note: a java.util.Map is mutable, so we need to convert a scala immutable Map to a java mutable Map before passing to a Java function
//         ex. conver a scala.collection.immutable.Map[String, Double] to a java.util.Map[String, java.lang.Double]
val scalaImmutableMap = Map[String, Double]("one" -> 1.0, "two" -> 2.0) // scala.collection.immutable.Map[String,Double] = Map(one -> 1.0, two -> 2.0)
val scalaImmutableDoubleMap: Map[String, java.lang.Double] = scalaImmutableMap.mapValues(Double.box) 
val scalaMutableDoubleMap: scala.collection.mutable.Map[String, java.lang.Double] = scala.collection.mutable.Map(
  scalaImmutableDoubleMap.toSeq: _*
)
import scala.collection.JavaConverters._
val javaMutableDoubleMap : java.util.Map[String, java.lang.Double] = scalaMutableDoubleMap.asJava // java.util.Map[String,Double] = {one=1.0, two=2.0}

// 11.2) scala.collection.immutable (Immutable Collections)
//                         -> Seq -> IndexedSeq -> String / Vector / Range / NumericRange
//                                -> LinearSeq  -> List   / Stream / Queue / Stack
// Traversable -> Iterable -> Set -> SortedSet  -> TreeSet
//                                -> BitSet
//                                -> HashSet
//                                -> ListSet
//                         -> Map -> SortedMap  -> TreeMap
//                                -> HashMap
//                                -> ListMap
//
// scala.collection.immutable.List[+A]   // covariant: i.e. List[Int] is a List[AnyVal], because class Int extends AnyVal
sealed abstract class List[+A] extends AbstractSeq[A] with LinearSeq[A]
// i)   apply(): construct a immutable List (defined by trait TraversableOnce)
val fruitList = List("apples", "oranges", "pears")
// ii)  final case class ::[B](override val head: B, private[scala] var tl: List[B]) extends List[B] 
//      alternative syntax for constructing a immutable List
val fruit = "apples" :: ("oranges" :: ("pears" :: Nil)) // parens optional, :: is right-associative
// iii) head() and tail()
fruit.head                  // "apples"
fruit.tail                  // List("oranges", "pears")
// iv) case object Nil extends List[Nothing]
val empty = List()          // an instance of type List[Nothing]
val empty = Nil             // a sigleton of type List[Nothing], i.e. case object Nil extends List[Nothing]

// scala.collection.immutable.Vector[+A] // covariant: i.e. Vector[Int] is a Vector[AnyVal], because class Int extends AnyVal
final class Vector[+A] extends AbstractSeq[A] with IndexedSeq[A]
// i)    apply(): construct a immutable Vector (defined by abstract class GenericCompanion)
val nums = Vector("louis", "frank", "hiromi")
nums(1)                     // returns "frank": complexity O(log(n))
nums.updated(2, "helena")   // returns a new Vector with a different string at index 2: complexity O(log(n))

// scala.collection.immutable.Set[A]     // nonvariant : i.e. Set[Int] is NOT a Set[AnyVal]
// i)    apply(): construct a immutable Set (defined by abstract class GenericCompanion)
val fruitSet = Set("apple", "banana", "pear", "banana")
fruitSet.size               // returns 3: there are no duplicates, only one banana

// scala.collection.immutable.Range
sealed class Range(val start: Int, val end: Int, val step: Int) extends scala.collection.AbstractSeq[Int] with IndexedSeq[Int]
// i)   def until(end: Int): Range
//      construct a Range, defined by final class RichInt(val self: Int)
val r1: Range = 1 until 5    // scala.collection.immutable.Range = Range 1 until 5:            i.e. 1, 2, 3, 4
// ii)  def to(end: Int, step: Int): Range.Inclusive
//      construct a Range, defined by class RichInt(val self: Int))
val r2: Range = 1 to 5       // scala.collection.immutable.Range.Inclusive = Range 1 to 5:     i.e. 1, 2, 3, 4, 5
// iii) def by(step: Int): Range
//      construct a Range, defined by class Range
val r3 = 1 to 10 by 3        // scala.collection.immutable.Range = Range 1 to 10 by 3:         i.e. 1, 4, 7, 10
val r4 = 6 to 1 by -2        // scala.collection.immutable.Range = inexact Range 6 to 1 by -2: i.e. 6, 4, 2 
val s = (1 to 6).toSet       // or (1 to 6).toSeq

// java.lang.String: i.e. scala String
// i) often used with scala.collection.immutable.StringOps for combinatortory methods such as map(), filter(), etc.
//    new StringOps([String]).filter(), where class StringOps is a sub-sub-subclass of class TraversableLike, where filter() is defined
val s = "Hello World"        // implicit conversion of new StringOps(s) takes place in the following statement
s filter (c => c.isUpper)    // returns "HW"; strings can be treated as Seq[Char]

// Operations on sequences: ex. List
val xs = List(1, 2, 3)
val ys = List(4, 5)
xs.length   // number of elements, complexity O(n)
xs.last     // last element (exception if xs is empty), complexity O(n)
xs.init     // all elements of xs but the last (exception if xs is empty), complexity O(n), ex. List(1, 2, 3).init = List(1, 2)
xs take n   // first n elements of xs, ex. List(1, 2, 3) take 2 = List(1, 2)
xs drop n   // the rest of the collection after taking n elements, ex. List(1, 2, 3) drop 1 = List(2, 3)
xs(n)       // the nth element of xs, complexity O(n)
xs ++ ys    // concatenation, complexity O(n)
xs.reverse  // reverse the order, complexity O(n)
xs updated(n, x)  // same list than xs, except at index n where it contains x, complexity O(n)
xs indexOf x      // the index of the first element equal to x (-1 otherwise)
xs contains x     // same as xs indexOf x >= 0
xs filter p       // returns a list of the elements that satisfy the predicate p
xs filterNot p    // filter with negated p 
xs partition p    // same as (xs filter p, xs filterNot p)
xs takeWhile p    // the longest prefix consisting of elements that satisfy p
xs dropWhile p    // the remainder of the list after any leading element satisfying p have been removed
xs span p         // same as (xs takeWhile p, xs dropWhile p)

List(x1, ..., xn) reduceLeft op    // (...(x1 op x2) op x3) op ...) op xn
List(x1, ..., xn).foldLeft(z)(op)  // (...( z op x1) op x2) op ...) op xn
List(x1, ..., xn) reduceRight op   // x1 op (... (x{n-1} op xn) ...)
List(x1, ..., xn).foldRight(z)(op) // x1 op (... (    xn op  z) ...)

xs exists p    // true if there is at least one element for which predicate p is true
xs forall p    // true if p(x) is true for all elements
xs zip ys      // returns a list of pairs which groups elements with same index together, ex. List(1, 2) zip List(3, 4) = List((1,3), (2,4))
xs unzip       // opposite of zip: returns a pair of two lists, ex. List((1,3), (2,4)).unzip = (List(1, 2),List(3, 4))
xs.flatMap f   // applies the function to all elements and concatenates the result (function f should return a List as well)
xs.sum         // sum of elements of the numeric collection
xs.product     // product of elements of the numeric collection
xs.max         // maximum of collection
xs.min         // minimum of collection
xs.flatten     // flattens a collection of collection into a single-level collection
xs groupBy f   // returns a map which points to a list of elements, ex. List(1, 2, 3, 4).groupBy(x => x % 2) = List(1, 2, 3, 4).groupBy(x => x % 2)
xs distinct    // sequence of distinct entries (removes duplicates)

// Operations on Streams
val xs = Stream(1, 2, 3) // => Stream(1, ?)
val xs = Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.empty))) // same as above
(1 to 1000).toStream     // => Stream(1, ?)
x #:: xs // Same as Stream.cons(x, xs)
// In the Stream's cons operator, the second parameter (the tail) is defined as a "call by name" parameter.
// Note that x::xs always produces a List

// Operations on maps: ex. Map
val myMap = Map("I" -> 1, "V" -> 5, "X" -> 10)  // create a map
myMap("I")      // => 1  
myMap("A")      // => java.util.NoSuchElementException  
myMap get "A"   // => None 
myMap get "I"   // => Some(1)
myMap.updated("V", 15)  // returns a new map where "V" maps to 15 (entry is updated), if the key ("V" here) does not exist, a new entry is added

// Tuples/Pairs
val pair = ("answer", 42)   // type: (String, Int)
val (label, value) = pair   // label = "answer", value = 42  
pair._1 // "answer"  
pair._2 // 42  

// Ordering
// scala.math.Ordering[T]
//   in Scala standard library, scala.math.Ordering[T] declares comparison functions, ex. lt() and gt() for standard types
//   types with a single natural ordering should inherit from the trait scala.math.Ordered[T].
// it is a trait whose instances each represent a strategy for sorting instances of a type
//   ex. implicit object String extends StringOrdering (where trait StringOrdering extends Ordering[String])
//       implicit object Double extends DoubleOrdering (where trait DoubleOrdering extends Ordering[Double])
// 
import math.Ordering  
import scala.util.Sorting
val fruits = Array("apple", "banana", "pear", "banana")
Sorting.quickSort(fruits)(Ordering.String) // explicitly pass the Ordering parameter: fruits - Array(apple, banana, banana, pear) 
Sorting.quickSort(fruits)                   // as the parameter is defined as implicit, so the compiler should figure out the right ordering (it looks for Ordering instance in the scope) 

// For-Comprehensions
// it is syntactic sugar for map, flatMap and filter operations on collections
// syntax: for (s) yield e
//   s is a sequence of generators and filters
//     p <- e is a generator, i.e. e.map(p => {...})
//     if f is a filter,      i.e. e.filter(p => f)
//   if there are several generators (equivalent of a nested loop), the last generator varies faster than the first
//   you can use { s } instead of ( s ) if you want to use multiple lines without requiring semicolons
//   e is an element of the resulting collection
// ex.
for (x <- 1 to M; y <- 1 to N)
  yield (x,y)
// is equivalent to
 (1 to M) flatMap (x => (1 to N) map (y => (x, y)))

// Translation Rules
// for-expression looks like a traditional for loop but works differently internally
for (x <- e1) yield e2 
// is translated to
e1.map(x => e2)

for (x <- e1 if f) yield e2
// is translated to
for (x <- e1.filter(x => f)) yield e2

for (x <- e1; y <- e2) yield e3
// is translated to
e1.flatMap(x => for (y <- e2) yield e3)

// ex.
for {                                                  // use for {... ...} for multiple lines
  i <- 1 until n  
  j <- 1 until i  
  if isPrime(i + j)  
} yield (i, j) 
// is equivalent to
for (i <- 1 until n; j <- 1 until i if isPrime(i + j)) // use for (...; ...; ...) for single line
  yield (i, j)  
// is equivalent to
(1 until n).flatMap(i => (1 until i).filter(j => isPrime(i + j)).map(j => (i, j)))
