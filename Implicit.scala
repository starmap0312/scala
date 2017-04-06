object Implicit {
    def main(args: Array[String]) {
        // 1) implicit parameters:
        //    if no value is supplied when called, compiler will look for an implicit value and pass it in for you 
        //    i.e. the values will be taken from the context in which they are called
        //         if there is no implicit value of the right type in scope, it will not compile
        implicit val myX = 2
        def multiply(x: Int)(implicit y: Int) = x * y
        println(multiply(3)(4))                       // a value for the implicit parameter is supplied, i.e. takes implicit myX from the current scope
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
        println
        // implicit classes have the following restrictions:
        // a) they must be defined inside of another trait/class/object
        // b) they may only take one non-implicit argument in their constructor
        //    ex. implicit class RichDate(date: java.util.Date)            // OK
        //        implicit class Indexer[T](collecton: Seq[T], index: Int) // NOT OK
        // c) there may not be any method, member or object in scope with the same name as the implicit class
        //    this means an implicit class cannot be a case class (because there will be a companion object created automatically if defined as a case class)
        //    ex. implicit case class Baz(x: Int)                          // NOT OK

        // 3) implicit scope of an argument's type
        //    if you have a method with an argument type A, then the implicit scope of type A will also be considered
        //      "implicit scope" means all these rules will be applied recursively
        //      ex. the companion object of A will be searched for implicits, as per the rule above
        class A(val n: Int) {
            def +(other: A) = new A(n + other.n)
        }
        object A {
            implicit def fromInt(n: Int) = new A(n)
        }
        // This becomes possible:
        1 + new A(1)
        // because it is converted into this:
        A.fromInt(1) + new A(1)
    }
}
