object Classes {
    class MyClass(x: Int, y: Int) {           // defines a new type MyClass with a constructor  
      require(y > 0, "y must be positive")    // require(): precondition/validation, throwing IllegalArgumentException if not met  
      def this(x: Int) = {                   // secondary constructor
          this(x, x)
      }
      def nb1 = x                             // public method computed every time it is called  
      def nb2 = y  
      private def priv(a: Int): Int = {       // private method  
          return nb1 + nb2 + a
      }
      val nb3 = x + y                         // computed only once  
      override def toString =                 // overridden method  
          "This is a MyClass instance with " + nb1 + " and " + nb2 + " and priv = " + priv(3)
    }

    def main(args: Array[String]) {
        val obj1 = new MyClass(1, 2)           // creates a new object of type MyClass using primary constructor
        println(obj1)
        val obj2 = new MyClass(3)              // creates a new object of type MyClass using secondary constructor
        println(obj2)
    }
}
