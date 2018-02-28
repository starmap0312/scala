// Data and Abstraction
// 1) class hierarchies
// 1.1) abstract classes: class not/paritially implemented (so it cannot be instantiated)
abstract class IntSet {      // an abstract superclass
  def add(x: Int): IntSet
  def contains(x: Int): Boolean
}
// if the keyword "abstract" is missing, you would get compile error as there are unimplemented methods
// if you try to "new" an abstract class, you also get compile error

class Empty extends IntSet { // a concrete subclass
  def add(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
  def contains(x: Int): Boolean = false 
}

// scala is more picky than java: to override a method, you need "override" keyword
abstract class Base {
  def foo = 1
  def bar: Int
}

class Sub extends {
  override def foo = 2  // already implemented in Base class, so we need the override keyword
  override def foo2 = 2 // this gives compile error as well, as override keyword is not needed 
  def bar = 3
}
// the override keyword is to avoid accidentally override methods in the superclass

// 1.2) trait
// i) trait is more flexible than abstract class in that is allows "multiple inheritance"
//      ex. class Sub extends Trait1 with Trait2
//    traits are used to share interfaces and fields between classes
//      they are similar to Java 8’s interfaces
trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}
//
// ii) traits is more restricted than abstract class in that it CAN NOT have "constructor parameters"
//       ex. trait Pet(name: String) { ... } is wrong
//     this is due to that multiple inheritance may introduce other issues
//       ex. we may inherit two methods with the same signature / two fields with the same name from two different traits
//     in constrast, abstract classes can have both constructor parameters and type parameters
trait Pet {        // or "abstract class Pet" also works
  val name: String // an abstract field which needs to be implemented in subclasses 
}
class Cat(val name: String) extends Pet
class Dog(val name: String) extends Pet
// the trait Pet has an abstract field name which gets implemented by Cat and Dog in their constructors
//
// iii) two main reasons to use an abstract class in Scala
//      a) if the code need to be called from Java code
//         i.e. if you do not ever expect a Java class inherit from it, you should always prefer a trait
//         abstract classes can be directly called from Java code without any wrappers (fully interoperable)
//         traits are fully interoperable only if they do not contain any implementation code
//      b) if you want to create a base class that requires constructor arguments
//
// ref: https://commitlogs.com/2016/10/01/scala-trait-and-abstract-class/
// ref: https://docs.scala-lang.org/tour/abstract-types.html

// 2) how classes are organized
// 2.1) auto imports in scala
//      all members of package java.lang,    i.e. import java.lang._
//        ex. java.lang.Object
//      all members of package scala,        i.e. import scala._
//        ex. scala.Int, scala.Boolean
//      all members of package scala.Predef, i.e. import scala.Predef._
//        ex. scala.Predef.assert, scala.Predef.require
// 2.2) type hierarchies
//      scala.Any     vs. scala.AnyRef (supertype of reference types, i.e. java.lang.Object) vs. scala.AnyVal (supertype of primitives)
//      scala.Nothing vs. scala.Null   (subtype of only reference types)
//        ex. val x: Null = null
//
// ref: (scaladoc) https://www.scala-lang.org/api/current/

// 3) polymorphism
//    subtyping: instances of a subclass can be passed to a base class
//    generics : instances of a function/class are created by type parameterization
// 3.1) val constructor parameters of a class
class IntList(val head: Int, val tail: IntList) { ... }
// the above is a shorthand of the following (it defines the constructor parameter and make them public fields at the same time)
class IntList(head_: Int, tail_: IntList) {
  val head = head_
  val tail = tail_
}
// 3.2) type parameters
// class or trait can have type parameter
trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}
class Cons[T](val head: Int, val tail: List[T]) extends List[T] { // head and tail are implemented, assinged as the constructor parameters)
  def isEmpty = false
}
class Nil[T] extends List[T] {
  def isEmpty = true
  def head = throw new NoSuchElementException("Nil head") // throw expression is of type Nothing, which is a subtype of any other type
  def head = throw new NoSuchElementException("Nil tail") // throw expression is of type Nothing, which is a subtype of any other type
}
// method can have type parameter
def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])
singleton(1)    // i.e. singleton[Int](1),        the type parameter can be induced by the compiler
singleton(true) // i.e. singleton[Boolean](true), the type parameter can be induced by the compiler
// type erasure: all type parameters are removed before evaluating the program (i.e. at compile time)
