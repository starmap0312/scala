object ClassConstructor {
    class MyClass(x: Int, y: Int) {           // defines a new type MyClass with a constructor  
      require(y > 0, "y must be positive")    // require(): precondition/validation, throwing IllegalArgumentException if not met  
      def this(x: Int) = {                    // secondary constructor
          this(x, x)
      }
      def nb1 = x                             // a value (public field), computed every time it is accessed
      def nb2 = y                             // a value (public field), computed every time it is accessed
      private def priv(a: Int): Int = {       // a method (private method), computed every time it is called 
          return nb1 + nb2 + a
      }
      val nb3 = x + y                         // a value (public field), computed only once  
      override def toString =                 // overridden method  
          "This is a MyClass instance with " + nb1 + " and " + nb2 + " and priv = " + priv(3)
    }

    def main(args: Array[String]) {
        val obj1 = new MyClass(1, 2)           // creates a new object of type MyClass using primary constructor
        println(obj1)                          // This is a MyClass instance with 1 and 2 and priv = 6
        println(obj1.nb1)                      // 1
        println(obj1.nb2)                      // 2
        println(obj1.nb3)                      // 3
        val obj2 = new MyClass(3)              // creates a new object of type MyClass using secondary constructor
        println(obj2)                          // This is a MyClass instance with 3 and 3 and priv = 9
        println(obj2.nb1)                      // 3
        println(obj2.nb2)                      // 3
        println(obj2.nb3)                      // 6
    }
}
