// use of {}:
// 1) {} indicates a block of code, which is composed of multiple statements and declarations
//    its value is that of the last statement
// 2) however, there are some exceptions to the above definition
//
object Primitives {

    def toInt(str: String): Option[Int] = {
        try {
            Some(Integer.parseInt(str.trim))       // return a Some[Int] object
        } catch {
            case e: NumberFormatException => None  // return a None object if not a number string
        }
    }

    def main(args: Array[String]) {
        // 0) type: a shorthand or alias of [some type]
        //    syntax:  type [alias] = [some type]
        type MyIntFunc = (Int => Int)
        val increment: MyIntFunc = ((x) => x + 1)
        println(increment(9))
     
        // 1) var vs. val
        var myVar: String = "myVar"
        val myVal: String = "myVal"
        myVar = "myVar2"            // note: myVal = "def" throws error: reassignment to val
        println(myVar)
        println(myVal)

        // 2) Option[T]/None[T] extends Option[T]/Some[T] extends Option[T]:
        //    used to avoid problems you encounter when dealing with null values returned from functions
        // ex1.
        toInt("123") match {
            case Some(x) => println(x)
            case None    => println("None object")
        }
        // ex2. Map.get() returns Option[T] which is either Some[T] or None
        val myMap = Map(1 -> "one", 2 -> "two")
        def getMapValue(x: Int): String = myMap.get(x) match {
            case Some(key) => "Found in myMap: " + key
            case None      => "Not found in myMap"
        }
        println(getMapValue(1))
        println(getMapValue(2))
        println(getMapValue(3))
        // syntax suger for pattern matching an Option value
        def getMapValue2(x: Int): String = myMap.get(x).map(
            "Found in myMap: " + _
        ).getOrElse(
            "Not found in myMap"
        )
        println(getMapValue2(1))
        println(getMapValue2(2))
        println(getMapValue2(3))

        // 3) String: length() and concat()
        var palindrome = "Dot saw I was Tod"
        println(palindrome.length())
        println(palindrome.concat(" appended"))
        println(palindrome + " appended")
    }
}
