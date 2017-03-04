// 1) treat function as a function parameter, or
// 2) return a function
object HigherOrderFunctions {
    def main(args: Array[String]) {
        // 1) function composition: ex. applying function in sequence, ex. g.f(x) = g(f(x))
        //    f  : X -> Y
        //    g  : Y -> Z
        //    g.f: X -> Z
        def f(x: Int): Int = (x * x) 
        def g(x: Int): Int = (x + 1) 
        def g_of_f1(x: Int): Int = g(f(x))
        def g_of_f2 = g _ compose f _
        def g_of_f3 = f _ andThen g _
        println(g_of_f1(3))
        println(g_of_f2(3))
        println(g_of_f3(3))
        // 2) functions that take function a parameter (callback), or return a function object
        //    i.e. a function object that has other function as one of its characteristics, or
        //         a function whose behavior is to produce another function object (like function factory)
        def sum_f1(callback: Int => Int): ((Int, Int) => Int) = {  
            // it takes a callback function of type (Int => Int) as parameter, and
            // it returns a function of type (Int, Int) => Int
            def sum(a: Int, b: Int): Int = {
                return callback(a) + callback(b)
            }
            return sum                           // returns a function object of type ((Int, Int) => Int) 
        } 
        def sum_square = sum_f1((x: Int) => x * x)
        def sum_cube   = sum_f1((x: Int) => x * x * x)
        println(sum_square(1, 2))
        println(sum_cube(1, 2))
   
        // syntactic suger:
        //   flattening the inner function in the above example, as it is not used outside and can be anonymous
        def sum_f2(callback: Int => Int)(a: Int, b: Int): Int = { // a shorthand of defining the function in the above example
             return callback(a) + callback(b)
        }
        println(sum_f1((x: Int) => x * x)(1, 2))
        println(sum_f2((x: Int) => x * x)(1, 2))
    }
}

