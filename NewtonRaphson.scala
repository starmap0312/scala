object NewtonRaphson {
    def next(n: Double, x:Double): Double = (x + n / x) / 2.0

    // an iterative approach based on while-loop
    def sqrt1(n: Double, epsilon: Double): Double = {
        def next(x:Double): Double = (x + n / x) / 2.0
        var x = n
        var nextX = next(x)
        while (math.abs(x - nextX) >= epsilon) {
            x = nextX
            nextX = next(x)
        }
        return nextX
    }

    // a recursive approach
    def sqrt2(n: Double, epsilon: Double): Double = {
        def next(x:Double): Double = (x + n / x) / 2.0
        def recursive(x: Double): Double = {
            val nextX = next(x)
            if (math.abs(x - nextX) >= epsilon) {
                return recursive(nextX)
            } else {
                return nextX
            }
        }
        return recursive(n)
    }

    // a tail recursion approach using infinite Streams
    def sqrt3(n: Double, epsilon: Double): Double = {
        require(n >= 0)
        def next(x: Double): Double = (x + n / x) / 2.0
        def within(epsilon: Double, s: Stream[Double]): Double = s match {
            case x0 #:: x1 #:: xs => if (math.abs(x0 - x1) < epsilon) x1 else within(epsilon, x1 #:: xs)
        }
        within(epsilon, Stream.iterate(n)(next))
    }

    def main(args: Array[String]) {
        val N = 2.0
        val EPS = 0.01
        println(sqrt1(N, EPS))
        println(sqrt2(N, EPS))
        println(sqrt3(N, EPS))
    }
}
