// converting a function with multiple arguments into a function with a single argument that returns another function
object Currying {
    def main(args: Array[String]) {
        // 1) normal version (type is (Int, Int) => Int)
        def sum1(a: Int, b: Int): Int = {
            return a + b
        }
        // 2) an uncurried version (type is Int => (Int => Int))
        def sum2(a: Int): (Int => Int) = {
            def inner(b: Int): Int = {
                return a + b
            }
            return inner
        }
        // 3) curried version (type is Int => Int => Int)
        def sum3(a: Int)(b: Int): Int = {
            return a + b
        }
        println(sum1(1, 2))
        println(sum2(1)(2))
        println(sum3(1)(2))
    }
}
