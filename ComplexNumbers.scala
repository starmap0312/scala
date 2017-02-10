class Complex(real: Double, imaginary: Double) { // default: class Complex inherits from scala.AnyRef
    def re = real
    def im = imaginary
    override def toString() = "" + re + (if (im < 0) "" else "+") + im + "i"
}

object ComplexNumbers {
    def main(args: Array[String]) {
        val num = new Complex(1.2, 3.4)
        println("imaginary part: " + num.im)
        println("imaginary part: " + num)
    }
}
