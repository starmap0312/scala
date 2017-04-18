object OODecomposition {
    // class hierarchy
    trait Expr {
        def isNum: Boolean = false                         // test methods return false by default
        def isVar: Boolean = false
        def isMul: Boolean = false
        def value: Int     = throw new NotImplementedError // accessor methods
        def name : String  = throw new NotImplementedError
        def left:  Expr    = throw new NotImplementedError
        def right: Expr    = throw new NotImplementedError
    }

    // each subclass re-implements its own accessor method and test method
    class Num(override val value: Int)   extends Expr { // override val: unify class constructor and overriding accessor method as one
        override def isNum = true
    }

    class Var(override val name: String) extends Expr {
        override def isVar = true
    }

    class Mul(override val left: Expr, override val right: Expr) extends Expr {
        override def isMul = true
    }

    // simplification rule
    def simplify(expr: Expr) = {
        if (expr.isMul) {                               // use test method is distinguish match patterns
            val r = expr.right                          // use accessor method to get the public field
            if (r.isNum && r.value == 1) {
                expr.left
            } else {
                expr
            }
        } else {
            expr
        }
    }

    def main(args: Array[String]) {
        val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr).value == 21)
    }
}
