// Uses of Arrays/Lists 
// 1) strict arrays   : simple lookup usage
//    arrays with allocated storage supporting quick lookup of elements (may be multiple times)
//    use strict arrays if you need to refer to most of their elements multiple times 
//    ex. arrays used in dynamic programming
// 2) nonstrict arrays: composable mapping and transformation
//    array in essense is just like a function of finite domain (indices)
//    i.e. a function that maps indexes to values (or objects) 
//    ex. arr: [0, 10] -> R 
//        arr[i] == arr(i)
//    use nonstrict arrays if the intermediate arrays are referred to exactly once
//      allocating storage for them is unnecessary in many functional programming cases
//      they are suitable for tasks that are not just look up elements
//    a) mapping over to obtain another (mapped) array
//       ex. mapping f over array arr
//           it is like composing f with arr’s lookup procedure
//           arr       : X -> Y (i.e. mapping indices to array values)
//           f         : Y -> Z (i.e. mapping array values using function f)
//           ------------------
//           arr.map(f): X -> Z
//           i.e. f(arr(i)) = (f o arr)(i) = arr.map(f)(i)
//                arr.map(f)(i) = f(arr(i)) for i in X
//    b) transforming array's indices to obtain another (transformed) array
//       ex. matrix transposing: arr[i][j] = arr[j][i]
//           transforming array arr's indices using function f(i, j) = (j, i)
//           it is like composing arr’s lookup procedure with f
//           arr       : X -> Y (i.e. mapping indices to array values)
//           f         : X -> X (i.e. function that maps a set X to itself) 
//           ------------------
//           arr.transform(f): X -> Z
//           i.e. arr(f((i, j))) = (arr o f)((i, j)) = arr.transform(f)((i, j))
//                arr.transform(f)((i, j)) = arr((j, i)) for (i, j) in X
// Laziness of Arrays/Lists: Stream vs. View in Scala
// 1) Stream: values are stored/cached if still needed
//    a List whose tail is a lazy val: it's value will be evaluated until first-time used
//    a stream is like a function of how to compute subsequent elements of the collection (just like a generator)
//      ex. defining an infinite sequence by a generator function
//    storage efficiency:
//      if you ask for the next element and it is evaluated and cached in memory 
//      before it evaluates the next element, it releases the storage and ties it to the new element 
//      we don't need to keep all of the Stream in memory, unless we keep a reference to its head
// 2) View: no value cached/stored, re-evaluated/re-computed each time accessed
//    it is like a procedure to create a (virtual) collection (just like a database view)
//      ex. a series of transformation applied to a collection to create a virtual collection
//    when you ask for elements of a view it carries out the procedure each time
//    all transformations will be re-applied each time you fetch elements from it
// 3) difference
//    Stream: elements are evaluated and cached when accessed
//            just like: lazy val [variable_name]
//    View  : elements are compuated online 
//            just like: def [variable_name] 
object LazyList {
    def main(args: Array[String]) {
        // 1) Stream[T]:
        // 1.1) explicitly define a stream
        val stream1 = 1#::2#::3#::Stream.empty
        val stream2: Stream[Int] = Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.empty)))
        val stream3 = (1 to 3).toStream
        val stream4 = (1 until 3).iterator.toStream // convert an iterator to a Stream
        println(stream1)      // Stream(1, ?): tail of the stream is not evaluated yet
        println(stream2)      // Stream(1, ?): tail of the stream is not evaluated yet
        println(stream3.head) // 1           : head is returned immediately 
        println(stream3.tail) // Stream(2, ?): tail is not evaluated yet
        println(stream4)      // Stream(1, ?): tail of the stream is not evaluated yet

        // 2) define a Stream by a recursive function (maybe infinite)
        // ex1.
        def repeat[T](a: T): Stream[T] = Stream.cons(a, repeat(a))
        // take(n: Int): Stream[T]
        //   return the n first elements of the Stream as another Stream
        println("ex1.")
        println(repeat(1))                       // Stream(1, ?)
        repeat(1).take(3).foreach(println)       // 1 1 1
        // ex2.
        def repeatSum(x: Int, y: Int): Stream[Int] = (x + y) #:: repeatSum(x, y)
        println("ex2.")
        println(repeatSum(1, 2))                 // Stream(3, ?)
        repeatSum(1, 2).take(3).foreach(println) // 3 3 3 

        // 3) Stream vs. View in Scala
        val stream = List(1, 2, 3).toStream.map(_ * 2)
        val view   = List(1, 2, 3).view.map(_ * 2)
        // mkString(sep: String): display all elements of this stream in a string
        println(stream.mkString(" ")) // values are evaluated once and stored/cached
        println(stream.mkString(" ")) // no evaluation, already evaluated
        println(view.mkString(" "))   // re-evaluated each time accessed (computed online, no extra storage) 
        println(view.mkString(" "))   // re-evaluated each time accessed (computed online, no extra storage)
    }
}
