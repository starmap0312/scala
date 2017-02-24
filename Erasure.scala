import scala.reflect.ClassTag

object Erasure {
    def filter1[T](list: List[Any]) = list.flatMap {
        case element: T => Some(element) // warning: abstract type pattern T is unchecked since it is eliminated by erasure
        case _ => None
    }

    def filter2[T](list: List[Any])(implicit tag: ClassTag[T]) = list.flatMap {
        case element: T => Some(element)
        case _ => None
    }

    def main(args: Array[String]) {
        val list = List(1, "string1", List(), "string2")
        // here, specify T == String and expect that the pattern matching of filter() is able to figure out the type
        val result1 = filter1[String](list)
        // however, in practice all generic types are erased after compilation (i.e. eliminated by erasure)
        // T will be intepreted as its upperbound (in this case, Object)
        // so the filter() method will not filter out String type as expected 
        println(result1)                  // List(1, string1, List(), string2)
        val result2 = filter2[String](list)
        println(result2)                  // List(1, string1, List(), string2)
    }
}
