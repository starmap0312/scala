// Scala supports first-class functions
//   functions can be expressed in function literal syntax
//   functions can be represented by objects (called function values)
// 1) def [function]: it evaluates when called (this creates new function object every time called)
//    syntax:
//    def functionName([list of parameters]): [return type] = {
//        function body
//        return [expr]
//    }
// 2) val [function]: it evaluates when defined
//    syntax:
//    val functionName([list of parameters]): [return type] = {
//        function body
//        return [expr]
//    }
// 3) lazy val [function]: it evaluates when called the first time 
//    syntax:
//    lazy val functionName([list of parameters]): [return type] = {
//        function body
//        return [expr]
//    }
//  ex.
//    def even: Int => Boolean = _ % 2 == 0
//    val even: Int => Boolean = _ % 2 == 0
//    lazy val even: Int => Boolean = _ % 2 == 0

object NamedFunciton {
    def main(args: Array[String]) {
        // 0) basic use
        def addInt(a:Int, b:Int): Int = {
            return a + b
        }
        println(addInt(1, 2))

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

        // 3) lazy val function: get same result every time called
        lazy val rand3: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand3() 1st call: " + rand3())
        println("rand3() 2nd call: " + rand3())
    }
}

