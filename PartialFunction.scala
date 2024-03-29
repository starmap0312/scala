// Function vs. Method
// 1) Function: a type (trait) of the form (T1, ..., Tn) => U
//              a shorthand for trait FunctionN in the standard library (Function0, Function1, Function2, etc.)
//              it defines the apply() syntactic sugar: func.apply(x) -> func(x)
//              i.e. allowing us to call it directly
//              anonymous function: an instance of trait FunctionN
//              it is contra-variant on the parameters it receives, and co-variant on the return result
// 2) Method: a non-value type (i.e. there is no value, no object, no instance, it's just a declaration)
//            a method type is a def declaration (everything about a def except its body)
// ETA expansion
//   a mechanism for transforming methods into functions
// Class Method:
//   class methods are just methods that can access the state of a class
// Total Function vs. PartialFunction
// 1) Total Function:
//    function that is defined for every value of a given type
// 2) PartialFunction is only defined for certain value of a given type
//    function that is NOT defined for every value of a given type
//    it throws exception for some values or return an Option(None/Some) object
//    it extends Function1 (i.e. a unary function instance)
//    PartialFunction f: X' -> Y, where X' < X
//    a) it is a unary function: i.e. f(x)
//    b) its domain does not necessarily include all values of type X
//    c) the isDefinedAt() function is automatically available to test if a value is in the domain
// syntax sugar:
//   val functionName: PartialFunction[parameter type, return type] = {
//       case x if ([condition]) => [expr]
//       case x if ([condition]) => [expr]
//   }

object PartialFunction {
    def main(args: Array[String]) {
        println("example 1")
        // 1.1) method
        def func1(x: Int): String = x match {
            case x if (x % 2 == 0) => "even"
        }
        // 1.2) function 
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
        // println(func(5))       // throw scala.MatchError if value not defined

        println("example 2")
        // 2) partial function definition (must use syntactical shorthand)
        val partialFunc: PartialFunction[Int, String] = {
            case x if (x % 2 == 0) => "even"
        }
        //println(partialFunc(3)) // throw scala.MatchError
        //println(totalFunc2(3))  // throw scala.MatchError

        // 2.1) isDefinedAt([value]): automatically defined, check if a value is defined
        //      a PartialFunction must provides method isDefinedAt
        //      this allows the caller to know whether the function can return a result for a given input value
        if (partialFunc.isDefinedAt(4)) {
            println(partialFunc(4))
        }
        // 2.2) orElse:                 // maybe a bad usage because of mutable function object? 
        val totalFunc: (Int => String) = partialFunc orElse {
            case _ => "odd"
        }
        println(totalFunc(5))
    }
}
