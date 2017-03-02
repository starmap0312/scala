object Underscore {
    def main(args: Array[String]) {
        // 1) Ignored variables
        val _ = 5
        // 2) Ignored parameters of anonymous functions
        List(1, 2, 3).foreach(_ => println("Hi"))
        // 3) Partially applied functions
        List(1, 2, 3).foreach(println(_))
        List(1, 2, 3).foreach(println _)
        // 3) Wildcard imports
        import java.util._
        // 4) Wildcard patterns
        Some(5) match {                     // val Some(_) = Some.apply(5)
            case Some(_) => println("Yes")
        }
        // 
    }
}
