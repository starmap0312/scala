// use of Case Classes
// 1) case class
//    Scala provides convenient shorthands for constructing and analyzing data
//    case class is syntactic sugar for a normal class with an injector/extractor (companion) object
// 2) the case modifier has several effects:
//    apply()  : convenient for constructing data without having to write new
//    unapply(): it allows pattern matching on their constructor
//               they are written exactly like constructor expressions, but are interpreted in reverse (i.e. extractors)
// Unlike Java, you compare the equality of two objects with ==
// 1) in Java, the == operator compares "reference equality"
// 2) in Scala,the == operator compares "equality of two instances"
// characteristics
// (good conciseness)
// 1) no notational overhead for the class hierarchy
// 2) pattern matching code is concise for both shallow and deep (nested) patterns
// 3) it exposes object representation
// 4) mixed characteristics with respect to extensibility
//    adding new variants is straightforward
//    but it is not possible to define new kinds of patterns
//      because patterns are in a one-to-one correspondence with (the types of) case classes
// the shortcoming is eliminated when case classes are paired with extractors
object CaseClasses {
    // Class hierarchy:
    trait Expr
    case class Num(val value: Int)                  extends Expr
    case class Var(val name: String)                extends Expr
    case class Mul(val left: Expr, val right: Expr) extends Expr

    // Simplification rule:
    def simplify(expr: Expr) = expr match {
        case Mul(x, Num(1)) => x
        // the above is syntactic sugar of the following
        //case Mul(x, y) if y.equals(Num(1)) => x
        case _ => expr
    }

    def main(args: Array[String]) {
        val expr = Mul(Num(21), Num(1))   // i.e. val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr) == Num(21)) // case class defines equals() method for you
    }
}
