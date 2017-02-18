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

        // 2) function definition
        // 2.1) def [function]: evaluated when called (lazy evaluation, this creates new function every time called)
        //    syntax:
        //    def functionName: [function type] = { 
        //        function body
        //        [expr]
        //    }
        // 2.2) val [function]: evaluated when defined (evaluated immediately)
        //    syntax:
        //    val functionName: [function type] = { 
        //        function body
        //        [expr]
        //    }
        // 2.3) lazy val [function]: evaluated when called the first time (lazy evaluation and evaluated only once) 
        //    syntax:
        //    lazy val functionName: [function type] = { 
        //        function body
        //        [expr]
        //    }
        // example 1
        def even1     : (Int => Boolean) = (_ % 2 == 0)
        val even2     : (Int => Boolean) = (_ % 2 == 0)
        lazy val even3: (Int => Boolean) = (_ % 2 == 0)
        println("even1(1) returns " + even1(1))
        println("even2(1) returns " + even2(1))
        println("even3(1) returns " + even3(1))
        // example 2
        // get new result every time called (as this is lazy evaluation)
        def rand1: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand1() 1st call: " + rand1())
        println("rand1() 2nd call: " + rand1())

        // get same result every time called (as the value is already evaluated when defined)
        val rand2: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand2() 1st call: " + rand2())
        println("rand2() 2nd call: " + rand2())
        // note: the above definition is different from the following
        // val rand2: (() => Int) = (() => util.Random.nextInt)

        // get same result every time called (but this is also lazy evaluation when first time called)
        lazy val rand3: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand3() 1st call: " + rand3())
        println("rand3() 2nd call: " + rand3()) 
        

        // 3) function arguments evaluation: call by value vs. call by name
        // 3.1) call by value: evaluates the function arguments before calling the function
        def square1(x: Double) = {}
        // 3.2) call by name : evaluates the function first, and then evaluates the arguments if needed (lazy evaluation)
        def square2(x: => Double) = {} 
        // 3.3) define a sequence of int, containing a varying # of arguments
        def func(arg: Int*) = {}
    }
}
