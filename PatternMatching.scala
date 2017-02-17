// Pattern Matching: match/case contruct
// Syntax
//   [value/typed pattern] match {
//       case [pattern] => [expression]
//       case [pattern] => [expression]
//   }

object PatternMatching {
    // value pattern
    def matchFunc(x: Int): String = x match {
        case 1 => "one"
        case 2 => "two"
        case _ => "other"
    }

    def matchFunc2(x: Any): Any = x match {
        case 1      => "one"
        case "two"  => 2
        case y: Int => "scala.Int"
        case _      => "other"
    }

    def matchFunc3(x: Any): String = x match {
        case num: Int    => "integer"
        case str: String => "string"
        case dbl: Double => "double"
    }

    def main(args: Array[String]) {
        // 1) value pattern
        // example: matchFunc
        println(matchFunc(1))
        println(matchFunc(2))
        println(matchFunc(3))
        // example: matchFunc2
        println(matchFunc2(1))
        println(matchFunc2("two"))
        println(matchFunc2(3))
        println(matchFunc2("four"))
        // 2) typed pattern
        // example: matchFunc3
        println(matchFunc3(1))
        println(matchFunc3("two"))
        println(matchFunc3(3.0))
    }
}
