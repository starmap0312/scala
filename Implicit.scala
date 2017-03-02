object Implicit {
    def main(args: Array[String]) {
        // 1) Implicit Parameters:
        //    if no value is supplied when called, compiler will look for an implicit value and pass it in for you 
        //    i.e. the values will be taken from the context in which they are called
        //         if there is no implicit value of the right type in scope, it will not compile
        implicit val myX = 2
        def multiply(x: Int)(implicit y: Int) = x * y
        println(multiply(3)(4))                       // a value for the implicit parameter is supplied 
        println(multiply(3))                          // no value is supplied, so an implicit variable of the right type must be found in the scope
    }
}
