import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag

object Erasure2 {

    def main(args: Array[String]) {
        trait Result
        case class Success[T](list: List[T]) extends Result
        case class Failure(msg: String) extends Result

        def handle(result: Result): Unit = result match {
            case Failure(msg)                => println("Failure: " + msg)
            case Success(list: List[String]) => println("Success: total length of strings in the list = " + list.map(_.size).sum)
            // warning: type pattern List[String] is unchecked since it is eliminated by erasure
            case _                           => println("No match")
        }
        handle(Failure("error"))
        handle(Success(List("a", "b", "cd")))
        //handle(Success(List("a", "b", 3)))
    }
}
