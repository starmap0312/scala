// Extractor objects
// 1) apply():
//    when you treat your object like a function, apply is the method that is called
//    i.e. Scala turns obj(a, b, c) into obj.apply(a, b, c)
// 2) unapply():
//    used in Scala's pattern matching 
// common use case:
//    apply/map    : X -> Y
//    unapply/unmap: Y -> X (i.e. Some(X) / None)

object Extractor {
    object Twice {
        // apply() method is not necessary for pattern matching
        //   it is only used to mimick constructor
        //   ex. val x = Twice(21) is syntactic sugar for val x = Twice.apply(21)
        def apply(x: Int): Int = x * 2

        // case Twice(x): in pattern matching this will invoke Twice.unapply() method
        //   return value of unapply (None/Some) signals whether argument has matched or not
        //     in this case, it is used to match even number (return Some if even number)
        //   any sub-values that can be used for further matching
        //     in this case, the sub-value is (x / 2)
        //   return Boolean: if it's just a test
        //     ex. case test():
        //   return Option[T]: if it returns a single sub-value of type T 
        def unapply(x: Int): Option[Int] = {
            if (x % 2 == 0) {
                Some(x / 2)
            } else {
                None
            }
        }
    }

    def main(args: Array[String]) {
        // 1) apply():
        val twice1 = Twice(21)                   // invokes Twice.apply(21) 
        // is syntactic sugar for
        val twice2 = Twice.apply(21)             // apply/map parameter value: 21
        println(twice1)                          // prints 42 
        println(twice2)                          // prints 42 

        // 2) unapply():
        val Twice(x1) = 42                       // unapply/unmap 42 to its parameter value
        // is syntactic sugar for
        val x2: Int = Twice.unapply(42) match {
            case Some(x) => x
            case None    => throw new scala.MatchError(42)
        }
        println(x1)                             // prints 21
        println(x2)                             // prints 21
        // in other words, Twice.unapply(Twice.apply(21)) returns Some(21)

        // unapply() used in pattern matching
        42 match {                               // from val Twice(x) = 42, we can derive that x = 21 (because Twice.unapply(42) returns Some(21))
            case Twice(x) => Console.println(x)  // prints 21
        }
        println(Twice.unapply(42))               // prints Some(21) 
    }
}