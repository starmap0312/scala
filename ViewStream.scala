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
//
object ViewStream {
    def main(args: Array[String]) {
        // 1) Stream: lazy list
        //   in Scala, a Stream is a List whose tail is a lazy val
        //   once computed, a value stays computed and is reused (i.e. Stream memoises, the values are stored/cached)
        //   advantage:
        //     when writing infinite sequences, ex. sequences recursively defined
        //     one can avoid keeping all of the Stream in memory, if you don’t keep a reference to its head
        //     ex. by using def instead of val to define the Stream
        // 2) Views: much like a database view
        //   it is a series of transformation one applies to a collection to produce a virtual collection
        //   all transformations are re-applied each time you need to fetch elements from it
        //   advantage:
        //     memory efficiency

        // Stream vs. Views
        //   View  : elements are recomputed each time they are accessed
        //   Stream: elements are retained as they are evaluated
        val doubled1 = List(1, 2, 3, 4, 5).view.map(_ * 2)
        println(doubled1.mkString(" ")) // re-evaluate the map for each element once
        println(doubled1.mkString(" ")) // re-evaluate the map for each element twice
        val doubled2 = List(1, 2, 3, 4, 5).toStream.map(_ * 2)
        println(doubled2.mkString(" "))
        println(doubled2.mkString(" ")) // only double the elements once (values are stored/cached) 
        // a view is like a procedure to create a collection
        //   when you ask for elements of a view it carries out the recipe each time
        // a stream is like a definition of how to compute subsequent elements of the collection
        //   you ask for the next element of the collection and it gives you the element and a string to help it remember
        //   before it gives you the next element, it unties the first string and ties it to the new element 
        //   but if you hold on the first element, you might eventually run out of memory
    }
}