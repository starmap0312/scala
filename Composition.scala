// Total Function vs. Partial Function
// 1) Total Function:   defined for every value of a given type
// 2) Partial Function: defined only for certain value of a given type
// Function composition:
// 1) def compose[A](g: A => T1):  A => R = { x => apply(g(x)) }
//    composes two instances of Function1 in a new Function1, with this function applied last
// 2) def andThen[A](g: R => A) : T1 => A = { x => g(apply(x)) }
//    composes two instances of Function1 in a new Function1, with this function applied first
// def [function] vs. val [function] 
// 1) def [function] evaluates on call, creating new instance of Function1 every time
// 2) val [function] evaluates only once and immediately when defined 
// so def's performance could be worse than with val for multiple calls
// ex. def even: Int => Boolean = _ % 2 == 0
//     val even: Int => Boolean = _ % 2 == 0

object Composition {
    def main(args: Array[String]) {
        // 1) compose: makes a new function that composes two functions
        //    i.e. g(x) compose f(x) = g(f(x))
        def f(x: String): String = "f(" + x + ")"               // method: (x: String)String
        def g(x: String): String = "g(" + x + ")"               // method: (x: String)String, Function1 instance not yet created
        //def g_of_f0: (String => String) = g compose f         // compiler error: missing arguments for method g
        //   because compiler tries to call method g but it does not find its String argument
        //   unapplied methods are only converted to functions when a function type is expected
        //   i.e. we need to make this conversion explicit by writing `g _` or `g(_)` instead of `g`
        def g_of_f0: (String => String) = (g _).compose(f)      // Function1 instance, (g _) creates an instance of Function1
        def g_of_f1: (String => String) =  g _  compose f       // Function1 instance
        def g_of_f2: (String => String) = (g _) compose (f _)   // Function1 instance
        def g_of_f3: (String => String) = (g _).compose(f)      // Function1 instance
        def g_of_f4(x: String): String  = g(f(x))               // method: (x: String)String
        println(g_of_f1("x"))
        println(g_of_f2("x"))
        println(g_of_f3("x"))
        println(g_of_f4("x"))

        // 2) andThen: it works like compose but in the reverse order
        //    def andThen[C](func: (B) => C): PartialFunction[A, C]
        //      note: func is expected to be fully defined, i.e. it doesn't have isDefinedAt
        def f_then_g1: (String => String) = f _ andThen g _     // Function1 instance
        def f_then_g2(x: String): String = g(f(x))              // method: (x: String)String
        println(f_then_g1("x"))
        println(f_then_g2("x"))

        // 3) def [function] vs. val [funciton]
        def f2: (String => String) = "f(" + _ + ")"             // Function1 instance
        def g2: (String => String) = "g(" + _ + ")"             // Function1 instance
        def g_of_f5: (String => String) = g2 compose f2         // Function1 instance, no compiler error
        println(g_of_f5("x"))
    }
}
