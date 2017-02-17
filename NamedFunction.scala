// Scala supports first-class functions
//   functions can be expressed in function literal syntax
//   functions can be represented by objects (called function values)
// conventional use
// 0) normal function definition
//    syntax:
//    def functionName([list of parameters]): [return type] = {
//            return [expr] 
//        }
// 1) shorthand to nullary or unary function definition
//    syntax:
//    def/val/lazy val functionName: [function type] = [expr]
//    use with {} to define functions:
//      {} indicates a block of code composed of multiple statements and its value is that of the last statement
// 1.1) def [function]: it evaluates when called (this creates new function every time called)
//    syntax:
//    def functionName: [function type] = { 
//        function body
//        [expr]
//    }
// 1.2) val [function]: it evaluates when defined
//    syntax:
//    val functionName: [function type] = { 
//        function body
//        [expr]
//    }
// 1.3) lazy val [function]: it evaluates when called the first time 
//    syntax:
//    lazy val functionName: [function type] = { 
//        function body
//        [expr]
//    }

object NamedFunciton {
    def main(args: Array[String]) {
        // example 0:
        // 0) normal function defintion 
        def addInt(a:Int, b:Int): Int = {
            return a + b
        }
        println("addInt(1, 2) = " + addInt(1, 2))

        // example 1: shorthand to unary function definition
        def even1     : (Int => Boolean) = (_ % 2 == 0)
        val even2     : (Int => Boolean) = (_ % 2 == 0)
        lazy val even3: (Int => Boolean) = (_ % 2 == 0)
        println("even1(1) returns " + even1(1))
        println("even2(1) returns " + even2(1))
        println("even3(1) returns " + even3(1))

        // example 1: shorthand to nullary function definition
        // 1.1) def function: get new result every time called
        def rand1: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand1() 1st call: " + rand1())
        println("rand1() 2nd call: " + rand1())

        // 1.2) val function: get same result every time called
        val rand2: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand2() 1st call: " + rand2())
        println("rand2() 2nd call: " + rand2())
        // note: the above definition is different from the following
        // val rand2: (() => Int) = (() => util.Random.nextInt)

        // 1.3) lazy val function: get same result every time called
        lazy val rand3: (() => Int) = {
            val result = util.Random.nextInt
            () => result
        }
        println("rand3() 1st call: " + rand3())
        println("rand3() 2nd call: " + rand3())

        // example 3: unary function definition with pattern matching
        def printType: ((Any) => Unit) = {
            case x: Int    => println("Int")
            case y: String => println("String")
            case _         => println("Other")
        }
        printType(1)
        printType("two")
        printType(3.0)
    }
}

