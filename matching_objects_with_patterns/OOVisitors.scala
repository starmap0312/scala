// use of Visitor design pattern
// 1) simulate pattern matching using double dispatch
object OOVisitors {
    // class hierarchy
    trait Visitor[T] {
        def caseNum(t:  Num): T = default(t)              // case-methods
        def caseVar(t:  Var): T = default(t)
        def caseMul(t:  Mul): T = default(t)
        def default(t: Expr): T = throw new MatchError(t)
    }

    trait Expr {
        def matchWith[T](v: Visitor[T]): T
    }

    class Num(val value: Int)   extends Expr {
        def matchWith[T](v: Visitor[T]): T = v.caseNum(this)
    }

    class Var(val name: String) extends Expr {
        def matchWith[T](v: Visitor[T]): T = v.caseVar(this)
    }

    class Mul(val left: Expr, val right: Expr) extends Expr {
        def matchWith[T](v: Visitor[T]): T = v.caseMul(this)
    }

    // simplification rule
    def simplify(expr: Expr) = {
        expr.matchWith {
            new Visitor[Expr] {
                override def caseMul(mul: Mul) = mul.right.matchWith {
                    new Visitor[Expr] {
                        override def caseNum(num: Num) = {
                            if (num.value == 1) {
                                mul.left
                            } else {
                                expr
                            }
                        }
                        override def default(e: Expr) = e
                    }
                }
                override def default(e: Expr) = e
            }
        }
    }

    def main(args: Array[String]) {
        val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr).asInstanceOf[Num].value == 21)
    }
}
