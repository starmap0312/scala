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
