// Case class 
// Uses of notation "=>":
// 1) anonymous funciton
//    syntax:
//      ([list of parameters]) => { function body }
//    ex.
//      var inc = (x:Int) => x+1
// 2) pattern matching
//    syntax:
//      [variable] match {
//          case [pattern] => [expression]
//          case [pattern] => [expression]
//      }
//    ex.
//      x match {
//          case 1 => "one"
//          case 2 => "two"
//      }

object CaseClass2 {
    abstract class Tree
    case class Sum(x: Tree, y: Tree) extends Tree
    case class Var(x: String)        extends Tree
    case class Const(x: Int)         extends Tree

    type Environment = (String => Int)

    def eval(tree: Tree, env: Environment): Int = tree match {
        case Sum(x, y) => eval(x, env) + eval(y, env)          // constructor pattern
        case Var(x)    => env(x)                               // constructor pattern
        case Const(x)  => x                                    // constructor pattern
    }

    def main(args: Array[String]) {
        val tree: Tree = Sum(
            Sum(Var("x"), Var("x")),
            Sum(Const(7), Var("y"))
        )
        val env: Environment = {
            case "x" => 5
            case "y" => 7
        }
        println("Expression: " + tree)
        println("Evaluation with x = 5, y = 7: " + eval(tree, env))
    }
}
