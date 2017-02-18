// 1) Function
//    defined for every value of a given type
// 2) PartialFunction
//    defined only for certain value of a given type

object Composition {
    def main(args: Array[String]) {
        // 1) compose: makes a new function that composes two functions
        //    i.e. g(x) compose f(x) = g(f(x))
        def f(x: String): String = "f(" + x + ")"
        def g(x: String): String = "g(" + x + ")"
        def g_of_f1: (String => String) = g _ compose f _
        def g_of_f2(x: String): String = g(f(x))
        println(g_of_f1("x"))
        println(g_of_f2("x"))
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
