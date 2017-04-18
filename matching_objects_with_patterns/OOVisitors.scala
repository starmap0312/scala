// use of Visitor design pattern
// 1) simulate pattern matching using double dispatch
// 2) it causes high notational overhead for framework construction
//    because a visitor class has to be defined and matches() methods be provided in all data variants
object OOVisitors {
    // class hierarchy
    trait Visitor[T] {                                        // each of its subclasses implements its own case-methods
        def case_Num(t:  Num): T = case_(t)
        def case_Var(t:  Var): T = case_(t)
        def case_Mul(t:  Mul): T = case_(t)
        def case_(t: Expr): T = throw new MatchError(t)
    }

    trait Expr {
        def matches[T](v: Visitor[T]): T
    }

    class Num(val value: Int)   extends Expr {
        def matches[T](v: Visitor[T]): T = v.case_Num(this)   // v must have case_Num() implemented
    }

    class Var(val name: String) extends Expr {                // v must have case_Var() implemented
        def matches[T](v: Visitor[T]): T = v.case_Var(this)
    }

    class Mul(val left: Expr, val right: Expr) extends Expr { // v must have case_Mul() implemented
        def matches[T](v: Visitor[T]): T = v.case_Mul(this)
    }

    // simplification rule
    def simplify(expr: Expr) = {
        expr matches {
            new Visitor[Expr] {         // a Visitor with case_Mul() implemented to simulate matching case Mul
                override def case_Mul(mul: Mul) = mul.right matches {
                    new Visitor[Expr] { // a Visitor with case_Num() implemented to simulate matching case Num
                        override def case_Num(num: Num) = {
                            if (num.value == 1) {
                                mul.left
                            } else {
                                expr
                            }
                        }
                        override def case_(e: Expr) = e
                    }
                }
                override def case_(e: Expr) = e
            }
        }
    }

    def main(args: Array[String]) {
        val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr).asInstanceOf[Num].value == 21)
    }
}
