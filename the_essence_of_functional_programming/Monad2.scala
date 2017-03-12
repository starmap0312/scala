// Monad:
//   think of monads as wrappers: you just take an object and wrap it with a monad
//   every wrapper that provides us with two operations: unit/apply() and flatMap(), is a monad
//     apply([object]):      A => M[A]          ... also called identity (return in Haskell)
//       an operation that creates a monad M[A] from an object of type A, i.e. apply() method
//     flatMap([function]): (A => M[B]) => M[B] ... also called bind     (   >>= in Haskell)
// Examples: 
//   List/Set/Option/Future are monads (note: Future breaks the referential transparency though)
//   they all have unit/apply() and flatMap() methods
//   unit/apply() method: List(x), Set(x), Some(x)/Option(x), Future(x)
//     has a syntactic sugar of: List.apply(3) == List(3)
// Scala doesn’t come with a built-in monad type (unlike Haskell), we need to model monad ourselves
//   there is no actual monad type class in plain Scala
//     i.e. constructs such as List, Option, Future etc. don’t extend any special Monad trait
//   this means that they are not obligated to provide us with methods unit/apply() and flatMap()
//   the obligation is developer's responsibility
// Monad laws: monad must satisfy three laws
// Given:
//   x: some basic value/object
//   m: a monad instance (holding some value)
//   f and g: functions of type (Int => M[Int])
// The Laws:
//   1) left-identity law : apply(x).flatMap(f)     == f(x)
//   2) right-identity law: m.flatMap(apply)        == m
//   3) associativity law : m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
// 
// Example: Generic monad vs. Concrete monad
// 1) generic monad : concept of monad
// 2) concrete monad: implement the two functions, actually doing something, ex. IO monad
// ex. generic monad:
//     --------------
//     apply  :  A => M[A]           
//     flatMap: (A => M[B]) => M[B]  
//
//     concrete monad:
//     -------------- 
//     apply  :  User => Option[User]
//     flatMap: (User => Option[User]) => Option[User]
// Why define and use Monads?
// 1) we can chain operations and manipulate data using map, flatMap, filter etc.
// 2) it can be accompanied by other functional programming constructs such as pattern matching
// 3) it improves readability and clearness
//    it really improves reasoning about the code and lowers the number of bugs
//    the code has fewer if-branches, nested loops with off-by-one errors and callbacks

object Monad2 {
    // model a monad with a generic trait that provides methods unit() and flatMap()

    // M[A]: monads take a type parameter A
    //   the type parameter is like a label sticker on the wrap
    //     it says what kind of object we have inside, so we won't have surprise
    //   if to wrap some object with monad, we must parameterize the monad with the type of underlying object
    //     ex. M[Int], M[String], etc. 
    trait M[A] {
        // flatMap([function]): (A => M[B]) => M[B]
        //   it takes a function with signature A -> M[B]
        //   it uses that function to transform underlying object of type A into a M[B]
        // flatMap() is very similar to collections' flatMap() in Scala
        //   ex. M is List and A is Int, and B is String
        //       function: (Int => List[String])
        //       it maps List[Int] to List[List[String]] but flattened
        //       it maps List[Int] -> List[List[String]] -> List[String]
        // in other words,
        //        mapped with (A => M[B])         flattened
        //   M[A] ----------------------> M[M[B]] --------> M[B]
        // flatMap() is more powerful than map(): it gives us ability to chain operations together 
        def flatMap[B](f: A => M[B]): M[B]
    }

    // unit([object]): A => M[A]
    //   it simply performs the wrapping part, like a monad constructor
    def unit[A](x: A): M[A]
    // why we define method unit() outside trait M[A]? 
    //   because we don’t want to invoke it upon existing monadic object: ex. myMonad.unit("myBook")
    //   this does not make much sense

    // ex. M is Option and A is Int
    //     unit(): (Int => Option[Int]) 
    //     flatMap([function]): (Option[Int] => Option[Option[Int]]) => Option[Int] 
    //       [function]: Option[Int] => Option[Option[Int]]
    //     in other words,
    //                 (Option[Int] => Option[Option[Int]])                     flattened
    //     Option[Int] -----------------------------------> Option[Option[Int]] --------> Option[Int]

    // m map g = flatMap(x => unit(g(x)))

    def main(args: Array[String]) {
        // example1: Option
        //   a construct used to avoid null pointers in Scala (Maybe in Haskell)
    }
}
