
object MapReduce {
    // define a reduce function that returns another function which is a recursive matching function
    def reduce1[T](f: ((T, T) => T), a: T): (List[T] => T) = {
        def func(list: List[T]) = list match {
            case Nil   => a
            case x::xs => f(x, reduce1(f, a)(xs))
        }
        return func
    }

    // shorthand: replace the above inner function with an anonymous function
    def reduce2[T](f: ((T, T) => T), a: T): (List[T] => T) = {
        list => list match {
            case Nil   => a
            case x::xs => f(x, reduce2(f, a)(xs))
        }
    } 

    // shorthand: simplify the anonymous function by omitting: list => list match
    def reduce3[T](f: ((T, T) => T), a: T): (List[T] => T) = {
        case Nil   => a
        case x::xs => f(x, reduce3(f, a)(xs))
    }

    // define a reduce function for map: (List[T] => List[T])
    def reduce[T](f: ((T, List[T]) => List[T]), a: List[T]): (List[T] => List[T]) = {
        case Nil   => a
        case x::xs => f(x, reduce(f, a)(xs))
    }

    def main(args: Array[String]) {
        // 1) add all the numbers of a list
        def add(x: Int, y: Int) = x + y
        // 1.1) define a sum(list) function:
        def sum: (List[Int] => Int) = {
            case Nil   => 0
            case x::xs => add(x, sum(xs))
        }
        println(sum(Nil))
        println(sum(List(1)))
        println(sum(List(1, 2)))
        println(sum(List(1, 2, 3)))
        // 1.2) define a more general function: reduce(f, a)
        def sum1 = reduce1(add, 0)
        println(sum1(Nil))
        println(sum1(List(1)))
        println(sum1(List(1, 2)))
        println(sum1(List(1, 2, 3)))
        def sum2 = reduce2(add, 0)
        println(sum2(Nil))
        println(sum2(List(1)))
        println(sum2(List(1, 2)))
        println(sum2(List(1, 2, 3)))
        def sum3 = reduce3(add, 0)
        println(sum3(Nil))
        println(sum3(List(1)))
        println(sum3(List(1, 2)))
        println(sum3(List(1, 2, 3)))

        // 2) multiply all the numbers of a list, i.e. product(List)
        def multiply(x: Int, y: Int) = x * y
        def product = reduce3(multiply, 1)
        println(product(Nil))
        println(product(List(1)))
        println(product(List(1, 2)))
        println(sum3(List(1, 2, 3)))

        // 3) test whether any of a list of booleans is true
        def or(x: Boolean, y: Boolean) = x || y 
        def anytrue = reduce3(or, false) 
        println(anytrue(Nil))
        println(anytrue(List(false, true, false)))

        // 4) test whether a list of booleans are all true
        def and(x: Boolean, y: Boolean) = x && y 
        def alltrue = reduce3(and, true) 
        println(alltrue(Nil))
        println(alltrue(List(true, false, true)))

        // 5) copy a list
        def cons[T](x: T, xs: List[T]): List[T] = x::xs
        def copylist[T] = reduce(cons[T], Nil)
        println(copylist(List(1, 2, 3)))

        // 6) double all elements of a list
        def doubleandcons(x: Int, xs: List[Int]): List[Int] = cons(2 * x, xs)
        def doubleall = reduce(doubleandcons, Nil)
        println(doubleall(List(1, 2, 3)))
        // further modualize doubleandcons()
        def fandcons[T](f: (T => T), x: T, xs: List[T]): List[T] = cons(f(x), xs)
        //def fandcons2[T]: ((T => T), T, List[T]) => List[T]: 
    }
}
