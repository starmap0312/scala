// practical examples of higher-order functions
object MapReduce {
    // example 1: reduce function that returns a value 
    // 1) normal definition: define and return a named inner function 
    def reduce1[T](f: ((T, T) => T), a: T): (List[T] => T) = {
        // a recursive function that takes a function parameter and returns a new function
        def func(list: List[T]) = list match {
            case Nil   => a
            case x::xs => f(x, reduce1(f, a)(xs))
        }
        return func
    }
    // 2) use an anonymous inner function as it is not used except for got returned
    def reduce2[T](f: ((T, T) => T), a: T): (List[T] => T) = {
        list => list match {
            case Nil   => a
            case x::xs => f(x, reduce2(f, a)(xs))
        }
    } 
    // 3) syntactic sugar: omitting "list => list match"
    def reduce3[T](f: ((T, T) => T), a: T): (List[T] => T) = {
        case Nil   => a
        case x::xs => f(x, reduce3(f, a)(xs))
    }

    // example 2: a more general reduce function that returns a list 
    def reduce4[T](f: ((T, List[T]) => List[T]), a: List[T]): (List[T] => List[T]) = {
        case Nil   => a
        case x::xs => f(x, reduce4(f, a)(xs))
    }

    // a general map() function based on reduce function and function composition
    // 1) List Version
    def map[T](f: (T => T)): (List[T] => List[T]) = { 
        def cons(x: T, xs: List[T]): List[T] = x::xs
        //def fandcons(f: (T => T)): ((T, List[T]) => List[T]) = {
        //    (x: T, xs: List[T]) => cons(f(x), xs)
        //}
        def fandcons(f: (T => T))(x: T, xs: List[T]) = cons(f(x), xs)
        def reduce(f: ((T, List[T]) => List[T]), a: List[T]): (List[T] => List[T]) = {
            case Nil   => a
            case x::xs => f(x, reduce4(f, a)(xs))
        }
        reduce(fandcons(f), Nil)
    }

    // 2) Stream Version 
    def map2[T](f: (T => T)): (Stream[T] => Stream[T]) = {
        def fandcons(f: (T => T))(x: T, xs: Stream[T]) = Stream.cons(f(x), xs)
        def reduce(f: ((T, Stream[T]) => Stream[T]), a: Stream[T]): (Stream[T] => Stream[T]) = {
            case x#::xs       => f(x, reduce(f, a)(xs))
            case _            => a
        }
        reduce(fandcons(f), Stream.empty)
    }

    def main(args: Array[String]) {
        // example 1: reduce function that returns a value
        // 1.1) explcitly define a recursive function of sum(List) function
        //      i.e. sum up all the numbers of a list
        def add(x: Int, y: Int) = x + y
        def sum: (List[Int] => Int) = {
            case Nil   => 0
            case x::xs => add(x, sum(xs))
        }
        println(sum(List(1, 2)))
        // 1.2) use the higher-order reduce(f, a) function to generate the sum(List) function
        def sum1 = reduce1(add, 0)
        def sum2 = reduce2(add, 0)
        def sum3 = reduce3(add, 0)
        println(sum1(List(1, 2)))
        println(sum2(List(1, 2)))
        println(sum3(List(1, 2)))
        // 1.3) use the higher-order reduce(f, a) function to generate the product(List) function
        //      i.e. multiply all the numbers of a list: product(List)
        def multiply(x: Int, y: Int) = x * y
        def product = reduce3(multiply, 1)
        println(product(List(1, 2)))

        // 1.4) use the higher-order reduce(f, a) function to generate the anytrue(List) function
        //      i.e. test whether any of a list of booleans is true
        def or(x: Boolean, y: Boolean) = x || y 
        def anytrue = reduce3(or, false) 
        println(anytrue(List(false, true, false)))

        // 1.5) use the higher-order reduce(f, a) function to generate the alltrue(List) function
        //      i.e. test whether a list of booleans are all true
        def and(x: Boolean, y: Boolean) = x && y 
        def alltrue = reduce3(and, true) 
        println(alltrue(List(true, false, true)))

        // example 2: a more general reduce function that returns a list 
        // 2.1) use the higher-order reduce(f, a) function to generate the copylist(List) function
        //      i.e. create a new list by copying all elements of a list
        def cons[T](x: T, xs: List[T]): List[T] = x::xs
        def copylist[T] = reduce4(cons[T], Nil)
        println(copylist(List(1, 2, 3)))

        // 2.2) use the higher-order reduce(f, a) function to generate the doubleall(List) function
        //      i.e. create a new list by doubling all elements of a list
        def double(x: Int): Int = 2 * x
        def doubleandcons(x: Int, xs: List[Int]): List[Int] = cons(double(x), xs)
        def doubleall1 = reduce4(doubleandcons, Nil)
        // 2.3) further modualize the doubleandcons() function
        def fandcons[T](f: (T => T)): ((T, List[T]) => List[T]) = {
            (x: T, xs: List[T]) => cons(f(x), xs)
        } 
        def doubleall2 = reduce4(fandcons(double), Nil) // you can use syntax suger: compose or andThen
        // 2.4) use the higher-order map(f) function
        def doubleall3 = map(double)
        def doubleall4 = map2(double)
        println(doubleall1(List(1, 2, 3)))
        println(doubleall2(List(1, 2, 3)))
        println(doubleall3(List(1, 2, 3)))
        doubleall4((1 to 3).toStream).foreach(print)
        println()
    }
}
