// Function Type vs. Method Type
// 1) Function Type: a type (trait) of the form (T1, ..., Tn) => U
//                   a shorthand for trait FunctionN in the standard library
//      function declaration:
//        it includes a type (Method Type) and a body (expression or block)
//      note:
//        Anonymous Function is an instance of a Function Type (i.e. an instance of trait FunctionN)
// 2) Method Type  : a non-value type (i.e. there is no value: no object, no instance)
//                   a Method Value actually has a Function Type
//                   a method type is a def declaration (everything about a def except its body)
// Function:
//   function declaration
//     it includes a type (Method Type) and a body (expression or block)
//   a function is an object which includes one of FunctionX traits, ex. Function0, Function1, Function2, etc.
//     it might include PartialFunction as well, which extends Function1
//   it has: def apply(v1: T1): R
//     it applies the body of this function to the arguments
//     it returns something of type R
//     it is contra-variant on the parameters it receives, and co-variant on the result
// Class Method:
//   methods are just functions that can access the state of a class
// Total Function:
//   defined for every value of a given type
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
