// Closure
//   function whose return value depends on the value of variables declared outside the function

object ClosureFunciton {
    def print(func: (Int => Int), num: Int): Unit = {
        println(func(num))
    }

    def main(args: Array[String]) {
        var factor = 3
        val multiplier = (num: Int) => num * factor
        // object multiplier encapsulates an anonymous function object with an outside object
        print(multiplier, 5)        // 15
    }
}

