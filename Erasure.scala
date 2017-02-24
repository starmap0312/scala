import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag

object Erasure {
    def filter1[T](list: List[Any]) = list.flatMap {
        case element: T => Some(element) // warning: abstract type pattern T is unchecked since it is eliminated by erasure
        // don’t expect compiler to know what T is when we get to pattern matching (since it will be erased by the JVM at runtime)
        case _ => None
    }

    def filter2[T](list: List[Any])(implicit tag: ClassTag[T]) = list.flatMap {
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
        // problem: specify T == String and we expect that the pattern matching of filter() is able to figure out the type
        //          however, in practice all generic types are erased after compilation (i.e. eliminated by erasure)
        val result1 = filter1[String](list)
        // T will be intepreted as its upperbound (in this case, Object)
        // so the filter() method will not filter out String type as expected 
        println(result1)                  // List(1, string1, List(), string2)
        // solution: when we require an implicit value that is of type ClassTag, compiler will create this value for us
        //           i.e. we need to ask compiler to provide us with an implicit instance of a needed ClassTag
        //                although we never needed to use the parameter itself
        val result2 = filter2[String](list)
        println(result2)                  // List(1, string1, List(), string2)

        // problem: Class tags cannot differentiate on a higher level
        //          i.e. it can differentiate between sets and lists, but it cannot tell apart one list from another (ex. List[Int] vs List[String])
        val list2: List[List[Any]] = List(List(1, 2), List("a", "b"))
        val result3 = filter2[List[Int]](list2)
        println(result3)                  // List(List(1, 2), List(a, b))

        // TypeTag provides runtime information about the type
        // ClassTag provides runtime information about the value
    }
}
