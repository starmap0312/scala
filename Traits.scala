// Scala object
// 1) object is a single instance of an anonymous class (singleton)
//    automatically instantiated when first-time access, access the object directly by name, no new keyword is needed
//    note: until it is accessed the first time it won’t get instantiated
// 2) as Scala's class cannot have static members, you need to define them in the class's companion object instead
// 3) object's methods and fields are static, ex. object A.func() or A.x
//    used when for static methods of a class (i.e. not tied to instance data and accessible without instantiation)
//    but it is not safe (ex. accidentally invoking class's global method or storing mutable data in class's global field)
// 4) object can have special methods: ex. apply(), unapply()
// Scala class
//   defined once but can be instantiated an unlimited number of times
// Scala trait
// 1) a class can extend multiple traits (it makes multiple inheritances possible
//    ex. classA extends classB with traitC with traitD
// 2) an abstract class can have constructor parameters, whereas a trait cannot
// 3) a trait can be partially implemented (like Java 8)
//    similar to Java interfaces except they can have non-abstract members (i.e. concrete members)
// 4) a trait cannot be instantiated
// 5) a trait is used to define object types by specifying signature of its methods
// Comparison: Scala class vs. abstract class vs. trait
// 1) class         : a class can only extend one superclass (classA extends classB with TraitA with TraitB)
//    abstract class: constructor parameters (O), type parameters (O), compatible with Java (O)
//    trait         : constructor parameters (X), type parameters (O), compatible with Java (X)
//                    a trait needs a wrapper in order to be used in Java, unless it contains no implementation code
// 2) class         : if the behavior will not be reused, then make it a concrete class
//    abstract class: if you want to inherit in Java code, then use an abstract class instead of a trait
//    trait         : if the behavior might be reused in multiple, unrelated classes, make it a trait
// Comparison: Scala abstract class vs. trait
// 1) an class can extend several traits, but a class can extend only one abstract class
// 2) trait constructors cannot take parameters, but abstract class constructors can
//    ex. class Person(val name: String, val age: Int) { ... }   // name and age are just like Java's public final fields
// scala object vs. class
// 1) object is a single instance of a class (singleton)
// 2) object's methods are static, ex. A.f()

object Traits {
    def main(args: Array[String]) {
        trait Equal {                                     // no constructor parameter
            def isEqual(x: Any): Boolean                  // specify only method signatures
            def isNotEqual(x: Any): Boolean = !isEqual(x) // specify only method signatures
        }

        class Point(xc: Int, yc: Int) extends Equal {     // can have constructor parameter
            var x: Int = xc
            var y: Int = yc
            def isEqual(obj: Any) = obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == y
        }
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
