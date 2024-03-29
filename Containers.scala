// final case class Tuple1[+T1](_1: T1)
// final case class Tuple2[+T1, +T2](_1: T1, _2: T2)
object Containers {
    def main(args: Array[String]) {
        // 0) Tuples: a sequence of immutable items
        // 0.1) Tuple2: a pair of immutable items
        val pair1 = (1, "two")
        val pair2 = (1 -> "two") 
        // which are syntactic sugar of
        val pair3 = Tuple2(1, "two")       // because Tuple2 is a case class, we can omit the new operator 
        println(pair1)
        println(pair2)
        println(pair3)
        // 0.2) Tuple3: a triple of immutable items
        val tuple1 = (1, "two", 3.0)
        // which is syntactic sugar of
        val tuple2 = Tuple3(1, "two", 3.0) // because Tuple3 is a case class, we can omit the new operator
        println(tuple1)
        println(tuple2._1, tuple2._2, tuple2._3)

        // 1) Array[T]
        val arr1: Array[String] = new Array[String](3) // var arr1 = new Array[String](3)
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
        // 1.3) foreach([function])
        arr2.foreach(println _)

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

        // 3) Map[T1, T2]: a hash table from key of type T1 to value of type T2
        val integers = Map("one" -> 1, "two" -> 2)
        println(integers.keys)           // Set(one, two) 
        println(integers.values)         // MapLike(1, 2)
        integers.keys.foreach(
            k => println(k, integers(k))
        )
        // 3.1) filterKeys() and mapValues():
        //      transformations that produce a new map by filtering and transforming bindings of an existing map
        //      ex. mp.filterKeys(p): p is a predicate, a view containing only those mappings in mp where the key satisfies predicate p
        //          mp.mapValues(f):  f is a function/mapping, applying function f to each value associated with a key in mp 
        val mp = Map("a" -> 1, "b" -> 2)
        println(mp.mapValues(_ + 1))     // Map(a -> 2, b -> 3)
    }
}
