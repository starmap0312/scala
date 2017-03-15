// Total Function vs. Partial Function
// 1) Function
//    defined for every value of a given type
// 2) PartialFunction
//    defined only for certain value of a given type
// Function composition:
// 1) def compose[A](g: A => T1):  A => R = { x => apply(g(x)) }
//    composes two instances of Function1 in a new Function1, with this function applied last
// 2) def andThen[A](g: R => A) : T1 => A = { x => g(apply(x)) }
//    composes two instances of Function1 in a new Function1, with this function applied first
// def [function] vs. val [function] 
// 1) def [function] evaluates on call and creates new instance of Function1 every time
// 2) val [function] evaluates when defined 
// so def's performance could be worse then with val for multiple calls
// ex. def even: Int => Boolean = _ % 2 == 0
//     val even: Int => Boolean = _ % 2 == 0
// Function1
//   a function of 1 parameter

object Composition {
    def main(args: Array[String]) {
        // 1) compose: makes a new function that composes two functions
        //    i.e. g(x) compose f(x) = g(f(x))
        def f(x: String): String = "f(" + x + ")"
        def g(x: String): String = "g(" + x + ")"               // not an instance of Function1
        //def g_of_f0: (String => String) = g.compose(f)        // compiler error: missing arguments for method g
        def g_of_f0: (String => String) = (g _).compose(f)      // (g _) is an instance of Function1
        def g_of_f1: (String => String) = g _ compose f
        def g_of_f2: (String => String) = (g _) compose (f _)
        def g_of_f3(x: String): String  = g(f(x))
        def g_of_f4: (String => String) = (g _).compose(f)
        println(g_of_f1("x"))
        println(g_of_f2("x"))
        println(g_of_f3("x"))
        println(g_of_f4("x"))
        // 2) andThen: it works like compose but in the reverse order
        //    i.e. g(x) andThen f(x) = f(g(x))
        //    def andThen[C](func: (B) => C): PartialFunction[A, C]
        //      func is expected to be fully defined, i.e. it doesn't have isDefinedAt
        def f_then_g1: (String => String) = f _ andThen g _
        def f_then_g2(x: String): String = g(f(x))
        println(f_then_g1("x"))
        println(f_then_g2("x"))
    }
}
