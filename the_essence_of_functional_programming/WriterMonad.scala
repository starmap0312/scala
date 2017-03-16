// WriterMonad
//   the writer monad encapsulates a value and a log
// 1) half: Int -> Writer[Int, String]
//    half(x) =
//      apply(x / 2, log("half(" ++ show(x) ++ ") logged;"))
// 2) apply(value, log) =
//      Writer(value, log)
//    apply(value) =
//      Writer(value, "")
// 3) unapply(half(8)) =
//      (4, "half(8) logged")
//    (half(8) >>= half) =
//      (2, "half(8) logged;half(4) logged")
// 4) flatMap()/>>=: knows how to combine two writers
//    writer1 >>= func =
//      val (value1, log1) = unapply(writer1)
//      val (value2, log2) = unapply(func(value1))
//      apply(value2, log1++log2)
//    ex.  
//    apply(8).flatMap(half) = 
//      val (value1, log1) = half(8)              ...... unapply(half(8))      to get (value1, log1)
//      val (value2, log2) = half(value1)         ...... unapply(half(value1)) to get (value2, log2)
//      apply(value2, log1 ++ log2)               ...... returns Writer of value2 and the combined logs
//    so we can write code like:
//      (half(8) >>= half)   == half(8).flatMap(half)  == (half <=< half)(8) ... function composition with monads
object WriterMonad {

    def main(args: Array[String]) {
    }
}
