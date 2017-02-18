// take a function as a parameter or return functions
object HigherOrderFunctions {
    def main(args: Array[String]) {
        // function composition, i.e. g(f(x))
        def f(x: Int): Int = (x * x) 
        def g(x: Int): Int = (x + 1) 
        def g_of_f1(x: Int): Int = g(f(x))
        def g_of_f2 = g _ compose f _
        def g_of_f3 = f _ andThen g _
        println(g_of_f1(3))
        println(g_of_f2(3))
        println(g_of_f3(3))
        // pass in a callback function/lambda expression/return a function object
        def sum_f(callback: Int => Int): ((Int, Int) => Int) = {  
            def sum(a: Int, b: Int): Int = {
                return callback(a) + callback(b)
            }
            return sum                           // returns a function object of type ((Int, Int) => Int) 
        } 
        def sum_square = sum_f((x: Int) => x * x)
        def sum_cube   = sum_f((x: Int) => x * x * x)
        println(sum_square(1, 2))
        println(sum_cube(1, 2))
   
        // shorthand: define a function of type (Int => Int) => (Int, Int) => Int that works like the above 
        // note: sum_f2 takes a callback function of type (Int => Int) as parameter and returns a function of type (Int, Int) => Int
        def sum_f2(callback: Int => Int)(a: Int, b: Int): Int = {
             return callback(a) + callback(b)
        }
        println(sum_f2((x: Int) => x * x)(1, 2))
        println(sum_f2((x: Int) => x * x * x)(1, 2))
    }
}

