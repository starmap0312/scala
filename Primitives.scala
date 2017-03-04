// use of {}:
// 1) {} indicates a block of code, which is composed of multiple statements and declarations
//    its value is that of the last statement
// 2) however, there are some exceptions to the above definition
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

        // 2) String: length() and concat()
        var palindrome = "Dot saw I was Tod"
        println(palindrome.length())
        println(palindrome.concat(" appended"))
        println(palindrome + " appended")

        // 2.1) Symbol: very similar to a String except that they are cached
        //              to compare String instances, you may need to check character-by-character whether they are the same
        //              to compare Symbol instances, it is only a constant time to look-up
        val symbols = Symbol("Symbol string")
        println(symbols)

        // 3) Option[T]/None[T]/Some[T]: the latter two extend Option[T]:
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

        // 4) Either[A, B]/Left/Right: a container type, the latter two extend Either
        //    used when need to deal with situations where the result can be of one of two possible types
        def double: ((Int) => Either[Int, Double]) = {
            case x: Int if x < 5  => Left(x * 2)
            case x: Int if x >= 5 => Right(x * 2.0)
        }
        println(double(3))
        println(double(6))

        // 5) Stream: lazy list
        val stream1 = 1#::2#::3#::Stream.empty
        val stream2 = (1 to 3).toStream         // use Stream to avoid java.lang.OutOfMemory error when creating huge lists
        stream1.foreach(println)
        stream2.foreach(println)
        val list = 1::2::3::Nil
        list.foreach(println)
        // 5.1) Stream.cons([element], [stream]) returns a stream
        val stream3 = Stream.cons(0, Stream.cons(1, Stream.empty))   // cons returns the rest of the stream NOT another new stream
        stream3.foreach(println)

        // 6) upper, lower and view bounds: i.e. <: >: <%

        // 7) define function of varying number of arguments
        def printSeq(arg: Int*): Unit = {
            println(arg)
        }
        printSeq(7, 8, 9)                        // WrappedArray(7, 8, 9)
    }
}
