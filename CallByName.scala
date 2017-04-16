// 1) call-by-value:
//    compute the passed-in expression's value before calling function
//    so the same computed value is accessed every time inside the function, and the side effect happens only once
// 2) call-by-name:
//    recompute the passed-in expression's value every time it is accessed (lazy evaluation?)
object CallByName {
    def exprOne = {                                // expression: Int
        println("side effect of calling exprOne")
        1                                             // return value 1
    }

    def methodOne() = {                                    // method: ()Int
        println("side effect of calling methodOne()")
        1                                             // return value 1
    }

    def callByValue(x: Int) = {                       // method: (x: Int)Unit, side effect happens here and only once
        println(x.getClass)                           // no side effect here
        println("callByValue: x =" + x)               // no side effect here
        println("callByValue: x =" + x)               // no side effect here
    }

    def callByName(x: => Int) = {                     // method: (x: => Int)Unit
        println(x.getClass)                           // side effect happens here when first accessed
        println("callByName: x =" + x)                // side effect happens here when second accessed
        println("callByName: x =" + x)                // side effect happens again when third accessed
    }
                                                      // function can also accept call-by-name parameter via ETA expansion
    def function1 = (x => callByName(x))              // function1: (=> Int) => Int
                                                      // convert method with call-by-name parameter to function1
    def main(args: Array[String]) {
        println("1.1) callByValue(exprOne)")
        callByValue(exprOne)
        println("1.2) callByValue(methodOne())")
        callByValue(methodOne())
        println("2.1) callByName(exprOne)")
        callByName(exprOne)
        println("2.2) callByName(methodOne())")
        callByName(methodOne())
        println("3.1) function1(exprOne)")
        function1(exprOne)
        println("3.2) function1(methodOne())")
        function1(methodOne())
    }
}
