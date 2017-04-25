// example: typed evaluator for lambda expressions 
object Parametricity2 {
    // Class hierarchy:
    trait Term[A]
    case class Var[A](val name: String)                        extends Term[A]
    case class Num(val value: Int)                             extends Term[Int]
    case class Lam[B, C](val x: Var[B], val e: Term[C])        extends Term[B => C]
    case class App[B, C] (val f: Term[B => C], val e: Term[B]) extends Term[C]
    case object Suc                                            extends Term[Int => Int]

    // Environments:
    abstract class Env {
        def apply[A](v: Var[A]): A
        def extend[A](v: Var[A], x: A) = new Env {
            def apply[B](w: Var[B]): B = w match {
                //case _: v.type => x
                case _ => Env.this.apply(w)
            }
        }
    }

    object empty extends Env {
        def apply[A](x: Var[A]): A = throw new Error("not found : " + x.name)
    }

    // Evaluation:
    def eval[A, B](t : Term[A], env : Env): A = t match {
        case v @ Var(name)     => env(v)
        case Num(value)        => value
        case Suc               => { y: Int => y + 1 }
        case Lam(x: Var[B], e) => { y: B => eval(e, env.extend(x, y)) }
        case App(f, e)         => eval(f, env)(eval(e, env))
    }

    def main(args: Array[String]) {
        def headOfAny1[A](x : Any) = x match {      // [A](x: Any)A
            case xs: List[A] => xs.head             // error: type parameter A escapes its scope as part of the type of xs.head
        }
        def headOfAny2[A](x : Any): Any = x match { // [A](x: Any)Any
            case xs: List[A] => xs.head             // OK, xs.head is inferred to have type Any, an explicitly given return type
        }

    }
}
