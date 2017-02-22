// 1) strict arrays:
//    arrays with allocated storage supporting quick lookup of elements (may be multiple times)
//    make arrays strict when you must refer to most of their elements more than once or twice
//      ex. arrays used in dynamic programming
// 2) nonstrict arrays:
//    think of every array as if it is a function (of finite domain, ex. [0, 100]) 
//      i.e. a function that maps indexes to elements
//      ex. arr(i): [0, 100] -> arr[i]
//    in functional code that operates on arrays, the intermediate arrays are referred to exactly once
//      so allocating storage for them is unnecessary
//      nonstrict arrays are suitable for many other tasks than just look up elements
//    a) mapping over to another array
//       mapping f over array arr is just composing f with arr’s lookup procedure
//         arr       : X -> Y
//         f         : Y -> Z
//         arr.map(f): X -> Z (i.e. arr.map(f)(x) = f(arr(x)) = (f o arr)(x))
//       ex.
//         let arr2 = arr.map(f)
//         arr2(i) = f(arr(i)) = (f o arr)(i)
//    b) transforming array's indices
//       a transformation is a function g that maps a set X to itself, g: X -> X (from old indices to new indices)
//       transforming arr using g is nothing more than composing arr’s procedure with g
//       ex. matrix transposing: arr[i][j] = arr[j][i]
//           transformation g: g((i, j)) = (j, i)
//           arr.transform(g)((i, j)) = arr(g((i, j))) = (arr . g)((i, j)) = arr((j, i))
// Stream: lazy list
//   in Scala, a Stream is a List whose tail is a lazy val
//   once computed, a value stays computed and is reused (i.e. Stream memoises, the values are stored/cached)
//   advantage:
//     when writing infinite sequences, ex. sequences recursively defined
//     one can avoid keeping all of the Stream in memory, if you don’t keep a reference to its head
//     ex. by using def instead of val to define the Stream
// Views: much like a database view
//   it is a series of transformation one applies to a collection to produce a virtual collection
//   all transformations are re-applied each time you need to fetch elements from it
//   advantage:
//     memory efficiency
// Stream vs. Views
//   Stream: elements are retained as they are evaluated (and drop if not needed anymore)
//     a stream is like a definition of how to compute subsequent elements of the collection
//     if you ask for the next element and it evaluates the element and remember it in a storage
//     if you hold on all the elements, you might eventually run out of memory
//     before it evaluates the next element, it releases the storage and ties it to the new element 
//   View  : elements are recomputed each time they are accessed (like a generator?)
//     a view is like a procedure to create a collection
//     when you ask for elements of a view it carries out the procedure each time
object ViewStream {
    def main(args: Array[String]) {
        // example: View
        val doubled1 = List(1, 2, 3, 4, 5).view.map(_ * 2)
        // example: Stream
        val doubled2 = List(1, 2, 3, 4, 5).toStream.map(_ * 2)
        // 1) mkString(sep: String): display all elements of this stream in a string
        println(doubled1.mkString(" ")) // re-evaluate the map for each element once
        println(doubled1.mkString(" ")) // re-evaluate the map for each element twice
        println(doubled2.mkString(" ")) // only double the elements once (values are stored/cached)
        println(doubled2.mkString(" ")) // only double the elements once (values are stored/cached) 
        // 2) Stream.cons(): define a Stream using Stream.cons
        val stream:Stream[Int] = Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.empty)))
        println(stream)
        // 2.1) define a Stream using a recursive function
        def repeat[T](a: T): Stream[T] = Stream.cons(a, repeat(a))
        // 3) take(n: Int): Stream[T]
        //    return the n first elements of the Stream as another Stream
        repeat(0).take(3).foreach(println)
    }
}
