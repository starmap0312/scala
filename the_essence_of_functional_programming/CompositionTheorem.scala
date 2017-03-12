// Composition Theorem:
//   reduce(f, a) . map(g) = reduce( (x, y) => f(g(x), y), a )
//   1) special case:
//      map(f) . map(g) = map(f . g)
//   2) special case:
//      for any value x and y, and functions h, f, and g
//      if h(f(x, y)) = g(x, h(y)),
//      then h . reduce(f, a) = reduce(g, h(a))

object CompositionTheorem {

    def main(args: Array[String]) {
        val stream: Stream[Int] = 1#::7#::5#::8#::Stream.empty
        def square(x: Int): Int = { return x * x }
        def add(x: Int, y: Int) = x + y

        // example1: sumSquare1([ 1, 7, 5, 8 ]) = [ 1², 7², 5², 8² ]
        // 1.1) use map(f)      : returns a function that takes a Stream and produces a Stream
        def map[T](f: (T => T)): (Stream[T] => Stream[T]) = {
             case x#::xs       => f(x)#::(map(f))(xs)
             case _            => Stream.empty
        }
        val mapSquare1 = map(square)

        // 1.2) use reduce(f, a): returns a function that takes a Stream and produces a result of type S (ex. Stream[T] or T)
        def reduce[T, S](f: ((T, S) => S), a: S): (Stream[T] => S) = { // ex. T == Int, S == Int/Stream[Int]
             case x#::xs       => f(x, reduce(f, a)(xs))
             case _            => a
        }
        val mapSquare2 = reduce(((x: Int, xs: Stream[Int]) => Stream.cons(square(x), xs)), Stream.empty)

        println(mapSquare1(stream).toList)                   // List(1, 49, 25, 64)
        println(mapSquare2(stream).toList)                   // List(1, 49, 25, 64)

        // example2: sumSquare([ 1, 7, 5, 8 ]) = 1² + 7² + 5² + 8² = 139
        // 2.1) use map(square) andThen sum() composition:
        val sum = reduce(add, 0)
        val sumSquare1 = sum compose map(square) // i.e. sumSquare1 = sum . map(square)
        // alternatively,
        //   val sumSquare1 = map(square) andThen sum
        // the above is a shorthand of
        //   def sumSquare1(x: Stream[Int]) = sum(map(square)(x))
        // why is it bad?
        //   this produces an intermediate list: [ 1, 49, 25, 64 ]

        // 2.2) use Composition Theorem: reduce(f, a) . map(g) = reduce((x, y) => f(g(x), y), a)
        //      so we have:
        //        sumSquare1 = sum . map(square)
        //                   = reduce(add, 0) . map(square)
        //                   = reduce((x: Int, y: Int) => add(square(x), y), 0)
        def sumSquare2 = reduce((x: Int, y: Int) => add(square(x), y), 0) 
        // why is it good?
        //   this DOES NOT produce any intermediate list

        println(sum(stream))
        println(sumSquare1(stream))
        println(sumSquare2(stream))

        // example3: map(f) . map(g) = map(f . g)
        def f = (x: Int) => x + 1
        def g = (x: Int) => x + 2
        val map_f_compose_map_g = map(f) compose map(g) 
        val map_f_compose_g     = map(f compose g)
        println(map_f_compose_map_g(stream).toList)
        println(map_f_compose_g(stream).toList)
    }
}
