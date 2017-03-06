import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag

object Erasure2 {

    def main(args: Array[String]) {
        // (bad design: type unchecked, risk of getting exception at runtime)
        trait Result
        case class Success[T](list: List[T]) extends Result  // type parameter T is erased when compiled
        // (the above can be viewed as the following)
        //case class Success(list: List) extends Result
        case class Failure(msg: String) extends Result
        def handle(result: Result): Unit = result match {
            case Failure(msg)                => println("Failure: " + msg)
            case Success(list: List[String]) => println("Success: total length of strings in the list = " + list.map(_.size).sum)
            // warning: type pattern List[String] is unchecked since it is eliminated by erasure
            // (the above can be viewed as the following )
            // case Success(list: List)      => println("Success: total length of strings in the list = " + list.map(_.size).sum)
            case _                           => println("No match")
        }
        handle(Failure("error"))
        handle(Success(List("a", "b", "cd")))
        // handle(Success(List("a", "b", 3))) // runtime exception: ClassCastException: Integer cannot be cast to String

        // (good design: type checked, type mismatch error received at compile time)
        trait Result2[T]
        case class Success2[T](list: List[T]) extends Result2[T]
        case class Failure2[T](msg: String)   extends Result2[T]
        def handle2(result: Result2[String]): Unit = result match {
            case Failure2(msg)               => println("Failure: " + msg)
            case Success2(list)              => println("Success: total length of strings in the list = " + list.map(_.size).sum)
            case _                           => println("No match")
        }
        handle2(Failure2("error"))
        handle2(Success2(List("a", "b", "cd")))
        //handle2(Success2(List("a", "b", 3))) // compile error: type mismatch, found: Int(3) but required: String
    }
}
