
object Zip {
    def cons[T](x: (T, T), xs: List[(T, T)]): List[(T, T)] = x::xs
    def zip [T](list1: List[T], list2: List[T]): List[(T, T)] = (list1, list2) match { 
        case (a::s, b::t) => cons((a, b), zip(s, t))
        case _            => Nil
    }

    def main(args: Array[String]) {
        for ((x, y) <- zip(List(1, 2, 3), List(4, 5, 6))) {
            println(x, y)
        }
        for ((x, y) <- zip(List("x1", "x2", "x3"), List("y1", "y2", "y3"))) {
            println(x, y)
        }
    }
}
