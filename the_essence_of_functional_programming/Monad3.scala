// Functions in Haskell:
//   all functions in Haskell take one parameter
//   so function of type A -> B -> C is interpreted as:
//     A -> (B -> C)
//   i.e. f(x, y) == (f(x))(y)
//   function with two parameters of types A and B == function of type A that returns a function of type (B -> C)
//   this enables us to partially apply functions by just calling them with too few parameters
//     which results in functions that can be further passed on to other functions
// Functors vs. Applicatives vs. Monads
// 1) Functors: (<$> in Haskell)
//    (<$>):: (Functor M) => (A -> B) -> (M[A] -> M[B])
//    it takes a function of type (A -> B) and returns a function of type M[A] -> M[B]
//      i.e. <$> takes a function and returns a new function that takes functor as parameter and returns functor as result (also called "lifting" a function)
//    you can think of a functor as:
//    a) a function that takes a function and lifts that function so that it operates on functors
//    b) a function that takes a function and a functor and then maps that function over the functor
//    ex. Option is a Functor
//        fmap:: (A -> B) -> (Option[A] -> Option[B])
//          fmap(f)(Some(x))    == Some(f(x))
//          fmap(f)(None)       == None
//        so we have
//          fmap(*2)(Some(200)) == Some(400)
//          fmap(*2)(None)      == None
//    ex. List is a Functor
//        fmap:: (A -> B) -> (List[A] -> List[B])
//          fmap(f)(List(1, 2, 3))  = List(f(1), f(2), f(3))
//          fmap(f)(Nil)            = Nil
//    Functor Laws:
//    a) fmap(id) = id, where id: \x -> x is an identity function
//       ex1. (fmap(id))(Some(3))       = Some(id(3))               = Some(3)       = id(Some(3))
//            (fmap(id))(None)                                      = None          = id(None)
//       ex2. (fmap(id))(List(1, 2, 3)) = List(id(1), id(2), id(3)) = List(1, 2, 3) = id(List(1, 2, 3))
//            (fmap(id))(Nil)                                       = Nil           = id(Nil)
//    b) fmap(f . g)     = fmap(f) . fmap(g)
//       fmap(f . g))(m) = (fmap(f))((fmap(g))(m)), where m: M is a Functor
//       i.e. composing two functions and then mapping the resulting function over a functor
//            is equal to first mapping one function over the functor and then mapping the other one
//       ex. (fmap(f . g))(Some(x)) = Some((f . g)(x)) = Some(f(g(x)))
//           (fmap(f))((fmap(g))(Some(x))) = (fmap(f))(Some(g(x))) = Some(f(g(x)))
//
// 2) Applicatives: (<*> in Haskell, more powerful than Functors)
//    both values and functions are wrapped in a context
//    (<*>) :: (Applicative M) => M[A -> B] -> (M[A] -> M[B])
//    object Applicative Option:
//        apply(x) = Some(x)
//        (<*>(None))(_) = None
//        (<*>(Some(f)))(Some(x)) = (fmap(f))(Some(x)) = Some(f(x))
//    Applicatives can do what Functors cannot
//      ex1. apply a function that takes two arguments to two wrapped values
//                 (fmap(+))(Some(5)) == Some(+5)        ...... function is wrapped in a context too (one parameter is already passed-in)
//          (fmap(Some(+5)))(Some(3))                    ...... error: fmap takes only function, not a functor 
//           (<*>(Some(+5)))(Some(3)) == Some(8)         ...... Applicative can take a wrapped function 
// (<*>((fmap(+))(Some(5))))(Some(3)) == Some(8)         ...... use Functor and Applicative, we can apply a function to multiple wrapped Functors 
//      i.e. ((+) <$> Some(5)) <*> Some(3) == (+) <$> Some(5) <*> Some(3) == Some((+) 5 3) 
//    example: Option
//      (<*>(Some(+3)))(Some(9))           = Some(12)
//      (<*>(Some(++"hello")))(None)       = None 
//      (<*>(None))(Some("hello"))         = None 
//    example:
//      max <$> Some(3) <*> Some(6)        = Some(max(3)(6))
//      max <$> Some(3) <*> None           = None
//    example:
//      (*) <$> List(1, 2, 3) <*> List(10, 100) = List(10, 100, 20, 200, 30, 300)
//          
// 3) Monads: (>>= in Haskell, Monads are just Functors that support flatMap/bind)
//    (>>=) :: (Monad M) => M[A] -> ((A -> M[B]) -> M[B])
//    class Monad M:
//        apply: A -> M[A]
//        flatMap: M[A] -> (A -> M[B]) -> M[B]
//    ex.
//    object Monad Option:
//        apply(x)           = Some(x)  
//        None.flatMap(f)    = None,    where f: A -> M[B]
//        Some(x).flatMap(f) = f(x),    where f: A -> M[B]
//        fail(_)            = None                        // used in pattern matching (when no case matched)          
//    ex.
//    object Monad List:
//        apply(x)           = List(x)
//        xs.flatMap(f)      = flatten((fmap(f))(xs))
//        fail(_)            = Nil                   // used in pattern matching (when no case matched)
//    (note: Nil is the equivalent of None, it signifies the absence of result)
//    example:
//      List(1, 2, 3).flatMap(x => List(x, -x)) = List(3, -3, 4, -4, 5, -5)
//      x => List(x, -x)     ...... a function that returns a non-deterministic value
//    example:
//      Nil.flatMap(x => List(x, -x))           = Nil

