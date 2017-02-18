// Anonymous funciton
// Syntax
//   ([list of parameters]) => { function body }

object AnonymousFunciton {
    def main(args: Array[String]) {
        // ex1.
        val inc = (x: Int) => x + 1   // declare a anonymous function
        println(inc(7))               // call a anonoymous function
        // ex2.
        def mul = (x: Int, y: Int) => x * y
        println(mul(3, 4))
        // ex3. Pattern Matching in Anonymous Functions
        def toString = (x: Int) => x match {
            case 1 => "one"
            case 2 => "two"
        }
        println(toString(1))
        println(toString(2))
        // syntax suger: p => p match { case ... } can be replaced by { case ... }
        def toString2: (Int => String) = {
            case 1 => "one"
            case 2 => "two"
        }
        println(toString2(1))
        println(toString2(2))
    }
}
