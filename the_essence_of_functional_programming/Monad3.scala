// Functors vs. Applicatives vs. Monads
object Monad3 {
    def main(args: Array[String]) {
        // 1) Functors: map() ... fmap() in Haskell
        //    it knows how to apply functions to values that are wrapped in a context
        //    ex1. Option (Some/None) is a Functor
        //      Some(2).map(x => x + 3) == Some(5)
        //      None.map(x => x + 3)    == None
        //    ex2. List is a Functor
        println(List(1, 2, 3).map( x => x + 3)) // List(4, 5, 6)
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
            if (x % 2== 0) {
                Some(x / 2)
            } else {
                None
            }
        }
        println(None.flatMap(half))       // None
        println(Some(3).flatMap(half))    // None
        println(Some(4).flatMap(half))    // Some(2)
        println(Some(16).flatMap(half).flatMap(half).flatMap(half)) // Some(2)
    }
}
