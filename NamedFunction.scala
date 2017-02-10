// Scala supports first-class functions
//   functions can be expressed in function literal syntax
//   functions can be represented by objects (called function values)
// Syntax 
//   def functionName ([list of parameters]) : [return type] = {
//       function body
//       return [expr]
//   }

object NamedFunciton {
    def addInt(a:Int, b:Int): Int = {
        return a + b
    }
    def main(args: Array[String]) {
        println(addInt(1, 2))
    }
}

