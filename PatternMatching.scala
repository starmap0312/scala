// Pattern Matching: match/case contruct
// Syntax
//   [value] match {
//       case [pattern] => [expression]
//       case [pattern] => [expression]
//   }
// they are used for conditional execution, destructuring, and casting into one construct
// ex.
//   (bad design)
//   animal match {
//       case dog: Dog => "dog (%s)".format(dog.breed) // type pattern
//       case _        => animal.species
//   }
//   (good design)
//   animal match {
//       case Dog(breed) => "dog (%s)".format(breed)   // constructor pattern
//       case other      => other.species
//   }

object PatternMatching {
    def main(args: Array[String]) {
        // example 1: match value & typed pattern
        // match value & typed pattern
        def matchNumber(x: Any): Any = x match {
            case 1         => "value pattern matched: " + 1
            case "two"     => "value pattern matched: " + "two"
            case x: Int    => "typed pattern matched: " + "x: Int"
            case x: Double => "typed pattern matched: " + "x: Double"
            //case _       => "nothing matched" (throw scala.MatchError at runtime)
        }
        println(matchNumber(1))
        println(matchNumber("two"))
        println(matchNumber(3))
        println(matchNumber(4.0))
        // println(matchNumber("N/A")) // throw scala.MatchError at runtime

        // example 2: match List object
        // match list
        def matchList[T](list: List[T]) = list match {
            case Nil        => ("value pattern matched: Nil")           // the empty list object
            case x::Nil     => ("value pattern matched: x::Nil")        // a single-element list 
            case List(x)    => ("constructor pattern matched: List(x)") // a single-element list (same as above)
            case 1::2::cs   => ("value pattern matched: 1::2::cs")      // a list object starting with 1 then 2 
            case x::xs      => ("constructor pattern matched: x::xs")   // at-least-one-element list
            case _          => ("all the other cases matched")          // atching with guards, for all the other cases
        }
        println(matchList(Nil))
        println(matchList(List(1)))
        println(matchList(List(2)))
        println(matchList(List(1, 2, 3)))
        println(matchList(List(1, 3, 4)))

        // example 3: pattern matching case object (singleton)
        trait Work
        case object DoWork extends Work                   // the constuctor accepts no argument
        case object NoWork extends Work                   // the constuctor accepts no argument
        // syntax suger for defining a matching function 
        val matchWork: (Work => Unit) = {
            case DoWork => println("value pattern matched: DoWork")
            case NoWork => println("value pattern matched: NoWork")
        }
        for (work <- List(DoWork, NoWork)) {
            matchWork(work)
        }
        // the above is a short hand of defining by creating an anonymous function
        val matchWork2: (Work => Unit) = (x => x match { 
            case DoWork => println("value pattern matched: DoWork")
            case NoWork => println("value pattern matched: NoWork")
        })
        for (work <- List(DoWork, NoWork)) {
            matchWork2(work)
        }
        // no function defined, simply using pattern matching expression
        for (work <- List(DoWork, NoWork)) {
            work match {
                case DoWork => println("value pattern matched: DoWork")
                case NoWork => println("value pattern matched: NoWork")
            }
        }

        // example 4: type erasure in pattern matching
        import scala.reflect.ClassTag
        val obj1: Any = "string value"        // a String instnace is declared but treated as type Any in the code 
        val obj2: Any = List(1, 2)            // a List instnace is declared but treated as type Any in the code 
        val obj3: Any = List("one", "tow")    // a List instnace is declared but treated as type Any in the code
        // getClass(): get only the class information of a value (not all type information)
        println(obj1.getClass)                // class java.lang.String
        println(obj2.getClass)                // class scala.collection.immutable.$colon$colon 
        println(obj3.getClass)                // class scala.collection.immutable.$colon$colon 
        def matchType[A: ClassTag, B](x: A) = x match {    // pattern matching is based on the instance's actual type
            case x: String       => println("String matched: " + x) // this gets printed out
            case x: List[String] => println("List matched: " + x)   // non-variable type argument String is eliminated
            case x: A            => println("A matched: " + x)      // abstract type pattern A is NOT eliminated
            case x: B            => println("B matched: " + x)      // abstract type pattern B is eliminated
            //case x: Any  => println("generic type is removed: " + x)     // the above is interpreted as this
        }
        // note:
        // a) the matching is based on the instance's actual type
        // b) generic type T is eliminated by erasure 
        matchType(obj1)                       // String matched: string value
        matchType(obj2)                       // List matched: List(1, 2, 3),      type parameter is eliminated
        matchType(obj3)                       // List matched: List(one, tow),     type parameter is eliminated
        matchType(3)                          // A matched: 3, thanks to ClassTag, type parameter is NOT eliminated
        // 1) WeakTypeTag
        //    type descriptor for abstract types
        // 2) ClassTag is limited in that it only covers the base class, but not its type parameters
        //    partial type descriptor of a Scala type
        //    it provides access only to the runtime class of a type
        //      ex. ClassTag[List[String]] contains only the erased class type information: scala.collection.immutable.List
        // 3) TypeTags: objects which carry all type information available at compile time, to runtime
        //    TypeTag[T] encapsulates the runtime type representation of some compile-time type T
        //    full type descriptor of a Scala type
        //      ex. a TypeTag[List[String]] contains all type information: scala.List[String]
    }
}
