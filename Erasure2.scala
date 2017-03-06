import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag
import scala.reflect.runtime.universe.typeOf

object Erasure2 {

    def main(args: Array[String]) {
        // 1) problem: matching function has list type unchecked, risk of getting exception at runtime
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
            // so you lose the ability to distinguish if the passed-in list is of type List[String]
            case _                           => println("No match")
        }
        handle(Failure("error"))
        handle(Success(List("a", "b", "cd")))
        //handle(Success(List("a", "b", 3))) // runtime exception: ClassCastException: Integer cannot be cast to String
        //handle(Success(List(1, 2, 3)))     // runtime exception: ClassCastException: Integer cannot be cast to String

        // 1.1) solution: type checked, type mismatch error received at compile time
        // (this assumes that we can control what type of value gets passed to function handle())
        trait Result2[T]
        case class Success2[T](list: List[T]) extends Result2[T]
        case class Failure2[T](msg: String)   extends Result2[T]
        def handle2(result: Result2[String]): Unit = result match { // add list type to the function parameter for compiler to check
            case Failure2(msg)               => println("Failure: " + msg)
            case Success2(list)              => println("Success: total length of strings in the list = " + list.map(_.size).sum)
            case _                           => println("No match")
        }
        handle2(Failure2("error"))
        handle2(Success2(List("a", "b", "cd")))
        //handle2(Success2(List("a", "b", 3))) // compile error: type mismatch, found: Int(3) but required: String
        //handle2(Success2(List(1, 2, 3)))     // compile error: type mismatch, found: Int(1), Int(2), Int(3) but required: String
        // why is it good?
        //   we enforce boundaries to the list type included in the Success2 type at compile time
        //   we make the type parameter covariant

        // 2) problem: what if we cannot control what type of value gets passed to function handle()
        // ex. Akka receive() function always accept messages of type Any
        // solution: do not use type parameter in the pattern matching function; instead, define explict types for compiler
        // (this assumes that we can control the body of the pattern matching function)
        def handle3(x: Any): Unit = x match {
            case list: List[String] => println("Any lists matched: total length of strings in the list = " + list.map(_.size).sum)
            case _                  => println("No match")
        }
        handle3(List("a", "b", "cd"))
        //handle3(List("a", "b", 3))           // runtime exception: ClassCastException: Integer cannot be cast to String
        //handle3(List(1, 2, 3))               // runtime exception: ClassCastException: Integer cannot be cast to String
        // 2.1) solution: do not use type parameter in the pattern matching function; instead, define explict types for compiler
        // (this assumes that we can control the body of the pattern matching function)
        case class Strings(values: List[String])
        def handle4(x: Any): Unit = x match {
            case Strings(list)      => println("String lists matched: total length of strings in the list = " + list.map(_.size).sum)
            case _                  => println("No match")
        }
        handle4(Strings(List("a", "b", "cd")))
        //handle4(Strings(List("a", "b", 3)))  // compile error: type mismatch, found: Int(3), required: String
        //handle4(Strings(List(1, 2, 3)))      // compile error: type mismatch, found: Int(1), Int(2), Int(3), required: String
        // why is it good?
        //   we define explict type Strings to manage the generic types for us
        //   this ensures that you cannot build lists of mixed types
        //   handle4() function can safely use lists without having to know the type parameters of lists at runtime

        // 2.2) solution: use TypeTag instance to get a full type description of a Scala type as a runtime value 
        //                it provides the ability to access the generic type parameter's type information during runtime
        // (this assumes that we can control what type of value gets passed to function handle() and also the body)
        def handle5[A: TypeTag](tag: A): Unit = typeOf[A] match {
            case t if t =:= typeOf[List[String]] => {
                println("String lists matched: total length of strings in the list = " + tag.asInstanceOf[List[String]].map(_.size).sum)
            }
            case _                               => println("No match")
        }
        handle5(List("a", "b", "cd"))
        //handle5(Strings(List("a", "b", 3)))  // compile error: type mismatch, found: Int(3), required: String
        handle5(List(1, 2, 3))                 // No match (no compile or runtime error)
    }
}
