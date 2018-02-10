// Reference
//   https://www.youtube.com/watch?v=Oij5V7LQJsA
// Implicit
// What is Context?
// 1) what comes with the text, but is NOT in the text
//    ex. the current "configuration"
//        the current scope
//        the meaning of < on this type (i.e. object's infix method)
//        the user on behalf of which the operation is performed
//        the security level in effect
// 2) traditional ways to express context (NOT GOOD DESIGN)
// 2.1) globals:
//      rigid if immutable
//      unsafe if mutable
// 2.2) monkey patching:
//      global effects implicitly 
// 2.3) dependency injection:
//      at run time (Spring, Guice), or with macros (MacWire)
//      use class annotation, XMLs 
// 2.4) cake pattern
//      close coupling + recursion
// 3) the functional way (BETTER DESIGN)
//    parameterize all things: context is passed as an parameter to a function
// 3.1) why is it good?
//      no side effects
//      type safe (compile-time type check)
//      fine-grained control (you can pass and refine your context)
// 3.2) why is it not so good?
//      too many of parameters
//      repetitive: most of which hardly change
// Scala Implicits:
// 1) implicit parameters
//    pass context as an "implicit" parameter
// 1.1) if the implicit argument is not given, the compiler will look for
//      found from all implicit values "visible" at the point of call
//      how to get the implicit value:
//        define the implicit value in the current scope directly, or
//        import the implicit value, or
//        inherit the implicit value from parent class
// 1.2) if there are more than one eligible candidate, the most "specific" one is chosen
//      if there is no unique most specifict candidate, an ambiguity error is reported
// 2) implicit conversion
//    define implicit method or implicit class (a shorthand for class definition and implicit method together)
// 2.1) if the type A of an expression does not match the expected type B, then
//      the compiler tries to find an (visible) implicit conversion method from A to B
// 2.2) the conversion applies to method parameter types or method return types
//      the conversion applies to implicit parameter as well
// 3) when to use implcits?
//    pros: they remove repetition and boilerplate
//    cons: they could hurt readability if taken too far
// 4) reasoning about when to use implicits
// 4.1) applicability: where are you allowed to use?
// 4.2) power        : what much does it influence?
// 4.3) scope        : is there a clear place look if the implicits are happening?
// 5) patterns of implicit conversion
// 5.1) extension methods (works like decorators, adding/extending new functionalities to an object)
//      make String have some extension methods, ex. take(), drop()
//        String is implicited converted to StringOps instance in Predef.scala
//        StringOps extends StringLike, which extends scala.collection.IndexedSeqOptimized
//        scala.collection.IndexedSeqOptimized are where the extension methods take() and drop() are defined
// ex.
implicit class String(x: String) { // wrapping a String and extending its functionalities
  def take(n: Int) = x.substring(0, n)
  def drop(n: Int) = x.substring(n, x.length)
}
"12345".take(3)
"12345".drop(3)
// 6) anti-patterns of implicit conversion
// 6.1) conversions that go both ways
//      conversions that change semantics
// ex. java.util.Collection[T] <=> scala.collection.Iterable[T]
implicit def toScala[T](x: java.util.Collection[T]): Iterable[T]
implicit def toJava[T](x: Iterable[T]): java.util.Collection[T]
// better design: one-way conversion, or make the conversion explcit
// ex. scala.collection.JavaConverters.{asJava, asScala}, i.e. use explicit extension methods, asJava(), asScala()
// 6.2) implicit conversions between two existing types
implicit def toTermName(x: String): TermName
// the scope of the implicit conversion from String to TermName could be large
// 7) patterns of implicit parameters
def sort[T](xs: Seq[T])(implicit ord: Ordering[T])
// context bound: a shorthand of the abve
def sort[T: Ordering](xs: Seq[T])
// 7.1) use cases of implicit parameters
//      establish context
// ex.
case class Viewers(persons: Set[Person])
// note: do not use a general type (ex. Set[Person]) as an implicit parameter
//       use a specific type instead (ex. case class Viewers)
def score(paper: Paper)(implicit vs: Viewers): Int = {
  if (hasConflict(vs.persons, authors(papers))) -100
  else realScore(paper) 
}
def viewRankings(implicit vs: Viewers) = papers.sortBy(score(_))
//      set configurations
//      implement type classes
//      prove theorems
//      inject dependencies
//      model capabilities
