object LazyEvaluation {
    def main(args: Array[String]) {
        // 1) variable definition
        val var1 = 2      // evaluated when defined    (immediately,     once)
        lazy val var2 = 2 // evaluated first-time used (lazy evaluation, once)
        def var3 = 2      // evaluated every-time used (lazy evaluation, every time)
        println(var1)
        println(var2)
        println(var3)

        // example: a random number
        // a) val [variable_name]: evaluated when defined (immediately)
        val rand1: Int = {
            util.Random.nextInt
        }
        println("rand1 1st call: " + rand1)     // -1595828197 (same result every-time used)
        println("rand1 2nd call: " + rand1)     // -1595828197 (same result every-time used)
        // which is different from the following function definition (called every-time used) 
        val randf: (() => Int) = (() => util.Random.nextInt)
        println("randf 1st call: " + randf())   // -679247471 (different result every-time used)
        println("randf 2nd call: " + randf())   // -212673105 (different result every-time used)

        // b) lazy val [variable_name]: evaluated first-time used 
        lazy val rand2: Int = {
            util.Random.nextInt
        }
        println("rand2 1st call: " + rand2)     // 1836646499 (same result every-time used)
        println("rand2 2nd call: " + rand2)     // 1836646499 (same result every-time used)

        // c) def [variable_name]: evaluated every-time used
        def rand3: Int = {
            util.Random.nextInt
        }
        println("rand3 1st call: " + rand3)     // -2029134267 (different result every-time used)
        println("rand3 2nd call: " + rand3)     // 14851415250 (different result every-time used)

        // 2) call-by-value vs. call-by-name: evaluation of function arguments
        // a) call-by-value: func([variable_name]: [type])
        //      arguments are evaluated once, immediately when function is called
        //      evaluated before the function is used (i.e. before calling the function)
        def printRand1(x: Int) = {              // i.e. val x: Int = randf() once function is called
            println("1st print x = " + x)       // -1170373068 (same result every-time used)
            println("2st print x = " + x)       // -1170373068 (same result every-time used)
        }
        printRand1(randf())                     // value of x is determined when this line is invoked
        // b) call-by-name : func([variable_name]: => [type])
        //      arguments are NOT evaluated when function is called (lazy evaluation)
        //      arguments are evaluated everytime they are needed inside the function
        def printRand2(x: => Int) = {           // i.e. def x: Int = randf() once function is called
            println("1st print x = " + x)       // -958425394  (different result every-time used)
            println("2st print x = " + x)       // -1197101621 (different result every-time used)
        }
        printRand2(randf())
    }
}
