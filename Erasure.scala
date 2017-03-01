// Scala erasure approach's drawback:
// 1) Scala is a strong type system
// 2) some type-related features are weakened by restrictions and limitations of its runtime environment
//    compiler will remove all generic type information after compilation 
// 3) at run time, only the class exists, not its type parameters
//    i.e. it doesn’t tell you at runtime what its actual type is
//         we cannot differitiate between List[Int] and List[String] at runtime
//    ex.  List<T> is just List<Object>
//         JVM knows it is handling a List, but not that this list is parameterized with T 
//         it replies on compiler to make sure client code does the right thing and to insert the correct casts for you
// C++ vs. Scala 
// 1) C++ templates compile completely different bytes for vector<string> to vector<int>
// 2) Scala uses exactly the same byte-code for all implementations 
// Solutions:
// 1) ClassTag: provides runtime information about the value
//              compiler provides us with an implicit instance of a needed ClassTag (it depends on scala-reflect)
// 2) TypeTag : provides runtime information about the type
//              TypeTag knows about higher type and has a much richer type information
import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag

object Erasure {
    def filter1[T](list: List[Any]) = list.flatMap {
        case element: T => Some(element) // warning: abstract type pattern T is unchecked since it is eliminated by erasure
        // don’t expect compiler to know what T is when we get to pattern matching (since it will be erased by the JVM at runtime)
        case _          => None
    }

    // the above is interpreted as (type parameter T is eliminated by erasure):
    def filter2(list: List[Any]) = list.flatMap {
        case element: Object => Some(element) // warning: abstract type pattern T is unchecked since it is eliminated by erasure
        // don’t expect compiler to know what T is when we get to pattern matching (since it will be erased by the JVM at runtime)
        case _               => None
    }

    // to differentiate a List[String] from a List[Integer], we can use ClassTag as below
    def filter3[T](list: List[Any])(implicit tag: ClassTag[T]) = list.flatMap {
        case element: T => Some(element)
        case _          => None
    }
    // compiler automatically provides us with the value for our implicit parameter tag
    // but we never needed to use the parameter tag
    // it allows our pattern matching to successfully match the String elements in list
    // i.e. compiler turns unchecked type tests in pattern matches into checked ones by wrapping a (_: T) type pattern as tag(_: T),
    //      where tag is the ClassTag[T] instance
    //      case element: T            => Some(element)
    //        (the above is written by compiler as)
    //      case (element @ tag(_: T)) => Some(element)
    //        (note: @ is a way of giving a name to the class you’re matching)
    //        ex. case Foo(p, q)     => // we can only reference parameters via p and q
    //            case f @ Foo(p, q) => // we can reference the whole object via f

    // or alternatively, we can write ClassTag as context bound syntax
    def filter4[T: ClassTag](list: List[Any]) = list.flatMap {
        case element: T => Some(element)
        case _          => None
    }

    def main(args: Array[String]) {
        val list = List(1, "string1", List(), "string2")
        // problem: we expect the pattern matching of filter() can figure out the type T == String
        //          however, generic types will be erased after compilation (i.e. eliminated by erasure)
        val result1 = filter1[String](list)
        val result2 = filter2(list)
        // T will be intepreted as its upperbound, i.e. Object, so filter() will not filter out String type as expected 
        println(result1)                    // List(1, string1, List(), string2), i.e. no type check
        println(result2)                    // List(1, string1, List(), string2), i.e. no type check
        // solution: when we require an implicit value that is of type ClassTag, compiler will create this value for us
        //           i.e. we need to ask compiler to provide us with an implicit instance of a needed ClassTag
        //                although we never needed to use the parameter itself
        val result3 = filter3[String](list)
        println(result3)                    // List(string1, string2), i.e. there is type check

        val result4 = filter4[String](list)
        println(result4)                    // List(string1, string2), i.e. there is type check

        // problem: Class tags cannot differentiate on a higher level
        //          i.e. it can differentiate between sets and lists
        //               but it cannot tell one list from another (ex. List[Int] vs List[String])
        val list2: List[List[Any]] = List(List(1, 2), List("a", "b"))
        val result5 = filter4[List[Int]](list2)
        println(result5)                    // List(List(1, 2), List(a, b)), i.e. both are printed out, no type check
    }
}
