// 

object EvaluationRules {
    def main(args: Array[String]) {
        // 1) variable definition
        // 1.1) evaluated only when called (lazy evaluation)
        def example1 = 2
        // 1.2) evaluated immediately
        val example2 = 2
        // 1.3) evaluated once when needed (lazy evaluation and evaluated only once)
        lazy val example3 = 2
        println(example1)
        println(example2)
        println(example3)

        // example: get a random number
        // get new result every time called (as this is lazy evaluation)
        def rand1: Int = {
            util.Random.nextInt
        }
        println("rand1 1st call: " + rand1)
        println("rand1 2nd call: " + rand1)

        // get same result every time called (as the value is already evaluated when defined)
        val rand2: Int = {
            util.Random.nextInt
        }
        println("rand2 1st call: " + rand2)
        println("rand2 2nd call: " + rand2)
        // note: the above definition is different from the following
        // val rand2: (() => Int) = (() => util.Random.nextInt)

        // get same result every time called (but this is also lazy evaluation when first time called)
        lazy val rand3: Int = {
            util.Random.nextInt
        }
        println("rand3 1st call: " + rand3)
        println("rand3 2nd call: " + rand3) 
        

        // 3) function arguments evaluation: call by value vs. call by name
        // 3.1) call by value: evaluates the function arguments before calling the function
        def square1(x: Double) = {}
        // 3.2) call by name : evaluates the function first, and then evaluates the arguments if needed (lazy evaluation)
        def square2(x: => Double) = {} 
        // 3.3) define a sequence of int, containing a varying # of arguments
        def func(arg: Int*) = {}
    }
}
