// ReaderMonad
//   suppose you want to pass some config a lot of functions
//   the reader monad lets you pass a value to all the functions behind the scenes
// 1) greet: () -> Reader[String -> String]
//    greet() =
//      Reader(
//          \config -> "hello, " ++ config
//      )
// 2) apply(config, a) =
//      Reader(\config -> a)
// 3) unapply(apply(config, a)) =
//      \confg -> a
//    ex.
//    unapply(greet()) =
//      \config -> apply("hello, " ++ config)
//    ex.
//    unapply(greet())("andy") = 
//      "hello, andy"
// 3) ask() =
//      Reader(\config -> config)
// 5) flatMap()/>>=: 
//    m >>= f =
//      Reader(
//        \config -> (unapply(f((unapply(m))(config))))(config)
//      )
object ReaderMonad {

    def main(args: Array[String]) {
    }
}
