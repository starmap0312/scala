// Parametricity
// static typing vs. dynamic typing
// 1) static typing: ex. C/C++, Java/Scala
//    a language is statically typed if the type of a variable is known at compile time
//    a programmer must specify what type each variable is
// 2) offer some form of type inference: ex. Scala/Haskell
//    the type system will try to deduce the type of a variable
// advantages of the above: type-checking by the compiler (less bug)
// 3) dynamic typing: ex. Perl, Ruby, Python
//    a language is dynamically typed if the type is associated with run-time values, and not named variables/fields/etc.
//    a programmer does not need to specify types every time 
//    most scripting languages do not have compiler to do static type-checking 
// example: typed evaluator for lambda expressions 
object Parametricity {
    // Class hierarchy:
    trait Term[A]
    class Var[A](val name: String)                        extends Term[A]
    class Num(val value: Int)                             extends Term[Int]
    class Lam[B, C](val x: Var[B], val e: Term[C])        extends Term[B => C]
    class App[B, C] (val f: Term[B => C], val e: Term[B]) extends Term[C]
    class Suc()                                           extends Term[Int => Int]

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
    def eval[A, B, C](t : Term[A], env : Env): A = t match {
        case v: Var[B] => env(v)                                       // A = B
        case n: Num    => n.value                                      // A = Int
        case i: Suc    => { y: Int => y + 1 }                          // A = Int => Int
        case f: Lam[B, C] => { y: B => eval(f.e, env.extend(f.x, y)) } // A = B => C
        case a: App[B, C] => eval(a.f, env)(eval(a.e, env))            // A = C
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
