// Function is defined for every value of a given type
// PartialFunction is only defined for certain value of a given type
// 1) it is a unary function
// 2) its domain does not necessarily include all values of type A
// 3) the isDefinedAt() function is automaticall available to test if a value is in the domain
// syntax:
//   val functionName: PartialFunction[parameter type, return type] = {
//       case x if ([condition]) => [expr]
//       case x if ([condition]) => [expr]
//   }

object PartialFunction {
    def main(args: Array[String]) {
        // 1) Normal function definition
        def func(x: Int): String = x match {
            case x if (x % 2 == 0) => "even"
        }
        println(func(4))
        // println(func(5)) // it will throw scala.MatchError if value not defined

        // 2) Partial functions: provides a syntactical shorthand (isDefinedAt() is automatically defined)
        val partialFunc: PartialFunction[Int, String] = {
            case x if (x % 2 == 0) => "even"
        }
        // 2.1) isDefinedAt([value]): check if a value is defined
        if (partialFunc.isDefinedAt(4)) {
            println(partialFunc(4))
            // println(partialFunc(5)) // it will throw scala.MatchError if value not defined 
        }
        // 2.2) orElse                 // maybe a bad usage because of mutable function object? 
        val totalFunc: (Int => String) = partialFunc orElse {
            case _ => "odd"
        }
        println(totalFunc(4))
        println(totalFunc(5))
    }
}
