// use of {}:
// 1) {} indicates a block of code, which is composed of multiple statements and declarations
//    its value is that of the last statement
// 2) however, there are some exceptions to the above definition
object OptionEither {

    def toInt(str: String): Option[Int] = {        // return a Option[Int] object (i.e. could be Some[Int] or None)
        try {
            Some(Integer.parseInt(str.trim))       // return a Some[Int] object
        } catch {
            case e: NumberFormatException => None  // return a None object if not a number string
        }
    }

    def main(args: Array[String]) {
        // 1) None/Some[T] extend Option[T]:
        //    used to avoid problems you encounter when dealing with null values returned from functions
        // ex1. toInt() returns a Option[Int]
        toInt("123") match {
            case Some(x) => println(x)
            case None    => println("None object")
        }
        // ex2. Map.get() returns Option[T] (i.e. Some[T] or None)
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

        // 2) Either[A, B]/Left/Right: a container type, the latter two extend Either
        //    used when need to deal with situations where the result can be of one of two possible types
        //    (a special case of List)
        def double: ((Int) => Either[Int, Double]) = {
            case x: Int if x < 5  => Left(x * 2)
            case x: Int if x >= 5 => Right(x * 2.0)
        }
        println(double(3))
        println(double(6))
    }
}
