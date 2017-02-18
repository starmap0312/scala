// Pattern Matching: match/case contruct
// Syntax
//   [value/typed pattern] match {
//       case [pattern] => [expression]
//       case [pattern] => [expression]
//   }

object PatternMatching {
    // match value & typed pattern
    def matchNumber(x: Any): Any = x match {
        case 1         => "value pattern matched: " + 1 
        case "two"     => "value pattern matched: " + "two"
        case x: Int    => "typed pattern matched: " + "x: Int" 
        case x: Double => "typed pattern matched: " + "x: Double"
        case _         => "nothing matched      : " + "_"
    }

    // match list
    def matchList[T](list: List[T]) = list match {
        case Nil        => ("value pattern matched: Nil")        // the empty list object
        case x::Nil     => ("typed pattern matched: x::Nil")     // a single-element list 
        case List(x)    => ("typed pattern matched: List(x)")    // a single-element list (same as above)
        case 1::2::cs   => ("typed pattern matched: 1::2::cs")   // a list object starting with 1 then 2 
        case x::xs      => ("typed pattern matched: x::xs")      // at-least-one-element list
        case _          => ("nothing matched")
    }

    def main(args: Array[String]) {
        // example 1: match value & typed pattern
        println(matchNumber(1))
        println(matchNumber("two"))
        println(matchNumber(3))
        println(matchNumber(4.0))
        println(matchNumber("N/A"))

        // example 2: match List object
        println(matchList(Nil))
        println(matchList(List(1)))
        println(matchList(List(1, 2)))
        println(matchList(List(3, 4)))
        println(matchList(List(5, 6, 7, 8, 9)))
    }
}
