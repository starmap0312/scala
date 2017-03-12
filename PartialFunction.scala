// Function is defined for every value of a given type
// PartialFunction is only defined for certain value of a given type
//   it may throw exception for some passed-in values or return an Option(None/Some) object
// f: X' -> Y, where X' < X
// 1) it is a unary function: i.e. f(x)
// 2) its domain does not necessarily include all values of type X
// 3) the isDefinedAt() function is automatically available to test if a value is in the domain
// syntax:
//   val functionName: PartialFunction[parameter type, return type] = {
//       case x if ([condition]) => [expr]
//       case x if ([condition]) => [expr]
//   }

object PartialFunction {
    def main(args: Array[String]) {
        println("example 1")
        // 1.1) normal function definition
        def func1(x: Int): String = x match {
            case x if (x % 2 == 0) => "even"
        }
        // 1.2) anonymous function definition
        def func2 = (x: Int) => x match {
            case x if (x % 2 == 0) => "even"
        }
        // 1.2) shorthand for pattern matching function 
        def func3: (Int => String) = {
            case x if (x % 2 == 0) => "even"
        }
        println(func1(4))
        println(func2(4))
        println(func3(4))
        // println(func(5)) // it will throw scala.MatchError if value not defined

        println("example 2")
        // 2) partial function definition (must use syntactical shorthand)
        val partialFunc: PartialFunction[Int, String] = {
            case x if (x % 2 == 0) => "even"
        }
        // 2.1) isDefinedAt([value]): check if a value is defined
        //                       isDefinedAt() is automatically defined
        if (partialFunc.isDefinedAt(4)) {
            println(partialFunc(4))
            // println(partialFunc(5)) // it will throw scala.MatchError if value not defined 
        }
        // 2.2) orElse:                 // maybe a bad usage because of mutable function object? 
        val totalFunc: (Int => String) = partialFunc orElse {
            case _ => "odd"
        }
        println(totalFunc(5))
    }
}
