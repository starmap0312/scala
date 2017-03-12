// Monad:
//   think of monads as wrappers: you just take an object and wrap it with a monad
//   Scala doesn’t come with a built-in monad type (unlike Haskell), so we need to model monad ourselves
//   it includes two operations (two natural transformations):
//   1) identity: unit()    in Scala (return in Haskell)
//   2) bind    : flatMap() in Scala (   >>= in Haskell)
// generic monad vs. concrete monad
//   1) generic monad : concept of monad
//   2) concrete monad: implement the two functions, actually doing something, ex. IO monad

object Monad2 {
    // model a monad with a generic trait that provides methods unit() and flatMap()
    trait M[A] {
        def flatMap[B](f: A => M[B]): M[B]
    }
    // M[A]: monads take a type parameter
    //   type parameter is like a label sticker on the wrap, saying what kind of object we have inside
    //     so we won't have surprise
    //   if we want to wrap some object with monad, we must parameterize the monad with the type of
    //     the underlying object, ex. M[Int], M[String], etc. 
    def unit[A](x: A): M[A]

    def main(args: Array[String]) {
    }
}
