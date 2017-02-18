// Pattern Matching: match/case contruct
// Syntax
//   [value/typed pattern] match {
//       case [pattern] => [expression]
//       case [pattern] => [expression]
//   }

object PatternMatching {
    // match value pattern
    def matchFunc(x: Int): String = x match {
        case 1 => "one"
        case 2 => "two"
        case _ => "other"
    }

    // match value & typed pattern
    def matchFunc2(x: Any): Any = x match {
        case 1      => "one"
        case "two"  => 2
        case y: Int => "scala.Int"
        case _      => "other"
    }

    // match typed pattern
    def matchFunc3(x: Any): String = x match {
        case num: Int    => "integer"
        case str: String => "string"
        case dbl: Double => "double"
    }

    def main(args: Array[String]) {
        // example 1: match value pattern
        println(matchFunc(1))
        println(matchFunc(2))
        println(matchFunc(3))
        // example 2: match value & typed pattern
        println(matchFunc2(1))
        println(matchFunc2("two"))
        println(matchFunc2(3))
        println(matchFunc2("four"))
        // example 3: match typed pattern
        println(matchFunc3(1))
        println(matchFunc3("two"))
        println(matchFunc3(3.0))
    }
}
