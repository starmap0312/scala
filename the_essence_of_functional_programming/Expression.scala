object Expression {
    abstract class Expr
    case class Num(x: Int)           extends Expr
    case class Neg(x: Expr)          extends Expr
    case class Add(x: Expr, y: Expr) extends Expr
    case class Div(x: Expr, y: Expr) extends Expr
    case class Or(x: Expr, y: Expr)  extends Expr

    // example1: data Expr = Num Int | Neg Expr | Add Expr Expr
    def eval1(expr: Expr): Int = expr match {
        case Num(n)      => n                     // constructor pattern
        case Neg(e)      => -(eval1(e))           // constructor pattern
        case Add(e1, e2) => eval1(e1) + eval1(e2) // constructor pattern
    }

    // example2: data Expr = Num Int | Neg Expr | Add Expr Expr | Div Expr Expr
    // 2.1) use if-then-else branches
    def eval2(expr: Expr): Option[Int] = expr match {
        case Num(n)    => Some(n)
        case Neg(e)    => eval2(e) match {
            case Some(v) => Some(-v)
            case None    => None
        } 
        case Add(e1, e2) => eval2(e1) match {
            case Some(v1) => eval2(e2) match {
                case Some(v2) => Some(v1 + v2)
                case None     => None
            }
            case None     => None
        } 
        case Div(e1, e2) => eval2(e2) match {
            case Some(0) => None
            case Some(v2) => eval2(e1) match {
                case Some(v1) => Some(v1 / v2)
                case None     => None
            }
            case None    => None
        }
    }

    // 2.2) use Option Monad:
    //      Option Monad has apply() and flatMap() methods so that we can omit the if-else branches
    def eval3(expr: Expr): Option[Int] = expr match {
        case Num(n)    => Some(n)
        case Neg(e)    => eval3(e).flatMap(
            (v: Int) => Some(-v)
        )
        case Add(e1, e2) => eval3(e1).flatMap(
            (v1: Int) => {
                eval3(e2).flatMap(
                    (v2: Int) => Some(v1 + v2)
                )
            }
        )
        case Div(e1, e2) => eval3(e2).flatMap(
            (v2: Int) => {
                if (v2 == 0) {
                    None
                } else {
                    eval3(e1).flatMap(
                        (v1: Int) => Some(v1 / v2)
                    )
                }
            }
        )
    }

    // example 3: data Expr = Num Int | Neg Expr | Add Expr Expr | Div Expr Expr | Or Expr Expr
    // 3) use List Monad
    //    List Monad has a List concat operation: ++/:::
    def eval4(expr: Expr): List[Int] = expr match {
        case Num(n)    => List(n)
        case Neg(e)    => eval4(e).flatMap(
            (x: Int) => List(-x)
        )
        case Add(e1, e2) => eval4(e1).flatMap(
            (v1: Int) => {
                eval4(e2).flatMap(
                    (v2: Int) => List(v1 + v2)
                )
            }
        )
        case Div(e1, e2) => eval4(e2).flatMap(
            (v2: Int) => {
                if (v2 == 0) {
                    Nil                          // i.e. List.empty
                } else {
                    eval4(e1).flatMap(
                        (v1: Int) => List(v1 / v2)
                    )
                }
            }
        )
        case Or(e1, e2) => eval4(e1):::eval4(e2) // i.e. eval4(e1)++eval4(e2)
    }

    def main(args: Array[String]) {
        // example1: only 3 operators: Num/Neg/Add
        val expr1: Expr = Add(Neg(Num(1)), Add(Neg(Num(2)), Neg(Num(3))))
        val expr2: Expr = Div(Neg(Num(4)), Num(2)) 
        val expr3: Expr = Div(Num(4), Add(Num(2), Neg(Num(2)))) 
        println("Evaluation = " + eval1(expr1))

        // example2: 4 operators: Num/Neg/Add/Div
        // 2.1) use if-then-else branches
        //   to add the Div operator, we can make eval() become a partial function
        //   ex. for inputs of divided by 0, the function needs to map to None (i.e. undefined)
        //   i.e. the funciton returns a Option(None/Some) object
        println("Evaluation = " + eval2(expr1))
        println("Evaluation = " + eval2(expr2))
        println("Evaluation = " + eval2(expr3))

        // 2.2) use Option Monad: apply() and flatMap() methods
        println("Evaluation = " + eval3(expr1))
        println("Evaluation = " + eval3(expr2))
        println("Evaluation = " + eval3(expr3))

        // example3: 5 operators: Num/Neg/Add/Div/Or
        // 3) use List Monad
        val expr4: Expr = Add(Or(Num(1), Num(2)), Or(Num(3), Num(4))) 
        val expr5: Expr = Add(Or(Num(1), Div(Num(2), Num(0))), Or(Num(3), Num(4)))
        println(eval4(expr4))              // List(4, 5, 5, 6)
        println(eval4(expr5))              // List(4, 5)
    }
}
