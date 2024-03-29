// Scala erasure approach's drawback:
// 1) Scala is a strong type system
// 2) erasure:
//      compiler will remove all generic type information after compilation 
//      some type-related features are weakened by restrictions and limitations of its runtime environment
// 3) at run time, only class exists, not its type parameters
//    i.e. it doesn’t tell you at runtime what its actual type is
//         we cannot differitiate between List[Int] and List[String] at runtime
//    ex.  List<T> is just List<Object>
//         JVM only knows it is handling a List, but not that this list is parameterized with T 
//         it replies on compiler to make sure client code does the right thing and to insert the correct casts for you
// C++ vs. Scala 
// 1) C++ templates compile completely different bytes for vector<string> to vector<int>
// 2) Scala uses exactly the same byte-code for all implementations 
// TypeTag: let client code uses runtime type information
//   TypeTag provides runtime information about the value
//   it can be thought of as objects which carry along all type information available at compile time, to runtime
//   ex. TypeTag[T] encapsulates the runtime type representation of some compile-time type T
// three different types of TypeTags:
// 1) TypeTag : a full type descriptor of a Scala type
//    ex. TypeTag[List[String]] contains all type information of type scala.List[String]
//    i.e. TypeTag knows about higher type and has a much richer type information
// 2) ClassTag: a partial type descriptor of a Scala type
//    ex. ClassTag[List[String]] contains only the erased class type information, i.e. type scala.collection.immutable.List
// 3) WeakTypeTag: a type descriptor for abstract types
// TypeTags are always generated by the compiler and can be obtained via methods:
//   typeTag()
//   classTag()
//   weakTypeTag()
// Context Bound
// 1) context bounds are implemented with implicit parameters
//    def g[T: B](x: T) = f(x)
//    (the above can be desugared as the following)
//    def g[T](x: T)(implicit ev: B[T]) = f(x)
// ex.
//    def g[T: ClassTag](x: T) = f(x)
//    (the above can be desugared as the following)
//    def g[T](x: T)(implicit ctag: ClassTag[T]) = f(x)
import scala.reflect.ClassTag
import scala.reflect.classTag
import scala.reflect.runtime.universe.TypeTag
import scala.reflect.runtime.universe.typeTag
import scala.reflect.runtime.universe.TypeRef
import scala.reflect.runtime.universe.typeOf

object Erasure1 {
    def filter1[T](list: List[Any]) = list.flatMap({
        case element: T => Some(element) // warning: abstract type pattern T is unchecked since it is eliminated by erasure
        case _          => None          // don’t expect compiler to know what T is when pattern matching
                                         // since T will be erased by the JVM at runtime
    })

    // the above is interpreted by compiler as:
    def filter2(list: List[Any]) = list.flatMap({
        case element: Object => Some(element)
        case _               => None
    })

    // Method 1) use implicit parameter in function definition 
    def filter3[T](list: List[Any])(implicit tag: ClassTag[T]) = list.flatMap({ // implicit parameters: compiler automatically finds and passes the value
        case element: T => Some(element)
        case _          => None
    })
    // we never needed to use the parameter tag
    // it allows our pattern matching to successfully match the String elements in list
    // i.e. this turns unchecked type tests into checked ones by wrapping a (_: T) type pattern as tag(_: T),
    //      where tag is the ClassTag[T] instance
    //   case element: T            => Some(element)
    //   (the above is interpreted by compiler as)
    //   case (element @ tag(_: T)) => Some(element)
    // note: @ is a way of giving a name to the class you’re matching
    // ex.
    //   case Foo(p, q)             => // we can only reference parameters via p and q
    //   case foo @ Foo(p, q)         => // we can reference not only parameters p and q but also the whole object via foo

    // Method 3) use context bound in function definition
    //           given context bound [T: TypeTag], compiler will generate an implicit parameter of type ClassTag[T]
    def filter4[T: ClassTag](list: List[Any]) = list.flatMap({
        case element: T => Some(element)
        case _          => None
    })

