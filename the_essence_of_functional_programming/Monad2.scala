// Monad:
//   think of monads as wrappers: take an object and wrap it with a monad
//   every wrapper that provides us with two operations: apply() and flatMap(), is a monad
//     apply([object]):      A => M[A]          ... also called identity (return in Haskell)
//       an operation that creates a monad M[A] from an object of type A, i.e. apply() method
//     flatMap([function]): (A => M[B]) => M[B] ... also called bind     (   >>= in Haskell)
// Option in Scala is a monad:
//   Option(a) = None | Some(a)
// Examples: 
//   List/Set/Option/Future are monads (note: Future breaks the referential transparency though)
//   they all have apply() and flatMap() methods
//   apply() method: List(x), Set(x), Some(x)/Option(x), Future(x)
//     a syntactic sugar of: List.apply(3) == List(3)
// Scala doesn’t come with a built-in monad type (unlike Haskell), we need to model monad ourselves
//   there is no actual monad type class in plain Scala
//     i.e. constructs such as List, Option, Future etc. don’t extend any special Monad trait
//   this means that they are not obligated to provide us with methods apply() and flatMap()
//   the obligation is developer's responsibility
// Monad laws: monad must satisfy three laws
// Given:
//   x: some value/object
//   m: a monad instance (wrapping some value/object)
//   f and g: functions of type (Int => M[Int])
// The Laws:
//   1) left-identity law : apply(x).flatMap(f)     == f(x)
//      i.e. apply(x) does not have side effect 
//   2) right-identity law: m.flatMap(apply)        == m
//      i.e. unapply m to x and then apply(x) should not change anything
//   3) associativity law : m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
//      i.e. similar to function composition: f(g(x)) == (f . g)(x)
//           unwrap/unapply m to x, and then f(x), and then unwrap/unapply f(x) to y, and then g(y)
//             should be equavalent to unwrap/unapply m to x, and then f(x).flatMap(g)
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
    // model a monad with a generic trait that provides methods apply() and flatMap()

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

        // apply([object]): A => M[A]
        //   it simply performs the wrapping part, like a monad constructor
        def apply[A](x: A): M[A]
        // it's better if we can define method apply() outside trait M[A]
        //   because we don’t want to invoke it upon existing monadic object: ex. myMonad.apply("myBook")
        //   this does not make much sense
    }

    def main(args: Array[String]) {
        // Monad laws
        // 1) Left identity
        //      if we put a value x in a default context with apply(), then fed into a function by using flatMap()
        //      it is the same as just applying the function to value x 
        //      i.e. apply(x).flatMap(f) == f(x)
        // 1.1) Option:
        val x1 = 3
        def f1(x: Int) = Option.apply(x + 1)  // Some(4)
        println(Option.apply(x1).flatMap(f1)) // Some(4)
        println(f1(x1))
        // 1.2) List
        val x2 = 3
        def f2(x: Int) = List.apply(x - 1, x, x + 1)
        println(List.apply(x2).flatMap(f2))   // List(2, 3, 4)
        println(f2(x2))                       // List(2, 3, 4)

        // 2) Right identity
        //    if a monadic value m is fed to apply() using flatMap(), the result is the original monadic value
        //    i.e. m.flatMap(apply) == m
        // 2.1) Option:
        val m1 = Option(3)
        println(m1.flatMap(Option.apply(_)))     // Some(3)
        println(m1)                              // Some(3)
        // 2.2) List:
        val m2 = List(2, 3, 4)
        println(m2.flatMap(List.apply(_)))          // List(2, 3, 4)
        println(m2)                                 // List(2, 3, 4)

        // 3) Associativity
        //    if we have a chain of monadic function applications with flatMap()
        //    it does not matter how they are nested
        //    i.e. (m.flatMap(f)).flatMap(g) == m.flatMap({ x => f(x).flatMap(g) })
        // 3.1) Option:
        val m3 = Option(2)
        def f3(x: Int) = Option(x + 1)
        def g3(x: Int) = if (x % 2 == 0) Some(x / 2) else None
        println(m3.flatMap(f3).flatMap(g3))                // None 
        println(m3.flatMap((x: Int) => f3(x).flatMap(g3))) // None
        println(m3.flatMap(f3(_).flatMap(g3)))             // None
        // 3.2) List:
        val m4 = List(3)
        def f4(x: Int) = List(x - 1, x, x + 1)
        def g4(x: Int) = List(x * 2, x * 3)
        println(m4.flatMap(f4).flatMap(g4))                // List(4, 6, 6, 9, 8, 12)
        println(m4.flatMap((x: Int) => f4(x).flatMap(g4))) // List(4, 6, 6, 9, 8, 12)
        println(m4.flatMap(f4(_).flatMap(g4)))             // List(4, 6, 6, 9, 8, 12)

        // Option: a construct used to avoid null pointers in Scala (Maybe in Haskell)
        // ex. M is Option and A is Int
        //     apply(): (Int => Option[Int]) 
        //     flatMap([function]): (Option[Int] => Option[Option[Int]]) => Option[Int] 
        //       [function]: Option[Int] => Option[Option[Int]]
        //     in other words,
        //                 (Option[Int] => Option[Option[Int]])                     flattened
        //     Option[Int] -----------------------------------> Option[Option[Int]] --------> Option[Int]
    }
}
