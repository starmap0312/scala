import scala.util.{Try, Success, Failure}

object TrySuccessFailure {

    def tryException(num: Int): Try[Int] = {
        // Try({expression}) will wrap the successfully evaluated value or any exception thrown
        //   a way to handle the error late and continue to pass it on to the pipeline (operation chain)
        Try(                                 // return object wrapped in Success or Failure
            if (num == 3) {
                num                          // Int value, wrapped by Success[Int]
            } else {
                throw new Exception("Boom!") // throw an Exception, wrapped by Failure[Exception]
            }
        )
    }

    def main(args: Array[String]) {
        // both Success[T] and Failure[T] extend Try[T]
        tryException(3) match {
            case Success(num) => println(s"Success: $num")           // Success: 3
            case Failure(ex)  => println(s"Failure: ${ex.getClass}")
        }
        tryException(4) match {
            case Success(num) => println(s"Success: $num")
            case Failure(ex)  => println(s"Failure: ${ex.getClass}") // Failure: class java.lang.Exception
        }
        val tryString: Try[String] = Try("one")
        //val tryString: Try[String] = Try(1)                        // compile-time error: type mismatch
        println(tryString)                                           // Success(string)

        // 1) try.get(), try.getOrElse([default value]):
        println(Try(1).get)                             // 1
        println(Try(throw new Exception).getOrElse(-1)) // -1
    }
}
