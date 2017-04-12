// use the new @tailrec annotation to mark a methods that you hope the compiler will optimize
// if the compiler fails to optimize it, it reports: error: could not optimize @tailrec annotated method
//   ex1: it contains a recursive call not in tail position
//   ex2. it is neither private nor final so can be overridden
// the compiler will automatically optimize any truly tail-recursive method,
//   so you use @tailrec annotation, to let the compiler warn you if the method is actually not tail-recursive
//   this both ensures that a method is currently optimizable and that it remains optimizable as it is modified
import scala.annotation.tailrec

object TailRec {
    def factorial(n: Int): Int = {
        @tailrec
        def factorialAcc(acc: Int, n: Int): Int = { // a recursive method
            if (n <= 1) acc                         // base case
            else factorialAcc(n * acc, n - 1)       // inductive step
        }
        factorialAcc(1, n)                          // evaluate the recursive method
    }

    def main(args: Array[String]) {
        println(factorial(3))                       // 6
    }
}
