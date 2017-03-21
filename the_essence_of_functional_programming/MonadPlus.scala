// MonadPlus 
// 1) Monad with mzero and operator mplus (i.e. 0 and operator +)
// 2) mzero and operator mplus should satisfy the following rules (i.e. they form a monoid)
//    a) mzero mplus m     = m
//    b) m     mplus mzero = m
//    c) (m mplus n) mplus k = m mplus (n mplus k)
// example
// 1) Option Monad is a MonadPlus
//    mzero: None
//    mplus: returns the lefthand side value
//    ex.
//      None    mplus m = m
//      Some(x) mplus m = Some(x)
// 2) List Monad is a MonadPlus
//    mzero: List.empty / Nil
//    mplus: ++         / :::    / List.concate
object MonadPlus {

    def main(args: Array[String]) {
    }
}
