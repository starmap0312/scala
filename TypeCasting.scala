// isInstanceOf/asInstanceOf/classOf
object TypeCasting {

    def main(args: Array[String]) {
        // 1) isInstanceOf[T]: check if object is an instance of type T
        class Foo
        class Bar extends Foo
        val foo = new Foo   
        val bar = new Bar 
        println(foo.isInstanceOf[Foo])                    // true
        println(bar.isInstanceOf[Foo])                    // true
        println("string".isInstanceOf[String])            // true
        println(1.isInstanceOf[Double])                   // false 

        // 2) asInstanceOf[T]: cast an instance to type T (a runtime operation)
        //                     compiler believes that the type can be casted (it throws ClassCastException at runtime if casting fails) 
        println(1.asInstanceOf[Double])                   // 1.0: cast Int to Double at runtime
        //println(1.asInstanceOf[String])                 // ClassCastException at runtime
        // erasure problem: as type argument is erased as part of compilation
        //                  it is not possible to check whether the list are of requested type
        println(List(1, 2, 3).asInstanceOf[List[String]]) // not working as parameterized type [String] will be eliminated by erasure
        println(List(1, 2, 3).asInstanceOf[List[_]])      // the above is interpreted by compiler as 

        // 3) classOf[T]: returns a runtime representation of the Scala class type T
        //    def classOf[T]: java.lang.Class[T]          // i.e. a value of type Class[T] 
        //      it retrieves the runtime representation of a class type
        //      classOf[T] is equivalent to the class literal T.class in Java
        println(classOf[String])                          // class java.lang.String
        println(classOf[Array[String]])                   // class [Ljava.lang.String;
        println(classOf[Double])                          // double 
        //println(classOf[Double].cast(1))                // ClassCastException: Cannot cast java.lang.Integer to double
    }
}
