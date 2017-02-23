
object Containers {
    def main(args: Array[String]) {
        // 0) Tuples: a sequence of immutable items
        // 0.1) Tuple2: a pair of immutable items
        val pair1 = (1, "two")
        // which is syntactic sugar of
        val pair2 = new Tuple2(1, "two") 
        val pair3 = (1 -> "two") 
        println(pair1)
        println(pair2)
        println(pair3)
        // 0.2) Tuple3: a triple of immutable items
        val tuple1 = (1, "two", 3.0)
        // which is syntactic sugar of
        val tuple2 = new Tuple3(1, "two", 3.0) 
        println(tuple1)
        println(tuple2._1, tuple2._2, tuple2._3)

        // 1) Array[T]
        val arr1:Array[String] = new Array[String](3) // var arr1 = new Array[String](3)
        val arr2 = Array("zero", "one", "two")
        arr1(0) = "zero"
        println(arr1(0))
        // 1.1) iterate an Array using for loop
        for (i <- 0 to (arr2.length - 1)) {
            println(arr2(i))
        }
        // 1.2) iterate an Array using for-in loop
        for (x <- arr2) {
            println(x)
        }

        // 2) List[T]
        val fruits: List[String] = List("apple", "orange", "pear")
        // 2.1) iterate List via for-in loop
        for (fruit <- fruits) {
            println(fruit)
        }
        // 2.2) foreach([func]): iterate List via Collections's foreach([func]) method
        fruits.foreach(println) 
        fruits.foreach((x: String) => {println("Fruit: " + x)})
        // 2.3) map([func]): map a collection to another based on passed-in function
        val numbers = List(1, 2, 3, 4, 5)
        val doubles = numbers.map((x: Int) => (x * 2))
        doubles.foreach(println)
        // 2.4) flatMap([func]): map a collection to another based on passed-in function then flatten the result
        println(numbers.map((x: Int) => List(x, x * 2)))
        println(numbers.flatMap((x: Int) => List(x, x * 2)))
        // 2.5) filter([func]): return another collection based on the evaluation result of the passed-in boolean function
        println(numbers.filter((x: Int) => (x % 2 == 0)))
    }
}
