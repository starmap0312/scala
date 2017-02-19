// syntax: for ([sequence of generators and filers]) yield [the result]
// 1) [element] <- [collection] : a generator
// 2) [generator] if [condition]: a filter
// 3) multiple generators is conceptually similar to a nested loop (first/outmost generator varies slower)
// 4) for (x <- e1; y <- e2 if f; z <- e3) yield
//    can be written as:
//    for {
//        x <- e1
//        y <- e2 if f
//        z <- e3
//    } yield
// 5) for (x <- e1) yield e2
//    is translated to:
//    e1.map(x => e2)
// 6) for (x <- e1 if f) yield e2
//    is translated to:
//    for (x <- e1.filter(x => f)) yield e2
// 7) for (x <- e1; y <- e2) yield e3
//    is translated to:
//    e1.flatMap(x => for (y <- e2) yield e3)
// 8) for-comprehension can be used for any container type, as long as its map(), flatMap() and filter() are defined

object ForComprehensions {
    def main(args: Array[String]) {
        // ex1.
        def e1 = for (x <- 1 to 2; y <- -2 to -1) yield (x, y)
        // is equivalent to
        def e2 = (1 to 2).flatMap(x => (-2 to -1).map(y => (x, y)))
        for (x <- e1) println(x)
        for (x <- e2) println(x)

        // ex2.
        def e3 = for {  
            i <- 1 until 3
            j <- -5 until i if (j % 2 == 0)
        } yield (i, j)  
        // is equivalent to
        def e4 = (1 until 3).flatMap(i => (-5 until i).filter(j => (j % 2 == 0)).map(j => (i, j)))
        for (x <- e3) println(x)
        for (x <- e4) println(x)
    }
}
