// use of Extractors
// 1) unlike case-classes, extractors can be used to hide data representations
//    the library interface might expose only the objects Num, Var, and Mul, but not the corresponding classes
//    that way, one can modify any of the classes representing arithmetic expressions without affecting client code
// 2) note that each of the extraction methods takes an argument of its specific type, not the common type Expr
//    this is is possible because an implicit type test gets added when matching on a term
//    otherwise, implicit casting just happens and may throw ClassCastException at runtime
//    ex.
//        val Num(x) = expr        ... Num.unapply(expr.asInstanceOf[Num]) may throw ClassCastException
object Extractors {
    // Class hierarchy:
    trait Expr

    object Num {
        def apply(value: Int) = new Num(value)
        def unapply(num: Num) = Some(num.value)
    }
    class Num(val value: Int)                  extends Expr // this can be hidden from client

    object Var {
        def apply(name: String)    = new Var(name)
        def unapply(variable: Var) = Some(variable.name)
    }
    class Var(val name: String)                extends Expr // this can be hidden from client

    object Mul {
        def apply(left: Expr, right: Expr) = new Mul(left, right)
        def unapply(mul: Mul)              = Some(mul.left, mul.right)
        // we can write the type test by ourself to avoid potential ClassCastException at runtime, see below
        //def unapply(x: Expr) = x match {
        //    case mul: Mul => Some(mul.left, mul.right)
        //    case _ => None
        //}
    }
    class Mul(val left: Expr, val right: Expr) extends Expr // this can be hidden from client

    // Simplification rule:
    def simplify(expr: Expr) = expr match {
        case Mul(x, Num(1)) => x        // implicit type tests, i.e. Mul and Num, are added when matching 
        case _ => expr
    }

    def main(args: Array[String]) {
        val expr = Mul(Num(21), Num(1)) // i.e. val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr).asInstanceOf[Num].value == 21)
    }
}
