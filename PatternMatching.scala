// Pattern Matching
// Syntax
//   case [pattern] => [expression]

object PatternMatching {
    def matchFunc(x: Int): String = x match {
        case 1 => "one"
        case 2 => "two"
        case _ => "other"
    }

   def matchFunc2(x: Any): Any = x match {
      case 1 => "one"
      case "two" => 2
      case y: Int => "scala.Int"
      case _ => "other"
   }

    def main(args: Array[String]) {
        // test matchFunc
        println(matchFunc(1))
        println(matchFunc(2))
        println(matchFunc(3))
        // test matchFunc2
        println(matchFunc2(1))
        println(matchFunc2("two"))
        println(matchFunc2(3))
        println(matchFunc2("four"))
    }
}
