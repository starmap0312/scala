// converting a function with multiple arguments into a function with a single argument that returns another function
object Currying {
    def main(args: Array[String]) {
        // 1) method definition: (a: Int, b: Int)Int 
        def sum1(a: Int, b: Int): Int = {
            return a + b
        }
        // 2) method definition: (a: Int)Int => Int 
        def sum2(a: Int): (Int => Int) = {
            def inner(b: Int): Int = {     // method definition: (b: Int)Int
                return a + b
            }
            inner                          // converted into a Function1 by compiler
        }
        // 3) method definition (curried version): (a: Int)(b: Int)Int
        def sum3(a: Int)(b: Int): Int = {
            return a + b
        }
        println(sum1(1, 2))                // 3
        println(sum2(1)(2))                // 3
        println(sum3(1)(2))                // 3
        println((sum1(_, _))(1, 2))        // 3: sum1(_, _) is converted into (Int, Int) => Int, i.e. Function2, by compiler
        println((sum3(1)(_))(2))           // 3: sum3(1)(_) is converted into Int => Int, i.e. Function1, by compiler
    }
}
