// Scala supports first-class functions
//   functions can be expressed in function literal syntax
//   functions can be represented by objects (called function values)
// 1) def [function]: it evaluates when called (this creates new function every time called)
//    syntax:
//    def functionName([list of parameters]): [return type] = {
//        function body
//        [expr]
//    }
// 2) val [function]: it evaluates when defined
//    syntax:
//    val functionName([list of parameters]): [return type] = {
//        function body
//        [expr]
//    }
// 3) lazy val [function]: it evaluates when called the first time 
//    syntax:
//    lazy val functionName([list of parameters]): [return type] = {
//        function body
//        [expr]
//    }

object NamedFunciton {
    def main(args: Array[String]) {
        // 0) normal function defintion 
        def addInt(a:Int, b:Int): Int = {
            return a + b
        }
        println("addInt(1, 2) = " + addInt(1, 2))

        // example 1:
        def even1     : (Int => Boolean) = _ % 2 == 0
        val even2     : (Int => Boolean) = _ % 2 == 0
        lazy val even3: (Int => Boolean) = _ % 2 == 0
        println("even1(1) returns " + even1(1))
        println("even2(1) returns " + even2(1))
        println("even3(1) returns " + even3(1))

        // example 2:
        // 1) def function: get new result every time called
        def rand1: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand1() 1st call: " + rand1())
        println("rand1() 2nd call: " + rand1())

        // 2) val function: get same result every time called
        val rand2: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand2() 1st call: " + rand2())
        println("rand2() 2nd call: " + rand2())
        // note: the above definition is different from the following
        // val rand2: (() => Int) = (() => util.Random.nextInt)

        // 3) lazy val function: get same result every time called
        lazy val rand3: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand3() 1st call: " + rand3())
        println("rand3() 2nd call: " + rand3())
    }
}

