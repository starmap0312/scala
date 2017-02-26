import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag

object Erasure {
    def filter1[T](list: List[Any]) = list.flatMap {
        case element: T => Some(element) // warning: abstract type pattern T is unchecked since it is eliminated by erasure
        // don’t expect compiler to know what T is when we get to pattern matching (since it will be erased by the JVM at runtime)
        case _ => None
    }

    def filter2[T](list: List[Any]) = list.flatMap {
        case element: Object => Some(element) // warning: abstract type pattern T is unchecked since it is eliminated by erasure
        // don’t expect compiler to know what T is when we get to pattern matching (since it will be erased by the JVM at runtime)
        case _ => None
    }

    def filter3[T](list: List[Any])(implicit tag: ClassTag[T]) = list.flatMap {
        // compiler turns unchecked type tests in pattern matches into checked ones by wrapping a (_: T) type pattern as tag(_: T),
        //   where tag is the ClassTag[T] instance
        case element: T => Some(element)
        // is translated by the compiler to:
        // case (element @ tag(_: T)) => Some(element)
        // note: @ is a way of giving a name to the class you’re matching
        //   ex. case Foo(p, q)     => // we can only reference parameters via p and q
        //       case f @ Foo(p, q) => // we can reference the whole object via f
        case _ => None
    }

    def main(args: Array[String]) {
        val list = List(1, "string1", List(), "string2")
        // problem: we expect the pattern matching of filter() can figure out the type T == String
        //          however, generic types will be erased after compilation (i.e. eliminated by erasure)
        val result1 = filter1[String](list)
        val result2 = filter2[String](list)
        // T will be intepreted as its upperbound, i.e. Object, so filter() will not filter out String type as expected 
        println(result1)                    // List(1, string1, List(), string2)
        println(result2)                    // List(1, string1, List(), string2)
        // solution: when we require an implicit value that is of type ClassTag, compiler will create this value for us
        //           i.e. we need to ask compiler to provide us with an implicit instance of a needed ClassTag
        //                although we never needed to use the parameter itself
        val result3 = filter3[String](list)
        println(result3)                    // List(1, string1, List(), string2)

        // problem: Class tags cannot differentiate on a higher level
        //          i.e. it can differentiate between sets and lists
        //               but it cannot tell one list from another (ex. List[Int] vs List[String])
        val list2: List[List[Any]] = List(List(1, 2), List("a", "b"))
        val result4 = filter3[List[Int]](list2)
        println(result4)                    // List(List(1, 2), List(a, b))

        // TypeTag provides runtime information about the type
        // ClassTag provides runtime information about the value
    }
}
