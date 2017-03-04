object Underscore {
    def main(args: Array[String]) {
        // 1) Ignored variables
        // 1.1) Ignored variables
        val _ = 5
        val x = 5                                 // the above is equavalent to the following, and x is ignored
        // 1.2) Ignored parameters of anonymous functions
        List(1, 2).foreach(_ => println("Hi"))
        List(1, 2).foreach(x => println("Hi")) // the above is equavalent to the following, and x is ignored
        // 1.3) Wildcard patterns
        Some(5) match {
            case Some(_) => println("Yes")
        }
        Some(5) match {                     // the above is equavalent to the following 
            case Some(x) => println("Yes")  // i.e. val Some(x) = Some.apply(5), and x is ignored (replaced with _)
        }
        // 2) Placeholders
        // 2.1) Partially applied functions
        List(1, 2, 3).foreach(println(_))   // lambda expression: writing lambda with no explicit argument type
                                            //   compiler will infer type from argument that foreach expects
        List(1, 2, 3).foreach(              // the above is equavalent to the following
            { (x: Int) => println(x) }
        )
        // 2.2) Placeholder syntax
        println(List(1, 2, 3).map(_ + 1))   // _ + 1 represents a lambda expression
        println(List(1, 2, 3).map(          // the above is equavalent to the following
            { (x: Int) => (x + 1) }
        ))

        // 3) Wildcards
        // 3.1) Wildcard imports
        import java.util._
    }
}
