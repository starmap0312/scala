// Pattern Matching: match/case contruct
// Syntax
//   [value/typed pattern] match {
//       case [pattern] => [expression]
//       case [pattern] => [expression]
//   }

object PatternMatching {
    // match value & typed pattern
    def matchFunc1(x: Any): Any = x match {
        case 1         => "value pattern matched: " + 1 
        case "two"     => "value pattern matched: " + "two"
        case x: Int    => "typed pattern matched: " + "x: Int" 
        case x: Double => "typed pattern matched: " + "x: Double"
        case _         => "nothing matched      : " + "_"
    }

    // match list
    def matchFunc2(x: Any): Any = x match {
        case 1         => "value pattern matched: " + 1
        case "two"     => "value pattern matched: " + "two"
        case x: Int    => "typed pattern matched: " + "x: Int"
        case x: Double => "typed pattern matched: " + "x: Double"
        case _         => "nothing matched      : " + "_"
    }

    def main(args: Array[String]) {
        // example 2: match value & typed pattern
        println(matchFunc1(1))
        println(matchFunc1("two"))
        println(matchFunc1(3))
        println(matchFunc1(4.0))
        println(matchFunc1("N/A"))
    }
}
