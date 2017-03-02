// classOf/isInstanceOf/asInstanceOf
object Casting {

    def main(args: Array[String]) {
        // isInstanceOf[T]: check if object is an instance of type T
        class Foo
        class Bar extends Foo
        val foo = new Foo   
        val bar = new Bar 
        println(foo.isInstanceOf[Foo])                    // true
        println(bar.isInstanceOf[Foo])                    // true
        println("string".isInstanceOf[String])            // true
        println(1.isInstanceOf[Double])                   // false 

        // 2) asInstanceOf[T]: cast an instance to type T
        println(1.asInstanceOf[Double])                   // 1.0: cast Int to Double
        println(List(1, 2, 3).asInstanceOf[List[String]]) // not working as parameterized type [String] will be eliminated by erasure
        println(List(1, 2, 3).asInstanceOf[List[_]])      // the above is interpreted by compiler as 

        // 3) classOf[T]: returns a runtime representation of the Scala class type T
        println(classOf[String])                          // class java.lang.String
        println(classOf[Array[String]])                   // class [Ljava.lang.String;
    }
}
