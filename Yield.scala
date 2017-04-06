// Scala’s yield does something very different from Python's yield
// scala's yield:
// 1) for each iteration of for-loop, yield generates a value which will be remembered
//    i.e. for each iteration of for-loop, an item is added to an imaginary buffer
// 2) the for/yield construct creates a new collection 
//    i.e. it returns a collection of all yielded values
//
// example: for-comprehensions
// 1) for(x <- c1; y <- c2) {...use of x & y ...}
//    is translated to:
//    c1.foreach(x => c2.foreach(y => {...use of x & y...}))
//
// 2) for(x <- c1; y <- c2) yield {...expression of x & y...}
//    is translated to:
//    c1.flatMap(x => c2.map(y => {...expression of x & y...})))
//    (generators & stream/lazy list)

object Yield {
    def main(args: Array[String]) {
        // for-comprehensions:
        val c1 = Array("x1", "x2")
        val c2 = Array("y1", "y2")

        // 1) for(x <- c1; y <- c2) {...}
        // for-in loop:
        for (x <- c1; y <- c2) {
            println(x + y)
        }
        // is translated to (syntax suger of):
        c1.foreach(
            x => c2.foreach(
                y => { println(x + y) }
            )
        )

        // 2) for(x <- c1; y <- c2) yield {...}
        // for-in loop:
        val list1 = for(x <- c1; y <- c2) yield {
            "(" + x + ", " + y + ")"
        }
        // is translated to (syntax suger of):
        val list2 = c1.flatMap(
            x => c2.map(
                y => {"(" + x + ", " + y + ")"}
            )
        )
        for (i <- list1) println(i)
        for (i <- list2) println(i)
    }
}
