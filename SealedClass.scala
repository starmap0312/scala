// sealed class:
// 1) a sealed class may not be directly inherited, except if the inheriting subclass is defined in the same source file
// 2) however, subclasses of a sealed class can inherited anywhere
// 3) sealed class is useful for creating ADTs (algebraic data types)
// 4) if a trait is sealed, the compiler can warn you if your pattern matches are not exhaustive enough 
//    ex. the compiler knows that Option is limited to Some and None
//
// ex1. Option[A] is sealed, it cannot be extended outside the source file by other developers (doing so would alter its meaning)
//      but its subclasses Some/None can be extended elsewhere
//   sealed trait Option[+A]
//   final case class Some[+A] extends Option[A]
//   object None extends Option[Nothing]
// ex2.
//   sealed abstract class List[+A] extends AbstractSeq[A]
//   case object Nil extends List[Nothing]
//   final case class ::[B](override val head: B, private[scala] var tl: List[B]) extends List[B]
//   object List extends SeqFactory[List]
//
// final class vs. sealed class
// 1) final class cannot be extended
// 2) sealed trait can only be extended in the same source file as it is declared
object SealedClass {
    def main(args: Array[String]) {
        val opt: Option[Int] = Some(1)
        opt match {
            case Some(x) => println(x) // 1
        } // compiler warning: match may not be exhaustive. It would fail on the following input: None
    }
}
