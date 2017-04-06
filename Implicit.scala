object Implicit {
    def main(args: Array[String]) {
        // 1) implicit parameters:
        //    if no value is supplied when called, compiler will look for an implicit value and pass it in for you 
        //    i.e. the values will be taken from the context in which they are called
        //         if there is no implicit value of the right type in scope, it will not compile
        implicit val myX = 2
        def multiply(x: Int)(implicit y: Int) = x * y
        println(multiply(3)(4))                       // a value for the implicit parameter is supplied 
        println(multiply(3))                          // no value is supplied, so an implicit variable of the right type must be found in the scope

        // 2) implicit classes:
        //    the implicit keyword makes the class' primary constructor available for implicit conversions when the class is in scope
        implicit class IntWithTimes(x: Int) {
            def times[A](f: => A): Unit = {
                def loop(current: Int): Unit =
                    if(current > 0) {
                        f
                        loop(current - 1)
                    }
                loop(x)
            }
        }
        // this class wraps an Int value and provides a new method: times()
        // to use this class, just import it into scope and call the times method
        // ex.
        3 times print("Hi ")                                               // Hi Hi Hi 
        // implicit classes have the following restrictions:
        // 1) they must be defined inside of another trait/class/object
        // 2) they may only take one non-implicit argument in their constructor
        //    ex. implicit class RichDate(date: java.util.Date)            // OK
        //        implicit class Indexer[T](collecton: Seq[T], index: Int) // NOT OK
        // 3) there may not be any method, member or object in scope with the same name as the implicit class
        //    this means an implicit class cannot be a case class (because there will be a companion object created automatically if defined as a case class)
        //    ex. implicit case class Baz(x: Int)                          // NOT OK
    }
}
