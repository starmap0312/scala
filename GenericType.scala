// variable that is assigned _:
//   ex. var variable: T = _
//   this initializes the variable to a default value
//   i.e.
//     0     for T is Int
//     0L    for T is Long
//     false for T is Boolean

object GenericType {
    class Generic[T] {
        private var data: T = _
        def set(value: T): Unit = { data = value }
        def get(): T = { return data }
    }

    def main(args: Array[String]) {
        // example
        val num = new Generic[Int]
        println("default: " + num.get())
        num.set(123)
        println("after set: " + num.get())
        // example
        val str = new Generic[String]
        println("default: " + str.get())
        str.set("abc")
        println("after set: " + str.get())
    }
}
