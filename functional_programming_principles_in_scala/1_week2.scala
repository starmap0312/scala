// Higher-Order Functions
anonymous functions
ex.
def str = "abc"; println(str)
println("abc")

ex.
def f[T1, T2](x1: T1, x2: T2) = E; f
(x1: T1, x2: T2) => E                // anonymous function, or function literals

// Currying
def sum(f: Int => Int): (Int, Int) => Int = { // a method that takes a Function1 and returns a Function2
  def SumF(a, Int, b: Int): Int =
    if (a > b) 0
    else f(a) + sumF(a + 1, b)
  sumF
}
// a shorthand for the above
def sum(f: Int => Int)(a: Int, b: Int): Int = // a method with currying parameters so that if given a Function1 it returns a Function2
  if (a > b) 0 else f(a) + sum(f)(a + 1, b)

// More Fun with Rationals
secondary constructor
ex.
  class Rational(x: Int, y: Int) {
    require(y != 0, "denominator must be nonzero") // throws IllegalArgument exception with the given message if precondition does not hold
    def this(x: Int) = this(x, 1)                  // secondary constructor which calls primary constructor (i.e. class parameters and body)
    private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b) // private to the client
    private val g = gcd(x, y)
    def numerator   = x / g
    def denominator = y / g
  }
