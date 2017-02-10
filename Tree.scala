abstract class Tree
case class Sum(l: Tree, r: Tree) extends Tree
case class Var(n: String) extends Tree
case class Const(v: Int) extends Tree

type Environment = String => Int

object HelloWorld {
    def main(args: Array[String]) {
        val exp: Tree = Sum(Sum(Var("x"),Var("x")),Sum(Const(7),Var("y")))
        val env: Environment = { case "x" => 5 case "y" => 7 }
        println("Hello, world!")
    }
}
