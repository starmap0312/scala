// Data and Abstraction
// 1) class hierarchies
//    abstract classes: class without implementation (so it cannot be instantiated)
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
}
// if "abstract" is missing, you would get compile error as there are unimplemented methods
// if you try to new an abstract class, you will also get compile error

class Empty extends IntSet {
  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
  def contains(x: Int): Boolean = false 
}

// override method: scala is more picky than java
abstract class Base {
  def foo = 1
  def bar: Int
}

class Sub extends {
  override def foo = 2  // already implemented in Base class, so we need the override keyword
  override def foo2 = 2 // this gives compile error as well, as override keyword is not needed 
  def bar = 3
}
// override keyword is required, otherwise, there will be compile error (this is to avoid accidentally override methods in the superclass)

// abstract class vs. trait
// 1) trait is more flexible than an abstract class
//    a class can extend only one abstract class, but it can implement multiple traits, so using traits is more flexible
//      but multiple inheritance introduces other issues
//      ex. we may inherit two methods with the same signature / two variables with the same name from two different traits
//          so it is better that we keep traits methods unimplemented
//    traits are used to share interfaces and fields between classes
//      they are similar to Java 8’s interfaces
trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}
//
// 2) abstract classes can have both constructor parameters and type parameters
//    traits can only have type parameters (no constructor parameters)
trait Pet {
  val name: String
}
class Cat(val name: String) extends Pet
class Dog(val name: String) extends Pet
// the trait Pet has an abstract field name which gets implemented by Cat and Dog in their constructors
//
// 3) abstract classes can be called from Java code without any wrappers (fully interoperable)
//    traits are fully interoperable only if they do not contain any implementation code
//      it is better that we keep traits methods unimplemented, so we can directly use them in Java
//
// 4) two main reasons to use an abstract class in Scala
//    i) you want to create a base class that requires constructor arguments (and abstract class can be partially implemented)
//   ii) the code need to be called from Java code
//
// example:https://docs.scala-lang.org/tour/abstract-types.html

// 2) how classes are organized

// 3) polymorphism

