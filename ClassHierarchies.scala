object ClassHierarchies {
    abstract class TopLevel {                 // an abstract class
        def method1(x: Int): Int              // method1 undefined
        def method2(x: Int): Int = {
            println("TopLevel::method2")
            return x
        }
    }

    class LowLevel extends TopLevel {         // a concrete class 
        def method1(x: Int): Int = {          // method1 defined
            println("LowLevel::method1")
            return x + 1
        }
        override def method2(x: Int): Int = { // method2 overriden
            println("LowLevel::method2")
            return x + 2 
        }
    }

    object MyObject extends TopLevel {        // a singleton object (no other instance can be created)
        def method1(x: Int): Int = {          // a concrete class
            println("MyObject::method1")
            return x + 3 
        }
    }

    def main(args: Array[String]) {
        // val obj = new TopLevel()           // abstract class cannot be instantiated 
        val obj1 = new LowLevel()
        println(obj1.method1(3))
        println(obj1.method2(3))

        println(MyObject.method1(3))
        println(MyObject.method2(3))
    }
}
