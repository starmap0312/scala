// take a function as a parameter or return functions
object HigherOrderFunctions {
    def main(args: Array[String]) {
      // example: define funciton g(f(x))
      def g_of_f(x: Int): Int = {
          def f(x: Int): Int = {
              x * x
          }
          f(x) + 1 
      }
      println(g_of_f(1))
      println(g_of_f(2))
      println(g_of_f(3))
      // sum() returns a function that takes two integers and returns an integer  
      def sum(f: Int => Int): (Int, Int) => Int = {  
          def sumf(a: Int, b: Int): Int = { return f(a) + f(b) }  
          sumf  
      } 
      println(sum((x: Int) => x * x)(1, 2))

    /*
    // same as above. Its type is (Int => Int) => (Int, Int) => Int  
    def sum(f: Int => Int)(a: Int, b: Int): Int = { ... } 

    // Called like this
    sum((x: Int) => x * x * x)          // Anonymous function, i.e. does not have a name  
    sum(x => x * x * x)                 // Same anonymous function with type inferred

    def cube(x: Int) = x * x * x  
    sum(x => x * x * x)(1, 10) // sum of cubes from 1 to 10
    sum(cube)(1, 10)           // same as above 
    */
    }
}

