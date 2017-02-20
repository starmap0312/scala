
object MapReduce {
    // define a general reduce function that returns another function which is a recursive matching function
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

    // define a general reduce function based on recursion 
    def reduce4[T](f: ((T, List[T]) => List[T]), a: List[T]): (List[T] => List[T]) = {
        case Nil   => a
        case x::xs => f(x, reduce4(f, a)(xs))
    }

    // define a general map function based on reduce function and function composition
    def map[T](f: (T => T)): (List[T] => List[T]) = { 
        def cons(x: T, xs: List[T]): List[T] = x::xs
        def fandcons(f: (T => T)): ((T, List[T]) => List[T]) = {
            (x: T, xs: List[T]) => cons(f(x), xs)
        }
        def reduce(f: ((T, List[T]) => List[T]), a: List[T]): (List[T] => List[T]) = {
            case Nil   => a
            case x::xs => f(x, reduce4(f, a)(xs))
        }
        reduce(fandcons(f), Nil)
    }
    // def cons[T](x: T, xs: List[T]): List[T] = x::xs
    // def cons[T](x: T, xs: Stream[T]): Stream[T] = x#::xs

    // replace List[T] with Stream[T]
    def map2[T](f: (T => T)): (Stream[T] => Stream[T]) = {
        def cons(x: T, xs: Stream[T]): Stream[T] = x#::xs
        def fandcons(f: (T => T)): ((T, Stream[T]) => Stream[T]) = {
            (x: T, xs: Stream[T]) => cons(f(x), xs)
        }
        def reduce(f: ((T, Stream[T]) => Stream[T]), a: Stream[T]): (Stream[T] => Stream[T]) = {
            case x#::xs       => f(x, reduce(f, a)(xs))
            case _            => a
        }
        reduce(fandcons(f), Stream.empty)
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
        // 1.2) further generalize by defining a reduce(f, a) function
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
        def copylist[T] = reduce4(cons[T], Nil)
        println(copylist(List(1, 2, 3)))

        // 6) double all elements of a list
        def double(x: Int): Int = 2 * x
        def doubleandcons(x: Int, xs: List[Int]): List[Int] = cons(double(x), xs)
        def doubleall = reduce4(doubleandcons, Nil)
        println(doubleall(List(1, 2, 3)))
        // further generalize/modualize doubleandcons()
        def fandcons[T](f: (T => T)): ((T, List[T]) => List[T]) = {
            (x: T, xs: List[T]) => cons(f(x), xs)
        } 
        def doubleall2 = reduce4(fandcons(double), Nil)
        println(doubleall2(List(1, 2, 3)))
        // try to use syntax suger: compose or andThen

        // define the function using the more general map function
        def doubleall3 = map(double)
        println(doubleall3(List(1, 2, 3)))

        // use the Stream version (Lazy List) 
        def doubleall4 = map2(double)
        for (x <- doubleall4(1#::2#::3#::Stream.empty)) {
            println(x)
        }
        for (x <- doubleall4((1 to 3).toStream)) {
            println(x)
        }
    }
}
