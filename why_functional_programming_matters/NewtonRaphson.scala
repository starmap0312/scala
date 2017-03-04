// example: sqaure root computation using the Newton-Raphson method
//          n, next(n), next(next(n)), ... continue until there are two consecusive values within the given epsilon value
object NewtonRaphson {
    // an iterative approach
    def sqrt1(n: Double, epsilon: Double): Double = {
        require(n >= 0.0)
        def next(x:Double): Double = (x + n / x) / 2.0
        var x = n
        var nextX = next(x)
        while (math.abs(x - nextX) >= epsilon) {
            x = nextX
            nextX = next(x)
        }
        return nextX
    }

    // a recursive approach: there may be stack overflow for deep recursions
    def sqrt2(n: Double, epsilon: Double): Double = {
        require(n >= 0.0)
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

    // a functional approach: use Stream (Lazy List), evaluated when needed, no extra storage allocated
    def sqrt3(n: Double, epsilon: Double): Double = {
        require(n >= 0.0)
        def next(x: Double): Double = (x + n / x) / 2.0
        def repeat[T](f: (T => T), x: T): Stream[T] = x#::repeat(f, f(x))
        def within(epsilon: Double, stream: Stream[Double]): Double = stream match {
            case a#::b#::rest => if (math.abs(a - b) < epsilon) b else within(epsilon, b#::rest)
        }
        within(epsilon, repeat(next, n))
    }

    def main(args: Array[String]) {
        val N = 2.0
        val EPS = 0.01
        println(sqrt1(N, EPS))
        println(sqrt2(N, EPS))
        println(sqrt3(N, EPS))
    }
}
