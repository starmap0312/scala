import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag
import scala.reflect.runtime.universe.typeOf

object Erasure2 {

    def main(args: Array[String]) {
        // 1) problem: matching function has list type unchecked, risk of getting exception at runtime
        println("1) problem")
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
        //handle(Success(List("a", "b", 3)))   // runtime exception: ClassCastException: Integer cannot be cast to String
        //handle(Success(List(1, 2, 3)))       // runtime exception: ClassCastException: Integer cannot be cast to String

        // 1.1) solution: explicitly define the type of passed-in parameter for compiler to type check 
        // (this assumes that we have control over what can be passed to function handle())
        println("1.1) solution")
        case class Success1(list: List[String]) extends Result
        def handle1(result: Result): Unit = result match {
            case Failure(msg)                => println("Failure: " + msg)
            case Success1(list: List[String]) => println("Success: total length of strings in the list = " + list.map(_.size).sum)
            case _                           => println("No match")
        }
        handle1(Failure("error"))
        handle1(Success1(List("a", "b", "cd")))
        //handle1(Success1(List("a", "b", 3))) // compile error: type mismatch, found: Int(3) but required: String
        //handle1(Success1(List(1, 2, 3)))     // compile error: type mismatch, found: Int(1), Int(2), Int(3) but required: String

        // 1.2) solution: define the generic at the base class for compiler to check type
        // (this assumes that we can control what type of value gets passed to function handle())
        // (no need to change client's passing parameter to function handle())
        println("1.2) solution")
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

        // 2) problem: if we cannot control what type of value gets passed to function handle(Result)
        //             but we can control the pattern matching function
        //             ex. in Akka, the functions always accept messages of type Any
        println("2) problem")
        def handle3(x: Any): Unit = x match {
            case list: List[String] => println("Any lists matched: total length of strings in the list = " + list.map(_.size).sum)
            case _                  => println("No match")
        }
        handle3(List("a", "b", "cd"))
        //handle3(List("a", "b", 3))           // runtime exception: ClassCastException: Integer cannot be cast to String
        //handle3(List(1, 2, 3))               // runtime exception: ClassCastException: Integer cannot be cast to String
        // 2.1) solution: what can be done if we could control what's passed in function handle() and also pattern matching body
        println("2.1) solution")
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

        // 2.2) solution: if we can function handle's passed-in type and its body but not what's passed-in function handle()
        //                use TypeTag instance to get a full type description of a Scala type as a runtime value 
        //                it provides the ability to access the generic type parameter's type information during runtime
        println("2.2) solution")
        def handle5[A: TypeTag](tag: A): Unit = typeOf[A] match {
            case t if t =:= typeOf[List[String]] => {
                println("String lists matched: total length of strings in the list = " + tag.asInstanceOf[List[String]].map(_.size).sum)
            }
            case _                               => println("No match")
        }
        handle5(List("a", "b", "cd"))
        handle5(List(1, 2, 3))                 // No match (no compile or runtime error)
    }
}