object Monad3 {
    def main(args: Array[String]) {
        // 1) Functors: map() ... fmap() in Haskell
        //    it knows how to apply functions to values that are wrapped in a context
        //    ex1. Option (Some/None) is a Functor
        //      Some(2).map(x => x + 3) == Some(5)
        //      None.map(x => x + 3)    == None
        //    ex2. List is a Functor
        println(List(1, 2, 3).map(x => x + 3)) // List(4, 5, 6)
        //    ex3. Function is a Functor
        //      val f = (x: Int) => x + 3
        //      val g = (y: Int) => y + 2
        //      f.map(g) == ((x: Int) => g(x) + 3) ... unwrap f(x) to x and returns function x => g(x) + 3

        // 2) Applicatives
        //    it knows how to apply function wrapped in a context to value wrapped in a context

        // 3) Monads: flatMap ... >== in Haskell, | in python
        //    it knows how to apply a function that returns a wrapped value to a wrapped value
        //    ex1. Option (Some/None) is a Monad
        def half(x: Int): Option[Int] = { // a function that returns a wrapped value
            if (x % 2== 0) Some(x / 2) else None
        }
        println(None.flatMap(half))       // None
        println(Some(3).flatMap(half))    // None
        println(Some(4).flatMap(half))    // Some(2)
        println(Some(16).flatMap(half).flatMap(half).flatMap(half)) // Some(2)
        //    ex2. List is a Monad
        println(List(1, 2, 3).map(_ + 3)) // List(4, 5, 6)
        println(List(1, 2, 3).flatMap(_ => Nil))    // List()
        println(Nil.map(_ => List(1, 2, 3)))        // List()
        // 4) List comprehensions: a syntactic sugar for binding monads
        //    for-in construct
        val list1 = for {
            n <- List(1, 2, 3, 4) if (n % 2 == 0)
            c <- List('a', 'b')
        } yield (n, c)
        // (is a syntactic sugar for)
        val list2 = List(1, 2, 3, 4).filter(
             _ % 2 == 0
        ).flatMap(
            (n: Int) => List('a', 'b').map(
                (c: Char) => (n, c)
            )
        )
        println(list1)                     // List((2,a), (2,b), (4,a), (4,b))
        println(list2)                     // List((2,a), (2,b), (4,a), (4,b))
    }
}
