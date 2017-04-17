object TypeTestTypeCast {
    // class hierarchy
    trait Expr

    class Num(val value: Int)                  extends Expr
    class Var(val name: String)                extends Expr
    class Mul(val left: Expr, val right: Expr) extends Expr

    // simplification rule
    def simplify(expr: Expr) = {
        if (expr.isInstanceOf[Mul]) {
            val mul = expr.asInstanceOf[Mul]
            val r = mul.right
            if (r.isInstanceOf[Num]) {
                val num = r.asInstanceOf[Num]
                if (num.value == 1) {
                    mul.left
                } else {
                    expr
                }
            } else {
                expr
            }
        } else {
            expr
        }
    }

    def main(args: Array[String]) {
        val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr).asInstanceOf[Num].value == 21)
    }
}
