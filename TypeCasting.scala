// classOf[T]/isInstanceOf[T]/asInstanceOf[T]
// 1) classOf[T]:
//    a global method that returns a Class[T] instance (a runtime type representation of class T)
//    it is defined in scala.Predef as:
//    object Predef {
//        def classOf[T]: Class[T] = null 
//    }
// 2) isInstanceOf[T] and asInstanceOf[T]:
//    a method of Any object used to check if the object's type is compatiable with class T
//    it is defined in scala.Any
//    ex. obj.isInstanceOf[String]
//        obj.asInstanceOf[List[_]]
// Type-Cast: x.asInstanceOf[T]
//   it is a cast, it is only needed by the compiler to enforce the type compatibility
//   it does not need to do anything at all: a reference is a reference, regarding of the type of underlying object
// Type-Test: x.isInstanceOf[T]
//   isInstanceOf is the opposite: compiler does not know anything about it, it's just a function call
//   it is executed at runtime to check whether the given object is of the expected type
//   so we will need a real Class instance if we need the information at runtime
// note type parameters are not available at runtime, all information they carry can only be used by the compiler
// Scala               vs. Java
// -------------------     ----------------
// classOf[T]          vs. T.class
// obj.isInstanceOf[T] vs. obj instanceof T
// obj.asInstanceOf[T] vs. (T)obj
//
// obj.getClass() vs. T.class in Java
//   obj.getClass() returns the runtime type representation of obj
//     ex. "123".getClass: i.e. Class[_ <: String] = class java.lang.String
//     i.e. if you have A a = new B(), then a.getClass() will return the B class
//   A.class evaluates to the A class statically
//     it is used for other purposes often related to reflection

object TypeCasting {

    def main(args: Array[String]) {
        // 1) isInstanceOf[T]: check if object is an instance of calss T
        println("string".isInstanceOf[String])            // true
        println("string".isInstanceOf[Any])               // true
        println(1.isInstanceOf[Double])                   // false 
        class Foo
        class Bar extends Foo
        val foo = new Foo   
        val bar = new Bar 
        println(foo.isInstanceOf[Foo])                    // true
        println(bar.isInstanceOf[Foo])                    // true

        // 2) asInstanceOf[T]: cast an instance to type T (a runtime operation)
        //                     compiler believes that the type can be casted at compile-time
        //                     it throws ClassCastException if casting fails at runtime
        println(1.asInstanceOf[Double])                   // 1.0: cast Int to Double at runtime
        //println(1.asInstanceOf[String])                 // throws ClassCastException at runtime
        // erasure problem: as type argument is erased as part of compilation
        //                  it is not possible to check whether a List is of a requested type
        println(List(1).asInstanceOf[List[String]])       // not working as type parameter is eliminated by erasure
        // the above is interpreted by compiler as the following 
        println(List(1).asInstanceOf[List[_]])

        // 3) classOf[T]: returns a runtime type representation of a Scala class
        //    def classOf[T]: Class[T]                    // returns a value of type java.lang.Class[T] 
        //    note: classOf[T] is equivalent to the class literal T.class in Java
        println(classOf[Number])                          // a Class[Number] instance, i.e. a Number type representation
        println(classOf[String])                          // a Class[String] instance, i.e. a String type representation
        println(classOf[List[String]])                    // a Class[List[_]] instance, i.e. a List type representation 
        // the above is equavalient to the following 
        println(classOf[List[_]])                         // a Class[List[_]] instance, i.e. a List type representation 
        // 3.1) isInstance(obj): Boolean
        //      if ctag = classOf[T], ctag.isInstance(obj) == obj.isInstanceOf[T]
        //      determines if the specified Object is assignment-compatible with the object represented by this Class
        //      this method is the dynamic equivalent of the Java language instanceof operator
        println(classOf[Number].isInstance(1))            // true, check if an object is an instance of class Number
        println(classOf[String].isInstance(1))            // false, check if an object is an instance of class String 
        println(classOf[List[String]].isInstance(List(1)))// true, check if an object is an instance of class List 
        // 3.2) cast(obj): T
        //      if ctag = classOf[T], ctag.cast(obj) == obj.asInstanceOf[T]
        //      Casts an object to the class or interface represented by this Class object
        println(classOf[Number].cast(1))                  // cast object 1 into Number class 
        //println(classOf[Number].cast("a"))              // ClassCastException: Cannot cast String to Number 
        //println("a".asInstanceOf[Number])               // ClassCastException: Cannot cast String to Number 

        // 4) asInstanceOf[T] vs. isInstanceOf[T]
        val num: Any = 123
        val str: Any = "abc"
        //num.asInstanceOf[String]                        // compiler trusts you about the casting, but we will get ClassCastException at runtime
        str.asInstanceOf[String]                          // OK, String = abc
        num.isInstanceOf[String]                          // OK, Boolean = false
        str.isInstanceOf[String]                          // OK, Boolean = true
        def as[T](x: Any): T = x.asInstanceOf[T]          // generic type will be lost at runtime
        as[String](num)                                   // OK, no effect
        as[String](str)                                   // OK, no effect
        def is[T](x: Any): Boolean = x.isInstanceOf[T]    // compile warning: abstract type T is unchecked since it is eliminated by erasure
        is[String](num)                                   // OK, Boolean = true
        is[String](str)                                   // OK, Boolean = true
    }
}
