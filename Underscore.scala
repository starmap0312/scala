object Underscore {
    def main(args: Array[String]) {
        // 1) Ignored variables
        // 1.1) Ignored variables
        val _ = 5
        // 1.2) Ignored parameters of anonymous functions
        List(1, 2, 3).foreach(_ => println("Hi"))
        // 1.3) Wildcard patterns
        Some(5) match {                     // i.e. val Some(x) = Some.apply(5), where x is ignored and replaced with _
            case Some(_) => println("Yes")
        }
        // 2) Placeholders
        // 2.1) Partially applied functions
        List(1, 2, 3).foreach(println(_))   // lambda expression: when writing lambda with no explicit argument type, Scala infers type from argument that foreach expects
        List(1, 2, 3).foreach(              // so the above is equavalent to the following
            { (x: Int) => println(x) }
        )
        List(1, 2, 3).foreach(println _)    // "process _" represents a function/method
        // 2.2) Placeholder syntax
        println(List(1, 2, 3).map(_ + 1))

        // 3) Wildcards
        // 3.1) Wildcard imports
        import java.util._
        // 
    }
}
