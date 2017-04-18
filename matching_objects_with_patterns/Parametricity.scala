// Parametricity
object Parametricity {
    // Class hierarchy:
    trait Term[A]
    class Var[A](val name: String)                        extends Term[A]
    class Num(val value: Int)                             extends Term[Int]
    class Lam[B, C](val x: Var[B], val e: Term[C])        extends Term[B => C]
    class App[B, C] (val f: Term[B => C], val e: Term[B]) extends Term[C]
    class Suc ()

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
    def eval[A](t : Term[A], env : Env): A = t match {
        case v: Var[_] => env(v)
        case n: Num    => n.value
        case i: Suc    => { y: Int => y + 1 }
        case f: Lam[_, _] => { y: Any => eval(f.e, env.extend(f.x, y)) }
        case a: App[_, _] => eval(a.f, env)(eval(a.e, env)) // a = c
    }

    def main(args: Array[String]) {
    }
}
