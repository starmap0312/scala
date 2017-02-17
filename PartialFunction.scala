// Function is defined for every value of a given type
// PartialFunction is only defined for certain value of a given type
// 

object PartialFunction {
    def main(args: Array[String]) {
        // 1) Conventional function definition
        def func(x: Int): String = x match {
            case x if (x % 2 == 0) => "even"
        }
        println(func(4))
        // println(func(5)) // it will throw scala.MatchError if value not defined

        // 2) Partial functions: syntactical shorthand
        val partialFunc: PartialFunction[Int, String] = {
            case x if (x % 2 == 0) => "even"
        }
        // 2.1) isDefinedAt([value]): check if a value is defined
        if (partialFunc.isDefinedAt(4)) {
            println(partialFunc(4))
            // println(partialFunc(5)) // it will throw scala.MatchError if value not defined 
        }
        // 2.2) orElse                 // this is bad because of mutation?
        val totalFunc: (Int => String) = partialFunc orElse {
            case _ => "odd"
        }
        println(totalFunc(4))
        println(totalFunc(5))
    }
}
