// use of Type-Test and Type-Cast
// 1) Type-Test: x.isInstanceOf[T ]
//    Type-Cast: x.asInstanceOf[T ]
// 2) most direct form of decomposition
//    zero overhead for the class hierarchy
// 3) type-casts are potentially unsafe because they can raise ClassCastExceptions
object TypeTestTypeCast {
    // Class hierarchy:
    trait Expr
    class Num(val value: Int)                  extends Expr
    class Var(val name: String)                extends Expr
    class Mul(val left: Expr, val right: Expr) extends Expr

    // Simplification rule:
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