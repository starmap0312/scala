// Desugaring case class 
// Unlike Java, you compare the equality of two objects with ==
// 1) in Java, the == operator compares "reference equality"
// 2) in Scala,the == operator compares "equality of two instances"
object Desugaring {
    // Class hierarchy:
    trait Expr

    //case class Num(value: Int) extends Expr
    // the above is desugared as below:
    object Num {
        def apply(value: Int) = new Num(value)
        def unapply(num: Num) = Some(num.value)
    }
    class Num(_value: Int) extends Expr {

        // Accessors for constructor arguments
        def value = _value

        // Standard methods
        override def equals(other : Any) = other match { // for instance comparison
            case num: Num => value == num.value
            case _ => false
        }

        override def hashCode = super.hashCode

        override def toString = "Num(" + value + ")"
    }

    // case class Mul(left: Expr, right: Expr) extends Expr
    // the above is desugared as below:
    object Mul {
        def apply(left: Expr, right: Expr) = new Mul(left, right)
        def unapply(mul: Mul)              = Some(mul.left, mul.right)
    }
    class Mul(_left: Expr, _right: Expr) extends Expr {

        // Accessors for constructor arguments
        def left  = _left
        def right = _right

        // Standard methods
        override def equals(other : Any) = other match {
            case m: Mul => left.equals(m.left) && right.equals(m.right)
            case _ => false
        }

        override def hashCode = super.hashCode

        override def toString = "Mul(" + left + ", " + right + ")"
    }

    // Simplification rule:
    def simplify(expr: Expr) = expr match {
        //case Mul(x, Num(1)) => x
        // the above is syntactic sugar of the following
        case Mul(x, y) if y.equals(Num(1)) => x
        case _ => expr
    }

    def main(args: Array[String]) {
        val expr = Mul(Num(21), Num(1))   // i.e. val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr) == Num(21)) // as equals() method is defined, we can compare two instances with ==
    }
}
