// Desugaring case class 
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
        override def equals(other : Any) = other match {
            case num: Num => value == num.value
            case _ => false
        }

        //override def hashCode = hash(this.getClass, value.hashCode)

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

        //override def hashCode = hash(this.getClass, left.hashCode, right.hashCode)

        override def toString = "Mul(" + left + ", " + right + ")"
    }

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
