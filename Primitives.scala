// 1) use of {}:
//    a) {} indicates a block of code, which is composed of multiple statements and declarations
//       its value is that of the last statement
//    b) however, there are some exceptions to the above definition
// 2) new operator:
//    a) use new when you want to refer to a class's own constructor
//       ex.
//         class Foo { }
//         val foo = new Foo
//    b) don't use new if you are referring to the companion object's apply method
//       ex.
//         class Foo { }
//         object Foo {
//             def apply() = new Foo
//         }
//       both of these are legal
//         val foo1 = Foo()
//         val foo2 = new Foo
//    c) if you've made a case class
//       ex.
//         case class Foo()
//       Scala secretly creates a companion object for you, turning it into this:
//         class Foo { }
//         object Foo {
//             def apply() = new Foo
//         }
//       so you can do
//         foo = Foo()

object Primitives {

    def main(args: Array[String]) {
        // 0) type: a shorthand or alias of [some type]
        //    syntax:  type [alias] = [some type]
        type MyIntFunc = (Int => Int)
        val increment: MyIntFunc = ((x) => x + 1)
        println(increment(9))
     
        // 1) var vs. val
        var myVar: String = "myVar"
        val myVal: String = "myVal"
        myVar = "myVar2"            // note: myVal = "def" throws error: reassignment to val
        println(myVar)
        println(myVal)

        // 2) String: length() and concat()
        var palindrome = "Dot saw I was Tod"
        println(palindrome.length())
        println(palindrome.concat(" appended"))
        println(palindrome + " appended")

        // 2.1) Symbol: similar to a String except that they are cached
        //              to compare String instances, you may need to check character-by-character
        //              to compare Symbol instances, it is only a constant time look-up
        val symbols = Symbol("Symbol string")
        println(symbols)

        // 3) Stream: lazy list
        val stream1 = 1#::2#::3#::Stream.empty
        val stream2 = (1 to 3).toStream         // use Stream to avoid java.lang.OutOfMemory error when creating huge lists
        stream1.foreach(println)
        stream2.foreach(println)
        val list = 1::2::3::Nil
        list.foreach(println)
        // 3.1) Stream.cons([element], [stream]) returns a stream
        val stream3 = Stream.cons(0, Stream.cons(1, Stream.empty))   // cons returns the rest of the stream NOT another new stream
        stream3.foreach(println)

        // 4) define function of varying number of arguments
        def printSeq(arg: Int*): Unit = {
            println(arg)
        }
        printSeq(7, 8, 9)                        // WrappedArray(7, 8, 9)

        // 5) new operator
        class Foo { } // class
        object Foo {  // companion object: static members/methods
            def apply() = 7
        }
        println(new Foo)                         // Main$Foo$2@d59970a ... class's constructor
        println(Foo())                           // 7                  ... companion object's apply() method

        // 6) fixity
        //    almost everything in Scala is read left to right, except for : (read right to left)
        //    ex. def x: Int = 3
        //        val x      = 1::2::Nil
        println(1 + 2 :: Nil)                    // List(3)
        println(Nil.::((1).+(2)))                // List(3)
    }
}
