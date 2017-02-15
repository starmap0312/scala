import scala.util.{Try, Success, Failure}
//import java.io.Exception

object TrySuccessFailure {

    def bad(num: Int): Try[Int] = {
        Try(
            if (num == 3) {
                num                          // return Success[Int]
            } else {
                throw new Exception("Boom!") // return Failure[Exception]
            }
        )
    }

    def main(args: Array[String]) {
        bad(3) match {
            case Success(num) => println(s"Success: $num")
            case Failure(e)   => println(s"Failure: ${e.getClass}")
        }
        bad(4) match {
            case Success(num) => println(s"Success: $num")
            case Failure(e)   => println(s"Failure: ${e.getClass}")
        }
    }
}
