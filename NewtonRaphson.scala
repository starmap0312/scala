object NewtonRaphson {
    // an iterative approach based on while-loop
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

    // a recursive approach
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

    // a functional solution
    def sqrt3(n: Double, epsilon: Double): Double = {
        require(n >= 0.0)
        def next(x: Double): Double = (x + n / x) / 2.0
        def repeat[T](f: (T => T), a: T): Stream[T] = a#::repeat(f, f(a))
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
