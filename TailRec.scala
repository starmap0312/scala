// use the new @tailrec annotation to mark a methods that you hope the compiler will optimize
// if the compiler fails to optimize it, it reports:
// ex. error: could not optimize @tailrec annotated method: it contains a recursive call not in tail position
// ex. error: could not optimize @tailrec annotated method: it is neither private nor final so can be overridden

import scala.annotation.tailrec

object TailRec {
    class Factorial {
        def factorial(n: Int): Int = {
            @tailrec
            def factorialAcc(acc: Int, n: Int): Int = { // a recursive method
                if (n <= 1) acc                         // base case
                else factorialAcc(n * acc, n - 1)       // inductive step
            }
            factorialAcc(1, n)                          // evaluate the recursive method
        }
    }

    def main(args: Array[String]) {
        val f = new Factorial
        println(f.factorial(3)) // 6
    }
}