    def main(args: Array[String]) {
        def func(x: List[String]) = {}
        func(List("a", "b"))
        //func(List(1, 2)) // type mismatch

        val list = List(1, "string1", List(), "string2")
        // 1) problem: we expect the pattern matching of filter() can figure out the type T == String
        //             however, generic types will be erased after compilation (i.e. eliminated by erasure)
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

        // 2) problem: Class tags cannot differentiate on a higher level
        //             i.e. it can differentiate between sets and lists
        //             but it cannot tell one list from another (ex. List[Int] vs List[String])
        val list2: List[List[Any]] = List(List(1, 2), List("a", "b"))
        val result5 = filter4[List[Int]](list2)
        println(result5)                    // List(List(1, 2), List(a, b)), i.e. both are printed out, no type check

        // How to obtain TypeTag instances for the definition of generic type functions?
        // method 1) use typeTag() method to obtain a TypeTag instance:
        val tag1 = typeTag[Int]             // construct a TypeTag[Int] instance
        val tag2 = classTag[String]         // construct a ClassTag[String] instance
        println(tag1)                       // TypeTag[Int]
        println(tag2)                       // java.lang.String 
        println(tag1.tpe)                   // Int: we can then directly access type that tag represents using method tpe of TypeTag 

        // method 2) use implicit parameter in function definition
        //           ask compiler finds or generates for us
        def func1[T]()(implicit tag: TypeTag[T]): Unit = { // we supply an implicit parameter (implicit tag: TypeTag[T])
            println(tag.tpe)                               // we can then directly access type that tag represents using method tpe of TypeTag
        }

        // method 3) use context bound of a type parameter in function definition
        def func2[T: TypeTag](): Unit = {
            println(typeOf[T])                             // we can then directly access type that tag represents using method typeOf[T]
        }
        func1[Int]()                                       // Int 
        func2[String]()                                    // String

        // 3) problem: type erased by Erasure in pattern matching
        val list3 = List[String]("one", "two", "three")
        val list4 = List[Int](1, 2, 3)
        def printList1(x: Any): Unit = x match {
            case x: List[Int]  => x.foreach(println)        // warning: type parameter Int is eliminated by erasure
            //case x: List[_]  => x.foreach(println)        //          i.e. it is interpreted by compiler as this
            case _             => println("No match")
        }
        printList1(list3)                                   // the list will be printed out, which is not what we expect
        // false solution: use an instance of ClassTag does not work
        val IntList1 = classTag[List[Int]]
        def printList2(x: Any): Unit = x match {
            case IntList1(x)   => x.foreach(println)        // no warning as no type parameter is erased now 
            case _             => println("No match")
        }
        printList2(list3)                                   // the list will be printed out, which is not what we expect
        // solution1: define a new case class for pattern matching
        //            this requires that you have control of the type of values passed into the function
        case class IntList(list: List[Int])
        def printList3(x: Any): Unit = x match {
            case IntList(x) => x.foreach(println) 
            case _             => println("No match")
        }
        //printList3(IntList(list3))                        // this does not compile as IntList accepts only List[Int] 
        printList3(IntList(list4))                          // the list will be printed out, which is what we expect
        // solution2: use a TypeTag instance to recover the type information of generic type

        // 4) context bound
        def mkArray1[T: ClassTag](elems: T*) = Array[T](elems: _*)                   // [T](elems: T*)(implicit evidence$1: ClassTag[T])Array[T] 
        def mkArray2[T](elems: T*)(implicit ctag: ClassTag[T]) = Array[T](elems: _*) // [T](elems: T*)(implicit ctag: ClassTag[T])Array[T]
        //val arr = mkArray1[String](1, 2, 3)                                        // type mismatch 
        val arr1 = mkArray1[String]("a", "b")                                        // Array[String] = Array(a, b)
        val arr2 = mkArray2[String]("a", "b")                                        // Array[String] = Array(a, b)
    }
}
