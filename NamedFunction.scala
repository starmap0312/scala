// Scala supports first-class functions
//   functions can be expressed in function literal syntax
//   functions can be represented by objects (called function values)
// conventional
// 0) normal function definition
//    syntax:
//    def functionName([list of parameters]): [return type] = {
//            return [expr] 
//        }
//    use {} to define multiple statements
//    ({} indicates a block of code composed of multiple statements and its value is that of the last statement)
// 1) shorthand to nullary or unary function definition
//    syntax:
//    def/val/lazy val functionName: [return type] = [expr/expr with _ variable]
//    (note: _ indicates the anonymous single argument)

object NamedFunciton {
    def main(args: Array[String]) {
        // normal function defintion 
        def add(a:Int, b:Int): Int = {
            return a + b                // return can be omitted
        }
        println("1 + 2 = " + add(1, 2)) // a new function object add(1, 2) is created when called

        // use of {} block
        def ten: Int = {
            println("statement 1")
            println("statement 2")
            return 10
        }
        println("ten = " + ten)         // a new variable object ten is created when called

        // shorthand to unary function definition (explicit argument names can be omitted)
        def even1(x: Int): Boolean = (x % 2 == 0)
        def even2: (Int => Boolean) = (_ % 2 == 0)
        println("even1(1) returns " + even1(1))
        println("even2(1) returns " + even2(1))
    }
}

