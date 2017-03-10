// variable that is assigned with value _ has the default value of its type:
//   syntax: var variable: T = _
//   ex.
//     0     for T is Int
//     0L    for T is Long
//     false for T is Boolean
// upper, lower and view bounds: i.e. <: >: <%

object GenericType {
    class Generic[T](arg: T) {
        private var data: T = _
        private var value: T = arg
        def setData(x: T): Unit = { data = x }
        def getData(): T = { return data }
        def getValue(): T = { return value }
    }

    def main(args: Array[String]) {
        // example
        val num = new Generic[Int](10)
        println("default: data = " + num.getData())
        println("default: value = " + num.getValue())
        num.setData(123)
        println("after setData: data = " + num.getData())
        // example
        val str = new Generic[String]("ten")
        println("default: data = " + str.getData())
        println("value = " + str.getValue())
        str.setData("abc")
        println("after setData: data = " + str.getData())
    }
}
