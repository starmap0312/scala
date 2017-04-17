// Curring
// 1) partially applied
//    when a method is called with a fewer number of parameter lists, then
//    it will yield a FunctionN taking the missing parameter lists as its arguments
// 2) FunctionN's curried() method: converting FuntionN into curried Function1's
//    trait Function2[-T1, -T2, +R] extends AnyRef { self =>
//        def apply(v1: T1, v2: T2): R
//        def curried: T1 => T2 => R = {            // returns a curried FunctionN: T1 => (T2 => R)
//            (x1: T1) => (x2: T2) => apply(x1, x2) // which takes one parameter at a time
//        }
//    }
object Currying {
    def main(args: Array[String]) {
        // 1) two-parameters method:
        def methodSum(a: Int, b: Int): Int = a + b        // (Int, Int)Int

        // 2) Function2:
        def Func2Sum = (a: Int, b: Int) => a + b          // (Int, Int) => Int

        // 3) method that returns a Function1: 
        def methodFuncSum(a: Int): (Int => Int) = {       // (Int)Int => Int
            def inner(b: Int): Int = a + b                // (Int)Int, method
            inner                                         // converted into a Function1 by compiler
        }

        // the following is a shorthand of the above
        // 4) curried method: (a: Int)(b: Int)Int
        def curriedMethodSum(a: Int)(b: Int): Int = a + b // (Int)(Int)Int

        // 5) curried Function1's:
        def curriedFunc1 = (a: Int) => (b: Int) => a + b  // Int => Int => Int
        def curriedFunc2 = methodFuncSum _                // Int => Int => Int 
        def curriedFunc3 = curriedMethodSum _             // Int => Int => Int 
        def curriedFunc4 = Func2Sum.curried               // Int => Int => Int

        println(curriedFunc1(1)(2))                       // 3
        println(curriedFunc2(1)(2))                       // 3
        println(curriedFunc3(1)(2))                       // 3
        println(curriedFunc4(1)(2))                       // 3
    }
}
