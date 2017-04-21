// use of Extractors
// 1) unlike case-classes, extractors can be used to hide data representations
//    the library interface might expose only the objects Num, Var, and Mul, but not the corresponding classes
//    that way, one can modify any of the classes representing arithmetic expressions without affecting client code
//    this allows easy extensions by both new variants and new patterns, since patterns are resolved to user-defined methods
// 2) note that each of the extraction methods takes an argument of its specific type, not the common type Expr
//    this is is possible because an implicit type test gets added when matching on a term
//    otherwise, implicit casting just happens and may throw ClassCastException at runtime
//    ex.
//        val Num(x) = expr        ... Num.unapply(expr.asInstanceOf[Num]) may throw ClassCastException
// Scala is loosely typed
//   i.e. a pattern does not restrict the set of legal types of the corresponding selector value
//   ex.
//   val x = Num(1)
//   x match {
//       case Mul(l, r) => ...
//   }
//   Mul.unapply(x) will be preceded by a type test whether the argument x has type Mul
//   i.e.
//   x match {
//       case x: Mul => {
//           Mul(l, r) => ...
//       }
//   }
// Representation independence:
//   unlike case-classes, extractors can be used to hide data representations
//     ex. a Polar object can have internal data representation of Cartesian coordinates
//         apply() takes a length and an angle, then constructs a Cartesian
//         unapply() takes a Complex, which is casted to a Cartesian, then computing the corresponding length and angle
//   how much of an object’s representation needs to be revealed by a pattern match
//     ex. Type-tests/type-casts, case classes are completely exposing representation
//   extraction can play the role of a representative object
//     its constituents (if any) can be bound or matched further with nested pattern matches
object Extractors {
    // Class hierarchy:
    trait Expr

    object Num {
        def apply(value: Int) = new Num(value)
        def unapply(num: Num) = Some(num.value)             // unapply() takes Num instead of Expr
    }
    class Num(val value: Int)                  extends Expr // this can be hidden from client

    object Var {
        def apply(name: String)    = new Var(name)
        def unapply(variable: Var) = Some(variable.name)    // unapply() takes Var instead of Expr
    }
    class Var(val name: String)                extends Expr // this can be hidden from client

    object Mul {
        def apply(left: Expr, right: Expr) = new Mul(left, right)
        def unapply(mul: Mul)              = Some(mul.left, mul.right) // unapply() takes Mul instead of Expr
        // we can write the type test by ourself to hide the underlying representation 
        //   this removes the target type from the interface, more effectively hiding underlying representation
        //   the client handles Expr, and need not to know the actual representation is a Mul
        //def unapply(x: Expr) = x match {
        //    case mul: Mul => Some(mul.left, mul.right)
        //    case _ => None
        //}
    }
    class Mul(val left: Expr, val right: Expr) extends Expr // this can be hidden from client

    // Simplification rule:
    def simplify(expr: Expr) = expr match {
        case Mul(x, Num(1)) => x        // implicit type tests are added when matching 
        case _ => expr                  // i.e. case mul: Mul => mul match { case Mul(x, Num(1)) => } happens implicitly
    }

    def main(args: Array[String]) {
        val expr = Mul(Num(21), Num(1))      // i.e. val expr = new Mul(new Num(21), new Num(1))
        assert(simplify(expr).asInstanceOf[Num].value == 21)
        // assert(simplify(expr) == Num(21)) // the default comparison operator does not work, unless we override equals() method in subclasses
    }
}
