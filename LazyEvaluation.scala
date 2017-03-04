object LazyEvaluation {
    def main(args: Array[String]) {
        // 1) variable definition
        val var1 = 2      // evaluated when defined    (immediate evaluation, evaluated once, storage allocated for the value)
        lazy val var2 = 2 // evaluated first-time used (lazy evaluation,      evaluated once, value stored/cached)
        def var3 = 2      // evaluated every-time used (lazy evaluation,      evaluated every time, no value stored/cached)
        println(var1)     // already evaluated
        println(var2)     // evaluated now
        println(var3)     // evaluated now

        // example: a random number
        // a) val [variable_name]: evaluated when defined (immediately)
        val rand1: Int = {
            util.Random.nextInt
        }
        // get the same result every-time used
        println("rand1 1st call: " + rand1)     // -1595828197 (already evaluated, when defined)
        println("rand1 2nd call: " + rand1)     // -1595828197 (already evaluated, when defined)

        // b) lazy val [variable_name]: evaluated first-time used 
        lazy val rand2: Int = {
            util.Random.nextInt
        }
        // get the same result every-time used
        println("rand2 1st call: " + rand2)     // 1836646499 (evaluated now, and value stored/cached)
        println("rand2 2nd call: " + rand2)     // 1836646499 (already evaluated)

        // c) def [variable_name]: evaluated every-time used
        def rand3: Int = {
            util.Random.nextInt
        }
        // get different results every-time used
        println("rand3 1st call: " + rand3)     // -2029134267 (evaluated now, computed online, no value cached)
        println("rand3 2nd call: " + rand3)     // 14851415250 (evaluated again, computed online, no value cached)

        // d) val [function_name] = [lambda expression]: evaluated every-time called
        val randf: (() => Int) = (() => util.Random.nextInt)
        // get different results every-time called 
        println("randf 1st call: " + randf())   // -679247471 (evaluated now)
        println("randf 2nd call: " + randf())   // -212673105 (evaluated again)

        // 2) call-by-value vs. call-by-name: evaluation of function arguments
        // a) call-by-value: func([variable_name]: [type])
        //      arguments are evaluated once, immediately when function is called
        //      evaluated before the function is used (i.e. before calling the function)
        def printRand1(x: Int) = {              // whenever function is called, val x: Int = [some value] is determined
            // get the same result every-time used
            println("1st print x = " + x)       // -1170373068 (already evaluated)
            println("2nd print x = " + x)       // -1170373068 (already evaluated) 
        }
        printRand1(randf())                     // value of x is determined when this line is invoked
        // b) call-by-name : func([variable_name]: => [type])
        //      arguments are NOT evaluated when function is called (lazy evaluation)
        //      arguments are evaluated everytime they are needed inside the function
        def printRand2(x: => Int) = {           // whenever function is called, def x: Int = [some value] is only declared, not evaluated
            // get different results every-time used
            println("1st print x = " + x)       // -958425394  (evaluated now)
            println("2nd print x = " + x)       // -1197101621 (evaluated now)
        }
        printRand2(randf())                     // value of x is only declared, not evaluated
    }
}
