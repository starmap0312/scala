// Scala supports first-class functions
//   functions can be expressed in function literal syntax: a method
//   functions can be represented by objects: a function value, i.e. FunctionN instance
// 1) method: (function literal syntax)
//    syntax:
//    def functionName([list of parameters]): [return type] = {
//        return [expr] 
//    }
//    use {} to define multiple statements
//    ({} indicates a block of code composed of multiple statements and its value is that of the last statement)
// 2) function (function value):
//    syntax:
//    def/val/lazy val functionName: [return type] = [expr/expr with _ variable]
//    shorthand to nullary or unary function definition, i.e. _ indicates the anonymous single argument

object NamedFunciton {
    def main(args: Array[String]) {
        // 1) method (function literal syntax)
        def add(a:Int, b:Int): Int = {
            println("statement 1")
            println("statement 2")
            a + b                       // note: return can be omitted
        }
        println("1 + 2 = " + add(1, 2)) // a new function object add(1, 2) is created when called

        // 2) function (function value)
        // ex1
        def even1(x: Int): Boolean = (x % 2 == 0)       // function literal syntax
        def even2     : (Int => Boolean) = (_ % 2 == 0) // function value
        val even3     : (Int => Boolean) = (_ % 2 == 0) // function value 
        lazy val even4: (Int => Boolean) = (_ % 2 == 0) // function value
        println("even1(1) returns " + even1(1))
        println("even2(1) returns " + even2(1))
        println("even3(1) returns " + even3(1))
        println("even4(1) returns " + even4(1))

        // ex2
        def f(x: String): String = "f(" + x + ")"
        def g(x: String): String = "g(" + x + ")"
        val g_of_f1: (String => String) = (g _ compose f _)
        def g_of_f2(x: String) = g(f(x))
        println(g_of_f1("x"))
        println(g_of_f2("x"))

        // ex3
        def toInt1(x: String): Int = x match { // method: (x: String)Int
            case "1" => 1
            case "2" => 2
            case _   => 100
        }
        def toInt2 = (x: String) => x match {  // function: String => Int 
            case "1" => 1
            case "2" => 2
            case _   => 100
        }
        // a shorthand of defining the above match function 
        def toInt3: (String => Int) = {        // function: String => Int
            case "1" => 1
            case "2" => 2
            case _   => 100
        }
        println(toInt1("1") + toInt1("2") + toInt1("3")) // 103
        println(toInt2("1") + toInt2("2") + toInt2("3")) // 103
        println(toInt3("1") + toInt3("2") + toInt3("3")) // 103
    }
}

