// object
// 1) object is a single instance of an anonymous class (singleton)
//    automatically instantiated, access the object directly by name, no new keyword is needed
//    note: until it is accessed the first time it won’t get instantiated
// 2) object's methods and fields are static, ex. object A.func() or A.x
//    in class, there are static methods, not tied to instance data and accessible without instantiation
//    not safe: ex. accidentally invoking class's global method or storing mutable data in class's global field
// 3) object can have special methods: ex. apply(), unapply()
// class
//   defined once but can be instantiated an unlimited number of times
// trait
//   similar to Java interfaces except they can have non-abstract members
//     used to define object types by specifying signature of its methods
//     it cannot be instantiated
//     it make multiple inheritances possible: i.e. class can extend multiple traits
//       ex. class D extends class A with trait B with trait C
//   like in Java 8, Scala allows traits to be partially implemented
// class vs. abstract class vs. trait
// 1) class         : it can only extend one superclass
//    abstract class: constructor parameters (O), type parameters (O), compatible with Java (O)
//    trait         : constructor parameters (X), type parameters (O), compatible with Java (X)
//                    it needs wrapper to be used in Java, unless it contains no implementation code
// 2) class         : if the behavior will not be reused, then make it a concrete class
//    abstract class: if you want to inherit from a trait in Java code, use an abstract class
//    trait         : if the behavior might be reused in multiple, unrelated classes, make it a trait
// object vs. class
// 1) object is a single instance of a class (singleton)
// 2) object's methods are static, ex. A.f()
// 3) object can have special methods: ex. apply(), unapply()
trait Equal {                                     // no constructor parameter
    def isEqual(x: Any): Boolean                  // specify only method signatures
    def isNotEqual(x: Any): Boolean = !isEqual(x) // specify only method signatures
}

class Point(xc: Int, yc: Int) extends Equal {     // can have constructor parameter
   var x: Int = xc
   var y: Int = yc
   def isEqual(obj: Any) = obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == y
}

object Demo {
    def main(args: Array[String]) {
        // example: trait & class
        val p1 = new Point(2, 3)
        val p2 = new Point(2, 4)
        val p3 = new Point(3, 3)
        println(p1.isNotEqual(p2))
        println(p1.isNotEqual(p3))
        println(p1.isNotEqual(2))

        // example: object
        trait B
        trait C
        object A extends B with C {
            // a single instance (singleton) of an anonymous class (inaccessible) that extends B and C
            // can define special methods: ex. apply() and unapply() for pattern matching
            def f(x: Any): Any = "f's return value"
        }
        println(A.f())
   }
}
