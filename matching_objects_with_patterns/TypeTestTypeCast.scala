// use of Type-Test and Type-Cast
// 1) Type-Cast: x.asInstanceOf[T]
//      it is a cast, it is only needed by the compiler to enforce the type compatibility
//      it does not do anything at all: a reference is a reference, regarding of the type of underlying object
//    Type-Test: x.isInstanceOf[T]
//      isInstanceOf is the opposite: compiler does not know anything about it, it's just a function call
//      it is executed at runtime to check whether the given object is of the expected type
//      so we will need a real Class instance if we need the information at runtime
//    note type parameters are not available at runtime, all information they carry can only be used by the compiler
// 2) most direct form of decomposition
//    zero overhead for the class hierarchy
// 3) type-casts are potentially unsafe because they may raise ClassCastExceptions at runtime
//    ex. it is safe to a.asInstanceOf[B] if a: A and A <: B (i.e. A is a sub-type of B, A is more specific)
//        but x.asInstanceOf[B] throws ClassCastExceptions if x: X and X is NOT a sub-type of B
// characteristics
// 1) no representation independence
//    type-tests and type-casts completely expose representation
// 2) mixed characteristics with respect to extensibility
//    one can add new variants without changing the framework (because nothing to be done in the framework itself)
//    one cannot invent new patterns over existing variants that use the same syntax
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
