// 

object EvaluationRules {
    def main(args: Array[String]) {
        def example = 2      // evaluated only when called (lazy evaluation)
        val example = 2      // evaluated immediately
        lazy val example = 2 // evaluated once when needed

        def square(x: Double)    // call by value
        def square(x: => Double) // call by name
        def myFct(bindings: Int*) = { ... } // bindings is a sequence of int, containing a varying # of arguments
    }
}
