import scala.util.{Try, Success, Failure}

object TrySuccessFailure {

    def bad(num: Int): Try[Int] = {
        Try(                                 // return object will be wrapped in Success or Failure
            if (num == 3) {
                num                          // return Success[Int]
            } else {
                throw new Exception("Boom!") // return Failure[Exception]
            }
        )
    }

    def main(args: Array[String]) {
        // both Success[T] and Failure[T] extend Try[T]
        bad(3) match {
            case Success(num) => println(s"Success: $num")
            case Failure(ex)  => println(s"Failure: ${ex.getClass}")
        }
        bad(4) match {
            case Success(num) => println(s"Success: $num")
            case Failure(ex)  => println(s"Failure: ${ex.getClass}")
        }
    }
}
