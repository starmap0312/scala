// use of Typecase
// 1) it plays an important role as the type-theoretic foundation of pattern matching in the presence of parametricity
//    i.e. matching typed pattern that has type parameters
// 2) it is logically the same as Type-Tests and Type-Casts, but more concise and secure
//    it is concise for shallow patterns
//    but it becomes more verbose as patterns grow deeper, because of nested match-expressions
// 3) it replies on Scala pattern matching expressions but uses only type patterns for matching cases 
//    case x: T =>  ... the case is matched if and only if x is a subclass of T
//    i.e. x.isInstanceOf[T] = true and val x = x.asInstanceOf[T] happens implicitly
// 4) it requires zero overhead for the class hierarchy
// characteristics
// 1) zero overhead for the class hierarchy
// 2) the pattern matching is concise for shallow patterns
//    but becomes more verbose as patterns grow deeper, because we will need nested match-expressions
// 3) it completely exposes object representation
// 4) same extensibility as type-test/typecast
//    adding new variants poses no problems
//    but adding new patterns require a different syntax
object Typecase {
    // Class hierarchy:
    trait Expr
    class Num(val value: Int)                  extends Expr
    class Var(val name: String)                extends Expr
    class Mul(val left: Expr, val right: Expr) extends Expr

    // Simplification rule:
    def simplify(expr: Expr) = expr match {
        case mul: Mul => {         // isInstanceOf[Mul] and asInstanceOf[Mul] happens implicitly
            mul.right match {
                case num: Num => { // isInstanceOf[Num] and asInstanceOf[Num] happens implicitly
                   if (num.value == 1) {
                       mul.left
                   } else {
                       expr
                   }
                }
                case _ => expr
            }
        }
        case _ => expr
    }

    def main(args: Array[String]) {
        val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr).asInstanceOf[Num].value == 21)
    }
}
