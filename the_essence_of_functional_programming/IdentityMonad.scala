// IdentityMonad 
// 1) apply([value]):      i.e. return
//    apply(x) = x
// 2) flatMap([function]): i.e. >>=
//    m.flatMap(f) = f(m)
object IdentityMonad {
    abstract class Expr
    case class Num(x: Int)           extends Expr
    case class Neg(x: Expr)          extends Expr
    case class Add(x: Expr, y: Expr) extends Expr

    // example: data Expr = Num Int | Neg Expr | Add Expr Expr
    def eval(expr: Expr): Int = expr match {
        case Num(n)      => n                   // constructor pattern
        // note: eval(Num(n))      = apply(n) = n
        case Neg(e)      => -(eval(e))          // constructor pattern
        // note: eval(Neg(e))      = eval(e).flatMap((x:Int) => apply(-x)) = eval(e).flatMap((x:Int) => -x) = -(eval(e))
        case Add(e1, e2) => eval(e1) + eval(e2) // constructor pattern
        // note: eval(Add(e1, e2)) = eval(e1).flatMap((v1:Int) => eval(e2).flatMap((v2:Int) => v1 + v2))    = eval(e1) + eval(e2)
    }

    def main(args: Array[String]) {
        // example1: only 3 operators: Num/Neg/Add
        val expr: Expr = Add(Neg(Num(1)), Add(Neg(Num(2)), Neg(Num(3))))
        println("Evaluation = " + eval(expr))   // Evaluation = -6
    }
}
