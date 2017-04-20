object OODecomposition2 {
    // class hierarchy
    trait Expr {
        // 1) replace parameterized method with explict methods
        //    this improves readability (no need to look into the parameterized method: ex. typeCode() gives you the subclass type)
        def isNum: Boolean = false                         // test methods return false by default
        def isVar: Boolean = false
        def isMul: Boolean = false
        def value: Int     = throw new NotImplementedError // accessor methods
        def name : String  = throw new NotImplementedError
        def left:  Expr    = throw new NotImplementedError
        def right: Expr    = throw new NotImplementedError
        // 2) replace conditional with polymorphism
        //    this removes if-branches in client code, by delegating the branching behaviors to subclasses
        def simplify()     = this                          // delegate simplify() method to subclasses
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
        override def simplify() = {
            if (right.isNum && right.value == 1) {
                left
            } else {
                this
            }
        }
    }

    def main(args: Array[String]) {
        val expr = new Mul(new Num(21), new Num(1))
        // no if-branches in the client code
        assert(expr.simplify().value == 21)
    }
}
