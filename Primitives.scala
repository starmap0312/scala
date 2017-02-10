object Primitives {
    def main(args: Array[String]) {
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
        // 3) Array
        var arr1:Array[String] = new Array[String](3)
        var arr2 = new Array[String](3)
        var arr3 = Array("zero", "one", "two")
        arr1(0) = "zero"
        println(arr1(0))
        // 3.1) iterate an Array using for-loop
        for (x <- arr3) {
            println(x)
        }
        for (i <- 0 to (arr3.length - 1)) {
            println(arr3(i))
        }
    }
}
