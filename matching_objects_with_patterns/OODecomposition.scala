object OODecomposition {
    // class hierarchy
    trait Expr {
        // in statically typed language, ex. C++/Java/Scala, we need to define the test methods and accessor methods in base class
        // test methods return false by default
        def isNum: Boolean = false                         // we need to define all test methods in base class;
        def isVar: Boolean = false                         // otherwise, we will get value isVar is not a member of Expr
        def isMul: Boolean = false
        // accessor methods
        def value: Int     = throw new NotImplementedError // we need to define all accessor methods in base class;
        def name : String  = throw new NotImplementedError // otherwise, we will get value name is not a member of Expr
        def left:  Expr    = throw new NotImplementedError
        def right: Expr    = throw new NotImplementedError
        // in dyanamically typed languages, ex. Python, SmallTalk
        // only test methods need to be defined in base class, accessor methods are not needed in base class
        //   missing accessors will be automatically caught and turned into Exceptions at runtime
        // ex. Python is duck-typing, so we don't need this class hierarchy at all if we don't want it
    }

    // each subclass re-implements its own accessor method and test method
    class Num(override val value: Int)   extends Expr { // override val: unify class constructor and overriding accessor method as one
        override def isNum = true
    }

    class Var(override val name: String) extends Expr {
        override def isVar = true
    }

    class Mul(override val left: Expr, override val right: Expr) extends Expr {
        override def isMul = true
    }

    // simplification rule
    def simplify(expr: Expr) = {
        if (expr.isMul) {                               // use test method is distinguish match patterns
            val r = expr.right                          // use accessor method to get the public field
            if (r.isNum && r.value == 1) {
                expr.left
            } else {
                expr
            }
        } else {
            expr
        }
    }

    def main(args: Array[String]) {
        val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr).value == 21)
    }
}
