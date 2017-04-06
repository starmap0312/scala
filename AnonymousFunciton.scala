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

        // ex3.
        // 1) method definition
        def toString1(x: Int): String = x match {
            case 1 => "one"
            case 2 => "two"
        }
        // 2) Function1 definition
        def toString2 = (x: Int) => x match {
            case 1 => "one"
            case 2 => "two"
        }
        // 3) syntax suger: p => p match { case ... } can be replaced by { case ... }
        def toString3: (Int => String) = { // type (Int => String) cannot be omitted
            case 1 => "one"
            case 2 => "two"
        }
        println(toString1(2))
        println(toString2(2))
        println(toString3(2))
    }
}
