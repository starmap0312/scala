object Evaluation {
    abstract class Expr
    case class Num(x: Int) extends Expr
    case class Neg(x: Expr) extends Expr
    case class Add(x: Expr, y: Expr) extends Expr
    case class Div(x: Expr, y: Expr) extends Expr

    // example1
    def eval1(expr: Expr): Int = expr match {
        case Num(n)      => n                     // constructor pattern
        case Neg(e)      => -(eval1(e))           // constructor pattern
        case Add(e1, e2) => eval1(e1) + eval1(e2) // constructor pattern
    }

    // example2
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

    def main(args: Array[String]) {
        // example1: only 3 operators: Num/Neg/Add
        val expr1: Expr = Add(Neg(Num(1)), Add(Neg(Num(2)), Neg(Num(3))))
        val expr2: Expr = Div(Neg(Num(4)), Num(2)) 
        val expr3: Expr = Div(Num(4), Add(Num(2), Neg(Num(2)))) 
        println("Evaluation = " + eval1(expr1))

        // example2: 4 operators: Num/Neg/Add/Div
        //   to add the Div operator, we can make eval() become a partial function
        //   ex. for inputs of divided by 0, the function needs to map to None (i.e. undefined)
        //   i.e. the funciton returns a Option(None/Some) object
        println("Evaluation = " + eval2(expr1))
        println("Evaluation = " + eval2(expr2))
        println("Evaluation = " + eval2(expr3))
    }
}
