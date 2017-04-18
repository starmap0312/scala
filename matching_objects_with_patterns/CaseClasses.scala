// use of Case Classes
// 1) case class
//    Scala provides convenient shorthands for constructing and analyzing data
//    case class is syntactic sugar for a normal class with an injector/extractor (companion) object
// 2) the case modifier has several effects:
//    apply()  : convenient for constructing data without having to write new
//    unapply(): it allows pattern matching on their constructor
//               they are written exactly like constructor expressions, but are interpreted in reverse (i.e. extractors)
object CaseClasses {
    // Class hierarchy:
    trait Expr
    case class Num(val value: Int)                  extends Expr
    case class Var(val name: String)                extends Expr
    case class Mul(val left: Expr, val right: Expr) extends Expr

    // Simplification rule:
    def simplify(expr: Expr) = expr match {
        case Mul(x, Num(1)) => x
        case _ => expr
    }

    def main(args: Array[String]) {
        val expr = Mul(Num(21), Num(1)) // i.e. val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr).asInstanceOf[Num].value == 21)
    }
}
