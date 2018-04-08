// Types and Pattern Matching
// 1) objects everywhere
// 1.1) package scala
type Iterable[+A] = scala.collection.Iterable[A] // iterable has an iterator() method, ex. Seq extends iterable
val Iterable = scala.collection.Iterable

type Iterator[+A] = scala.collection.Iterator[A] // iterator has an next() method
val Iterator = scala.collection.Iterator         // it can be used to traverse the elements of iterable via a while-loop

type List[+A] = scala.collection.immutable.List[A]
val List = scala.collection.immutable.List

final abstract class Int() extends scala.AnyVal     {
  def ==(x: Int): Boolean     // a == b
  def !=(x: Int): Boolean     // a != b
  def <(x: Int): Boolean      // a > b
  def >(x: Int): Boolean      // a < b
  def +(x: Int): Int          // a + b
  def unary_- : Int           // -a
  // ...
}
// note: abstract forbids instantiation (new Int), whereas final forbids subclassing (new Int { ... })
//       scala.Int is directly represented by the primitive integer type of the Java virtual machine (so is Boolean)
//       because they are primitive types on the runtime (for better performance than the boxed types)
//       there is no way to instantiate them other than by giving a literal (val i: Int = 33)
//       (Scala has these types to create a unified object system between primitive types and objects)
final abstract class Boolean private extends AnyVal {
  def ==(x: Boolean): Boolean // a == b
  def !=(x: Boolean): Boolean // a != b
  def ||(x: Boolean): Boolean // a || b
  def &&(x: Boolean): Boolean // a && b
  def unary_! : Boolean       // !a
  // ...
}

// 1.2) scala.Predef
//  i)  Commonly Used Types
type Map[A, +B] = immutable.Map[A, B]
type Set[A]     = immutable.Set[A]
// ii)  Console I/O 
def println() = Console.println()
// iii) Assertions
def assert(assertion: Boolean)    { ... }         // throw new java.lang.AssertionError("assertion failed")
def require(requirement: Boolean) { ... }         // throw new IllegalArgumentException("requirement failed")
def ??? : Nothing = throw new NotImplementedError // marking methods that remain to be implemented
//  iv) Implicit Conversions
implicit def augmentString(x: String): StringOps = new StringOps(x) // class StringOps has length() and StringLike methods
implicit def int2Integer(x: Int)          = java.lang.Integer.valueOf(x) // convert scala.Int     to java.lang.Integer
// class java.lang.Integer wraps a value of the primitive type int in an object and provides some useful static methods
implicit def boolean2Boolean(x: Boolean)  = java.lang.Boolean.valueOf(x) // convert scala.Boolean to java.lang.Boolean

// 2) function objects
//    eta-expansion:
//      if method f is used in a place where a Function type is expected,
//      it is automatically converted to the Function value

// 3) subtyping and generics:
// 3.1) two forms of polymorphism: subtyping and generics
// 3.2) interaction between subtyping and generics: type bounds and variance
// i) covariant vs. contravariant vs. invariant
//    the compiler will have variance checking for us (roughly):
//      covariant parameters     can appear only in method results
//      contravariant parameters can appear only in method parameters
//      invariant parameters     can appear anywhere
trait Function1[-T, +U] {
  def apply(x: T): U             // this is OK in compile time, as the convariant parameter appears in method result
}

// 4) Decomposition
// 4.1) OOP decomposition
trait Expr {
  def isNumber: Boolean // classification method
  def isSum:    Boolean // classification method
  def eval:     Int     // access method
}
class Number(n: Int) extends Expr {
  def isNumber: Boolean = true
  def isSum:    Boolean = false
  def eval:     Int     = n
}
class Sum(e1: Expr, e2: Expr) extends Expr {
  def isNumber: Boolean = false
  def isSum:    Boolean = true
  def eval:     Int     = e1.eval + e2.eval
}
// why is it bad?
// 1) the interface grows when more and more subtypes are added
//      we need to touch all subtypes when adding a new method
// 2) if we want to implement some kind of simplification rule
//      ex. (a * b + a * c) -> a * (b + c)
//    we can not encapsulate the above functionality inside the objects, as the objects will need to know about
//      the global information to implement such a rule (i.e. access all the different subtypes)

// we can move the eval() functionality outside the objects
//  so there is no need for classification method (isNumber, isSum, etc.)
trait Expr {
  def value:   Int
  def leftOp:  Expr
  def rightOp: Expr
}
class Number(n: Int) extends Expr {
  def value:   Int  = n
  def leftOp:  Expr = throw new Exception("Number has no left Op")
  def rightOp: Expr = throw new Exception("Number has no right Op")
}
class Sum(e1: Expr, e2: Expr) extends Expr {
  def value:   Int  = throw new Exception("Sum has no value")
  def leftOp:  Expr = e1
  def rightOp: Expr = e2
}
// 4.2) use type-check and type-casting:
def eval(e: Expr): Int = {
  if (e.isInstanceOf[Number])
    e.asInstanceOf[Number].value
  else if (e.isInstanceOf[Sum])
    eval(e.asInstanceOf[Sum].leftOp) + eval(e.asInstanceOf[Sum].rightOp)
}
// why is it bad?
//   it is not safe as there is no type-check at compile-time, so it may throw runtime exception

// 4.3) functional decomposition (use pattern matching)
//      use extractor classes: ex. case class, a way to directly accessing the internal fields
//      use pattern matching : a shorthand for type-check and type-casting
trait Expr
case class Number(n: Int)          extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr 
// companion object are created implicitly:
// objcet Number {
//   def apply(n: Int) = new Number(n)
// }

def eval(e: Expr): Int = e match {
  case Number(n)   => n                   // a constructor pattern
  case Sum(e1, e2) => eval(e1) + eval(e2) // a constructor pattern
}

// client
eval(Sum(Number(1), Number(2))) = eval(Number(1)) + eval(Number(2)) = 1 + 2 = 3

// or we can move the eval() method inside trait 
trait Expr {
  def eval: Int = this match {
    case Number(n)   => n
    case Sum(e1, e2) => e1.eval + e2.eval
  }
}

// oop decomposition vs. functional decomposition
// if you often create new subtypes, then use oop decomposition is better (adding a new subclass)
// if you often create new methods, then use pattern matching is better   (local change by adding a new case)
