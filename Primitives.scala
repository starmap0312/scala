object Primitives {

    def toInt(str: String): Option[Int] = {
        try {
            Some(Integer.parseInt(str.trim))       // return a Some[Int] object
        } catch {
            case e: NumberFormatException => None  // return a None object if not a number string
        }
    }

    def main(args: Array[String]) {
        // 1) var vs. val
        var myVar: String = "myVar"
        val myVal: String = "myVal"
        myVar = "myVar2"            // note: myVal = "def" throws error: reassignment to val
        println(myVar)
        println(myVal)

        // 2) Option[T]/None[T] extends Option[T]/Some[T] extends Option[T]:
        //    used to avoid problems you encounter when dealing with null values returned from functions
        toInt("123") match {
            case Some(x) => println(x)
            case None    => println("None object")
        }

        // 3) String: length() and concat()
        var palindrome = "Dot saw I was Tod"
        println(palindrome.length())
        println(palindrome.concat(" appended"))
        println(palindrome + " appended")
    }
}
