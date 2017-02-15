
object Containers {
    def main(args: Array[String]) {
        // 1) List[T]
        val fruits: List[String] = List("apple", "orange", "pear")
        // 1.1) iterate List via for-loop
        for (fruit <- fruits) {
            println(fruit)
        }
        // 1.2) iterate List via Collections's foreach([func]) method
        fruits.foreach(println) 
        fruits.foreach((fruit: String) => {println("Fruit: " + fruit)})
    }
}
